/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

import org.json.simple.JSONObject;

/**
 *
 * @author bruk
 */
public class Database {

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("user");
        Document doc = new Document("user_name", "admin")
                .append("pwd_hash", Hash.getSHA256("admin"));

        MongoCollection<Document> collection = database.getCollection("users");
        collection.insertOne(doc);

        doc = new Document("user_name", "user")
                .append("pwd_hash", Hash.getSHA256("user"));
        collection.insertOne(doc);

        mongoClient.close();
    }

    public static boolean verifyUser(String username, char[] password, String dbName, String collName) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase(dbName);

        MongoCollection<Document> collection = database.getCollection(collName);
        
        String pwdhash = Hash.getSHA256(String.valueOf(password));
        System.out.println("The hash of the password: " + pwdhash);
        List<Document> docs = collection.find(eq("user_name",username)).into(new ArrayList<>());
         int value = 1;
         System.out.println(docs);
         for (Document doc : docs) {
                
                value = pwdhash.compareTo(doc.getString("pwd_hash"));
                if (value == 0){
                    System.out.println("User exists");
                    return true;
                }
         }
         if(value==1)
              System.out.println("No user inside");   
         mongoClient.close();  
         return false;
    }
}