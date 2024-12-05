<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.CartItem" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Your Cart</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container mt-5">
            <h2>Your Cart</h2>

            <!-- Hiển thị lỗi nếu có -->
            <div class="alert alert-danger" style="display: <% if (request.getAttribute("ERROR") != null) {
                    out.print("block");
                } else {
                    out.print("none");
                }%>;">
                <%= request.getAttribute("ERROR") != null ? request.getAttribute("ERROR") : ""%>
            </div>

            <!-- Bảng giỏ hàng -->
            <table class="table table-bordered">
                <thead>
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
                                <input type="number" name="quantity" value="<%= item.getQuantity()%>" min="1" required>
                                <button type="submit" name="action" value="UpdateCart" class="btn btn-warning">Update</button>
                            </form>
                        </td>
                        <td><%= String.format("%.2f", item.getPrice())%></td>
                        <td><%= String.format("%.2f", itemTotal)%></td>
                        <td>
                            <form method="POST" action="CartController">
                                <input type="hidden" name="foodID" value="<%= item.getFoodID()%>">
                                <button type="submit" name="action" value="RemoveFromCart" class="btn btn-danger">Remove</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5">No items in your cart</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <!-- Hiển thị tổng tiền -->
            <h4>Total: <%= String.format("%.2f", totalPrice)%></h4>

            <!-- Nút chuyển đến checkout -->
            <form method="GET" action="CartController">
                <button type="submit" name="action" value="Checkout" class="btn btn-primary">Proceed to Checkout</button>
            </form>
            <!-- Nút Place Order -->
            <form method="POST" action="CartController">
                <button type="submit" name="action" value="PlaceOrder" class="btn btn-primary">Place Order</button>
            </form>
        </div>
        <a href="views/login.jsp">Back to Login</a>

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>

</html>
