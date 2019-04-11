package com.example.dos;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashDos {

    private static class Key {


        @Override
        public int hashCode() {
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    public static void main(String[] args) {

/**
        testHashMap();
        testHashTable();
**/
    }

    private static void testHashMap() {
        long start=System.currentTimeMillis();
        Map<Key, String> map = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            map.put(new Key(), String.valueOf(i));
        }
        System.out.println(map.get(new Key())+", 耗时："+(System.currentTimeMillis()-start));
    }

    private static void testHashTable() {
        long start=System.currentTimeMillis();
        Map<Key, String> map = new Hashtable<>();
        for (int i = 0; i < 100000; i++) {
            map.put(new Key(), String.valueOf(i));
        }
        System.out.println(map.get(new Key())+", 耗时："+(System.currentTimeMillis()-start));
    }
}
