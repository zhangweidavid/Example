/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月9日
 */

package rxjava.demo;

import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.parallel.ParallelFlowable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月9日 下午8:04:48
 * @version v 0.1
 */
public class AsyncDemo {
	public static void main(String[] args) throws Exception {
		/**
		 * one, two, three, four 顺序随机
		 */
		Flowable.create(s -> {
			new Thread(() -> {
				s.onNext("one");
				s.onNext("two");
			}).start();
			new Thread(() -> {
				s.onNext("three");
				s.onNext("four");
			}).start();
		}, BackpressureStrategy.DROP).subscribe(System.out::println);

		Flowable<String> a = Flowable.create(s -> {
			new Thread(() -> {
				s.onNext("one");
				s.onNext("two");
			}).start();
		}, BackpressureStrategy.DROP);

		Flowable<String> b = Flowable.create(s -> {
			new Thread(() -> {
				s.onNext("three");
				s.onNext("four");
			}).start();
		}, BackpressureStrategy.DROP);
		a.subscribe(System.out::println);
		a.subscribe(System.out::println);
		/**
		 * [ one two], [three four] 顺序是一定的， 但是这两组的属性不是固定的
		 */
		Flowable.mergeArray(a, b).subscribe(System.out::println);

		ParallelFlowable.from(new Publisher<String>() {

			@Override
			public void subscribe(Subscriber<? super String> subscriber) {
				subscriber.onNext("a");
				subscriber.onNext("b");
				subscriber.onNext("c");
				subscriber.onNext("d");
				subscriber.onNext("e");
			}
		}).doOnSubscribe(System.out::println);

		TimeUnit.SECONDS.sleep(30);
	}
}
