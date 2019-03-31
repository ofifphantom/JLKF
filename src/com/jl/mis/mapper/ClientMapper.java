package com.jl.mis.mapper;

import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.model.entity.UserEntity;
import org.apache.cxf.endpoint.Client;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.ClientInfoStatus;
import java.util.List;

/**
 * 客户数据库映射表
 * 用于售前聊天模块
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/24 9:45
 */
@Repository
public interface ClientMapper {
    /**
     * 删除七天前聊天人
     */
    void   delClientSevenDaysAgo();
    /**
     * 查询售前所有聊天完成的客户信息
     *
     * @param state 状态
     * @return
     */
    List<ClientEntity> selectClientAll(@Param("state") int state);

    /**
     * 添加一条客户聊天信息
     *
     * @param clientEntity 客户信息实体类
     */
    void insertClient(@Param("ClientEntity") ClientEntity clientEntity);

    /**
     * 修改客户聊天信息
     *
     * @param clientEntity 客户聊天信息实体类
     * @param state        当前修改以前的客户聊天状态
     */
    void updateClient(@Param("ClientEntity") ClientEntity clientEntity, @Param("state") int state);

    /**
     * 查询客服的接待人数
     *
     * @param id    客服id
     * @param state 聊天状态
     * @return 接待人数
     */
    int selectClientToLeisure(@Param("id") int id, @Param("state") int state);

    /**
     * 用做查看当前等待状态的客服是否是VIP
     *
     * @param loginName 登陆账号
     * @param state     等待状态
     * @return 是否是VIP标识
     */
    int selectIdToVip(@Param("login_name") String loginName, @Param("state") int state);

    /**
     * 根据登陆账号和聊天状态查询
     *
     * @param loginName 登陆账号
     * @param state     聊天状态
     * @return 客户集合
     */
    ClientEntity selectClientToLoginName(@Param("login_name") String loginName, @Param("state") int state);

    /**
     * 根据客服id 查看当前与该客服正坐在聊天的客户
     *
     * @param audienceId 客服id
     * @param state      客服状态
     * @return 客服集合
     */
    List<ClientEntity> selectClientToAudienceId(@Param("audience_id") int audienceId, @Param("state")int state);

    /**
     * 修改客户状态
     *
     * @param clientEntity 客服实体类
     * @param state        状态
     */
    void updateClientState(@Param("ClientEntity") ClientEntity clientEntity, @Param("state") int state);

    /**
     * 用户等待结束 开始聊天
     *
     * @param clientEntity 客服实体类
     * @param state        聊天开始状态
     */
    void updateClientNotWait(@Param("ClientEntity") ClientEntity clientEntity, @Param("state") int state);

    /**
     * 根据条件查询咨询记录
     *
     * @param startNumber 起始条数
     * @param showNumber  每页显示条数
     * @param condition   查询条件
     * @return 查询的信息
     */
    List<ClientEntity> listClientByConditionLimit(@Param("startNumber") Integer startNumber, @Param("showNumber") Integer showNumber, @Param("condition") String condition);

    /**
     * 根据 id 查询咨询信息
     *
     * @param clientId 咨询 id
     * @return 咨询信息
     */
    ClientEntity getClientByClientId(@Param("clientId") Integer clientId);

    /**
     * 根据当前登陆客服查询正在聊天的用户
     * @param providerId 客服id
     * @return
     */
    List<ClientEntity> selectClient(@Param("providerId") Integer providerId);

    /**
     * 查询所有等待的用户
     *
     * @return
     */
    List<ClientEntity>  selectToWait();

    /**
     * 查询当前用户聊天完成的用户
     *
     * @param providerId
     * @return
     */
    List<ClientEntity> selectToFinish(@Param("providerId") Integer providerId);

    /**
     * 根据登陆名查询id
     * @param login_name 客户登陆名
     * @return
     */
    List<ClientEntity> selectClientToClientLoginName(@Param("login_name")String login_name);

    /**
     * 根据条件查询咨询记录
     * @param condition 查询条件
     * @return 查询后的信息
     */
    List<ClientEntity> listClientByCondition(@Param("condition")String condition);

    /**
     * 根据当前登陆 查询正在聊天的消息记录
     * @param login_name 登陆名
     * @param state 聊天状态
     * @return
     */
    List<ClientEntity> listToChatting(@Param("login_name") String login_name,@Param("state") int state);

    /**
     * 客服点击保存并退出 修改项
     * @param clientEntity
     * @param state
     */
    void updateClientByState(@Param("ClientEntity") ClientEntity clientEntity,@Param("state") int state);
    
    /**
     * 删除等待用户
     * @param loginName 登陆用户
     * @param state 聊天状态
     * @return 返回删除是否成功
     */
    int delClientWait(@Param("login_name") String loginName,@Param("state") Integer state);
    /**
     * 查询所有等待用户
     */
   List<ClientEntity> findByWait(@Param("state") Integer state);



}
