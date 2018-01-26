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
 * @since 2017年5月14日 下午5:45:05
 * @version v 0.1
 */
public class ReceiveNick extends MessageReceive {
	public static final String COMMAND = "NICK";

	private final String nick;

	private final String sender;

	public ReceiveNick(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		sender = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		nick = message.getParams()[0];
	}

	public String getNick() {
		return nick;
	}

	public String getSender() {
		return sender;
	}
}
