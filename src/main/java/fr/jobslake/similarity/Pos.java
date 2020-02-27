package fr.jobslake.similarity;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.bytedeco.opencv.presets.opencv_core;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;


public class Pos {

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

    public static String cleanSentence(String sentence) {
        return sentence.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
    }

    public String tagSentence(String sentence) {
        return this.tagger.tagString(sentence);

    }

    public HashMap<String, ArrayList<String>> buildSentenceTags(String taggedSentence){

        String[] tokensVal = taggedSentence.split(" ");
        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();
        builtTags.put("skill", new ArrayList<String>());

        for(String token:tokensVal) {
            String[] keyVal = token.split("_");
            // change "this.isSkill(keyVal[0])" with taxonomy.isLeaf(keyVal[0])
            if(this.isSkill(keyVal[0])){
                ArrayList<String> vals = new ArrayList<String>(builtTags.get("skill"));
                vals.add(keyVal[0]);
                builtTags.replace("skill", builtTags.get("skill"), vals);
            }else{
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

    public HashMap<String, ArrayList<String>> buildSentenceSuperTags(String taggedSentence){

        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();
        builtTags.put("skill", new ArrayList<String>());

        List<String> threeGrams = ngrams(3, taggedSentence);

        for (String element:threeGrams){
            if(this.isSkill(formatString(element))){
                ArrayList<String> vals = new ArrayList<String>(builtTags.get("skill"));
                vals.add(formatString(element));
                builtTags.replace("skill", builtTags.get("skill"), vals);
                taggedSentence = taggedSentence.replaceAll(element, "__UNK__");
            }
        }

        List<String> biGrams = ngrams(2, taggedSentence);
        for (String element:biGrams){
            if(this.isSkill(formatString(element))){
                ArrayList<String> vals = new ArrayList<String>(builtTags.get("skill"));
                vals.add(formatString(element));
                builtTags.replace("skill", builtTags.get("skill"), vals);
                taggedSentence = taggedSentence.replaceAll(element, "__UNK__");
            }
        }

        List<String> uniGrams = ngrams(1, taggedSentence);
        for (String element:uniGrams){
            if(this.isSkill(formatString(element))){
                ArrayList<String> vals = new ArrayList<String>(builtTags.get("skill"));
                vals.add(formatString(element));
                builtTags.replace("skill", builtTags.get("skill"), vals);
                taggedSentence = taggedSentence.replaceAll(element, "");
            }
        }


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

    public String formatString(String testString){
        return testString.replaceAll("_[A-Z]+", "");
    }

    public double getSimilarity(String word1, String word2) {

        return this.vec.similarity(word1, word2);
    }

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

    public boolean isSkill(String word){
        return this.skills.contains(word);
    }

    public double filtredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2) {

        double totalSimilarity = 0;
        double idfSum = 0;

        for(String key:sentence1.keySet()){

            double partialSimilarity = 0;
            if(sentence2.containsKey(key)){
                ArrayList<String> listSentence1 = sentence1.get(key);
                ArrayList<String> listSentence2 = sentence2.get(key);
                for(String word1:listSentence1){
                    double maxSimilarityWord = 0;
                    double idfValue = this.getIdfValue(word1);
                    if(idfValue != 0){

                        for(String word2:listSentence2){
                            double sim = this.getSimilarity(word1, word2);
                            if(!Double.isNaN(sim)){
                                maxSimilarityWord = Math.max(maxSimilarityWord, sim);
                            }
                        }
                        partialSimilarity += maxSimilarityWord*idfValue;
                        idfSum += idfValue;
                    }

                }
            }
            totalSimilarity += partialSimilarity;
        }
        return totalSimilarity/idfSum;
    }

    public double posFiltredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2){

        return  (this.filtredSimilarity(sentence1, sentence2) + this.filtredSimilarity(sentence2, sentence1))/2;

    }

}