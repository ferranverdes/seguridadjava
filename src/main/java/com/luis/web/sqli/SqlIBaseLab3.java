package com.luis.web.sqli;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SQLI3", urlPatterns = {"/SQLI3"})
public class SqlIBaseLab3 extends HttpServlet {
    
    @Inject
    UserDaoLab3 dao;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/sqli/sqli.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        User user = null;
        
        if (action.equals("Reset")) {
            dao.reset();
        }
        
        if (action.equals("Login")) {
            user = dao.login(username, password);
        }
        
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/sqli/sqli.jsp").forward(request, response);
    }
    
    
}