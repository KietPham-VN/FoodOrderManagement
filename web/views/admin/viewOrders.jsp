<%@ page import="java.util.List"%>
<%@ page import="com.example.dto.Order"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>All Orders</title>
        <!-- Thêm link Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            table {
                width: 100%;
                margin-top: 20px;
            }
            th, td {
                padding: 10px;
                text-align: left;
            }
        </style>
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <h2 class="mb-4 text-center text-primary">All Orders</h2>

            <%
                // Lấy danh sách đơn hàng từ request
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                String userID = (String) request.getAttribute("userID");

                if (orders != null && !orders.isEmpty()) {
            %>
            <div class="table-responsive">
                <table class="table table-striped table-hover table-bordered shadow-sm">
                    <thead class="thead-dark bg-primary text-white">
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
            </div>
            <%
            } else {
            %>
            <div class="alert alert-warning" role="alert">
                No orders found.
            </div>
            <%
                }
            %>

            <!-- Button Back to Login -->
            <div class="text-center mt-4">
                <a href="views/login.jsp" class="btn btn-success btn-lg">Back to Login</a>
            </div>
        </div>

        <!-- Thêm script Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pzjw8f+ua7Kw1TIq0v8FqP1IztfGB7/Klr4T5tEK0R1N6au3XHfC4Zpww8H3pOhm" crossorigin="anonymous"></script>
    </body>
</html>
