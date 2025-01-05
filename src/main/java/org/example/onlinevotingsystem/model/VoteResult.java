package org.example.onlinevotingsystem.model;

public class VoteResult {
    private String voteOption;
    private int voteCount;

    // Getters and Setters
    public String getVoteOption() {
        return voteOption;
    }

    public void setVoteOption(String voteOption) {
        this.voteOption = voteOption;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}