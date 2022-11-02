package com.luis.web;

import com.luis.xss.model.Post;
import com.luis.xss.service.PostService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("postBean")
@RequestScoped
public class PostBean {
    
    @Inject
    private PostService postService;
    
    private List<Post> posts;
    
    private Post newPost;
    
    public PostBean() {
    }
    
    @PostConstruct
    public void inicializar(){
        this.posts = postService.getAllPosts();
        this.newPost = new Post();
    }

    public List<Post> getPosts() {
        return posts;
    }
    
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public void setNewPost(Post newPost) {
        this.newPost = newPost;
    }

    public Post getNewPost() {
        return newPost;
    }
    
    public void insertPost() {
        postService.insertPost(newPost);
        posts.add(newPost);
        newPost = null;
    }
    
    public void deletePost(Post post) {
        postService.deletePost(post);
        posts.remove(post);
    }
    
}