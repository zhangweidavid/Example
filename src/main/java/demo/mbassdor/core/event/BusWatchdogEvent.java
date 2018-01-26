/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event;

import demo.mbassdor.net.io.OutputQueue;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:35:17
 * @version v 0.1
 */
public class BusWatchdogEvent extends IrcEvent {
	public BusWatchdogEvent(OutputQueue replyQueue) {
		super(replyQueue);
	}
}