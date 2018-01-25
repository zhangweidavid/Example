/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.core.event.message.MessageReceive;
import demo.mbassdor.core.event.message.interfaces.DirectlyReplyable;
import demo.mbassdor.core.event.message.interfaces.PrivatelyReplyable;
import demo.mbassdor.core.event.message.interfaces.Replyable;
import demo.mbassdor.core.misc.LineWrap;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.InvalidMessageParamException;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:30:20
 * @version v 0.1
 */
public class ReceivePrivmsg extends MessageReceive implements Replyable, DirectlyReplyable, PrivatelyReplyable {
	
	private static final Logger logger=LogManager.getLogger();
	public static final String COMMAND = "PRIVMSG";

	private final String channel;

	private final String sender;

	private final String text;

	public ReceivePrivmsg(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		sender = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		if (!message.getParams()[0].startsWith("#"))
			channel = sender;
		else
			channel = message.getParams()[0];
		text = message.getParams()[1].substring(1);
	}

	public String getChannel() {
		return channel;
	}

	public String getSender() {
		return sender;
	}

	public String getText() {
		return text;
	}

	@Override
	public void reply(String reply) {
		try {
			for (String line : LineWrap.wrap(reply))
				getReplyQueue().privmsg(channel, line);
		} catch (InvalidMessageParamException e) {
			logger.error( "Error creating reply message",e);
		}
	}

	@Override
	public void replyDirectly(String reply) {
		reply(sender + ": " + reply);
	}

	@Override
	public void replyPrivately(String reply) {
		try {
			for (String line : LineWrap.wrap(reply))
				getReplyQueue().privmsg(sender, line);
		} catch (InvalidMessageParamException e) {
			logger.error("Error creating reply message", e);
		}
	}
}
