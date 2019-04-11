/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot;

import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.bot.handlers.ConnectionPreamble;
import demo.mbassdor.core.event.connection.IrcConnect;
import demo.mbassdor.net.IrcConnection;
import demo.mbassdor.net.IrcConnection.SslMode;
import demo.mbassdor.net.mbassdor.MBassadorWrapper;
import demo.mbassdor.net.util.InvalidPortException;
import net.engio.mbassy.listener.Handler;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 下午5:56:07
 * @version v 0.1
 */
public class DelegateConnection {
	private static final Logger logger = LogManager.getLogger();

	/**
	 * Basically a Runnable, but called with an IrcConnection instead of
	 * nothing.
	 */
	public static interface ConnectionRunnable {
		public void run(IrcConnection conn);
	}

	private IrcConnection connection;

	private final BlockingQueue<DelegateConnection.ConnectionRunnable> taskQueue;

	private final String address;

	private final int port;

	private final String user;

	private final String nick;

	private final String realName;

	private final SslMode sslMode;

	/**
	 * Create with given information. Connection will not be made until the
	 * first task is recieved.
	 * 
	 * @param address
	 *            Server address
	 * @param port
	 *            Server port
	 * @param sslMode
	 * @param user
	 *            User name
	 * @param nick
	 *            Initial nick
	 * @param realName
	 *            Real name
	 */
	public DelegateConnection(String address, int port, SslMode sslMode, String user, String nick, String realName) {
		taskQueue = new LinkedBlockingQueue<DelegateConnection.ConnectionRunnable>();

		this.address = address;
		this.port = port;
		this.sslMode = sslMode;
		this.user = user;
		this.nick = nick;
		this.realName = realName;

		new Thread() {
			@Override
			public void run() {
				Thread.currentThread().setName("DelegateConnection");
				logger.trace("Delegate connection thread running");

				while (!Thread.interrupted()) {
					try {
						ConnectionRunnable task = taskQueue.take();
						logger.trace("Got delegate connection task");
						try {
							task.run(getConnection());
						} catch (Exception e) {
							logger.error("Delegate task threw an exception", e);
						}
					} catch (InterruptedException e) {
						break;
					}
				}
				logger.trace("Run loop ends, exiting");

				if (connection != null) {
					logger.trace("Disconnecting delegate connection");
					connection.disconnect();
				}
			}
		}.start();
	}

	private IrcConnection getConnection() {
		if (connection == null) {
			logger.info("Starting delegate connection");
			try {
				IrcConnection conn = new IrcConnection(address, port, sslMode);

				MBassadorWrapper bus = conn.getEventBus();
				bus.subscribe(new ConnectionPreamble(user, nick, realName));

				// So up so we can wait for the connection to be established
				final Semaphore connectionEstablished = new Semaphore(0);
				bus.subscribe(new Object() {
					@Handler
					private void awaitConnection(IrcConnect event) {
						connectionEstablished.release();
					}
				});

				connection = conn;
				conn.connect();

				logger.trace("Waiting for the connection to be established");
				try {
					connectionEstablished.acquire();
				} catch (InterruptedException e) {
					logger.trace("Interrupted while waiting for the connection to be established", e);
				}
				logger.trace("Connection established");
			} catch (UnknownHostException | InvalidPortException e) {
				logger.error("Could not make IrcConnection", e);
			}
		}
		return connection;
	}

	/**
	 * @see {@link LinkedBlockingQueue#offer(Object)}
	 */
	public boolean offer(DelegateConnection.ConnectionRunnable task) {
		return taskQueue.offer(task);
	}

	/**
	 * @see {@link LinkedBlockingQueue#offer(Object, long, TimeUnit)}
	 */
	public void offer(DelegateConnection.ConnectionRunnable task, long timeout, TimeUnit unit)
			throws InterruptedException {
		taskQueue.offer(task, timeout, unit);
	}
}
