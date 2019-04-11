/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:11:14
 * @version v 0.1
 */
@Listener(references = References.Strong)
public class BusErrorHandler implements IPublicationErrorHandler {

	private static final Logger logger = LogManager.getLogger(BusErrorHandler.class);

	@Override
	public void handleError(PublicationError error) {
		logger.error(error.toString());
		logAllSubCauses(error.getCause());

	}

	private void logAllSubCauses(Throwable cause) {
		Throwable subcause = cause.getCause();
		if (subcause != null) {
			logger.error("Subcause of " + cause, subcause);
			logAllSubCauses(subcause);
		}
	}
}
