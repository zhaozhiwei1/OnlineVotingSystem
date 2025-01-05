package org.example.onlinevotingsystem.model;

import java.util.Date;

public class MultipleOptionPoll {
    private String id;
    private String theme;
    private String description;
    private String options; // 存储为JSON格式
    private int maxChoices;
    private int minChoices;
    private Date startTime;
    private Date endTime;
    private String creatorUsername;
    private String imagePath;
    private String status;
    private String type; // 新增属性

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOptions() { return options; }
    public void setOptions(String options) { this.options = options; }
    public int getMaxChoices() { return maxChoices; }
    public void setMaxChoices(int maxChoices) { this.maxChoices = maxChoices; }
    public int getMinChoices() { return minChoices; }
    public void setMinChoices(int minChoices) { this.minChoices = minChoices; }
    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }
    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }
    public String getCreatorUsername() { return creatorUsername; }
    public void setCreatorUsername(String creatorUsername) { this.creatorUsername = creatorUsername; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}