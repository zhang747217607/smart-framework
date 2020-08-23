package com.zhangrh.smart.framework.helper;

import com.zhangrh.smart.framework.bean.FormParam;
import com.zhangrh.smart.framework.bean.Param;
import com.zhangrh.smart.framework.util.CodecUtil;
import com.zhangrh.smart.framework.util.StreamUtil;
import com.zhangrh.smart.framework.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @description: 请求助手类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/22 21:42
 */
public final class RequestHelper {

    /**
     * 创建请求对象
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String fieldName = parameterNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (ArrayUtils.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuilder sb = new StringBuilder("");
                    for (int i = 0; i < fieldValues.length; i++) {
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length - 1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }

    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = CodecUtil.decodeUrl(StreamUtil.getString(request.getInputStream()));
        if (StringUtils.isNotEmpty(body)) {
            String[] kvs = StringUtils.split(body, "&");
            if (ArrayUtils.isNotEmpty(kvs)) {
                for (String kv : kvs) {
                    String[] array = StringUtils.split(kv, "=");
                    if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                        formParamList.add(new FormParam(array[0], array[1]));
                    }
                }
            }
        }
        return formParamList;
    }

}
