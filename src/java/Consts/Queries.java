package Consts;

public class Queries
{

    // User related queries
    public static final String LOGIN
            = "SELECT 1 "
            + "FROM [tblUsers] "
            + "WHERE [UserID] = ? AND [Password] = ?";

    public static final String CHECK_ADMIN
            = "SELECT [RoleID] "
            + "FROM [tblUsers] "
            + "WHERE [UserID] = ? AND [Password] = ?";
    public static final String CREATE_USER
            = "INSERT [tblUsers] "
            + "VALUES(?, ?, ?, ?, ?, ?)";

    public static final String CHECK_USER_EXIST
            = "SELECT 1 "
            + "FROM [tblUsers] "
            + "WHERE [userID] = ?";

    public static final String GET_FOOD_LIST
            = "SELECT * "
            + "FROM [tblFoodItems]";

    // Food related queries
    public static final String GET_FOOD_BY_ID
            = "SELECT foodID, foodName, price, quantity, category "
            + "FROM [tblFoodItems] "
            + "WHERE foodID = ?";

    public static final String ADD_FOOD
            = "INSERT INTO [tblFoodItems] (foodID, foodName, price, quantity, category) "
            + "VALUES (?, ?, ?, ?, ?)";

    public static final String UPDATE_FOOD
            = "UPDATE [tblFoodItems] "
            + "SET foodName = ?, price = ?, quantity = ?, category = ? "
            + "WHERE foodID = ?";

    public static final String DELETE_FOOD
            = "DELETE FROM [tblFoodItems] "
            + "WHERE foodID = ?";

    // Các truy vấn cho CartDAO
    public static final String GET_CART_ITEMS
            = "SELECT c.foodID, f.foodName, f.price, c.quantity "
            + "FROM tblCart c "
            + "INNER JOIN tblFoodItems f ON c.foodID = f.foodID "
            + "WHERE c.userID = ?";
    public static final String ADD_TO_CART
            = "MERGE INTO tblCart AS Target "
            + "USING (SELECT ? AS userID, ? AS foodID) AS Source "
            + "ON Target.userID = Source.userID AND Target.foodID = Source.foodID "
            + "WHEN MATCHED THEN "
            + "    UPDATE SET quantity = Target.quantity + ? "
            + "WHEN NOT MATCHED THEN "
            + "    INSERT (userID, foodID, quantity) VALUES (Source.userID, Source.foodID, ?);";
    public static final String UPDATE_CART
            = "UPDATE tblCart "
            + "SET quantity = ? "
            + "WHERE userID = ? AND foodID = ?";
    public static final String REMOVE_FROM_CART
            = "DELETE FROM tblCart "
            + "WHERE userID = ? AND foodID = ?";
    public static final String CLEAR_CART
            = "DELETE FROM tblCart "
            + "WHERE userID = ?";

    // OrderDAO
    public static final String ADD_ORDER
            = "INSERT INTO tblOrders (userID, totalAmount) "
            + "VALUES (?, ?)";
    public static final String ADD_ORDER_DETAILS
            = "INSERT INTO tblOrderDetails (orderID, foodID, quantity, price) "
            + "VALUES (?, ?, ?, ?)";
    public static final String GET_LAST_ORDER_ID
            = "SELECT LAST_INSERT_ID()";
    public static final String GET_ORDER_BY_ID
            = "SELECT * "
            + "FROM tblOrders "
            + "WHERE orderID = ?";
    public static final String GET_ORDER_DETAILS_BY_ORDER_ID
            = "SELECT foodID, quantity, price "
            + "FROM OrderDetails "
            + "WHERE orderID = ?";

}
