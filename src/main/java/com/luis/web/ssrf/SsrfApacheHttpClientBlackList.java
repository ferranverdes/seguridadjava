package com.luis.web.ssrf;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@WebServlet(name = "SSRF", urlPatterns = {"/SSRF3"})
public class SsrfApacheHttpClientBlackList extends HttpServlet {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final String[] blacklist = new String[] {"localhost","127.0.0.1","0.0.0.0","127.1","0"}; 
    
    private boolean blacklisted(String url) {
        try {
            URL aurl = new URL(url);
            String host = aurl.getHost();
            return Arrays.asList(blacklist).contains(host);
        } catch (MalformedURLException ex) {
            return true;
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        
        /* 
        IPv6 bypass
            http://[0000::1]:8080/seguridadjava/secret
            http://[::]:8080/seguridadjava/secret
        CDIR bypass
            http://127.127.127.127:8080/seguridadjava/secret
            http://127.0.1.3:8080/seguridadjava/secret
        Decimal bypass
            http://2130706433:8080/seguridadjava/secret
        DNS to localhost
            http://localtest.me:8080/seguridadjava/secret
            http://spoofed.burpcollaborator.net:8080/seguridadjava/secret
        
        */
        if (url== null || url.equals("")) {
            request.setAttribute("response", "");
        } else if (blacklisted(url)) {
            request.setAttribute("response", "URL blacklisted");
        } else {
            HttpGet req = new HttpGet(url);
            try (CloseableHttpResponse resp = httpClient.execute(req)) {
                // Get HttpResponse Status
                HttpEntity entity = resp.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    request.setAttribute("response", result);
                } else {
                    request.setAttribute("response", "Error");
                }
            } catch (HttpHostConnectException e) {
                request.setAttribute("response", "Conexión rehusada");
                System.out.println(e.toString());
            }            
        }
        request.getRequestDispatcher("/ssrf/ssrf.jsp").forward(request, response);
    }     
    
}