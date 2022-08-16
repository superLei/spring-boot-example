package com.example.service.qa.dao.redisinfo;


import com.example.service.qa.model.redisinfo.ClearRedisRecord;

import java.util.List;

/**
 *  @author sulei
 */
public interface ClearRedisRecordMapper {

    int insertClearRedisRecord(ClearRedisRecord object);

    int updateClearRedisRecord(ClearRedisRecord object);

    List<ClearRedisRecord> queryClearRedisRecord(ClearRedisRecord object);

    ClearRedisRecord queryClearRedisRecordLimit1(ClearRedisRecord object);

}