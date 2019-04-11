/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * <p>
 * Date: 2017年3月30日
 */

package com.example.demo;

import java.util.Date;
import java.util.List;

/**
 * Desc:TODO
 *
 * @author wei.zw
 * @version v 0.1
 * @since 2017年3月30日 上午10:04:39
 */
public class LogUtil {

    public synchronized  static void log(String message) {
        System.out.println(new Date() + " | " + Thread.currentThread().getName() + " | " + message);

    }

    public static void main(String[] args) {
        int temp = 256 / 100;
        //总计多少页
        int totalPage = 256 % 100 == 0 ? temp : temp + 1;

        for (int pageNo = 1; pageNo < totalPage + 1; pageNo++) {
            try {
                int endCount = 256 - (pageNo - 1) * 100;
                System.out.println(endCount);
            } catch (Exception e) {

            }
        }
    }
}
