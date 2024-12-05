package com.example.controller;

import com.example.dao.FoodDAO;
import com.example.dto.FoodItem;
import com.example.util.DatabaseConnection;
import com.example.util.Validator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FoodController", urlPatterns
        = {
            "/FoodController"
        })
public class FoodController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        Connection conn = null;
        try {
            conn = DatabaseConnection.initializeDatabase();
        } catch (Exception ex) {
            Logger.getLogger(FoodController.class.getName()).log(Level.SEVERE, null, ex);
        }

        FoodDAO foodDAO = new FoodDAO(conn);

        try {
            if ("ViewFoods".equals(action)) {
                List<FoodItem> foodItems = foodDAO.getAllFoodItems();
                request.setAttribute("foodItems", foodItems);
                request.getRequestDispatcher("views/admin/manageFood.jsp").forward(request, response);
            } else if ("EditFood".equalsIgnoreCase(action)) {
                String foodID = request.getParameter("foodID");
                FoodItem food = foodDAO.getFoodById(foodID);
                request.setAttribute("food", food);
                request.getRequestDispatcher("views/admin/editFood.jsp").forward(request, response);
            } else if ("CreateFood".equalsIgnoreCase(action)) {
                request.getRequestDispatcher("views/admin/createFood.jsp").forward(request, response);
            } else if ("DeleteFood".equalsIgnoreCase(action)) {
                String foodID = request.getParameter("foodID");
                if (foodID != null && !foodID.isEmpty()) {
                    foodDAO.deleteFood(foodID);
                }
                response.sendRedirect("FoodController?action=ViewFoods");
            }
        } catch (IOException | SQLException | ServletException e) {
            request.setAttribute("ERROR", "Error processing food request.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            Connection conn = DatabaseConnection.initializeDatabase();

            FoodDAO foodDAO = new FoodDAO(conn);

            try {
                if ("CreateFood".equalsIgnoreCase(action)) {
                    String foodID = request.getParameter("foodID");
                    String foodName = request.getParameter("foodName");
                    double price = Double.parseDouble(request.getParameter("price"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    String category = request.getParameter("category");

                    if (!Validator.validateFoodItem(foodID, foodName, price, quantity, category)) {
                        request.setAttribute("ERROR", "Invalid input data. Please check your inputs.");
                        request.getRequestDispatcher("views/admin/createFood.jsp").forward(request, response);
                        return;
                    }

                    FoodItem food = new FoodItem(foodID, foodName, price, quantity, category);
                    foodDAO.addFood(food);

                    response.sendRedirect("FoodController?action=ViewFoods");
                } else if ("UpdateFood".equalsIgnoreCase(action)) {
                    String foodID = request.getParameter("foodID");
                    String foodName = request.getParameter("foodName");
                    double price = Double.parseDouble(request.getParameter("price"));
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    String category = request.getParameter("category");

                    FoodItem food = new FoodItem(foodID, foodName, price, quantity, category);
                    foodDAO.updateFood(food);
                    response.sendRedirect("FoodController?action=ViewFoods");
                } else if ("DeleteFood".equalsIgnoreCase(action)) {
                    String foodID = request.getParameter("foodID");
                    foodDAO.deleteFood(foodID);
                    response.sendRedirect("FoodController?action=ViewFoods");
                }
            } catch (IOException | NumberFormatException | SQLException | ServletException e) {
                request.setAttribute("ERROR", "Error processing food request.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(FoodController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
