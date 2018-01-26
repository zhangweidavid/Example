/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.engio.mbassy.bus.common.DeadMessage;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:15:15
 * @version v 0.1
 */
@Listener(references=References.Strong)
public class DeadMessageHandler {

	private static final Logger logger = LogManager.getLogger(DeadMessageHandler.class);

	@Handler
	private void logDeadMessage(DeadMessage msg) {
		logger.trace("Unhandled message: '" + msg.getMessage() + "'");
	}

}
