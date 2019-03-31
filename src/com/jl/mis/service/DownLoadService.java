package com.jl.mis.service;



import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传下载
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/10 14:35
 */

public interface DownLoadService {
    /**
     * 文件下载
     *
     * @param response
     * @param fileName 文件名字
     * @param url 文件路径
     * @return
     */
     boolean downLoad(HttpServletResponse response, String fileName, String url);
 }
