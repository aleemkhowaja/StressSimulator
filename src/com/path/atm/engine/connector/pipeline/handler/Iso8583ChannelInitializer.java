package com.path.atm.engine.connector.pipeline.handler;

import com.path.atm.engine.connector.AbstractConnector;
import com.path.atm.engine.connector.pipeline.codec.Iso8583Decoder;
import com.path.atm.engine.connector.pipeline.codec.Iso8583Encoder;
import com.path.atm.engine.connector.pipeline.codec.IsoLengthFieldBasedFrameDecoder;
import com.path.atm.engine.iso8583.MessageFactory;

import com.path.lib.log.Log;
import com.path.simulator.core.SimulatorConfiguration;
import com.path.simulator.core.StressSimulator;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;

/**
 * @author MohammadAliMezzawi
 *
 * @param <C>
 * @param <Config>
 */
@SuppressWarnings("rawtypes")
public class Iso8583ChannelInitializer<C extends Channel, Connector extends AbstractConnector>
	extends ChannelInitializer<C>
{


    /**
     * Hold reference to the atm connector
     */
    private final Connector connector;

    /**
     * Hold Custom handlers
     */
    protected ChannelHandler[] customChannelHandlers;

    /**
     * Holder worker event group
     */
    private EventLoopGroup workerGroup;

    /**
     * Create a channel and prepare the pipeline
     * 
     * @param isoMessageFactory
     */
    public Iso8583ChannelInitializer(Connector connector)
    {
	this.connector = connector;
    }

    /**
     * Initialize the Channel
     * <p>
     * This method will be invoked when creating a new channel and will add the
     * given handlers to the channel pipeline
     */
    protected void initChannel(C ch) throws Exception
    {
	/**
	 * @todo : remove those notes Each channel has its own pipeline and it
	 *       is created automatically when a new channel is created where we
	 *       can have one or more ChannelHandlers to receive I/O events
	 *       (e.g. read) and to request I/O operations (e.g. write and
	 *       close).
	 */
	final ChannelPipeline pipeline = ch.pipeline();

	/**
	 * @todo test long msg and exception if it close the connection since we
	 *       encounter such case
	 */

	pipeline.addLast("lengthFieldFameDecoder", createFrameDecoder());

	/**
	 * Inbound handler
	 */

	/**
	 * decoder handler main functionality is just to return an iso message
	 * so other handler can differentiate between netowkr message and iso
	 * message for now we will keep it but we should consider removing it
	 */
	pipeline.addLast("iso8583Decoder", createIso8583Decoder());


	/**
	 * Outound handler
	 */
	pipeline.addLast("iso8583Encoder", createIso8583Encoder());

	/**
	 * Duplex handler
	 */

	// set idle state behavior
	if(connector.getConfiguration().getIdleTimeout() > 0)
	{
	    pipeline.addLast("idleState", new IsoIdleStateHandler(0, 0, connector.getConfiguration().getIdleTimeout()));
	    pipeline.addLast("idleEventHandler", new IdleEventHandler(retunMessageFactory()));
	}

	// @todo fix this
	if(customChannelHandlers != null)
	    pipeline.addLast(workerGroup, customChannelHandlers);

    }

    /**
     * <pre>
     * lengthFieldOffset   = 1 (= the length of HDR1)
     * lengthFieldLength   = 2
     * <b>lengthAdjustment</b>    = <b>1</b> (= the length of HDR2)
     * <b>initialBytesToStrip</b> = <b>3</b> (= the length of HDR1 + LEN)
     *
     * BEFORE DECODE (16 bytes)                       AFTER DECODE (13 bytes)
     * +------+--------+------+----------------+      +------+----------------+
     * | HDR1 | Length | HDR2 | Actual Content |----->| HDR2 | Actual Content |
     * | 0xCA | 0x000C | 0xFE | "HELLO, WORLD" |      | 0xFE | "HELLO, WORLD" |
     * +------+--------+------+----------------+      +------+----------------+
     * </pre>
     * 
     * Create IsoLengthFieldBasedFrameDecoder Handler
     * 
     * @return
     */
    private IsoLengthFieldBasedFrameDecoder createFrameDecoder()
    {
    	SimulatorConfiguration configuration = connector.getConfiguration();

	int lengthFieldLength = configuration.getLengthFieldLength();
	/**
	 * Include header length should always be Y by default
	 */
	int adjustement = !configuration.isIncludeHeaderLength() && configuration.getHeaderLength() > 0
		? configuration.getHeaderLength()
		: 0;

	// if length of length field is included in length we should remove it
	adjustement += configuration.isIncludeLength() ? -lengthFieldLength : 0;

	/**
	 * Currently we support two types: 1 - Base 256 2 - Base 10
	 */
	String lengthType = String.valueOf(configuration.getLengthType());

	/*
	 * pipeline.addLast("lengthFieldFameDecoder", new
	 * LengthFieldBasedFrameDecoder( configuration.getMaxFrameLength(),
	 * configuration.getFrameLengthFieldOffset(), lengthFieldLength,
	 * adjustement , lengthFieldLength));
	 */
	// old behavior
	// int initialBytesToStrip = configuration.getFrameLengthFieldOffset() +
	// lengthFieldLength;
	int initialBytesToStrip = 0;
	IsoLengthFieldBasedFrameDecoder frameDecoder = new IsoLengthFieldBasedFrameDecoder(
		configuration.getMaxFrameLength(), configuration.getFrameLengthFieldOffset(), lengthFieldLength,
		lengthType, adjustement, initialBytesToStrip);


	return frameDecoder;

    }



    /**
	 * @param configuration
	 * @return
	 */
	protected Iso8583Encoder createIso8583Encoder() {
		SimulatorConfiguration configuration = connector.getConfiguration();
		Iso8583Encoder isoEncoder = new Iso8583Encoder(retunMessageFactory(),
				configuration.getLengthFieldLength());

		return isoEncoder;
	}

	/**
	 * Create Iso decoder and log the action
	 * 
	 * @return
	 */
	protected Iso8583Decoder createIso8583Decoder() {
		/**
		 * Since extracting is moved to the decoder we should provide the
		 * decoder with the value of initial byte to strips
		 */
		SimulatorConfiguration configuration = connector.getConfiguration();
		int lengthFieldLength = configuration.getLengthFieldLength();
		int initialBytesToStrip = configuration.getFrameLengthFieldOffset() + lengthFieldLength;
		Iso8583Decoder isoDecoder = new Iso8583Decoder(retunMessageFactory(), initialBytesToStrip);

		return isoDecoder;
	}

    /**
     * Create Parse Exception handler
     * 
     * @return
     */
    protected ChannelHandler createParseExceptionHandler()
    {
	return new ParseExceptionHandler(retunMessageFactory(), true);
    }
    
    
	/**
	 * @return the messageFactory
	 */
	protected MessageFactory retunMessageFactory() {
		return StressSimulator.getInstance().getMessageFactory();
	}

    /**
     * set custom Channel handlers
     * 
     * @param handler
     */
    public void setCustomChannelHandlers(ChannelHandler... handlers)
    {
	this.customChannelHandlers = handlers;
    }

    /**
     * Set worker group
     * 
     * @param workerEventLoopGroup
     */
    public void setWorkerGroup(EventLoopGroup workerEventLoopGroup)
    {
	this.workerGroup = workerEventLoopGroup;
    }

    /**
     * @return the connector
     */
    public Connector getConnector()
    {
	return connector;
    }

}
