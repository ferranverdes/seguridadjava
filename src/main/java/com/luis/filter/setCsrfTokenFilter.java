package com.luis.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.security.SecureRandom;
import javax.servlet.annotation.WebFilter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebFilter("/csrftest")
public class setCsrfTokenFilter implements Filter{

    private final static SecureRandom random = new SecureRandom();
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update((""+random.nextLong()).getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest((""+random.nextLong()).getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            String token = sb.toString();
            httpRequest.getSession().setAttribute("csrf_token", token);
            chain.doFilter(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(setCsrfTokenFilter.class.getName()).log(Level.SEVERE, null, ex);
            chain.doFilter(request, response);
        }
    }
    
}
