package common.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by wei.zw on 2017/5/23.
 */
public class OutOfMemoryTest {

    public static ExecutorService executorService= Executors.newCachedThreadPool();

    public static ExecutorService es=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args){
//        try {
//            for (long i = 0; i < Long.MAX_VALUE; i++) {
//                try {
//                    executorService.execute(() -> {
//                        try {
//                            TimeUnit.MINUTES.sleep(1);
//                        } catch (Exception e) {
//
//                        }
//                    });
//                } catch (OutOfMemoryError e) {
//                    System.out.println(i);
//                    throw e;
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
        for(long i=0;i<Long.MAX_VALUE;i++){
            try {
                es.execute(() -> {
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (Exception e) {

                    }
                });
            }catch(OutOfMemoryError e){
                System.out.println(i);
                throw e;
            }
        }
    }
}
