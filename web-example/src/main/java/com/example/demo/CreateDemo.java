/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月3日
 */

package com.example.demo;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年3月3日 上午11:50:52
 * @version  v 0.1
 */
public class CreateDemo {

	/**
	 * 
	 * @param args
	 * @author wei.zw
	 */
	public static void main(String[] args) {
		/**
		 * create
		 */
		Flowable.create(new FlowableOnSubscribe<String>() {

			@Override
			public void subscribe(FlowableEmitter<String> paramFlowableEmitter) throws Exception {
				paramFlowableEmitter.onNext("item");
			}
		},BackpressureStrategy.DROP).subscribe(System.out::println);
		
		Flowable.create(s->{
			s.onNext("item");
		}, BackpressureStrategy.ERROR).doOnError(e->e.printStackTrace());
		/**
		 * 
		 */
		Flowable.defer(()->Flowable.just("item")).subscribe(System.out::println);
		
		Flowable.empty().subscribe(s->System.out.println("p"+s));
		
		Flowable.never().subscribe(s->System.out.println("p"+s));
		
		Flowable.error(new UnsupportedOperationException()).subscribe(System.out::println, e-> System.out.println("Error : "+e));
		
		Flowable.just(1);
		//Flowable.ju
		Flowable.fromArray(1,2);
		//Flowable.from
		Flowable.timer(1L, TimeUnit.SECONDS).subscribe(System.out::println);
		Flowable.range(1, 10).scan((a,b)->a+b).lastElement().subscribe(System.out::println);
		Flowable.just(1).repeat(10).subscribe(System.out::println);
		
		
	}

}
