/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event;

import java.net.Socket;

import demo.mbassdor.net.io.OutputQueue;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:33:58
 * @version v 0.1
 */
public abstract class IrcConnectionEvent extends IrcEvent {

	private final Socket socket;

	public IrcConnectionEvent(OutputQueue replyQueue, Socket socket) {
		super(replyQueue);
		this.socket = socket;
	}

	/**
	 * Get the socket that the event pertains to.
	 * 
	 * @return The related Socket, or null if initial or final use of the
	 *         socket.
	 */
	public Socket getSocket() {
		return socket;
	}

	@Override
	public String toString() {
		return "IrcConnectionEvent [socket=" + socket + "]";
	}

}