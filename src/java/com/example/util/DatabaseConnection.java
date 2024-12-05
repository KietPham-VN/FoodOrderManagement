package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
    public static Connection initializeDatabase() throws Exception
    {
        String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=FoodOrderDB;encrypt=false";
        String jdbcUsername = "sa";
        String jdbcPassword = "12345";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
}
