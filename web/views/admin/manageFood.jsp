<%@page import="java.util.List"%>
<%@page import="com.example.dto.FoodItem"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food List</title>
        <!-- Link đến Bootstrap -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script>
            function confirmDelete(foodID) {
                if (confirm("Are you sure you want to delete this food item?")) {
                    window.location.href = "FoodController?action=DeleteFood&foodID=" + foodID;
                } else {
                    return false;
                }
            }
        </script>
    </head>
    <body>
        <div class="container mt-5">
            <!-- Thêm nút đăng xuất ở góc trái -->
            <a href="LogoutController" class="btn btn-danger float-left mb-3">Logout</a>

            <div class="text-center mb-4">
                <h2 class="mb-4">Food Management</h2>
            </div>
            <%
                // Lấy danh sách món ăn từ request
                List<FoodItem> foodItems = (List<FoodItem>) request.getAttribute("foodItems");
                if (foodItems != null && !foodItems.isEmpty())
                {
            %>       
            <a href="FoodController?action=CreateFood" class="btn btn-success float-right mb-3">Create FoodItem</a>

            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                    <tr>
                        <th>No.</th>
                        <th>Food ID</th>
                        <th>Food Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Category</th>
                        <th colspan="2" style="text-align: center;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 0;
                        for (FoodItem foodItem : foodItems)
                        {
                    %>
                    <tr>
                        <td><%= (++count)%></td>
                        <td><%= foodItem.getFoodID()%></td>
                        <td><%= foodItem.getFoodName()%></td>
                        <td><%= foodItem.getPrice()%></td>
                        <td><%= foodItem.getQuantity()%></td>
                        <td><%= foodItem.getCategory()%></td>
                        <td>
                            <!-- Thêm link xóa món ăn với sự kiện xác nhận -->
                            <button onclick="confirmDelete('<%= foodItem.getFoodID()%>')" class="btn btn-danger btn-sm">Delete</button>

                        </td>
                        <td>
                            <!-- Thêm link cập nhật món ăn -->
                            <a href="FoodController?action=EditFood&foodID=<%= foodItem.getFoodID()%>" class="btn btn-warning btn-sm">Update</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <h5>Number of food items found: <%= foodItems.size()%></h5>
            <%
            } else
            {
            %>
            <h3>No food items found.</h3>
            <%
                }
            %>
        </div>

        <!-- Thêm script Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
