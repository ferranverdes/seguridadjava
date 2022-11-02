package com.luis.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/csrftestaction")
public class verifyCsrfTokenFilter implements Filter{

    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        
        //Vulnerable, solo verifica si el método es POST. Si cambiamos a GET la acción se ejecuta.
        if (!httpReq.getMethod().equalsIgnoreCase("POST")) {
            chain.doFilter(request, response);
        } else {
            String csrfRequestToken = httpReq.getParameter("csrf_token");
            String csrfToken = (String) httpReq.getSession().getAttribute("csrf_token");

            if (csrfRequestToken == null || !csrfRequestToken.equals(csrfToken)) {
                    httpRes.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }			
            else {
                    chain.doFilter(request, response);
            }
        }
    }
    
}
