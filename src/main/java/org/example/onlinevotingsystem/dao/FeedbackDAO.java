package org.example.onlinevotingsystem.dao;

import org.example.onlinevotingsystem.model.Feedback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    private Connection connection;

    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(Feedback feedback) throws SQLException {
        String query = "INSERT INTO feedback (feedback_type, feedback_content, contact_phone, feedback_user) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, feedback.getFeedbackType());
            ps.setString(2, feedback.getFeedbackContent());
            ps.setString(3, feedback.getContactPhone());
            ps.setString(4, feedback.getFeedbackUser());
            ps.executeUpdate();
        }
    }
    public List<Feedback> getAllFeedbacks(String searchQuery) throws SQLException {
        List<Feedback> feedbacks = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE feedback_content LIKE ? OR feedback_user LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setFeedbackType(rs.getString("feedback_type"));
                feedback.setFeedbackContent(rs.getString("feedback_content"));
                feedback.setContactPhone(rs.getString("contact_phone"));
                feedback.setFeedbackUser(rs.getString("feedback_user"));
                feedback.setFeedbackTime(rs.getTimestamp("feedback_time"));
                feedbacks.add(feedback);
            }
        }
        return feedbacks;
    }
}