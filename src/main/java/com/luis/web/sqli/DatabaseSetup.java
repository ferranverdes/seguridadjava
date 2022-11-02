package com.luis.web.sqli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.sql.DataSource;


@Singleton
@Startup
public class DatabaseSetup {

    @Resource(lookup = "jdbc/bonarea")
    private DataSource dataSource;

    @Inject
    private Pbkdf2PasswordHash passwordHash;


    @PostConstruct
    public void init() {

        Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, "Creando base de datos...");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);

        String pass = passwordHash.generate("super".toCharArray());
        if (passwordHash.verify("super".toCharArray(), pass)) {
        
            executeUpdate(dataSource, "create table USER (id int not null auto_increment, username varchar(255) not null, password char(255) not null, name varchar(255), avatar varchar(255), isAdmin boolean, PRIMARY KEY(id), UNIQUE(username))");

            executeUpdate(dataSource, "insert into USER (username, password, name) values ('test', '" + passwordHash.generate("super".toCharArray()) + "', 'maria')");
            executeUpdate(dataSource, "insert into USER (username, password, name) values ('lucia', '" + passwordHash.generate("lucia2000".toCharArray()) + "', 'Lucía López')");
            executeUpdate(dataSource, "insert into USER (username, password, name) values ('admin', '" + passwordHash.generate("superadmin".toCharArray()) + "', 'Usuario Administrador')");
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            executeUpdate(dataSource, "DROP TABLE IF EXISTS USER");
        } catch (Exception e) {
            Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, e.getMessage());

        }
    }

    private void executeUpdate(DataSource dataSource, String query) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            Logger.getLogger(DatabaseSetup.class.getName()).log(Level.SEVERE, null, e.getMessage());
        }
    }

}
