package com.jl.mis.model.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/24 8:37
 */
public class MenuDetailEntity implements Serializable {
    private Integer id;
    private String menuName;
    private String menuUrl;
    private String menuIconUrl;
    private Integer menuSort;
    private Integer menuParentId;
    private Integer isEffective;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIconUrl() {
        return menuIconUrl;
    }

    public void setMenuIconUrl(String menuIconUrl) {
        this.menuIconUrl = menuIconUrl;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public Integer getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(Integer menuParentId) {
        this.menuParentId = menuParentId;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }


}
