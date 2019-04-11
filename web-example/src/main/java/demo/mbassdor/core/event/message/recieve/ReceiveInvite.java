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
 * @since 2017年5月14日 下午5:40:24
 * @version v 0.1
 */
public class ReceiveInvite extends MessageReceive {
	public static final String COMMAND = "INVITE";

	private final String channel;

	private final String invitee;

	private final String sender;

	public ReceiveInvite(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		sender = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		invitee = message.getParams()[0];
		channel = message.getParams()[1];
	}

	public String getChannel() {
		return channel;
	}

	public String getInvitee() {
		return invitee;
	}

	public String getSender() {
		return sender;
	}
}
