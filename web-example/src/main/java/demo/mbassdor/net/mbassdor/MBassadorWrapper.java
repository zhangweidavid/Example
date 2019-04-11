/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.mbassdor;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.core.event.BusWatchdogEvent;
import demo.mbassdor.core.event.IrcEvent;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.listener.Handler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:17:15
 * @version v 0.1
 */
public class MBassadorWrapper {

	private static final Logger logger = LogManager.getLogger(MBassadorWrapper.class);
	private MBassador<IrcEvent> delegate;

	private final IBusConfiguration configuration;

	private final Set<Object> listeners;

	private final Set<IPublicationErrorHandler> errorHandlers;

	public MBassadorWrapper(IBusConfiguration configuration) {
		this.configuration = configuration;

		listeners = new HashSet<Object>();
		errorHandlers = new HashSet<IPublicationErrorHandler>();

		rebootDelegate();

		Thread wd = new Thread() {
			private Semaphore activityMonitor = new Semaphore(0);

			private Semaphore watchdogMonitor = new Semaphore(0);

			public void run() {
				logger.trace("Bus watchdog running");
				Thread.currentThread().setName("BusWatchdog");

				while (!Thread.interrupted()) {
					try {
						boolean activityTimedout = !activityMonitor.tryAcquire(30, TimeUnit.SECONDS);
						if (activityTimedout) {
							logger.trace("No activity, checking if the bus is alive");
							publish(new BusWatchdogEvent(null));
							boolean watchdogTimedout = !activityMonitor.tryAcquire(5, TimeUnit.SECONDS);
							if (watchdogTimedout) {
								logger.trace("The bus is not alive, rebooting the bus");
								rebootDelegate();
							} else {
								logger.trace("The bus is alive");
							}
						}
					} catch (InterruptedException e) {
						break;
					}
				}
				logger.trace("Interrupted, exiting");
			};

			@Handler
			public void activityMonitor(IrcEvent event) {
				activityMonitor.release();
			}

			@Handler
			public void watchdogMonitor(BusWatchdogEvent event) {
				watchdogMonitor.release();
			}
		};
		subscribe(wd);
		wd.start();
	}

	private synchronized void rebootDelegate() {
		// Make sure old delegate is disposed of
		if (delegate != null)
			delegate.shutdown();

		// New delegate
		delegate = new MBassador<IrcEvent>(configuration);

		// Add the active listeners and handler the old delegate had.
		for (Object listener : listeners)
			delegate.subscribe(listener);
		for (IPublicationErrorHandler handler : errorHandlers)
			delegate.getRegisteredErrorHandlers().add(handler);
			
	}

//	public synchronized void addErrorHandler(IPublicationErrorHandler handler) {
//		delegate.getRuntime()
//	}

	public synchronized void publish(IrcEvent message) {
		delegate.publish(message);
	}

	public synchronized IMessagePublication publishAsync(IrcEvent message) {
		return delegate.publishAsync(message);
				
	}

	public synchronized void shutdown() {
		delegate.shutdown();
	}

	public synchronized void subscribe(Object listener) {
		delegate.subscribe(listener);
		listeners.add(listener);
	}

	public synchronized boolean unsubscribe(Object listener) {
		listeners.remove(listener);
		return delegate.unsubscribe(listener);
	}
}
