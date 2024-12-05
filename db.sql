-- Create the database
CREATE DATABASE FoodOrderDB;
GO
USE FoodOrderDB;

-- Create tblUsers
CREATE TABLE tblUsers (
    userID VARCHAR(50) PRIMARY KEY NOT NULL,
    fullName NVARCHAR(500) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15) NOT NULL,
    roleID NVARCHAR(5) NOT NULL,
    password VARCHAR(50) NOT NULL
);

INSERT INTO tblUsers (userID, fullName, email, phoneNumber, roleID, password) VALUES
('user1', 'Alice Brown', 'alice@example.com', '1234567890', 'USR', 'password1'),
('admin', 'Admin User', 'admin@example.com', '9876543210', 'ADM', 'adminpassword');

-- Create tblFoodItems
CREATE TABLE tblFoodItems (
    foodID VARCHAR(50) PRIMARY KEY NOT NULL,
    foodName NVARCHAR(200) NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    quantity INT NOT NULL,
    category NVARCHAR(100) NOT NULL
);

INSERT INTO tblFoodItems (foodID, foodName, price, quantity, category) VALUES
('F001', 'Margherita Pizza', 10.99, 50, 'Pizza'),
('F002', 'Caesar Salad', 7.99, 30, 'Salad'),
('F003', 'Cheeseburger', 8.99, 40, 'Burger');

-- Create tblOrders
CREATE TABLE tblOrders (
    orderID VARCHAR(50) PRIMARY KEY NOT NULL,
    userID VARCHAR(50),
    orderDate DATE NOT NULL,
    totalAmount DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (userID) REFERENCES tblUsers(userID)
);

-- Create tblOrderDetails
CREATE TABLE tblOrderDetails (
    orderDetailID INT IDENTITY(1,1) PRIMARY KEY,
    orderID VARCHAR(50),
    foodID VARCHAR(50),
    quantity INT NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    FOREIGN KEY (orderID) REFERENCES tblOrders(orderID),
    FOREIGN KEY (foodID) REFERENCES tblFoodItems(foodID)
);

CREATE TABLE tblCart (
    cartID INT IDENTITY(1,1) PRIMARY KEY,
    userID VARCHAR(50),
    foodID VARCHAR(50),
    quantity INT NOT NULL,
    FOREIGN KEY (userID) REFERENCES tblUsers(userID),
    FOREIGN KEY (foodID) REFERENCES tblFoodItems(foodID)
);