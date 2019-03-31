package com.jl.mis.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Websocket拦截器
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/22 9:45
 */
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        /**
         * 在拦截器中 判断是否登陆
         */
        if (request instanceof ServletServerHttpRequest) {

            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (session != null) {
                //不可变session
                String userName = (String) session.getAttribute(session.getId());
                if (userName == null) {
                    userName =  session.getId();
                }
                attributes.put(session.getId(), userName);

            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, org.springframework.web.socket.WebSocketHandler wsHandler, Exception exception) {
        System.out.println("After Handshake");
    }
}
