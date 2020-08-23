package com.zhangrh.smart.framework.bean;

import com.zhangrh.smart.framework.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 请求参数对象
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/11 21:29
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> geFieldMap() {
        Map<String, Object> fieldNap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldNap.containsKey(fieldName)) {
                    fieldValue = fieldNap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldNap.put(fieldName, fieldValue);
            }
        }
        return fieldNap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String filedName = fileParam.getFiledName();
                List<FileParam> fileParamList2;
                if (fileMap.containsKey(filedName)) {
                    fileParamList2 = fileMap.get(filedName);
                } else {
                    fileParamList2 = new ArrayList<>();
                }
                fileParamList2.add(fileParam);
                fileMap.put(filedName, fileParamList2);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数是否为空
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
    }


}
