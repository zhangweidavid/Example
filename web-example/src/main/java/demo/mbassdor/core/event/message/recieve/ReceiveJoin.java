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
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.InvalidMessageParamException;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:33:48
 * @version v 0.1
 */
public class ReceiveJoin extends MessageReceive implements Replyable, DirectlyReplyable, PrivatelyReplyable {

	private static final Logger logger = LogManager.getLogger();
	public static final String COMMAND = "JOIN";

	private final String channel;

	private final String joiner;

	public ReceiveJoin(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		joiner = message.getPrefix().substring(0, message.getPrefix().indexOf('!'));
		channel = message.getParams()[0].substring(1);
	}

	public String getChannel() {
		return channel;
	}

	public String getJoiner() {
		return joiner;
	}

	@Override
	public void reply(String reply) {
		try {
			getReplyQueue().privmsg(channel, reply);
		} catch (InvalidMessageParamException e) {
			logger.error("Error creating reply message", e);
		}
	}

	@Override
	public void replyDirectly(String reply) {
		reply(joiner + ": " + reply);
	}

	@Override
	public void replyPrivately(String reply) {
		try {
			getReplyQueue().privmsg(joiner, reply);
		} catch (InvalidMessageParamException e) {
			logger.error("Error creating reply message",e);
		}
	}
}
