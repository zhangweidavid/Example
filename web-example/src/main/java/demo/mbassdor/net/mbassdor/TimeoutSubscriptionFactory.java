/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.mbassdor;

import net.engio.mbassy.dispatch.EnvelopedMessageDispatcher;
import net.engio.mbassy.dispatch.FilteredMessageDispatcher;
import net.engio.mbassy.dispatch.IHandlerInvocation;
import net.engio.mbassy.dispatch.IMessageDispatcher;
import net.engio.mbassy.subscription.SubscriptionContext;
import net.engio.mbassy.subscription.SubscriptionFactory;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:22:16
 * @version v 0.1
 */
public class TimeoutSubscriptionFactory extends SubscriptionFactory {
	// Super doesn't specify types, can't do anything to correct that and still
	// override
	@SuppressWarnings("rawtypes")
	@Override
	protected IMessageDispatcher buildDispatcher(SubscriptionContext context, IHandlerInvocation invocation) {
		IMessageDispatcher dispatcher = new TimeoutMessageDispatcher(context, invocation);
		if (context.getHandler().isEnveloped()) {
			dispatcher = new EnvelopedMessageDispatcher(dispatcher);
		}
		if (context.getHandler().isFiltered()) {
			dispatcher = new FilteredMessageDispatcher(dispatcher);
		}
		return dispatcher;
	}

	// TODO override actual handler maker thing, so we target individual methods
	// instead of whole container classes?
}
