<%@page import="java.util.ArrayList"%>
<%@page import="com.luis.intro.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Product> products = null;
    int totalProducts = 0;
    if (session.getAttribute("products") == null) {
        products = Product.randomProducts(100001);
        for(int i = 0; i < products.size(); i++) {
            totalProducts += products.get(i).getStock();
        }
        session.setAttribute("products", products);
        session.setAttribute("total", totalProducts);
    } else {
        products = (ArrayList<Product>)request.getSession().getAttribute("products");
        totalProducts = (Integer) session.getAttribute("total");
    }
    
    //rango -32.768 a 32.767
    //Uso short para evitar números tan grandes en el ejemplo
    short currentPage = 0;
    short n = 20;
    try {
        //Si está fuera de rango se genera excepción al convertir de cadena a (long, int, short, byte)
        currentPage = Short.parseShort(request.getParameter("page"));
    } catch (NumberFormatException e) {}
    
    try{
        n = Short.parseShort(request.getParameter("pageProducts"));
    } catch (NumberFormatException e) {}
    
    //Error. No se controla bien la página
    if (currentPage >= products.size()) {
        currentPage = 0;
    }
    
    short nextPage = (short) (currentPage + 1);
    short prevPage = (short) (currentPage - 1);
    
    //No se controla que la página sea negativa
    //No se controla desbordamiento de currentPage
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Store</title>
        <%@include file="../header.jsp" %>
    </head>        
    </head>
    <body>
    <div class="wrapper">
            <%@include file="../sidebar.jsp" %>
            <div id="content">
          
        <h1>Página: <%= currentPage %></h1>
        <p>Total Productos en stock: <%= totalProducts %></p>
        <a href="IntegerOverflow.jsp?page=<%= prevPage %>&pageProducts=<%= n %>">&lt; Anterior (<%= prevPage %>) </a>&nbsp;
        <a href="IntegerOverflow.jsp?page=<%= nextPage %>&pageProducts=<%= n %>">Siguiente &gt; (<%= nextPage %>) </a>
        <div class="row">
            <div class="col">
                <ol start="<%= currentPage * n %>">
                    <%
                        int inicio = currentPage * n;
                        for (int i = 0; i < n; i++) {
                            Product prod = products.get(inicio + i);
                            out.print("<li>" + prod.toString() + "</li>");
                        }
                    %>
                </ol>
            </div>
        </div>
    </div>
        <%@include file="../footer.jsp" %>
    </body>
</html>



    
    