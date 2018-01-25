package concurrency.demo.queue;

import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wei.zw on 2017/6/10.
 */
public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> blockingQueue = null;
        try {
            blockingQueue = new LinkedBlockingQueue<>(0);
        } catch (IllegalArgumentException e) {
            System.out.println("the capactity not greater than zero");
        }

        blockingQueue = new LinkedBlockingQueue<>(1);
        try {
            // Inserts the specified elemet into this queue if it is possible  to do immediately without violating capacity
            // restrictions,returning true upon success and throw an IllegalStateException if no space is currently available
            blockingQueue.add("add");
            blockingQueue.add("bb");
            //  System.out.println(blockingQueue.add("cc"));
        } catch (IllegalStateException illegalStateException) {
            System.out.println("no space is currently available");
        }
        // Atomically removes all of the elements from this queue. This queue will be empty after this call returns.
        //对put,take 加锁后执行remove
        blockingQueue.clear();
        // Inserts the specified element into this queue if it is possible to do immediately without exceeding capacity
        // return true upon success  and false if this queue is full
        // When using a capacity-restricted queue,this method is generally preferable to method add, which can fail to insert an element
        // only by throw an exception
        blockingQueue.offer("offer");

        System.out.println(blockingQueue.offer("test"));
        // Retrieves,but dose not remove the head of this queue. This method differs from peek only in that  it throw an exception
        // if this queue is empty
        System.out.println(blockingQueue.element());
        // Retrieves,but dose not remove the head of this queue, or return null if this queue is empty
        //判断队列长度，如果长度为0则返回null,如果不为0则加take锁 返回对象
        System.out.println(blockingQueue.peek());
        //Retrieves and remove the head of this queue.or returns null if this queue is empty.
        blockingQueue.poll();
        //Retrieves and remove the head of this queue. This method differs from poll only in that it throw an exception if this queue is empty
        try {
            blockingQueue.remove();
        } catch (NoSuchElementException e) {
            System.out.println("No such element");
        }
        //Inserts the specified element into this queue,waiting if necessary for space to become available
        blockingQueue.put("a");
        // blockingQueue.

    }
}
