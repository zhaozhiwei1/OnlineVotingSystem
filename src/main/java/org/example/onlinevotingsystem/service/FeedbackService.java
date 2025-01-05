package org.example.onlinevotingsystem.service;

import org.example.onlinevotingsystem.dao.FeedbackDAO;
import org.example.onlinevotingsystem.model.Feedback;

import java.sql.SQLException;
import java.util.List;

public class FeedbackService {
    private FeedbackDAO dao;

    public FeedbackService(FeedbackDAO dao) {
        this.dao = dao;
    }
    public void submitFeedback(Feedback feedback) throws SQLException {
        dao.save(feedback);
    }

    public List<Feedback> getAllFeedbacks(String searchQuery) throws SQLException {
        return dao.getAllFeedbacks(searchQuery);
    }
}