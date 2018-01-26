/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.core.event.message.MessageReceive;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.InvalidMessageParamException;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:36:23
 * @version v 0.1
 */
public class ReceivePing extends MessageReceive {
	private static final Logger logger = LogManager.getLogger();
	public static final String COMMAND = "PING";

	private final String[] params;

	public ReceivePing(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		params = message.getParams();
	}

	public String[] getParams() {
		return params;
	}

	public void pong() {
		try {
			if (params.length == 1)
				getReplyQueue().pong(params[0]);
			else if (params.length == 2)
				getReplyQueue().pong(params[0], params[1]);
			else
				logger.error("Can't PONG, invalid number of PING message parameters");
		} catch (InvalidMessageParamException e) {
			logger.error("Error creating reply message", e);
		}
	}
}
