package com.example.corejava;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapTest {

    public static void main(String[] args){
        TreeMap<BigDecimal,BigDecimal> test=new TreeMap<>();

        test.put(BigDecimal.valueOf(99),BigDecimal.valueOf(10));
        test.put(BigDecimal.valueOf(180),BigDecimal.valueOf(20));

      Map.Entry<BigDecimal,BigDecimal> entry= test.lowerEntry(new BigDecimal(188));
      System.out.println("满"+entry.getKey()+"减"+entry.getValue());
    }
}
