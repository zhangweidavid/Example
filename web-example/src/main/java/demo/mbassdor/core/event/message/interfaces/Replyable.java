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
 * @since 2017年5月14日 下午5:27:10
 * @version v 0.1
 */
public interface Replyable {
	/***
	 * Reply to the event with some text.**
	 * 
	 * @param reply
	 *            Text to use in reply
	 */

	public void reply(String reply);
}
