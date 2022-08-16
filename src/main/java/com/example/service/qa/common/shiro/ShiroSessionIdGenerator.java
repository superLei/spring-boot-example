package com.example.service.qa.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * @Description 自定义SessionId生成器
 * @Author su
 * @CreateTime
 */
public class ShiroSessionIdGenerator implements SessionIdGenerator {

    /**
     * 实现SessionId生成
     */
    @Override
    public Serializable generateId(Session session) {
        Serializable sessionId = new JavaUuidSessionIdGenerator().generateId(session);
//        Serializable sessionId =
        return String.format("login_token_%s", sessionId);
    }

}
