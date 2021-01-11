package com.path.simulator.core.pool;

import com.path.simulator.core.StressSimulator;
import com.path.simulator.core.vo.MessageVO;

public class MessageTask implements Runnable{
	
	private MessageVO message;
	private int delay;
	
	public MessageTask(MessageVO message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(delay);
			System.out.println("Message sent: " + message.getName());
			StressSimulator.getInstance()
				.getSender()
				.sendMessage(message);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}
