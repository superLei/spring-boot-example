package com.example.service.qa.common;

import com.example.service.qa.utils.CommonUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class ImportErrorCodeProperties {


    private static final String ERROR_MESSAGE_PATH = "classpath:errorMessage";
    private static ImportErrorCodeProperties errorCodeProperties = new ImportErrorCodeProperties();
    static ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

    @PostConstruct
    private static void getErrorProperties() {
        messageSource.setBasename(ERROR_MESSAGE_PATH);
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
    }

    public static ImportErrorCodeProperties init() {
        return errorCodeProperties;
    }

    public static String getErrorMessage(String errorCode) {
        getErrorProperties();//临时设置
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        if (CommonUtils.notNull(errorMessage)) {
            return errorMessage;
        }
        return "";
    }
}
