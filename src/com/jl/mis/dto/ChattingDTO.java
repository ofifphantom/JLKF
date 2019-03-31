package com.jl.mis.dto;

/**
 * 聊天记录
 */
public class ChattingDTO {
    private String startTime;
    private String records;
    private Integer sender;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }
}
