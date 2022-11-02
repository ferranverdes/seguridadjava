package com.luis.nosqli;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;

@Stateless
public class UserService {
    
    @Inject
    MongoDatabase mongoDB;
    
    public List<User> list(){
        List<User> list = new ArrayList<>();

        Bson filter =  Filters.empty();
        mongoDB.getCollection("users").find(filter).forEach(doc -> list.add(new User(doc.getString("name"), doc.getString("username"))));

        return list;
    }
    
    public User vulnLogin(String username, String password) {
        BasicDBObject query = BasicDBObject.parse("{\"username\":\""+username+"\", \"password\":\""+password+"\"}");
        Document doc = mongoDB.getCollection("users").find(query).first();        
        if (doc != null)
            return new User(doc.getString("name"), doc.getString("username"));
        else
            return null;
    }
    
    public User login(String username, String password) {       
        List<Bson> filters = new ArrayList();
        filters.add(Filters.eq("username", username));
        filters.add(Filters.eq("password", password));
        Document doc = mongoDB.getCollection("users").find(Filters.and(filters)).first();
        if (doc != null)
            return new User(doc.getString("name"), doc.getString("username"));
        else
            return null;
    }
    
    
}
