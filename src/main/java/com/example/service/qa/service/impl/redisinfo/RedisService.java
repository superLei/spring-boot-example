package com.example.service.qa.service.impl.redisinfo;

import com.example.service.qa.enums.RedisKeyEnums;
import com.example.service.qa.common.BusinessException;
import com.example.service.qa.exceptions.ErrorCode;
import com.example.service.qa.model.redisinfo.ClearRedisRecord;
import com.example.service.qa.model.response.RedisResponse;
import com.example.service.qa.service.common.RedisDataManager;
import com.example.service.qa.utils.CommonUtils;
import com.example.service.qa.utils.RedisCommonUtils;
import com.example.service.qa.utils.SnowflakeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 小流量增删除查改功能
 * @author su
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisCommonUtils redisCommonUtils;

    @Autowired
    private RedisDataManager redisDataManager;


    /**
     * 保存redis key记录
     * @return
     */
    public RedisResponse saveRedisRecord(ClearRedisRecord data) {
        // 设置主键ID
        SnowflakeIdUtils idUtils = new SnowflakeIdUtils(1,2);
        data.setItemID(idUtils.nextId());

        int num = redisDataManager.addRedisData(data);
        if(!CommonUtils.verifyBigThanAndEqualZero(num)){
            throw new BusinessException(ErrorCode.DATABASE_UPDATE_ERROR);
        }
        return new RedisResponse();
    }

    /**
     * 更新redis key记录
     * @return
     */
    public RedisResponse updateRedisRecord(ClearRedisRecord data) {
        int num = redisDataManager.updateRedisData(data);
        if(!CommonUtils.verifyBigThanAndEqualZero(num)){
            throw new BusinessException(ErrorCode.DATABASE_UPDATE_ERROR);
        }
        return new RedisResponse();
    }

    /**
     * 查询redis key记录
     * @return
     */
    public RedisResponse queryRedisRecord(ClearRedisRecord data) {
        List<ClearRedisRecord> clearRedisRecords = redisDataManager.queryRedisData(data);
        if(CommonUtils.isEmpty(clearRedisRecords)){
            throw new BusinessException(ErrorCode.DATABASE_UPDATE_ERROR);
        }
        RedisResponse redisResponse = new RedisResponse();
        redisResponse.setRecords(clearRedisRecords);
        return redisResponse;
    }

    /**
     * 设置 redis key值
     * @param data
     * @return
     */
    public RedisResponse setRedisKeyValue(ClearRedisRecord data){
        if(!CommonUtils.notNull(data.getRedisKey()) || CommonUtils.isBlank(data.getRedisKey())){
            throw new BusinessException(ErrorCode.REDIS_KEY_NOT_EXIST);
        }
        if(!CommonUtils.notNull(data.getRedisValue()) ){
            throw new BusinessException(ErrorCode.REDIS_KEY_NOT_EXIST);
        }
        if(!CommonUtils.notNull(data.getRedisType()) ){
            throw new BusinessException(ErrorCode.REDIS_KEY_NOT_EXIST);
        }
//        boolean set = redisCommonUtils.set(data.getRedisKey(), (String) data.getRedisValue());
        boolean set = redisCommonUtils.set(data.getRedisKey(), (String) data.getRedisValue(), data.getRedisType());
        if(!CommonUtils.verifiyBooleanValue(set)){
            throw new BusinessException(ErrorCode.REDIS_KEY_NOT_EXIST);
        }
        return new RedisResponse();
    }

    /**
     * 查询 redis key值
     * @param data
     * @return
     */
    public RedisResponse queryRedisKeyValue(ClearRedisRecord data){
        if(CommonUtils.isBlank(data.getRedisKey())){
            return new RedisResponse();
        }
        List<ClearRedisRecord> clearRedisRecords = redisDataManager.queryRedisData(data);
        if(CommonUtils.isEmpty(clearRedisRecords)){
            throw new BusinessException(ErrorCode.DATABASE_UPDATE_ERROR);
        }
        clearRedisRecords.forEach(x -> {
            verifyRedisRecordParams(x);
            if(x.getRedisType().equals(RedisKeyEnums.SET.getRedisType())){
                x.setRedisValue(redisCommonUtils.setMembers(x.getRedisKey()));
            }else if(x.getRedisType().equals(RedisKeyEnums.STRING.getRedisType())){
                x.setRedisValue(redisCommonUtils.get(x.getRedisKey()));
            }
        });
        RedisResponse redisResponse = new RedisResponse();
        redisResponse.setRecords(clearRedisRecords);
        return redisResponse;
    }

    /**
     *  校验入参
     * */
    private void verifyRedisRecordParams(ClearRedisRecord redisRecord) {
        if(CommonUtils.isNull(redisRecord.getRedisType()) || CommonUtils.isNull(redisRecord.getRedisKey())){
            log.error("redis key or redisType is null");
            throw new BusinessException(ErrorCode.REDIS_KEY_NOT_EXIST);
        }
    }


}
