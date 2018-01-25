package concurrency.demo.guarded;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class ClientThread extends Thread {

    private RequestQueue requestQueue;

    public ClientThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            Request request = new Request("RequestID:" + i + " Thread_Name:" + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " handles " + request);
            requestQueue.addRequest(request);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ClinetThread name is " + Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName() + " request end");
    }
}
