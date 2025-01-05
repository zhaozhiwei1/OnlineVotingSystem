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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AdminFeedbackManage")
public class AdminFeedbackManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            FeedbackDAO feedbackDAO = new FeedbackDAO(connection);
            FeedbackService feedbackService = new FeedbackService(feedbackDAO);

            // 获取搜索关键字
            String searchQuery = request.getParameter("searchQuery");

            // 获取所有反馈
            List<Feedback> feedbacks = feedbackService.getAllFeedbacks(searchQuery);

            // 构建 HTML 表格内容
            StringBuilder feedbackTableContent = new StringBuilder();
            for (Feedback feedback : feedbacks) {
                feedbackTableContent.append("<tr>")
                        .append("<td>").append(feedback.getId()).append("</td>")
                        .append("<td>").append(feedback.getFeedbackType()).append("</td>")
                        .append("<td>").append(feedback.getFeedbackContent()).append("</td>")
                        .append("<td>").append(feedback.getContactPhone()).append("</td>")
                        .append("<td>").append(feedback.getFeedbackUser()).append("</td>")
                        .append("<td>").append(feedback.getFeedbackTime()).append("</td>")
                        .append("</tr>");
            }

            // 将表格内容设置到请求属性中
            request.setAttribute("feedbackTableContent", feedbackTableContent.toString());

            // 转发到 admin-feedback.jsp
            request.getRequestDispatcher("admin-feedback.jsp").forward(request, response);
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