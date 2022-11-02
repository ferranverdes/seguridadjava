package com.luis.web.ssrf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@WebServlet(name = "SSRF5", urlPatterns = {"/SSRF5"})
public class SsrfApacheHttpClientSafe extends HttpServlet {
    //No se permiten redirecciones
    private final CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
    
    
    private boolean isPublicIp(String host) {
        InetAddress address;
        try {
            address = (InetAddress) InetAddress.getByName(host);
        } catch (UnknownHostException exception) {
            return false;
        }
        return !(address.isSiteLocalAddress() || 
                 address.isAnyLocalAddress()  || 
                 address.isLinkLocalAddress() || 
                 address.isLoopbackAddress() || 
                 address.isMulticastAddress());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = request.getParameter("url");
        try {
            if (url== null || url.equals("")) {
                request.setAttribute("response", "");
            } else {
                URL aurl;
                aurl = new URL(url);  
                String host = aurl.getHost();
                if (isPublicIp(host)){ 
                    HttpGet req = new HttpGet(url);
                    try (CloseableHttpResponse resp = httpClient.execute(req)) {
                        HttpEntity entity = resp.getEntity();
                        if (entity != null) {
                            String result = EntityUtils.toString(entity);
                            request.setAttribute("response", result);
                        } else {
                            request.setAttribute("response", "Error");
                        }
                    }
                } else {
                    request.setAttribute("response", "Host privado no permitido");
                }
            }
        } catch (HttpHostConnectException e) {
            request.setAttribute("response", "Conexión rehusada");
        } catch (MalformedURLException ex) {
                request.setAttribute("response", "URL mal formada.");
        }
        
        request.getRequestDispatcher("/ssrf/ssrf.jsp").forward(request, response);
    }
}