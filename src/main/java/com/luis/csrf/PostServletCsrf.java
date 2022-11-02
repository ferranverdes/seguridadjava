package com.luis.csrf;

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


@WebServlet("/csrftest")
public class PostServletCsrf extends HttpServlet {

    @Inject
    PostService postService;
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Post> posts = postService.getAllPosts();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/csrf/csrf.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
