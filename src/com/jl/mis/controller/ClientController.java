package com.jl.mis.controller;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.dto.ChattingRecordsDTO;
import com.jl.mis.service.ClientService;
import com.jl.mis.utils.DataTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("callback/clientRecords")
public class ClientController {
    @Autowired
    private ClientService clientService;
    /**
     * 访问示例:localhost:8080/listClient
     * 咨询列表查询
     *             -1 首页    -2 末页
     *              1 上一页   2 下一页
     * @param defined 自定义页面跳转
     * @param consultNumber 咨询编号
     * @param userName 用户名称
     * @param serviceName 客服名称
     * @param startTime 开始时间
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/listClient",method = RequestMethod.POST)
    public DataTables listClient(Integer defined,
                                 String consultNumber, String userName, String serviceName, String startTime,
                                 HttpServletRequest request){
        DataTables dataTables = clientService.listClient(defined,consultNumber,userName,serviceName,startTime,request);
        return dataTables;
    }

    /**
     * 访问示例:localhost:8080/listChattingRecords?clientId=2
     * 获取咨询详情
     * @param clientId 咨询id
     * @return 咨询信息
     */
    @ResponseBody
    @RequestMapping(value = "/listChattingRecords",method = RequestMethod.POST)
    public JSONObject listChattingRecords(Integer clientId){
        JSONObject jsonObject = new JSONObject();
        ChattingRecordsDTO chattingRecordsDTO = clientService.listChattingRecords(clientId);
        if(null != chattingRecordsDTO){
            jsonObject.put("success",true);
            jsonObject.put("data",chattingRecordsDTO);
        }else {
            jsonObject.put("success",false);
        }
        return jsonObject;
    }
    @RequestMapping(value = "/outPutExcel",method = RequestMethod.GET)
    public void outPutExcel(HttpServletRequest request, HttpServletResponse response){
        clientService.outPutExcel(request,response);
    }
}
