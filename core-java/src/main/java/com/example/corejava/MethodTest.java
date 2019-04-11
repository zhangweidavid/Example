package com.example.corejava;

import java.util.Random;

/**
 * Created by wei.zw on 2017/5/19.
 */
public class MethodTest {

    public static void main(String[] args){
       int i=1024;

       for(int s=0;s<10;s++) {
           long start = System.nanoTime();
           for (int a = 0; a < 10000; a++) {
               Random r = new Random();
               int h = String.valueOf(r.nextInt()).hashCode();
               int j = h % i;
           }

           System.out.println(System.nanoTime() - start);
           start = System.nanoTime();
           for (int a = 0; a < 10000; a++) {
               Random r = new Random();
               int h = String.valueOf(r.nextInt()).hashCode();
               int j = h & (i - 1);
           }
           System.out.println(System.nanoTime() - start);

           System.out.println("______________________");
       }




    }
}
