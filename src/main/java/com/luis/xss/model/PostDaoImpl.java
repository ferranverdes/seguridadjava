package com.luis.xss.model;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PostDaoImpl implements PostDao{
    
    @PersistenceContext(unitName="PostPU")
    EntityManager em;

    @Override
    public List<Post> getAllPosts() {
        return em.createNamedQuery("Post.findAll").getResultList();
    }

    @Override
    public Post findPostById(long id) {
        return em.find(Post.class, id);
    }

    @Override
    public void insertPost(Post post) {
        em.persist(post);
    }

    @Override
    public void updatePost(Post post) {
        em.merge(post);
    }

    @Override
    public void deletePost(Post post) {
        em.remove(em.merge(post));
    }

    @Override
    public void deleteAll() {
        em.createNamedQuery("Post.deleteAll").executeUpdate();
    }
}
