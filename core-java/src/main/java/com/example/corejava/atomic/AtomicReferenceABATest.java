package com.example.corejava.atomic;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class AtomicReferenceABATest {

    public static final AtomicReference<String> ATOMIC_REFERENCE=new AtomicReference<>("abc");

    private static final Random RANDOM_OBJECT=new Random();

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch startCountDownLatch=new CountDownLatch(1);
        Thread[] threads=new Thread[200];
        for(int i=0;i<200;i++){
            final int num=1;
            threads[i]=new Thread(()->{
                String oldValue=ATOMIC_REFERENCE.get();
                try{
                    startCountDownLatch.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(ATOMIC_REFERENCE.compareAndSet(oldValue,oldValue+num)){
                    System.out.println("我是线程："+num+",进行对象修改！");
                }
            });
            threads[i].start();

        }
        Thread.sleep(20000);

        startCountDownLatch.countDown();

        new Thread(()->{
            try{
                Thread.sleep(RANDOM_OBJECT.nextInt()&200);
            }catch (Exception e){
                e.printStackTrace();
            }
            while(!ATOMIC_REFERENCE.compareAndSet(ATOMIC_REFERENCE.get(),"abc"));
            System.out.print("已经改为初始值");

        }).start();
    }
}
