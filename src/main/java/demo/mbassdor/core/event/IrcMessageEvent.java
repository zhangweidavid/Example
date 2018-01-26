/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event;

import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:30:28
 * @version v 0.1
 */
public abstract class IrcMessageEvent extends IrcEvent {

	private final IrcMessage message;

	public IrcMessageEvent(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue);
		this.message = message;
	}

	/**
	 * Get the IRC message
	 */
	public IrcMessage getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message.toString();
	}

}
