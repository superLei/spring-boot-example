package com.example.service.qa.enums;


import lombok.Getter;

/**
 * 执行结果的状态值
 *
 * @author sulei
 */
public enum ResultStatusEnum {
    PROCESS(0, "执行中"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    @Getter
    private int code;

    @Getter
    private String desc;

    ResultStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
