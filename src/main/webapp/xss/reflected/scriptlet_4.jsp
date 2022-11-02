<%@page import="com.luis.xss.model.Post"%>
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
                <%
                    List<Post> posts = new ArrayList();
                    Post p1 = new Post();
                    Post p2 = new Post();
                    p1.setTitle("XSS en JSP");
                    p1.setAuthor("Luis Molina");
                    p1.setContent("Este es el <b>contenido</b>");
                    p2.setTitle("Vulnerabilidades web");
                    p2.setAuthor("Luis Molina");
                    p2.setContent("Este es el <b>contenido</b>");
                    posts.add(p1);
                    posts.add(p2);
                    String search = request.getParameter("search");
                    if (search == null) {
                        search = "";
                    }
                    /* Protección insegura. Se sustituye < y > por entidades html.
                    - Usar como payload onerror, onfocus, onclick, onmouseover, etc
                    - Ejemplo en la url: hola"+onfocus%3D"alert(1)"+autofocus+id="
                    */
                    search = search.replace("<", "&lt;");
                    search = search.replace(">", "&gt;");
                    search = search.replace("&", "&amp;");
                %>
                <h1>XSS scriptlet</h1>
                <p>Búsqueda:</p>
                <form method="GET">
                    <input type="text" name="search" value="<%= search %>">
                    <input type="submit" value="Buscar">
                </form>
                <%
                    for(Post post: posts) {
                        if (post.getTitle().contains(search)) {
                 %>    
                 <article>
                     <hr/>
                    <h2><%= post.getTitle() %></h2>
                    <p><%= post.getAuthor() %></p>
                    <div><%= post.getContent() %></div>
                 </article>
                 <%
                        }
                    }
                 %>
             </div>
        </div>
        <%@include file="../../footer.jsp" %>
    </body>
</html>