package org.example.onlinevotingsystem.service;

import org.example.onlinevotingsystem.dao.PollDAO;
import org.example.onlinevotingsystem.model.Poll;
import org.example.onlinevotingsystem.model.Vote;
import org.example.onlinevotingsystem.model.VoteResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class PollService {

    private PollDAO pollDAO;

    public PollService(PollDAO pollDAO) {
        this.pollDAO = pollDAO;
    }

    // 创建投票
    public void createPoll(Poll poll) throws SQLException {
        pollDAO.savePoll(poll);
    }

    // 获取创建者的投票列表
    public List<Poll> getPollsByCreator(String creatorUsername) throws SQLException {
        return pollDAO.getPollsByCreator(creatorUsername);
    }

    // 获取活跃的投票列表
    public List<Poll> getActivePolls(String searchQuery, String type) throws SQLException {
        return pollDAO.getActivePolls(searchQuery, type);
    }

    // 根据ID获取投票
    public Poll getPollById(String id) throws SQLException {
        return pollDAO.getPollById(id);
    }

    // 更新投票
    public boolean updatePoll(Poll poll) throws SQLException {
        return pollDAO.updatePoll(poll);
    }

    // 删除投票
    public boolean deletePoll(String id) throws SQLException {
        return pollDAO.deletePoll(id);
    }

    // 保存投票记录
    public void saveVote(Vote vote) throws SQLException {
        pollDAO.saveVote(vote);
    }

    // 检查投票ID是否有效
    public boolean isPollIdValid(String pollId) throws SQLException {
        return pollDAO.isPollIdValid(pollId);
    }

    // 检查投票ID是否唯一
    public boolean isIdUnique(String id) throws SQLException {
        return pollDAO.isIdUnique(id);
    }

    // 获取投票结果
    public List<VoteResult> getVoteResults(String pollId) throws SQLException {
        return pollDAO.getVoteResults(pollId);
    }

    // 检查用户是否已经投票
    public boolean hasUserVoted(String pollId, String voterUsername) throws SQLException {
        return pollDAO.hasUserVoted(pollId, voterUsername);
    }

    // 获取所有投票
    public List<Poll> getAllPolls(String searchQuery) throws SQLException {
        return pollDAO.getAllPolls(searchQuery);
    }

    // 更新投票状态
    public boolean updatePollStatus(String pollId, String status) throws SQLException {
        return pollDAO.updatePollStatus(pollId, status);
    }

    // 获取所有投票记录
    public List<Vote> getAllVotes(String searchQuery) throws SQLException {
        return pollDAO.getAllVotes(searchQuery);
    }
}