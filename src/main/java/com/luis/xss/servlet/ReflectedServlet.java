package com.luis.xss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReflectedServlet", urlPatterns = {"/xss/reflected/reflected_servlet"})
public class ReflectedServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Cookie[] cookies = request.getCookies();
        String color = request.getParameter("color");
        
        if (color != null) {
            //Set new background color
            Cookie cookie = new Cookie("background", color);
            response.addCookie(cookie);
        } else {
            //Get cookie background color
            for (Cookie c: cookies) {
                if (c.getName().equals("background")) {
                    color = c.getValue();
                }
            }
        }
        
        if (color == null) {
            //default background color
            color = "blue";
            Cookie cookie = new Cookie("background", color);
            response.addCookie(cookie);
        }
        
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReflectedServlet</title>");
            //Color reflejado en la hoja de estilos
            out.println("<style>body {background-color: "+ color +"</style>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReflectedServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
