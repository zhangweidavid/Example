/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月5日
 */

package disruptor.demo.trade;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月5日 下午3:03:35
 * @version v 0.1
 */
public class TradeTransaction {
	private String id;// 交易ID
	private double price;// 交易金额
	
	private Map<String,String> extend=new HashMap<>();

	public TradeTransaction() {
	}

	public TradeTransaction(String id, double price) {
		super();
		this.id = id;
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Map<String, String> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, String> extend) {
		this.extend = extend;
	}
	
	
}
