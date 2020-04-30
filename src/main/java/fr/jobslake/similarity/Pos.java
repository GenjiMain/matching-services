package fr.jobslake.similarity;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


import org.bson.Document;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;


public class Pos implements Similarity{

    MaxentTagger tagger;
    Word2Vec vec;
    File gModel;
    MongoClientURI connectionString;
    MongoClient mongoClient;
    MongoDatabase database;
    MongoCollection<Document> collection;
    ArrayList<String> skills;

    public Pos() throws IOException {
        this.tagger = new MaxentTagger("C:\\Users\\mehdi\\Documents\\GitHub\\matching-services\\src\\models\\english-caseless-left3words-distsim.tagger");
        this.gModel = new File("C:\\Users\\mehdi\\Downloads\\glove.6B\\glove.6B.50d.txt");
        this.vec = (Word2Vec) WordVectorSerializer.loadTxtVectors(this.gModel);
        this.connectionString = new MongoClientURI("mongodb://localhost:27017");
        this.mongoClient = new MongoClient(this.connectionString);
        this.database = this.mongoClient.getDatabase("tfidfdatabase");
        this.collection = this.database.getCollection("idf");
        this.skills = new ArrayList<String>();
        this.skills.add("python");
        this.skills.add("java");
        this.skills.add("data science");
        this.skills.add("machine learning");
    }

    public Pos(String taggerPath) {
        this.tagger = new MaxentTagger(taggerPath);
    }

    // Clean the sentence
    public String cleanSentence(String sentence) {
        return sentence.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
    }

    // Generate n grams from the sentence
    public List<String> ngrams(int n, String sentence) {
        String[] tokensVal = sentence.split(" ");
        List<String> ngrams = new ArrayList<String>();
        String textAdd = "";
        int i = 0;

        for (i = 0; i < tokensVal.length - n + 1; i++) {
            textAdd = "";
            for (int j = 0; j < n; j++) {
                textAdd = textAdd + tokensVal[i + j] + " ";
            }
            ngrams.add(textAdd.trim());
        }
        return ngrams;
    }


    public String formatString(String testString){
        return testString.replaceAll("_[A-Z]+", "");
    }

    // add skills of maximun n graams to list
    public String addSkillMap(String taggedSentence, int maxGram, HashMap<String, ArrayList<String>> builtTags){
        int iterator = maxGram;

        while(iterator > 0){

            List<String> listGrams = ngrams(iterator, taggedSentence);
            for (String element:listGrams){
                if(this.isSkill(formatString(element))){
                    ArrayList<String> vals = new ArrayList<String>(builtTags.get("skill"));
                    vals.add(formatString(element));
                    builtTags.replace("skill", builtTags.get("skill"), vals);
                    taggedSentence = taggedSentence.replaceAll(element, "__UNK__");
                }
            }
            iterator--;
        }

        return taggedSentence;
    }

    // Build the tags using the tagger and the addSkillMap function
    public HashMap<String, ArrayList<String>> buildSentenceSuperTags(String sentence){

        String taggedSentence = this.tagger.tagString(sentence);

        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();
        builtTags.put("skill", new ArrayList<String>());

        taggedSentence = addSkillMap(taggedSentence, 2, builtTags);
        String[] tokensVal = taggedSentence.split(" ");
        for(String token:tokensVal) {
            if (token.equals("__UNK__") | token.equals(" ") | token.equals("")){
            }
            else {
                String[] keyVal = token.split("_");
                // change "this.isSkill(keyVal[0])" with taxonomy.isLeaf(keyVal[0])
                if (builtTags.containsKey(keyVal[1])) {
                    ArrayList<String> vals = new ArrayList<String>(builtTags.get(keyVal[1]));
                    vals.add(keyVal[0]);
                    builtTags.replace(keyVal[1], builtTags.get(keyVal[1]), vals);
                }else {
                    String key = keyVal[1];
                    ArrayList<String> vals = new ArrayList<String>();
                    vals.add(keyVal[0]);
                    builtTags.put(key, vals);
                }
            }
        }
        return builtTags;
    }


    // Calculate the editDistance between two words
    public double distance(String a, String b) {

        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()]/b.length();
    }

    // Calculate the similarity between two skills
    // (use the edit distance if skills have more than 1 word else use the glove similarity)
    public double getSimilarity(String word1, String word2) {

        if (word1.split(" ").length != 1 || word2.split(" ").length != 1){
            return distance(word1, word2);
        }

        return this.vec.similarity(word1, word2);
    }

    // Get the calculated idf from the mongoDb dataBase
    public double getIdfValue(String word){

        double maxIdf = 0;
        BasicDBObject query = new BasicDBObject("word", new BasicDBObject("$eq", word));

        try(MongoCursor<Document> cur = this.collection.find(query).iterator()){

            while(cur.hasNext()){
                Document doc = cur.next();
                maxIdf = Math.max(maxIdf, (double) doc.get("idf"));
            }
        }

        return maxIdf;
    }

    // chack if the word belongs to the skills table
    public boolean isSkill(String word){
        return this.skills.contains(word);
    }


    // Calculate the similarity of two sentence taking account the calculated tags
    public double filtredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2) {

        double totalSimilarity = 0;
        double idfSum = 0;
        double skillsSimilarity = 0;

        for(String key:sentence1.keySet()){

            double partialSimilarity = 0;
            if(sentence2.containsKey(key)){
                if (key.equals("skill")){
                    ArrayList<String> listSentence1 = sentence1.get(key);
                    ArrayList<String> listSentence2 = sentence2.get(key);
                    for(String word1:listSentence1){
                        double maxSimilarityWord = 0;
                        for(String word2:listSentence2){
                            maxSimilarityWord = Math.max(maxSimilarityWord, this.getSimilarity(word1, word2));

                        }
                        skillsSimilarity = maxSimilarityWord;
                    }
                    skillsSimilarity = (listSentence1.size() == 0) ? 0 : skillsSimilarity/listSentence1.size();
                }
                else{
                    ArrayList<String> listSentence1 = sentence1.get(key);
                    ArrayList<String> listSentence2 = sentence2.get(key);
                    for(String word1:listSentence1){
                        double maxSimilarityWord = 0;
                        double idfValue = this.getIdfValue(word1);
                        if(idfValue != 0) {
                            for (String word2 : listSentence2) {
                                double sim = this.getSimilarity(word1, word2);
                                if (!Double.isNaN(sim)) {
                                    maxSimilarityWord = Math.max(maxSimilarityWord, sim);
                                }
                            }
                            partialSimilarity += maxSimilarityWord * idfValue;

                            idfSum += idfValue;
                        }
                    }
                }
            }
            totalSimilarity += partialSimilarity;
        }
        double score = (idfSum == 0 ) ? skillsSimilarity : (totalSimilarity/idfSum + skillsSimilarity)/2;

        return score;
    }

    // Calculate final similarity
    @Override
    public double ScoreSimilarity(String sentence1, String sentence2) {

        HashMap<String, ArrayList<String>> builtSenetence1Tags = this.buildSentenceSuperTags(sentence1);

        HashMap<String, ArrayList<String>> builtSenetence2Tags =  this.buildSentenceSuperTags(sentence2);

        return (this.filtredSimilarity(builtSenetence1Tags, builtSenetence2Tags) + this.filtredSimilarity(builtSenetence2Tags, builtSenetence1Tags))/2;
    }
}