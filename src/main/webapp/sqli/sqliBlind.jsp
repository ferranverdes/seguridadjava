<%@page import="com.luis.web.sqli.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.luis.model.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SQL Injection</title>
        <%@include file="../header.jsp" %>
    </head>
    <body>
        <div class="wrapper">
            <%@include file="../sidebar.jsp" %>
            <div id="content">
                <h1>SQLi Login</h1>
                <form method="POST">
                    <div class="form-group">
                        <label for="username">Usuario</label>
                        <input type="text" name="username" class="form-control" id="username">
                        <label for="password">Contraseña</label>
                        <input type="password" name="password" class="form-control" id="password">
                    </div>  
                    <p>Usuario de prueba: test:super</p>
                    <input type="submit" name="action" value="Login" class="btn btn-primary">
                    <input type="submit" name="action" value="Reset" class="btn btn-primary">
                </form>    
                <div class="row">
                        <%
                        if (request.getMethod().equals("POST")) {
                            User user = (User) request.getAttribute("user");
                            if ( user != null ) {
                             out.print("<p> Bienvenido al sistema</p>");
                            } else {
                             out.print("Usuario o contraseña incorrecta");
                            }
                        }
                        %>
                </div>
             </div>
        </div>
        <%@include file="../footer.jsp" %>
    </body>
</html>