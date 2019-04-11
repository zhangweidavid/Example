/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2017年3月7日
 */

package com.example.corejava;

import java.util.concurrent.*;

/**
 * Desc:TODO
 *
 * @author wei.zw
 * @since 2017年3月7日 下午8:09:37
 * @version v 0.1
 */
public class ExecutorsDemo {
    public static void main(String[] args) {
//        ExecutorService exec = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            System.out.println("add");
//            exec.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    System.out.println(Thread.currentThread().getName());
//                }
//            });
//        }
       // 就算把线程的执行语句放到try - catch块中也无济于事
        try {

            ExecutorService exec = new MyExecutor(0, Integer.MAX_VALUE, 60L,
                    TimeUnit.SECONDS,
                    new SynchronousQueue<Runnable>());
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    throw new RuntimeException();
                }
            });
        } catch (Exception e) {
            System.out.println("Exception has been handled!");
            e.printStackTrace();
        }
    }

    public static class MyExecutor extends ThreadPoolExecutor {

        /**
         * @param corePoolSize
         * @param maximumPoolSize
         * @param keepAliveTime
         * @param unit
         * @param workQueue
         */
        public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                          BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        /**
         * @see java.util.concurrent.ThreadPoolExecutor#beforeExecute(java.lang.Thread,
         *      java.lang.Runnable)
         */
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            System.out.println("before executor");
        }

        /**
         * @see java.util.concurrent.ThreadPoolExecutor#afterExecute(java.lang.Runnable,
         *      java.lang.Throwable)
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            System.out.println("throwable  ");
            if (t != null) {
                t.printStackTrace();
            }
            System.out.println("throwable end  ");
            // super.afterExecute(r, t);
            // Future<?> f = (Future<?>) r;
            // try {
            // f.get();
            // } catch (InterruptedException e) {
            // System.out.println("线程池中发现异常，被中断"); e.printStackTrace();
            // } catch (ExecutionException e) {
            // System.out.println("线程池中发现异常，被中断"); e.printStackTrace();
            // }

        }

      //  super.execute();
      public void execute(Runnable command){
            super.execute(command);
      }
    }
}
