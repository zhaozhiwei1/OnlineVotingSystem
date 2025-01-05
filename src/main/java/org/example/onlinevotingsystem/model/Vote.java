package org.example.onlinevotingsystem.model;

import java.sql.Timestamp;

public class Vote {
    private int id;
    private String pollId;
    private String pollType;
    private String voterUsername;
    private String voteOption;
    private Timestamp voteTime;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPollId() { return pollId; }
    public void setPollId(String pollId) { this.pollId = pollId; }
    public String getPollType() { return pollType; }
    public void setPollType(String pollType) { this.pollType = pollType; }
    public String getVoterUsername() { return voterUsername; }
    public void setVoterUsername(String voterUsername) { this.voterUsername = voterUsername; }
    public String getVoteOption() { return voteOption; }
    public void setVoteOption(String voteOption) { this.voteOption = voteOption; }
    public Timestamp getVoteTime() { return voteTime; }
    public void setVoteTime(Timestamp voteTime) { this.voteTime = voteTime; }
}