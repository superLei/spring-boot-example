package com.example.service.qa.model.redisinfo;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
*
*  @author sulei
*/

@Data
public class ClearRedisRecord implements Serializable {

    private static final long serialVersionUID = 1583769225855L;

    /**
    * 主键
    * 
    * isNullAble:0
    */
    private Long itemID;

    /**
    * redis key
    * isNullAble:0
    */
    private String redisKey;

    /**
    * key描述信息
    * isNullAble:0,defaultVal:
    */
    private String remarks;

    /**
    * 标签
    * isNullAble:0,defaultVal:
    */
    private String tag;

    /**
    * 操作人
    * isNullAble:0,defaultVal:
    */
    private String operator;

    /**
    * 记录添加时间
    * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
    */
    private Timestamp createStamp;

    /**
    * 记录更新时间
    * isNullAble:0,defaultVal:CURRENT_TIMESTAMP
    */
    private Timestamp actionStamp;

    /**
     * redis key值
     * */
    private Object redisValue;

    /**
     *
     * redis key 类型
     * */
    private Integer redisType;

}
