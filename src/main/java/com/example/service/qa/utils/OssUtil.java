package com.example.service.qa.utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.example.service.qa.common.BusinessException;
import com.example.service.qa.exceptions.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;



@Slf4j
public class OssUtil {


    /**
     * oss所需参数
     */
    private static final String END_POINT = "http://oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "";
    private static final String ACCESS_KEY_SECRET = "";
    private static final String BUCKET_NAME = "";
    private static final String PATH = "data/";
    private static final int SOCKET_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 5000;

    /**
     * 存入oss文件的后缀
     */
    private static final String FILE_SUFFIX = ".sql";

    public static void upload(InputStream inputStream, String name) {

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(CONNECTION_TIMEOUT);
        conf.setSocketTimeout(SOCKET_TIMEOUT);
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        String objectName = PATH + name;
        // 上传网络流。
        try {
            ossClient.putObject(BUCKET_NAME, objectName, inputStream);
        } catch (Exception e) {
            log.error("upload error", e);
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }


    /**
     *
     * @param fileName 文件名
     * @return
     */
    public static BufferedReader downloadForReader(OSS ossClient, String fileName) {

        String objectName = PATH + fileName;
        // 上传网络流。
        try {
            final OSSObject object = ossClient.getObject(BUCKET_NAME, objectName);
            return new BufferedReader(new InputStreamReader(object.getObjectContent()));
        } catch (Exception e) {
            log.error("download error", e);
            throw new BusinessException(ErrorCode.RECORD_NOT_EXISTS);
        }
    }

    public static OSS buildOssClient() {

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(CONNECTION_TIMEOUT);
        conf.setSocketTimeout(SOCKET_TIMEOUT);
        // 创建OSSClient实例。
        return  new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

}
