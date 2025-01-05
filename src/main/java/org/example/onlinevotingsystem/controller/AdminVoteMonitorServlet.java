package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.model.Vote;
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

@WebServlet("/AdminVoteMonitor")
public class AdminVoteMonitorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String searchQuery = request.getParameter("searchQuery");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            // 获取所有投票记录（已合并）
            List<Vote> votes = pollService.getAllVotes(searchQuery);

            // 构建 HTML 表格内容
            StringBuilder voteMonitorTableContent = new StringBuilder();
            for (Vote vote : votes) {
                // 获取投票主题
                Poll poll = pollService.getPollById(vote.getPollId());
                String pollTheme = poll != null ? poll.getTheme() : "未知";

                voteMonitorTableContent.append("<tr>")
                        .append("<td>").append(vote.getPollId()).append("</td>")
                        .append("<td>").append(pollTheme).append("</td>")
                        .append("<td>").append(vote.getVoterUsername()).append("</td>")
                        .append("<td>").append(vote.getVoteOption()).append("</td>")
                        .append("<td>").append(vote.getVoteTime()).append("</td>")
                        .append("</tr>");
            }

            // 将表格内容设置到请求属性中
            request.setAttribute("voteMonitorTableContent", voteMonitorTableContent.toString());

            // 转发到 admin-vote-monitor.jsp
            request.getRequestDispatcher("admin-vote-monitor.jsp").forward(request, response);
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