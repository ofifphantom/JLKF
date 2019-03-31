package com.jl.mis.service;

import com.jl.mis.model.Menu;

import java.util.List;

/**
 * 菜单Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:44:40
 * @Description TODO
 */
public interface MenuService  extends BaseService<Menu> {
	List<Menu>selectByAll();
}