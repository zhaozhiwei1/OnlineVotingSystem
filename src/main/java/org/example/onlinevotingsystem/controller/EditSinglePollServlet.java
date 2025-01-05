package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.model.Poll;
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
import java.sql.Timestamp;
import java.util.Arrays;

@WebServlet("/EditSinglePoll")
public class EditSinglePollServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("id");
        String theme = request.getParameter("voteTheme");
        String description = request.getParameter("voteDescription");
        String[] options = request.getParameterValues("voteOptions[]");
        String optionsJson = Arrays.toString(options);
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        Poll poll = new Poll();
        poll.setId(id);
        poll.setTheme(theme);
        poll.setDescription(description);
        poll.setOptions(optionsJson);
        poll.setMaxChoices(1); // 单选投票，最大选择数为1
        poll.setMinChoices(1); // 单选投票，最小选择数为1
        poll.setStartTime(Timestamp.valueOf(startTimeStr.replace("T", " ") + ":00"));
        poll.setEndTime(Timestamp.valueOf(endTimeStr.replace("T", " ") + ":00"));
        poll.setType("single");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            boolean success = pollService.updatePoll(poll);
            if (success) {
                response.sendRedirect("vote-manage.jsp");
            } else {
                request.setAttribute("message", "更新失败");
                request.getRequestDispatcher("edit-single-poll.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "数据库错误: " + e.getMessage());
            request.getRequestDispatcher("edit-single-poll.jsp").forward(request, response);
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