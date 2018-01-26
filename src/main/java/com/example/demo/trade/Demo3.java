/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package com.example.demo.trade;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import com.example.demo.LogUtil;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:14:06
 * @version v 0.1
 */
public class Demo3 {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {
		long beginTime = System.currentTimeMillis();

		int bufferSize = 1024;

		// 这个构造函数参数，相信你在了解上面2个demo之后就看下就明白了，不解释了~
		Disruptor<TradeTransaction> disruptor = new Disruptor<TradeTransaction>(new EventFactory<TradeTransaction>() {
			@Override
			public TradeTransaction newInstance() {
				LogUtil.log("create new TradeTrasaction");
				return new TradeTransaction();
			}
		}, bufferSize, Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());
	
		// 使用disruptor创建消费者组C1,C2
		EventHandlerGroup<TradeTransaction> handlerGroup = disruptor.handleEventsWith(new TradeTransactionVasConsumer(),
				new TradeTransactionInDBHandler());
		TradeTransactionJMSNotifyHandler jmsConsumer = new TradeTransactionJMSNotifyHandler();
		// 声明在C1,C2完事之后执行JMS消息发送操作 也就是流程走到C3
		handlerGroup.then(jmsConsumer);

		disruptor.start();// 启动
		CountDownLatch latch = new CountDownLatch(1);
		// 生产者准备
		new TradeTransactionPublisher(latch, disruptor).run();
		latch.await();// 等待生产者完事.
		disruptor.shutdown();
		// executor.shutdown();

		LogUtil.log("总耗时:" + (System.currentTimeMillis() - beginTime));
	}
}
