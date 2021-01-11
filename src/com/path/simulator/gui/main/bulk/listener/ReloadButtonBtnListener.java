package com.path.simulator.gui.main.bulk.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.path.simulator.gui.main.bulk.BulkMessagePanel;
import com.path.simulator.util.FileUtil;

public class ReloadButtonBtnListener implements ActionListener {

	/**
	 * 
	 */
	private BulkMessagePanel messagePanel;

	public ReloadButtonBtnListener(BulkMessagePanel messagePanel) {
		this.messagePanel = messagePanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//ArrayList<String> fileNames = FileUtil.getAllFiles("./messageProvider","xml");
		
		// list of all directories
		String[] directories = FileUtil.getAllDirectories("./messageProvider");
		// clear old data
		messagePanel.getMessageProviderCombo().removeAllItems();
		
		// refresh grid
		for(String fileName : directories)
			messagePanel.getMessageProviderCombo()
				.addItem(fileName.split("\\.")[0]);

	}

}
