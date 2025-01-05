package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/BlockUser")
public class BlockUserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        boolean success = userService.blockUser(userId);
        if (success) {
            response.sendRedirect("UserManage");
        } else {
            response.getWriter().write("<script>alert('拉黑用户失败');</script>");
        }
    }
}