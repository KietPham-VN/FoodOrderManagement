package com.example.dto;

public class CartItem
{

    private String foodID;
    private String name;
    private double price;
    private int quantity;

    // Constructor không tham số
    public CartItem()
    {
    }

    // Constructor đầy đủ
    public CartItem(String foodID, String name, double price, int quantity)
    {
        this.foodID = foodID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter và Setter cho foodID
    public String getFoodID()
    {
        return foodID;
    }

    public void setFoodID(String foodID)
    {
        this.foodID = foodID;
    }

    // Getter và Setter cho name
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    // Getter và Setter cho price
    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    // Getter và Setter cho quantity
    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    // Phương thức tính tổng giá của sản phẩm trong giỏ
    public double getTotalPrice()
    {
        return price * quantity;
    }

    @Override
    public String toString()
    {
        return "CartItem{"
                + "foodID='" + foodID + '\''
                + ", name='" + name + '\''
                + ", price=" + price
                + ", quantity=" + quantity
                + ", totalPrice=" + getTotalPrice()
                + '}';
    }
}
