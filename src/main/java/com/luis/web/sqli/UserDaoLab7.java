package com.luis.web.sqli;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;


@Stateless
public class UserDaoLab7 {
    
    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @PersistenceContext(unitName="UserPU")
    EntityManager em;

    public UserDaoLab7() {
    }
    
    @PostConstruct
    public void init() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");
        passwordHash.initialize(parameters);
    }
    
    public User login(String username, String password) {
        //JPQL también es vulnerable a SQLi si se concatena al construir la sentencia.
        Query query = em.createQuery("SELECT new com.luis.model(u.name, u.username, u.password) FROM User u where u.username=:username");
        query.setParameter("username", username);
        User user = (User) query.getResultStream().findFirst().orElse(null);
                
        if (user != null && passwordHash.verify(password.toCharArray(), user.getPassword())) {
            return user;
        }
        return null;
    }   
}