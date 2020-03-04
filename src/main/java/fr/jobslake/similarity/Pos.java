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

import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;
import fr.jobslake.utils.taxonomy.Node;
import fr.jobslake.utils.taxonomy.Taxonomy;
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


    Taxonomy taxonomy = Taxonomy.getInstance();

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

        Node node1 = new Node("node1", taxonomy.root);
        Node node2 = new Node("node2", taxonomy.root);

        Node node11 = new Node("node11", node1);
        Node node12 = new Node("node12", node1);
        Node node21 = new Node("node21", node2);
        Node node22 = new Node("node22", node2);

        Node node111 = new Node("data science", node11);
        Node node112 = new Node("machine learning", node11);
        Node node121 = new Node("java", node12);
        Node node122 = new Node("python", node12);
        Node node211 = new Node("power bi", node21);
        Node node212 = new Node("sql server", node21);
        Node node221 = new Node("visual basic", node22);
        Node node222 = new Node("c++", node22);

        taxonomy.addNode(node1, taxonomy.root);
        taxonomy.addNode(node2, taxonomy.root);

        taxonomy.addNode(node11, node1);
        taxonomy.addNode(node12, node1);
        taxonomy.addNode(node21, node2);
        taxonomy.addNode(node22, node2);

        taxonomy.addNode(node111, node11);
        taxonomy.addNode(node112, node11);
        taxonomy.addNode(node121, node12);
        taxonomy.addNode(node122, node12);

        taxonomy.addNode(node211, node21);
        taxonomy.addNode(node212, node21);
        taxonomy.addNode(node221, node22);
        taxonomy.addNode(node222, node22);

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
/*
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
*/


    public HashMap<String, ArrayList<String>> buildSentenceTags(String taggedSentence) throws NotLeafException, NotInTaxonomyException {

        String[] tokensVal = taggedSentence.split(" ");
        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();
        builtTags.put("skill", new ArrayList<String>());

        for(String token:tokensVal) {
            String[] keyVal = token.split("_");
            // change "this.isSkill(keyVal[0])" with taxonomy.isLeaf(keyVal[0])
            if(taxonomy.isLeaf(keyVal[0])){
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

    public HashMap<String, ArrayList<String>> buildSentenceSuperTags(String taggedSentence){

        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();
        builtTags.put("skill", new ArrayList<String>());

        taggedSentence = addSkillMap(taggedSentence, 3, builtTags);
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

    public double filtredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2) throws NotSameLengthAsTaxonomyDepthException, NotLeafException, NotInTaxonomyException {

        double totalSimilarity = 0;
        double idfSum = 0;
        double skillsSimilarity = 0;
        int lenghtSentence1 = 0;

        List<Double> weights = Arrays.asList(new Double[3]);
        weights.set(0,1.0);
        weights.set(1, 0.8);
        weights.set(2,0.6);


        for(String key:sentence1.keySet()){

            double partialSimilarity = 0;
            if(sentence2.containsKey(key)){
                if (key.equals("skill")){
                    ArrayList<String> listSentence1 = sentence1.get(key);
                    ArrayList<String> listSentence2 = sentence2.get(key);
                    for(String word1:listSentence1){
                        double maxSimilarityWord = 0;
                        for(String word2:listSentence2){
                            maxSimilarityWord = Math.max(maxSimilarityWord, taxonomy.nodesSimilarity(word1, word2, weights));
                        }
                        skillsSimilarity = maxSimilarityWord;
                    }
                    if(listSentence1.size() == 0){
                        skillsSimilarity = 0;
                    }else {
                        skillsSimilarity = skillsSimilarity / listSentence1.size();
                    }
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

        return totalSimilarity/idfSum + skillsSimilarity;
    }

    public double posFiltredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2) throws NotSameLengthAsTaxonomyDepthException, NotInTaxonomyException, NotLeafException {

        return  (this.filtredSimilarity(sentence1, sentence2) + this.filtredSimilarity(sentence2, sentence1))/2;

    }

}