package com.jl.mis.controller;
import com.jl.mis.service.DownLoadService;
import com.jl.mis.websocket.ResourcesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
/**
 * 上传图片
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/30 16:31
 */
@RestController
public class UploadController {
    @Autowired
    private DownLoadService downLoadService;
    @Autowired
    private ResourcesFile resourcesFile;
    @RequestMapping(value = "downLoadFile",method = RequestMethod.GET)
    public Boolean uploadToImg(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");//传值编码
        response.setContentType("text/html;charset=UTF-8");//设置传输编码
        String fileName=request.getParameter("fileName");
        String url =resourcesFile.getFileName()+fileName;
        return  downLoadService.downLoad(response,fileName,url);
       //将图片上传到服务器
    }
}
