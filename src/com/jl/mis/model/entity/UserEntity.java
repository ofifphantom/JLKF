package com.jl.mis.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class UserEntity implements Serializable {
    private Integer id;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String loginName;
    private String loginPassword;
    private String userSeatNumber;
    private Integer userTypeId;
    private Integer userStatusId;
    private Integer userGroup;
    private Integer administratorOrUser;
    private Timestamp createTime;
    private Integer isForbidden;
    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getUserSeatNumber() {
        return userSeatNumber;
    }

    public void setUserSeatNumber(String userSeatNumber) {
        this.userSeatNumber = userSeatNumber;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Integer getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Integer userStatusId) {
        this.userStatusId = userStatusId;
    }

    public Integer getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
    }

    public Integer getAdministratorOrUser() {
        return administratorOrUser;
    }

    public void setAdministratorOrUser(Integer administratorOrUser) {
        this.administratorOrUser = administratorOrUser;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(Integer isForbidden) {
        this.isForbidden = isForbidden;
    }


}
