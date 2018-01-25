package concurrency.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by wei.zw on 2017/6/24.
 */
public class ReadWriteLockDemo {

    private static Lock lock = new ReentrantLock();

    private Object value = new Object();

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();

    public Object handleRead() throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1);
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void handleWrite(int index) throws InterruptedException {
        try {
            lock.lock();

            Thread.sleep(1);
            value = index;
        } finally {
            lock.unlock();
        }
    }

    public Object handleRead2() throws InterruptedException {
        try {
            readLock.lock();
            System.out.println(readWriteLock.getReadHoldCount());
            Thread.sleep(1);
            return value;
        } finally {
            readLock.unlock();
        }
    }

    public void handleWrite2(int index) throws InterruptedException {
        try {
            writeLock.lock();
            Thread.sleep(1);
            value = index;
        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {

        ReadWriteLockDemo demo = new ReadWriteLockDemo();

        long startTime=System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    //final int index=i;
                    demo.handleWrite2(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            new Thread(() -> {
                try {
                    //final int index=i;
                    demo.handleRead2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println();
    }
}
