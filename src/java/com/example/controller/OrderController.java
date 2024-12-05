package com.example.controller;

import com.example.dao.CartDAO;
import com.example.dao.OrderDAO;
import com.example.dto.CartItem;
import com.example.dto.Order;
import com.example.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Kiểm tra xem người dùng có phải là admin không
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("role");

        // Nếu là admin, hiển thị tất cả đơn hàng
        if ("admin".equals(userRole)) {
            if ("viewOrders".equals(action)) {
                try {
                    // Truy vấn tất cả đơn hàng
                    Connection conn = DatabaseConnection.initializeDatabase();

                    // Sử dụng PreparedStatement thay vì Statement
                    String sql = "SELECT * FROM tblOrders";
                    PreparedStatement ps = conn.prepareStatement(sql);

                    // Thực thi truy vấn và nhận kết quả
                    ResultSet rs = ps.executeQuery();

                    List<Order> orders = new ArrayList<>();
                    while (rs.next()) {
                        String orderID = rs.getString("orderID");
                        String userID = rs.getString("userID");
                        Date orderDate = rs.getDate("orderDate");
                        double totalAmount = rs.getDouble("totalAmount");

                        // Tạo đối tượng Order và thêm vào danh sách
                        Order order = new Order(orderID, userID, orderDate.toString(), totalAmount);
                        orders.add(order);
                    }

                    // Gửi danh sách đơn hàng đến trang JSP
                    request.setAttribute("orders", orders);
                    request.getRequestDispatcher("views/admin/viewOrders.jsp").forward(request, response);

                } catch (SQLException ex) {
                    request.setAttribute("ERROR", "Failed to retrieve orders.");
                    request.getRequestDispatcher("views/error.jsp").forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            response.sendRedirect("views/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            Connection conn = DatabaseConnection.initializeDatabase();
            OrderDAO orderDAO = new OrderDAO(conn);
            CartDAO cartDAO = new CartDAO(conn);
            
            try {
                if ("PlaceOrder".equals(action)) {
                    String userID = (String) session.getAttribute("userID");
                    
                    // Lấy giỏ hàng từ session
                    List<CartItem> cartItems = cartDAO.getCartItems(session);
                    
                    if (cartItems == null || cartItems.isEmpty()) {
                        request.setAttribute("ERROR", "Your cart is empty!");
                        request.getRequestDispatcher("views/cart.jsp").forward(request, response);
                        return;
                    }
                    
                    // Tính tổng tiền của giỏ hàng
                    double totalAmount = 0.0;
                    totalAmount = cartItems.stream().map((item) -> item.getQuantity() * item.getPrice()).reduce(totalAmount, (accumulator, _item) -> accumulator + _item);
                    
                    // Tạo đơn hàng mới
                    String orderID = orderDAO.createOrder(userID, totalAmount);
                    
                    // Thêm chi tiết đơn hàng vào tblOrderDetails
                    orderDAO.addOrderDetails(orderID, cartItems);
                    
                    // Xóa giỏ hàng sau khi tạo đơn
                    cartDAO.clearCart(session);
                    
                    // Đặt đối tượng Order vào request để thông báo cho người dùng
                    Order order = orderDAO.getOrderDetails(orderID);
                    request.setAttribute("order", order);
                    
                    // Trả về trang giỏ hàng với thông báo đơn hàng đã được tạo thành công
                    request.getRequestDispatcher("views/cart.jsp").forward(request, response);
                }
            } catch (IOException | SQLException | ServletException e) {
                request.setAttribute("ERROR", "Failed to place order.");
                request.getRequestDispatcher("views/cart.jsp").forward(request, response);
                
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
