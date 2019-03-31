package com.jl.mis.service;

import com.jl.mis.dto.OperatingRecordDTO;
import com.jl.mis.model.entity.OperatingModelEntity;
import com.jl.mis.model.entity.OperatingRecordEntity;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.utils.DataTables;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 操作日志 业务逻辑接口
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 11:21
 */
public interface OperatingRecordService {
    /**
     * 查询所有的操作日志
     *
     * @return 返回操作详情
     */
    List<OperatingRecordDTO> selectOperatingRecord();

    /**
     * 根据类型查询操作
     *
     * @param provider  操作人姓名
     * @param modelType 操作模块id
     * @return
     */
    DataTables selectOperatingRecordToTypeId(String provider, int modelType, Integer defined, HttpServletRequest request);

    /**
     * 查询所有的操作模块
     *
     * @return
     */
    List<OperatingModelEntity> selectOperatingModel();

    /**
     * 用做转换具体类型 返回给前端
     *
     * @param modelEntities           操作模块集合
     * @param operatingRecordEntities 操作详细集合
     * @param userEntities            操作人集合
     * @return
     */
    List<OperatingRecordDTO> convertTypeToOperatingRecordDto(List<OperatingModelEntity> modelEntities, List<OperatingRecordEntity> operatingRecordEntities, List<UserEntity> userEntities);

    /**
     * 添加操作日志
     */
    boolean insertOperatingRecord(OperatingRecordEntity operatingRecordEntity);
}
