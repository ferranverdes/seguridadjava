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
        /*
            Payload: <><img src=xss onerror=alert(1)>
        */
        %>
        <div id="name"></div>
        <script>
            function escapeHTML(html) {
                //En javascript replace solo reemplaza la primera cadena encontrada.
                return html.replace('<', '&lt;').replace('>', '&gt;');
            }
            document.getElementById("name").innerHTML = escapeHTML("${param.name}");
        </script>

            </div>
        </div>
        <%@include file="../../footer.jsp" %>
    </body>
</html>



