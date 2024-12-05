<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.CartItem" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Your Cart</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

        <style>
            table {
                width: 100%;
            }
            th, td {
                padding: 12px;
                text-align: left;
            }
            .container {
                max-width: 1200px;
            }
            .btn-group {
                display: flex;
            }
            /* Flexbox layout for the action buttons */
            .action-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            .action-buttons form {
                flex: 1; /* Đảm bảo các form có chiều rộng bằng nhau */
            }
            .action-buttons .btn {
                width: 80%; /* Đảm bảo các nút có chiều rộng bằng nhau */
            }
        </style>
    </head>

    <body class="bg-light">
        <div class="container mt-5">
            <h2 class="text-center text-primary mb-4">Your Cart</h2>

            <!-- Hiển thị lỗi nếu có -->
            <div class="alert alert-danger" style="display: <% if (request.getAttribute("ERROR") != null) {
                    out.print("block");
                } else {
                    out.print("none");
                }%>;">
                <%= request.getAttribute("ERROR") != null ? request.getAttribute("ERROR") : ""%>
            </div>

            <!-- Bảng giỏ hàng -->
            <div class="table-responsive">
                <table class="table table-striped table-bordered table-hover shadow-sm">
                    <thead class="thead-dark bg-primary text-white">
                        <tr>
                            <th>Food Name</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Total</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<CartItem> cartItems = (List<CartItem>) request.getAttribute("cartItems");
                            double totalPrice = 0.0;
                            if (cartItems != null && !cartItems.isEmpty()) {
                                for (CartItem item : cartItems) {
                                    double itemTotal = item.getQuantity() * item.getPrice();
                                    totalPrice += itemTotal;
                        %>
                        <tr>
                            <td><%= item.getName()%></td>
                            <td>
                                <form method="POST" action="CartController">
                                    <input type="hidden" name="foodID" value="<%= item.getFoodID()%>">
                                    <input type="number" name="quantity" value="<%= item.getQuantity()%>" min="1" class="form-control" required>
                                </form>
                            </td>
                            <td><%= String.format("%.2f", item.getPrice())%></td>
                            <td><%= String.format("%.2f", itemTotal)%></td>
                            <td>
                                <div class="btn-group">
                                    <!-- Nút Update -->
                                    <form method="POST" action="CartController">
                                        <input type="hidden" name="foodID" value="<%= item.getFoodID()%>">
                                        <button type="submit" name="action" value="UpdateCart" class="btn btn-warning">Update</button>
                                    </form>
                                    <!-- Nút Remove -->
                                    <form method="POST" action="CartController">
                                        <input type="hidden" name="foodID" value="<%= item.getFoodID()%>">
                                        <button type="submit" name="action" value="RemoveFromCart" class="btn btn-danger">Remove</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="5" class="text-center">No items in your cart</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <h4 class="text-end">Total: <%= String.format("%.2f", totalPrice)%></h4>

            <!-- Action buttons with equal width -->
            <div class="action-buttons mt-4">
                <!-- Nút Back to Login -->
                <form method="GET" action="views/login.jsp">
                    <button type="submit" class="btn btn-secondary">Back to Login</button>
                </form>

                <!-- Nút Proceed to Checkout -->
                <form method="GET" action="CartController">
                    <button type="submit" name="action" value="Checkout" class="btn btn-primary">Proceed to Checkout</button>
                </form>

                <!-- Nút Place Order -->
                <form method="POST" action="CartController">
                    <button type="submit" name="action" value="PlaceOrder" class="btn btn-success mx-2">Place Order</button>
                </form>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
