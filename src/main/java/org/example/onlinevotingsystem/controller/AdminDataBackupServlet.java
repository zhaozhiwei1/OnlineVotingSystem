package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.DataBackupDAO;
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

@WebServlet("/AdminDataBackup")
public class AdminDataBackupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String tableName = request.getParameter("tableName");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            DataBackupDAO dataBackupDAO = new DataBackupDAO(connection);

            if (tableName != null && !tableName.isEmpty()) {
                // 获取表的所有列名
                List<String> columns = dataBackupDAO.getTableColumns(tableName);
                // 获取表的所有数据
                List<List<String>> data = dataBackupDAO.getTableData(tableName);

                request.setAttribute("selectedTable", tableName);
                request.setAttribute("tableColumns", columns);
                request.setAttribute("tableData", data);
            }

            // 转发到 admin-data-backup.jsp
            request.getRequestDispatcher("admin-data-backup.jsp").forward(request, response);
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