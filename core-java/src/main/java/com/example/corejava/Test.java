package com.example.corejava;


import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wei.zw on 2017/5/22.
 */
public class Test {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(3,5,10,TimeUnit.SECONDS,new LinkedBlockingDeque<>(200));
        ((ThreadPoolExecutor)es).allowCoreThreadTimeOut(true);
        CountDownLatch latch = new CountDownLatch(1);
        int cap = 1;
        int n = cap - 1;
        int t = n >>> 1;
        System.out.println(Integer.toBinaryString(n));
        System.out.println(Integer.toBinaryString(t));
        System.out.println(n = n | t);
        System.out.println(Integer.toBinaryString(n));
        t = n >>> 2;
        System.out.println(Integer.toBinaryString(t));
        System.out.println(n |= t);
        t = n >>> 4;
        System.out.println(Integer.toBinaryString(t));
        System.out.println(Integer.toBinaryString(n |= t));
        t = n >>> 8;
        System.out.println(Integer.toBinaryString(t));
        System.out.println(Integer.toBinaryString(n |= t));
        t = n >>> 16;
        System.out.println(Integer.toBinaryString(t));
        System.out.println(n |= t);
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            linkedList.add(String.valueOf(i));
        }
        Map<TestKey, String> map = new HashMap<>();
        map.put(null, null);
        ConcurrentHashMap cmap = new ConcurrentHashMap();
        //cmap.put(null,null);
        Map<String, String> treeMap = new TreeMap<>();
        //treeMap.put(null,null);
        Map<TestKey, String> lMap = new LinkedHashMap(16, 0.75F, true);
        List<Integer> arrayList = new ArrayList<>();
        map.put(new TestKey(111), "End");
        AtomicInteger ai = new AtomicInteger(0);
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                try {
                    latch.await();
                    int tmp = ai.getAndIncrement();
                    arrayList.add(tmp);
                    map.put(new TestKey(tmp), String.valueOf(tmp));
                    lMap.put(new TestKey(tmp), String.valueOf(tmp));
                    System.out.println("peek="+linkedList.poll());

                } catch (Exception e) {

                }
            });
        }


        latch.countDown();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {

        }
        System.out.println(arrayList);
        System.out.println("hashMap.values()=" + map.values());
        System.out.println("linkedHashMap.values()=" + lMap.values());
        lMap.forEach((k, v) -> System.out.println(k + ":" + v));
//        for(String str:linkedList){
//            System.out.println(str);
//        }
        System.out.println("linkeList.size="+linkedList.size());


        List<Integer> a=new ArrayList<>();
        a.add(1);a.add(6);a.add(3);a.add(4);a.add(5);

        List<Integer> b=new ArrayList<>();
        b.add(5);
        b.add(1);

    for(;;);
    }

}
