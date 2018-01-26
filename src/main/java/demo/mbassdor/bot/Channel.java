/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.bot.User.PrivLevel;
import demo.mbassdor.core.event.message.recieve.ReceiveChannelMode;
import demo.mbassdor.core.event.message.recieve.ReceiveJoin;
import demo.mbassdor.core.event.message.recieve.ReceivePart;
import demo.mbassdor.core.event.message.recieve.ReceiveReply;
import demo.mbassdor.net.message.IrcMessage;
import net.engio.mbassy.listener.Handler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午6:45:08
 * @version v 0.1
 */
public class Channel {
	private static final Logger logger = LogManager.getLogger();
	private static final String OP_PREFIX = "@";

	private static final String CODE_RPL_NAMREPLY = "353";

	private final String name;

	private final String key;

	/**
	 * Map with key of IRC nickname to corresponding User
	 */
	private final ConcurrentHashMap<String, User> nicks;

	private final Object nicksMutex = new Object();

	/**
	 * @param channelName
	 *            The channel's name. Including the #. Cannot be null.
	 * @param channelKey
	 *            The channel's key. May be null if there is no key.
	 */
	public Channel(String channelName, String channelKey) {
		this.name = channelName;
		this.key = channelKey;
		nicks = new ConcurrentHashMap<String, User>();
	}

	/**
	 * @return The channel's name. Including the #. Cannot be null.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The channel's key. May be null if there is no key.
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get all of the nicks in the channel, regardless of privilege level.
	 * Requires an active connection.
	 * 
	 * @return The NAMES list of this channel, or null on error.
	 */
	public List<String> getNicks() {
		LinkedList<String> nickList = new LinkedList<String>();

		synchronized (nicksMutex) {
			for (User user : nicks.values()) {
				nickList.add(user.getNick());
			}
		}

		return nickList;
	}

	/**
	 * Get the nicks of all regular users in the channel. Requires an active
	 * connection.
	 * 
	 * @return The filtered NAMES list of this channel, or null on error.
	 */
	public List<String> getRegularNicks() {
		LinkedList<String> filteredList = new LinkedList<String>();

		synchronized (nicksMutex) {
			for (User user : nicks.values()) {
				if (user.getPrivLevel().equals(PrivLevel.Regular))
					filteredList.add(user.getNick());
			}
		}

		return filteredList;
	}

	/**
	 * Get the nicks of all operator users in the channel. Requires an active
	 * connection.
	 * 
	 * @return The filtered NAMES list of this channel, or null on error.
	 */
	public List<String> getOperatorNicks() {
		LinkedList<String> filteredList = new LinkedList<String>();

		synchronized (nicksMutex) {
			for (User user : nicks.values()) {
				if (user.getPrivLevel().equals(PrivLevel.Op))
					filteredList.add(user.getNick());
			}
		}

		return filteredList;
	}

	/**
	 * @return true iff the nick is in the channel and is an op in the channel
	 */
	public boolean isOp(String nick) {
		synchronized (nicksMutex) {
			if (nicks.containsKey(nick)) {
				User user = nicks.get(nick);
				if (user.getPrivLevel().equals(PrivLevel.Op))
					return true;
			}

			return false;
		}
	}

	/**
	 * @return true iff the nick is in the channel and is a regular user
	 *         (non-op) in the channel
	 */
	public boolean isRegular(String nick) {
		synchronized (nicksMutex) {
			if (nicks.containsKey(nick)) {
				User user = nicks.get(nick);
				if (user.getPrivLevel().equals(PrivLevel.Regular))
					return true;
			}

			return false;
		}
	}

	@Override
	public String toString() {
		synchronized (nicksMutex) {
			return "Channel [name=" + name + ", key=" + key + ", nicks=" + nicks + "]";
		}
	}

	private void setNickAs(String nick, PrivLevel privLevel) {
		synchronized (nicksMutex) {
			if (nicks.containsKey(nick)) {
				User user = nicks.get(nick);
				if (user.getPrivLevel().equals(privLevel)) {
					logger.trace(getName() + " Nick already " + privLevel + ", doing nothing: " + nick);
				} else {
					logger.trace(getName() + " Changing nick privLevel to " + privLevel + ": " + nick);
					user.setPrivLevel(privLevel);
				}
			} else {
				logger.trace(getName() + " New user, adding as " + privLevel + ": " + nick);
				nicks.put(nick, new User(nick, privLevel));
			}
		}
	}

	@Handler
	private void namesListing(ReceiveReply event) {
		IrcMessage msg = event.getMessage();
		String[] params = msg.getParams();
		String replyNum = msg.getCommand();

		if (CODE_RPL_NAMREPLY.equals(replyNum)) {
			String channelName = params[2];
			if (name.equals(channelName)) {
				String nicksBlob = params[3];
				if (nicksBlob.startsWith(":"))
					nicksBlob = nicksBlob.substring(1);

				String[] nicksArray = nicksBlob.split(" ");

				synchronized (nicksMutex) {
					logger.trace("Processing names reply for channel: " + getName());
					for (String nick : nicksArray) {
						// Treating the NAMES listing as authoritative, may
						// override existing values
						if (nick.startsWith(OP_PREFIX)) {
							nick = nick.substring(1);
							setNickAs(nick, PrivLevel.Op);
						} else {
							setNickAs(nick, PrivLevel.Regular);
						}
					}
				}
			}
		}
	}

	@Handler
	private void updateOnJoin(ReceiveJoin event) {
		String nick = event.getJoiner();

		// Only do something if the mode change is for this channel
		if (name.equals(event.getChannel())) {
			synchronized (nicksMutex) {
				logger.trace("Processing join in channel: " + getName());
				// Treating JOINs as non-authoritative, may not override
				// existing values
				if (!nicks.containsKey(nick)) {
					setNickAs(nick, PrivLevel.Regular);
				}
			}
		}
	}

	@Handler
	private void updateOnPart(ReceivePart event) {
		String nick = event.getParter();

		// Only do something if the mode change is for this channel
		if (name.equals(event.getChannel())) {
			synchronized (nicksMutex) {
				logger.trace("Processing part in channel: " + getName());
				logger.trace("Removing nick: " + nick);
				if (nicks.remove(nick) == null) {
					logger.trace("Nick to be removed was not in the nicks collection: " + nick);
				}
			}
		}
	}

	// TODO handle nick changes (transfer user data to new nick)

	@Handler
	private void updateOnMode(ReceiveChannelMode event) {
		String flags = event.getFlags();

		// Only do something if the mode change is for this channel
		if (name.equals(event.getTarget())) {
			boolean op = flags.startsWith("+o");
			boolean deop = flags.startsWith("-o");

			if (op || deop) {
				List<String> affectedNicks = event.getFlagParams();

				synchronized (nicksMutex) {
					logger.trace("Processing mode update in channel: " + getName());
					for (String nick : affectedNicks) {
						if (nick.startsWith(":"))
							nick = nick.substring(1);

						if (nicks.containsKey(nick)) {
							if (op) {
								setNickAs(nick, PrivLevel.Op);
							} else if (deop) {
								setNickAs(nick, PrivLevel.Regular);
							}
						}
					}
				}
			}
		}
	}
}
