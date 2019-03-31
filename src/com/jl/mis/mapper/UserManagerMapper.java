package com.jl.mis.mapper;

import com.jl.mis.model.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客服表操作
 * @date 2018-5-20
 */
public interface UserManagerMapper {
    /**
     * 获取所有用户
     * @return 客服信息列表
     */
    List<UserEntity> listUserAll();

    /**
     * 根据条件查询用户
     * @param startNumber 起始条数
     * @param showNumber 每页显示条数
     * @param condition 查询条件
     * @return 用户列表
     */
    List<UserEntity> listUserByLimit(@Param("startNumber") Integer startNumber, @Param("showNumber") Integer showNumber, @Param("condition") String condition);

    /**
     * 根据条件查询用户
     * @param condition 查询条件
     * @return 用户列表
     */
    List<UserEntity> listUserByCondition(@Param("condition") String condition);

    /**
     * 根据用户名查询用户
     * @param userName 用户名称
     * @return 用户信息列表
     */
    List<UserEntity> listUserByUserName(@Param("userName") String userName);
    Integer countUserByLoginName(@Param("loginName") String loginName);

    /**
     * 根据登录名查询用户
     * @param loginName 登录名
     * @return 查询到的用户信息
     */
    UserEntity getUserByLoginName(@Param("loginName") String loginName);

    /**
     * 根据用户 id 查询用户信息
     * @param userId 用户 id
     * @return 用户信息
     */
    UserEntity getUserByUserId(@Param("userId") Integer userId);

    /**
     * 添加用户
     * @return 是否成功
     */
    Integer insertUser(@Param("userEntity") UserEntity userEntity);

    /**
     * 禁用用户
     * @param userId 用户id
     * @return 是否成功
     */
    int disableUser(@Param("userId") Integer userId, @Param("isForbidden") Integer isForbidden);

    /**
     * 编辑用户
     * @param userEntity 用户信息
     * @return
     */
    int updateUserById(@Param("userEntity") UserEntity userEntity);

    /**
     * 根据用户 id 修改密码
     * @param password 新密码
     * @param userId 用户 id
     * @return 是否成功
     */
    int updateUserPassword(@Param("loginPassword") String password, @Param("userId") Integer userId);

    /**
     * 用户上下线
     * @param userId 用户 Id
     * @param identifying
     * @return 是否成功
     */
    int logoutUser(@Param("userId") Integer userId, @Param("identifying") Integer identifying);

    int updateAccessTokenByUserName(@Param("token") String token,@Param("refreshToken") String refreshToken,@Param("loginName") String loginName);
}
