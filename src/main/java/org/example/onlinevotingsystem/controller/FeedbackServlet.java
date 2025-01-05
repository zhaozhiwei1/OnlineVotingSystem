package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.FeedbackDAO;
import org.example.onlinevotingsystem.model.Feedback;
import org.example.onlinevotingsystem.service.FeedbackService;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/SubmitFeedback")
public class FeedbackServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection connection = null;

        try {
            connection = DBUtil.getConnection();
            FeedbackDAO dao = new FeedbackDAO(connection);
            FeedbackService service = new FeedbackService(dao);

            // 从会话中获取当前登录用户的用户名
            HttpSession session = request.getSession();
            String feedbackUser = (String) session.getAttribute("username");
            if (feedbackUser == null) {
                out.write("<h3>请先登录，然后再提交反馈。</h3>");
                response.setHeader("Refresh", "2; URL=login.html");
                return;
            }

            // 获取表单参数
            String feedbackType = request.getParameter("feedbackType");
            String feedbackContent = request.getParameter("feedbackContent");
            String contactPhone = request.getParameter("contactPhone");

            // 构建反馈对象
            Feedback feedback = new Feedback();
            feedback.setFeedbackType(feedbackType);
            feedback.setFeedbackContent(feedbackContent);
            feedback.setContactPhone(contactPhone);
            feedback.setFeedbackUser(feedbackUser);

            // 提交反馈
            service.submitFeedback(feedback);

            out.write("<h3>反馈提交成功！</h3>");
            response.setHeader("Refresh", "2; URL=index.html");
        } catch (Exception e) {
            e.printStackTrace();
            out.write("<h3>提交失败：" + e.getMessage() + "</h3>");
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