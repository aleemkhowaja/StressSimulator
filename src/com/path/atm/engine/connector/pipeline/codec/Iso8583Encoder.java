package com.path.atm.engine.connector.pipeline.codec;

import com.path.atm.engine.iso8583.AtmIsoMessage;
import com.path.atm.engine.iso8583.MessageFactory;
import com.path.simulator.core.vo.MessageVO;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.ByteBuffer;

/**
 * Encodes message in a stream-like fashion from one message to an
 * {@link ByteBuf}.
 * 
 * @author MohammadAliMezzawi
 *
 */
public class Iso8583Encoder extends MessageToByteEncoder<Object> {
	/**
	 * Length of the Header
	 */
	private final int lengthHeaderLength;

	/**
	 * Message factory
	 */
	private final MessageFactory messageFactory;

	/**
	 * Constructor
	 * 
	 * @param messageFactory
	 * 
	 * @param lengthHeaderLength
	 */
	public Iso8583Encoder(MessageFactory messageFactory, int lengthHeaderLength) {
		this.lengthHeaderLength = lengthHeaderLength;
		this.messageFactory = messageFactory;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object isoMessage, ByteBuf out) {
		try {
			/**
			 * @todo fix this , we should use maybe the new AtmIsoMessage
			 *       writeToBuffer instead of using the writeData/writeToBuffer
			 */
			if(isoMessage instanceof MessageVO){
				ByteBuffer byteBuffer =  ((MessageVO)isoMessage).writeToBuffer(lengthHeaderLength);
				out.writeBytes(byteBuffer);
				return;
			}
				
			// logger.debug("[Iso8583Encoder] Start encoding");

			// @todo we may need to remove this part
			// in case message doesn't have a header
			if (lengthHeaderLength == 0) {
				byte[] bytes = ((AtmIsoMessage)isoMessage).writeData();
				out.writeBytes(bytes);
				return;
			}

			// write the data to the buffer with a length header
			ByteBuffer byteBuffer =  ((AtmIsoMessage)isoMessage).writeToBuffer(lengthHeaderLength);
			out.writeBytes(byteBuffer);

		} catch (Exception exception) {
			/**
			 * Wrap all exceptions at this level with IsoEncoderException so we
			 * can differentiate it while handling it at the
			 * {@link PipelineExceptionHandler}
			 */
			throw new RuntimeException(exception);
		}

	}

	/**
	 * Handle exception thrown during encoding
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

		// logger.debug("[Iso8583Encoder] : exceptionCaught");
		super.exceptionCaught(ctx, cause);
	}

	/**
	 * @return the messageFactory
	 */
	public MessageFactory getMessageFactory() {
		return messageFactory;
	}

	/**
	 * Returns a string representation of the object.
	 */
	public String toString() {
		return "Iso8583Decoder";
	}
}
