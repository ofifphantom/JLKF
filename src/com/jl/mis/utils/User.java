package com.jl.mis.utils;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author edward
 * @date 2017/11/12
 */
@Entity
@Table(name = "user")
public class User {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码，明文
     */
    private String password;

    /**
     * 阿里云账户绑定标志
     */
    private Boolean isAliyunBound;

    /**
     * 阿里云账户访问令牌
     */
    private TokenPackage tokePackage;

    /**
     * 该用户创建时间
     */
    private Timestamp gmtCreate;

    /**
     * 该用户信息最后修改时间
     */
    private Timestamp gmtModified;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public Boolean isAliyunBound() {
        return isAliyunBound;
    }

    public void setAliyunBound(Boolean aliyunBound) {
        isAliyunBound = aliyunBound;
    }

    /**
     * 此处仅为演示，生产环境请不要向前端暴露Token信息,需要标注@JsonIgnore
     *
     * @return
     */
    @Transient
    public TokenPackage getTokePackage() {
        return tokePackage;
    }

    public void setTokePackage(TokenPackage tokePackage) {
        this.tokePackage = tokePackage;
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
