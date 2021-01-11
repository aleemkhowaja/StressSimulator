package com.path.atm.engine.connector.pipeline.handler;

import com.path.lib.log.Log;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * This class will handle any exception that occur in pipeline
 * 
 * <p> When an exception occur pipeline exception handler will check the type
 * of the exception and act accordingly
 * Several type of exception can occur during request handling and while moving from
 * one handler to another such as CorruptedFrameException, IsoParseException ...
 * Each one of those exception will be handled differently, some will be ignored and no technical
 * response will be sent.
 * 
 * @author MohammadAliMezzawi
 *
 */
public class PipelineExceptionHandler extends ChannelDuplexHandler  {
	
	/**
	 * Logger
	 */
	private Log logger = Log.getInstance();
	
	/**
	 * Handle Frame decoding exception.
	 * <p> Exception thrown at this level will be logged in file since
	 * the engine couldn't assign a request id to the message.
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		/**
		 * If an exception is caught at the LengthFrame that mean we
		 * don't have the MTI yet so our response will be a pure technical
		 * usually we will throw unable to parse.
		 */
		logger.debug("[PipelineExceptionHandler] : exceptionCaught");
		
		super.exceptionCaught(ctx, cause);
	}
	
	



}
