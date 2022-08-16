package com.example.service.qa.common.sqllog.register;


import com.example.service.qa.common.sqllog.BaseSqlLogger;
import com.example.service.qa.utils.CommonUtils;

/**
 * sqllogger注册器
 */
public class SqlLoggerHolder {

    private static ThreadLocal<BaseSqlLogger> threadLocal = new ThreadLocal<>();

    public static void register(BaseSqlLogger sqlLogger) {
        threadLocal.set(sqlLogger);
    }

    public static BaseSqlLogger getLogger() {
        return threadLocal.get();
    }

    public static void write(String content) {
        final BaseSqlLogger baseSqlLogger = threadLocal.get();
        if (!CommonUtils.isNull(baseSqlLogger)) {
            baseSqlLogger.write(content);
        }
    }

    public static void release() {
        threadLocal.remove();
    }
}
