package com.jl.mis.service;

import com.jl.mis.model.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/22 18:32
 */
public interface TokenService {
    /**
     * 通过用户名查找用户信息
     *
     * @param userName
     * @return
     */
    UserEntity findByUserName(String userName, HttpServletRequest request);

    /**
     * 更新用户在阿里云的访问令牌
     *
     * @param userName
     * @param accessToken
     * @param isRefreshed 是否是通过refreshToken获取的accessToken,刷新令牌和用授权码换取的令牌的返回值一致，但不包含refresh_token和id_token。
     * @return
     */
    UserEntity updateAccessTokenByUserName(String userName, String accessToken, boolean isRefreshed, HttpServletRequest request);



}
