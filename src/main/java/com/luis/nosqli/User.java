package com.luis.nosqli;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String username = "guest";
    private String password;
    private String name = "guest";
    private String avatar = "guest.jpg";
    private Boolean isAdmin;

    public User() {
    }
    
    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }
    
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + '}';
    }

}
