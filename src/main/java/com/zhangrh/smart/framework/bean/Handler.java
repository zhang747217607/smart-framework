package com.zhangrh.smart.framework.bean;

import java.lang.reflect.Method;

/**
 * @description: 封装Action 信息
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/11 20:59
 */
public class Handler {

    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
