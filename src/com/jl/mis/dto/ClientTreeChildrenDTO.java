package com.jl.mis.dto;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/8 15:09
 */
public class ClientTreeChildrenDTO {
    private int id;
    private String text;
    private AttributesDTO attributes;

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AttributesDTO getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesDTO attributes) {
        this.attributes = attributes;
    }

}
