package com.example.service.qa.common.sqllog;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Optional;
import java.util.UUID;

public class MdcHolder {

    /**
     * traceID
     */
    private static final String TRACE_ID = "traceID";

    /**
     * 集团ID
     */
    private static final String GROUP_ID = "groupID";

    /**
     * 操作人
     */
    private static final String OPERATOR = "operator";

    /**
     * contextMethod
     */
    private static final String CONTEXT_METHOD = "contextMethod";

    private MdcHolder() {
    }

    private static MdcHolder mdcHolder = new MdcHolder();

    public static MdcHolder log() {
        return mdcHolder;
    }

    public static void clear() {
        MDC.clear();
    }

    public MdcHolder traceID(String traceID) {
        MDC.put(TRACE_ID, traceID);
        return mdcHolder;
    }

    public static String getTraceID() {
        return Optional.ofNullable(MDC.get(TRACE_ID)).filter(StringUtils::isNotBlank).orElseGet(() -> {
            String traceID = UUID.randomUUID().toString().replaceAll("-", "");
            mdcHolder.traceID(traceID);
            return traceID;
        });
    }

    public MdcHolder groupID(Object groupID) {
        MDC.put(GROUP_ID, safeToString(groupID));
        return mdcHolder;
    }

    public static Long getGroupID() {
        String groupIDStr = MDC.get(GROUP_ID);
        return Long.parseLong(groupIDStr, 0);
    }

    public MdcHolder operator(Object operator) {
        MDC.put(OPERATOR, safeToString(operator));
        return mdcHolder;
    }

    public static String getOperator() {
        return MDC.get(OPERATOR);
    }

    public MdcHolder contextMethod(String contextMethod) {
        MDC.put(CONTEXT_METHOD, safeToString(contextMethod));
        return mdcHolder;
    }

    public static String getContextMethod() {
        return MDC.get(CONTEXT_METHOD);
    }

    private static String safeToString(Object o) {
        if (o == null) {
            return null;
        }
        if (o instanceof String) {
            return (String) o;
        }
        if (o instanceof Number) {
            return o.toString();
        }
        try {
//            return JSON.toJSONString(o);
            return new Gson().toJson(o);
        } catch (Exception ignore) {
        }

        return o.toString();
    }

}