package com.luis.xss.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.luis.xss.model.Post;
import com.luis.xss.model.PostDao;

@Stateless
public class PostServiceImpl implements PostService{

    @Inject
    private PostDao postDao;
    
    @Override
    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    @Override
    public Post findById(long id) {
        return postDao.findPostById(id);
    }

    @Override
    public void insertPost(Post post) {
        postDao.insertPost(post);
    }

    @Override
    public void updatePost(Post post) {
        postDao.updatePost(post);
    }

    @Override
    public void deletePost(Post post) {
        postDao.deletePost(post);
    }

    @Override
    public void deleteAll() {
        postDao.deleteAll();
    }
    
}
