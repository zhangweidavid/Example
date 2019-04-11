package com.example.corejava.jvm;

/**
 * Created by wei.zw on 2017/6/17.
 */
public class TryCatchException {

    public void test(int i){
        if(i==0){
            throw  new IllegalArgumentException();
        }
    }

    public void catchExceptionjava(int i){
        try {
            test(i);
        }catch (Exception e){

        }
    }
}
