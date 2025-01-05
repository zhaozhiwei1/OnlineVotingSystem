package org.example.onlinevotingsystem.controller;



import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet("/voting/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String result = userService.login(username, password);
        if (result.equals("登录成功")) {
            // 登录成功后，存储用户名到会话
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            resp.sendRedirect("index.html");
        } else {
            req.setAttribute("message", result);
            req.getRequestDispatcher("login.html").forward(req, resp);
        }
    }
}
