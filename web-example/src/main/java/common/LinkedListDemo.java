package common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Created by wei.zw on 2017/5/23.
 */
public class LinkedListDemo {

    public  static  void main(String[] args){

       new   ArrayList<String>().add("a");
        LinkedList<String> queue=new LinkedList<String>();
        /**
         * 在添加失败时候会抛出异常
         */
        queue.add("add");
        //添加元素
        queue.offer("off");
       System.out.println(queue.add("add"));
       queue.clear();
        /**
         * element() 返回队列头元素，如果为null则抛出一个NoSuchElementException
         */
       try{
           queue.element();
       }catch (NoSuchElementException nse){
           System.out.println("SUCCESS");
       }
       try{
           queue.getFirst();
       }catch (NoSuchElementException e){
           System.out.println("SUCCESS");
       }
       System.out.println(queue.poll());
       try{
           queue.remove();
       }catch (NoSuchElementException e){
           System.out.println("SUCCESS");
       }
       try{
           queue.remove(null);
       }catch (NoSuchElementException e){
           System.out.println("SUCCESS");
       }
        /**
         * peek也是返回队列的头元素，但是如果元素为NULL则直接返回null，如果不为null则和element效果一致
         */
       System.out.println(queue.peek());
    }
}
