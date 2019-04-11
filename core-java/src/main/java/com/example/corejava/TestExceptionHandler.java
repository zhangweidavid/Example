package com.example.corejava;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class TestExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程出现异常：");
        e.printStackTrace();
    }

}
