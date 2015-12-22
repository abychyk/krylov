package com.mkyong.core;

import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Created by shedimon on 22.12.2015.
 */
public class AddBdObj {
    public static void main(String[] args) {

        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("pizza");

            String[] s1 ={"name","type","price","taste"};
            String[] s2={"Trapparoni","Huizza","1488","awful"};

            DBCollection collection = db.getCollection("product");
            addNewDoc(s1,s2,collection);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public static void addNewDoc(String[] field, String[] args, DBCollection c){

        BasicDBObject document = new BasicDBObject();
        for (int i=1; i<field.length; i++) {
            document.put(field[i], args[i]);
        }
        c.insert(document);
    }
    public static void saveNewDoc(String[] field, String[] args, String id, DBCollection c) {
        
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        DBObject dbObj = c.findOne(query);
        if (dbObj.equals(null))
            addNewDoc(String[] field, String[] args, DBCollection c);
        else {
            BasicDBObject document = new BasicDBObject();
            for (int i=1; i<field.length; i++) {
                document.put(field[i], args[i]);
            }
            query = new BasicDBObject().append("_id", id);
            c.update(query, document);
        }
    }
}
