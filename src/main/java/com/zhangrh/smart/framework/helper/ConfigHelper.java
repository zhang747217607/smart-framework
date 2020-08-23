package com.zhangrh.smart.framework.helper;

import com.zhangrh.smart.framework.constant.ConfigConstant;
import com.zhangrh.smart.framework.util.PropsUtil;

import java.util.Properties;

/**
 * @description: 属性文件助手类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/10 22:33
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取JSP路径
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "WEB-INF/view/");
    }

    /**
     * 获取静态资源路径
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    /**
     * JDBC_DRIVER
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * JDBC_URL
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * JDBC_USERNAME
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * JDBC_PASSWORD
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }


    /**
     * 获取文件上传大小限制
     */
    public static String getAppUploadLimit() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT);
    }

}
