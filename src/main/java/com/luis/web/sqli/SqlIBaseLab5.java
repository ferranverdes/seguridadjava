package com.luis.web.sqli;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SQLI5", urlPatterns = {"/SQLI5"})
public class SqlIBaseLab5 extends HttpServlet {
    
    @Inject
    UserDaoLab5 dao;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/sqli/sqliInsert.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        User user = null;
        
        if (action.equals("Reset")) {
            dao.reset();
        }
        
        if (action.equals("Register")) {
            user = dao.register(name, username, password);
        }
        
        if (action.equals("Hash")) {
            String hash = dao.hash(name);
            request.setAttribute("hash", hash);
        }
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/sqli/sqliInsert.jsp").forward(request, response);
    }
    
    
}