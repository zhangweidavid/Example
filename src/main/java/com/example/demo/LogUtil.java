/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月30日
 */

package com.example.demo;

import java.util.Date;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年3月30日 上午10:04:39
 * @version  v 0.1
 */
public class LogUtil {

	public static void log(String message) {
		System.out.println(new Date() + " | " + Thread.currentThread().getName() + " | " + message);
	}
}
