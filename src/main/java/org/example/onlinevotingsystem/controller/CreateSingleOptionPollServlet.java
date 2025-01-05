package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.service.PollService;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

@MultipartConfig
@WebServlet("/CreateSingleOptionPoll")
public class CreateSingleOptionPollServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection connection = null;

        try {
            connection = DBUtil.getConnection();
            PollDAO dao = new PollDAO(connection);
            PollService service = new PollService(dao);

            HttpSession session = request.getSession();
            String creatorUsername = (String) session.getAttribute("username");
            if (creatorUsername == null) {
                out.write("<h3>请先登录，然后再创建投票。</h3>");
                response.setHeader("Refresh", "2; URL=login.html");
                return;
            }

            String id = request.getParameter("voteId");
            if (!service.isIdUnique(id)) {
                out.write("<h3>投票ID已被使用，请使用其他ID。</h3>");
                return;
            }

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
            poll.setCreatorUsername(creatorUsername);
            poll.setImagePath(null); // 图片上传功能暂未实现
            poll.setStatus("待开启");
            poll.setType("single");

            service.createPoll(poll);

            out.write("<h3>投票创建成功！</h3>");
            response.setHeader("Refresh", "2; URL=index.html");
        } catch (Exception e) {
            e.printStackTrace();
            out.write("<h3>创建失败：" + e.getMessage() + "</h3>");
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