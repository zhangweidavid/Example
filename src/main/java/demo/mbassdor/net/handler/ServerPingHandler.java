/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.core.event.message.recieve.ReceivePing;
import demo.mbassdor.core.event.message.recieve.ReceivePong;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:13:38
 * @version v 0.1
 */
@Listener(references=References.Strong)
public class ServerPingHandler {

	private static final Logger logger = LogManager.getLogger(ServerPingHandler.class);

	@Handler
	private void handlePingPong(ReceivePing event) {
		logger.trace("Handling PING by sending echo PONG");
		event.pong();
	}

	@Handler
	private void handleServerPong(ReceivePong event) {
		// Nothing
	}
}
