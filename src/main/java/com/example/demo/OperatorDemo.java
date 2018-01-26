/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月2日
 */

package com.example.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月2日 下午4:58:20
 * @version v 0.1
 */
public class OperatorDemo {
	public static void main(String[] args) throws Exception {
		Flowable<String> f = Flowable.just("1", "2");
		System.out.println("print");
		f.delay(10, TimeUnit.SECONDS).subscribe(System.out::print);
		System.out.println();
		/**
		 * 将字符串转换为数字后，再平方，再转换为字符串
		 */
		Flowable<String> i = f.map(s -> Integer.valueOf(s)).map(s -> s * s).map(s -> String.valueOf(s));
		i.subscribe(System.out::print);
		System.out.println();
		/**
		 * 两个flowable平铺在一起
		 */
		Flowable<Flowable<String>> j = Flowable.just(f, i);
		Flowable<String> tmp = j.flatMap(a -> a.startWith("3"));

		tmp.subscribe(System.out::print);
		System.out.println("concatMap");
		j.concatMap(a->a.startWith("3")).subscribe(System.out::println);
		System.out.println();
		/**
		 * 奇数，偶数分组
		 */
		tmp.groupBy(s -> Integer.valueOf(s).intValue() % 2 == 1 ? "奇" : "偶")
				.subscribe(entry -> entry.subscribe(s -> System.out.println(entry.getKey() + "_" + s)));
		/**
		 * 两个字符串一组
		 */
		tmp.buffer(2).subscribe(System.out::println);
		/**
		 * Scan
		 */
		Flowable.just(1, 2, 3, 4, 5).scan((a, b) -> a + b).subscribe(System.out::println);
		
		/**
		 * ZIP
		 */
		Flowable.zip(Flowable.defer(()->Flowable.fromCallable(()->{
			System.out.println(Thread.currentThread().getName()+",Time"+new Date());
			TimeUnit.SECONDS.sleep(2);
			return Arrays.asList("A", "B", "C");
		}).flatMap(a->Flowable.fromIterable(a))), Flowable.defer(()->Flowable.fromCallable(()->{
			System.out.println(Thread.currentThread().getName()+",Time"+new Date());
			TimeUnit.SECONDS.sleep(3);
			return Arrays.asList("L1", "L2", "L3");
		}).flatMap(a->Flowable.fromIterable(a))), (a,b)->{
			TestClass t=new TestClass();
			t.setName(a);
			t.setTest(b);
			return t;
		}).zipWith(Flowable.fromCallable(()->{
			System.out.println(Thread.currentThread().getName()+",Time"+new Date());
			TimeUnit.SECONDS.sleep(3);
			return Arrays.asList(1, 2, 3);
		}).subscribeOn(Schedulers.io()).flatMap(a->Flowable.fromIterable(a)), (a,t)->{
			a.setValue(t);
			return a;
		}).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread()).subscribe(s->System.out.println(s+"time"+new Date()));
		/**
		 * window
		 */
		tmp.window(2).subscribe(s -> s.subscribe(System.out::print));
		// Flowable.interval(1,
		// TimeUnit.SECONDS).subscribe(System.out::println);
		Flowable<TestClass> testF=Flowable.just(new TestClass(), new TestClass());
		testF.subscribe(t->{
			t.setValue(1);
		});
		testF.subscribe(t->{
			t.setName("n");
		});
		testF.subscribe(System.out::println);
		Flowable.fromCallable(() -> {
			return System.in.read();
			// return null;
		}).subscribe(System.out::println);
		List<List<Integer>> list = Flowable.range(1, 1000).buffer(900).toList().blockingGet();
		System.out.println(list);
		List<List<Object>> objList = Flowable.fromArray().buffer(900).toList().blockingGet();
		System.out.println(objList);
		Flowable.fromArray().skip(1).subscribe(System.out::println);
		
		Flowable.create(s -> {
			for (;;) {
				int a = System.in.read();
				s.onNext(a);
			}
		}, BackpressureStrategy.DROP).subscribe(System.out::println);

		TimeUnit.HOURS.sleep(1);
	}
	
	public static class TestClass{
		private int value;
		
		private String name;
		
		private String test;
		
		
		
		

		public String getTest() {
			return test;
		}

		public void setTest(String test) {
			this.test = test;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		/** 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "TestClass [value=" + value + ", name=" + name + ", test=" + test + "]";
		}

	
		
	}
}
