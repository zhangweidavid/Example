/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月10日
 */

package rxjava.demo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月10日 上午10:54:23
 * @version v 0.1
 */
public class ZipDemo {
	public static void main(String[] args) throws Exception {
		Flowable<String> a = Flowable.fromArray("a");
		Flowable<String> b = Flowable.fromArray("b");
		Flowable.zip(a, b, (x, y) -> x + y).subscribe(System.out::println);

		Observable<Long> red = Observable.interval(10, TimeUnit.MILLISECONDS);
		Observable<Long> green = Observable.interval(10, TimeUnit.MILLISECONDS);
		Observable
				.zip(red.timestamp(), green.timestamp(), (r, g) -> (r.value() - g.value()) + r.time() + "," + g.time())
				.forEach(System.out::println);

		TimeUnit.SECONDS.sleep(30);
		System.out.println("end");
	}
}
