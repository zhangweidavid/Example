/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot;

import demo.mbassdor.net.IrcConnection.SslMode;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午6:58:44
 * @version v 0.1
 */
public class Test {
	public static void main(String[] args) {
		Channel[] channels = new Channel[1];
		channels[0] = new Channel("test", "test");
		KeratinBot bot = new KeratinBot("test", "David", "wei", "localhost", 8090, SslMode.OFF, channels);

		bot.connect();
		System.out.println("connected");
		bot.sendPrivmsgAs("test", "test", "tst");
	}
}
