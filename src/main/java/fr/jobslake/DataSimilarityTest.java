package fr.jobslake;
/*
import ch.qos.logback.classic.Logger;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import fr.jobslake.similarity.Pos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;
import org.bson.Document;
import org.slf4j.LoggerFactory;

public class DataSimilarityTest {

    public static void main(String[] args) throws IOException, NotLeafException, NotInTaxonomyException, NotSameLengthAsTaxonomyDepthException {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLoggerMongo = loggerContext.getLogger("org.mongodb.driver");
        rootLoggerMongo.setLevel(Level.OFF);
        Logger rootLoggerDeep = loggerContext.getLogger("org.deeplearning4j.models");
        rootLoggerDeep.setLevel(Level.OFF);

        int batchRequestSize = 1;
        int batchRequest = 0;

        Pos posSimilarityObject = new Pos();
        System.out.println("pos object instanciated");

        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");;
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("datatestDB");;
        MongoCollection<Document> experienceDesc = database.getCollection("experienceDesc");
        MongoCollection<Document> resultSimilarity = database.getCollection("resultSimilarity");
        MongoCursor<Document> cursor = experienceDesc.find().iterator();
        BasicDBObject query = new BasicDBObject();

        List<Document> documents = new ArrayList<Document>();

        System.out.println("Starting the Jobs ;)");
        while(cursor.hasNext()){
            Document firstElement = cursor.next();
            int firstElementId = (int) firstElement.get("id");
            String firstElementExpDesc = (String) firstElement.get("experience");
            String cleanedSentence1 = posSimilarityObject.cleanSentence(firstElementExpDesc);
            String taggedSentence1 = posSimilarityObject.tagSentence(cleanedSentence1);
            HashMap<String, ArrayList<String>> builtSenetence1Tags = posSimilarityObject.buildSentenceTags(taggedSentence1);

            query.put("id", new BasicDBObject("$gt", firstElementId));
            MongoCursor<Document> secondeElementCursor = experienceDesc.find(query).iterator();

            while (secondeElementCursor.hasNext()){
                Document secondElement = secondeElementCursor.next();
                int secondElementId = (int) secondElement.get("id");
                String secondElementExpDesc = (String) secondElement.get("experience");
                String cleanedSentence2 = posSimilarityObject.cleanSentence(secondElementExpDesc);
                String taggedSentence2 = posSimilarityObject.tagSentence(cleanedSentence2);
                HashMap<String, ArrayList<String>> builtSenetence2Tags = posSimilarityObject.buildSentenceTags(taggedSentence2);

                double scoreSimilarity = posSimilarityObject.ScoreSimilarity(builtSenetence1Tags, builtSenetence2Tags);

                Document doc = new Document("idSentence1", firstElementId)
                        .append("idSentence2", secondElementId)
                        .append("typeSimilarity", "PosSimilarity")
                        .append("scoreSimilarity", scoreSimilarity)
                        .append("timeDate", java.time.LocalDate.now());

                documents.add(doc);
                if(batchRequest == batchRequestSize){
                    resultSimilarity.insertMany(documents);
                    documents.clear();
                    batchRequest = 0;
                    System.out.println("Batch added");
                }else{
                    batchRequest ++;
                }
            }
        }

        if(documents.size() != 0){
            resultSimilarity.insertMany(documents);
        }
        System.out.println("Calcules Done");
    }

}
*/