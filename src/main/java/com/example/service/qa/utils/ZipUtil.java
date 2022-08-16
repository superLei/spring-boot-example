package com.example.service.qa.utils;

import com.alibaba.excel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 压缩或解压zip：
 * 由于直接使用java.util.zip工具包下的类，会出现中文乱码问题，所以使用ant.jar中的org.apache.tools.zip下的工具类
 */
@Slf4j
public class ZipUtil {
    private static byte[] _byte = new byte[1024];

    /**
     * 压缩文件或路径
     *
     * @param zip      压缩的目的地址
     * @param srcFiles 压缩的源文件
     */
    public static void zipFile(String zip, List<File> srcFiles) throws IOException {
        if (zip.endsWith(".zip") || zip.endsWith(".ZIP")) {
            ZipOutputStream _zipOut = new ZipOutputStream(new FileOutputStream(new File(zip)));
            _zipOut.setEncoding("GBK");
            for (File _f : srcFiles) {
                handleFile(zip, _zipOut, _f, "");
            }
            _zipOut.close();
        } else {
            log.error("target file[" + zip + "] is not .zip type file");
        }
    }

    /**
     * @param zip     压缩的目的地址
     * @param zipOut
     * @param srcFile 被压缩的文件信息
     * @param path    在zip中的相对路径
     * @throws IOException
     */
    private static void handleFile(String zip, ZipOutputStream zipOut, File srcFile, String path) throws IOException {
        if (!"".equals(path) && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        if (!srcFile.getPath().equals(zip)) {
            if (srcFile.isDirectory()) {
                File[] _files = srcFile.listFiles();
                if (_files == null || _files.length == 0) {
                    zipOut.putNextEntry(new ZipEntry(path + srcFile.getName() + File.separator));
                    zipOut.closeEntry();
                } else {
                    for (File _f : _files) {
                        handleFile(zip, zipOut, _f, path + srcFile.getName());
                    }
                }
            } else {
                InputStream _in = FileUtils.openInputStream(srcFile);
                zipOut.putNextEntry(new ZipEntry(path + srcFile.getName()));
                int len;
                while ((len = _in.read(_byte)) > 0) {
                    zipOut.write(_byte, 0, len);
                }
                _in.close();
                zipOut.closeEntry();
            }
        }
    }

    /**
     * 解压缩ZIP文件，将ZIP文件里的内容解压到targetDIR目录下
     *
     * @param zipPath 待解压缩的ZIP文件名
     * @param descDir 目标目录
     */
    public static List<File> upzipFile(String zipPath, String descDir) {
        return upzipFile(new File(zipPath), descDir);
    }

    /**
     * 对.zip文件进行解压缩
     *
     * @param zipFile 解压缩文件
     * @param descDir 压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<File> upzipFile(File zipFile, String descDir) {
        List<File> _list = new ArrayList<>();
        try {
            ZipFile _zipFile = new ZipFile(zipFile, "GBK");
            for (Enumeration entries = _zipFile.getEntries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File _file = new File(descDir + File.separator + entry.getName());
                if (entry.isDirectory()) {
                    _file.mkdirs();
                } else {
                    File _parent = _file.getParentFile();
                    if (!_parent.exists()) {
                        _parent.mkdirs();
                    }
                    InputStream _in = _zipFile.getInputStream(entry);
                    OutputStream _out = new FileOutputStream(_file);
                    int len;
                    while ((len = _in.read(_byte)) > 0) {
                        _out.write(_byte, 0, len);
                    }
                    _in.close();
                    _out.flush();
                    _out.close();
                    _list.add(_file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _list;
    }

    /**
     * 对临时生成的文件夹和文件夹下的文件进行删除
     */
    public static void deleteFile(String delPath) {
        try {
            File file = new File(delPath);
            if (!file.isDirectory()) {
                file.delete();
            } else {
                String[] fileList = file.list();
                if (fileList == null) {
                    return;
                }
                for (String s : fileList) {
                    File delFile = new File(delPath + File.separator + s);
                    if (!delFile.isDirectory()) {
                        delFile.delete();
                    } else {
                        deleteFile(delPath + File.separator + s);
                    }
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

