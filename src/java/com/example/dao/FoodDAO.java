package com.example.dao;

import Consts.Queries;
import com.example.dto.FoodItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodDAO
{
    private final Connection conn;

    public FoodDAO(Connection conn)
    {
        this.conn = conn;
    }

    public List<FoodItem> getAllFoodItems() throws SQLException
    {
        List<FoodItem> foodList = new ArrayList<>();
        try (PreparedStatement ptm = conn.prepareStatement(Queries.GET_FOOD_LIST))
        {

            try (ResultSet rs = ptm.executeQuery())
            {
                while (rs.next())
                {

                    String foodID = rs.getString("foodID");
                    String foodName = rs.getString("foodName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String category = rs.getString("category");

                    FoodItem foodItem = new FoodItem(foodID, foodName, price, quantity, category);
                    foodList.add(foodItem);
                }
            }
        }
        return foodList;
    }

    public FoodItem getFoodById(String foodID) throws SQLException
    {
        FoodItem foodItem = null;
        try (PreparedStatement ptm = conn.prepareStatement(Queries.GET_FOOD_BY_ID))
        {
            ptm.setString(1, foodID);

            try (ResultSet rs = ptm.executeQuery())
            {
                if (rs.next())
                {
                    String foodName = rs.getString("foodName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String category = rs.getString("category");
                    foodItem = new FoodItem(foodID, foodName, price, quantity, category);
                }
            }
        }
        return foodItem;
    }

    public boolean addFood(FoodItem foodItem) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.ADD_FOOD))
        {
            ptm.setString(1, foodItem.getFoodID());
            ptm.setString(2, foodItem.getFoodName());
            ptm.setDouble(3, foodItem.getPrice());
            ptm.setInt(4, foodItem.getQuantity());
            ptm.setString(5, foodItem.getCategory());

            return ptm.executeUpdate() > 0;
        }
    }

    public boolean updateFood(FoodItem foodItem) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.UPDATE_FOOD))
        {
            ptm.setString(1, foodItem.getFoodName());
            ptm.setDouble(2, foodItem.getPrice());
            ptm.setInt(3, foodItem.getQuantity());
            ptm.setString(4, foodItem.getCategory());
            ptm.setString(5, foodItem.getFoodID());

            return ptm.executeUpdate() > 0;
        }
    }

    public boolean deleteFood(String foodID) throws SQLException
    {
        try (PreparedStatement ptm = conn.prepareStatement(Queries.DELETE_FOOD))
        {
            ptm.setString(1, foodID);
            return ptm.executeUpdate() > 0;
        }
    }

}
