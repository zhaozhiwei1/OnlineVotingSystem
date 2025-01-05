package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.service.PollService;
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

@WebServlet("/AdminDeletePoll")
public class AdminDeletePollServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pollId = request.getParameter("pollId");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            boolean success = pollService.deletePoll(pollId);
            if (success) {
                response.sendRedirect("AdminVoteManage");
            } else {
                response.getWriter().write("<script>alert('删除投票失败');</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('删除投票失败');</script>");
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