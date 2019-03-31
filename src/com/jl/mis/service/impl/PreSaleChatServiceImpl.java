package com.jl.mis.service.impl;
import com.jl.mis.dto.AttributesDTO;
import com.jl.mis.dto.ClientTreeChildrenDTO;
import com.jl.mis.dto.ClientTreeJsonDTO;
import com.jl.mis.mapper.ChattingRecordsMapper;
import com.jl.mis.mapper.ClientMapper;
import com.jl.mis.mapper.UsersMapper;
import com.jl.mis.model.entity.ChattingRecordsEntity;
import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.PreSaleChatService;
import com.jl.mis.utils.ServiceConstant;

import com.jl.mis.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 售前聊天模块业务实现
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/24 10:36
 */
@Service
public class PreSaleChatServiceImpl implements PreSaleChatService {
    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private ChattingRecordsMapper chattingRecordsMapper;

    @Override
    public int insertClientAndSendSession(HttpSession session, String userName, String loginName, int isVip) {
        Date date= new Date();
        Timestamp timestamp=new Timestamp(date.getTime());
        session.setAttribute(session.getId(), loginName);
        //判断当前客服是否有空闲
        int isLeisure=selectMinLinkingNumber();
        ClientEntity clientEntity=new ClientEntity();
        //是否是vip
        clientEntity.setIsVip(isVip);
        //登陆昵称
        clientEntity.setUserName(userName);
        //登陆名字
        clientEntity.setLoginName(loginName);
        //进入队列时间
        clientEntity.setEnterQueue(timestamp);
         if (isLeisure!=0){
        //有的话添加数据库  直接分配 并给客户发送 客服编号***为您服务
            //进入聊天室时间
            clientEntity.setEntranceTime(timestamp);
            //接待客服id
             clientEntity.setAudienceId(isLeisure);
             //等待时间
             clientEntity.setAwaitTime("00:00:00");
             //接待状态
             clientEntity.setState(ServiceConstant.CLIENT_RECEPTION);
             //添加数据库
             clientMapper.insertClient(clientEntity);
             //查看到当前客服id 的客服信息
             UserEntity userEntity= usersMapper.selectUserToId(isLeisure);
            return ServiceConstant.CLIENT_RECEPTION;
         }else{
         //没有空闲 进入等待 添加数据库
             //接待状态
             clientEntity.setState(ServiceConstant.CLIENT_WAIT);
             //添加数据库
             clientMapper.insertClient(clientEntity);
             //如果客户是vip

             return ServiceConstant.CLIENT_WAIT;
         }
    }

