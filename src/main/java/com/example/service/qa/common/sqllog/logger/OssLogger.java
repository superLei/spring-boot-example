//package com.xiaoju.manhattan.qa.common.sqllog.logger;
//
//import com.xiaoju.manhattan.qa.common.sqllog.BaseSqlLogger;
//import com.xiaoju.manhattan.qa.common.sqllog.register.SqlLoggerHolder;
//import com.xiaoju.manhattan.qa.utils.CommonUtils;
//import com.xiaoju.manhattan.qa.utils.OssUtil;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.*;
//
///**
// * Oss记录
// */
//@Slf4j
//public class OssLogger extends BaseSqlLogger {
//
//    private BufferedWriter writer = null;
//
//    private String absolutePath = null;
//
//    private String fileName = null;
//
//    public OssLogger(String fileName) {
//        super();
//        this.absolutePath = CommonUtils.getAbsolutePath(fileName);
//        this.fileName = fileName;
//        try {
//            writer = new BufferedWriter(new FileWriter(this.absolutePath));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            this.absolutePath = null;
//            this.fileName = null;
//        }
//    }
//
//    @Override
//    public void write(String content) {
//        if (null == writer) {
//            log.warn("writer does not exist");
//        }
//        try {
//            writer.write(content);
//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void processResult() {
//        // 输出流关闭
//        if (!CommonUtils.isNull(writer)) {
//            try {
//                writer.close();
//                writer = null;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (StrUtil.isNotEmpty(absolutePath)) {
//
//            try(FileInputStream inputStream = new FileInputStream(absolutePath)) {
//                OssUtil.upload(inputStream, fileName);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void clearResource() {
//        if (StrUtil.isNotBlank(absolutePath)) {
//            new File(absolutePath).delete();
//        }
//
//        if (null != writer) {
//            try {
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        SqlLoggerHolder.release();
//    }
//}
