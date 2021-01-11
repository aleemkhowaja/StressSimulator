package com.path.simulator.core;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.xml.parsers.ParserConfigurationException;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.path.atm.engine.iso8583.IsoConfigParser;
import com.path.atm.engine.iso8583.MessageFactory;
import com.path.atm.engine.util.AtmEngineConstants;
import com.path.atm.vo.engine.AtmInterfaceCO;
import com.path.bo.common.ConstantsCommon;
import com.path.simulator.gui.main.SwitchFrame;
import com.path.simulator.util.StringUtil;

/**
 * @author MohammadAliMezzawi
 *
 */
public class StressSimulator {

	/**
	 * StressSimulator instance
	 */
	private volatile static StressSimulator instance;
	
	/**
	 * Configuration
	 */
	private SimulatorConfiguration config = new SimulatorConfiguration();
	
	/**
	 * Monitor object
	 */
	private static Object monitor = new Object();

	/**
	 * 
	 */
	private SwitchFrame mainWindow;
	
	/**
	 * 
	 */
	private DefaultSender sender;

	/**
	 * 
	 */
	private MessageFactory messageFactory;

	/**
	 * Return StressSimulator instance
	 */
	public static StressSimulator getInstance() {

		if (instance == null) {
			synchronized (monitor) {
				if (instance == null)
					instance = new StressSimulator();
			}
		}

		return instance;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					// create the main application
					StressSimulator.getInstance().launch();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void launch() throws ParserConfigurationException, IOException {
		
		// load configuration
		loadConfiguration();
		
		// Apply the flag gui
		FlatLightLaf.install(new FlatIntelliJLaf());
		UIManager.put("TextField.inactiveBackground", new ColorUIResource(new Color(255, 255, 255)));

		// launch the switch frame ( main frame )
		StressSimulator.getInstance().setMainWindow(new SwitchFrame(config)).setVisible(true);
		
	}

	
	/**
	 * Called when we connect to the netty connector
	 * @param configuration
	 * @throws Exception
	 */
	public void start(SimulatorConfiguration configuration) throws Exception {

		/**
		 * Prepare interface configuration
		 */
		AtmInterfaceCO interfaceCO = new AtmInterfaceCO();
		interfaceCO.setProtocolIdentification(configuration.getProtocolIdentification());
		interfaceCO.setMsgLength(new BigDecimal(configuration.getLengthFieldLength()));
		interfaceCO.setHeaderData(configuration.getHeader());
		interfaceCO.setHeaderLength(new BigDecimal(configuration.getHeader().length()));
		interfaceCO.setBitmapType(String.valueOf(configuration.getBitmapType()));
		interfaceCO.setLengthType(String.valueOf(configuration.getLengthType()));
		
		interfaceCO.setIncludeLength(configuration.isIncludeLength()?BigDecimal.ONE: BigDecimal.ZERO);
		interfaceCO.setPosInHeader(configuration.isPosInHeader()?ConstantsCommon.YES: ConstantsCommon.NO);
		interfaceCO.setSkipBitmap(configuration.isSkipBitmap()?ConstantsCommon.YES: ConstantsCommon.NO);
		interfaceCO.setIsoPresent(configuration.isIsoPresent()?ConstantsCommon.YES: ConstantsCommon.NO);
		
		// set message factory
		messageFactory = IsoConfigParser.createIsoFactory(interfaceCO);

		
		// length field Configuration ( offset ,length)
		configuration.setFrameLengthFieldOffset(null != interfaceCO.getProtocolIdentification()
					? interfaceCO.getProtocolIdentification().length()
					: 0)
				.setMaxFrameLength(12348);
				
		// create the default sender and connect
		sender = new DefaultSender(configuration, messageFactory);
		sender.start();
		
		// set the configuration used while start the simulator
		config = configuration;
	}
	

	/**
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	private void loadConfiguration() throws ParserConfigurationException, IOException {
		
		String content = new String (Files.readAllBytes(Paths.get("./configuration.xml")));
		HashMap<String, Object> hm = StringUtil.convertXmlToHashMap(content);
		
		// Root element
		HashMap<String,Object> configurations = (HashMap<String, Object>) hm.get("configuration");
		
		// parse roles
		HashMap<String,Object> clients = (HashMap<String, Object>) configurations.get("clients");
		ArrayList<HashMap<String,Object>> clientList = (ArrayList<HashMap<String, Object>>) clients.get("client");
		
		HashMap<String,String> clientConfiguration = new HashMap<>();
		for(HashMap<String,Object> client : clientList){
			
			if(!(Boolean)client.get("enabled"))
				continue;
			
			config.setIp((String)client.get("ip"));
			config.setPort((Integer)client.get("port"));
			config.setConnectorType((String)client.get("connectorType"));
			
			config.setName((String)client.get("name"));
			config.setProtocolIdentification((String)client.get("protocolIdentification"));
			config.setLengthFieldLength((int)client.get("lengthField"));
			config.setHeader((String)client.get("header"));
			config.setHeaderLength(config.getHeader().length());
			
			
			config.setBitmapType((Integer)client.get("bitmapType") == 8 ? 
					AtmEngineConstants.BITMAP_8 : AtmEngineConstants.BITMAP_16);
			config.setLengthType((Integer)client.get("lengthType") == 256 ? 
					AtmEngineConstants.LENGTH_BASE256 : AtmEngineConstants.LENGTH_BASE10);
			
			config.setIncludeLength(((int)client.get("includeLength")) == 1 );
			config.setPosInHeader(((int)client.get("posInHeader")) == 1 );
			config.setSkipBitmap(((int)client.get("skipBitmap")) == 1 );
			config.setIsoPresent(((int)client.get("isoPresent")) == 1 );
			break;
		}
	}
	
	
	/**
	 * @throws Exception
	 * 
	 */
	public void shutdown() throws Exception {
		sender.shutdown();
		sender = null;
	}

	/**
	 * @return the mainWindow
	 */
	public SwitchFrame getMainWindow() {
		return mainWindow;
	}

	/**
	 * @param mainWindow
	 *            the mainWindow to set
	 */
	public SwitchFrame setMainWindow(SwitchFrame mainWindow) {
		this.mainWindow = mainWindow;
		return this.mainWindow;
	}

	/**
	 * @return
	 */
	public DefaultSender getSender() {
		return sender;
	}

	public MessageFactory getMessageFactory() {
		return messageFactory;
	}
}
