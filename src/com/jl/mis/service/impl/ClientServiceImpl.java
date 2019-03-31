package com.jl.mis.service.impl;

import com.jl.mis.dto.ChattingDTO;
import com.jl.mis.dto.ChattingRecordsDTO;
import com.jl.mis.dto.ClientDTO;
import com.jl.mis.mapper.ChattingRecordsMapper;
import com.jl.mis.mapper.ClientMapper;
import com.jl.mis.mapper.UserManagerMapper;
import com.jl.mis.model.entity.ChattingRecordsEntity;
import com.jl.mis.model.entity.ClientEntity;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.ClientService;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.DateUtil;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.PageTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private UserManagerMapper userManagerMapper;
    @Autowired
    private ChattingRecordsMapper chattingRecordsMapper;
    @Override
    public DataTables listClient(Integer defined, String consultNumber, String userName
            , String serviceName, String startTime, HttpServletRequest request) {
        DataTables dataTables = new DataTables();
        List<ClientDTO> clientDTOList = null;
        // 获取分页帮助类
        PageTools pageTools = GetSessionUtil.GetSessionClientDataTables(request);
        if(null != pageTools){
            String userServiceIds = null;
            // 判断客服名称是否有误
            if(null != serviceName && !serviceName.equals("")){
                // 获取客服 id String形式
                userServiceIds = getUserIds(serviceName);
            }
            // 判断是否为客服
            if(GetSessionUtil.GetSessionUserTypeId(request) == 5){
               userServiceIds = Integer.toString(GetSessionUtil.GetSessionUserId(request));
            }
            if(null == userServiceIds || !("".equals(userServiceIds))){
                String queryParams = getCilentConditionString(consultNumber,userName,userServiceIds,startTime);
                Integer startNumber = Integer.valueOf(request.getParameter("start"));
                Integer lengthNumber = Integer.valueOf(request.getParameter("length"));
                // 获取咨询列表
                List<ClientEntity> clientEntityList = clientMapper.listClientByConditionLimit(startNumber,lengthNumber,queryParams);
                // 将信息整合
                clientDTOList = convertClientDTO(clientEntityList);
                pageTools.setData(clientDTOList);
                dataTables.setiTotalDisplayRecords(clientMapper.listClientByCondition(queryParams).size());
                dataTables.setiTotalRecords(clientDTOList.size());
                dataTables.setData(clientDTOList);
            }else {
                dataTables.setiTotalDisplayRecords(Integer.valueOf(0));
                dataTables.setiTotalRecords(Integer.valueOf(0));
                dataTables.setData(new ArrayList<>());
            }
        }
        return dataTables;
    }

    @Override
    public ChattingRecordsDTO listChattingRecords(Integer clientId) {
        ChattingRecordsDTO chattingRecordsDTO = null;
        if(null != clientId && clientId > 0){
            ClientEntity clientEntity = clientMapper.getClientByClientId(clientId);
            if(null != clientEntity){
                UserEntity userEntity = userManagerMapper.getUserByUserId(clientEntity.getAudienceId());
                List<ChattingRecordsEntity> chattingRecordsEntityList = chattingRecordsMapper.listChattingRecordsByClientId(clientId);
                chattingRecordsDTO = convertChattingRecordsDTO(chattingRecordsEntityList,clientEntity,userEntity);
            }
        }
        return chattingRecordsDTO;
    }

    @Override
    public boolean outPutExcel(HttpServletRequest request, HttpServletResponse response) {
        boolean result = false;
        PageTools pageTools = GetSessionUtil.GetSessionClientDataTables(request);
        if(null != pageTools.getData()){
            List<String[]> dataList = new ArrayList<>();
            List<ClientDTO> clientDTOList = (List<ClientDTO>) pageTools.getData();
            for(ClientDTO clientDTO : clientDTOList){
                String[] strings = {
                        clientDTO.getConsultNumber(), clientDTO.getUserName(), clientDTO.getIsVip(),clientDTO.getServiceName()
                        ,clientDTO.getCreateTime(),clientDTO.getConsultTime()
                };
                dataList.add(strings);
            }
            result = OutPutExcelUtil.outPutExcel(dataList,1,response,request);
        }
        return result;
    }

    /**
     * 获取客服的 id String 类型
     * @param serviceName 客服名称
     * @return
     */
    private String getUserIds(String serviceName){
        String ids = "";
        if(null != serviceName && !serviceName.equals("")){
            List<UserEntity> userEntityList = userManagerMapper.listUserByUserName("\'%"+serviceName+"%\'");
            if(null != userEntityList && userEntityList.size() > 0){
                if(userEntityList.size() > 1){
                    ids = userEntityList.get(0).getId().toString();
                    for(int i = 1; i < userEntityList.size();i++){
                        ids = ids + "," + userEntityList.get(i).getId();
                    }
                }else {
                    ids = userEntityList.get(0).getId().toString();
                }
            }
        }
        return ids;
    }
    /**
     * 将查询条件转换为 String
     * @param consultNumber 咨询编号
     * @param userName 用户姓名
     * @param serviceId 客服 id
     * @param startTime 开始时间
     * @return 查询条件
     */
    private String getCilentConditionString(String consultNumber,String userName,String serviceId,String startTime){
        String condition = "";
        if(null != consultNumber && !consultNumber.equals("")){
            condition = " AND consult_number LIKE \'%" + consultNumber + "%\'";
        }
        if(null != userName && !userName.equals("")){
            condition = condition + " AND user_name LIKE \'%" + userName + "%\'";
        }
        if(null != serviceId && !serviceId.equals("")){
            condition = condition + " AND audience_id IN (\'" + serviceId + "\')";
        }
        if(null != startTime && !startTime.equals("")){
            condition = condition + " AND entrance_time >= '" + startTime+"'";
        }
        return condition;
    }

    /**
     * 转换整合咨询信息
     * @param clientEntityList 咨询信息
     * @return 整合后的咨询信息
     */
    private List<ClientDTO> convertClientDTO(List<ClientEntity> clientEntityList){
        List<ClientDTO> clientDTOList = new ArrayList<>();
        if(null != clientEntityList && clientEntityList.size() > 0){
            clientDTOList = new ArrayList<>();
            List<UserEntity> userEntityList = userManagerMapper.listUserAll();
            for(ClientEntity clientEntity : clientEntityList){
                if(null != clientEntity.getConsultNumber() && !"".equals(clientEntity.getConsultNumber())){
                    ClientDTO clientDTO = new ClientDTO();
                    clientDTO.setUserName(clientEntity.getUserName());
                    clientDTO.setConsultNumber(clientEntity.getConsultNumber());
                    clientDTO.setCreateTime(DateUtil.convertDateToString(clientEntity.getEntranceTime(),DateUtil.DATE_HOUR_FORMAT));
                    clientDTO.setConsultTime(clientEntity.getConsultTime());
                    clientDTO.setId(clientEntity.getId());
                    clientDTO.setIsVip(clientEntity.getIsVip() == 1 ? "是" : "否");
                    for(UserEntity userEntity : userEntityList){
                        if(clientEntity.getAudienceId() == userEntity.getId()){
                            clientDTO.setServiceName(userEntity.getUserName());
                            break;
                        }
                    }
                    clientDTOList.add(clientDTO);
                }
            }
        }
        return clientDTOList;
    }

    /**
     * 整合咨询信息和聊天记录
     * @param chattingRecordsEntityList 聊天记录
     * @param clientEntity 咨询信息
     * @param userEntity 客服信息
     * @return 整合后的信息
     */
    private ChattingRecordsDTO convertChattingRecordsDTO(List<ChattingRecordsEntity> chattingRecordsEntityList, ClientEntity clientEntity, UserEntity userEntity){
        ChattingRecordsDTO chattingRecordsDTO = new ChattingRecordsDTO();
        List<ChattingDTO> chattingDTOList = new ArrayList<>();
        if(null != chattingRecordsEntityList && chattingRecordsEntityList.size() > 0){
            if(null != clientEntity && null != userEntity){
                    chattingRecordsDTO = new ChattingRecordsDTO();
                    chattingDTOList = new ArrayList<>();
                    chattingRecordsDTO.setConsultNumber(clientEntity.getConsultNumber());
                    chattingRecordsDTO.setUserName(clientEntity.getUserName());
                    chattingRecordsDTO.setIsVip(clientEntity.getIsVip() == 1 ? "是" : "否");
                    chattingRecordsDTO.setServiceName(userEntity.getUserName());
                    chattingRecordsDTO.setServiceLoginName(userEntity.getLoginName());
                    chattingRecordsDTO.setEnterQueueTime(DateUtil.dateToDateString(clientEntity.getEnterQueue(),DateUtil.DATE_HOUR_FORMAT));
                    chattingRecordsDTO.setAwaitTime(clientEntity.getAwaitTime());
                    chattingRecordsDTO.setEntranceTime(DateUtil.dateToDateString(clientEntity.getEntranceTime(),DateUtil.DATE_HOUR_FORMAT_SECOND));
                    chattingRecordsDTO.setFinishTime(DateUtil.dateToDateString(clientEntity.getFinishTime(),DateUtil.DATE_HOUR_FORMAT_SECOND));
                    chattingRecordsDTO.setConsultTime(clientEntity.getConsultTime());
                    for(ChattingRecordsEntity chattingRecordsEntity : chattingRecordsEntityList){
                        ChattingDTO chattingDTO = new ChattingDTO();
                        chattingDTO.setRecords(chattingRecordsEntity.getRecords());
                        chattingDTO.setSender(chattingRecordsEntity.getSender());
                        chattingDTO.setStartTime(DateUtil.dateToDateString(chattingRecordsEntity.getStartTime(),DateUtil.DATE_HOUR_FORMAT));
                        chattingDTOList.add(chattingDTO);
                    }
                    chattingRecordsDTO.setChattingRecordsEntityList(chattingDTOList);
            }
        }
        return chattingRecordsDTO;
    }
}
