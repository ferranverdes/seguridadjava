package com.luis.csrf;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.luis.xss.service.PostService;
import com.luis.xss.model.Post;


@WebServlet("/csrftestaction")
public class PostServletCsrfAction extends HttpServlet {

    @Inject
    PostService postService;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        Post post;
        switch (action) {
            case "Guardar":
                post = new Post();
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
                break;
            case "Borrar":
                postService.deleteAll();
                break;
            case "Eliminar":
                String id = request.getParameter("id");
                if (id != null) {
                    long postId = Long.parseLong(id);
                    post = postService.findById(postId);
                    postService.deletePost(post);
                }
                break;
            default:
                throw new AssertionError();
        }
        response.sendRedirect("/seguridadjava/csrftest");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        processRequest(request, response);
    }

}
