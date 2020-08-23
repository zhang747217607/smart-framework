package com.zhangrh.smart.framework;

import com.zhangrh.smart.framework.helper.*;
import com.zhangrh.smart.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @description: 加载相应的Helper类
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/9 13:18
 */
public final class HelpLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelpLoader.class);

    public static void init() {
        Class<?>[] classSet = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        Arrays.stream(classSet)
                .forEach(p -> ClassUtil.loadClass(p.getName(), true));
        LOGGER.info("---------------- 框架初始化成功 --------------------");
    }
}
