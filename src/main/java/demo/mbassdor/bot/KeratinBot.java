/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.bot.DelegateConnection.ConnectionRunnable;
import demo.mbassdor.bot.handlers.ConnectionPreamble;
import demo.mbassdor.bot.handlers.ManageChannels;
import demo.mbassdor.net.IrcConnection;
import demo.mbassdor.net.IrcConnection.SslMode;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.mbassdor.MBassadorWrapper;
import demo.mbassdor.net.message.InvalidMessageParamException;
import demo.mbassdor.net.message.IrcMessage;
import demo.mbassdor.net.message.SendMessage;
import demo.mbassdor.net.util.InvalidPortException;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午6:33:58
 * @version v 0.1
 */
public class KeratinBot {
	private static final Logger logger=LogManager.getLogger();
	private String user;

	private String nick;

	private String realName;

	private String serverAddress;

	private int serverPort;

	private SslMode sslMode;

	private Map<String, Channel> channels;

	private boolean connectionActive;

	private OutputQueue outputQueue;

	private IrcConnection connection;

	private DelegateConnection delegateConn;

	private MBassadorWrapper connectionBus;

	/**
	 * Make a KeratinBot with no fields predefined. Must have fields set before
	 * calling connect().
	 */
	public KeratinBot() {
		connectionActive = false;
	}

	/**
	 * Make a KeratinBot with all fields needed for a connection predefined.
	 * 
	 * @param user
	 *            IRC username
	 * @param nick
	 *            IRC nick. This is the unique ID of a client on IRC
	 * @param realName
	 *            IRC full/real name
	 * @param serverAddress
	 *            The address of the server to connect to when connect() is
	 *            called.
	 * @param serverPort
	 *            The port on the server to connect to when connect() is called.
	 * @param sslMode
	 *            {@link SslMode} value
	 * @param initialChannels
	 *            The channels to connect to initially, can be empty, but not
	 *            null.
	 */
	public KeratinBot(String user, String nick, String realName, String serverAddress, int serverPort, SslMode sslMode,
			Channel[] initialChannels) {
		this();
		setUser(user);
		setNick(nick);
		setRealName(realName);
		setServerAddress(serverAddress);
		setServerPort(serverPort);
		setSslMode(sslMode);
		for (Channel channel : initialChannels) {
			addChannel(channel.getName(), channel.getKey());
		}
	}

	/**
	 * Perform the connection. All fields must be defined before calling this,
	 * if using the blank constructor.
	 */
	public void connect() {
		IrcConnection conn = null;
		try {
			conn = new IrcConnection(serverAddress, serverPort, sslMode);
		} catch (UnknownHostException | InvalidPortException e) {
			logger.error( "Could not make IrcConnection",e);
		}

		connectionBus = conn.getEventBus();

		connectionBus.subscribe(new ConnectionPreamble(user, nick, realName));
		connectionBus.subscribe(new ManageChannels(this, channels));


		for (Channel channel : channels.values()) {
			connectionBus.subscribe(channel);
		}

		delegateConn = new DelegateConnection(serverAddress, serverPort, sslMode, user, nick + "-del", realName);

		connection = conn;
		conn.connect();
		outputQueue = conn.getOutputQueue();

		connectionActive = true;
	}

	/**
	 * End the connection.
	 */
	public void disconnect() {
		connectionActive = false;
		connection.disconnect();
		connection = null;
		outputQueue = null;
	}

	/**
	 * Get the current nick ID of the bot on the server.
	 * 
	 * @return Nick string
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Get the full/real name of the bot on the server.
	 * 
	 * @return Real name string, can contain spaces.
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Get the address of the server the bot is connected to.
	 * 
	 * @return Server address, can be a domain name or IP address.
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * Get the port of the server the bot is connected to.
	 * 
	 * @return Port number
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * Get the user name of the bot on the server.
	 * 
	 * @return User string
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Get the SSL socket use status/mode.
	 * 
	 * @return {@link SslMode} value
	 */
	public SslMode getSslMode() {
		return sslMode;
	}

