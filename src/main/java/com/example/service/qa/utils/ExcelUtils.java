package com.example.service.qa.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.example.service.qa.service.common.CustomCellWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class ExcelUtils {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    public static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    public static final String accessKeyId = "";
    public static final String accessKeySecret = "";
    public static final String bucket = "";
    public static final String baseURL = "";

    /**
     * 直接写入数据
     *
     * @param data 导出数据
     * @param clazz 导出类字节码
     * @param excludeColumnFiledNames 排除字段
     * @param recordName 文件名
     * @return File excel文件
     */
    public static File write(List data, Class<?> clazz, Set<String> excludeColumnFiledNames, String recordName) {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        // 字体大小
        headWriteFont.setFontHeightInPoints((short)11);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 自动换行
        headWriteCellStyle.setWrapped(false);

        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, new WriteCellStyle());
        // 文件路径
        String path = initFilePath(recordName);
        File file = new File(path);

        // 写入数据
        EasyExcel.write(file, clazz)
                .registerWriteHandler(new CustomCellWriteHandler())
                .registerWriteHandler(horizontalCellStyleStrategy)
                .excludeColumnFiledNames(excludeColumnFiledNames)
                .sheet("Sheet1")
                .doWrite(data);
        return file;
    }

    /**
     * 初始化文件上传路径
     *
     * @return
     */
    public static String initObjectName(Long groupID, String fileName) {
        //生成zip文件路径 export/${fileName}.zip
        String objectName = MessageFormat.format(ConstantUtils.OSS_OBJECT_NAME, groupID, fileName);
        log.info("oss objectName:{}", objectName);
        return objectName;
    }

    /**
     * 分片上传文件至oss
     *
     * @param zip 文件
     * @return 文件路径
     */
    public static String multipartUpload(File zip, Long groupID) {

        String objectName = initObjectName(groupID, zip.getName());

        OSS ossClient = null;
        try {

            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 创建InitiateMultipartUploadRequest对象。
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucket, objectName);

            // 初始化分片。
            InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
            // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个ID来发起相关的操作，如取消分片上传、查询分片上传等。
            String uploadId = upresult.getUploadId();

            // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
            List<PartETag> partETags = new ArrayList<>();
            // 计算文件有多少个分片。
            final long partSize = 5 * 1024 * 1024L;   // 5MB

            long fileLength = zip.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }

            List<UploadPartRequest> requests = new ArrayList<>();
            // 遍历分片上传。
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                InputStream inputStream = new FileInputStream(zip);
                // 跳过已经上传的分片。
                inputStream.skip(startPos);
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucket);
                uploadPartRequest.setKey(objectName);
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(inputStream);
                // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100KB。
                uploadPartRequest.setPartSize(curPartSize);
                // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
                uploadPartRequest.setPartNumber(i + 1);

                requests.add(uploadPartRequest);
            }

            // 异步上传
            final OSS client = ossClient;
            List<CompletableFuture<PartETag>> futures = new ArrayList<>();
            requests.forEach(partition -> {
                CompletableFuture<PartETag> future = CompletableFuture.supplyAsync(() -> {
                    // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                    UploadPartResult uploadPartResult = client.uploadPart(partition);
                    return uploadPartResult.getPartETag();
                });
                futures.add(future);
            });

            futures.forEach(f -> {
                PartETag join = f.join();
                // 每次上传分片之后，OSS的返回结果会包含一个PartETag。PartETag将被保存到partETags中。
                partETags.add(join);
            });

            // 创建CompleteMultipartUploadRequest对象。
            // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
            CompleteMultipartUploadRequest completeMultipartUploadRequest =
                    new CompleteMultipartUploadRequest(bucket, objectName, uploadId, partETags);

            // 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
            // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);

            // 完成上传。
            ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            return objectName;
        } catch (Exception e) {
            log.error("上传文件时发生异常", e);
            return "";
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
            //本地压缩包删除
            zip.delete();
        }
    }

    /**
     * 删除多远程文件
     *
     * @param keys
     */
    public static void removeFiles(List<String> keys) {

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 删除文件
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucket).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

            log.info("已删除远程文件：{}", deletedObjects);

        } catch (Exception e) {
            log.error("删除远程文件失败：", e);
        } finally {
            if (ossClient != null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }

    /**
     * 初始化文件路径
     *
     * @return 文件路径
     */
    public static String initZipFilePath(String recordName) {

        String os_name = System.getProperty("os.name").toLowerCase();

        if (os_name.startsWith("win")) {
            return MessageFormat.format(ConstantUtils.WIN_TMP_DIR_ZIP, recordName);
        } else {
            return MessageFormat.format(ConstantUtils.LINUX_TMP_DIR_ZIP, recordName);
        }
    }

    /**
     * 初始化文件路径
     *
     * @return 文件路径
     */
    public static String initFilePath(String recordName) {
        String os_name = System.getProperty("os.name").toLowerCase();
        if (os_name.startsWith("win")) {
            return MessageFormat.format(ConstantUtils.WIN_TMP_DIR, UUID.randomUUID().toString(), recordName);
        } else {
            return MessageFormat.format(ConstantUtils.LINUX_TMP_DIR, recordName);
        }
    }

    /**
     * 初始化文件名称
     *
     * @return 文件名称
     */
    public static String initFileName(Long groupID, String recordName, int index) {
        return groupID + "-" + recordName +
                "(" +
                index +
                ")";
    }
}
