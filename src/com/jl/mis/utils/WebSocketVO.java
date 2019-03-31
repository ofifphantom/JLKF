package com.jl.mis.utils;

/**
 * webSocket发送json转换类
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/28 10:10
 */
public class WebSocketVO {
    private int identifying;
    private String clientLoginName;
    private String loginName;
    private String textMessage;

    public String getClientLoginName() {
        return clientLoginName;
    }

    public void setClientLoginName(String clientLoginName) {
        this.clientLoginName = clientLoginName;
    }

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

