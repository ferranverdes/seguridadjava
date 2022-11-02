package com.luis.web.sqli;


import java.sql.Connection;
import java.sql.ResultSet;
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
public class UserDaoLab3 {
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;
    
    @Resource(lookup = "jdbc/bonarea")
    private DataSource dataSource;
    
    public UserDaoLab3() {
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
   
    
    public User login(String username, String password) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                try (
                        Statement stmt = connection.createStatement();
                    ) {
                        String query = "select * from USER where username='" + username + "'";
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next() && passwordHash.verify(password.toCharArray(), rs.getString("password"))) {
                            User user = new User();
                            user.setUsername(rs.getString("username"));
                            user.setName(rs.getString("name"));
                            user.setAvatar(rs.getString("avatar"));
                            user.setIsAdmin(rs.getBoolean("isAdmin"));
                            return user;
                        }

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
    
    public User register(String username, String password, String name) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                try (
                        Statement stmt = connection.createStatement();
                    ) {
                        String hash = passwordHash.generate(password.toCharArray());
                        String query = "insert into USER (username, password, name) values ('" + username + "','" + hash + "','" + name + "')";
                        ResultSet rs = stmt.executeQuery(query);
                        

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
    
}
