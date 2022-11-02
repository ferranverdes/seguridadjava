package com.luis.xss.servlet;

import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.luis.xss.service.PostService;
import com.luis.xss.model.Post;


@WebServlet("/xss/persistent")
public class PostServlet extends HttpServlet {

    @Inject
    PostService postService;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Post> posts = postService.getAllPosts();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/xss/persisted/persisted.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action!=null && action.equals("Eliminar")) {
            String id = request.getParameter("id");
            if (id != null) {
                long postId = Long.parseLong(id);
                Post post = postService.findById(postId);
                postService.deletePost(post);
            }
        }
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action.equals("Guardar")) {
            Post post = new Post();
            if (request.getParameter("title") == null) {
                request.setAttribute("error", "Título obligatorio");
                processRequest(request, response);
            }

            post.setTitle(request.getParameter("title"));

            if (request.getParameter("author")!= null) {
                post.setAuthor(request.getParameter("author"));    
            }
            if (request.getParameter("content") != null) {
                post.setContent(request.getParameter("content"));
            }
            if (request.getParameter("url") != null) {
                post.setUrl(request.getParameter("url"));
            }
            postService.insertPost(post);    
        }
        
        if (action.equals("Borrar")) {
            postService.deleteAll();
        }
                
        processRequest(request, response);
    }

}
