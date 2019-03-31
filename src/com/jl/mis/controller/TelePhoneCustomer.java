package com.jl.mis.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.BearerTokenCredentials;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.TelePhoneCustomerService;
import com.jl.mis.service.TokenService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.service.impl.TokenServiceImpl;
import com.jl.mis.utils.*;
import com.jl.mis.websocket.ResourcesFile;

import org.apache.commons.httpclient.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/22 11:23
 */
@RestController
public class TelePhoneCustomer {
    /**
     * 生产环境的Region是cn-shanghai
     */
    private static final String REGION = "cn-shanghai";
    private static final String PRODUCT = "CCC";
    private static final String ENDPOINT = "CCC";
    /**
     * 生产环境的Region是ccc.cn-shanghai.aliyuncs.com
     */
    private static final String DOMAIN = "ccc.cn-shanghai.aliyuncs.com";
    private static final String VERSION = "2017-07-05";

    @Autowired
    private TelePhoneCustomerService telePhoneCustomerService;
    @Autowired
    private ResourcesFile resourcesFile;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private TokenService tokenService;


    private static final Logger logger = LoggerFactory.getLogger(TelePhoneCustomer.class);


    public static void main(String[] args) {
        String jsonString = "{\"HttpStatusCode\":200,\"RequestId\":\"4322BD50-ACF5-4527-877B-0FA34CBBF331\","
                + "\"Success\":true,\"Code\":\"OK\",\"SkillLevels\":{\"SkillLevel\":[{\"SkillLevelId\":\"15831\","
                + "\"Skill\":{\"OutboundPhoneNumbers\":{\"PhoneNumber\":[]},"
                + "\"InstanceId\":\"dcbdbe37-b966-48c1-b8d7-dd6574190db4\",\"SkillGroupName\":\"e69041\","
                + "\"SkillGroupId\":\"1cf05ff4-1ce6-4d71-919e-d56a48bb2059\"},\"Level\":5}]}}";

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        JSONObject newObject = new JSONObject();

        copyObject(newObject, jsonObject);

        String result = JSONObject.toJSONString(newObject);

        System.out.println(result);
    }

    private static void copyArray(JSONArray destination, JSONArray source) {
        Iterator<Object> iterator = source.iterator();
        while (iterator.hasNext()) {
            Object object = iterator.next();
            if (object instanceof JSONObject) {
                JSONObject newObject = new JSONObject();
                destination.add(newObject);
                copyObject(newObject, (JSONObject) object);
            } else if (object instanceof JSONArray) {
                JSONArray newArray = new JSONArray();
                destination.add(newArray);
                copyArray(newArray, (JSONArray) object);
            } else {
                destination.add(object);
            }
        }
    }

    private static void copyObject(JSONObject destination, JSONObject source) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey().trim();
            if (key.length() > 1) {
                key = key.substring(0, 1).toLowerCase() + key.substring(1);
            } else if (key.length() == 1) {
                key = key.toUpperCase();
            } else {
                continue;
            }

            Object object = entry.getValue();

            if (object instanceof JSONObject) {
                JSONObject tempObject = (JSONObject) object;
                if (tempObject.entrySet().size() == 1) {
                    Object theOne = tempObject.entrySet().iterator().next().getValue();
                    if (theOne instanceof JSONArray) {
                        JSONArray newArray = new JSONArray();
                        destination.put(key, newArray);
                        copyArray(newArray, (JSONArray) theOne);
                        continue;
                    }
                }
                JSONObject newObject = new JSONObject();
                destination.put(key, newObject);
                copyObject(newObject, (JSONObject) object);
            } else if (object instanceof JSONArray) {
                JSONArray newArray = new JSONArray();
                destination.put(key, newArray);
                copyArray(newArray, (JSONArray) object);
            } else {
                destination.put(key, object);
            }
        }
    }

    @RequestMapping(value = "/aliyun/ccc/api", method = RequestMethod.POST)
    public String call(Principal principal, HttpServletRequest httpServletRequest) throws ClientException, IOException, ParseException {
        //  String loginName= String.valueOf(httpServletRequest.getSession().getAttribute("loginName")) ;

       UserEntity users= userManagerService.selectUserByLoginName(String.valueOf(httpServletRequest.getSession().getAttribute("loginName")));
        String accessToken = null;
        try {
            accessToken = refreshAccessToken(httpServletRequest,users.getRefreshToken(),
                    httpServletRequest.getRequestURI());

            tokenService.updateAccessTokenByUserName(String.valueOf(httpServletRequest.getSession().getAttribute("loginName")), accessToken, true,httpServletRequest);
        } catch (Exception e) {
            logger.error("Failed to refresh token, request=" + JSON.toJSONString(httpServletRequest), e);
        }
        String action = httpServletRequest.getParameter("action");
        String request = httpServletRequest.getParameter("request");


      tokenService.updateAccessTokenByUserName(String.valueOf(httpServletRequest.getSession().getAttribute("loginName")),accessToken,true,httpServletRequest);
        System.out.println(TokenServiceImpl.tokenPackage.getAccess_token());
        SessionGainSet.setSession(httpServletRequest);
        return invokeApiByBearerToken(TokenServiceImpl.tokenPackage.getAccess_token(), action, request);
    }

    private String refreshAccessToken(HttpServletRequest request,String refreshToken, String requestUrl) throws Exception {
        SessionGainSet.getSession(request);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("client_id", resourcesFile.getAlibabaUid()));
        params.add(new NameValuePair("grant_type", "refresh_token"));
        params.add(new NameValuePair("client_secret", resourcesFile.getAlibabaSelectClient()));
        params.add(new NameValuePair("refresh_token", refreshToken));
        params.add(new NameValuePair("redirect_uri", requestUrl));
        NameValuePair[] pairs = new NameValuePair[params.size()];
        return HttpRequester.post("http://oauth.aliyun.com/v1/token", params.toArray(pairs));
    }

    //   /**
