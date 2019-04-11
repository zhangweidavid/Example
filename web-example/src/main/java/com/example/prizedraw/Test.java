package com.example.prizedraw;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Test {

    public static void main(String[] args) {


        int seed = 21191;
        int number = 100000; //new Random().nextInt(1000000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        String datefmt = simpleDateFormat.format(new Date());
        int currentInt = Integer.valueOf(datefmt) * 10 + 1;

        int lucky = getLucky(seed, currentInt, number);
        System.out.println((11000000 - lucky) + "," + lucky);
        lucky = getNext(seed, number, lucky);
        System.out.println((11000000 - lucky) + "," + lucky);

        lucky = getNext(seed, number, lucky);
        System.out.println((11000000 - lucky) + "," + lucky);

        lucky = getNext(seed, number, lucky);
        System.out.println((11000000 - lucky) + "," + lucky);

        lucky = getNext(seed, number, lucky);
        System.out.println((11000000 - lucky) + "," + lucky);
        lucky = getNext(seed, number, lucky);
        System.out.println((11000000 - lucky) + "," + lucky);


    }


    private static int getLucky(int seed, int currentId, int number) {
        return 11000000 - (seed + currentId) % number;
    }

    /**
     * @param seed    老时时彩的开奖结果
     * @param max     参与人数
     * @param current 当前中奖号码
     * @return 下一个中奖号码
     */
    private static int getNext(int seed, int max, int current) {
        return 11000000 - (seed + 11000000 - current) % max;
    }

}

