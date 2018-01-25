package core.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wei.zw on 2017/5/16.
 */
public class HeapOOM {
  static final   List<String> list=new ArrayList<>();
 public static void main(String[] args) throws InterruptedException {

         while(true){
             list.add( new String("OOM test test test test test test test test test test test test test test test test OOM test test test test test test test test test test test test test test test test OOM test test test test test test test test test test test test test test test test"));
             TimeUnit.MICROSECONDS.sleep(1);
         }
 }
}