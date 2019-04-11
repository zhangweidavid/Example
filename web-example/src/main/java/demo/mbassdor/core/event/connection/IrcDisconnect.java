/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.connection;

import java.net.Socket;

import demo.mbassdor.core.event.IrcConnectionEvent;
import demo.mbassdor.net.io.OutputQueue;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:33:18
 * @version v 0.1
 */
public class IrcDisconnect extends IrcConnectionEvent {

	public IrcDisconnect(OutputQueue replyQueue, Socket socket) {
		super(replyQueue, socket);
	}

}
