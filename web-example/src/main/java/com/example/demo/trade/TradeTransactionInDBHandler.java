/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package com.example.demo.trade;

import java.util.UUID;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import com.example.demo.LogUtil;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:04:32
 * @version v 0.1
 */
public class TradeTransactionInDBHandler implements EventHandler<TradeTransaction>, WorkHandler<TradeTransaction> {

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}

	@Override
	public void onEvent(TradeTransaction event) throws Exception {
		// 这里做具体的消费逻辑
		event.setId(UUID.randomUUID().toString());// 简单生成下ID
		event.getExtend().put("persistent", "done");
		LogUtil.log(event.getId());
	}
}