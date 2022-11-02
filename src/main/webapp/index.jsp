<%-- 
    Document   : index
    Created on : 16 jul 2022, 14:47:27
    Author     : Luis Molina Garzón
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Laboratorio Java EE 8</title>
        <%@include file="header.jsp" %>
    </head>
    <body>
        <div class="wrapper">
            <%@include file="sidebar.jsp" %>
            <div id="content">
                <h1>Laboratorio Vulnerabilidades Java EE 8</h1>
                <h2>Formación Hispasec: BonÀrea</h2>
                <p>Atención, esta página web es vulnerable por diseño.</p>
                <p>Autor: Luis Molina Garzón</p>
                <p>Fecha: 1 de Octubre de 2022</p>
                <ol>
                    <li>Cross Site Scripting - XSS</li>
                    <li>Cross Site Request Forgery - CSRF</li>
                    <li>Insecure Deserialization</li>
                    <li>Server Side Request Forgery - SSRF</li>
                    <li>SQL and NoSQL Injection - SQLi</li>
                </ol>
            </div>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
