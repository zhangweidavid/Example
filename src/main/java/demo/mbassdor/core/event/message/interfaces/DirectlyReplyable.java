/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.core.event.message.interfaces;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:27:45
 * @version v 0.1
 */
public interface DirectlyReplyable {
	/**
	 * Reply to the event with some text by appending the sender to the reply
	 * message.
	 * 
	 * @param reply
	 *            Text to use in reply
	 */
	public void replyDirectly(String reply);
}
