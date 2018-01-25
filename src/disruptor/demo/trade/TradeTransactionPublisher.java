/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package disruptor.demo.trade;

import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.dsl.Disruptor;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:12:09
 * @version v 0.1
 */
public class TradeTransactionPublisher implements Runnable {
	Disruptor<TradeTransaction> disruptor;
	private CountDownLatch latch;
	private static int LOOP = 100000;

	public TradeTransactionPublisher(CountDownLatch latch, Disruptor<TradeTransaction> disruptor) {
		this.disruptor = disruptor;
		this.latch = latch;
	}

	@Override
	public void run() {
		TradeTransactionEventTranslator tradeTransloator = new TradeTransactionEventTranslator();
		for (int i = 0; i < LOOP; i++) {
			disruptor.publishEvent(tradeTransloator);
		}
		latch.countDown();
	}

}
