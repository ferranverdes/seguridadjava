package com.luis.xss.service;

import java.util.List;
import javax.ejb.Local;
import com.luis.xss.model.Post;

@Local
public interface PostService {
    public List<Post> getAllPosts();
    public Post findById(long id);
    public void insertPost(Post post);
    public void updatePost(Post post);
    public void deletePost(Post post);
    public void deleteAll();
}
