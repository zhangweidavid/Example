package common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wei.zw on 2017/5/21.
 */
public class ConcurrencyHashMapDemo {
    public static void main(String[] args){
        ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();
        for(int i=0;i<100;i++) {
            map.put("a" + i, "a");
        }

            Map<String,String> test=new HashMap<>();
    }
}
