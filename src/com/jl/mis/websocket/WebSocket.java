package com.jl.mis.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * webSocket配置
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 16:13
 */
@Configuration
@ComponentScan("com.jl")
@EnableWebSocket
public class WebSocket implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //1.注册WebSocket
        String websocket_url = "/websocket/socketServer.do";                        //设置websocket的地址
        registry.addHandler(webSocketHandler(), websocket_url).                          //注册Handler
                addInterceptors(new WebSocketHandshakeInterceptor());                   //注册Interceptor

        //2.注册SockJS，提供SockJS支持(主要是兼容ie8)
        String sockjs_url = "/sockjs/socketServer.do";                              //设置sockjs的地址
        registry.addHandler(webSocketHandler(), sockjs_url).                            //注册Handler
                addInterceptors(new WebSocketHandshakeInterceptor()).                   //注册Interceptor
                withSockJS();                                                           //支持sockjs协议
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }

}
