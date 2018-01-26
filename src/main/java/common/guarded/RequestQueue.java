package common.guarded;

import java.util.LinkedList;

/**
 * Created by wei.zw on 2017/6/9.
 */
public class RequestQueue {
    private LinkedList queue = new LinkedList();

    public synchronized Request getRequest() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        return (Request) queue.remove();
    }

    public synchronized void addRequest(Request request) {
        queue.add(request);
        notifyAll();
    }
}
