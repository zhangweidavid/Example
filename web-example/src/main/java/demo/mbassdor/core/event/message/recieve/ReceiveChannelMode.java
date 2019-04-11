/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.recieve;

import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.IrcMessage;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:39:53
 * @version v 0.1
 */
public class ReceiveChannelMode extends ReceiveMode {
	public static final String COMMAND = "MODE";

	public ReceiveChannelMode(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);
	}
}
