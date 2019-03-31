package com.jl.mis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.dto.TokenDTO;
import com.jl.mis.service.TelePhoneCustomerService;
import com.jl.mis.utils.UriRequest;
import com.jl.mis.websocket.ResourcesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/20 8:37
 */
@Service
public class TelePhoneCustomerServiceImpl implements TelePhoneCustomerService {
    @Autowired
    private ResourcesFile resourcesFile;
    @Override
    public void setAccredit(HttpServletRequest request, HttpServletResponse response) {

        String path=null;
        path=  "https://signin.aliyun.com/oauth2/v1/auth?client_id="+resourcesFile.getAlibabaUid()+"&redirect_uri="+resourcesFile.getAlibabaCallBack()+"&response_type=code&access_type=offline&state=1234567890";
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getToken(String code) {
        String path1="https://oauth.aliyun.com/v1/token?code="+code+"&client_id="+resourcesFile.getAlibabaUid()+"&redirect_uri="+resourcesFile.getAlibabaCallBack()+"&grant_type=authorization_code&client_secret="+resourcesFile.getAlibabaSelectClient()+"";
        
        String token= UriRequest.doGet(path1,null,null);
//        try {
//            response.sendRedirect(path1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
      
        return token;
    }
}
