package demo;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class ProbabilityDemo {

    public static void main(String[] args){
      TreeMap<Double,String> probabilities=new TreeMap<>();
      probabilities.put(0.5D,"0.1-1");
      probabilities.put(0.8D,"2-3");
    ThreadLocalRandom random=ThreadLocalRandom.current();


        for(int a=0;a<100;a++) {
            Map<String, Integer> pMap = new TreeMap<>();
            for (int i = 0; i < 10000; i++) {
                double probalility = random.nextDouble();

                Map.Entry<Double,String> entry=probabilities.higherEntry(probalility);
                if(entry==null){
                    continue;
                }
               // System.out.println("probalility="+probalility+", key="+entry.getKey()+", value="+entry.getValue());
                String key =entry .getValue();
                if (!pMap.containsKey(key)) {
                    pMap.put(key, 1);
                } else {
                    Integer value = pMap.get(key);
                    pMap.put(key, value + 1);
                }
            }

        System.out.println(pMap);
        }


    }
}
