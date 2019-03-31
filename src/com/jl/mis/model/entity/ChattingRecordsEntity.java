package com.jl.mis.model.entity;

import javax.xml.soap.Text;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class ChattingRecordsEntity implements Serializable {
    private Integer id;
    private Integer clientId;
    private Timestamp startTime;
    private String records;
    private Integer sender;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
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
