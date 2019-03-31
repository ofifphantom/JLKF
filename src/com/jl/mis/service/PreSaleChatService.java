package com.jl.mis.service;
import com.jl.mis.dto.ClientTreeJsonDTO;
import com.jl.mis.model.entity.ChattingRecordsEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 售前聊天业务接口
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/24 10:34
 */
public interface PreSaleChatService {
    /**
     * 接收客户信息，并且到数据库
     * 并把改用户信息存入session
     * 以便后续聊天
     *
     * @param userName 客服名称
     * @param loginName 客服登陆名 唯一标识
     * @param isVip 是否是vip 0是 1否
     * @param session session作用域
     * @return 判断客服是否连接
     */
    int insertClientAndSendSession(HttpSession session, String userName, String loginName, int isVip);

    /**
     * 发送消息
     *
     * @param identifying 客服客户标识
     * @param loginName 登陆名
     * @param textMessage 发送消息
     */
    void send(String clientLoginName,int identifying,String loginName, String textMessage);


    /**
     * 发送图片
     *
     * @param identifying 客服客户标识
     * @param loginName 登陆名
     * @param textMessage 发送消息
     */
    void sendImages(String clientLoginName,int identifying,String loginName, String textMessage);

    /**
     * 客服下线处理
     *
     * @param session
     * @return
     */
    boolean CustomerServiceLine(HttpSession session);



    /**
     * 通过数据库判断该用户是否是vip  然后自动回复
     *
     * @param loginName 用户登陆账号
     */
    void isVipReplyMessage(String loginName);

    /**
     * 客服上线
     *
     * @param loginName 客服登陆名
     */
    boolean CustomerServiceOnline(HttpSession session, String loginName);

    /**
     * 查看客服哪个是最闲状态
     *
     * @return 最空闲状态的客服id
     */
    int selectMinLinkingNumber();

    /**
     * 查看所有等待的客户，
     * 查看所有当前客服正在聊天的客户
     * 查看当天 当前客服今天聊天过的客户
     */
    List<ClientTreeJsonDTO> selectClientAll(int providerId);


    /**
     * 查询所有客服与这个客户的聊天信息
     *
     * @param loginName 客服登陆名
     * @return
     */
     List<ChattingRecordsEntity> selectChattingRecord(String loginName);

}
