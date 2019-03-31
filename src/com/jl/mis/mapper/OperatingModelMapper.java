package com.jl.mis.mapper;

import com.jl.mis.model.entity.OperatingModelEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作模块映射
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/20 17:15
 */
@Repository
public interface OperatingModelMapper {
    /**
     * 查询所有的操作模块
     *
     * @return
     */
    List<OperatingModelEntity> selectOperatingModel();

}
