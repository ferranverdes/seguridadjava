<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.luis.xss.model.Post"%>
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
          
                <h1>XSS persisted</h1>
                <p>El payload se almacena en la base de datos. Si hay XSS todos los usuarios ejecutarán el payload.</p>
                <p>Prueba distintos payloads</p>
                <ol>
                    <li> &lt;script&gt;alert(1)&lt;/script&gt;</li>
                    <li> &lt;img src="noexiste.jpg" onerror="alert(1)"&gt;
                    <li> En el enlace url autor: javascript:alert(1) </li>    
                </ol>
                <form method="POST" action="/seguridadjava/csrftestaction">
                    <div class="form-group">
                        <label for="inputTitle">Título</label>
                        <input type="text" name="title" class="form-control" id="inputTitle" aria-describedby="titleHelp" placeholder="Título">
                        <small id="titleHelp" class="form-text text-muted">Título del Post.</small>
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="invalid-feedback">
                                Título obligatorio.
                            </div>
                        <% }%>
                    </div>  
                    <div class="form-group">
                        <label for="inputAuthor">Autor</label>
                        <input type="text" name="author" class="form-control" id="inputAuthor" aria-describedby="authorHelp" placeholder="Author">
                        <small id="authorHelp" class="form-text text-muted">Autor del Post.</small>
                    </div>
                    <div class="form-group">
                        <label for="inputContent">Content</label>
                        <textarea name="content" class="form-control" id="inputContent" aria-describedby="contentHelp" placeholder="Content"></textarea>
                        <small id="contentHelp" class="form-text text-muted">Contenido del Post.</small>
                    </div>
                     <div class="form-group">
                        <label for="inputContent">Author Url</label>
                        <input name="url" class="form-control" id="inputUrl" aria-describedby="urlHelp" placeholder="URL del autor"></textarea>
                        <small id="urlHelp" class="form-text text-muted">URL del autor.</small>
                    </div>
                    <input type="hidden" name="csrf_token" value="<%= session.getAttribute("csrf_token")%>"/>
                    <input type="submit" name="action" value="Guardar" class="btn btn-primary">
                    <input type="submit" name="action" value="Borrar" class="btn btn-secondary"/>
                </form>
                <%
                    for(Post post: (List<Post>)request.getAttribute("posts")) {
                %>    
                 <article>
                    <h2><%= post.getTitle() %></h2>
                    <p><%= post.getAuthor() %></p>
                    <div><%= post.getContent() %></div>
                    <a href="<c:out value="<%= post.getUrl()%>"/>">URL del autor insegura</a>
                    <a class="btn btn-primary" href="/seguridadjava/csrftestaction?action=Eliminar&id=<%= post.getPostId() %>">Borrar post</a>
                 </article>
                 <%
                    }
                 %>
             </div>
        </div>
        <%@include file="../footer.jsp" %>
    </body>
</html>