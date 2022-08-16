package com.example.service.qa.controller;

import com.example.service.qa.model.redisinfo.ClearRedisRecord;
import com.example.service.qa.model.response.RedisResponse;
import com.example.service.qa.service.impl.redisinfo.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author su
 */
@RestController
@RequestMapping({"/redis"})
public class RedisController {

    @Autowired
    private RedisService redisService;


    @RequestMapping(value={"/queryRecord"}, method={RequestMethod.POST})
    public RedisResponse queryRecord(@RequestBody ClearRedisRecord data){
        return redisService.queryRedisRecord(data);
    }

    @RequestMapping(value={"/addRecord"}, method={RequestMethod.POST})
    public RedisResponse addRecord(@RequestBody ClearRedisRecord data){
        return redisService.saveRedisRecord(data);
    }

    @RequestMapping(value={"/updateRecord"}, method={RequestMethod.POST})
    public RedisResponse updateRecord(@RequestBody ClearRedisRecord data){
        return redisService.updateRedisRecord(data);
    }

    @RequestMapping(value={"/setRedisKeyValue"}, method={RequestMethod.POST})
    public RedisResponse setRedisKeyValue(@RequestBody ClearRedisRecord data){
        return redisService.setRedisKeyValue(data);
    }

    @RequestMapping(value={"/queryRedisKeyValue"}, method={RequestMethod.POST})
    public RedisResponse queryRedisKeyValue(@RequestBody ClearRedisRecord data){
        return redisService.queryRedisKeyValue(data);
    }
}
