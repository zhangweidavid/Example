package com.example.cacheline;


public class FalseSharing implements Runnable {
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private static ValueNoPadding[] longs;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        for (int i = 1; i < 10; i++) {
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println("Thread num " + i + " duration = " + (System.currentTimeMillis() - start));
        }

    }

    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValueNoPadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ValueNoPadding();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = 0L;
        }
    }

    /**
     * Thread num 1 duration = 458
     * Thread num 2 duration = 586
     * Thread num 3 duration = 738
     * Thread num 4 duration = 756
     * Thread num 5 duration = 896
     * Thread num 6 duration = 998
     * Thread num 7 duration = 1214
     * Thread num 8 duration = 1424
     * Thread num 9 duration = 1721
     */
    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
    }

    /**
     * Thread num 1 duration = 504
     * Thread num 2 duration = 1152
     * Thread num 3 duration = 1249
     * Thread num 4 duration = 1425
     * Thread num 5 duration = 3034
     * Thread num 6 duration = 3267
     * Thread num 7 duration = 3057
     * Thread num 8 duration = 3334
     * Thread num 9 duration = 2669
     */
    public final static class ValueNoPadding {

        protected volatile long value = 0L;


    }
}
