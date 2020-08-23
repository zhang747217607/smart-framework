package com.zhangrh.smart.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @description: 切面代理
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/16 15:25
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams);
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
        } finally {
            end();
        }
        return result;
    }

    private void begin() {

    }

    public boolean intercept(Class<?> cls, Method method, Object[] params) {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] param) throws Throwable {

    }

    public void after(Class<?> cls, Method method, Object[] param) throws Throwable {

    }

    public void error(Class<?> cls, Method method, Object[] param, Throwable e) {

    }

    public void end() {

    }


}
