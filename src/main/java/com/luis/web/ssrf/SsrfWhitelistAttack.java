package com.luis.web.ssrf;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


@WebServlet(name = "SSRFAttack", urlPatterns = {"/SSRFA"})
public class SsrfWhitelistAttack extends HttpServlet {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
            Este servidor se tiene que montar en una IP pública
            Por ejemplo con ngrok
            Respondemos con una redirección permanente 301
        */
        response.sendError(301, "http://localhost:8080/seguridadjava/secret");
    }
}