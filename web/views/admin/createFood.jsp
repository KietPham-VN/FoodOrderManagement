<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Food</title>
    <!-- Thêm Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <div class="container mt-5">
      <div class="text-center mb-4">
        <h2>Create a New Food Item</h2>
      </div>

      <form action="FoodController" method="post">
        <input type="hidden" name="action" value="CreateFood">

        <div class="form-group">
          <label for="foodName">Food ID:</label>
          <input type="text" class="form-control" name="foodID" required>
        </div>

        <div class="form-group">
          <label for="foodName">Food Name:</label>
          <input type="text" class="form-control" name="foodName" required>
        </div>

        <div class="form-group">
          <label for="price">Price:</label>
          <input type="number" class="form-control" step="0.01" name="price" required>
        </div>

        <div class="form-group">
          <label for="quantity">Quantity:</label>
          <input type="number" class="form-control" name="quantity" required>
        </div>

        <div class="form-group">
          <label for="category">Category:</label>
          <input type="text" class="form-control" name="category" required>
        </div>

        <button type="submit" class="btn btn-primary">Create Food</button>
        <a href="FoodController?action=ViewFoods" class="btn btn-secondary ml-2">Cancel</a>
      </form>
      <%
          String error = (String) request.getAttribute("ERROR");
          if (error != null)
          {
      %>
      <div class="alert alert-danger text-center mt-3">
        <%= error%>
      </div>
      <%
          }
      %>
    </div>

    <!-- Thêm các script Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  </body>
</html>
