/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import demo.mbassdor.net.handler.BusErrorHandler;
import demo.mbassdor.net.handler.DeadMessageHandler;
import demo.mbassdor.net.handler.ServerPingHandler;
import demo.mbassdor.net.io.ConnectionThread;
import demo.mbassdor.net.io.OutputQueue;
import demo.mbassdor.net.mbassdor.MBassadorWrapper;
import demo.mbassdor.net.util.InvalidPortException;
import demo.mbassdor.net.util.TrustAllTrustManager;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:14:34
 * @version v 0.1
 */
public class IrcConnection {
	private static final Logger logger=LogManager.getLogger();
	private final SocketFactory socketFactory;

	private InetSocketAddress endpoint;

	private MBassadorWrapper bus;

	private OutputQueue outputQueue;

	private ConnectionThread connectionThread;

	/**
	 * Defines the different use states of SSL. See the javadoc of the members
	 * for more information.
	 */
	public enum SslMode {
		/**
		 * Do not use SSL
		 */
		OFF,

		/**
		 * Enable full SSL
		 */
		ON,

		/**
		 * Enable SSL without host verification
		 */
		ON_NOHOST
	}

	/**
	 * Create an IRC connection with a String address and SSL disabled. The
	 * connection is not active until connect is called.
	 * 
	 * @param address
	 *            A string representing the address to connect to
	 * @param port
	 *            The port number within the valid range to connect to
	 * @throws UnknownHostException
	 *             If no IP address for the host could be found, or if a
	 *             scope_id was specified for a global IPv6 address.
	 * @throws InvalidPortException
	 *             If the port parameter is outside the specified range of valid
	 *             port values.
	 */
	public IrcConnection(String address, int port) throws UnknownHostException, InvalidPortException {
		this(InetAddress.getByName(address), port, SslMode.OFF);
	}

	/**
	 * Create an IRC connection with SSL disabled. The connection is not active
	 * until connect is called.
	 * 
	 * @param address
	 *            A InetAddress representing the address to connect to
	 * @param port
	 *            The port number within the valid range to connect to
	 * @throws InvalidPortException
	 *             If the port parameter is outside the specified range of valid
	 *             port values.
	 */
	public IrcConnection(InetAddress address, int port) throws InvalidPortException {
		this(address, port, SslMode.OFF);
	}

	/**
	 * Create an IRC connection with a String address. The connection is not
	 * active until connect is called.
	 * 
	 * @param address
	 *            A string representing the address to connect to
	 * @param port
	 *            The port number within the valid range to connect to
	 * @throws UnknownHostException
	 *             If no IP address for the host could be found, or if a
	 *             scope_id was specified for a global IPv6 address.
	 * @throws InvalidPortException
	 *             If the port parameter is outside the specified range of valid
	 *             port values.
	 */
	public IrcConnection(String address, int port, SslMode ssl) throws UnknownHostException, InvalidPortException {
		this(InetAddress.getByName(address), port, ssl);
	}

	/**
	 * Create an IRC connection. The connection is not active until connect is
	 * called.
	 * 
	 * @param address
	 *            A InetAddress representing the address to connect to
	 * @param port
	 *            The port number within the valid range to connect to
	 * @throws InvalidPortException
	 *             If the port parameter is outside the specified range of valid
	 *             port values.
	 */
	public IrcConnection(InetAddress address, int port, SslMode ssl) throws InvalidPortException {
		 logger.trace("IrcConnection instantiation");
		try {
			endpoint = new InetSocketAddress(address, port);
		} catch (IllegalArgumentException e) {
			throw new InvalidPortException(e);
		}

		// Bus has to be made in this class's constructor because we want to be
		// able to subscribe stuff to the bus
		// before connecting.
		IBusConfiguration busConf = new BusConfiguration().addPublicationErrorHandler(new BusErrorHandler()).addFeature(Feature.SyncPubSub.Default())
	            .addFeature(Feature.AsynchronousHandlerInvocation.Default())
	            .addFeature(Feature.AsynchronousMessageDispatch.Default()).setProperty(IBusConfiguration.Properties.BusId, "global bus");
		// busConf.setSubscriptionFactory( new TimeoutSubscriptionFactory() );
		bus = new MBassadorWrapper(busConf);

		if (SslMode.ON.equals(ssl))
			socketFactory = SSLSocketFactory.getDefault();
		else if (SslMode.ON_NOHOST.equals(ssl))
			socketFactory = TrustAllTrustManager.getSSLSocketFactory();
		else
			socketFactory = SocketFactory.getDefault();
	}

	/**
	 * Get the IrcEvent bus for the connection. May be called immediately.
	 * 
	 * @return the central MBassador bus
	 */
	public MBassadorWrapper getEventBus() {
		return bus;
	}

	/**
	 * Get the output Queue. May be called after calling {@link #connect()}.
	 */
	public OutputQueue getOutputQueue() {
		if (outputQueue != null)
			return outputQueue;
		else
			throw new IllegalStateException("OutputQueue cannot be requested before connection is made");
	}

	/**
	 * Activate the connection. Will return immediately after starting the
	 * connection thread.
	 */
	public void connect() {
		logger.info("Connecting");

		 logger.trace("Subscribing to event bus");
		bus.subscribe(new ServerPingHandler());
		bus.subscribe(new DeadMessageHandler());

		logger.trace("Creating/starting connection thread");
	connectionThread = new ConnectionThread(bus, endpoint, socketFactory);
   	outputQueue = connectionThread.getOutputQueue();
//		connectionThread.start();

		logger.trace("Done set up");
	}

	/**
	 * Deactivate the connection.
	 */
	public void disconnect() {
		// Logger.info("Disconnecting");
		//
		// Logger.trace("Stopping worker thread");
		connectionThread.interrupt();
		try {
			connectionThread.join();
		} catch (InterruptedException e) {
		}

		// Logger.trace("Shutting down event bus");
		bus.shutdown();
		bus = null;

		// Logger.trace("Done shut down");
	}
}
