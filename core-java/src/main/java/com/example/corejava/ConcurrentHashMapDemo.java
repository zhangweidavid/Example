package com.example.corejava;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wei.zw on 2017/5/24.
 */
public class ConcurrentHashMapDemo {
	public static void main(String[] args) {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		for (int i = 0; i < 100; i++) {
			map.put(String.valueOf(i), String.valueOf(i));
		}
		map.put("test", "test");
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "b");
		map.put("d", "b");
		map.put("4", "b");
	}
}
