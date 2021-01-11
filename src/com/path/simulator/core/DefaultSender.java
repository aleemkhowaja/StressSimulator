package com.path.simulator.core;

import java.io.IOException;

import com.path.atm.engine.connector.Connector;
import com.path.atm.engine.connector.client.AtmClientConnector;
import com.path.atm.engine.connector.server.AtmServerConnector;
import com.path.atm.engine.iso8583.AtmIsoMessage;
import com.path.atm.engine.iso8583.MessageFactory;
import com.path.simulator.core.pool.BulkSender;
import com.path.simulator.core.vo.MessageVO;

public class DefaultSender {
	
	/**
	 * 
	 */
	private SimulatorConfiguration configuration;
	
	/**
	 * Netty Connector
	 */
	private Connector connector;

	/**
	 * Iso Message factory
	 */
	private MessageFactory messageFactory;
	
	
	/**
	 * @param configuration
	 * @param messageFactory 
	 */
	public DefaultSender(SimulatorConfiguration configuration, MessageFactory messageFactory) {
		this.configuration = configuration;
		this.messageFactory = messageFactory;
	}


	/**
	 * Start the connector
	 * @throws Exception
	 */
	public void start() throws Exception {
		
		// What the simulator should act as
		boolean actAsServer = !configuration.getConnectorType().equalsIgnoreCase("TS");
		
		// based on the is either to create a server connector or client connector
		 // create the appropriate connector
	    connector = actAsServer ? createServerConnector() : createClientConnector();
	    connector.start();
	}

	public void shutdown() throws Exception {
		connector.shutdown();
	}
	
	/**
	 * Send all messages
	 * @param messagesToSend
	 */
	public void sendMessage(MessageVO messageVO) {
		
		// Add this at this level to allow the message
		// to calculate bytes in write Data 
		messageVO.setFactory(messageFactory);
		connector.sendAsync(messageVO);
	}
	
	
	public BulkSender createBulkSender( int senderCount, int messagePerMinute ){
		return new BulkSender(senderCount,messagePerMinute);
	}
	
	
    /**
     * Create Atm Server connector using the loaded properties
     * 
     * <p>
     * Connector configurations are stored into 2 tables ( interface , channel )
     * for that we load the channel configuration and we populate the other from
     * the interface
     * 
     * @return
     * @throws IOException
     */
    private Connector createServerConnector() throws IOException
    {
    	// create Server connector
    	return new AtmServerConnector(configuration);
    }

    /**
     * Create Client connector
     * 
     * @return
     * @throws IOException
     */
    private Connector createClientConnector() throws IOException
    {
    	// create Server connector
    	return new AtmClientConnector(configuration);
    }
}
