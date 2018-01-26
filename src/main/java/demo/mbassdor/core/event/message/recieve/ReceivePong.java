/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import demo.mbassdor.core.event.message.MessageReceive;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:44:30
 * @version v 0.1
 */
public class ReceivePong extends MessageReceive {
	public static final String COMMAND = "PONG";

	private final String[] params;

	public ReceivePong(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		params = message.getParams();
	}

	public String[] getParams() {
		return params;
	}
}
