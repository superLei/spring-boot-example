package com.example.service.qa.model.clearrecord;


import com.example.service.qa.utils.SnowflakeIdUtils;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Random;


/**
 * @author sulei
 * @date 2019/11/07
 * @desc 数据清除结果记录
 */
@Data
public class ClearAllDataRecord implements Serializable {
    private Long itemID;

    // onduty系统中的工单ID
    private String ticketNo;

    // 文件名
    private String fileName;

    private Long groupID;

    private String groupName;

    // 清除类型
    private Integer clearType;

    private String clearTypeName;

    // 用户类型
    private Integer userType;

    private String userTypeName;

    // 执行结果
    private Integer result = 0;
    // 结果备注信息
    private String remarks;
    // 链路ID
    private String traceID;
    // 操作人
    private String operator;
    // ip地址
    private String ipAddr;

    // 导出文件路径
    private String filePath;

    private Timestamp createStamp;

    private Timestamp actionStamp;

    public ClearAllDataRecord() {
        // 获取唯一ID
        int nextInt = new Random().nextInt(30);
        SnowflakeIdUtils idUtils = new SnowflakeIdUtils(nextInt, nextInt % 7);
        this.itemID = idUtils.nextId();
    }
}
