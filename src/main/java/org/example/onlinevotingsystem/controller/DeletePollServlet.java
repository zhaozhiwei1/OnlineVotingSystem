package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/DeletePoll")
public class DeletePollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String pollId = request.getParameter("pollId");
        String pollType = request.getParameter("pollType");

        if (pollId == null || pollType == null) {
            response.sendRedirect("vote-manage.jsp");
            return;
        }

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);

            boolean success = pollDAO.deletePoll(pollId);
            if (success) {
                response.sendRedirect("vote-manage.jsp");
            } else {
                request.setAttribute("message", "删除失败");
                request.getRequestDispatcher("vote-manage.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "数据库错误: " + e.getMessage());
            request.getRequestDispatcher("vote-manage.jsp").forward(request, response);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }
}