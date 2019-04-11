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
 * @since 2017年5月14日 下午5:35:50
 * @version v 0.1
 */
public class ReceiveUserMode extends ReceiveMode {
	public static final String COMMAND = "MODE";

	public ReceiveUserMode(OutputQueue replyQueue, IrcMessage message) {
		super(replyQueue, message);
	}
}