package com.example.dao;

import com.example.dto.CartItem;
import com.example.dto.Order;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class OrderDAO
{

    private final Connection conn;

    public OrderDAO(Connection conn)
    {
        this.conn = conn;
    }
    // Hàm tạo đơn hàng mới

    public void placeOrder(String userID, double totalAmount, HttpSession session) throws SQLException
    {
        String orderID = generateOrderID(userID);
        Date orderDate = new Date(System.currentTimeMillis());

        // Insert order vào bảng tblOrders
        String insertOrderQuery = "INSERT INTO tblOrders (orderID, userID, orderDate, totalAmount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertOrderQuery))
        {
            stmt.setString(1, orderID);
            stmt.setString(2, userID);
            stmt.setDate(3, orderDate);
            stmt.setDouble(4, totalAmount);
            stmt.executeUpdate();
        }

        // Insert các item trong giỏ hàng vào bảng tblOrderDetails
        CartDAO cartDAO = new CartDAO(conn);
        List<CartItem> cartItems = cartDAO.getCartItems(session);
        String insertOrderDetailsQuery = "INSERT INTO tblOrderDetails (orderID, foodID, quantity, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(insertOrderDetailsQuery))
        {
            for (CartItem cartItem : cartItems)
            {
                stmt.setString(1, orderID);
                stmt.setString(2, cartItem.getFoodID());
                stmt.setInt(3, cartItem.getQuantity());
                stmt.setDouble(4, cartItem.getPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();  // Execute batch insert
        }

        // Sau khi tạo đơn hàng xong, xóa giỏ hàng
        cartDAO.clearCart(session);
    }

    // Hàm sinh orderID
    private String generateOrderID(String userID)
    {
        return userID + "-" + System.currentTimeMillis();  // Ví dụ: userID + timestamp
    }

    // Hàm lấy danh sách đơn hàng của người dùng
    public List<Order> getOrdersByUserID(String userID) throws SQLException
    {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM tblOrders WHERE userID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                String orderID = rs.getString("orderID");
                Date orderDate = rs.getDate("orderDate");
                double totalAmount = rs.getDouble("totalAmount");

                Order order = new Order(orderID, userID, orderDate.toString(), totalAmount);
                orders.add(order);
            }
        }
        return orders;
    }

    // Hàm lấy thông tin đơn hàng dựa trên orderID
    public Order getOrderByID(String orderID) throws SQLException
    {
        String query = "SELECT * FROM tblOrders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                String userID = rs.getString("userID");
                Date orderDate = rs.getDate("orderDate");
                double totalAmount = rs.getDouble("totalAmount");

                return new Order(orderID, userID, orderDate.toString(), totalAmount);
            }
        }
        return null;
    }

    // Hàm cập nhật tổng số tiền của đơn hàng
    public void updateTotalAmount(String orderID, double totalAmount) throws SQLException
    {
        String query = "UPDATE tblOrders SET totalAmount = ? WHERE orderID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setDouble(1, totalAmount);
            stmt.setString(2, orderID);
            stmt.executeUpdate();
        }
    }

    // Hàm xóa đơn hàng
    public void deleteOrder(String orderID) throws SQLException
    {
        String query = "DELETE FROM tblOrders WHERE orderID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query))
        {
            stmt.setString(1, orderID);
            stmt.executeUpdate();
        }
    }
}
