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
 * @since 2017年5月14日 下午5:39:24
 * @version v 0.1
 */
public class ReceivePart extends MessageReceive {
	public static final String COMMAND = "PART";

	private final String channel;

	private final String parter;

	private final String reason;

	public ReceivePart(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		parter = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		channel = message.getParams()[0];
		reason = message.getParams()[1].substring(1);
	}

	public String getChannel() {
		return channel;
	}

	public String getParter() {
		return parter;
	}

	public String getReason() {
		return reason;
	}
}
