/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月23日
 */

package com.example.demo;

import java.util.Arrays;

import io.reactivex.Observable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月23日 上午9:50:27
 * @version v 0.1
 */
public class FlatMapDemo {
	public static void main(String[] args) {
		Observable.just("Hello,RxJava", "Hello,RxJava2").flatMapIterable(s -> Arrays.asList(s.split(",")))
				.doOnNext(System.out::println).subscribe();
		
	}
}
