package com.example.controller;

import Consts.URL;
import com.example.dao.UserDAO;
import com.example.dto.User;
import com.example.util.DatabaseConnection;
import com.example.util.Validator;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RegisterController", urlPatterns
        = {
            "/RegisterController"
        })
public class RegisterController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String msg = "Something went wrong";
        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            String validationError = Validator.validateUser(userID, fullName, email, phoneNumber, password, confirmPassword);

            Connection conn = DatabaseConnection.initializeDatabase();
            UserDAO userDAO = new UserDAO(conn);

            if (userDAO.isUserExist(userID)) {
                request.setAttribute("ERROR", "User ID already exists. Please choose another.");
                request.getRequestDispatcher(URL.REGISTER_PAGE).forward(request, response);  // Quay lại trang đăng ký với lỗi
                return;
            }

            if (validationError != null) {
                request.setAttribute("ERROR", validationError);
                request.getRequestDispatcher(URL.REGISTER_PAGE).forward(request, response);  // Quay lại trang đăng ký với lỗi
                return;
            }

            User user = new User(userID, fullName, email, phoneNumber, roleID, password);
            if (userDAO.createUser(user)) {
                response.sendRedirect(request.getContextPath() + URL.LOGIN_PAGE);  // Chuyển tới trang đăng nhập nếu thành công
            } else {
                request.setAttribute("ERROR", "Failed to create user. Please try again.");
                request.getRequestDispatcher(URL.REGISTER_PAGE).forward(request, response);  // Nếu không tạo được user, quay lại trang đăng ký
            }
        } catch (Exception e) {
            log("Error at RegisterController: " + e.getMessage(), e);
            request.setAttribute("ERROR", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher(URL.REGISTER_PAGE).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
