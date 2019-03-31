package com.jl.mis.controller;


import com.jl.mis.utils.SHAUtil;

/**
 * @author
 * @Version 1.0
 * @Data 2018/5/31 10:39
 */
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println( SHAUtil.shaEncode("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
