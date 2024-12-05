package com.example.util;

public class Validator
{
    public static String validateUser(String userID, String fullName, String email, String phoneNumber, String password, String confirmPassword)
    {
        // Kiểm tra trường bắt buộc
        if (userID == null || userID.trim().isEmpty()
            || fullName == null || fullName.trim().isEmpty()
            || email == null || email.trim().isEmpty()
            || phoneNumber == null || phoneNumber.trim().isEmpty()
            || password == null || password.trim().isEmpty()
            || confirmPassword == null || confirmPassword.trim().isEmpty())
        {
            return "Please fill in all required fields.";
        }

        // Kiểm tra định dạng email
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$"))
        {
            return "Invalid email format.";
        }

        // Kiểm tra số điện thoại
        if (!phoneNumber.matches("\\d+"))
        {
            return "Phone number must contain only digits.";
        }

        // Kiểm tra mật khẩu khớp
        if (!password.equals(confirmPassword))
        {
            return "Passwords do not match.";
        }

        // Nếu không có lỗi, trả về null
        return null;
    }

    // Kiểm tra foodID theo định dạng Fxxx (x là số)
    public static boolean isValidFoodID(String foodID)
    {
        // Kiểm tra xem foodID có đúng định dạng Fxxx không
        String regex = "^F\\d{3}$";  // F theo sau là 3 chữ số
        return foodID != null && foodID.matches(regex);
    }

    // Kiểm tra tên món ăn phải là chuỗi không rỗng
    public static boolean isValidFoodName(String foodName)
    {
        return foodName != null && !foodName.trim().isEmpty();
    }

    // Kiểm tra giá món ăn phải là số dương
    public static boolean isValidPrice(double price)
    {
        return price > 0; // Giá phải lớn hơn 0
    }

    // Kiểm tra số lượng món ăn phải là số nguyên không âm
    public static boolean isValidQuantity(int quantity)
    {
        return quantity >= 0; // Số lượng không thể âm
    }

    // Kiểm tra thể loại món ăn phải là chuỗi không rỗng
    public static boolean isValidCategory(String category)
    {
        return category != null && !category.trim().isEmpty();
    }

    // Kiểm tra tất cả các điều kiện
    public static boolean validateFoodItem(String foodID, String foodName, double price, int quantity, String category)
    {
        // Kiểm tra ID món ăn
        if (!isValidFoodID(foodID))
        {
            return false; // Nếu ID không hợp lệ
        }

        // Kiểm tra tên món ăn
        if (!isValidFoodName(foodName))
        {
            return false; // Nếu tên món ăn không hợp lệ
        }

        // Kiểm tra giá món ăn
        if (!isValidPrice(price))
        {
            return false; // Nếu giá món ăn không hợp lệ
        }

        // Kiểm tra số lượng món ăn
        if (!isValidQuantity(quantity))
        {
            return false; // Nếu số lượng không hợp lệ
        }

        // Kiểm tra thể loại món ăn
        if (!isValidCategory(category))
        {
            return false; // Nếu thể loại không hợp lệ
        }

        return true; // Tất cả các điều kiện đều hợp lệ
    }
}
