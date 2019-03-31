package com.jl.mis.mapper;

import com.jl.mis.model.entity.MenuDetailEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDetailMapper {
    /**
     * 根据主键id查询菜单
     * @param ids 主键id
     * @return 菜单列表
     */
    List<MenuDetailEntity> listMenuById(@Param("ids") String ids);
}
