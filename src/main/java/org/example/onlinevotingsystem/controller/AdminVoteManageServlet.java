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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/AdminVoteManage")
public class AdminVoteManageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            PollDAO pollDAO = new PollDAO(connection);
            PollService pollService = new PollService(pollDAO);

            // 获取搜索关键字
            String searchQuery = request.getParameter("searchQuery");

            // 获取所有投票
            List<Poll> polls = pollService.getAllPolls(searchQuery);

            // 定义时间格式化器，确保过期时间与结束时间格式一致
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 构建 HTML 表格内容
            StringBuilder voteTableContent = new StringBuilder();
            for (Poll poll : polls) {
                if (searchQuery == null || poll.getId().contains(searchQuery) || poll.getTheme().contains(searchQuery)) {
                    // 计算过期时间（结束时间 + 三个月）
                    Date expirationDate = calculateExpirationDate(poll.getEndTime());
                    boolean isExpired = isPollExpired(poll.getEndTime());

                    // 格式化过期时间
                    String formattedExpirationDate = dateFormat.format(expirationDate);

                    // 判断投票是否开启
                    boolean isActive = "进行中".equals(poll.getStatus());

                    voteTableContent.append("<tr>")
                            .append("<td>").append(poll.getId()).append("</td>")
                            .append("<td>").append(poll.getTheme()).append("</td>")
                            .append("<td>").append(poll.getCreatorUsername()).append("</td>")
                            .append("<td>").append(dateFormat.format(poll.getStartTime())).append("</td>")
                            .append("<td>").append(dateFormat.format(poll.getEndTime())).append("</td>")
                            .append("<td class='").append(isExpired ? "expired" : "").append("'>").append(formattedExpirationDate).append("</td>")
                            .append("<td>").append(isExpired ? "已过期" : poll.getStatus()).append("</td>")
                            .append("<td>").append("single".equals(poll.getType()) ? "单选投票" : "多选投票").append("</td>")
                            .append("<td>")
                            .append("<div class='action-buttons'>")
                            .append("<label class='toggle-switch'>")
                            .append("<input type='checkbox' ").append(isActive ? "checked" : "").append(" onchange=\"togglePollStatus('").append(poll.getId()).append("', ").append(isActive).append(")\">")
                            .append("<span class='slider'></span>")
                            .append("</label>")
                            .append("<a href='javascript:void(0);' onclick=\"confirmDelete('").append(poll.getId()).append("')\" class='action-btn delete-btn'>删除</a>")
                            .append("</div>")
                            .append("</td>")
                            .append("</tr>");
                }
            }

            // 将表格内容设置到请求属性中
            request.setAttribute("voteTableContent", voteTableContent.toString());

            // 转发到 admin-vote-manage.jsp
            request.getRequestDispatcher("admin-vote-manage.jsp").forward(request, response);
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
     * 计算过期时间（结束时间 + 三个月）
     */
    private Date calculateExpirationDate(Date endTime) {
        long threeMonthsInMillis = 3L * 30 * 24 * 60 * 60 * 1000; // 三个月的毫秒数
        return new Date(endTime.getTime() + threeMonthsInMillis);
    }

    /**
     * 判断投票是否过期（当前时间是否超过过期时间）
     */
    private boolean isPollExpired(Date endTime) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = endTime.getTime() + 3L * 30 * 24 * 60 * 60 * 1000; // 过期时间 = 结束时间 + 三个月
        return currentTime > expirationTime;
    }
}