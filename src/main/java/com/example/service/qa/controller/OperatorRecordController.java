package com.example.service.qa.controller;

import com.aliyun.oss.OSS;
import com.example.service.qa.common.BusinessException;
import com.example.service.qa.utils.CommonUtils;
import com.example.service.qa.utils.OssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;

import static com.example.service.qa.exceptions.ErrorCode.DOWNLOAD_ERROR;


@RestController
@RequestMapping
@Slf4j
public class OperatorRecordController {


    @RequestMapping("/record/download/{recordId}")
    public void downloadFile(@PathVariable("recordId") String recordId, HttpServletResponse response) {
        OSS oss = null;
        BufferedReader br = null;
        //被下载的文件在服务器中的路径
        try {
            oss = OssUtil.buildOssClient();
            br = OssUtil.downloadForReader(oss, recordId);
            //设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + recordId);
            //设置文件类型
            response.setContentType("application/octet-stream");

            OutputStream outputStream = response.getOutputStream();
            while (true) {
                final String s = br.readLine();
                if (CommonUtils.isNull(s)) {
                    break;
                }
                outputStream.write(s.getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            log.error("io exception", e);
            throw new BusinessException(DOWNLOAD_ERROR);
        } finally {
            if (!CommonUtils.isNull(oss)) {
                oss.shutdown();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
