package com.jl.mis.dto;

import java.util.List;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/8 15:08
 */
public class ClientTreeJsonDTO {
    private int id;
    private String text;
    private String state;
    private List<ClientTreeChildrenDTO> children;

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


    public List<ClientTreeChildrenDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ClientTreeChildrenDTO> children) {
        this.children = children;
    }
}
