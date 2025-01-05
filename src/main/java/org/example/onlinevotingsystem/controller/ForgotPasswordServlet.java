package org.example.onlinevotingsystem.controller;



import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/voting/reset-password")
public class ForgotPasswordServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String newPassword = req.getParameter("newPassword");

        boolean success = userService.resetPassword(username,email, newPassword);
        if (success) {
            resp.sendRedirect("login.html"); // 密码重置成功，跳转到登录页面
        } else {
            req.setAttribute("message", "密码重置失败"); // 密码重置失败，返回错误信息
            req.getRequestDispatcher("forgot_password.html").forward(req, resp);
        }
    }
}

