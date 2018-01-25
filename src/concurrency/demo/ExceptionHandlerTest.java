package concurrency.demo;

import java.util.concurrent.TimeUnit;

/**
 * Created by wei.zw on 2017/5/18.
 */
public class ExceptionHandlerTest {

    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(()->{
            Integer.parseInt("abc");
        });
        t.setUncaughtExceptionHandler(new TestExceptionHandler());
        t.start();

        TimeUnit.SECONDS.sleep(10);
    }
}
