package com.luis.web.ssrf;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@WebServlet(name = "Secret", urlPatterns = {"/secret"})
public class SsrfApacheHttpClientLocalhost extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr.equals("127.0.0.1")) {
            response.getOutputStream().print("!Enhorabuena! has conseguido acceder desde localhost. Secreto 1934572 ");
        } else {
            response.getOutputStream().print("Lo siento no puedes pasar desde " + remoteAddr);
        }
    }     
    
}