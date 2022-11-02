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
                <h1>DOM XSS</h1>
                <p>Si incrustas una etiqueta script con innerHTML no se va a ejecutar, pero puedes incluir un payload:</p>
                <p>&lt;img src="noexiste.jpg" onerror="alert(1)"&gt;</p>
                <form method="GET">
                    <select id="lang" name="lang">
                        <option value="" selected>Seleccionar idioma</option>
                        <%
                        String[][] languages = {{"en", "Inglés"},{"es", "Español"}, {"fr", "Francés"}, {"cat", "Catalán"}};
                            
                        for(int i = 0; i < languages.length; i++) {
                            out.print("<option ");
                            if (languages[i][0].equals(request.getParameter("lang"))) {
                                out.print("selected=\"selected\"");
                            }
                            out.println(" value=\""+languages[i][0]+"\">"+ languages[i][1] + "</option>");
                        }
                        %>
                    </select>
                    <input type="submit" value="Cambiar"/>
                </form>
                <div id="language">
                    No seleccionado.
                </div>
            </div>
        </div>
        <%@include file="../../footer.jsp" %>
         <script>
            var lang = (new URLSearchParams(window.location.search)).get('lang');
            if (lang) {
                document.getElementById("language").innerHTML = "Idioma seleccionado: " + lang;
            }
        </script>

    </body>
</html>



