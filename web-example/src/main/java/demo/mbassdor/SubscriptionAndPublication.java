/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月15日
 */

package demo.mbassdor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

import java.io.File;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月15日 上午9:43:38
 * @version v 0.1
 */
public class SubscriptionAndPublication {

	static MBassador bus = new MBassador(new BusConfiguration().addFeature(Feature.SyncPubSub.Default())
			.addFeature(Feature.AsynchronousHandlerInvocation.Default())
			.addFeature(Feature.AsynchronousMessageDispatch.Default())
			.addPublicationErrorHandler(new IPublicationErrorHandler.ConsoleLogger())
			.setProperty(IBusConfiguration.Properties.BusId, "global bus"));

	public static void main(String[] args) throws Exception {

		// Listeners are subscribed by passing them to the #subscribe() method
		bus.subscribe(new ListenerDefinition.SyncAsyncListener());

		// #subscribe() is idem-potent => Multiple calls to subscribe do NOT add
		// the listener more than once (set semantics)
//		Object listener = new ListenerDefinition.SyncAsyncListener();
//		bus.subscribe(listener);
//		bus.subscribe(listener);

		// Classes without handlers will be silently ignored
		bus.subscribe(new Object());
		bus.subscribe(new String());

		bus.publishAsync(new File("/Users/david/work/log/service.log")); // returns
																			// immediately,
		// publication will
		// continue
		// asynchronously
		bus.post(new File("/Users/david/work/log/service.log")).asynchronously(); // same
																					// as
																					// above

		bus.publish("some message"); // will return after each handler has been
										// invoked
		bus.post("some message").now(); // same as above

		TimeUnit.SECONDS.sleep(20);

	}
}
