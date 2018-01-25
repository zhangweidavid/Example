package concurrency.demo.queue;

import java.util.concurrent.TimeUnit;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class TestDemo {

    public static  void main(String[] args) throws InterruptedException {
        HandleQueueThread handleQueueThread=new HandleQueueThread();
        handleQueueThread.initConcurrentLinkedQueue();
        new Thread(handleQueueThread).start();

        handleQueueThread=new HandleQueueThread();
        handleQueueThread.initLikedBlockingQueue();
        new Thread(handleQueueThread).start();

        TimeUnit.MINUTES.sleep(100);
    }
}
