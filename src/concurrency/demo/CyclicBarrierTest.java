package concurrency.demo;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by wei.zw on 2017/5/19.
 */
public class CyclicBarrierTest {

    private static final int THREAD_COUNT=3;

    private final static CyclicBarrier CYCLIC_BARRIER=new CyclicBarrier(THREAD_COUNT, new Runnable() {
        @Override
        public void run() {
            System.out.println("-------------我是导游，本次点名结束，准备走下一个环节");
        }
    });


    public static void main(String[] args) throws  Exception{
        for(int i=0;i<THREAD_COUNT;i++){
            new Thread(()->{
                try{
                    System.out.println("我是线程："+Thread.currentThread().getName()+" 我们到达旅游地点");
                    CYCLIC_BARRIER.await();
                    System.out.println("我是线程："+Thread.currentThread().getName()+" 我们开始骑车");
                    CYCLIC_BARRIER.await();
                    System.out.println("我是线程：" +Thread.currentThread().getName()+",我们开始爬山");
                    CYCLIC_BARRIER.await();
                    System.out.println("我是线程："+Thread.currentThread().getName()+"我们回宾馆休息 ");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
