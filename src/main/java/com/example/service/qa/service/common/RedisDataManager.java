package com.example.service.qa.service.common;

import com.example.service.qa.dao.redisinfo.ClearRedisRecordMapper;
import com.example.service.qa.model.redisinfo.ClearRedisRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author su
 */
@Service
public class RedisDataManager {
    @Autowired
    private ClearRedisRecordMapper redisRecordMapper;

    /**
     * 查询redis key存储记录
     * @param data
     * @return
     */
    public List<ClearRedisRecord> queryRedisData(ClearRedisRecord data){
        return redisRecordMapper.queryClearRedisRecord(data);
    }

    /**
     * 添加redis key记录
     * @param data
     * @return
     */
    public int addRedisData(ClearRedisRecord data){
        return redisRecordMapper.insertClearRedisRecord(data);
    }

    /**
     * 更新redis key记录
     * @param data
     * @return
     */
    public int updateRedisData(ClearRedisRecord data){
        return redisRecordMapper.updateClearRedisRecord(data);
    }
}
