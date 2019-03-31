package com.jl.mis.dto;

/**
 * 树形菜单
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/6 15:22
 */
public class TreeChildrenDTO {
    private int id;
    private String text;

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
}
