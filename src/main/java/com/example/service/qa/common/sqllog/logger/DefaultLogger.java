package com.example.service.qa.common.sqllog.logger;

import com.example.service.qa.common.sqllog.BaseSqlLogger;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认日志记录
 */
@Slf4j
public class DefaultLogger extends BaseSqlLogger {
    @Override
    public void write(String content) {
        log.info(content);
    }

    @Override
    public void processResult() {

    }
}
