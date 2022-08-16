package com.example.service.qa.enums;

import lombok.Getter;

/**
 * @author su
 */

public enum RedisKeyEnums {
    /**
     * redis key 类型枚举
     */
    // 字符串
    STRING(1, "string"),
    // 列表
    SET(2, "set"),
    // hash
    HASH(3, "hash"),
    //
    ZSET(4, "zset"),
    DEFAULT(0, "default");


    @Getter
    private final int redisType;

    @Getter
    private final String description;

    RedisKeyEnums(int redisType, String description) {
        this.redisType = redisType;
        this.description = description;
    }

    public static RedisKeyEnums getRedisByKey(int redisType) {
        RedisKeyEnums[] values = values();
        for (RedisKeyEnums value : values) {
            if (value.getRedisType() == redisType) {
                return value;
            }
        }
        return DEFAULT;
    }
}
