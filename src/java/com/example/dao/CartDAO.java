package com.example.dao;

import Consts.Queries;
import com.example.dto.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

public class CartDAO
{

    private final Connection conn;

    public CartDAO(Connection conn)
    {
        this.conn = conn;
    }

    public List<CartItem> getCartItems(HttpSession session) throws SQLException
    {
        List<CartItem> cartItems = new ArrayList<>();
        String userId = (String) session.getAttribute("userID");

        if (userId == null)
        {
            return cartItems;
        }

        try (PreparedStatement ps = conn.prepareStatement(Queries.GET_CART_ITEMS))
        {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    CartItem item = new CartItem();
                    item.setFoodID(rs.getString("foodID"));
                    item.setName(rs.getString("foodName"));
                    item.setPrice(rs.getDouble("price"));
                    item.setQuantity(rs.getInt("quantity"));
                    cartItems.add(item);
                }
            }
        }

        return cartItems;
    }

    public void addToCart(HttpSession session, String foodID, int quantity) throws SQLException
    {
        String userId = (String) session.getAttribute("userID");
        if (userId == null)
        {
            throw new IllegalStateException("User must be logged in to add items to the cart.");
        }

        try (PreparedStatement ps = conn.prepareStatement(Queries.ADD_TO_CART))
        {
            ps.setString(1, userId);
            ps.setString(2, foodID);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            ps.executeUpdate();
        }
    }

    public void updateCart(HttpSession session, String foodID, int quantity) throws SQLException
    {
        String userId = (String) session.getAttribute("userID");
        if (userId == null)
        {
            throw new IllegalStateException("User must be logged in to update cart items.");
        }

        try (PreparedStatement ps = conn.prepareStatement(Queries.UPDATE_CART))
        {
            ps.setInt(1, quantity);
            ps.setString(2, userId);
            ps.setString(3, foodID);
            ps.executeUpdate();
        }
    }

    public void removeFromCart(HttpSession session, String foodID) throws SQLException
    {
        String userId = (String) session.getAttribute("userID");
        if (userId == null)
        {
            throw new IllegalStateException("User must be logged in to remove items from the cart.");
        }

        try (PreparedStatement ps = conn.prepareStatement(Queries.REMOVE_FROM_CART))
        {
            ps.setString(1, userId);
            ps.setString(2, foodID);
            ps.executeUpdate();
        }
    }

}
