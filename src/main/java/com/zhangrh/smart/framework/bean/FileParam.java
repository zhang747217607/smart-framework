package com.zhangrh.smart.framework.bean;

import java.io.InputStream;

/**
 * @description: 封装上传文件参数
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/22 18:49
 */
public class FileParam {

    private String filedName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream inputStream;

    public FileParam(String filedName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.filedName = filedName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
