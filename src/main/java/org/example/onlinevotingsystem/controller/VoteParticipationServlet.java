package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.service.PollService;
import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VoteParticipation")
public class VoteParticipationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String searchQuery = request.getParameter("searchQuery");
        String pollType = request.getParameter("pollType");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            List<Poll> polls = pollService.getActivePolls(searchQuery, pollType);

            request.setAttribute("singleOptionPolls", polls.stream().filter(p -> "single".equals(p.getType())).toList());
            request.setAttribute("multipleOptionPolls", polls.stream().filter(p -> "multiple".equals(p.getType())).toList());
            request.getRequestDispatcher("vote-participation.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('数据获取失败');</script>");
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