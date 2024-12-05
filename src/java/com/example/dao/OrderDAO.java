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
import java.util.UUID;
import javax.servlet.http.HttpSession;

public class OrderDAO {

    private final Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    // Phương thức lấy thông tin chi tiết của một đơn hàng
    public Order getOrderDetails(String orderID) throws SQLException {
        String query = "SELECT * FROM tblOrders WHERE orderID = ?";
        Order order = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderID);
            ResultSet resultSet = stmt.executeQuery();

            // Kiểm tra nếu có đơn hàng với ID này
            if (resultSet.next()) {
                // Tạo đối tượng Order từ dữ liệu trong cơ sở dữ liệu
                order = new Order(
                        resultSet.getString("orderID"),
                        resultSet.getString("userID"),
                        resultSet.getString("orderDate"),
                        resultSet.getDouble("totalAmount")
                );
            }
        }

        return order;
    }

    // Tạo đơn hàng mới
    public String createOrder(String userID, double totalAmount) throws SQLException {
        String orderID = UUID.randomUUID().toString();
        String query = "INSERT INTO tblOrders (orderID, userID, orderDate, totalAmount) VALUES (?, ?, GETDATE(), ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderID);
            stmt.setString(2, userID);
            stmt.setDouble(3, totalAmount);
            stmt.executeUpdate();
        }
        return orderID;
    }

    // Thêm chi tiết đơn hàng vào bảng tblOrderDetails
    public void addOrderDetails(String orderID, List<CartItem> cartItems) throws SQLException {
        String query = "INSERT INTO tblOrderDetails (orderID, foodID, quantity, price) VALUES (?, ?, ?, ?)";
        String priceQuery = "SELECT price FROM tblFoodItems WHERE foodID = ?";  // Lấy giá từ bảng tblFoodItems

        try (PreparedStatement stmt = conn.prepareStatement(query);
                PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {

            for (CartItem item : cartItems) {
                // Lấy giá của món ăn
                priceStmt.setString(1, item.getFoodID());
                ResultSet rs = priceStmt.executeQuery();

                if (rs.next()) {
                    double price = rs.getDouble("price");

                    // Chèn chi tiết đơn hàng vào bảng tblOrderDetails
                    stmt.setString(1, orderID);
                    stmt.setString(2, item.getFoodID());
                    stmt.setInt(3, item.getQuantity());
                    stmt.setDouble(4, price);
                    stmt.addBatch();
                } else {
                    throw new SQLException("Food item not found: " + item.getFoodID());
                }
            }
            stmt.executeBatch();
        }
    }

    // Thêm chi tiết đơn hàng cho một món ăn riêng lẻ
    public void addOrderDetails(String orderID, String foodID, int quantity) throws SQLException {
        // Câu lệnh SQL để lấy giá của món ăn từ bảng tblFoodItems
        String priceQuery = "SELECT price FROM tblFoodItems WHERE foodID = ?";

        // Câu lệnh SQL để chèn chi tiết đơn hàng vào bảng OrderDetails
        String insertQuery = "INSERT INTO tblOrderDetails (orderID, foodID, quantity, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
            // Lấy giá của món ăn từ bảng tblFoodItems
            priceStmt.setString(1, foodID);

            // Thực thi câu lệnh để lấy giá
            ResultSet rs = priceStmt.executeQuery();

            if (rs.next()) {
                // Lấy giá của món ăn từ kết quả truy vấn
                double price = rs.getDouble("price");

                // Chèn chi tiết đơn hàng vào bảng tblOrderDetails
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setString(1, orderID);  // Đặt giá trị orderID
                    stmt.setString(2, foodID);   // Đặt giá trị foodID
                    stmt.setInt(3, quantity);    // Đặt giá trị quantity
                    stmt.setDouble(4, price);    // Đặt giá trị price

                    // Thực thi câu lệnh chèn dữ liệu vào bảng
                    stmt.executeUpdate();
                }
            } else {
                // Nếu không tìm thấy món ăn trong bảng tblFoodItems
                throw new SQLException("Food item not found.");
            }
        }
    }

    // Đặt hàng và thêm chi tiết vào tblOrderDetails
    public void placeOrder(String userID, double totalAmount, HttpSession session) throws SQLException {
        String orderID = generateOrderID(userID);
        Date orderDate = new Date(System.currentTimeMillis());

        // Insert order vào bảng tblOrders
        String insertOrderQuery = "INSERT INTO tblOrders (orderID, userID, orderDate, totalAmount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertOrderQuery)) {
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

        try (PreparedStatement stmt = conn.prepareStatement(insertOrderDetailsQuery)) {
            for (CartItem cartItem : cartItems) {
                stmt.setString(1, orderID);
                stmt.setString(2, cartItem.getFoodID());
                stmt.setInt(3, cartItem.getQuantity());
                stmt.setDouble(4, cartItem.getPrice());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
        cartDAO.clearCart(session);
    }

    // Hàm sinh orderID
    private String generateOrderID(String userID) {
        return userID + "-" + System.currentTimeMillis();  // Ví dụ: userID + timestamp
    }

    // Hàm lấy danh sách đơn hàng của người dùng
    public List<Order> getOrdersByUserID(String userID) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM tblOrders WHERE userID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
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
    public Order getOrderByID(String orderID) throws SQLException {
        String query = "SELECT * FROM tblOrders WHERE orderID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userID = rs.getString("userID");
                Date orderDate = rs.getDate("orderDate");
                double totalAmount = rs.getDouble("totalAmount");

                return new Order(orderID, userID, orderDate.toString(), totalAmount);
            }
        }
        return null;
    }

    // Hàm cập nhật tổng số tiền của đơn hàng
    public void updateTotalAmount(String orderID, double totalAmount) throws SQLException {
        String query = "UPDATE tblOrders SET totalAmount = ? WHERE orderID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDouble(1, totalAmount);
            stmt.setString(2, orderID);
            stmt.executeUpdate();
        }
    }

    // Hàm xóa đơn hàng
    public void deleteOrder(String orderID) throws SQLException {
        String query = "DELETE FROM tblOrders WHERE orderID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderID);
            stmt.executeUpdate();
        }
    }
}
