package com.example.demo.core;

public class IntegerCacheDemo {

    public static  void main(String[] args){
        Integer one=Integer.valueOf(1);
        System.out.println(one==Integer.valueOf(1));


        Integer max=Integer.valueOf(127);
        System.out.println(max==Integer.valueOf(127));


        Integer big=Integer.valueOf(200);
        System.out.println(big==Integer.valueOf(200));
    }
}
