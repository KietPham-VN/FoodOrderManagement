package com.example.controller;

import com.example.dao.CartDAO;
import com.example.dao.FoodDAO;
import com.example.dto.CartItem;
import com.example.dto.FoodItem;
import com.example.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/CartController")
public class CartController extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null)
        {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        if ("Checkout".equals(action))
        {
            try (Connection conn = DatabaseConnection.initializeDatabase())
            {
                FoodDAO foodDAO = new FoodDAO(conn);
                List<FoodItem> foodItems = foodDAO.getAllFoodItems();
                request.setAttribute("FoodItems", foodItems);
                request.getRequestDispatcher("views/checkout.jsp").forward(request, response);
            } catch (Exception ex)
            {
                Logger.getLogger(CartController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            try (Connection conn = DatabaseConnection.initializeDatabase())
            {
                CartDAO cartDAO = new CartDAO(conn);
                List<CartItem> cartItems = cartDAO.getCartItems(session);
                request.setAttribute("cartItems", cartItems);
                request.getRequestDispatcher("views/cart.jsp").forward(request, response);
            } catch (Exception e)
            {
                e.printStackTrace();
                request.setAttribute("ERROR", "Failed to load cart.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null)
        {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try (Connection conn = DatabaseConnection.initializeDatabase())
        {
            CartDAO cartDAO = new CartDAO(conn);

            if (null == action)
            {
                request.setAttribute("ERROR", "Invalid action.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            } else
            {
                switch (action)
                {
                    case "AddToCart":
                        handleAddToCart(request, response, session, cartDAO);
                        break;
                    case "UpdateCart":
                        handleUpdateCart(request, response, session, cartDAO);
                        break;
                    case "RemoveFromCart":
                        handleRemoveFromCart(request, response, session, cartDAO);
                        break;
                    default:
                        request.setAttribute("ERROR", "Invalid action.");
                        request.getRequestDispatcher("views/error.jsp").forward(request, response);
                        break;
                }
            }
        } catch (Exception e)
        {
            request.setAttribute("ERROR", "An error occurred while processing your request.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    private void handleAddToCart(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, CartDAO cartDAO) throws IOException, ServletException
    {
        try
        {
            String foodID = request.getParameter("foodID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cartDAO.addToCart(session, foodID, quantity);
            response.sendRedirect("CartController");
        } catch (NumberFormatException e)
        {
            request.setAttribute("ERROR", "Invalid quantity format.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        } catch (IOException | SQLException e)
        {
            request.setAttribute("ERROR", "Failed to add item to cart.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    private void handleUpdateCart(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, CartDAO cartDAO) throws IOException, ServletException
    {
        try
        {
            String foodID = request.getParameter("foodID");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // Log để kiểm tra tham số
            System.out.println("Updating cart: foodID=" + foodID + ", quantity=" + quantity);

            cartDAO.updateCart(session, foodID, quantity);

            // Chuyển hướng về trang CartController để tải lại giỏ hàng
            response.sendRedirect("CartController");
        } catch (NumberFormatException e)
        {
            request.setAttribute("ERROR", "Invalid quantity format.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        } catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("ERROR", "Failed to update cart.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

    private void handleRemoveFromCart(HttpServletRequest request, HttpServletResponse response,
            HttpSession session, CartDAO cartDAO) throws IOException, ServletException
    {
        try
        {
            String foodID = request.getParameter("foodID");

            // Log để kiểm tra tham số
            System.out.println("Removing from cart: foodID=" + foodID);

            cartDAO.removeFromCart(session, foodID);

            // Chuyển hướng về trang CartController để tải lại giỏ hàng
            response.sendRedirect("CartController");
        } catch (Exception e)
        {
            e.printStackTrace();
            request.setAttribute("ERROR", "Failed to remove item from cart.");
            request.getRequestDispatcher("views/error.jsp").forward(request, response);
        }
    }

}
