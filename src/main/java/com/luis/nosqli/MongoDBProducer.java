package com.luis.nosqli;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Disposes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;


@ApplicationScoped
public class MongoDBProducer {
    
    @Produces
    public MongoClient createMongo() {
        String mongouser = getProperties("jndi/mongodb").getProperty("username");
        String mongopassword = getProperties("jndi/mongodb").getProperty("password");
        return MongoClients.create("mongodb://" + mongouser + ":" + mongopassword + "@mongo:27017/?maxPoolSize=20&w=majority");
    }
    
    @Produces
    public MongoDatabase createDB(MongoClient client) {
        return client.getDatabase("bonarea");
    }
    
    public void close(@Disposes MongoClient toClose) {
        toClose.close();
    }
    
    public Properties getProperties(String jndiName) {
        Properties properties;
        try {
            InitialContext context = new InitialContext();
            properties = (Properties) context.lookup(jndiName);
            context.close();
        } catch (NamingException e) {
            Logger.getLogger(MongoDBProducer.class.getName()).log(Level.SEVERE, null, "Error credenciales Mongodb");
            return null;
        }
        return properties;
    }
    
}