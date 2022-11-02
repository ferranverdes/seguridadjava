package com.luis.web.sqli;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.codec.digest.DigestUtils;

@ApplicationScoped
public class UserDaoLab1 {
       
    static class PasswordHash {
        //Nunca usar md5 para almacenar hash de contraseñas.
        //sha512 tampoco es recomendable
        //bcrypt, pbkdf2 y scrypt son algoritmos que hacen hash de forma iterativa retardando el ataque de fuerza bruta
        //https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
        static public String generate(String password) {        
            return DigestUtils.md5Hex(password).toUpperCase();
        }

        static public boolean verify(String hash, String password) {
            return DigestUtils.md5Hex(password).toUpperCase().equals(hash);
        }
    }

    
    private Connection connection = null;
    
    public void connect() {
        //Mala práctica JPA mezclado con JDBC. Credenciales en el código!!!!
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Si ejecutas en local crea una entrada en hosts db 127.0.0.1
            connection = DriverManager.getConnection("jdbc:mysql://db:3306/bonarea?useSSL=false", "user", "password");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDaoLab1.class.getName()).log(Level.SEVERE, null, ex);   
        }
    }
    
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoLab1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
    
    public void reset() {
        if (connection != null) {
            try (
                    Statement stmt = connection.createStatement();
                ) {
                
                    String query = "drop table if exists USER";
                    stmt.executeUpdate(query);
                
                    query = "create table USER (id int not null auto_increment, username varchar(255) not null, password char(255) not null, name varchar(255), avatar varchar(255), isAdmin boolean, PRIMARY KEY(id), UNIQUE(username))";
                    stmt.executeUpdate(query);
                     
                    query = "insert into USER (username, password, name) values ('test', '" + PasswordHash.generate("super") + "', 'maria')";
                    stmt.executeUpdate(query);
                    
                    query = "insert into USER (username, password, name) values ('admin', '" + PasswordHash.generate("supersecurepassword") + "', 'pepe')";
                    stmt.executeUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(UserDaoLab1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
    
    public User login(String username, String password) {
        if (connection != null) {
            try (
                    Statement stmt = connection.createStatement();
                ) {
                    String hash = PasswordHash.generate(password);
                    String query = "select * from USER where password='"+ hash +"' and username='" + username + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setName(rs.getString("name"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setIsAdmin(rs.getBoolean("isAdmin"));
                        return user;
                    }

            } catch (SQLException ex) {
                Logger.getLogger(UserDaoLab1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    public String filterSQLi(String input) {
        input = input.replace("\"", "\\\"");
        input = input.replace("'", "\\'");
        return input;
    }
    
    public User loginFilter(String username, String password) {
        if (connection != null) {
            try (
                    Statement stmt = connection.createStatement();
                ) {
                    username = filterSQLi(username);
                    String hash = UserDaoLab1.PasswordHash.generate(password);
                    String query = "select * from USER where password='"+ hash +"' and username='" + username + "'";
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        User user = new User();
                        user.setUsername(rs.getString("username"));
                        user.setName(rs.getString("name"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setIsAdmin(rs.getBoolean("isAdmin"));
                        return user;
                    }

            } catch (SQLException ex) {
                Logger.getLogger(UserDaoLab1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}


