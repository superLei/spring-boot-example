package com.example.service.qa.model;


import com.example.service.qa.utils.CommonUtils;
import com.example.service.qa.utils.ConstantUtils;
import lombok.Data;
//import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static com.example.service.qa.utils.CommonUtils.getIP;

@Data
public class BaseContext {
    /**
     * 操作人
     */
    private String operator;

    /**
     * 结果记录id
     */
    private Long itemID;

    /**
     * 链路ID
     */
    private String traceID = UUID.randomUUID().toString();

    /**
     * 清除类型
     */
    private Integer clearType;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 集团ID
     */
    private Long groupID;

    /**
     * 工单id
     */
    private String ticketNo;

    /**
     * 偏移量
     */
    private long offset = -1;

    /**
     * 分页数量
     */
    private int limitCount = ConstantUtils.PAGE_SIZE;

    /**
     * 分页起始值
     */
    private int limitStartValue = 0;

    /**
     * 请求客户端的ip
     */
    private String ipAddr;

    private Long recordItemId;

    private String fileName;

    private String remarks;

    private boolean async = true;

    private Integer result;

    public void calculateOffset() {
        this.offset = this.getOffset() == -1 ? 0 : this.getOffset() + this.getLimitCount();
    }

    public String getIpAddr() {
        if (CommonUtils.isBlank(ipAddr)) {
            return CommonUtils.getIP();
        }
        return ipAddr;
    }

    public BaseContext() {
    }
}
