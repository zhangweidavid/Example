package com.example.corejava.proxy.jdk;

import com.example.corejava.proxy.IDBQuery;

import java.lang.reflect.Proxy;

/**
 * Created by wei.zw on 2017/6/7.
 */
public class JdkDBQueryProxyFactory {

    public static IDBQuery createJdkProxy() {
        return (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IDBQuery.class}, new JdkDBQueryHandler());
    }
}
