<%@ page import="com.example.dto.Order" %>
<html>
    <head>
        <title>Order Confirmation</title>
    </head>
    <body>
        <h2>Order Confirmation</h2>

        <%
            Order order = (Order) request.getAttribute("order");
            if (order != null)
            {
        %>
        <p>Order ID: <%= order.getOrderID()%></p>
        <p>User ID: <%= order.getUserID()%></p>
        <p>Order Date: <%= order.getOrderDate()%></p>
        <p>Total Amount: <%= order.getTotalAmount()%></p>
        <% } else
    { %>
        <p>Order details not found.</p>
        <% }%>
    </body>
</html>