    /**
     * {
     * 	"identifying": 1,
     * 	"loginName": 123,
     * 	"textMessage": 123456
     * }
     */
    @Override
    public void send(String clientLoginName,int identifying,String loginName,String textMessage) {
          //identifying 0标识客户  1标识客服
        if (identifying==ServiceConstant.CUSTOMER_SERVICE){
            //判断客户是不是已经退出了

            //判断客户是不是已经退出了


            //如果退出自动回复

            ClientEntity clientEntity1=null;
            try {
                clientEntity1=clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_RECEPTION);
            }catch (Exception e){
                clientEntity1=null;
            }
            if (clientEntity1==null){
                //找到最近一次聊天的聊天id拿到loginName

                webSocketHandler.sendMessage(loginName,clientLoginName,new TextMessage("该客户已经退出，请点击保存并提交关闭对话"));
            }else{
                webSocketHandler.sendMessageToUser(clientEntity1.getLoginName(),new TextMessage(textMessage));
                Date date= new Date();
                Timestamp timestamp=new Timestamp(date.getTime());
                ChattingRecordsEntity chattingRecordsEntity=new ChattingRecordsEntity();
                chattingRecordsEntity.setClientId(clientEntity1.getId());
                chattingRecordsEntity.setRecords(textMessage);
                chattingRecordsEntity.setSender(ServiceConstant.CUSTOMER_SERVICE);
                chattingRecordsEntity.setStartTime(timestamp);
                System.out.println(timestamp);
                // 添加数据库 等待客服回复
                chattingRecordsMapper.insertChatRecords(chattingRecordsEntity);
            }
        }else{
            //给客服发送消息
            // 判断改客服在数据库的状态是否是等待 如果是
            ClientEntity clientEntity=null;
            //查看该客服等待状态时 数据库是否有值
            try {
                clientEntity= clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_WAIT);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("该用户不是等待状态");
            }
            if (null!=clientEntity){
                //等待状态
                isVipReplyMessage(loginName);

            }else if (null==clientEntity){
                //如果正在与客服聊天 判断客户对应的客服是谁，然后发送
                //拿到正在聊天的客户信息
                ClientEntity clientEntity1=clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_RECEPTION);
                //获取当前接待客服id是谁
                int userId= clientEntity1.getAudienceId();
                //查到客服信息
                UserEntity userEntity= usersMapper.selectUserToId(userId);
                TextMessage textMessage1=new TextMessage("{\"text\":\""+textMessage+"\",\"loginName\":\""+clientEntity1.getLoginName()+"\"}");
                webSocketHandler.sendMessageToUser(userEntity.getLoginName(),textMessage1);
                Date date= new Date();
                Timestamp timestamp=new Timestamp(date.getTime());
                ChattingRecordsEntity chattingRecordsEntity=new ChattingRecordsEntity();
                chattingRecordsEntity.setClientId(clientEntity1.getId());
                chattingRecordsEntity.setRecords(textMessage);
                chattingRecordsEntity.setSender(ServiceConstant.CLIENT);
                chattingRecordsEntity.setStartTime(timestamp);
                System.out.println(timestamp);
                // 添加数据库 等待客服回复
                chattingRecordsMapper.insertChatRecords(chattingRecordsEntity);
            }
        }
    }



    @Override
    public void sendImages(String clientLoginName,int identifying, String loginName, String textMessage) {
        //identifying 0标识客户  1标识客服
        if (identifying==ServiceConstant.CUSTOMER_SERVICE){
            //判断客户是不是已经退出了


            //如果退出自动回复

            ClientEntity clientEntity1=null;
            try {
                clientEntity1=clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_RECEPTION);
            }catch (Exception e){
                clientEntity1=null;
            }
            if (clientEntity1==null){
                webSocketHandler.sendMessage(loginName,clientLoginName,new TextMessage("该客户已经退出，请点击保存并提交关闭对话"));
            }else{
                webSocketHandler.sendPicture(loginName,clientEntity1.getLoginName(),new TextMessage(textMessage));
            }
            //给客户回复消息


//            Date date= new Date();
//            Timestamp timestamp=new Timestamp(date.getTime());
//            ChattingRecordsEntity chattingRecordsEntity=new ChattingRecordsEntity();
//            chattingRecordsEntity.setClientId(clientEntity1.getId());
//            chattingRecordsEntity.setRecords(textMessage);
//            chattingRecordsEntity.setSender(ServiceConstant.CUSTOMER_SERVICE);
//            chattingRecordsEntity.setStartTime(timestamp);
//            System.out.println(timestamp);
//            // 添加数据库 等待客服回复
//            chattingRecordsMapper.insertChatRecords(chattingRecordsEntity);
        }else{
            //给客服发送消息
            // 判断改客服在数据库的状态是否是等待 如果是
            ClientEntity clientEntity=null;
            //查看该客服等待状态时 数据库是否有值
            try {
                clientEntity= clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_WAIT);
            }catch (Exception e){
                System.out.println("该用户不是等待状态");
            }
            if (null!=clientEntity){
                //等待状态
                isVipReplyMessage(loginName);

            }else if (null==clientEntity){
                //如果正在与客服聊天 判断客户对应的客服是谁，然后发送
                ClientEntity clientEntity1=clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_RECEPTION);
                //获取当前接待客服id是谁
                int userId= clientEntity1.getAudienceId();
                //查到客服信息
                UserEntity userEntity= usersMapper.selectUserToId(userId);
                webSocketHandler.sendPicture(loginName,userEntity.getLoginName(),new TextMessage(textMessage));
