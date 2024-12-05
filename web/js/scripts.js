function confirmDelete(foodID) {
    if (confirm("Are you sure you want to delete this food item?")) {
        // Nếu người dùng chọn "OK", thực hiện chuyển hướng
        window.location.href = "FoodController?action=DeleteFood&foodID=" + foodID;
    } else {
        // Nếu người dùng chọn "Cancel", không làm gì cả
        return false;
    }
}