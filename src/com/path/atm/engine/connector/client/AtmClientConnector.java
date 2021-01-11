package com.path.atm.engine.connector.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.path.atm.engine.connector.AbstractConnector;
import com.path.atm.engine.connector.pipeline.handler.Iso8583ChannelInitializer;
import com.path.atm.engine.connector.pipeline.handler.Iso8583ClientChannelInitializer;
import com.path.atm.engine.connector.pipeline.listener.ReconnectOnCloseListener;
import com.path.simulator.core.SimulatorConfiguration;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author MohammadAliMezzawi
 */
public class AtmClientConnector extends AbstractConnector<Bootstrap, SimulatorConfiguration> {
	/**
	 * 
	 */
	private ReconnectOnCloseListener reconnectOnCloseListener;

	/**
	 * @param config
	 * @param messageFactory
	 */
	public AtmClientConnector(SimulatorConfiguration config) {
		super(config);
	}

	/**
	 * @approved Create a server bootstrap 1- Set Boss and worker group event
	 *           loop 2- Set needed channel pipeline
	 */
	protected Bootstrap createBootstrap() {

		final Bootstrap bootstrap = new Bootstrap();

		/**
		 * - Set boss and worker group - Class to instantiate Channel - set
		 * ChildHandler
		 */
		bootstrap.group(getBossEventLoopGroup()).channel(NioSocketChannel.class).remoteAddress(getSocketAddress())
				.handler(initChannelInitialize());

		/**
		 * @todo 1- do we need to hook configuration 2- set more network layer
		 *       options
		 */
		configureBootstrap(bootstrap);
		bootstrap.validate();

		reconnectOnCloseListener = new ReconnectOnCloseListener(this,
				// getConfiguration().getReconnectInterval(),
				2000, getBossEventLoopGroup());

		return bootstrap;
	}

	/**
	 * @approved This method will allow to configure Client bootstrap
	 * @param bootstrap
	 */
	protected void configureBootstrap(Bootstrap bootstrap) {
		super.configureBootstrap(bootstrap);

		// @todo add this as property
		final boolean tcpNoDelay = Boolean.parseBoolean(System.getProperty("nfs.rpc.tcp.nodelay", "true"));

		bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, tcpNoDelay);
	}

	/**
	 * Initialize the channel initialized
	 * 
	 * 1- create Iso8583ChannelInitialize 2- Set configuration/worker group 3-
	 * Add custom channel handlers
	 * 
	 * @return {@link Iso8583ChannelInitializer}
	 */
	protected Iso8583ChannelInitializer<Channel, AtmClientConnector> initChannelInitialize() {

		Iso8583ClientChannelInitializer channelInitializer = new Iso8583ClientChannelInitializer(this);

		channelInitializer.setWorkerGroup(getBossEventLoopGroup());

		if (null != getChannelHandlers()) {
			ChannelHandler[] handlers = (ChannelHandler[]) getChannelHandlers().toArray();
			channelInitializer.setCustomChannelHandlers(handlers);
		}

		return channelInitializer;

	}

	/**
	 * @approved Check if the Client connector is connected.
	 * @return
	 */
	public boolean isStarted() {
		final Channel channel = getChannel();
		return channel != null && channel.isActive();
	}

	/**
	 * Start the Client.
	 * 
	 * <p>
	 * The client will try to connect to the server synch if it fail the engine
	 * will shutdown safely.
	 * 
	 * <p>
	 * After connecting the client will try at max 3 times to reconnect to the
	 * server after that the reactor will be turned off.
	 * 
	 * @throws InterruptedException
	 */
	public void start() throws InterruptedException {
		// init the connector
		super.start();

		// connect to the server Synch
		connect();
	}

	/**
	 * @approved Shutdown the server SCRAM/Safe operation
	 * 
	 * @throws Exception
	 */
	public void shutdown() {
		final ChannelFuture future = disconnectAsync();

		if (future != null) {
			future.syncUninterruptibly();
		}

		super.shutdown();
	}

	/**
	 * Connects synchronously to remote address.
	 *
	 * @return Returns the {@link ChannelFuture} which will be notified when
	 *         this channel is closed.
	 * @throws InterruptedException
	 *             if connection process was interrupted
	 * @see #setSocketAddress(SocketAddress)
	 */
	public ChannelFuture connect() throws InterruptedException {
		final Channel channel = connectAsync().sync().channel();
		assert (channel != null) : "Channel must be set";
		setChannel(channel);
		return channel.closeFuture();
	}

	/**
	 * Connect synchronously to specified host and port.
	 *
	 * @param host
	 *            A server host to connect to
	 * @param port
	 *            A server port to connect to
	 * @return {@link ChannelFuture} which will be notified when connection is
	 *         established.
	 * @throws InterruptedException
	 *             if connection process was interrupted
	 */
	public ChannelFuture connect(String host, int port) throws InterruptedException {
		return connect(new InetSocketAddress(host, port));
	}

	/**
	 * Connects synchronously to specified remote address.
	 *
	 * @param serverAddress
	 *            A server address to connect to
	 * @return {@link ChannelFuture} which will be notified when connection is
	 *         established.
	 * @throws InterruptedException
	 *             if connection process was interrupted
	 */
	public ChannelFuture connect(SocketAddress serverAddress) throws InterruptedException {
		setSocketAddress(serverAddress);
		return connect().sync();
	}
	
	/**
	 * asynchronously Connect with auto reconnect allowed
	 * @return
	 */
	public ChannelFuture connectAsync() {
		return connectAsync(true);
	}

	/**
	 * Connects asynchronously to remote address.
	 *
	 * @return Returns the {@link ChannelFuture} which will be notified when
	 *         this channel is active.
	 */
	public ChannelFuture connectAsync(boolean autoReconnect) {
		// logger.debug("Connecting to {}", getSocketAddress());
		final Bootstrap bootstrap = getBootstrap();
		if (reconnectOnCloseListener != null) {
			reconnectOnCloseListener.requestReconnect();
		}

		final ChannelFuture connectFuture = bootstrap.connect();
		connectFuture.addListener(connFuture -> {
			if (!connectFuture.isSuccess()) {
				if(autoReconnect)
					reconnectOnCloseListener.scheduleReconnect();
				return;
			}

			Channel channel = connectFuture.channel();
			// logger.debug("Client is connected to {}",
			// channel.remoteAddress());
			setChannel(channel);

			// reset the essay counter
			if (reconnectOnCloseListener != null) {
				reconnectOnCloseListener.resetCounter();
			}
			channel.closeFuture().addListener(reconnectOnCloseListener);
		});

		return connectFuture;
	}

	/**
	 * @return
	 */
	protected ChannelFuture disconnectAsync() {
		if (null != reconnectOnCloseListener) {
			reconnectOnCloseListener.requestDisconnect();
		}

		final Channel channel = getChannel();
		if (channel != null) {
			final SocketAddress socketAddress = getSocketAddress();
			// logger.info("Closing connection to {}", socketAddress);
			return channel.close();
		}

		return null;
	}

	/**
	 * @throws InterruptedException
	 */
	@SuppressWarnings("unused")
	protected void disconnect() throws InterruptedException {
		final ChannelFuture disconnectFuture = disconnectAsync();
		if (disconnectFuture != null) {
			disconnectFuture.await();
		}
	}

}
