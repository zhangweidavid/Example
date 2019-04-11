/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package com.example.corejava.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 下午12:57:21
 * @version v 0.1
 */
public class Client {
	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.currentTimeMillis();
		CompletableFuture<Double> future = shop.getPriceAsync("My Favorite");
		long invocationTime = System.currentTimeMillis() - start;
		System.out.println("调用接口时间：" + invocationTime + "毫秒");

		doSomethingElse();
		
		future.thenAccept(System.out::println);
		//CompletableFuture.allOf(cfs).
		long retrievalTime = System.currentTimeMillis() - start;
		System.out.println("返回价格消耗时间：" + retrievalTime + "毫秒");

	}

	public static void doSomethingElse() {
		System.out.println("做其他的事情。。。");
	}
}
