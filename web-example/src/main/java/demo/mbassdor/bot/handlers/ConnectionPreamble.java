/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot.handlers;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.core.event.connection.IrcConnect;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.InvalidMessageParamException;
import demo.mbassdor.net.message.IrcMessage;
import demo.mbassdor.net.message.SendMessage;
import net.engio.mbassy.listener.Handler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:05:20
 * @version v 0.1
 */
public class ConnectionPreamble {
	private static final Logger logger = LogManager.getLogger();
	private String user;

	private String nick;

	private String realName;

	public ConnectionPreamble(String user, String nick, String realName) {
		this.user = user;
		this.nick = nick;
		this.realName = realName;
	}

	/**
	 * Sends the two required NICK and USER messages in sequence immediately
	 * after a connection is made.
	 */
	@Handler(priority = Integer.MIN_VALUE + 1)
	private void initialConnectionHandler(IrcConnect event) {
		OutputQueue replyQueue = event.getReplyQueue();

		logger.trace("Sending connection nick/user preamble");
		try {
			List<IrcMessage> messageList = new LinkedList<IrcMessage>();

			// TODO use SendMessage's static methods
			messageList.add(SendMessage.nick(nick));
			messageList.add(SendMessage.user(user, "0", realName));

			for (IrcMessage message : messageList)
				replyQueue.offer(message);
		} catch (InvalidMessageParamException e) {
			logger.error("Couldn't enqueue nick/user info preamble.", e);
		}
	}
}
