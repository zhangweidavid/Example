/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年3月10日
 */

package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;

import io.reactivex.Flowable;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年3月10日 上午9:15:54
 * @version v 0.1
 */
public class RxAndStream {
	
	private static final Logger LOG = org.apache.logging.log4j.LogManager.getLogger(RxAndStream.class);
	/**
	 * 
	 * @param args
	 * @author wei.zw
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Stream<String> stream = Stream.of("one", "two", "three", "four", "five", "six", "seven", "egith", "nine", "ten",
				"one", "two", "three", "four", "five", "six", "seven", "egith", "nine", "ten");
		stream.skip(5).limit(5).map(s -> s + "_stransformed stream").forEach(LOG::info);
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(3, "three");
		map.put(4, "four");
		map.put(5, "five");
		map.put(6, "six");
		map.put(7, "seven");
		map.put(8, "eight");
		map.put(9, "nine");
		map.put(0, "ten");
		Flowable.unsafeCreate(s -> {
			new Thread(() -> {

				for (int i = 1; i < 100; i++) {
					String v = map.get(i % 10);
					System.out.println(v);
					if (s != null) {
						s.onNext("" + v);
					}
				}
			}).start();
		}).skip(5).take(5).map(s -> s + "_stransformed rx").subscribe(System.out::println);

		TimeUnit.SECONDS.sleep(100);
	}

}
