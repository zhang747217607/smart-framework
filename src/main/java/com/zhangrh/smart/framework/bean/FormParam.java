package com.zhangrh.smart.framework.bean;

/**
 * @description: 封装表单参数
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/22 18:51
 */
public class FormParam {

    private String fieldName;
    private Object fieldValue;

    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
