package com.jl.mis.timedtask;

import com.jl.mis.mapper.ChattingRecordsMapper;
import com.jl.mis.mapper.ClientMapper;
import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.service.impl.PreSaleChatServiceImpl;
import com.jl.mis.utils.DateTime;
import com.jl.mis.utils.RegularBusiness;
import com.jl.mis.utils.ServiceConstant;
import com.jl.mis.websocket.ResourcesFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

/**
 * 任务调度
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/29 15:33
 */
@Component
public class TimeTaskToPreSale {
    @Autowired
    private PreSaleChatServiceImpl preSaleChatService;
    @Autowired
    private ChattingRecordsMapper chattingRecordsMapper;
    @Autowired
    private ResourcesFile resourcesFile;
    @Autowired
    private ClientMapper clientMapper;


    /**
     * 每五秒执行一次 监听等待客户
     */
    @Scheduled(cron = "0/3 * * * * ?")   //每5秒执行一次
    public void aTask() {
        //查看所有等待的用户
        //创建对象保存一个等待中的用户
        ClientEntity clientEntity1=null;
        List<ClientEntity> clientWait;
        //如果有等待用户
        // 查看是否有VIP用户在等待 VIP优先
        clientWait=clientMapper.findByWait(ServiceConstant.CLIENT_WAIT);
        int state=0;
        for (int i=0;i<clientWait.size();i++){
            ClientEntity clientEntity=clientWait.get(i);
            if (clientEntity.getIsVip()==ServiceConstant.IS_VIP){
                state=1;
                clientEntity1=clientEntity;
            }
        }
        if (state==0){
            for (int i=0;i<clientWait.size();i++){
                ClientEntity clientEntity=clientWait.get(i);
                if (clientEntity.getIsVip()==ServiceConstant.NOT_VIP){
                  clientEntity1=clientEntity;
                }
            }
        }

        if (clientEntity1 == null) {
            //没有用户等待
        } else {
            //表示有人等待
            //查看最闲的客服id
            int isLeisure = preSaleChatService.selectMinLinkingNumber();
            if (isLeisure != 0) {
                //有空闲
                Timestamp timestamp = DateTime.getTimestamp();
                //进入聊天室时间
                clientEntity1.setEntranceTime(timestamp);
                //接待客服id
                clientEntity1.setAudienceId(isLeisure);
                //计算等待时间
                String waitTime = DateTime.timestamp(timestamp, clientEntity1.getEnterQueue());
                //等待时间
                clientEntity1.setAwaitTime(waitTime);
                //修改数据库
                clientMapper.updateClientNotWait(clientEntity1,ServiceConstant.CLIENT_RECEPTION);
            } else {
                //没空闲不做任何处理
            }
        }
    }

    /**
     * 每晚2点执行 删除当天img 创建文件 删除七天前聊天记录s
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void fileImg(){
        RegularBusiness regularBusiness=new RegularBusiness();
        String path=resourcesFile.getFileName();
        boolean  result = regularBusiness.DeleteFolder(path);
        System.out.println(result);
        File file=new File(path);
        file.mkdirs();
        clientMapper.delClientSevenDaysAgo();
        chattingRecordsMapper.delSevenDaysAgo();
    }
}