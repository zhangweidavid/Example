/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月2日
 */

package com.example.demo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月2日 下午7:38:41
 * @version v 0.1
 */
public class BackpressureDemo {
	public static void main(String[] args) throws Exception {
		Observable.interval(1, TimeUnit.MICROSECONDS).observeOn(Schedulers.newThread()).subscribe(new Consumer<Long>() {

			@Override
			public void accept(Long paramT) throws Exception {
				System.out.println(Thread.currentThread().getName()+"-"+paramT);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//		Flowable.interval(1, TimeUnit.MICROSECONDS).onBackpressureDrop().observeOn(Schedulers.newThread()).subscribe(new Consumer<Long>() {
//
//			@Override
//			public void accept(Long paramT) throws Exception {
//				System.out.println("Flowable"+paramT);
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
	TimeUnit.HOURS.sleep(1);
	}
}
