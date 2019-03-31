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
import com.google.gson.JsonObject;
import com.jl.mis.dto.TokenDTO;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.TelePhoneCustomerService;
import com.jl.mis.service.TokenService;
import com.jl.mis.service.UserManagerService;
import com.jl.mis.utils.HttpRequester;
import com.jl.mis.utils.RandomString;
import com.jl.mis.utils.SessionGainSet;
import com.jl.mis.websocket.ResourcesFile;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import jodd.http.HttpRequest;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author
 * @Version 1.0
 * @Data 2018/6/20 7:57
 */
@Controller
public class TokenCustomer {
    /**
     * 用来生成nonce字符串，长度必须是45位
     */
    private final RandomString randomString = new RandomString(45);
    /**
     * 生产环境的Region是cn-shanghai
     */
    public static final long SESSION_ATTRIBUTE_STATES_TTL_SECONDS = 300;
    public static final String SESSION_ATTRIBUTE_STATES = "states";
    public static final String CALLBACK_REQUEST_PARAMETER_STATE = "state";

    public static String token;
    @Autowired
    private TelePhoneCustomerService telePhoneCustomerService;
    @Autowired
    private ResourcesFile resourcesFile;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private TokenService tokenService;

    private AuthenticationResponse parse(URI redirectURI, Map<String, String> params) throws ParseException {
        return params.containsKey("error") ? AuthenticationErrorResponse.parse(redirectURI,
                params) : AuthenticationSuccessResponse.parse(redirectURI, params);
    }

    private void validateAuthenticationResponse(AuthenticationSuccessResponse successResponse) throws Exception {
        if (successResponse.getIDToken() != null || successResponse.getAccessToken() != null ||
                successResponse.getAuthorizationCode() == null) {
            throw new Exception(
                    "Failed to validate data received from Authorization service: unexpected set of artifacts received");
        }
    }

