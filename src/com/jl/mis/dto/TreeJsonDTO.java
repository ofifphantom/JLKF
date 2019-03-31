package com.jl.mis.dto;

import java.util.List;

/**
 * 定义一些easy树形菜单 DTO
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/6 14:36
 */
public class TreeJsonDTO {
    private int id;
    private String text;
    private String state;
    private List<TreeChildrenDTO> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public List<TreeChildrenDTO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeChildrenDTO> children) {
        this.children = children;
    }
}
