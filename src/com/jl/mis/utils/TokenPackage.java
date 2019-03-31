package com.jl.mis.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * @author edward
 * @date 2017/11/12
 */
public class TokenPackage implements Serializable {

    private String access_token;

    private int expires_in;

    private String token_type;

    private String refresh_token;

    private String id_token;

    private Map<String, Object> additionalInformation;

    private Date createDate;

    private Credentials credentials;

    private UserInfo userInfo;

    public TokenPackage() {
        createDate = new Date();
    }

    public static TokenPackage parse(String token) throws ParseException, IOException {
        TokenPackage tokenPackage = JSONObject.parseObject(token, TokenPackage.class);
        if (tokenPackage.getId_token() != null) {
            SignedJWT signedJWT = SignedJWT.parse(tokenPackage.getId_token());
            ReadOnlyJWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            UserInfo userInfo = new UserInfo();
            userInfo.setPrincipalName(jwtClaimsSet.getStringClaim("upn"));
            userInfo.setName(jwtClaimsSet.getStringClaim("name"));
            userInfo.setAccountId(jwtClaimsSet.getStringClaim("aid"));
            tokenPackage.setUserInfo(userInfo);
        }

        return tokenPackage;
    }

    public static TokenPackage unpack(String token) {
        return JSONObject.parseObject(token, TokenPackage.class);
    }

    public static String mergeAndPack(TokenPackage destination, TokenPackage source) {
        destination.setId_token(source.id_token);
        destination.setRefresh_token(source.refresh_token);
        return JSONObject.toJSONString(destination);
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpiredDate() {
        return new Date(createDate.getTime() + expires_in * 1000);
    }

    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, Object> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public boolean isExpire() {
        return new Date(createDate.getTime() + expires_in * 1000).before(new Date());
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfo {
        private String accountId;
        private String principalName;
        private String name;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getPrincipalName() {
            return principalName;
        }

        public void setPrincipalName(String principalName) {
            this.principalName = principalName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Credentials implements Serializable {

        @JsonProperty("AccessKeyId")
        private String accessKeyId;

        @JsonProperty("AccessKeySecret")
        private String accessKeySecret;

        @JsonProperty("Expiration")
        private String expiration;

        @JsonProperty("SecurityToken")
        private String securityToken;

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }
    }
}
