package com.example.controller;

import com.example.dao.CartDAO;
import com.example.dao.OrderDAO;
import com.example.dto.CartItem;
import com.example.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        try
        {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            Connection conn = DatabaseConnection.initializeDatabase();
            OrderDAO orderDAO = new OrderDAO(conn);
            CartDAO cartDAO = new CartDAO(conn);

            try
            {
                if ("Checkout".equals(action))
                {
                    List<CartItem> cartItems = cartDAO.getCartItems(session);
                    request.setAttribute("cartItems", cartItems);
                    request.getRequestDispatcher("views/checkout.jsp").forward(request, response);
                } else if ("PlaceOrder".equals(action))
                {
                    String userID = (String) session.getAttribute("userID");
                    double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
                    orderDAO.placeOrder(userID, totalAmount, session);
                    session.removeAttribute("cartItems");  // Clear the cart after placing order
                    response.sendRedirect("views/orderConfirmation.jsp");
                }
            } catch (IOException | NumberFormatException | SQLException | ServletException e)
            {
                e.printStackTrace();
                request.setAttribute("ERROR", "Failed to process order.");
                request.getRequestDispatcher("views/error.jsp").forward(request, response);
            }
        } catch (Exception ex)
        {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
