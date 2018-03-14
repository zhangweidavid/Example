package common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CLHLock implements Lock {

    public static class Node {
        private volatile boolean isLocked = true; // 默认是在等待锁
    }


    private AtomicReference<Node> tail;

    private ThreadLocal<Node> myPre;

    private ThreadLocal<Node> myNode;


    public CLHLock() {
        tail= new AtomicReference<Node>(new Node());
        myNode=new ThreadLocal<Node>(){
            protected Node initialValue() {
                return new Node();
            }
        };

        myPre=new ThreadLocal<Node>(){
            protected Node initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
      Node node=  myNode.get();
      node.isLocked=true;
      Node pre= tail.getAndSet(node);
      myPre.set(pre);
      while(pre.isLocked){

      }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Node node=myNode.get();
        node.isLocked=false;
        myNode.set(node);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
