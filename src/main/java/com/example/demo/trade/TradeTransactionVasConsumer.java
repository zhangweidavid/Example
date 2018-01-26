/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package com.example.demo.trade;

import com.lmax.disruptor.EventHandler;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月5日 下午3:13:32
 * @version  v 0.1
 */
public class TradeTransactionVasConsumer implements EventHandler<TradeTransaction> {  
	  
    @Override  
    public void onEvent(TradeTransaction event, long sequence,  
            boolean endOfBatch) throws Exception {  
        	event.getExtend().put("vas", "done");
    }  
      
}  