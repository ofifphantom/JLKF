package com.jl.mis.utils;

import java.sql.Blob;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/1 16:24
 */
public class WebSocketPhotoVO<T> {
    private int identifying;
    private String loginName;
    private String textMessage;


    public int getIdentifying() {
        return identifying;
    }

    public void setIdentifying(int identifying) {
        this.identifying = identifying;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
