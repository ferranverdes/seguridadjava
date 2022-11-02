<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="../../header.jsp" %>
    </head>
    <body>
        <div class="wrapper">
            <%@include file="../../sidebar.jsp" %>
            <div id="content">
                <form method="POST">
                    <div class="form-group">
                        <label for="username">Usuario</label>
                        <input type="text" name="username" class="form-control" id="username"/>
                    </div>
                    <div class="form-group">
                        <label for="password">Constraseña</label>
                        <input type="password" name="password" class="form-control" id="password"/>
                    </div>
                    <input type="submit" name="action" value="Login" class="btn btn-primary">            
                </form>
            </div>
        </div>
        <%@include file="../../footer.jsp" %>
    </body>
</html>
