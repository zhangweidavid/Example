package com.example.corejava;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

    public static void main(String[] args){

        Map<Key,Integer>  map=new HashMap<>();
        for(int i=0;i<32;i++){
            map.put(new Key(i),i);
        }
        map.put(new Key(1),1);
        map.put(new Key(1<<20+1),2);
        map.put(new Key(1<<21+1),3);
        map.put(new Key(1<<22+1),4);
        map.put(new Key(1<<23+1),2);
        map.put(new Key(1<<24+1),2);
        map.put(new Key(1<<25+1),2);
        map.put(new Key(1<<26+1),2);
        map.put(new Key(1<<27+1),2);
        map.put(new Key(1<<28+1),2);
        map.put(new Key(1<<29+1),2);
        map.put(new Key(1<<30+1),2);
        map.put(new Key(1<<31+1),2);




    }

    public static class Key{

        public int value;

        public  Key(int value){
            this.value=value;
        }

        @Override
        public int hashCode(){
            return value;
        }
    }
}
