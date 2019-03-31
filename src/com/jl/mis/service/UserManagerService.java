package com.jl.mis.service;

import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.utils.DataTables;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作客服Service
 * @date 2018-5-20
 */
public interface UserManagerService {
    /**
     * 查询全部用户
     * @return 用户列表
     */
    DataTables listUserAll(HttpServletRequest request);

    /**
     * 根据条件查询用户
     * @param defined 自定义页面跳转
     * @param userCondition 用户查询条件
     * @return 用户列表
     */
    DataTables listUserByCondition(Integer defined, UserEntity userCondition, HttpServletRequest request);

    /**
     * 根据登录名查询用户
     * @return 用户信息
     */
    UserEntity selectUserByLoginName(String loginName);
    Integer countUserByLoginName(String loginName);
    /**
     * 根据用户 id 查询用户信息
     * @param userId 用户id
     * @return 用户信息
     */
    UserEntity selectById(Integer userId);
    /**
     * 生成密码
     * @return
     */
    String getPassword();

    /**
     * 添加用户
     * @param request
     * @param userEntity 用户信息
     * @return 是否成功
     */
    boolean insertUser(HttpServletRequest request, UserEntity userEntity);

    /**
     * 修改用户
     * @param userEntity 用户信息
     * @return 是否成功
     */
    boolean updateUser(HttpServletRequest request, UserEntity userEntity);

    /**
     * 禁用用户信息
     * @param request
     * @param userEntity 用户信息
     * @return 是否成功
     */
    Integer disableUser(HttpServletRequest request, UserEntity userEntity);

    /**
     * 修改用户密码
     * @param request
     * @param password 新密码
     * @return 是否成功
     */
    boolean updatePassword(HttpServletRequest request, String password);
    /**
     * 用户上下线
     * @param request
     * @param identifying 标识
     *                    1 上线
     *                    2 离线
     * @return 是否成功
     */
    boolean logoutUser(HttpServletRequest request, Integer identifying);

    /**
     * 修改token
     * @param token
     */
    void updateAccessTokenByUserName(String token, String refreshToken, String loginName);
}
