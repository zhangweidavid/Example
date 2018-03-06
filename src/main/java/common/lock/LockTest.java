package common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wei.zw on 2017/5/19.
 */
public class LockTest {
    public static void main(String[] args){
        Lock lock=new ReentrantLock();
        for(int i=0;i<5;i++){
            if(i==4){
                System.out.println(lock.newCondition());
            }
            new Thread(()->{
                try {
                    System.out.println("--lock--"+Thread.currentThread().getName());
                    lock.lock();
                    System.out.println("---get lock-----"+Thread.currentThread().getName());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }).start();
        }
        try {
            TimeUnit.MINUTES.sleep(10);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