//     * 用以方便调试
//     *
//     * @param principal
//     * @param httpServletRequest
//     * @param action
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = "/aliyun/ccc/api/debug", method = RequestMethod.GET)
//    public String get(Principal principal, HttpServletRequest httpServletRequest,
//                      @RequestParam String action, @RequestParam String request) throws ClientException {
//        User user = userManager.findByUserName(principal.getName());
//
//        if (!user.isAliyunBound()) {
//            throw new CallCenterServiceException(null, "NotAuthenticated",
//                    "Not authenticated to access Aliyun Cloud Call Center resources.");
//        } else {
//            logger.info("userName=[{}], accessToken=[{}], ",
//                    user.getUserName(), user.getTokePackage().getAccessToken());
//            return invokeApiByBearerToken(user.getTokePackage().getAccessToken(), action, request);
//        }
//    }

    private String invokeApiByBearerToken(String accessToken, String action, String request) throws ClientException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(PRODUCT, DOMAIN);

        DefaultProfile profileBear = DefaultProfile.getProfile(REGION);
        profileBear.addEndpoint(ENDPOINT, REGION, PRODUCT, DOMAIN);

        BearerTokenCredentials bearerTokenCredentials =
                new BearerTokenCredentials(accessToken);
        DefaultAcsClient accessTokenClient = new DefaultAcsClient(profileBear, bearerTokenCredentials);
        accessTokenClient.setAutoRetry(false);

        /**
         * 使用CommonAPI调用POP API时，和使用传统产品SDK相比，请求和返回参数的格式都有所不同，因此需要进行一定的格式转换。
         */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setDomain(DOMAIN);
        commonRequest.setVersion(VERSION);
        commonRequest.setAction(action);
        JSONObject jsonObject = JSONObject.parseObject(request);

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey().trim();
            if (key.length() > 1) {
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
            } else if (key.length() == 1) {
                key = key.toUpperCase();
            } else {
                continue;
            }
            commonRequest.putQueryParameter(key, entry.getValue().toString());
        }

        commonRequest.putQueryParameter("accessToken", accessToken);
        CommonResponse response = null;
        try {
            response = accessTokenClient.getCommonResponse(commonRequest);
            System.out.println(JSONObject.toJSONString(response.getData()));
        } catch (ClientException e) {
            logger.error("Failed to invoke open API, request=" + JSON.toJSONString(commonRequest), e);
            throw new CallCenterServiceException(e.getRequestId(), e.getErrCode(), e.getMessage());
        }

        JSONObject jsonResult = JSONObject.parseObject(response.getData());
        JSONObject newJsonResult = new JSONObject();
        copyObject(newJsonResult, jsonResult);
        System.out.println("zheng" + JSONObject.toJSONString(newJsonResult));
        return JSONObject.toJSONString(newJsonResult);
    }


    private String invokeApi(TokenPackage.Credentials credentials, String action, String request) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(PRODUCT, DOMAIN);

        IClientProfile stsProfile = DefaultProfile.getProfile(REGION, map, credentials.getAccessKeyId(),
                credentials.getAccessKeySecret(), credentials.getSecurityToken());
        DefaultAcsClient stsClient = new DefaultAcsClient(stsProfile);

        stsClient.setAutoRetry(false);

        /**
         * 使用CommonAPI调用POP API时，和使用传统产品SDK相比，请求和返回参数的格式都有所不同，因此需要进行一定的格式转换。
         */
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.setDomain(DOMAIN);
        commonRequest.setVersion(VERSION);
        commonRequest.setAction(action);

        JSONObject jsonObject = JSONObject.parseObject(request);

        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey().trim();
            if (key.length() > 1) {
                key = key.substring(0, 1).toUpperCase() + key.substring(1);
            } else if (key.length() == 1) {
                key = key.toUpperCase();
            } else {
                continue;
            }
            commonRequest.putQueryParameter(key, entry.getValue().toString());
        }

        commonRequest.putQueryParameter("SecurityToken", credentials.getSecurityToken());
        commonRequest.putQueryParameter("AccessKeyId", credentials.getAccessKeyId());

        logger.info("SecurityToken: " + credentials.getSecurityToken());
        logger.info("AccessKeyId: " + credentials.getAccessKeyId());

        CommonResponse response = null;
        try {
            response = stsClient.getCommonResponse(commonRequest);
            System.out.println(response.getData());
        } catch (ClientException e) {
            logger.error("Failed to invoke open API, request=" + JSON.toJSONString(commonRequest), e);
            throw new CallCenterServiceException(e.getRequestId(), e.getErrCode(), e.getMessage());
        }

        JSONObject jsonResult = JSONObject.parseObject(response.getData());
        JSONObject newJsonResult = new JSONObject();
        copyObject(newJsonResult, jsonResult);
        return JSONObject.toJSONString(newJsonResult);
    }


}
