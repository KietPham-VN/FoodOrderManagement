<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <!-- Link đến Bootstrap -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
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

            .login-container {
                max-width: 450px; /* Tăng chiều rộng form */
                width: 100%;
                padding: 40px; /* Tăng padding cho form */
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* Đổ bóng cho form */
            }

            h2 {
                font-size: 32px; /* Tăng kích thước font tiêu đề */
                font-weight: bold;
                color: #333;
                margin-bottom: 30px; /* Thêm khoảng cách dưới tiêu đề */
            }

            .form-control {
                border-radius: 20px;
                padding: 15px;
                font-size: 16px; /* Tăng kích thước font trong các ô input */
            }

            .btn {
                border-radius: 20px;
                padding: 14px;
                font-size: 18px; /* Tăng kích thước font cho nút bấm */
            }

            .alert-danger {
                border-radius: 8px;
            }

            .btn-block {
                width: 100%;
            }

            .form-group {
                margin-bottom: 25px; /* Tăng khoảng cách giữa các trường input */
            }

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
        <div class="login-container">
            <h2 class="text-center mb-4">Login</h2>
            <form action="/FoodOrderManagement/LoginController" method="post">
                <div class="form-group">
                    <label for="userID">Username:</label>
                    <input type="text" class="form-control" id="userID" name="userID" required />
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password" required />
                </div>
                <button type="submit" class="btn btn-primary btn-block" name="action">Login</button>
                <button type="reset" class="btn btn-secondary btn-block mt-2">Reset</button>
            </form>
            <p class="text-center mt-3">
                Don't Have A Account? </br> <a href="/FoodOrderManagement/views/register.jsp">Sign Up</a>
            </p>
            <%
                String error = (String) request.getAttribute("ERROR");
                if (error != null) {
            %>
            <div class="alert alert-danger text-center mt-3">
                <%= error%>
            </div>
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
