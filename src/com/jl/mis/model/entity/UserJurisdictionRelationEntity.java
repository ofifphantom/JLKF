package com.jl.mis.model.entity;

public class UserJurisdictionRelationEntity {
    private Integer id;
    private Integer menuDetailId;
    private Integer userTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuDetailId() {
        return menuDetailId;
    }

    public void setMenuDetailId(Integer menuDetailId) {
        this.menuDetailId = menuDetailId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }
}
