package com.jl.mis.controller;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.dto.OperatingRecordDTO;
import com.jl.mis.model.entity.OperatingModelEntity;
import com.jl.mis.service.OperatingRecordService;
import com.jl.mis.utils.DataTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/21 14:27
 */
@Controller
@RequestMapping("callback/operatingRecord")
public class OperatingRecordController {
    @Autowired
    private OperatingRecordService operatingRecordService;
    /**
     * 访问示例: localhost:8080/listModel
     * @return 操作列表
     */
    @ResponseBody
    @RequestMapping(value = "/listModel",method = RequestMethod.POST)
    public JSONObject listModel(){
        JSONObject jsonObject = new JSONObject();
        List<OperatingRecordDTO> modelListAll = operatingRecordService.selectOperatingRecord();
        jsonObject.put("modelListAll",modelListAll);
        return jsonObject;
    }

    /**
     * 访问示例: localhost:8080/listModelByUserNameAndModelType?userName=admin
     * 根据用户名和操作模块查询操作列表
     * @param userName 操作人姓名
     * @param modeTypeId 操作模块
     * @return 操作列表
     */
    @ResponseBody
    @RequestMapping(value = "/listModelByUserNameAndModelType",method = RequestMethod.POST)
    public DataTables listModelByUserNameAndModelType(String userName, Integer modeTypeId, Integer defined, HttpServletRequest request){
        DataTables dataTables = operatingRecordService.selectOperatingRecordToTypeId(userName,modeTypeId,defined,request);
        return dataTables;
    }

    /**
     * 访问示例: localhost:8080/listModelType
     * @return 模块类型列表
     */
    @ResponseBody
    @RequestMapping(value = "/listModelType",method = RequestMethod.POST)
    public JSONObject listModelType(){
        JSONObject jsonObject = new JSONObject();
        List<OperatingModelEntity> list = operatingRecordService.selectOperatingModel();
        jsonObject.put("modelType",list);
        return jsonObject;
    }
}
