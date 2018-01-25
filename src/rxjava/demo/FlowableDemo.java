/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月3日
 */

package rxjava.demo;

import java.util.ArrayList;
import java.util.List;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月3日 上午10:18:11
 * @version v 0.1
 */
public class FlowableDemo {
	public static void main(String[] args) {
		System.out.println("Buffer size:" + Flowable.bufferSize());
		/**
		 * 
		 */
		Single<Boolean> s = Flowable.just(1).all(i -> i.intValue() > 0);
		System.out.println("[1]全部大于0：" + s.blockingGet());

		List<Publisher<Integer>> publishers = new ArrayList<>();
		publishers.add(Flowable.just(1));
		publishers.add(Flowable.just(2));
		publishers.add(Flowable.just(3));
		Flowable.amb(publishers).subscribe(System.out::println);
	}
}
