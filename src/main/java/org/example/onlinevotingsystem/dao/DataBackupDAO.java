package org.example.onlinevotingsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBackupDAO {
    private Connection connection;

    public DataBackupDAO(Connection connection) {
        this.connection = connection;
    }

    public List<String> getTableColumns(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        String query = "SELECT column_name FROM information_schema.columns WHERE table_name = ? AND table_schema = DATABASE() ORDER BY ordinal_position";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                columns.add(rs.getString("column_name"));
            }
        }
        return columns;
    }

    public List<List<String>> getTableData(String tableName) throws SQLException {
        List<List<String>> data = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        }
        return data;
    }
}