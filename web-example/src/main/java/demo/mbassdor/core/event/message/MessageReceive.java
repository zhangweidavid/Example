/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message;

import demo.mbassdor.core.event.IrcMessageEvent;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:37:47
 * @version v 0.1
 */
public abstract class MessageReceive extends IrcMessageEvent {

	public MessageReceive(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);
	}

}
