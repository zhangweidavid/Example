package com.example.corejava.jvm;

/**
 * Created by wei.zw on 2017/6/19.
 */
public class ExitDemo {

    public void test(){
        try{

            System.exit(0);
            System.out.println("Exit");
        }finally{
            System.out.println("finally");
        }
    }
    public  static void main(String[] args){
        new ExitDemo().test();
    }
}
