package com.zhangrh.smart.framework.proxy;

/**
 * @description: 代理接口
 * @version: 1.0
 * @author: zhangrenhua
 * @date: 2020/8/16 14:38
 */
public interface Proxy {

    /**
     * 执行代理
     *
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
