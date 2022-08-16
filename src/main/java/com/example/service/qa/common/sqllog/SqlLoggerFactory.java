//package com.didi.easybuild.sqllog;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class SqlLoggerFactory {
//
//
//    public static String MODE;
//
//    @Value("${sqllogger.mode:}")
//    public void setMode(String mode) {
//        MODE = mode;
//    }
//
//
//
//    public static BaseSqlLogger create(ClearDataContext context) {
//        switch (MODE){
//            case "oss":
//                final String fileName = generateFileName(context.getTicketNo(), System.currentTimeMillis());
//                context.setFileName(fileName);
//                return new OssLogger(fileName);
//            default:
//                return new DefaultLogger();
//        }
//
//    }
//
//    public static BaseSqlLogger create(BaseContext context) {
//        switch (MODE){
//            case "oss":
//                final String fileName = generateFileName(context.getTicketNo(), System.currentTimeMillis());
//                context.setFileName(fileName);
//                return new OssLogger(fileName);
//            default:
//                return new DefaultLogger();
//        }
//
//    }
//}
