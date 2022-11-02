<%@page import="java.util.ArrayList"%>
<%@page import="com.luis.intro.Product"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    ArrayList<Product> products = null;
    int totalProducts = 0;
    boolean overflow = false;
    if (session.getAttribute("products") == null) {
        products = Product.randomProducts(100001);
        try {
            for(int i = 0; i < products.size(); i++) {
                totalProducts = Math.addExact(totalProducts, products.get(i).getStock());
            }
        } catch (ArithmeticException e) {
            totalProducts = Integer.MAX_VALUE;
            overflow = true;
        }
        session.setAttribute("products", products);
        session.setAttribute("total", totalProducts);
        session.setAttribute("overflow", overflow);        
    } else {
        products = (ArrayList<Product>)request.getSession().getAttribute("products");
        totalProducts = (Integer) session.getAttribute("total");
        overflow = (Boolean) session.getAttribute("overflow");
    }
    
    //rango -32,768 a 32,767
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
    
    
    if (n <= 0) {
        n = 20;
    }
    
    short totalPages = (short) (products.size() / n);
    
    if (currentPage >= totalPages) {
        currentPage = totalPages;
    }
    
    if (currentPage < 0) {
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
    </head>
    <body>
        <h1>Página: <%= currentPage %></h1>
        <p>Total Productos en stock: <%= totalProducts %><%= overflow ? "+":""%></p>
        <a href="IntegerOverflowSolved.jsp?page=<%= prevPage %>&pageProducts=<%= n %>">&lt; Anterior (<%= prevPage %>)  </a>&nbsp;
        <a href="IntegerOverflowSolved.jsp?page=<%= nextPage %>&pageProducts=<%= n %>">Siguiente (<%= nextPage %>) &gt; </a>
        <ol>
            <%
                int inicio = currentPage * n;
                for (int i = 0; i < n && inicio + i < products.size(); i++) {
                    Product prod = products.get(inicio + i);
                    out.print("<li>" + prod.toString() + "</li>");
                }
            %>
        </ol>
    </body>
</html>