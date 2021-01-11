package com.path.simulator.gui.main.plain.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

import com.path.simulator.core.StressSimulator;
import com.path.simulator.core.vo.MessageVO;
import com.path.simulator.gui.main.plain.PlainMessagePanel;
import com.path.simulator.util.Notifier;

public class SendBtnListener implements ActionListener {

	/**
	 * 
	 */
	private PlainMessagePanel messagePanel;

	public SendBtnListener(PlainMessagePanel messagePanel) {
		this.messagePanel = messagePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			new SendMessageWorker().execute();
		} catch (Exception x) {
			Notifier.errorMsg("Error while Sending message");
		}
	}
	
	
	/**
	 * @author MohammadAliMezzawi
	 */
	class SendMessageWorker extends SwingWorker<Integer, String> {

		@Override
		protected Integer doInBackground() throws Exception {
			try {
				// show loader;
				messagePanel.showLoader(true);
				messagePanel.setEditable(false);
				Thread.sleep(1000);
				MessageVO messageVO = new MessageVO();
				messageVO.setPayload(messagePanel.getMessagePayload().getText());
				StressSimulator.getInstance().getSender()
					.sendMessage(messageVO);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}finally {
				// hide loader;
				messagePanel.showLoader(false);
				messagePanel.setEditable(false);
			}
			
			return 1;
		}
	}

}