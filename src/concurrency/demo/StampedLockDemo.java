/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月8日
 */

package concurrency.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月8日 下午12:49:06
 * @version v 0.1
 */
public class StampedLockDemo {
	public static void main(String[] args) throws Exception {
		final Point point = new Point();
		ExecutorService es = Executors.newCachedThreadPool();
		es.submit(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("commit");
				es.submit(() -> {
					point.move(Math.random() * 10, Math.random() * 10);
				});
			}
		});
		es.submit(() -> {
			for (int i = 0; i < 1000; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// logger.error("", e);
				}
				es.submit(() -> {
					System.out.println(point.distanceFromOrigin());
				});
			}
		});
	}
}
