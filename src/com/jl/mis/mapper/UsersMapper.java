package com.jl.mis.mapper;

import com.jl.mis.model.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查询所有用户信息
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 14:20
 */
@Repository
public interface UsersMapper {
    /**
     * 查询所有User信息
     *
     * @return 返回User集合
     */
    List<UserEntity> selectUser();

    /**
     * 根据姓名查询姓名对应的id
     *
     * @param username 客服姓名
     * @return 返回当前客服姓名的id
     */
    int selectIdToName(@Param("username") String username);
    List<Integer> selectUserIdByUserName(@Param("username") String username);

    /**
     * 查询所有在线客服的客服信息
     *
     * @param user_status_id 在线状态
     * @return 客服信息集合
     */
    List<UserEntity> selectUserToOnLine(@Param("user_status_id") int user_status_id);

    /**
     * 根据id查看客服信息
     *
     * @param id 客服id
     * @return 客服信息实体类
     */
    UserEntity selectUserToId(@Param("id") int id);

    /**
     * 客服登陆的
     *
     * @param LoginName 客服登陆名
     * @return 客服实体类
     */
    UserEntity selectUserToLoginName(@Param("login_name") String LoginName);

    void updateToUserStatusId(@Param("user_status_id") int user_status_id,@Param("login_name") String login_name);

    /**
     * 根据登陆名查询
     * @param loginName
     * @return
     */
    UserEntity findByLoginName(@Param("loginName") String loginName);
}
