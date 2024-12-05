package com.example.controller;

import Consts.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class MainController extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        String url = URL.LOGIN_PAGE;
        try
        {
            switch (action)
            {
                case "Login":
                    url = "LoginController";
                    break;
                case "Register":
                    url = "RegisterController";
                    break;
                case "ViewFoods":
                    url = "FoodController";
                    break;
                case "AddToCart":
                    url = "CartController";
                    break;
                case "PlaceOrder":
                    url = "OrderController";
                    break;
                case "Checkout":
                    url = "CartController";
                    break;
                default:
                    request.setAttribute("ERROR", "Action not supported.");
            }
        } catch (Exception e)
        {
            log("Error at MainController: " + e.toString());
        } finally
        {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }
}
