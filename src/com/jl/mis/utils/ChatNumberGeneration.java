package com.jl.mis.utils;

/**
 * 订单编号生成
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/11 14:27
 */
public class ChatNumberGeneration {
    /**
     * 生成六位数随机数
     * @return
     */
    public static int chatNumberGeneration(){
      return  (int)((Math.random()*9+1)*100000);
    }
}