	/**
	 * Set the stored nick value, and send an update message to the server if
	 * connected.
	 * 
	 * @param nick
	 *            A spaceless string, must start with an alphabetic character
	 */
	public void setNick(String nick) {
		if (connectionActive) {
			try {
				outputQueue.nick(nick);
			} catch (InvalidMessageParamException e) {
				logger.error("Error creating IRC message",e);
			}
		}
		this.nick = nick;
	}

	/**
	 * Set the full/real name of the bot on the server. No effect after
	 * connect() has been called.
	 * 
	 * @param realName
	 *            Real name string, can contain spaces.
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Set the address of the server the bot going to connect to. No effect
	 * after connect() has been called.
	 * 
	 * @param serverAddress
	 *            Server address, can be a domain name or IP address.
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/**
	 * Set the port of the server the bot going to connect to. No effect after
	 * connect() has been called.
	 * 
	 * @param serverAddress
	 *            Server port, has to be a valid port number.
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Set if and how SSL sockets are going to be used. No effect after
	 * connect() has been called.
	 * 
	 * @param sslMode
	 *            {@link SslMode} value
	 */
	public void setSslMode(SslMode sslMode) {
		this.sslMode = sslMode;
	}

	/**
	 * Set the user name the bot will use on the server. No effect after
	 * connect() has been called.
	 * 
	 * @param user
	 *            User string
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Get the set of current channels the bot is joined to.
	 * 
	 * @return Set of channel strings
	 */
	public Collection<Channel> getChannels() {
		return channels.values();
	}

	/**
	 * Get the Channel stored under a channel name.
	 * 
	 * @param name
	 *            The name of the channel to get. Includes the # prefix.
	 * @return The corresponding Channel, if it exists, else null
	 */
	public Channel getChannel(String name) {
		return channels.get(name);
	}

	/**
	 * Add a channel to the list of current channels the bot is joined to. Send
	 * an update message to the server immediately if connected. If a channel
	 * was added previously with a key, the key will be looked up automatically
	 * when being re-added.
	 * 
	 * @param name
	 *            Channel's name
	 */
	public void addChannel(String name) {
		addChannel(name, null);
	}

	/**
	 * Add a channel to the list of current channels the bot is joined to. Send
	 * an update message to the server immediately if connected. If a channel
	 * was added previously with a key, and the key given is null, the key will
	 * be looked up automatically when being re-added.
	 * 
	 * @param name
	 *            Channel's name
	 * @param key
	 *            Channel's key if it exists, otherwise null
	 */
	public void addChannel(String name, String key) {
		if (channels == null) {
			channels = new HashMap<String, Channel>();
		}

		if (key == null) {
			for (Entry<String, Channel> channelEntry : channels.entrySet()) {
				String channelName = channelEntry.getKey();
				Channel channel = channelEntry.getValue();
				String channelKey = channel.getKey();

				if (channelKey != null && channelName.equals(name)) {
					key = channelEntry.getKey();
					break;
				}
			}
		}

		addChannel(new Channel(name, key));
	}

	/**
	 * Add a channel to the list of current channels the bot is joined to. Send
	 * an update message to the server if connected.
	 * 
	 * @param channel
	 *            The channel to add
	 */
	public void addChannel(Channel channel) {
		if (channels == null) {
			channels = new HashMap<String, Channel>();
		}

		if (connectionActive) {
			try {
				if (channel.getKey() == null)
					outputQueue.join(channel.getName());
				else
					outputQueue.join(channel.getName(), channel.getKey());
			} catch (InvalidMessageParamException e) {
				logger.error( "Error creating IRC message",e);
			}

			connectionBus.subscribe(channel);
		}

		this.channels.put(channel.getName(), channel);
	}

	/**
	 * Remove a channel from the list of current channels the bot is joined to.
	 * Send an update message to the server if connected.
	 * 
	 * @param channel
	 *            Channel to part from
	 */
	public void remChannel(Channel channel) {
		remChannel(channel.getName());
	}

	/**
	 * Remove a channel from the list of current channels the bot is joined to.
	 * Send an update message to the server if connected.
	 * 
	 * @param name
	 *            Name of the channel to part from
	 */
	public void remChannel(String name) {
		if (connectionActive) {
			try {
				outputQueue.part(name);
			} catch (InvalidMessageParamException e) {
				logger.error("Error creating IRC message",e);
			}
		}
		channels.remove(name);
	}

