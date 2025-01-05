package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.model.User;
import org.example.onlinevotingsystem.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/UserManage")
public class UserManageServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchQuery = request.getParameter("searchQuery");

        List<User> users = userService.getAllUsers(searchQuery);

        StringBuilder userTableContent = new StringBuilder();
        for (User user : users) {
            userTableContent.append("<tr>")
                    .append("<td>").append(user.getId()).append("</td>")
                    .append("<td>").append(user.getUsername()).append("</td>")
                    .append("<td>").append(user.getEmail()).append("</td>")
                    .append("<td>").append(user.isLocked() ? "已拉黑" : "正常").append("</td>")
                    .append("<td>")
                    .append("<div class='action-buttons'>")
                    .append("<a href='javascript:void(0);' onclick=\"confirmResetPassword('").append(user.getId()).append("')\" class='action-btn reset-btn'>重置密码</a> ")
                    .append("<a href='javascript:void(0);' onclick=\"confirmBlockUser('").append(user.getId()).append("')\" class='action-btn block-btn'>拉黑用户</a> ")
                    .append("<a href='javascript:void(0);' onclick=\"confirmUnblockUser('").append(user.getId()).append("')\" class='action-btn unblock-btn'>解封用户</a>")
                    .append("</div>")
                    .append("</td>")
                    .append("</tr>");
        }

        request.setAttribute("userTableContent", userTableContent.toString());
        request.getRequestDispatcher("user-manage.jsp").forward(request, response);
    }
}