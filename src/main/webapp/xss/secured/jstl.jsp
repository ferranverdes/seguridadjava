<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                <h1>JSTL es seguro frente a XSS</h1>
                <form method="POST">
                    <input type="text" name="name"/>
                    <input type="submit" value="Enviar"/>
                </form>
                <p>No vulnerable: <c:out value="${param.name}"/></p>
                <p>No vulnerable: ${fn:escapeXml(param.name)}</p>
                <p>Los navegadores actuales no son vulnerables a javascript:alert(1) en img:<img src="${fn:escapeXml(param.name)}"/></p>
                <p>Cuidado con: <script type="text/javascript" src="<c:out value="${param.name}"/>"></script></p>
                <p>Cuidado con: <a href="<c:out value="${param.name}"/>">Url</a></p>
             </div>
        </div>
        <%@include file="../../footer.jsp" %>
    </body>
</html>