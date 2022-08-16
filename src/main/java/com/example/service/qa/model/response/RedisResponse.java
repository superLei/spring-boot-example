package com.example.service.qa.model.response;

import com.example.service.qa.model.redisinfo.ClearRedisRecord;
import com.example.service.qa.model.ResultInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 小流量接口响应
 *
 * @author su
 */
public class RedisResponse extends ResultInfo {
    @Setter
    @Getter
    private List<ClearRedisRecord> records;

}
