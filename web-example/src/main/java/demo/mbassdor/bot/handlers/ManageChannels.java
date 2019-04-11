/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot.handlers;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.bot.Channel;
import demo.mbassdor.bot.KeratinBot;
import demo.mbassdor.core.event.connection.IrcConnect;
import demo.mbassdor.core.event.message.recieve.ReceiveKick;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.message.InvalidMessageParamException;
import net.engio.mbassy.listener.Handler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午6:33:06
 * @version v 0.1
 */
public class ManageChannels {
	private static final Logger logger=LogManager.getLogger();
	private final Map<String, Channel> channels;

	private final KeratinBot bot;

	public ManageChannels(KeratinBot bot, Map<String, Channel> channels) {
		this.bot = bot;
		this.channels = channels;
	}

	/**
	 * Sends a join message for each of the given channels soon after a
	 * connection is made.
	 */
	@Handler(priority = Integer.MIN_VALUE + 2)
	private void initialConnectionHandler(IrcConnect event) {
		logger.trace("Sending initial channel join messages");
		for (Entry<String, Channel> channelEntry : channels.entrySet()) {
			OutputQueue replyQueue = event.getReplyQueue();
			Channel channel = channelEntry.getValue();
			try {
				if (channel.getKey() == null)
					replyQueue.join(channel.getName());
				else
					replyQueue.join(channel.getName(), channel.getKey());
			} catch (InvalidMessageParamException e) {
				logger.error( "Error creating IRC message",e);
			}
		}
	}

	/**
	 * Rejoins channels when kicked
	 */
	@Handler
	public void onKick(ReceiveKick event) {
		String target = event.getTarget();

		if (target.equals(bot.getNick())) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}

			String channelName = event.getChannel();

			// Add channel will automatically look up the key, if there was one,
			// since this channel has been added
			// previously.
			bot.addChannel(channelName);
		}
	}
}
