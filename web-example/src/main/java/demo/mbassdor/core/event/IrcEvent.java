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
 * @since 2017年5月14日 上午11:06:29
 * @version v 0.1
 */
public abstract class IrcEvent {

	private final OutputQueue replyQueue;

	/**
	 * Reference to the queue supplied at instantiation to handle reply
	 * messages.
	 * 
	 * @return The supplied queue for reply messages.
	 */
	public OutputQueue getReplyQueue() {
		return replyQueue;
	}

	public IrcEvent(OutputQueue replyQueue) {
		this.replyQueue = replyQueue;
	}

}
