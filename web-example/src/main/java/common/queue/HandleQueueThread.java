package common.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class HandleQueueThread implements Runnable {

    protected String name;

    private Queue q;

    java.util.Random rand = new java.util.Random();

    public HandleQueueThread() {

    }

    public HandleQueueThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        long start=System.currentTimeMillis();
        try {
            for (int i = 0; i < 500; i++) {
                handleQueue(rand.nextInt(1000));
            }
            Thread.sleep(rand.nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    public Object handleQueue(int index) {
        q.add(index);
        q.poll();
        return null;
    }

    public void initConcurrentLinkedQueue() {
        q = new ConcurrentLinkedQueue();
        for (int i = 0; i < 300; i++) {
            q.add(i);
        }
    }

    public void initLikedBlockingQueue() {
        q = new LinkedBlockingQueue();
        for (int i = 0; i < 300; i++) {
            q.add(i);
        }
    }
}
