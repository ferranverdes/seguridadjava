package com.luis.xss.model;

import java.util.List;

public interface PostDao {

    public List<Post> getAllPosts();
    
    public Post findPostById(long id);
    
    public void insertPost(Post post);
    
    public void updatePost(Post post);
    
    public void deletePost(Post post);
    
    public void deleteAll();
    
}
