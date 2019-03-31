package com.jl.mis.mapper;

import com.jl.mis.model.Menu;

import java.util.List;

/**
 * 菜单mapper
 *
 * @author 景雅倩
 * @date 2017-11-3  下午3:44:40
 * @Description TODO
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> selectByAll();
}