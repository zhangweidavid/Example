package demo;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wei.zw on 2017/5/27.
 */
public class StringTest {

    public static void main(String[] args)
            throws Exception {
        TimeUnit.SECONDS.sleep(60);
        new StringTest().phantomeDemo();
        List<SoftReference<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            Integer integer = new Integer(i);
            BigDecimal b = new BigDecimal(i);
            SoftReference<Integer> wstr = new SoftReference<Integer>(integer);
//			TimeUnit.MILLISECONDS.sleep(1);
            list.add(wstr);

        }
        if (list.size() == 200000) {
            System.out.println("软引用没有在Monir GC 的时候被回收,触发 Full GC 后");
            System.gc();
        }
        final ReferenceQueue<Integer> queue = new ReferenceQueue<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Reference<Integer> intRef = (Reference<Integer>) queue.remove();
                        System.out.println("被回收");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        for (int i = 200000; i < 10000000; i++) {
            Integer integer = new Integer(i);
            PhantomReference<Integer> phantomReference = new PhantomReference<Integer>(integer, queue);

            BigDecimal b = new BigDecimal(i);
            // String t = new String("abcdefghigkdkfjdfkdjfkdkfjkdjfkdjfkdjfkdjk" + i).intern();
            // t = t + integer + b;
            SoftReference<Integer> wstr = new SoftReference<Integer>(integer);
//			TimeUnit.MILLISECONDS.sleep(1);
            list.add(wstr);


        }


    }

    public void phantomeDemo() throws InterruptedException {
        String abc = new String("abc");
        System.out.println(abc.getClass() + "@" + abc.hashCode());
        final ReferenceQueue<String> referenceQueue = new ReferenceQueue<String>();
        new Thread() {
            public void run() {
                while (true) {
                    Object obj = referenceQueue.poll();
                    if (obj != null) {
                        try {
                            Field rereferent = Reference.class
                                    .getDeclaredField("referent");
                            rereferent.setAccessible(true);
                            Object result = rereferent.get(obj);
                            System.out.println("gc will collect："
                                    + result.getClass() + "@"
                                    + result.hashCode() + "\t"
                                    + (String) result);
                            System.exit(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }.start();
        PhantomReference<String> abcWeakRef = new PhantomReference<String>(abc,
                referenceQueue);
        abc = null;
        Thread.currentThread().sleep(3000);
        System.gc();
        Thread.currentThread().sleep(3000);

    }
}
