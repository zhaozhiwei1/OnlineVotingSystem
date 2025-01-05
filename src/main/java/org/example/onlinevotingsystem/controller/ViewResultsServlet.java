package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.VoteResult;
import org.example.onlinevotingsystem.service.PollService;
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

@WebServlet("/ViewResults")
public class ViewResultsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String pollId = request.getParameter("pollId");

        if (pollId == null) {
            response.sendRedirect("vote-manage.jsp");
            return;
        }

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            // 获取投票结果
            List<VoteResult> voteResults = pollService.getVoteResults(pollId);
            request.setAttribute("voteResults", voteResults);
            request.setAttribute("pollId", pollId);

            // 转发到查看结果的页面
            request.getRequestDispatcher("view-results.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('获取投票结果失败');</script>");
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