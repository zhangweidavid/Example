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
 * @since 2017年5月14日 下午5:38:40
 * @version v 0.1
 */
public class ReceiveKick extends MessageReceive {
	public static final String COMMAND = "KICK";

	private final String channel;

	private final String sender;

	private final String target;

	public ReceiveKick(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		sender = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		channel = message.getParams()[0];
		target = message.getParams()[1];
	}

	public String getChannel() {
		return channel;
	}

	public String getSender() {
		return sender;
	}

	public String getTarget() {
		return target;
	}
}
