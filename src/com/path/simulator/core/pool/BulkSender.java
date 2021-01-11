package com.path.simulator.core.pool;

import java.util.ArrayList;

import com.path.atm.engine.iso8583.MessageFactory;
import com.path.simulator.core.DefaultSender;
import com.path.simulator.core.StressSimulator;
import com.path.simulator.core.dataprovider.DataProviderManager;
import com.path.simulator.core.dataprovider.MessageGenerator;
import com.path.simulator.core.vo.MessageVO;

public class BulkSender {

	private SenderPoolExecutor senderPool;
	private ArrayList<MessageTask> tasks = new ArrayList<>();
	private int messageDelay;
	
	/**
	 * @param defaultSender
	 * @param numberOfSender
	 */
	public BulkSender(int numberOfSender, int messagePerMinute) {
		senderPool = SenderPoolExecutor.newFixedThreadPool(numberOfSender);
		this.messageDelay = (60000/numberOfSender)/messagePerMinute;
		
		
	}
	
	/**
	 * @param messagesToSend
	 * @return
	 */
	public BulkSender prepareTasks(ArrayList<MessageVO> messagesToSend){
		
		for(MessageVO message : messagesToSend){
			// check count and based on that create the needed tasks
			ArrayList<MessageTask> tasksbyMessage = prepareMsgTasks(message);
			tasks.addAll((tasksbyMessage));
		}
		
		return this;
	}
	
	/**
	 * @param messagesToSend
	 */
	public void sendMessages(){
		
		for(MessageTask task : tasks)
			senderPool.submit(task);
		
		// shutdown the 
		senderPool.shutdown();
	}
	
	
	/**
	 * Prepare the tasks
	 * @param message
	 * @return 
	 */
	private ArrayList<MessageTask> prepareMsgTasks(MessageVO message) {
		
		// get the number of messages to be sent
		int total = message.getTotal() > 0 ? message.getTotal() : 1 ;
		ArrayList<MessageTask> tasks = new ArrayList<>();

		MessageFactory factory = StressSimulator.getInstance()
				.getMessageFactory();
		for(int i =0 ; i<total; i++){
			MessageGenerator messageGenerator = new MessageGenerator(DataProviderManager.getInstance());
			String payload = messageGenerator.generate(message.getPayload());
			MessageVO vo = new MessageVO();
			vo.setFactory(factory);
			vo.setName(message.getName());
			vo.setPayload(payload);
			MessageTask msgTsk = new MessageTask(vo);
			msgTsk.setDelay(messageDelay);
			tasks.add(msgTsk);
		}
		
		return tasks;
	}

	/**
	 * @return the senderPool
	 */
	public SenderPoolExecutor getSenderPool() {
		return senderPool;
	}

	/**
	 * @param senderPool the senderPool to set
	 */
	public void setSenderPool(SenderPoolExecutor senderPool) {
		this.senderPool = senderPool;
	}

	/**
	 * @return the tasks
	 */
	public ArrayList<MessageTask> getTasks() {
		return tasks;
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(ArrayList<MessageTask> tasks) {
		this.tasks = tasks;
	}

	public void setMessagePerMinute(int mpp) {
		// TODO Auto-generated method stub
		
	}

}
