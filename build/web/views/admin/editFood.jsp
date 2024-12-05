<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.dto.FoodItem" %>
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Food</title>
    <!-- Link đến Bootstrap -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <div class="container mt-5">
      <div class="text-center mb-4">
        <h2 class="mb-4">Edit Food: <%= request.getAttribute("food") != null ? ((FoodItem) request.getAttribute("food")).getFoodName() : "Unknown Food"%></h2>
      </div>
      <%
          FoodItem food = (FoodItem) request.getAttribute("food");
          if (food != null)
          {
      %>
      <form action="FoodController" method="post">
        <input type="hidden" name="action" value="UpdateFood">
        <input type="hidden" name="foodID" value="<%= food.getFoodID()%>">

        <div class="form-group">
          <label for="foodName">Food Name:</label>
          <input type="text" class="form-control" name="foodName" value="<%= food.getFoodName()%>" required>
        </div>

        <div class="form-group">
          <label for="price">Price:</label>
          <input type="number" class="form-control" step="0.01" name="price" value="<%= food.getPrice()%>" required>
        </div>

        <div class="form-group">
          <label for="quantity">Quantity:</label>
          <input type="number" class="form-control" name="quantity" value="<%= food.getQuantity()%>" required>
        </div>

        <div class="form-group">
          <label for="category">Category:</label>
          <input type="text" class="form-control" name="category" value="<%= food.getCategory()%>" required>
        </div>

        <button type="submit" class="btn btn-primary">Update Food</button>
        <a href="FoodController?action=ViewFoods" class="btn btn-secondary ml-2">Cancel</a>
      </form>
      <% }
      else
      { %>
      <div class="alert alert-danger">
        <strong>Error!</strong> Food item not found.
      </div>
      <% }%>
    </div>

    <!-- Thêm script Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  </body>
</html>
