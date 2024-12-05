<%@ page import="java.util.List"%>
<%@ page import="com.example.dto.Order"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>All Orders</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
        </style>
    </head>
    <body>
        <h2>All Orders</h2>

        <%
            // Lấy danh sách đơn hàng từ request
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            String userID = (String) request.getAttribute("userID");

            if (orders != null && !orders.isEmpty()) {
        %>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>User ID</th>
                    <th>Order Date</th>
                    <th>Total Amount</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Order order : orders) {
                %>
                <tr>
                    <td><%= order.getOrderID()%></td>
                    <td><%= order.getUserID()%></td>
                    <td><%= order.getOrderDate()%></td>
                    <td><%= order.getTotalAmount()%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p>No orders found.</p>
        <%
            }
        %>

        <br>
        <!-- Truyền userID vào link -->
        <a href="views/login.jsp">Back to Login</a>

    </body>
</html>
