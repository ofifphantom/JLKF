package com.jl.mis.service.impl;

import com.jl.mis.utils.Constants;
import org.springframework.context.annotation.Configuration;

/**
 * 给UserServiceImpl 和 UserTypeServiceImpl 提供帮助
 */
@Configuration
public class UserServiceUtil {
    String getUserTypeId(Integer judgeUserTypeId){
        String resultUserTypeId = "";
        if((null != judgeUserTypeId) && (judgeUserTypeId > 0)){
            switch (judgeUserTypeId){
                case Constants.USER_GROUP_SUPER_ADMINISTRATOR:
                    resultUserTypeId = Constants.USER_SUPER_ADMINISTRATOR + "," +
                            Constants.PRE_SALES_SERVICE_MANAGE + "," +
                            Constants.AFTER_SALE_SERVICE_MANAGE + "," +
                            Constants.ACCOUNT_NUMBER_SERVICE_MANAGE + "," +
                            Constants.PRE_SALES_SERVICE + "," +
                            Constants.AFTER_SALE_SERVICE + "," +
                            Constants.ACCOUNT_NUMBER_SERVICE ;
                    break;
                case Constants.PRE_SALES_SERVICE_MANAGE:
                    resultUserTypeId = Constants.PRE_SALES_SERVICE + "";
                    break;
                case Constants.AFTER_SALE_SERVICE_MANAGE:
                    resultUserTypeId = Constants.AFTER_SALE_SERVICE + "";
                    break;
                case Constants.ACCOUNT_NUMBER_SERVICE_MANAGE:
                    resultUserTypeId = Constants.ACCOUNT_NUMBER_SERVICE + "";
                    break;
            }
        }
        return resultUserTypeId;
    }
}
