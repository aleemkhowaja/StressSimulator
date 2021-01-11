package com.path.atm.engine.connector.pipeline.codec;

import java.nio.ByteOrder;

import com.path.atm.engine.iso8583.IsoUtil;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.lib.log.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * This class is written to override some LengthFieldBasedFrameDecoder functionality
 * such as handling ASCII length code.
 * 
 * @author MohammadAliMezzawi
 *
 */
public class IsoLengthFieldBasedFrameDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * Logger
	 */
	private Log logger = Log.getInstance();
	
	/**
	 * Hold length type
	 */
	protected String lengthType;
	
	/**
	 * @param byteOrder
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 * @param failFast
	 */
	public IsoLengthFieldBasedFrameDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}
	
	
	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 */
	public IsoLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	
	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthType
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 */
	public IsoLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			String lengthType, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		this.lengthType = lengthType;
	}
	
	
	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 * @param failFast
	 */
	public IsoLengthFieldBasedFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}
	
	
	/**
     * Decodes the specified region of the buffer into an unadjusted frame length.  The default implementation is
     * capable of decoding the specified region into an unsigned 8/16/24/32/64 bit integer.  Override this method to
     * decode the length field encoded differently.  Note that this method must not modify the state of the specified
     * buffer (e.g. {@code readerIndex}, {@code writerIndex}, and the content of the buffer.)
     *
     * @throws DecoderException if failed to decode the specified region
     */
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
    	
		// get Length byte
		buf = buf.order(order);
		byte[] lengthBytes = new byte[length];
		buf.getBytes(offset, lengthBytes);
		String lengthStr = new String(lengthBytes,AtmEngineConstants.CHAR_ENCODING);
		
		// calculate length based on the length type
		switch( lengthType ) {
			case AtmEngineConstants.LENGTH_BASE10:
				
				lengthStr = lengthStr.replaceFirst("^0+(?!$)", "");
	            return Long.valueOf(lengthStr);
	            
			case AtmEngineConstants.LENGTH_BASE256 :
				
				/**
				 * @todo test issue 
				 * when number is negative and the buffer isn't reading anymore
				 */
				int lengthValue = IsoUtil.convertLenFromAscii(lengthStr);
				logger.debug("Frame Length : Ascii : "+ lengthStr + " | Integer : "+ lengthValue
				+ " | nb of Bytes " + lengthStr.getBytes(AtmEngineConstants.CHAR_ENCODING).length);
				return lengthValue;
				
			default :
				return super.getUnadjustedFrameLength( buf,  offset, length, order);
				
		}
    }

    
    /**
     * Create a frame out of the {@link ByteBuf} and return it.
     *
     * @param   ctx             the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param   in              the {@link ByteBuf} from which to read data
     * @return  frame           the {@link ByteBuf} which represent the frame or {@code null} if no frame could
     *                          be created.
     */
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
    	
	    try {
	    	/**
	    	 * prepare all logging needed info
	    	 */
	    	return super.decode( ctx, in);
	    }catch(Exception exception) {
	    	// wrap any exception with IsoFrameDecoder exception
	    	throw new RuntimeException("Failed while IsoFrameDecoderException",exception);
	    }
    }

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
		logger.debug("[IsoLengthFieldBasedFrameDecoder] : exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}
}
