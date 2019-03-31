package com.jl.mis.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.mapper.ClientMapper;
import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.service.PreSaleChatService;
import com.jl.mis.utils.ChatNumberGeneration;
import com.jl.mis.utils.DateTime;
import com.jl.mis.utils.ServiceConstant;
import com.jl.mis.utils.WebSocketVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wuwenhai
 * @since JDK1.6
 * @history 2017-6-3 wuwenhai 新建
 * 聊天室代码
 */
@Service
public class WebSocketHandler extends AbstractWebSocketHandler {
    @Autowired
    private ResourcesFile webSocketImgFile;
    @Autowired
    private PreSaleChatService preSaleChatService;
    @Autowired
    private ClientMapper clientMapper;
    //已建立连接的用户
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();
//    private static final String  imgAddress= "F:\\JLMIS\\web\\img\\";
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Logger logger = Logger.getLogger(this.getClass());
    FileOutputStream output;
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        users.add(webSocketSession);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws Exception {
        try {
            //修改当前聊天状态为已完成
            Map map = webSocketSession.getAttributes();
            for (Object value : map.values()
                    ) {
                //拿到要退出的用户登陆账号
                String value1 = String.valueOf(value);
                //根据登陆账号查询该用户正在聊天的对象
                ClientEntity clientEntityWait = clientMapper.selectClientToLoginName(value1, ServiceConstant.CLIENT_WAIT);
               //如果是等待客服断开连接
                if (clientEntityWait!=null){
                    //登陆名
                   String loginName= clientEntityWait.getLoginName();
                    //聊天状态
                   Integer state= clientEntityWait.getState();
                    //删除消息
                   clientMapper.delClientWait(loginName, state);
                }else {
                //根据登陆账号查询该用户正在聊天的对象
                ClientEntity clientEntity = clientMapper.selectClientToLoginName(value1, ServiceConstant.CLIENT_RECEPTION);
                clientEntity.setState(ServiceConstant.CLIENT_FINISH);
                clientEntity.setLoginName(String.valueOf(value));
                Timestamp timestamp = DateTime.getTimestamp();
                clientEntity.setFinishTime(timestamp);
                String consultTime = DateTime.timestamp(timestamp, clientEntity.getEntranceTime());
                clientEntity.setConsultTime(consultTime);
                clientEntity.setConsultNumber("A"+ChatNumberGeneration.chatNumberGeneration());
                clientMapper.updateClientState(clientEntity, ServiceConstant.CLIENT_RECEPTION);
                }
            }
        } catch (Exception e) {
        } finally {
            users.remove(webSocketSession);
        }
    }
    @Override
    public void handleTextMessage(WebSocketSession websocketsession, TextMessage message) {
        String imgAddress=webSocketImgFile.getFileName();
        String payload=message.getPayload();
        try {
            if(payload.endsWith(":fileStart")){
                output=new FileOutputStream(new File(imgAddress+payload.split(":")[0]));
                System.out.println(imgAddress+payload.split(":")[0]);
                }else if(payload.endsWith(";fileFinishSingle")){
                output.close();
                String json= payload.split(";")[0];
                WebSocketVO webSocketVO=  JSON.parseObject(json,WebSocketVO.class);
                System.out.println(webSocketVO);
                String loginName = webSocketVO.getLoginName();
                int identifying = webSocketVO.getIdentifying();
                String messages1 = webSocketVO.getTextMessage();
                String clientLoginName = webSocketVO.getClientLoginName();
                //发送文字
                preSaleChatService.sendImages(clientLoginName,identifying, loginName, messages1);
            }else if(payload.endsWith(";fileFinishWithText")){
                output.close();
                String json= payload.split(";")[0];
                WebSocketVO webSocketVO=  JSON.parseObject(json,WebSocketVO.class);

                String loginName = webSocketVO.getLoginName();

                String clientLoginName = webSocketVO.getClientLoginName();
                int identifying = webSocketVO.getIdentifying();
                String messages1 = webSocketVO.getTextMessage();
                //发送文字
                preSaleChatService.sendImages(clientLoginName,identifying, loginName, messages1);

            }
            else{
                System.out.println(message.getPayload());
                //发送文字
                WebSocketVO webSocketVo = JSONObject.parseObject(message.getPayload(), WebSocketVO.class);
                String loginName = webSocketVo.getLoginName();
                int identifying = webSocketVo.getIdentifying();
                String messages1 = webSocketVo.getTextMessage();
                String clientLoginName = webSocketVo.getClientLoginName();
                //发送文字
                preSaleChatService.send(clientLoginName,identifying, loginName, messages1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
    {
        ByteBuffer buffer= message.getPayload();
        try {
            output.write(buffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }

    //先行注释，发送图片 客户给客服发送
//    public void sendPicture(String loginName, TextMessage message){
//        FileInputStream input;
//        try {
//            File file=new File(imgAddress+message.getPayload());
//            input = new FileInputStream(file);
//            byte bytes[] = new byte[(int) file.length()];
//            input.read(bytes);
//            BinaryMessage byteMessage=new BinaryMessage(bytes);
//
//            for (WebSocketSession user : users) {
//                Map map = user.getAttributes();
//                Iterator iter = map.entrySet().iterator();
//                while (iter.hasNext()) {
//                    Map.Entry entry = (Map.Entry) iter.next();
//                    if (entry.getValue().equals(loginName)) {
//                        try {
//                            if (user.isOpen()) {
//                                System.out.println("生成二进制成功");
//                                user.sendMessage(byteMessage);
//                                System.out.println(byteMessage);
//                                System.out.println(byteMessage.getPayload());
//
//                                file.delete();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    }
//                }
//            }
//            input.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void sendPrictureToClient(String loginName, TextMessage message,String clientLoginName){
        FileInputStream input;
        String imgAddress=webSocketImgFile.getFileName();
        try {
            File file=new File(imgAddress+message.getPayload());
            input = new FileInputStream(file);
            byte bytes[] = new byte[(int) file.length()];
            input.read(bytes);
            BinaryMessage byteMessage=new BinaryMessage(bytes);

            for (WebSocketSession user : users) {
                Map map = user.getAttributes();
                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    if (entry.getValue().equals(loginName)) {
                        try {
                            if (user.isOpen()) {
                                System.out.println("生成二进制成功");
                                TextMessage textMessage=new TextMessage("{\"text\":\""+byteMessage+"~img\",\"loginName\":\""+clientLoginName+"\"}");
                                user.sendMessage(textMessage);
                                file.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPicture(String clientName,String loginName, TextMessage message){

        FileInputStream input;
        String imgAddress=webSocketImgFile.getFileName();
        try {
            File file=new File(imgAddress+message.getPayload());
            input = new FileInputStream(file);
            byte bytes[] = new byte[(int) file.length()];
            input.read(bytes);
            BinaryMessage byteMessage=new BinaryMessage(bytes);

            for (WebSocketSession user : users) {
                Map map = user.getAttributes();
                Iterator iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    if (entry.getValue().equals(loginName)) {
                        try {
                            if (user.isOpen()) {
                                System.out.println("生成二进制成功");
                                user.sendMessage(new TextMessage(clientName+"^BinarySystem"));
                                user.sendMessage(byteMessage);
                                file.delete();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 给某个用户发送消息
     *
     * @param loginName 登陆名
     * @param message   发送的消息
     */
    public void sendMessageToUser(String loginName, TextMessage message) {
        for (WebSocketSession user : users) {
            Map map = user.getAttributes();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry.getValue().equals(loginName)) {
                    try {
                        if (user.isOpen()) {
                            user.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }


    public void sendMessage(String loginName,String clientLoginName, TextMessage message) {
        for (WebSocketSession user : users) {
            Map map = user.getAttributes();
            Iterator iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry.getValue().equals(clientLoginName)) {
                    try {
                        if (user.isOpen()) {
                            TextMessage textMessage=new TextMessage("  {\"text\":\"<li class='message-text'>"+message.getPayload()+"</li>\",\"loginName\":\""+loginName+"\"}");
                            user.sendMessage(textMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }
}
