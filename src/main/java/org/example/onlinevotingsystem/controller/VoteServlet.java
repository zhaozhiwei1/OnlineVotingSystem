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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String pollId = request.getParameter("pollId");
        String pollType = request.getParameter("pollType");

        if (pollId == null || pollType == null) {
            response.sendRedirect("vote-participation.jsp");
            return;
        }

        HttpSession session = request.getSession();
        String voterUsername = (String) session.getAttribute("username");

        if (voterUsername == null) {
            response.sendRedirect("login.html");
            return;
        }

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            Poll poll = pollService.getPollById(pollId);
            if (poll != null) {
                String[] options = poll.getOptions().replace("[", "").replace("]", "").split(",");
                request.setAttribute("pollId", pollId);
                request.setAttribute("pollType", pollType);
                request.setAttribute("options", options);
                request.setAttribute("maxChoices", poll.getMaxChoices()); // 传递最大选择数

                // 检查用户是否已经投过票
                boolean hasVoted = pollService.hasUserVoted(pollId, voterUsername);
                request.setAttribute("hasVoted", hasVoted);

                if ("single".equals(pollType)) {
                    request.getRequestDispatcher("vote-single.jsp").forward(request, response);
                } else if ("multiple".equals(pollType)) {
                    request.getRequestDispatcher("vote-multiple.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("vote-participation.jsp");
            }
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String pollId = request.getParameter("pollId");
        String[] voteOptions = request.getParameterValues("voteOption");

        // 验证数据
        if (pollId == null || voteOptions == null || voteOptions.length == 0) {
            response.getWriter().write("<script>alert('请选择投票选项');</script>");
            return;
        }

        HttpSession session = request.getSession();
        String voterUsername = (String) session.getAttribute("username");

        if (voterUsername == null) {
            response.sendRedirect("login.html");
            return;
        }

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            // 检查投票是否存在
            if (!pollService.isPollIdValid(pollId)) {
                response.getWriter().write("<script>alert('投票不存在');</script>");
                return;
            }

            // 检查用户是否已经投过票
            if (pollService.hasUserVoted(pollId, voterUsername)) {
                response.getWriter().write("<script>alert('您已经投过票了，不能重复投票');</script>");
                return;
            }

            // 获取投票的最大选择数
            Poll poll = pollService.getPollById(pollId);
            if (poll == null) {
                response.getWriter().write("<script>alert('投票不存在');</script>");
                return;
            }

            int maxChoices = poll.getMaxChoices();

            // 检查用户选择的选项数量是否超过最大选择数
            if (voteOptions.length > maxChoices) {
                response.getWriter().write("<script>alert('选择的选项数量超过最大限制');</script>");
                return;
            }

            // 保存投票记录
            for (String voteOption : voteOptions) {
                Vote vote = new Vote();
                vote.setPollId(pollId);
                vote.setVoterUsername(voterUsername);
                vote.setVoteOption(voteOption.trim()); // 去除空格
                vote.setVoteTime(new Timestamp(System.currentTimeMillis()));
                pollService.saveVote(vote);
            }

            response.sendRedirect("vote-participation.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('投票失败: " + e.getMessage() + "');</script>");
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