package com.jl.mis.service.impl;

import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.TokenService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.utils.TokenPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/22 18:32
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    @Autowired
    private UserManagerService userManagerService;
    public static  TokenPackage tokenPackage = null;
    @Override
    public UserEntity findByUserName(String userName,HttpServletRequest request) {
        UserEntity user = userManagerService.selectUserByLoginName(String.valueOf(request.getSession().getAttribute("loginName")));
        if (user != null) {
        if (user.getRefreshToken()!=null&&user.getRefreshToken()!=""){
                try {
                    tokenPackage = TokenPackage.parse(user.getToken());
                } catch (ParseException e) {
//                    logger.error("Failed to parse access token: " + account.getAccessCode(), e);
                } catch (IOException ioe) {
//                    logger.error("Failed to parse access token: " + account.getAccessCode(), ioe);
                }
            }
        }
        return user;
    }

    @Override
    public UserEntity updateAccessTokenByUserName(String userName, String accessToken, boolean isRefreshed, HttpServletRequest request) {
        UserEntity user = userManagerService.selectUserByLoginName(String.valueOf(request.getSession().getAttribute("loginName")));
        if (user != null) {

            try {
                tokenPackage = TokenPackage.parse(accessToken);
            } catch (ParseException e) {
                logger.error("Failed to parse access token: " + accessToken, e);
            } catch (IOException ioe) {
                logger.error("Failed to parse access token: " + accessToken, ioe);
            }
            if (tokenPackage != null) {

                if (isRefreshed) {
                    //进行一次处理
                    TokenPackage refreshed = TokenPackage.unpack(accessToken);
                    TokenPackage original = TokenPackage.unpack(user.getToken());

                  userManagerService.updateAccessTokenByUserName(  TokenPackage.mergeAndPack(refreshed, original),user.getRefreshToken(),String.valueOf(request.getSession().getAttribute("loginName")));
                } else {
                    userManagerService.updateAccessTokenByUserName(accessToken,user.getRefreshToken(),String.valueOf(request.getSession().getAttribute("loginName")));

                }

            }
        }

        return user;
    }
}