	/**
	 * Op a single nick in a channel. Probably will fail if the bot is not an
	 * operator in the given channel.
	 * 
	 * @param channelName
	 *            The channel to op the nick in
	 * @param nick
	 *            The nick to op in the channel
	 */
	public void opNick(String channelName, String nick) {
		try {
			outputQueue.mode(channelName, "+o", nick);
		} catch (InvalidMessageParamException e) {
			logger.error("Error creating IRC message",e);
		}
	}

	/**
	 * Op one or more nicks in a single channel. Probably will fail if the bot
	 * is not an operator in the given channel.
	 * 
	 * @param channelName
	 *            The channel to op the nicks in
	 * @param nicks
	 *            The nicks to op in the channel
	 */
	public void opNicks(String channelName, Collection<String> nicks) {
		final int bufferSize = 4;

		// Op in groups of size bufferSize
		Iterator<String> iterator = nicks.iterator();
		while (iterator.hasNext()) {
			List<String> nickBuffer = new LinkedList<String>();
			StringBuilder modeBuffer = new StringBuilder();
			modeBuffer.append("+");

			for (int i = 0; iterator.hasNext() && i < bufferSize;) {
				String nick = iterator.next();
				nickBuffer.add(nick);
				modeBuffer.append("o");

				i++;
			}

			if (nickBuffer.size() > 0) {
				String[] nickArray = new String[nickBuffer.size()];
				nickBuffer.toArray(nickArray);
				try {
					outputQueue.mode(channelName, modeBuffer.toString(), nickArray);
				} catch (InvalidMessageParamException e) {
					logger.error("Error creating IRC message",e);
				}
			}
		}
	}

	/**
	 * Use a separate connection to send a PRIVMSG as a different nick to a
	 * certain channel.
	 * 
	 * @param nick
	 *            The nick to use
	 * @param channel
	 *            The name of the channel. The bot must be in that channel.
	 * @param text
	 *            The text to send. Send multiple PRIVMSGs by including \n
	 *            characters.
	 */
	public void sendPrivmsgAs(final String nick, final String channel, final String text) {
		if (channel.startsWith("#")) {
			sendPrivmsgAs(nick, getChannel(channel), text);
		} else {
			delegateConn.offer(new ConnectionRunnable() {
				@Override
				public void run(IrcConnection conn) {
					logger.trace("sendPrivmsgAs runnable running");

					OutputQueue outputQueue = conn.getOutputQueue();

					try {
						// Send as one block or not at all (an Exception will
						// stop offer from being called)
						List<IrcMessage> messageList = new LinkedList<IrcMessage>();

						messageList.add(SendMessage.nick(nick));
						messageList.add(SendMessage.privmsg(channel, text));

						for (IrcMessage message : messageList)
							outputQueue.offer(message);
					} catch (InvalidMessageParamException e) {
						logger.error("Error creating IRC messages",e);
					}
				}
			});
		}
	}

	/**
	 * Use a separate connection to send a PRIVMSG as a different nick to a
	 * certain channel.
	 * 
	 * @param nick
	 *            The nick to use
	 * @param channel
	 *            The channel to use
	 * @param text
	 *            The text to send. Send multiple PRIVMSGs by including \n
	 *            characters.
	 */
	public void sendPrivmsgAs(final String nick, final Channel channel, final String text) {
		delegateConn.offer(new ConnectionRunnable() {
			@Override
			public void run(IrcConnection conn) {
				logger.trace("sendPrivmsgAs runnable running");

				OutputQueue outputQueue = conn.getOutputQueue();

				try {
					// Send as one block or not at all (an Exception will stop
					// offer from being called)
					List<IrcMessage> messageList = new LinkedList<IrcMessage>();

					messageList.add(SendMessage.nick(nick));

					String channelName = channel.getName();
					if (channel.getKey() == null)
						messageList.add(SendMessage.join(channelName));
					else
						messageList.add(SendMessage.join(channelName, channel.getKey()));

					messageList.add(SendMessage.privmsg(channelName, text));
					messageList.add(SendMessage.part(channelName));

					for (IrcMessage message : messageList)
						outputQueue.offer(message);
				} catch (InvalidMessageParamException e) {
					logger.error("Error creating IRC messages",e);
				}
			}
		});
	}
}
