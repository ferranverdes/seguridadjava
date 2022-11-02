package com.luis.web.ssrf;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@WebServlet(name = "SSRF2", urlPatterns = {"/SSRF2"})
public class SsrfApacheHttpClient extends HttpServlet {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        
        /*
        No hay ningún tipo de filtrado. 
        Podemos escanear la red interna o acceder a sus servicios. 
        */
        if (url != null) {
            HttpGet req = new HttpGet(url);
            try (CloseableHttpResponse resp = httpClient.execute(req)) {

                // Get HttpResponse Status
                System.out.println(resp.getStatusLine().toString());

                HttpEntity entity = resp.getEntity();
                Header headers = entity.getContentType();
                
                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    request.setAttribute("response", result);
                } else {
                    request.setAttribute("response", "Error");
                }
            } catch (HttpHostConnectException e) {
                request.setAttribute("response", "Conexión rehusada");
            }            
        }
        request.getRequestDispatcher("/ssrf/ssrf.jsp").forward(request, response);
    }     
    
}