package com.path.simulator.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.path.simulator.core.vo.MessageVO;
public class MessageProviderUtil {
	
	public static void main(String[] args) throws IOException{
		returnMessages("Summit Message");
	}
	/**
	 * @param providerName
	 * @return
	 * @throws IOException 
	 */
	public static ArrayList<MessageVO> returnMessages(String providerName) throws IOException{
		
		// parse the 
		ArrayList<MessageVO> messageVOs = new ArrayList<>();
		
		// open the xml file
		String content = new String (Files.readAllBytes(
			Paths.get("./messageProvider/"+providerName+"/messages.xml")));
		HashMap<String, Object> hm = StringUtil.convertXmlToHashMap(content);
		
		// parse messages
		HashMap<String,Object> messagesRoot = (HashMap<String, Object>) hm.get("messages");
		ArrayList<HashMap<String,Object>> messages = (ArrayList<HashMap<String, Object>>)
				messagesRoot.get("message");
		
		for(HashMap<String,Object> message : messages){
			
			MessageVO messageVO = new MessageVO();
			messageVO.setName((String)message.get("name"));
			messageVO.setPayload((String)message.get("content"));
			
			messageVOs.add(messageVO);
		}
		return messageVOs;
		
	}
}
