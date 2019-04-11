package com.example.corejava.innerclass;

/**
 * Created by wei.zw on 2017/6/6.
 */
public class StaticInnnerClassDemo {

    public StaticInnnerClassDemo() {
        System.out.println("OuterClass");
    }

    public String getValue() {
        return "test";
    }

    public static StaticInnnerClassDemo getInstance() {
        System.out.println("getInstance");
        return Inner.instance;
    }

    public static class Inner {
        static{
            System.out.println("Init Inner");
        }
        public Inner() {
            System.out.println("Inner");
        }

        private static StaticInnnerClassDemo instance = new StaticInnnerClassDemo();
    }
}
