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
 * @since 2017年5月14日 下午5:28:08
 * @version v 0.1
 */
public interface PrivatelyReplyable {
	/**
	 * Reply to the event's sender privately with some text.
	 * 
	 * @param reply
	 *            Text to use in reply
	 */
	public void replyPrivately(String reply);
}
