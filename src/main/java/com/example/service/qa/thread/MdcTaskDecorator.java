package com.example.service.qa.thread;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {
    @Override
    public Runnable decorate(Runnable runnable) {
        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
        try {
            return () -> {
                if(copyOfContextMap != null) {
                    MDC.setContextMap(copyOfContextMap);
                }
                runnable.run();
            };
        } finally {
            MDC.clear();
        }
    }
}