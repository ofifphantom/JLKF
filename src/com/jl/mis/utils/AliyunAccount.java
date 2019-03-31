package com.jl.mis.utils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author edward
 * @date 2017/11/12
 */
public class AliyunAccount {
    /**
     * CRM用户和阿里云账户映射关系ID
     */
    private Long id;

    /**
     * CRM用户ID
     */
    private Long userId;

    /**
     * 阿里云账户OAuth2 Access Code
     */
    private String accessCode;

    /**
     * 该映射关系创建时间
     */
    private Timestamp gmtCreate;

    /**
     * 该映射关系最后修改时间
     */
    private Timestamp gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }
}
