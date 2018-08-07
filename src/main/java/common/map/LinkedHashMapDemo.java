package common.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

    public static void main(String[] args) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap(16, 0.75f, true);
        linkedHashMap.put("a", "1");
        linkedHashMap.put("b", "2");
        linkedHashMap.put("c", "3");
        System.out.println(linkedHashMap);
        linkedHashMap.get("b");
        linkedHashMap.entrySet().stream().forEach(System.out::println);

        LRUCache<Integer, Integer> lruCache = new LRUCache(16);
        for (int i = 0; i < 20; i++) {
            lruCache.put(i, i);
        }
        System.out.println(lruCache);
    }

    public static class LRUCache<K, V> extends LinkedHashMap {

        private int maxCapacity;

        public LRUCache(int maxCapacity) {
            super(maxCapacity, 0.75f, true);
            this.maxCapacity=maxCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > maxCapacity;
        }
    }
}
