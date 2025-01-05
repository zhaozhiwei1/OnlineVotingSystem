package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.model.VoteResult;
import org.example.onlinevotingsystem.service.PollService;
import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/VoteManage")
public class VoteManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String creatorUsername = (String) session.getAttribute("username");
        if (creatorUsername == null) {
            response.sendRedirect("login.html");
            return;
        }

        String action = request.getParameter("action");
        String pollId = request.getParameter("pollId");
        String pollType = request.getParameter("pollType");

        if ("edit".equals(action) && pollId != null && pollType != null) {
            // 处理编辑请求
            handleEditRequest(request, response, pollId, pollType);
            return;
        } else if ("viewResults".equals(action) && pollId != null) {
            // 处理查看结果请求
            handleViewResultsRequest(request, response, pollId);
            return;
        }

        // 默认显示投票列表
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            // 获取搜索关键字
            String searchQuery = request.getParameter("searchQuery");

            // 获取当前用户创建的投票
            List<Poll> polls = pollService.getPollsByCreator(creatorUsername);

            // 构建 HTML 表格内容
            StringBuilder voteTableContent = new StringBuilder();
            for (Poll poll : polls) {
                if (searchQuery == null || poll.getId().contains(searchQuery) || poll.getTheme().contains(searchQuery)) {
                    voteTableContent.append("<tr>")
                            .append("<td>").append(poll.getId()).append("</td>")
                            .append("<td>").append(poll.getTheme()).append("</td>")
                            .append("<td>").append(poll.getCreatorUsername()).append("</td>")
                            .append("<td>").append(poll.getStartTime()).append("</td>")
                            .append("<td>").append(poll.getStatus()).append("</td>")
                            .append("<td>").append("single".equals(poll.getType()) ? "单选投票" : "多选投票").append("</td>")
                            .append("<td>")
                            .append("<div class='action-buttons'>")
                            .append("<a href='VoteManage?action=edit&pollId=").append(poll.getId()).append("&pollType=").append(poll.getType()).append("' class='action-btn edit-btn'>编辑</a> ")
                            .append("<a href='javascript:void(0);' onclick=\"confirmDelete('").append(poll.getId()).append("', '").append(poll.getType()).append("')\" class='action-btn delete-btn'>删除</a> ")
                            .append("<a href='VoteManage?action=viewResults&pollId=").append(poll.getId()).append("' class='action-btn view-btn'>查看结果</a>")
                            .append("</div>")
                            .append("</td>")
                            .append("</tr>");
                }
            }

            // 将表格内容设置到请求属性中
            request.setAttribute("voteTableContent", voteTableContent.toString());

            // 转发到 vote-manage.jsp
            request.getRequestDispatcher("vote-manage.jsp").forward(request, response);
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

    /**
     * 处理编辑请求
     */
    private void handleEditRequest(HttpServletRequest request, HttpServletResponse response, String pollId, String pollType) throws ServletException, IOException {
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            if ("single".equals(pollType)) {
                // 获取单选投票数据
                Poll poll = pollService.getPollById(pollId);
                if (poll != null) {
                    request.setAttribute("poll", poll);
                    request.getRequestDispatcher("edit-single-poll.jsp").forward(request, response);
                } else {
                    response.sendRedirect("vote-manage.jsp");
                }
            } else if ("multiple".equals(pollType)) {
                // 获取多选投票数据
                Poll poll = pollService.getPollById(pollId);
                if (poll != null) {
                    request.setAttribute("poll", poll);
                    request.getRequestDispatcher("edit-multiple-poll.jsp").forward(request, response);
                } else {
                    response.sendRedirect("vote-manage.jsp");
                }
            } else {
                response.sendRedirect("vote-manage.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("vote-manage.jsp");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    /**
     * 处理查看结果请求
     */
    private void handleViewResultsRequest(HttpServletRequest request, HttpServletResponse response, String pollId) throws ServletException, IOException {
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if ("updateStatus".equals(action)) {
            String pollId = request.getParameter("pollId");
            String status = request.getParameter("status");

            Connection connection = null;
            try {
                connection = DBUtil.getConnection();
                PollDAO pollDAO = new PollDAO(connection);
                PollService pollService = new PollService(pollDAO);

                boolean success = pollService.updatePollStatus(pollId, status);
                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
}