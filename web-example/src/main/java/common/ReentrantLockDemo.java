package common;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wei.zw on 2017/6/24.
 */
public class ReentrantLockDemo {

    private static final ReentrantLock lock = new ReentrantLock();


    private static Runnable createTask = new Runnable() {
        @Override
        public void run() {
            while (true) {

                try {
                    System.out.println("before locked "+Thread.currentThread().getName());
                    //获取锁但是优先响应中断
                    lock.lockInterruptibly();
                    //lock.lock(); 获取锁，如果锁已经被占用则等待
                    try {
                        System.out.println("locked " + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } finally {
                        //释放锁
                        lock.unlock();
                        System.out.println("unlocked " + Thread.currentThread().getName());
                    }
                    break;
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is Interrupted");
                }
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread first=new Thread(createTask,"First Thread");
        Thread second=new Thread(createTask,"Second Thread");

        first.start();
        second.start();

        Thread.sleep(1000);

        second.interrupt();
    }
}
