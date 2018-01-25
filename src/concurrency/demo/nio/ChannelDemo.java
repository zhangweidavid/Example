/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月9日
 */

package concurrency.demo.nio;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月9日 下午5:29:56
 * @version v 0.1
 */
public class ChannelDemo {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			HttpSocket socket=	new HttpSocket();
			socket.doHttpConnection("www.kaola.com", 80);
			socket.doHttpConnection("www.jd.com", 80);
			socket.doHttpConnection("www.suning.com", 80);
			socket.doHttpConnection("www.tmall.com", 80);
			socket.start();

		}
	}
}
