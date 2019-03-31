package com.jl.mis.service.impl;

import com.jl.mis.service.DownLoadService;
//import com.sun.deploy.net.URLEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/10 14:37
 */
@Service
public class DownLoadServiceImpl implements DownLoadService {
    @Override
    public boolean downLoad(HttpServletResponse response, String fileName, String url) {
        OutputStream outputStream = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(url);
            outputStream = response.getOutputStream();
            response.reset();
            String fileNames = new String(fileName.getBytes(), "ISO-8859-1");
//            String tempFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition","attachment;filename =" +  fileNames);
            response.setHeader("Connection","close");
            response.setHeader("Content-type","application/octet-stream");
            byte[] buffer = new byte[8192];
            int bytesRead = 0;
            while ((bytesRead = fileInputStream.read(buffer,0,8192)) != -1){
                outputStream.write(buffer,0,bytesRead);
            }
            outputStream.flush();
        } catch (Exception e) {
            return false;
        }finally {
            try {
                if(null != outputStream){
                    outputStream.close();
                }
                if(null != fileInputStream){
                    fileInputStream.close();
                }
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }

        return true;
    }
}
