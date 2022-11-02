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
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

@WebServlet(name = "SSRF4", urlPatterns = {"/SSRF4"})
public class SsrfApacheHttpClientWhitelist extends HttpServlet {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    
    
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
        
        /*
            En este caso solo se permite el acceso a IPs o host que se resuelven a una IP pública.
            Podemos saltarnos (bypass) la protección montando un servidor con IP pública que haga
            una redirección 301 a una ip o host privados.
        */
        
        try {
            if (url== null || url.equals("")) {
                request.setAttribute("response", "");
            } else {
                URL aurl;
                aurl = new URL(url);  
                String host = aurl.getHost();
                if (isPublicIp(host)){
                    HttpGet req = new HttpGet(url);
                    /*
                    Por defecto si el servidor responde con código 30X de redirección
                    httpClient volverá a hacer otra petición a la nueva dirección
                    saltando el filtro anterior.
                    */
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