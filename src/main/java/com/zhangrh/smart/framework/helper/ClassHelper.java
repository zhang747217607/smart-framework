package com.zhangrh.smart.framework.helper;

import com.zhangrh.smart.framework.annotation.Controller;
import com.zhangrh.smart.framework.annotation.Service;
import com.zhangrh.smart.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 类操作助手类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/10 21:33
 */
public final class ClassHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassHelper.class);

    /**
     * 定义类集合
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下所有的类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下所有service类
     */
    public static Set<Class<?>> getServiceClassSet() {
        return CLASS_SET.stream()
                .filter(p -> p.isAnnotationPresent(Service.class))
                .collect(Collectors.toSet());
    }

    /**
     * 获取应用包名下所有controller类
     */
    public static Set<Class<?>> getControllerClassSet() {
        return CLASS_SET.stream()
                .filter(p -> p.isAnnotationPresent(Controller.class))
                .collect(Collectors.toSet());
    }

    /**
     * 获取应用包名下所有Bean类
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getControllerClassSet());
        classSet.addAll(getServiceClassSet());
        return classSet;
    }

    /**
     * 获取应用包下某父类的所有子类
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
        return CLASS_SET.stream()
                .filter(p -> superClass.isAssignableFrom(p) && !superClass.equals(p))
                .collect(Collectors.toSet());
    }

    /**
     * 获取应用包下带有某注解的所有类
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        return CLASS_SET.stream()
                .filter(p -> p.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());
    }
}

