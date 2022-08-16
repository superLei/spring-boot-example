package com.example.service.qa.common.sqllog;


import com.example.service.qa.common.sqllog.register.SqlLoggerHolder;

/**
 * sql记录方式的父类
 */
public abstract class BaseSqlLogger {

    public BaseSqlLogger() {
        SqlLoggerHolder.register(this);
    }

    /**
     * 写日志方法
     *
     * @param content 记录内容
     */
    public abstract void write(String content);

    /**
     * 对日志结果进行操作
     */
    public abstract void processResult();


    public void clearResource() {

    }
}
