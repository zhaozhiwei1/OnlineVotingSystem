package org.example.onlinevotingsystem.dao;

import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.model.Vote;
import org.example.onlinevotingsystem.model.VoteResult;

import java.sql.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollDAO {

    private Connection connection;

    public PollDAO(Connection connection) {
        this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void savePoll(Poll poll) throws SQLException {
        String query = "INSERT INTO polls (id, theme, description, options, max_choices, min_choices, start_time, end_time, creator_username, image_path, status, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setString(12, poll.getType());
            ps.executeUpdate();
        }
    }
    public List<Poll> getPollsByCreator(String creatorUsername) throws SQLException {
        List<Poll> polls = new ArrayList<>();
        String query = "SELECT * FROM polls WHERE creator_username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, creatorUsername);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Poll poll = new Poll();
                poll.setId(rs.getString("id"));
                poll.setTheme(rs.getString("theme"));
                poll.setDescription(rs.getString("description"));
                poll.setOptions(rs.getString("options"));
                poll.setMaxChoices(rs.getInt("max_choices"));
                poll.setMinChoices(rs.getInt("min_choices"));
                poll.setStartTime(rs.getTimestamp("start_time"));
                poll.setEndTime(rs.getTimestamp("end_time"));
                poll.setCreatorUsername(rs.getString("creator_username"));
                poll.setImagePath(rs.getString("image_path"));
                poll.setStatus(rs.getString("status"));
                poll.setType(rs.getString("type"));
                polls.add(poll);
            }
        }
        return polls;
    }

    public boolean updatePoll(Poll poll) throws SQLException {
        String query = "UPDATE polls SET theme = ?, description = ?, options = ?, max_choices = ?, min_choices = ?, start_time = ?, end_time = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, poll.getTheme());
            ps.setString(2, poll.getDescription());
            ps.setString(3, poll.getOptions());
            ps.setInt(4, poll.getMaxChoices());
            ps.setInt(5, poll.getMinChoices());
            ps.setTimestamp(6, new java.sql.Timestamp(poll.getStartTime().getTime()));
            ps.setTimestamp(7, new java.sql.Timestamp(poll.getEndTime().getTime()));
            ps.setString(8, poll.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public Poll getPollById(String id) throws SQLException {
        String query = "SELECT * FROM polls WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Poll poll = new Poll();
                poll.setId(rs.getString("id"));
                poll.setTheme(rs.getString("theme"));
                poll.setDescription(rs.getString("description"));
                poll.setOptions(rs.getString("options"));
                poll.setMaxChoices(rs.getInt("max_choices"));
                poll.setMinChoices(rs.getInt("min_choices"));
                poll.setStartTime(rs.getTimestamp("start_time"));
                poll.setEndTime(rs.getTimestamp("end_time"));
                poll.setCreatorUsername(rs.getString("creator_username"));
                poll.setImagePath(rs.getString("image_path"));
                poll.setStatus(rs.getString("status"));
                poll.setType(rs.getString("type"));
                return poll;
            }
        }
        return null;
    }

    public boolean deletePoll(String id) throws SQLException {
        String query = "DELETE FROM polls WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Poll> getActivePolls(String searchQuery, String type) throws SQLException {
        List<Poll> polls = new ArrayList<>();
        String query = "SELECT * FROM polls WHERE status = '进行中' AND (id LIKE ? OR theme LIKE ?)";
        if (type != null && !type.equals("all")) {
            query += " AND type = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            if (type != null && !type.equals("all")) {
                ps.setString(3, type);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Poll poll = new Poll();
                poll.setId(rs.getString("id"));
                poll.setTheme(rs.getString("theme"));
                poll.setDescription(rs.getString("description"));
                poll.setOptions(rs.getString("options"));
                poll.setMaxChoices(rs.getInt("max_choices"));
                poll.setMinChoices(rs.getInt("min_choices"));
                poll.setStartTime(rs.getTimestamp("start_time"));
                poll.setEndTime(rs.getTimestamp("end_time"));
                poll.setCreatorUsername(rs.getString("creator_username"));
                poll.setImagePath(rs.getString("image_path"));
                poll.setStatus(rs.getString("status"));
                poll.setType(rs.getString("type"));
                polls.add(poll);
            }
        }
        return polls;
    }

    public void saveVote(Vote vote) throws SQLException {
        String query = "INSERT INTO votes (poll_id, voter_username, vote_option, vote_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, vote.getPollId());
            ps.setString(2, vote.getVoterUsername());
            ps.setString(3, vote.getVoteOption());
            ps.setTimestamp(4, vote.getVoteTime());
            ps.executeUpdate();
        }
    }

    public boolean isPollIdValid(String pollId) throws SQLException {
        String query = "SELECT id FROM polls WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }
    public boolean isIdUnique(String id) throws SQLException {
        String query = "SELECT COUNT(*) FROM polls WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) == 0; // 如果计数为0，表示ID唯一
        }
    }

    /*12.30*/
    public List<VoteResult> getVoteResults(String pollId) throws SQLException {
        List<VoteResult> results = new ArrayList<>();
        String query = "SELECT vote_option, COUNT(*) AS vote_count FROM votes WHERE poll_id = ? GROUP BY vote_option";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VoteResult result = new VoteResult();
                result.setVoteOption(rs.getString("vote_option"));
                result.setVoteCount(rs.getInt("vote_count"));
                results.add(result);
            }
        }
        return results;
    }

    public boolean hasUserVoted(String pollId, String voterUsername) throws SQLException {
        String query = "SELECT COUNT(*) FROM votes WHERE poll_id = ? AND voter_username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, pollId);
            ps.setString(2, voterUsername);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0; // 如果计数大于0，表示用户已经投过票
        }
    }

    public List<Poll> getAllPolls(String searchQuery) throws SQLException {
        List<Poll> polls = new ArrayList<>();
        String query = "SELECT * FROM polls WHERE id LIKE ? OR theme LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Poll poll = new Poll();
                poll.setId(rs.getString("id"));
                poll.setTheme(rs.getString("theme"));
                poll.setDescription(rs.getString("description"));
                poll.setOptions(rs.getString("options"));
                poll.setMaxChoices(rs.getInt("max_choices"));
                poll.setMinChoices(rs.getInt("min_choices"));
                poll.setStartTime(rs.getTimestamp("start_time")); // 获取 Timestamp 并转换为 Date
                poll.setEndTime(rs.getTimestamp("end_time")); // 获取 Timestamp 并转换为 Date
                poll.setCreatorUsername(rs.getString("creator_username"));
                poll.setImagePath(rs.getString("image_path"));
                poll.setStatus(rs.getString("status"));
                poll.setType(rs.getString("type"));
                polls.add(poll);
            }
        }
        return polls;
    }
    public boolean updatePollStatus(String pollId, String status) throws SQLException {
        String query = "UPDATE polls SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, pollId);
            return ps.executeUpdate() > 0;
        }
    }
    public List<Vote> getAllVotes(String searchQuery) throws SQLException {
        List<Vote> votes = new ArrayList<>();
        String query = "SELECT poll_id, voter_username, GROUP_CONCAT(vote_option SEPARATOR ', ') AS vote_options, MAX(vote_time) AS vote_time " +
                "FROM votes " +
                "WHERE poll_id LIKE ? OR voter_username LIKE ? " +
                "GROUP BY poll_id, voter_username";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, "%" + searchQuery + "%");
            ps.setString(2, "%" + searchQuery + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vote vote = new Vote();
                vote.setPollId(rs.getString("poll_id"));
                vote.setVoterUsername(rs.getString("voter_username"));
                vote.setVoteOption(rs.getString("vote_options")); // 合并的选项
                vote.setVoteTime(rs.getTimestamp("vote_time"));
                votes.add(vote);
            }
        }
        return votes;
    }
    public Map<String, Integer> getPollCreationData(String month) throws SQLException {
        Map<String, Integer> creationData = new HashMap<>();
        String query = "SELECT DATE(start_time) AS date, COUNT(*) AS count FROM polls WHERE DATE_FORMAT(start_time, '%Y-%m') = ? GROUP BY DATE(start_time)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                creationData.put(rs.getString("date"), rs.getInt("count"));
            }
        }

        // 补全缺失日期
        creationData = fillMissingDates(creationData, month);
        return creationData;
    }

    public Map<String, Integer> getPollParticipationData(String month) throws SQLException {
        Map<String, Integer> participationData = new HashMap<>();
        String query = "SELECT DATE(vote_time) AS date, COUNT(*) AS count FROM votes WHERE DATE_FORMAT(vote_time, '%Y-%m') = ? GROUP BY DATE(vote_time)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                participationData.put(rs.getString("date"), rs.getInt("count"));
            }
        }

        // 补全缺失日期
        participationData = fillMissingDates(participationData, month);
        return participationData;
    }

    public Map<String, Integer> getPollTypeData(String month) throws SQLException {
        Map<String, Integer> pollTypeData = new HashMap<>();
        String query = "SELECT type, COUNT(*) AS count FROM polls WHERE DATE_FORMAT(start_time, '%Y-%m') = ? GROUP BY type";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pollTypeData.put(rs.getString("type"), rs.getInt("count"));
            }
        }
        return pollTypeData;
    }
    private Map<String, Integer> fillMissingDates(Map<String, Integer> data, String month) {
        Map<String, Integer> filledData = new HashMap<>();
        int year = Integer.parseInt(month.split("-")[0]);
        int monthValue = Integer.parseInt(month.split("-")[1]);
        int daysInMonth = YearMonth.of(year, monthValue).lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            String date = String.format("%04d-%02d-%02d", year, monthValue, day);
            filledData.put(date, data.getOrDefault(date, 0));
        }

        return filledData;
    }
}