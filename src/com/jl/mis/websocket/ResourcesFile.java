package com.jl.mis.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 聊天上传下载路径
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/6/10 16:10
 */
@PropertySource("classpath:jdbc.properties")
@Configuration
public class ResourcesFile {
    @Value("${aliyun.https}")
    private String https;
	//下载图片路径
    @Value("${downLoadImg.fileName}")
    private String fileName;
    //远程服务数据路径
    @Value("${DataAddress}")
    private String dataAddress;
    //WebSocket连接路径
    @Value("${WebSocketAddress}")
    private String webSocketAddress;
    @Value("${aliyun.useId}")
    private String alibabaUid;
    @Value("${aliyun.selectClient}")
    private String alibabaSelectClient;
    @Value("${aliyun.useCallback}")
    private String alibabaCallBack;
    @Value("${aliyun.authority.endpoint}")
    private String auth;

    public String getHttps() {
        return https;
    }

    public void setHttps(String https) {
        this.https = https;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


    public String getWebSocketAddress() {
        return webSocketAddress;
    }

    public void setWebSocketAddress(String webSocketAddress) {
        this.webSocketAddress = webSocketAddress;
    }

    public String getAlibabaUid() {
        return alibabaUid;
    }

    public void setAlibabaUid(String alibabaUid) {
        this.alibabaUid = alibabaUid;
    }

    public String getAlibabaSelectClient() {
        return alibabaSelectClient;
    }

    public void setAlibabaSelectClient(String alibabaSelectClient) {
        this.alibabaSelectClient = alibabaSelectClient;
    }

    public String getAlibabaCallBack() {
        return alibabaCallBack;
    }

    public void setAlibabaCallBack(String alibabaCallBack) {
        this.alibabaCallBack = alibabaCallBack;
    }

    public String getDataAddress() {
		return dataAddress;
	}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer PropertySourcesPlaceholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }
  

	public void setDataAddress(String dataAddress) {
		this.dataAddress = dataAddress;
	}

	public String getwebSocketAddress() {
		return webSocketAddress;
	}

	public void setwebSocketAddress(String webSocketAddress) {
		webSocketAddress = webSocketAddress;
	}

   
}
