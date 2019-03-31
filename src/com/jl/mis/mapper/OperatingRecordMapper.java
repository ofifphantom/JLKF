package com.jl.mis.mapper;

import com.jl.mis.model.entity.OperatingRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/20 16:10
 * 操作记录映射表
 */
@Repository
public interface OperatingRecordMapper {
    /**
     * 根据条件查询操作日志
     * @param provider_id 操作人id
     * @param operating_model_type_id 类型id
     * @return
     */
    List<OperatingRecordEntity> selectOperatingRecord(@Param("provider_id") String provider_id, @Param("operating_model_type_id") int operating_model_type_id
            , @Param("startNumber") Integer startNumber, @Param("lengthNumber") Integer lengthNumber);
    Integer countOperationRecord(@Param("provider_id") String provider_id, @Param("operating_model_type_id") int operating_model_type_id);
    /**
     * 添加操作日志
     * @param operatingRecordEntity 操作日志实体类s
     */
    void insertOperatingRecord(@Param("OperatingRecordEntity") OperatingRecordEntity operatingRecordEntity);
}
