package com.example.service.qa.common;

import java.util.Arrays;
import java.util.function.Supplier;

public class ServiceException extends RuntimeException{


    private String errorCode;
    private String errorMessage;
    private Object[] params;

    public ServiceException(String errorCode) {
        this(errorCode, (String)null, (Object[])null);
    }

    public ServiceException(String errorCode, Object[] params) {
        this(errorCode, (String)null, params);
    }

    public ServiceException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, (Object[])null);
    }

    public ServiceException(String errorCode, String errorMessage, Object[] params) {
        super("[" + errorCode + "][" + errorMessage + "][" + Arrays.toString(params) + "]");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.params = params;
    }

    public static Supplier<ServiceException> of(String errorCode) {
        return () -> {
            return new ServiceException(errorCode);
        };
    }

    public static Supplier<ServiceException> of(String errorCode, String errorMessage) {
        return () -> {
            return new ServiceException(errorCode, errorMessage);
        };
    }

    public static Supplier<ServiceException> of(String errorCode, String errorMessage, Object[] params) {
        return () -> {
            return new ServiceException(errorCode, errorMessage, params);
        };
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public Object[] getParams() {
        return this.params;
    }
}
