package com.path.atm.engine.connector.pipeline.handler;

import com.path.atm.engine.iso8583.MessageFactory;
import com.path.lib.log.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * This class house all the behavior and actions related to the idle event
 * 
 * <p> Handling idle event is based on the reactor type ( server / client ).
 * Server : when acting as sever the reactor will send an echo message over the channel
 * and in case the response wasn't delivered, the server will close the channel
 * and assumed that the switch is down.
 * 
 * Client : .....
 * 
 * @author MohammadAliMezzawi
 *
 */
public class IdleEventHandler extends ChannelInboundHandlerAdapter {

	/**
	 * Logger
	 */
	private Log logger = Log.getInstance();
	
	/**
	 * Message Factory
	 */
	private final MessageFactory messageFactory;
	
	/**
	 * @param messageFactory
	 */
	public IdleEventHandler(MessageFactory messageFactory) {
		this.messageFactory = messageFactory;
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		logger.debug("[IdleEventHandler] channelRead");
		super.channelRead(ctx, msg);
	}
	
	@Override
	/**
	 * @todo exception thrown at this level should be logged 
	 */
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE || e.state() == IdleState.ALL_IDLE) {
				
				// send a log off message
//				final AtmIsoMessage echoMessage = messageFactory.
//						createDraftMessage().asEcho();
				
//				Attribute<AtmEngineReactor> attr = ctx.channel()
//		        		.attr(AtmConnectorConstants.REACTOR_KEY);
//		        AtmEngineReactor reactor = attr.get();
//		        
//		        //@todo we should log it here
//		        reactor.getConnector()
//		        	.sendAsync(echoMessage);
//		        
				//ctx.write(echoMessage);
				//ctx.flush(); //.close();
			}
		}
	}
	
	
	/**
	 * Handle Parse exception thrown during handler invocation
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.debug("[IdleEventHandler] : exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}
}
