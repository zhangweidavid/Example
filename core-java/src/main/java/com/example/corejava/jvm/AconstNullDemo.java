package com.example.corejava.jvm;

/**
 * Created by wei.zw on 2017/6/17.
 */
public class AconstNullDemo {
    Integer i=null;

    void test(){
        i=1;
        if(i.equals(Integer.valueOf(1))){
            System.out.println("eq");
        }
    }
}
