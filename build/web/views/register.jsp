<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>User Registration</title>
        <!-- Thêm link đến Bootstrap CDN -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <style>
            /* Căn giữa trang */
            body, html {
                height: 100%;
                margin: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                background-color: #f4f7fc;
            }

            /* Căn giữa form */
            .container {
                max-width: 800px; /* Điều chỉnh chiều rộng tối đa của form */
                width: 100%;
                padding: 30px;
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* Đổ bóng cho form */
            }

            h2 {
                text-align: center;
                font-size: 28px;
                margin-bottom: 30px;
                color: #333;
            }

            .form-group label {
                font-size: 16px;
            }

            .form-control {
                border-radius: 20px; /* Bo tròn các góc input */
                padding: 12px 18px;
                font-size: 16px;
            }

            .btn {
                border-radius: 20px; /* Bo tròn góc nút */
                padding: 12px;
                font-size: 18px;
                width: 100%;
            }

            .alert-danger {
                border-radius: 8px;
            }

            .form-group {
                margin-bottom: 25px;
            }

            /* Thêm khoảng cách giữa các trường input */
            .row > .col {
                margin-bottom: 20px;
            }

            /* Đổi màu cho liên kết */
            p.text-center a {
                color: #007bff;
                text-decoration: none;
            }

            p.text-center a:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h2>Register</h2>
            <form action="/FoodOrderManagement/RegisterController" method="post" class="mt-4">

                <!-- Email (Chiếm toàn bộ chiều rộng) -->
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" class="form-control" required>
                </div>

                <div class="row">
                    <!-- Cột bên trái -->
                    <div class="col-md-6">
                        <!-- User ID -->
                        <div class="form-group">
                            <label for="userID">User ID:</label>
                            <input type="text" id="userID" name="userID" class="form-control" required>
                        </div>

                        <!-- Full Name -->
                        <div class="form-group">
                            <label for="fullName">Full Name:</label>
                            <input type="text" id="fullName" name="fullName" class="form-control" required>
                        </div>

                        <!-- Phone Number -->
                        <div class="form-group">
                            <label for="phoneNumber">Phone Number:</label>
                            <input type="text" id="phoneNumber" name="phoneNumber" class="form-control" required>
                        </div>
                    </div>

                    <!-- Cột bên phải -->
                    <div class="col-md-6">
                        <!-- Role ID -->
                        <div class="form-group">
                            <label for="roleID">Role ID:</label>
                            <input type="text" id="roleID" name="roleID" class="form-control" required>
                        </div>

                        <!-- Password -->
                        <div class="form-group">
                            <label for="password">Password:</label>
                            <input type="password" id="password" name="password" class="form-control" required>
                        </div>

                        <!-- Confirm Password -->
                        <div class="form-group">
                            <label for="confirmPassword">Confirm Password:</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">Register</button>
            </form>
            <p class="text-center mt-3">
                Already Have An Account? </br> <a href="/FoodOrderManagement/views/login.jsp">Sign In</a>
            </p>

            <%
                String error = (String) request.getAttribute("ERROR");
                if (error != null) {
            %>
            <div class="alert alert-danger mt-3">
                <%= error%>
            </div>
            <%
                }
            %>
        </div>

        <!-- Thêm các script của Bootstrap -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
