package org.example.onlinevotingsystem.controller;

import org.example.onlinevotingsystem.dao.DataBackupDAO;
import org.example.onlinevotingsystem.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ExportData")
public class ExportDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tableName = request.getParameter("tableName");

        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            DataBackupDAO dataBackupDAO = new DataBackupDAO(connection);

            // 获取表的所有列名
            List<String> columns = dataBackupDAO.getTableColumns(tableName);
            // 获取表的所有数据
            List<List<String>> data = dataBackupDAO.getTableData(tableName);

            // 设置响应头，告诉浏览器这是一个 CSV 文件
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + tableName + ".csv");

            // 写入 CSV 文件
            PrintWriter writer = response.getWriter();
            // 写入列名
            writer.println(String.join(",", columns));
            // 写入数据
            for (List<String> row : data) {
                writer.println(String.join(",", row));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("<script>alert('导出失败');</script>");
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