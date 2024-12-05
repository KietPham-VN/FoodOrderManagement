package com.example.dto;

public class FoodItem
{
    // props
    private String foodID;
    private String foodName;
    private double price;
    private int quantity;
    private String category;

    // constructor
    public FoodItem(String foodID, String foodName, double price, int quantity,
        String category)
    {
        this.foodID = foodID;
        this.foodName = foodName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // getters
    public String getFoodID()
    {
        return foodID;
    }

    public String getFoodName()
    {
        return foodName;
    }

    public double getPrice()
    {
        return price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public String getCategory()
    {
        return category;
    }

    // setters
    public void setFoodID(String foodID)
    {
        this.foodID = foodID;
    }

    public void setFoodName(String foodName)
    {
        this.foodName = foodName;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    // toString
    @Override
    public String toString()
    {
        return String.format("FoodID: %s, FoodName: %s, Price: %f, "
            + "Quantity: %d, Category: %s",
            foodID, foodName, price, quantity, category);
    }
}
