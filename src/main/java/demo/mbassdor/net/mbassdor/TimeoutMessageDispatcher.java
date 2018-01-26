/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.mbassdor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.engio.mbassy.bus.MessagePublication;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.dispatch.MessageDispatcher;
import net.engio.mbassy.subscription.SubscriptionContext;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:15:49
 * @version v 0.1
 */
public class TimeoutMessageDispatcher extends MessageDispatcher {

	private static final Logger logger = LogManager.getLogger(TimeoutMessageDispatcher.class);

	private static final ExecutorService service = Executors.newCachedThreadPool();

	// Super doesn't specify types, can't do anything to correct that
	@SuppressWarnings("rawtypes")
	public TimeoutMessageDispatcher(SubscriptionContext context, IHandlerInvocation invocation) {
		super(context, invocation);
	}

	// Super doesn't specify types, can't do anything to correct that
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void dispatch(MessagePublication publication, final Object message, Iterable  listeners) {
		publication.markDispatched();
		for (final Object listener : listeners) {
			Future future = service.submit(new Runnable() {
				@Override
				public void run() {
					getInvocation().invoke(listener, message, publication);
				}
			});

			try {
				future.get(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				logger.error("Handler threw exception on invocation", e);
			} catch (TimeoutException e) {
				future.cancel(true);
				logger.error("Invocation timed out for handler in listener " + listener + " with message " + message);
			}
		}
	}
}
