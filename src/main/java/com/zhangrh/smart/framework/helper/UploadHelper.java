package com.zhangrh.smart.framework.helper;

import com.zhangrh.smart.framework.bean.FileParam;
import com.zhangrh.smart.framework.bean.FormParam;
import com.zhangrh.smart.framework.bean.Param;
import com.zhangrh.smart.framework.util.FileUtil;
import com.zhangrh.smart.framework.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 文件上传助手类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/22 19:21
 */
public final class UploadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(ServletContext servletContext) {

        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = Integer.parseInt(ConfigHelper.getAppUploadLimit());
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为multipart
     */
    public static boolean isMultiPart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    private static final String UTF8 = "UTF-8";

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();
        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (MapUtils.isNotEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> entry : fileItemListMap.entrySet()) {
                    String fieldName = entry.getKey();
                    List<FileItem> fileItemList = entry.getValue();
                    if (CollectionUtils.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString(UTF8);
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            } else {
                                String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), UTF8));
                                if (StringUtils.isNotEmpty(fileName)) {
                                    long fileSize = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("create param failure ", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (CollectionUtils.isNotEmpty(fileParamList)) {
                fileParamList.forEach(fileParam -> uploadFile(basePath, fileParam));
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

}
