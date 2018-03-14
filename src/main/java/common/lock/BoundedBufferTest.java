package common.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BoundedBufferTest {
    public static void main(String[] args) throws Exception {
        BoundedBuffer boundedBuffer = new BoundedBuffer();
     ExecutorService service= Executors.newCachedThreadPool();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(;;) {
                        boundedBuffer.put(1);
                        TimeUnit.SECONDS.sleep(1);
                    }
                } catch (Exception e) {

                }
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(;;) {
                        boundedBuffer.take();
                        TimeUnit.SECONDS.sleep(10);
                    }
                } catch (Exception e) {

                }
            }
        });


        TimeUnit.MINUTES.sleep(10);
    }
}
