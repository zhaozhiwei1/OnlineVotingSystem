package org.example.onlinevotingsystem.dao;

import org.example.onlinevotingsystem.model.MultipleOptionPoll;
import org.example.onlinevotingsystem.util.DBUtil;

import java.sql.*;

public class MultipleOptionPollDAO {
    private Connection connection;

    public MultipleOptionPollDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * 检查投票ID是否唯一（联合查询单选投票和多选投票表）
     *
     * @param id 投票ID
     * @return 如果ID唯一返回true，否则返回false
     * @throws SQLException 数据库异常
     */
    public boolean isIdUnique(String id) throws SQLException {
        String query = "SELECT COUNT(*) FROM (SELECT id FROM singleoptionpoll UNION ALL SELECT id FROM multipleoptionpoll) AS combined WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 0; // 如果计数为0，表示ID唯一
        }
    }

    /**
     * 保存多选投票到数据库
     *
     * @param poll 多选投票对象
     * @throws SQLException 数据库异常
     */
    public void save(MultipleOptionPoll poll) throws SQLException {
        String query = "INSERT INTO multipleoptionpoll (id, theme, description, options, max_choices, min_choices, start_time, end_time, creator_username, image_path, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, poll.getId());
            ps.setString(2, poll.getTheme());
            ps.setString(3, poll.getDescription());
            ps.setString(4, poll.getOptions());
            ps.setInt(5, poll.getMaxChoices());
            ps.setInt(6, poll.getMinChoices());
            ps.setTimestamp(7, new Timestamp(poll.getStartTime().getTime()));
            ps.setTimestamp(8, new Timestamp(poll.getEndTime().getTime()));
            ps.setString(9, poll.getCreatorUsername());
            ps.setString(10, poll.getImagePath());
            ps.setString(11, poll.getStatus());
            ps.executeUpdate();
        }
    }
}