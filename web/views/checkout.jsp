<%@page import="com.example.dto.FoodItem"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Checkout</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2>Checkout</h2>

            <form action="CartController" method="GET">
                <button type="submit" class="btn btn-primary mb-4">View Cart</button>
            </form>

            <h3>Available Products</h3>
            <table class="table table-bordered">
                <thead>
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
                        if (foodItems != null && !foodItems.isEmpty())
                        {
                            for (FoodItem food : foodItems)
                            {
                    %>
                    <tr>
                        <td><%= food.getFoodName()%></td>
                        <td><%= food.getPrice()%></td>
                        <td><%= food.getCategory()%></td>
                        <td>
                            <form method="POST" action="CartController">
                                <input type="hidden" name="foodID" value="<%= food.getFoodID()%>">
                                <input type="number" name="quantity" value="1" min="1" required>
                                <button type="submit" name="action" value="AddToCart" class="btn btn-primary">Add to Cart</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else
                    {
                    %>
                    <tr>
                        <td colspan="4">No products available</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </body>
</html>
