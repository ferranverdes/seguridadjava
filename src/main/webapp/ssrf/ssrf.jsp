<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
          
                <h1>SSRF Proxy</h1>
                <form method="GET">
                    <div class="form-group">
                        <label for="url">URL</label>
                        <input type="text" name="url" class="form-control" id="url" placeholder="url">
                    </div>  
                    <input type="submit" name="action" value="visit" class="btn btn-primary">
                </form>
                <%= request.getAttribute("response") %>    
             </div>
        </div>
        <%@include file="../footer.jsp" %>
    </body>
</html>