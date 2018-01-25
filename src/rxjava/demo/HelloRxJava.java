/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年2月24日
 */

package rxjava.demo;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年2月24日 下午3:33:14
 * @version v 0.1
 */
public class HelloRxJava {

	/**
	 * 
	 * @param args
	 * @author wei.zw
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		/**
		 * 第一步：创建只含有一个字符串元素的Obserable 第二步：对Obserable进行操作 第三部:订阅者消费事件
		 */
		Observable.just("Hello").map(m -> m + ", RxJava").subscribe(System.out::println);
		
		Flowable.just("Hello world").subscribe(System.out::println);
		/**
		 * Java 8 
		 */
		Stream.of("Hello").map(m -> m + ",Java8 Strem").forEach(System.out::println);
		
		
		Observable.fromArray("Hello", "Hello").map(new Function<String, String>() {

			@Override
			public String apply(String paramT) throws Exception {
				System.out.println("map->" + paramT);
				return paramT + " ,RxJava too";
			}
		}).subscribeOn(Schedulers.newThread()).subscribe(System.out::println, System.out::println, new Action() {

			@Override
			public void run() throws Exception {
				System.out.println("Complete,"+Thread.currentThread().getName());
			}
		});
		
		/**
		 * RxJava 一个比较典型的使用场景，将耗时的计算，网络请求等放在后台线程执行，将执行结果在UI线程中进行显示
		 * 通常，通过subscriberOn将耗时的计算，阻塞的IO请求放到后台线程，通过observeOn设置一旦数据准备完毕，确保它在GUI或前台线程中处理。
		 */
		Flowable.fromCallable(()->{
			Thread.sleep(1000); //  耗时计算
			System.out.println(Thread.currentThread().getName());
		    return "Done";
		}).subscribeOn(Schedulers.io()).observeOn(Schedulers.single()).subscribe(s->{
			System.out.println("subscriber:"+Thread.currentThread().getName()+" - "+s);
		});

		Thread.sleep(2000);
		
		Flowable<String> tmp=Flowable.fromCallable(new Callable<String>() {

			@Override
			public String call() throws Exception {
				Thread.sleep(1000); //  耗时计算
				return "Done";
			}
		});
		tmp=tmp.subscribeOn(Schedulers.io());
		tmp=tmp.observeOn(Schedulers.single());
		tmp.subscribe(new Consumer<String>() {

			@Override
			public void accept(String paramT) throws Exception {
				System.out.println("subscriber:"+Thread.currentThread().getName()+" - "+paramT);
			}
		});
		
		Thread.sleep(2000);
		//
		
		Flowable.range(1, 10)
		  .observeOn(Schedulers.computation())
		  .map(v -> v * v)
		  .blockingSubscribe(System.out::println);
		

	}

}
