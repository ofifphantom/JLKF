package com.jl.mis.controller;
import com.jl.mis.dto.ClientTreeJsonDTO;
import com.jl.mis.mapper.ClientMapper;
import com.jl.mis.model.entity.ChattingRecordsEntity;
import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.service.PreSaleChatService;
import com.jl.mis.utils.ChatNumberGeneration;
import com.jl.mis.utils.DateTime;
import com.jl.mis.utils.ServiceConstant;
import com.jl.mis.websocket.ResourcesFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 聊天模块
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 16:23
 */
@Controller
public class PreSaleController {
   @Autowired
   private PreSaleChatService preSaleChatService;
   @Autowired
   private ClientMapper clientMapper;
   @Autowired
   private ResourcesFile resourcesFile;
   /**
    * 客户过来拿到接口
    *
    * @param request
    * @return
    */
    @RequestMapping("/chatClient")
    public String login(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");//必须写在第一位，因为采用这种方式去读取数据，否则数据会出错。
        response.setContentType("text/html;charset=utf-8");//设置传过去的页面显示的编码
        String userName= request.getParameter("userName");
        String loginName= request.getParameter("loginName");
        String userId= request.getParameter("clientId");
        request.getSession().setAttribute("clientId",userId);
        request.getSession().setAttribute("userId",userId);
        Integer isVip=Integer.parseInt(request.getParameter("isVip"));
        request.getSession().setAttribute("clientLoginName",loginName);
        //设置websocket连接地址
        request.getSession().setAttribute("webSocketAddress",resourcesFile.getwebSocketAddress());
        //查询该用户是否有正在等待卡bug
        List<ClientEntity> clientEntitiesWait= clientMapper.listToChatting(loginName,ServiceConstant.CLIENT_WAIT);
      //查询这个loginName是不是有正在聊天的（卡bug的）
        List<ClientEntity> clientEntities= clientMapper.listToChatting(loginName,ServiceConstant.CLIENT_RECEPTION);
      //修改消息
        for (int i=0;i<clientEntities.size();i++){
          ClientEntity clientEntity=  clientEntities.get(i);
          clientEntity.setState(ServiceConstant.CLIENT_FINISH);
          clientMapper.updateClientState(clientEntity,ServiceConstant.CLIENT_RECEPTION);
        }
        for (int i=0;i<clientEntitiesWait.size();i++){
            ClientEntity clientEntity=  clientEntitiesWait.get(i);
            clientEntity.setState(ServiceConstant.CLIENT_FINISH);
            clientMapper.updateClientState(clientEntity,ServiceConstant.CLIENT_WAIT);
        }
        preSaleChatService.insertClientAndSendSession(request.getSession(),userName,loginName,isVip);
        return "chat";
    }
    /**
     * 客服上线
     *
     * @param session
     * @return
     */
    @RequestMapping("onLine")
    public String online(HttpSession session){
        String loginName= (String) session.getAttribute("loginName");
        preSaleChatService.CustomerServiceOnline(session,loginName);
        //展示这个客服的常用语
        return "/chatProvider";
    }
    /**
     * 客服下线
     *
     * @param session
     * @return
     */
    @RequestMapping("offLine")
    @ResponseBody
    public Boolean offline(HttpSession session){
    preSaleChatService.CustomerServiceLine(session);
  //客服下线页面
    return true;
    }

    /**
     * 查看客户状态
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("seeClient")
    public List<ClientTreeJsonDTO> seeClient(HttpSession session){
        int providerId= (int) session.getAttribute("userId");
       return preSaleChatService.selectClientAll(providerId);
    }
    /**
     * 查看当前客户的聊天记录
     */
    @ResponseBody
    @RequestMapping("seeChattingRecords")
    public List<ChattingRecordsEntity> seeChattingRecords(HttpServletRequest request){
       String loginName= request.getParameter("loginName");
        System.out.println(preSaleChatService.selectChattingRecord(loginName));
       return preSaleChatService.selectChattingRecord(loginName);
    }

    /**
     * 保存并提交
     */
    @ResponseBody
    @RequestMapping("SaveAndsubmit")
    public String saveAndSubmit(HttpServletRequest request){
     String remark= request.getParameter("remark");
     String loginName=request.getParameter("id");
     //查到当前登陆名 并且在聊天的id
     ClientEntity clientEntity=clientMapper.selectClientToLoginName(loginName,ServiceConstant.CLIENT_RECEPTION);
     clientEntity.setRemark(remark);
        Timestamp timestamp=new Timestamp(new Date().getTime());

      //修改状态为售前完成
     clientEntity.setState(ServiceConstant.CLIENT_FINISH);
      //聊天结束时间
     clientEntity.setFinishTime(timestamp);
        clientEntity.setConsultNumber("A"+ChatNumberGeneration.chatNumberGeneration());
      //聊天时间
     clientEntity.setConsultTime(DateTime.timestamp(timestamp, clientEntity.getEntranceTime()));

     clientMapper.updateClientByState(clientEntity,ServiceConstant.CLIENT_RECEPTION);
        return "";

    }
    /**
     * 返回WebSocket路径
     */
    @ResponseBody
    @RequestMapping("getWebSocketAddress")
    public String getWebSocketAddress() {
        String webSocketAddress= resourcesFile.getwebSocketAddress();
        String name ="{dataAddress:\""+webSocketAddress+"\"}";
        return "{\"webSocketAddress\":\""+webSocketAddress+"\"}";
    }
    /**
     * 返回远程数据路径
     */
    @ResponseBody
    @RequestMapping("getDataAddress")
    public String getDataAddress() {
       String dataAddress= resourcesFile.getDataAddress();

    	return "{\"dataAddress\":\""+dataAddress+"\"}";
    }


    @ResponseBody
    @RequestMapping("getHttps")
    public String getHttps() {
        String https= resourcesFile.getHttps();

        return "{\"getHttps\":\""+https+"\"}";
    }

}
