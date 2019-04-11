package com.example.corejava;

import java.math.BigDecimal;
import java.util.TreeMap;

/**
 * Created by wei.zw on 2017/6/8.
 */
public class TreeMapDemo {


    public static class Studen implements Comparable<Studen> {

        private String name;

        private int score;

        public Studen(String name, int s) {
            this.name = name;
            this.score = s;
        }


        @Override
        public int compareTo(Studen studen) {
            if (studen.score < this.score) {
                return 1;
            }
            if (studen.score > this.score) {
                return -1;
            }
            return 0;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Studen{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    public static void main(String[] args){

        TreeMap<BigDecimal,String> treeMap=new TreeMap<>();
        treeMap.put(BigDecimal.valueOf(0.1),"10");
        treeMap.put(BigDecimal.valueOf(10),"10");
        treeMap.put(BigDecimal.valueOf(100),"100");
        System.out.println(treeMap.floorEntry(BigDecimal.valueOf(10)));
        System.out.println(treeMap.floorEntry(BigDecimal.valueOf(1)));
        System.out.print(treeMap.firstEntry());
    }
}
