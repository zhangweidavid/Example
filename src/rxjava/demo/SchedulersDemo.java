/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月6日
 */

package rxjava.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月6日 下午4:51:56
 * @version v 0.1
 */
public class SchedulersDemo {
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Schedulers.computation()
					.scheduleDirect(() -> System.out.println(Thread.currentThread().getName() + " computation"));
		}

		for (int i = 0; i < 20; i++) {
			Schedulers.io().scheduleDirect(() -> {
				System.out.println(Thread.currentThread().getName() + " io");
			});
		}
		ExecutorService es = Executors.newScheduledThreadPool(1);
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			es.submit(() -> System.out.println("newScheduledThreadPool"));
		}
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			es.submit(() -> System.out.println("newScheduledThreadPool"));
			Schedulers.single().scheduleDirect(() -> System.out.println(Thread.currentThread().getId() + " single"));
		}
		for (int i = 0; i < 10; i++) {
			Schedulers.trampoline().scheduleDirect(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(
						Thread.currentThread().getName() + ",id=" + Thread.currentThread().getId() + " trampoline");
			});
		}
		
	
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			es.submit(() -> System.out.println("newScheduledThreadPool"));
		}
		
	}
}
