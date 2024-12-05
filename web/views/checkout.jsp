<%@ page import="com.example.dto.FoodItem"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Checkout</title>
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
                max-width: 1000px;
            }
        </style>
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <h2 class="mb-4 text-center text-primary">Checkout</h2>

            <!-- View Cart Button -->
            <form action="CartController" method="GET" class="mb-4">
                <button type="submit" class="btn btn-primary btn-lg">View Cart</button>
            </form>

            <h3 class="mb-4">Available Products</h3>

            <!-- Product Table -->
            <div class="table-responsive">
                <table class="table table-striped table-hover table-bordered shadow-sm">
                    <thead class="thead-dark bg-primary text-white">
                        <tr>
                            <th>Food Name</th>
                            <th>Price</th>
                            <th>Category</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<FoodItem> foodItems = (List<FoodItem>) request.getAttribute("FoodItems");
                            if (foodItems != null && !foodItems.isEmpty()) {
                                for (FoodItem food : foodItems) {
                        %>
                        <tr>
                            <td><%= food.getFoodName()%></td>
                            <td><%= food.getPrice()%></td>
                            <td><%= food.getCategory()%></td>
                            <td>
                                <form method="POST" action="CartController">
                                    <input type="hidden" name="foodID" value="<%= food.getFoodID()%>">
                                    <div class="input-group">
                                        <input type="number" name="quantity" class="form-control" value="1" min="1" required>
                                        <button type="submit" name="action" value="AddToCart" class="btn btn-success">Add to Cart</button>
                                    </div>
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="4" class="text-center">No products available</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>

            <!-- Back to Login Button -->
            <div class="text-center mt-4">
                <a href="views/login.jsp" class="btn btn-secondary btn-lg">Back to Login</a>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>    </body>
</html>
