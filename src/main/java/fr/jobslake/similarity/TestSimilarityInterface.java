package fr.jobslake.similarity;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestSimilarityInterface {
    String similarityName;
    Object similarityInstance;
    Method similarityMethod;
    Method cleanSentenceMethod;
    Class[] paramSimilarity;

    // constructor of the similarity object
    // taking the name of the similarity as argument
    public TestSimilarityInterface(String similarityName) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException {

        this.similarityName = similarityName;

        // Get the class of the similarity and instanciate the object
        Class similarityClass = Class.forName("fr.jobslake.similarity."+this.similarityName);
        this.similarityInstance = similarityClass.newInstance();

        // Get the cleanSentence Method
        this.cleanSentenceMethod = similarityClass.getMethod("cleanSentence", String.class);

        // Get the scoreSimilarity method
        this.paramSimilarity = new Class[2];
        paramSimilarity[0] = String.class;
        paramSimilarity[1] = String.class;
        this.similarityMethod = similarityClass.getMethod("ScoreSimilarity", paramSimilarity);
    }

    // Function to test the similarity function
    // Connect to MongoDB database
    // Name of database : datatestDB
    // Collection of the testing sentences : experienceDesc
    // Each element of the experience contains : id experience, sentence
    // Collection to save the result : resultSimilarity
    // The result collection contains : id first sentence, id second sentence, name similarity, score, date test

    private static void similarityJob (String similarityName) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger rootLoggerMongo = loggerContext.getLogger("org.mongodb.driver");
        rootLoggerMongo.setLevel(Level.OFF);
        Logger rootLoggerDeep = loggerContext.getLogger("org.deeplearning4j.models");
        rootLoggerDeep.setLevel(Level.OFF);


        int batchRequestSize = 1; // the batch size to put results in the dataset
        int batchRequest = 0; // counter

        // Connecting to MongoDB
        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");;
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("datatestDB");;
        MongoCollection<Document> experienceDesc = database.getCollection("experienceDesc");
        MongoCollection<Document> resultSimilarity = database.getCollection("resultSimilarity");
        MongoCursor<Document> cursor = experienceDesc.find().iterator();
        BasicDBObject query = new BasicDBObject();
        List<Document> documents = new ArrayList<Document>();


        System.out.println("Starting the Jobs ;)");
        TestSimilarityInterface testSimilarityInterface = new TestSimilarityInterface(similarityName);

        // Starting the tests
        while(cursor.hasNext()){
            Document firstElement = cursor.next();
            int firstElementId = (int) firstElement.get("id");
            String firstElementExpDesc = (String) firstElement.get("experience");

            String cleanedSentence1 = (String) testSimilarityInterface.cleanSentenceMethod.invoke(testSimilarityInterface.similarityInstance, firstElementExpDesc);

            query.put("id", new BasicDBObject("$gt", firstElementId));
            MongoCursor<Document> secondeElementCursor = experienceDesc.find(query).iterator();

            while (secondeElementCursor.hasNext()){
                Document secondElement = secondeElementCursor.next();
                int secondElementId = (int) secondElement.get("id");
                String secondElementExpDesc = (String) secondElement.get("experience");
                String cleanedSentence2 = (String) testSimilarityInterface.cleanSentenceMethod.invoke(testSimilarityInterface.similarityInstance, secondElementExpDesc);;

                double scoreSimilarity = (double) testSimilarityInterface.similarityMethod.invoke(testSimilarityInterface.similarityInstance, cleanedSentence1,cleanedSentence2);

                Document doc = new Document("idSentence1", firstElementId)
                        .append("idSentence2", secondElementId)
                        .append("typeSimilarity", similarityName)
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


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        similarityJob("Pos");
    }
}



