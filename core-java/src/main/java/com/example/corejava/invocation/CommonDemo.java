package com.example.corejava.invocation;

import java.util.Objects;

/**
 * Created by wei.zw on 2017/5/27.
 */
public class CommonDemo {
    public static void main(String[] args){
        for(int i=0;i<100;i++) {
            int h = String.valueOf(i).hashCode();
            int h2=(h ^ (h >>> 16)) & 0x7fffffff;
            System.out.println(i);
            System.out.println(Integer.toBinaryString(h2));
            System.out.println(h2&15);
            System.out.println(h2&31);
            System.out.println(h2&63);
           System.out.println(Objects.hash(null));
        }
    }
}
