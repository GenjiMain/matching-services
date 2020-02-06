package fr.jobslake.similarity;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;


public class Pos {

    MaxentTagger tagger;
    Word2Vec vec;
    File gModel;

    public Pos() throws IOException {
        this.tagger = new MaxentTagger("C:\\Users\\mehdi\\Documents\\GitHub\\matching-services\\src\\models\\english-caseless-left3words-distsim.tagger");
        this.gModel = new File("C:\\Users\\mehdi\\Downloads\\glove.6B\\glove.6B.50d.txt");
        this.vec = (Word2Vec) WordVectorSerializer.loadTxtVectors(this.gModel);
    }

    public Pos(String taggerPath) {
        this.tagger = new MaxentTagger(taggerPath);
    }

    public static String cleanSentence(String sentence) {

        String cleanSentence = sentence.replaceAll("[^a-zA-Z0-9]", "");
        return cleanSentence;
    }

    public String tagSentence(String sentence) {

        String tagged = this.tagger.tagString(sentence);

        return tagged;
    }

    public static HashMap<String, ArrayList<String>> buildSentenceTags(String taggedSentence){

        String[] tokensVal = taggedSentence.split(" ");
        HashMap<String, ArrayList<String>> builtTags = new HashMap<String, ArrayList<String>>();

        for(String token:tokensVal) {
            String[] keyVal = token.split("_");
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

        return builtTags;
    }

    public double getSimilarity(String word1, String word2) {

        double cosSimilarity = this.vec.similarity(word1, word2);
        return cosSimilarity;
    }

    public double filtredSimilarity(HashMap<String, ArrayList<String>> sentence1,HashMap<String, ArrayList<String>>sentence2) {

        double totalSimilarity = 0;
        for(String key:sentence1.keySet()){

            double partialSimilarity = 0;
            if(sentence2.containsKey(key)){
                ArrayList<String> listSentence1 = sentence1.get(key);
                ArrayList<String> listSentence2 = sentence2.get(key);
                for(String word1:listSentence1){
                    double maxSimilarityWord = 0;
                    for(String word2:listSentence2){
                        double sim = this.getSimilarity(word1, word2);
                        if(!Double.isNaN(sim)){
                            maxSimilarityWord = Math.max(maxSimilarityWord, sim);
                        }
                    }
                    partialSimilarity += maxSimilarityWord;
                }
            }
            totalSimilarity += partialSimilarity;
        }
        return totalSimilarity;
    }
}