package common.executors;

import java.util.concurrent.*;

/**
 * Created by wei.zw on 2017/5/20.
 */
public class ExecutorsDemo {
    private static final int COUNT_BITS = Integer.SIZE - 3;

    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    public static void main(String[] args) {

        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString(RUNNING));
        System.out.println(Integer.toBinaryString(SHUTDOWN));
        System.out.println(Integer.toBinaryString(STOP));
        System.out.println(Integer.toBinaryString(TIDYING));
        System.out.println(Integer.toBinaryString(TERMINATED));
        System.out.println("~capacity");
        System.out.println(Integer.toBinaryString(~CAPACITY));
        BlockingQueue<Integer> bq=new LinkedBlockingDeque<Integer>(10);
        for(int i=0;i<100;i++){
            System.out.println("offer "+i+", result="+bq.offer(i));

        }

        ExecutorService es=new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),Runtime.getRuntime().availableProcessors()<<1, 1,TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(10), new ThreadPoolExecutor.DiscardOldestPolicy());
        ((ThreadPoolExecutor)es).allowCoreThreadTimeOut(true);
        for(int i=0;i<1024;i++){
            es.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(10);
                }catch(Exception e){

                }

            });
        }

        try {
            TimeUnit.MINUTES.sleep(30);
        }catch(Exception e){

        }


    }
}
