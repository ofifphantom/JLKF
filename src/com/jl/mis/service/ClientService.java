package com.jl.mis.service;

import com.jl.mis.dto.ChattingRecordsDTO;
import com.jl.mis.utils.DataTables;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作咨询记录
 * @Date 2018-5-29
 */
public interface ClientService {
    /**
     * 查询咨询记录
     * @param defined 自定义翻页
     * @param consultNumber 咨询编号
     * @param userName 用户名称
     * @param serviceName 客服名称
     * @param startTime 开始时间
     * @param request
     * @return 咨询记录列表
     */
    DataTables listClient(Integer defined,
                          String consultNumber, String userName, String serviceName, String startTime,
                          HttpServletRequest request);

    /**
     * 获取咨询详情列表
     * @param clientId 咨询 id
     * @return 咨询详情列表
     */
    ChattingRecordsDTO listChattingRecords(Integer clientId);

    /**
     * 导出报表
     * @param request 输出内容
     * @param response 输出流
     */
    boolean outPutExcel(HttpServletRequest request, HttpServletResponse response);
}
