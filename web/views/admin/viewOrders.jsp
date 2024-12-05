<%@page import="com.example.dto.CartItem"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Order Details</title>
    </head>
    <body>
        <h1>Order Details</h1>

        <p><strong>Order ID:</strong> <%= request.getAttribute("order").getOrderID()%></p>
        <p><strong>User ID:</strong> <%= request.getAttribute("order").getUserID()%></p>
        <p><strong>Order Date:</strong> <%= request.getAttribute("order").getOrderDate()%></p>
        <p><strong>Total Amount:</strong> <%= request.getAttribute("order").getTotalAmount()%></p>

        <h3>Items in the Order:</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Food ID</th>
                    <th>Quantity</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<CartItem> cartItems = (List<CartItem>) request.getAttribute("order").getCartItems();
                    for (CartItem item : cartItems) {
                %>
                <tr>
                    <td><%= item.getFoodID()%></td>
                    <td><%= item.getQuantity()%></td>
                    <td><%= item.getPrice()%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </body>
</html>
