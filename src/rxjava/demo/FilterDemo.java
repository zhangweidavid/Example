/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月3日
 */

package rxjava.demo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月3日 下午1:54:37
 * @version v 0.1
 */
public class FilterDemo {
	public static void main(String[] args) {
		/**
		 * Filter
		 * 
		 */
		Flowable.just(2, 30, 22, 5, 60, 1).filter(a -> a > 10).subscribe(System.out::println);

		/**
		 * Fisrt
		 */
		Flowable.range(1, 4).firstElement().subscribe(System.out::println);
		Flowable.range(1, 4).first(1).subscribe(System.out::println);
		/**
		 * last
		 */
		Flowable.range(1, 4).lastElement().subscribe(System.out::println);
		Flowable.range(1, 4).last(2).subscribe(System.out::println);

		/**
		 * skip
		 */
		Flowable.range(1, 4).skip(2).subscribe(System.out::println);
		Flowable.range(1, 4).skipLast(2).subscribe(System.out::println);
		
		/**
		 * ignore
		 */
		Flowable.range(1, 4).ignoreElements().subscribe(System.out::println);
		
		/**
		 * Sample
		 */
		Flowable.interval(1, TimeUnit.SECONDS).sample(10, TimeUnit.SECONDS).subscribe(System.out::println);
		
		Flowable.just(1, 2, 2, 1, 3).distinct().subscribe(System.out::println);
		
		Flowable.range(1, 10).elementAt(6).subscribe(System.out::println);
		
		Flowable.range(1, 4).take(2).subscribe(System.out::println);
		Flowable.create(new FlowableOnSubscribe<String>() {

			@Override
			public void subscribe(FlowableEmitter<String> paramFlowableEmitter) throws Exception {
				int i=0;
				for(;;){
					long sleep=(long) (0.5);
					TimeUnit.SECONDS.sleep(sleep);
					paramFlowableEmitter.onNext("ping"+i+++",sleep:"+sleep);
				}
			}
		}, BackpressureStrategy.DROP).debounce(1, TimeUnit.SECONDS).subscribe(System.out::println);
		try {
			TimeUnit.HOURS.sleep(1);
		} catch (InterruptedException e) {
			//logger.error("", e);
		}
	}
}
