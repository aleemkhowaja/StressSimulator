package com.path.simulator.gui.base;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.path.simulator.gui.main.connection.ConnectionPanel;


/**
 * This Class is the parent of all Panel in this application.
 * It hold all the frame common behavior.
 * 
 * @author MohammadAliMezzawi
 *
 */
public abstract class BasePanel extends JPanel{

	private JLabel lblLoader;
	private JLabel lblLoaderText;
	
	/**
	 * Create loader Panel
	 */
	protected void createLoaderPanel(int x, int y) {
		
		ImageIcon loader = new ImageIcon(ConnectionPanel.class.getResource(
				"/com/path/simulator/images/6.gif"));
		
		lblLoader = new JLabel();
		lblLoader.setBounds(x, y , 64, 64);
		add(lblLoader);
		lblLoader.setIcon(loader);
		lblLoader.setVisible(false);
		
		lblLoaderText = new JLabel("loading...");
		lblLoaderText.setBounds(x+5, y+68, 55, 17);
		add(lblLoaderText);
		lblLoaderText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoaderText.setVisible(false);
	}
	
	/**
	 * @param show
	 */
	public void showLoader(boolean show) {
		lblLoaderText.setVisible(show);
		lblLoader.setVisible(show);
		setPanelEnabled(!show);
		
		// 
		lblLoaderText.setEnabled(true);
		lblLoader.setEnabled(true);
	}
	
	/**
	 * @param enable
	 */
	public void setPanelEnabled(boolean enable){
		setPanelEnabled(this,enable);
	}
	
	/**
	 * @param enable
	 */
	public void setEditable(boolean enable){
		setEditable(this,enable);
	}
	
	/**
	 * Disable Panel and all it's child components
	 * @param panel
	 * @param isEnabled
	 */
	public static void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	            continue;
	        }
	        
	        if(component instanceof JScrollPane){
	        	JScrollPane scrollPane = (JScrollPane) component;
	        	scrollPane.getHorizontalScrollBar().setEnabled(isEnabled);
	        	scrollPane.getVerticalScrollBar().setEnabled(isEnabled);
	        	scrollPane.getViewport().getView().setEnabled(isEnabled);
	        	continue;
	        }

	        component.setEnabled(isEnabled);
	    }
	}
	
	/**
	 * @param panel
	 * @param isEditable
	 */
	public static void setEditable(JPanel panel, Boolean isEditable) {
		
		 Component[] components = panel.getComponents();
		 
		 for (Component component : components) {
			 
			 if (component instanceof JPanel) {
				 setEditable((JPanel) component, isEditable);
				 continue;
			 }
			 
			 if(component instanceof JTextField){
				 ((JTextField)component).setEditable(isEditable);
				 continue;
			 }
			 
			 // specific case ( different than combo box editable feature)
			 if(component instanceof JComboBox){
				 ((JComboBox)component).setEnabled(isEditable);
				 continue;
			 }
			 
			 // specific case
			 if(component instanceof JCheckBox){
				 ((JCheckBox)component).setEnabled(isEditable);
				 continue;
			 }
		 }
	}

}
