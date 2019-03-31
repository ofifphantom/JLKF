package com.jl.mis.mapper;

import com.jl.mis.model.entity.ChattingRecordsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 聊天信息数据库映射
 *
 * @author 郑国超
 * @Version 1.0
 * @Data 2018/5/26 9:26
 */
@Repository
public interface ChattingRecordsMapper {
    /**
     * 添加一条聊天记录
     *
     * @param chattingRecordsEntity 聊天记录实体类
     */
    void insertChatRecords(@Param("ChattingRecordsEntity") ChattingRecordsEntity chattingRecordsEntity);
    List<ChattingRecordsEntity> selectAll();
    /**
     * 根据咨询 id 查询聊天记录
     * @param clientId 咨询 id
     * @return 聊天记录列表
     */
    List<ChattingRecordsEntity> listChattingRecordsByClientId(@Param("clientId")Integer clientId);

    /**
     * 删除七天以前的聊天数据
     */
    void delSevenDaysAgo();
}
