/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月10日
 */

package concurrency.demo.completableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月10日 下午12:55:38
 * @version v 0.1
 */
public class Shop {
	private String name;
	private Random random = new Random();

	public Shop(String name) {
		this.name = name;
	}

	// 直接获取价格
	public double getPrice(String product) {
		return calculatePrice(product);
	}

	// 模拟延迟
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 模拟获取价格的服务
	private double calculatePrice(String product) {
		delay();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}

	// 异步获取价格
	public CompletableFuture<Double> getPriceAsync(String product) {
		return CompletableFuture.supplyAsync(()->calculatePrice(product));
		
	}
}
