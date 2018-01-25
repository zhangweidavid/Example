/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package disruptor.demo.trade;

import com.lmax.disruptor.EventHandler;

import rxjava.demo.LogUtil;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:11:34
 * @version v 0.1
 */
public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		LogUtil.log("Send JMS. " + event.getExtend());
	}
}
