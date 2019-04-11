package com.example.corejava;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wei.zw on 2017/6/8.
 */
public class LinkedHashMapDemo {

    public static void main(String[] args){
        Map<String,String> testMap=new LinkedHashMap(16,0.75F,true);
        testMap.put("1","a");
        testMap.put("2","B");
        testMap.put("3","C");
        testMap.put("4","D");
        testMap.get("3");
        testMap.get("2");
       System.out.println(testMap);
    }
}
