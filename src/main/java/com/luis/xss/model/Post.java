package com.luis.xss.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(name="Post.findAll", query="SELECT p FROM Post p ORDER BY p.title"),
    @NamedQuery(name="Post.deleteAll", query="DELETE FROM Post")
})
public class Post implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    private long postId;
    
    @Column(nullable = false)
    private String title;
    private String author;
    private String content;
    private String url;

    public Post() {
        
    }
    
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    } 
    
    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }
    
}
