package com.jl.mis.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * session的获取和设置
 * @author
 * @Version 1.0
 * @Data 2018/6/23 17:35
 */
public class SessionGainSet {
    private static String userId;
    private static String userName;
    private static String loginName;
    private static String userType;
    private static String userGroup;
    private static String password;
    private static String webSocketAddress;
    private static String clientLoginName;
    public static void getSession(HttpServletRequest request){
        userId= String.valueOf(request.getSession().getAttribute("userId"));
        userName=String.valueOf(request.getSession().getAttribute("userName"));
        loginName=String.valueOf(request.getSession().getAttribute("loginName"));
        userType=String.valueOf( request.getSession().getAttribute("userType"));
        userGroup=String.valueOf( request.getSession().getAttribute("userGroup"));
        password=String.valueOf( request.getSession().getAttribute("password"));
        webSocketAddress=String.valueOf( request.getSession().getAttribute("webSocketAddress"));
        clientLoginName=String.valueOf( request.getSession().getAttribute("clientLoginName"));
    }
    public static void setSession(HttpServletRequest request){
        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("userName", userName);
        request.getSession().setAttribute("loginName", loginName);
        request.getSession().setAttribute("userType" ,userType);
        request.getSession().setAttribute("userGroup", userGroup);
        request.getSession().setAttribute("password", password);
        request.getSession().setAttribute("webSocketAddress",webSocketAddress);
        request.getSession().setAttribute("clientLoginName",clientLoginName);
        request.getSession().setAttribute("clientLoginName",loginName);
    }
}