    @RequestMapping("/aliyun/auth")
    public void auth(ModelMap model,HttpSession session,HttpServletResponse response, Principal principal, HttpServletRequest httpServletRequest)
            throws UnsupportedEncodingException {
        SessionGainSet.getSession(httpServletRequest);

        UserEntity users=userManagerService.selectUserByLoginName(String.valueOf(httpServletRequest.getSession().getAttribute("loginName")));
        if (users.getRefreshToken()==null||users.getRefreshToken()=="") {
            telePhoneCustomerService.setAccredit(httpServletRequest,response);
        }	
        if (users.getRefreshToken()!=null&&users.getRefreshToken()!=""){
            try {
                httpServletRequest.getRequestDispatcher("/aliyun/refresh").forward(httpServletRequest,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/aliyun/refresh")
    public String refresh(ModelMap model,HttpSession session,HttpServletResponse response, Principal principal, HttpServletRequest httpServletRequest)
            throws Exception {
        SessionGainSet.getSession(httpServletRequest);
            String loginName= String.valueOf(httpServletRequest.getSession().getAttribute("loginName")) ;
        UserEntity users = userManagerService.selectUserByLoginName(loginName);
        if (users.getRefreshToken() == null) {
            telePhoneCustomerService.setAccredit(httpServletRequest,response);
//            logger.debug("redirect to: " + redirectUrl);
        }
        String refreshToken = users.getRefreshToken();
        System.out.println("***refresh token " + refreshToken);

        String accessToken = refreshAccessToken(users.getRefreshToken(),
                httpServletRequest.getRequestURI());

        System.out.println("***access token " + accessToken);
        SessionGainSet.setSession(httpServletRequest);
        tokenService.updateAccessTokenByUserName(String.valueOf(httpServletRequest.getSession().getAttribute("loginName")),accessToken,true,httpServletRequest);

        return "call";
    }

//    @RequestMapping("/aliyun/revoke")
//    public Object revoke(ModelMap model, Principal principal, HttpServletRequest httpServletRequest)
//            throws Exception {
//        User user = userManager.findByUserName(principal.getName());
////      如果没有阿里云授权,则该API无法调用,则重定向至阿里云授权页面
//        if (user.isAliyunBound()) {     // 如果有绑定, 需要清除
//            // revoke access token
//            String response = revokeAccessToken(user.getTokePackage().getAccessToken(), httpServletRequest.getRequestURI());
//            // clear local access token info
//            userManager.revokeAccessTokenByUserName(principal.getName(), false);
//            model.addAttribute("userName", user.getUserName());
//            model.addAttribute("isAliyunBound", user.isAliyunBound());
//            return new ModelAndView("/home", model);
//
//            /*
//            String redirectUrl = getAliyunAuthorityUrl(httpServletRequest);
//            logger.debug("redirect to: " + redirectUrl);
//            return new ModelAndView("redirect:" + redirectUrl, model);
//
//            如果有阿里云授权,则取消授权
//            第一次使用授权码code获取AccessToken的时候就携带了refreshToken
//            所以只需要把token传过去就可以了
//            解绑阿里云账号和User的关系
//            把没有isAliyunBond=fase的user序列化
//            */
//        } else {
//            return new RedirectView("/home");
//        }
//
//    }

    @RequestMapping("/tokens")
    public String authCallback(ModelMap model,HttpSession session,HttpServletRequest request, Principal principal, HttpServletRequest httpServletRequest)
            throws Exception {
        SessionGainSet.setSession(request);
        System.out.println(session.getAttribute("loginName"));
        for (int i=0;i<10;i++){
            System.out.println(session.getAttribute("loginName"));
        }
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();
        String currentUrl = requestUrl + (queryString != null ? "?" + queryString : "");

        Map<String, String> params = httpServletRequest.getParameterMap().entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue()[0]));

        AuthenticationResponse authenticationResponse = AuthenticationResponseParser.parse(new URI(currentUrl), params);

        if (authenticationResponse instanceof AuthenticationSuccessResponse) {
            AuthenticationSuccessResponse successResponse = (AuthenticationSuccessResponse) authenticationResponse;
            validateAuthenticationResponse(successResponse);
            State state = loadStateFromSession(httpServletRequest.getSession(),
                    params.get(CALLBACK_REQUEST_PARAMETER_STATE));
            if (state == null) {
//                throw new CallCenterServiceException(null, "InvalidState",
//                        "Failed to validate state data received from authentication service");
            }
            //获得token
            String accessToken=getAccessToken(request );
            

            //String.valueOf(request.getSession().getAttribute("loginName"))

            
            UserEntity users= userManagerService.selectUserByLoginName(String.valueOf(session.getAttribute("loginName")));
            System.out.println("access token is " + accessToken);
            System.out.println(accessToken);
            userManagerService.updateAccessTokenByUserName(accessToken,JSONObject.parseObject(accessToken,TokenDTO.class).getRefresh_token(),users.getLoginName());

        } else {
            AuthenticationErrorResponse errorResponse = (AuthenticationErrorResponse) authenticationResponse;
//            throw new CallCenterServiceException(null, errorResponse.getErrorObject().getCode(),
//                    ("Request for authentication code failed: " +
//                            errorResponse.getErrorObject().getDescription()));
        }
        return "call";
    }

    private void getAliyunAuthorityUrl(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {

    }

    private String getAccessToken( HttpServletRequest request)
            throws Exception {
       String code= request.getParameter("code");
      return   telePhoneCustomerService.getToken(code);


    }

    private String refreshAccessToken(String refreshToken, String requestUrl) throws Exception {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new NameValuePair("client_id", resourcesFile.getAlibabaUid()));
        params.add(new NameValuePair("grant_type", "refresh_token"));
        params.add(new NameValuePair("client_secret", resourcesFile.getAlibabaSelectClient()));
        params.add(new NameValuePair("refresh_token", refreshToken));
        params.add(new NameValuePair("redirect_uri", requestUrl));

        NameValuePair[] pairs = new NameValuePair[params.size()];

        return HttpRequester.post("http://oauth.aliyun.com/v1/token", params.toArray(pairs));
    }
    private State loadStateFromSession(HttpSession session, String state) throws Exception {
        Map<String, State> states = getStatesFromSessionAttributes(session, false);
        if (states != null) {
            eliminateExpiredStates(states);
            State stateValue = states.get(state);
            if (stateValue != null) {
                return stateValue;
            }
        }
        return null;
    }

    private void storeStateInSession(HttpSession session, String state, String nonce) {
        Map<String, State> statesObject = getStatesFromSessionAttributes(session, true);
        statesObject.put(state,
                new State(nonce, new Date()));
    }

    private Map<String, State> getStatesFromSessionAttributes(HttpSession session, boolean createIfAbsence) {
        Object statesObject = session.getAttribute(SESSION_ATTRIBUTE_STATES);
        if (statesObject == null && createIfAbsence) {
            statesObject = new HashMap<String, State>(1);
            session.setAttribute(SESSION_ATTRIBUTE_STATES, statesObject);
        }
        return (Map<String, State>) statesObject;
    }

    private void eliminateExpiredStates(Map<String, State> states) {
        Iterator<Map.Entry<String, State>> iterator = states.entrySet().iterator();
        Date now = new Date();
        while (iterator.hasNext()) {
            Map.Entry<String, State> entry = iterator.next();
            long timeElapsedSeconds = TimeUnit.MILLISECONDS.
                    toSeconds(now.getTime() - entry.getValue().getExpirationDate().getTime());
            if (timeElapsedSeconds > SESSION_ATTRIBUTE_STATES_TTL_SECONDS) {
                iterator.remove();
            }
        }
    }

    private class State {
        private String nonce;
        private Date expirationDate;

        public State(String nonce, Date expirationDate) {
            this.nonce = nonce;
            this.expirationDate = expirationDate;
        }

        public String getNonce() {
            return nonce;
        }

        public Date getExpirationDate() {
            return expirationDate;
        }
    }
    }

