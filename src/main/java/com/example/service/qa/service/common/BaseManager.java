package com.example.service.qa.service.common;

import com.example.service.qa.dao.clearrecord.CardClearResultMapper;
import com.example.service.qa.model.BaseContext;
import com.example.service.qa.model.clearrecord.ClearAllDataRecord;
import com.example.service.qa.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Random;

@Component
public class BaseManager {


    @Autowired
    private CardClearResultMapper cardClearResultMapper;

    /**
     * 创建执行结果记录
     */
    public Long createRecord(BaseContext context) {
        ClearAllDataRecord record = new ClearAllDataRecord();
        // 清除类型
        record.setClearType(context.getClearType());
        // 集团id
        record.setGroupID(context.getGroupID());
        // 工单id
        record.setTicketNo(context.getTicketNo());
        // 主键id
        int nextInt = new Random().nextInt(30);
        SnowflakeIdUtils idUtils = new SnowflakeIdUtils(nextInt, nextInt % 7);
        long itemID = idUtils.nextId();
        record.setItemID(itemID);
        //
        context.setRecordItemId(record.getItemID());
        // 请求ip
        record.setIpAddr(context.getIpAddr());
        // 文件名称
        record.setFileName(context.getFileName());
        // 链路id
        record.setTraceID(context.getTraceID());
        // 操作人
        record.setOperator(context.getOperator());
        // 创建时间
        record.setCreateStamp(new Timestamp(System.currentTimeMillis()));
        // 记录入库
        cardClearResultMapper.insertClearRecord(record);
        return itemID;
    }

    public void updateRecord(Long itemID, Integer result) {
        // 给record更新
        final Timestamp actionStamp = new Timestamp(System.currentTimeMillis());
        cardClearResultMapper.updateClearRecordStatus(itemID, result, actionStamp);
    }

    public void updateRecord(Long itemID, BaseContext context) {
        // 给record更新
        final Timestamp actionStamp = new Timestamp(System.currentTimeMillis());
        cardClearResultMapper.updateClearRecord(itemID, actionStamp, context);
    }
}
