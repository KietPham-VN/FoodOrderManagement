package com.example.controller;

import Consts.URL;
import com.example.dao.UserDAO;
import com.example.util.DatabaseConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "LoginController", urlPatterns
        = {
            "/LoginController"
        })
public class LoginController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String password = request.getParameter("password");
        try {
            Connection conn = DatabaseConnection.initializeDatabase();
            UserDAO userDAO = new UserDAO(conn);
            boolean isAuthenticated = userDAO.authenticateUser(userID, password);
            boolean isAdmin = userDAO.checkAdmin(userID, password);

            if (isAuthenticated) {
                if (isAdmin) {
                    HttpSession session = request.getSession();
                    session.setAttribute("userID", userID);
                    session.setAttribute("role", "admin");
                    response.sendRedirect("MainController?action=ViewFoods");
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("userID", userID);
                    response.sendRedirect("MainController?action=AddToCart");
                }
            } else {
                request.setAttribute("ERROR", "Invalid credentials. Please try again.");
                request.getRequestDispatcher(URL.LOGIN_PAGE).forward(request, response);
            }
        } catch (Exception e) {
            log(e.getMessage());
            request.getRequestDispatcher(URL.LOGIN_PAGE).forward(request, response);
        }
    }
}
