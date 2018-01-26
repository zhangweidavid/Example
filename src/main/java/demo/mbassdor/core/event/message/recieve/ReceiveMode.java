/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import java.util.Arrays;
import java.util.List;

import demo.mbassdor.core.event.message.MessageReceive;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:24:21
 * @version v 0.1
 */
public class ReceiveMode extends MessageReceive {
	public static final String COMMAND = "MODE";

	// private final String sender;

	private final List<String> flagParams;

	private final String flags;

	private final String target;

	public ReceiveMode(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);

		String[] params = message.getParams();

		// sender = message.getPrefix().substring( 0,
		// message.getPrefix().indexOf( '!' ) );
		target = params[0];
		flags = params[1];

		// Channel mode or user mode?
		if (params.length > 2) {
			// Channel mode
			flagParams = Arrays.asList(Arrays.copyOfRange(params, 2, params.length));
			String firstFlagParam = flagParams.get(0);
			if (firstFlagParam.startsWith(":"))
				flagParams.set(0, firstFlagParam.substring(1));
		} else {
			// User mode
			flagParams = null;
		}
	}

	// public String getSender()
	// {
	// return sender;
	// }

	public List<String> getFlagParams() {
		return flagParams;
	}

	public String getFlags() {
		return flags;
	}

	public String getTarget() {
		return target;
	}
}
