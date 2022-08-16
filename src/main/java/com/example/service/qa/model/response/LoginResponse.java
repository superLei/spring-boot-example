package com.example.service.qa.model.response;

import com.example.service.qa.model.ResultInfo;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse extends ResultInfo {
    // 登录姓名
    @Getter
    @Setter
    private String loginName;

    /**
     * 登录token
     * */
    @Getter
    @Setter
    private String token;
}
