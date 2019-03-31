package com.jl.mis.dto;

import java.util.List;

/**
 * 菜单返回类
 * @Date 2018-5-29
 */
public class MenuDTO {
    // 唯一标识
    private Integer id;
    // 菜单名称
    private String text;
    // 菜单url
    private String url;
    // 菜单图片url
    private String iconUrl;
    // 菜单子集
    private List<MenuDTO> children;
    private boolean oper = true;
    // 是否启用
    private boolean checked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    public boolean isOper() {
        return oper;
    }

    public void setOper(boolean oper) {
        this.oper = oper;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
