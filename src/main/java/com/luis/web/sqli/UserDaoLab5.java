package com.luis.web.sqli;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.sql.DataSource;


@Stateless
public class UserDaoLab5 {
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    @Resource(lookup = "jdbc/bonarea")
    private DataSource dataSource;

    public UserDaoLab5() {       
    }
    
    @PostConstruct
    public void init() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
    }

    
    public void reset() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
            try (
                    Statement stmt = connection.createStatement();
                ) {       
                    String query = "drop table if exists USER";
                    stmt.executeUpdate(query);
                
                    query = "create table USER (id int not null auto_increment, username varchar(255) not null, password char(255) not null, name varchar(255), avatar varchar(255), isAdmin boolean, PRIMARY KEY(id), UNIQUE(username))";
                    stmt.executeUpdate(query);
                     
                    query = "insert into USER (username, password, name) values ('test', '" + passwordHash.generate("super".toCharArray()) + "', 'maria')";
                    stmt.executeUpdate(query);
                    
                    query = "insert into USER (username, password, name) values ('admin', '" + passwordHash.generate("supersecurepassword".toCharArray()) + "', 'pepe')";
                    stmt.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoLab3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoLab3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User register(String name, String username, String password) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                try (
                        Statement stmt = connection.createStatement();
                    ) {
                        String hash = passwordHash.generate(password.toCharArray());

                        String query = "insert into USER (name, username, password) values ('" + name + "','" + username + "','" + hash + "')";
                        int resul = stmt.executeUpdate(query);
                        
                        if (resul==1) return new User(name, username);
                        else return null;
                        
                } catch (SQLException ex) {
                    Logger.getLogger(UserDaoLab3.class.getName()).log(Level.SEVERE, null, ex);
                }
                connection.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDaoLab3.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public String hash(String data) {
        return passwordHash.generate(data.toCharArray());
    }

}
