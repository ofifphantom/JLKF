package com.jl.mis.model.entity;

import java.io.Serializable;
import java.sql.Blob;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/7 22:25
 */
public class TestEntity implements Serializable {
    private Blob css;

    public Blob getCss() {
        return css;
    }

    public void setCss(Blob css) {
        this.css = css;
    }
}
