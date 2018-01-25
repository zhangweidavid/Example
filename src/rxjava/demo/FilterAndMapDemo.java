/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月22日
 */

package rxjava.demo;

import io.reactivex.Observable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月22日 下午7:17:56
 * @version v 0.1
 */
public class FilterAndMapDemo {
	public static void main(String[] args) {
		Observable.just(8, 9, 10).doOnNext(t -> System.out.println("A:" + t)).filter(i -> i % 3 > 0)
				.doOnNext(t -> System.out.println("B:" + t)).map(i -> "#" + i * 10)
				.doOnNext(t -> System.out.println("C:" + t)).filter(s -> s.length() > 4)
				.subscribe(t -> System.out.println("Done:" + t));
	}
}
