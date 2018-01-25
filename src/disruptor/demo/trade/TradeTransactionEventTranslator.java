/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package disruptor.demo.trade;

import java.util.Random;

import com.lmax.disruptor.EventTranslator;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:12:53
 * @version v 0.1
 */
public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
	private Random random = new Random();

	@Override
	public void translateTo(TradeTransaction event, long sequence) {
		this.generateTradeTransaction(event);
	}

	private TradeTransaction generateTradeTransaction(TradeTransaction trade) {
		trade.setPrice(random.nextDouble() * 9999);
		return trade;
	}
}
