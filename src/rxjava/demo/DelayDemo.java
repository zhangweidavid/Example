/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月22日
 */

package rxjava.demo;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年3月22日 下午6:50:49
 * @version  v 0.1
 */
public class DelayDemo {
	public static void main(String[] args) {
		Flowable<Integer> f=Flowable.just(1, 1, 2);
		f.delay(10, TimeUnit.SECONDS);
		System.out.println("print");
		f.subscribe(System.out::println);
		f.test().assertValues(1,1,2);
	}
}
