package com.jl.mis.utils;

import com.jl.mis.model.entity.ClientEntity;
import org.aspectj.weaver.ast.Literal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 君霖业务静态标识
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/24 9:51
 */
public class ServiceConstant {
//售前聊天常量

    //客户标识
    public static final int CLIENT = 0;
    //客服标识
    public static final int CUSTOMER_SERVICE = 1;

//客户是否是vip

    //该客户是vip
    public static final int IS_VIP = 1;

    //该客户不是vip
    public static final int NOT_VIP = 0;

    //定义一个客服可以聊天的客户量
    public static final int NUMBER_OF_CHAT = 5;

//客户聊天状态

    //售前聊天等待
    public static final int CLIENT_WAIT = 0;

    //售前聊天接待中
    public static final int CLIENT_RECEPTION = 1;

    //售前完成
    public static final int CLIENT_FINISH = 2;

//定义搜索时传入空解决常量

    //int类型 空
    public static final int INTEGER_TO_NUll = 0;

//客服在线状态

    //在线
    public static final int ON_LINE = 1;

    //离线
    public static final int OFF_LINE = 2;

//客服是否介入聊天

    //客服介入聊天
    public static final boolean CUSTOMER_SERVICE_IN = true;

    //客服没有介入
    public static final boolean CUSTOMER_SERVICE_NOTIN = false;


}
