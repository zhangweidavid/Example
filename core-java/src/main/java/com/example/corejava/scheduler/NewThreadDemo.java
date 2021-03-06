/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月29日
 */

package com.example.corejava.scheduler;

import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Scheduler.Worker;
import io.reactivex.schedulers.Schedulers;
import com.example.demo.LogUtil;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月29日 下午9:48:25
 * @version v 0.1
 */
public class NewThreadDemo {
	public static void main(String[] args) {
		Scheduler scheduler = Schedulers.newThread();
		Worker worker = scheduler.createWorker();
		LogUtil.log("Main Start");
		worker.schedule(() -> {
			LogUtil.log("Outer start");
			sleepOneSecond();
			worker.schedule(() -> {
				LogUtil.log("Inner start");
				sleepOneSecond();
				LogUtil.log("Innner end");
			});
			LogUtil.log("Outer end");
		});
		LogUtil.log("Main end");
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			// logger.error("", e);
		}
	}

	private static void sleepOneSecond() {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {

		}
	}

}
