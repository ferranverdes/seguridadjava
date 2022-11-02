package com.luis.web.ssrf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SSRF1", urlPatterns = {"/SSRF1"})
public class SsrfOpenConnection extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        if (url != null) {
            URLConnection httpClient =  new URL(url).openConnection();
            
        // Payload file:///etc/passwd            

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder resp = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                resp.append(line);
            }

            request.setAttribute("response", resp.toString());
               
            } catch (Exception e) {
                request.setAttribute("response", "Conexión rehusada");
                System.out.println(e.toString());
            }            
        }
        request.getRequestDispatcher("/ssrf/ssrf.jsp").forward(request, response);
    }   
    
}