package concurrency.demo.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class AtomicIntegerTest {

    public final static AtomicInteger TEST_INTEGER=new AtomicInteger(1);

    public volatile static int index=1;

    public static void main(String[] args) throws Exception{
        final CountDownLatch startCountDown=new CountDownLatch(1);
        final Thread[] threads=new  Thread[10];
        for(int i=0;i<10;i++){
            threads[i]=new Thread(()->{
                try{
                    startCountDown.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                for(int j=0;j<100;j++){
                    index++;
                    TEST_INTEGER.incrementAndGet();
                }
            });
            threads[i].start();
        }
        Thread.sleep(1000);
        startCountDown.countDown();
        for(Thread t:threads){
            t.join();
        }
        System.out.println("Atomic 最终的运行结果是："+TEST_INTEGER.get());
        System.out.println("Volatile 最终运行结果是："+index);
    }
}
