package common.map;

import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args){
        TreeMap<Integer,Integer> treeMap=new TreeMap<>();
        for(int i=0;i<10;i++){
            treeMap.put(i,i);
        }
        System.out.println(treeMap);

        TreeMap<Integer,Integer> treeMapByCompare=new TreeMap<>((a,b)->b-a);
        for(int i=0;i<10;i++){
            treeMapByCompare.put(i,i);
        }
        System.out.println(treeMapByCompare);
    }
}
