package com.zhangrh.smart.framework.helper;

import com.zhangrh.smart.framework.util.RefectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: Bean 助手类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/10 21:57
 */
public final class BeanHelper {


    private static final Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);

    /**
     * 定义Bean 映射(用于存放Bean类和Bean实例的映射关系)
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        if (CollectionUtils.isNotEmpty(beanClassSet)) {
            beanClassSet.forEach(p -> {
                BEAN_MAP.put(p, RefectionUtil.newInstance(p));
            });
        }
    }

    /**
     * 获取Bean 映射
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class：" + cls);
        }
        return (T) BEAN_MAP.get(cls);
    }

    /**
     * 设置Bean实例
     */
    public static void setBean(Class<?> cls, Object obj) {
        BEAN_MAP.put(cls, obj);
    }
}
