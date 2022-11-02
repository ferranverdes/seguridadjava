<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.luis.model.Post"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="../header.jsp" %>
    </head>
    <body>
        <div class="wrapper">
            <%@include file="../sidebar.jsp" %>
            <div id="content">
          
                <h1>Deserialización insegura</h1>
                <img src="http://localhost:8080/seguridadjava/InsecureDeserializationUser?fileName=<%= request.getAttribute("filename")%>"/>
                
                <form method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="inputTitle">Nombre</label>
                        <input type="text" name="name" class="form-control" id="inputTitle" aria-describedby="titleHelp" placeholder="Nombre">
                        <small id="nameHelp" class="form-text text-muted">Nombre</small>
                    </div>  
                    <div class="form-group">
                        <label for="avatar">Avatar</label>
                        <input id="avatar" type="file" name="avatar" class="form-control"/>     
                    </div>
                    <input type="submit" name="action" value="Guardar" class="btn btn-primary">
                    <input type="submit" name="action" value="Borrar" class="btn btn-secondary">
                </form>
             </div>
        </div>
        <%@include file="../footer.jsp" %>
    </body>
</html>