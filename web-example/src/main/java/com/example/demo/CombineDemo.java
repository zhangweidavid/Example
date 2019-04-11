/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月3日
 */

package com.example.demo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月3日 下午2:59:11
 * @version v 0.1
 */
public class CombineDemo {
	public static void main(String[] args) throws Exception {

		// Flowable.just("join").map(s -> s + "-").join(Flowable.interval(1,
		// TimeUnit.SECONDS),
		// a -> Flowable.timer(30, TimeUnit.SECONDS), b -> Flowable.timer(20,
		// TimeUnit.SECONDS), (a, b) -> a + b)
		// .subscribe(System.out::println);

		Flowable.just("A").mergeWith(Flowable.just("B")).subscribe(System.out::println);

		Flowable.just("A").startWith("B").subscribe(System.out::println);

//		Flowable.combineLatest(Flowable.interval(2, TimeUnit.SECONDS), Flowable.interval(1, TimeUnit.SECONDS),
//				(x, y) -> x + "_" + y).subscribe(System.out::println);

		Observable.combineLatest(Observable.interval(17, TimeUnit.SECONDS).map(x -> "S" + x),
				Observable.interval(10, TimeUnit.SECONDS).map(x -> "F" + x), (s, f) -> f + ":" + s)
				.forEach(System.out::println);
	
		TimeUnit.SECONDS.sleep(60);
	}
}
