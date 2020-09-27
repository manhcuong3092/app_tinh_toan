package com.learn.tinhtoan.model;

public class Task{

    public static final int FINISHED = 1;
    public static final int UNFINISHED = 0;

    private int id;
    private int userId;
    private String content;
    private int status;

    public Task(int id, int userId, String content, int status) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
