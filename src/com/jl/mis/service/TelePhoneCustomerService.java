package com.jl.mis.service;

import com.jl.mis.dto.TokenDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 售后客服
 * @author
 * @Version 1.0
 * @Data 2018/6/20 8:30
 */
public interface TelePhoneCustomerService {
    /**
     * 授权
     * @param request
     */
     void setAccredit(HttpServletRequest request, HttpServletResponse response);

    /**
     * 根据授权信息拿到token
     * @param code 授权信息
     */
    String getToken(String code);


}
