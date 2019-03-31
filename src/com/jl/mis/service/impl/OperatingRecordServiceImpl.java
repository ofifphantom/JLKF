package com.jl.mis.service.impl;

import com.jl.mis.dto.OperatingRecordDTO;
import com.jl.mis.mapper.OperatingModelMapper;
import com.jl.mis.mapper.OperatingRecordMapper;
import com.jl.mis.mapper.UsersMapper;
import com.jl.mis.model.entity.OperatingModelEntity;
import com.jl.mis.model.entity.OperatingRecordEntity;
import com.jl.mis.model.entity.UserEntity;
import com.jl.mis.service.OperatingRecordService;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志的所有业务
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/23 11:24
 */
@Service
public class OperatingRecordServiceImpl implements OperatingRecordService {
    @Autowired
    private OperatingRecordMapper operatingRecordMapper;
    @Autowired
    private OperatingModelMapper operatingModelMapper;
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public List<OperatingRecordDTO> selectOperatingRecord() {
        //获取所有的操作记录
        List<OperatingRecordEntity> operatingRecordEntities = operatingRecordMapper.selectOperatingRecord(null, 0,0,10);
        //获取所有的操作类型记录
        List<OperatingModelEntity> modelEntities = operatingModelMapper.selectOperatingModel();
        //获取所有的操作人集合
        List<UserEntity> userEntities = usersMapper.selectUser();
        //获得具体数据返回集合
        List<OperatingRecordDTO> dtos = convertTypeToOperatingRecordDto(modelEntities, operatingRecordEntities, userEntities);
        return dtos;
    }

    @Override
    public DataTables selectOperatingRecordToTypeId(String provider, int modelType, Integer defined, HttpServletRequest request) {
        DataTables dataTables = new DataTables();
        List<OperatingRecordEntity> operatingRecordEntities = null;
        String provider_id = null;
        List<Integer> ids = null;
        if(null != provider && !provider.equals("")){
            provider = "\'%" + provider + "%\'";
            ids = usersMapper.selectUserIdByUserName(provider);
            if(null != ids && ids.size() > 0){
                if(ids.size() == 1){
                    provider_id = ids.get(0).toString();
                }else {
                    provider_id = ids.get(0).toString();
                    for(int i = 1;i < ids.size();i++){
                        provider_id = provider_id + "," + ids.get(i).toString();
                    }
                }
            }
        }
        if(null == ids || ids.size() > 0){
            Integer startNumber = Integer.valueOf(request.getParameter("start"));
            Integer lengthNumber = Integer.valueOf(request.getParameter("length"));
            operatingRecordEntities = operatingRecordMapper.selectOperatingRecord(provider_id, modelType,startNumber,lengthNumber);
            List<OperatingModelEntity> modelEntities = operatingModelMapper.selectOperatingModel();
            List<UserEntity> userEntities = usersMapper.selectUser();
            List<OperatingRecordDTO> dtos = convertTypeToOperatingRecordDto(modelEntities, operatingRecordEntities, userEntities);
            Integer count = operatingRecordMapper.countOperationRecord(provider_id,modelType);
            dataTables.setiTotalDisplayRecords(count);
            dataTables.setiTotalRecords(dtos.size());
            dataTables.setData(dtos);
        }else {
            dataTables.setiTotalDisplayRecords(Integer.valueOf(0));
            dataTables.setiTotalRecords(Integer.valueOf(0));
            dataTables.setData(new ArrayList<>());
        }
        return dataTables;
    }

    @Override
    public List<OperatingModelEntity> selectOperatingModel() {
        List<OperatingModelEntity> modelEntities = operatingModelMapper.selectOperatingModel();
        return modelEntities;
    }

    @Override
    public boolean insertOperatingRecord(OperatingRecordEntity operatingRecordEntity) {
        try {
            operatingRecordMapper.insertOperatingRecord(operatingRecordEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<OperatingRecordDTO> convertTypeToOperatingRecordDto(List<OperatingModelEntity> modelEntities, List<OperatingRecordEntity> operatingRecordEntities, List<UserEntity> userEntities) {
        //创建Dto对象来进行类型转换
        OperatingRecordDTO operatingRecordDto;
        //创建返回值对象集合
        List<OperatingRecordDTO> operatingRecordDtos = new ArrayList<>();
        for (int i = 0; i < operatingRecordEntities.size(); i++) {
            //获取当前循环中集合的下标 并赋值给 operatingRecordEntity
            OperatingRecordEntity operatingRecordEntity = operatingRecordEntities.get(i);
            //保证每次循环Dto为空
            operatingRecordDto = new OperatingRecordDTO();
            //赋值(一些不用变动的值)
            operatingRecordDto.setOperatingIntroduce(operatingRecordEntity.getOperatingIntroduce());
            operatingRecordDto.setCreateTime(DateUtil.convertDateToString(operatingRecordEntity.getCreateTime(),"yyyy-MM-dd HH:mm:ss"));
            operatingRecordDto.setId(operatingRecordEntity.getId());
            operatingRecordDto.setShowOperatingRecord(Integer.valueOf(i + 1));
            //遍历操作日志类型集合拿出类型
            for (int j = 0; j < modelEntities.size(); j++) {
                //拿出下标j 赋给 操作日志类型对象
                OperatingModelEntity operatingModelEntity = modelEntities.get(j);
                if (operatingModelEntity.getId() == operatingRecordEntity.getOperatingModelTypeId()) {
                    operatingRecordDto.setOperatingModel(operatingModelEntity.getOperatingModelName());
                }
            }
            //遍历用户对象
            for (int k = 0; k < userEntities.size(); k++) {
                UserEntity userEntity = userEntities.get(k);
                if (userEntity.getId() == operatingRecordEntity.getProviderId()) {
                    operatingRecordDto.setProviderName(userEntity.getUserName());
                    operatingRecordDto.setProvideRnickName(userEntity.getLoginName());
                }
            }
            operatingRecordDtos.add(operatingRecordDto);
        }

        return operatingRecordDtos;
    }
}
