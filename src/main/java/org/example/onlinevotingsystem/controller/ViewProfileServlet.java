package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.model.User;
import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ViewProfile")
public class ViewProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            response.sendRedirect("login.html");
            return;
        }

        UserService userService = new UserService();
        User user = userService.getUserByUsername(username);
        request.setAttribute("user", user);
        request.getRequestDispatcher("viewProfile.jsp").forward(request, response);
    }
}