//                Date date= new Date();
//                Timestamp timestamp=new Timestamp(date.getTime());
//                ChattingRecordsEntity chattingRecordsEntity=new ChattingRecordsEntity();
//                chattingRecordsEntity.setClientId(clientEntity1.getId());
//                chattingRecordsEntity.setRecords(textMessage);
//                chattingRecordsEntity.setSender(ServiceConstant.CLIENT);
//                chattingRecordsEntity.setStartTime(timestamp);
//                System.out.println(timestamp);
//                // 添加数据库 等待客服回复
//                chattingRecordsMapper.insertChatRecords(chattingRecordsEntity);
            }
        }
    }
    @Override
    public boolean CustomerServiceLine(HttpSession session) {
        String clientLoginName= (String) session.getAttribute(session.getId());
        UserEntity userEntity= usersMapper.selectUserToLoginName(clientLoginName);
        //查到客服id 查看他下面的所有正在聊天的用户
        List<ClientEntity> clientEntities=clientMapper.selectClientToAudienceId(userEntity.getId(),ServiceConstant.CLIENT_RECEPTION);
        //客服下线
        usersMapper.updateToUserStatusId(ServiceConstant.OFF_LINE,clientLoginName);
       //变为等待状态，重新分配
        for (int i=0;i<clientEntities.size();i++){

            ClientEntity clientEntity;
            clientEntity=clientEntities.get(i);
            clientEntity.setState(ServiceConstant.CLIENT_WAIT);
            clientMapper.updateClientState(clientEntity,ServiceConstant.CLIENT_RECEPTION);

        }

        // 将该客服的session 删除
        session.removeAttribute(session.getId());
        return true;
    }
    @Override
    public void isVipReplyMessage(String loginName) {
        // 判断该客户是否是vip
        int isVip=clientMapper.selectIdToVip(loginName,ServiceConstant.CLIENT_WAIT);
        if (ServiceConstant.IS_VIP==isVip){
            // 如果是vip 自动回复您是VIP用户 我们将尽快安排客服介入 请稍等...
            webSocketHandler.sendMessageToUser(loginName,new TextMessage( "<li class='message-text'> 尊敬的vip用户,客服繁忙,我们将尽快安排客服介入 等稍等...</li>"));
        }else if (ServiceConstant.NOT_VIP==isVip){
            // 如果不是VIP 自动回复 正在等待客服介入 请稍等...
            webSocketHandler.sendMessageToUser(loginName,new TextMessage("<li class='message-text'> 客服繁忙,我们将尽快安排客服介入 等稍等...</li>"));
        }
    }
    @Override
    public boolean CustomerServiceOnline(HttpSession session,String loginName) {
        //客服上线
        usersMapper.updateToUserStatusId(ServiceConstant.ON_LINE,loginName);

        //存入session
        session.setAttribute(session.getId(),loginName);
        // 改变数据库状态 并且将客服loginName存入session聊天找session 单向聊天
        return true;
    }

    @Override
    public int selectMinLinkingNumber() {
        //创建标识符记录是否有客服空闲
        int index=0;
        //创建map对象 存取客户聊天中人数
        Map<Integer, Integer> map = new HashMap<>();
        //查看所有在线的客服
        List<UserEntity> userEntities = usersMapper.selectUserToOnLine(ServiceConstant.ON_LINE);
        for (int i = 0; i < userEntities.size(); i++) {
            //查询在线客服的聊天人数
            int number = clientMapper.selectClientToLeisure(userEntities.get(i).getId(), ServiceConstant.CLIENT_RECEPTION);
            //将聊天人数存入map
            map.put(userEntities.get(i).getId(), number);
        }
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            //通过entry来拿key或者value
            Map.Entry entry = (Map.Entry) iter.next();
            int count= Integer.parseInt(String.valueOf(entry.getValue()));
            if (count < ServiceConstant.NUMBER_OF_CHAT){
                    index= (int) entry.getKey();
            }
        }
        return index;
    }

    @Override
    public List<ClientTreeJsonDTO> selectClientAll(int providerId) {
        //tree父级
        List<ClientTreeJsonDTO> clientTreeJsonDTOS=new ArrayList<>();
        //查询当前用户正在聊天的客户
        List<ClientEntity> clientEntities=  clientMapper.selectClient(providerId);
        //查询所有正在等待的客户
        List<ClientEntity> clientEntitiyWait= clientMapper.selectToWait();
        //查询当天聊天完成的客户
        List<ClientEntity> clientEntitiyFinish=clientMapper.selectToFinish(providerId);
        ClientTreeJsonDTO clientTreeJsonDTO=new ClientTreeJsonDTO();
        clientTreeJsonDTO.setId(1);
        clientTreeJsonDTO.setState("open");
        clientTreeJsonDTO.setText("等待开始");
        List<ClientTreeChildrenDTO> chlidren=new ArrayList<>();
        //循环所有等待客户
        for (int i=0;i<clientEntitiyWait.size();i++){
            //tree子集集合
            ClientTreeChildrenDTO clientTreeChildrenDTO=new ClientTreeChildrenDTO();
            ClientEntity clientEntity= clientEntitiyWait.get(i);
            clientTreeChildrenDTO.setId(clientEntity.getId());
            //如果该用户是VIP
            if (clientEntity.getIsVip()==ServiceConstant.IS_VIP){
                clientTreeChildrenDTO.setText(clientEntity.getUserName()+"-vip");
            }else{
                clientTreeChildrenDTO.setText(clientEntity.getUserName());
            }
            AttributesDTO attributesDTO1=new AttributesDTO();
            attributesDTO1.setCustomerId(clientEntity.getId());
            attributesDTO1.setCustomerName(clientEntity.getLoginName());
            clientTreeChildrenDTO.setAttributes(attributesDTO1);
            chlidren.add(clientTreeChildrenDTO);
            clientTreeJsonDTO.setChildren(chlidren);
        }
        clientTreeJsonDTOS.add(clientTreeJsonDTO);
        clientTreeJsonDTO=new ClientTreeJsonDTO();
        clientTreeJsonDTO.setId(2);
        clientTreeJsonDTO.setState("open");
        clientTreeJsonDTO.setText("对话中");
        chlidren=new ArrayList<>();
        //循环当前用户正在聊天的客户
      for (int i=0;i<clientEntities.size();i++){

          ClientTreeChildrenDTO clientTreeChildrenDTO=new ClientTreeChildrenDTO();
          ClientEntity clientEntity= clientEntities.get(i);
            clientTreeChildrenDTO.setId(clientEntity.getId());
            //如果该用户是VIP
            if (clientEntity.getIsVip()==ServiceConstant.IS_VIP){
                clientTreeChildrenDTO.setText(clientEntity.getUserName()+"-vip");
            }else{
                clientTreeChildrenDTO.setText(clientEntity.getUserName());
            }
            AttributesDTO attributesDTO1=new AttributesDTO();
            System.out.println();
            attributesDTO1.setCustomerId(clientEntity.getId());
            attributesDTO1.setCustomerName(clientEntity.getLoginName());
            clientTreeChildrenDTO.setAttributes(attributesDTO1);
            chlidren.add(clientTreeChildrenDTO);
            clientTreeJsonDTO.setChildren(chlidren);

      }
        clientTreeJsonDTOS.add(clientTreeJsonDTO);
        clientTreeJsonDTO=new ClientTreeJsonDTO();
        clientTreeJsonDTO.setId(3);
        clientTreeJsonDTO.setState("open");
        clientTreeJsonDTO.setText("已完成");
        chlidren=new ArrayList<>();
      //查询所有聊天完成的用户
      for (int i=0;i<clientEntitiyFinish.size();i++){
          System.out.println();
          ClientTreeChildrenDTO clientTreeChildrenDTO=new ClientTreeChildrenDTO();
          ClientEntity clientEntity= clientEntitiyFinish.get(i);
          DateFormat f_month=new SimpleDateFormat("dd");
         String entranceTime=  f_month.format(clientEntity.getEntranceTime().getTime());
          String thisTime=  f_month.format(new Date());
          if (entranceTime.equals(thisTime)){
              clientTreeChildrenDTO.setId(clientEntity.getId());
              //如果该用户是VIP
              if (clientEntity.getIsVip()==ServiceConstant.IS_VIP){
                  clientTreeChildrenDTO.setText(clientEntity.getUserName()+"-vip");
              }else{
                  clientTreeChildrenDTO.setText(clientEntity.getUserName());
              }
              AttributesDTO attributesDTO1=new AttributesDTO();
              attributesDTO1.setCustomerId(clientEntity.getId());
              attributesDTO1.setCustomerName(clientEntity.getLoginName());
              clientTreeChildrenDTO.setAttributes(attributesDTO1);
              chlidren.add(clientTreeChildrenDTO);
              clientTreeJsonDTO.setChildren(chlidren);
          }

      }
        clientTreeJsonDTOS.add(clientTreeJsonDTO);
        return clientTreeJsonDTOS;
    }

    @Override
    public List<ChattingRecordsEntity> selectChattingRecord(String loginName) {
        //查询当前用户所聊天的所有chattingrecords表id
       List<ClientEntity> clientEntities= clientMapper.selectClientToClientLoginName(loginName);
        //消息记录总集合
        List<ChattingRecordsEntity> chattingRecordsEntities=new ArrayList<>();
       //消息记录临时存储集合
        List<ChattingRecordsEntity> chattingRecordsEntitie=new ArrayList<>();
        //查询聊天记录，根据每个人不同加入时间戳
        for (int i=0;i<clientEntities.size();i++){

            ClientEntity clientEntity=  clientEntities.get(i);
            int s=clientEntity.getId();
            chattingRecordsEntitie=chattingRecordsMapper.listChattingRecordsByClientId(clientEntity.getId());
                for (int j=0;j<chattingRecordsEntitie.size();j++){
                    chattingRecordsEntities.add(chattingRecordsEntitie.get(j));
                }
        }
        //查询当前聊天的所有
        return chattingRecordsEntities;
    }
}
