package com.zhangrh.smart.framework.proxy;

import com.zhangrh.smart.framework.annotation.Transaction;
import com.zhangrh.smart.framework.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @description: 事务代理
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/16 23:27
 */
public class TransactionProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = ThreadLocal.withInitial(() -> false);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;
        boolean flag = FLAG_HOLDER.get();
        Method targetMethod = proxyChain.getTargetMethod();
        if (!flag && targetMethod.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DataBaseHelper.beginTransaction();
                result = proxyChain.doProxyChain();
                DataBaseHelper.commitTransaction();
            } catch (Exception e) {
                DataBaseHelper.rollbackTransaction();
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }
        return result;
    }
}
