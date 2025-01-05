package org.example.onlinevotingsystem.controller;



import org.example.onlinevotingsystem.model.User;
import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/voting/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        boolean success = userService.register(user);
        if (success) {
            resp.sendRedirect("login.html"); // 注册成功，跳转到登录页面
        } else {
            req.setAttribute("message", "注册失败"); // 注册失败，返回错误信息
            req.getRequestDispatcher("register.html").forward(req, resp);
        }
    }
}