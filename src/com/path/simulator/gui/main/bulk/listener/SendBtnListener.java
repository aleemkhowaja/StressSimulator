package com.path.simulator.gui.main.bulk.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.path.simulator.core.StressSimulator;
import com.path.simulator.core.pool.BulkSender;
import com.path.simulator.core.vo.MessageVO;
import com.path.simulator.gui.main.bulk.BulkMessagePanel;
import com.path.simulator.util.MessageProviderUtil;
import com.path.simulator.util.Notifier;

public class SendBtnListener implements ActionListener {

	/**
	 * 
	 */
	private BulkMessagePanel messagePanel;

	public SendBtnListener(BulkMessagePanel messagePanel) {
		this.messagePanel = messagePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// validate sender number
		String senderNb = messagePanel.getNumberOfSender().getText();
		
		if( null == senderNb || senderNb.isEmpty()){
			Notifier.errorMsg("Sender number must be provided");
			return;
		}
		
		ArrayList<MessageVO> messagesToSend = messagePanel.
			getMessagesGrid().getValues();
		
		if( null == messagesToSend || messagesToSend.size() <= 0){
			Notifier.errorMsg("No message selected");
			return;
		}
		
		
		int senderCount = Integer.valueOf(messagePanel.getNumberOfSender().getText());
		int mpp = messagePanel.getMessagePerMinute().getText().isEmpty() ? 0 :
			Integer.valueOf(messagePanel.getMessagePerMinute().getText());
		
		BulkSender bulkSender = StressSimulator.getInstance().getSender().createBulkSender(senderCount,mpp);
		bulkSender.prepareTasks(messagesToSend);
		bulkSender.sendMessages();
		
		new SenderGUIWorker(bulkSender,bulkSender.getTasks().size()).execute();
	}
	
	
	/**
	 * @author MohammadAliMezzawi
	 */
	class SenderGUIWorker extends SwingWorker<Integer, String> {
		
		private BulkSender bulkSender;
		private int itemCount;
		
		/**
		 * @param itemEvent
		 */
		public SenderGUIWorker(BulkSender bulkSender, int itemCount){
			this.bulkSender = bulkSender;
			this.itemCount = itemCount;
		}
		
		@Override
		protected Integer doInBackground() throws Exception {
			
			messagePanel.setPanelEnabled(false);
			messagePanel.getProgressBar().setEnabled(true);
			//messagePanel.showLoader(true);
			
			boolean progress = true;
			messagePanel.getProgressBar().setVisible(true);
			messagePanel.getProgressBar().setMaximum(itemCount);
			System.out.println("Maximum" + itemCount);
			while( progress ){
				
				long count = bulkSender.getSenderPool().getCompletedTaskCount();
				messagePanel.getProgressBar().setValue((int) count);
				System.out.println("Completed" +count);
				if(((int)count) >= itemCount)
					progress = false;
				
				Thread.sleep(100);
			}
			
			messagePanel.setPanelEnabled(true);
			//messagePanel.showLoader(false);
			return 1;
		}
	}

}