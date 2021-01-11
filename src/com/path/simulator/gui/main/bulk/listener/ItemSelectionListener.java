package com.path.simulator.gui.main.bulk.listener;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import com.path.simulator.core.dataprovider.DataProviderManager;
import com.path.simulator.core.vo.MessageVO;
import com.path.simulator.gui.main.bulk.BulkMessagePanel;
import com.path.simulator.util.MessageProviderUtil;

/**
 * Handle Queue list selection
 * 
 * @author MohammadAliMezzawi
 *
 */
public class ItemSelectionListener implements ItemListener {

	private BulkMessagePanel owner;
	
	public ItemSelectionListener(BulkMessagePanel messagePanel) {
		owner = messagePanel;
	}

	public void itemStateChanged(ItemEvent itemEvent) {
		int state = itemEvent.getStateChange();
		
		// kickoff handler on select
		if(state == ItemEvent.SELECTED)
			new SelectionWorker(itemEvent).execute();
	}

	/**
	 * @author MohammadAliMezzawi
	 */
	class SelectionWorker extends SwingWorker<Integer, String> {
		
		private ItemEvent itemEvent;
		
		/**
		 * @param itemEvent
		 */
		public SelectionWorker(ItemEvent itemEvent){
			this.itemEvent = itemEvent;
		}
		
		@Override
		protected Integer doInBackground() throws Exception {
			
			
			owner.getMessagesGrid().removeRows();
			String fileName = (String)itemEvent.getItem();
			
			// 
			ArrayList<MessageVO> messageVOs = MessageProviderUtil
				.returnMessages(fileName);
			
			for(MessageVO message : messageVOs){
				owner.getMessagesGrid().addLine(message);
			}
			
			// regenerate the data provider
			DataProviderManager.getInstance().regenerate(fileName);
			return 1;
		}
	}
}
