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
 * @since 2017年5月14日 下午5:37:57
 * @version v 0.1
 */
public class ReceiveQuit extends MessageReceive {
	public static final String COMMAND = "QUIT";

	private final String quitter;

	private final String text;

	public ReceiveQuit(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		quitter = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		text = message.getParams()[0].substring(1);
	}

	public String getQuitter() {
		return quitter;
	}

	public String getText() {
		return text;
	}
}
