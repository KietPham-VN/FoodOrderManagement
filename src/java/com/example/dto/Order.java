package com.example.dto;

public class Order
{

    // Các thuộc tính
    private String orderID;
    private String userID;
    private String orderDate;
    private double totalAmount;

    // Constructor
    public Order(String orderID, String userID, String orderDate,
            double totalAmount)
    {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String orderID)
    {
        this.orderID = orderID;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public double getTotalAmount()
    {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString()
    {
        return String.format("OrderID: %s, UserID: %s, OrderDate: %s, TotalAmount: %.2f",
                orderID, userID, orderDate, totalAmount);
    }
}
