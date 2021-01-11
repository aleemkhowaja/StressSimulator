package com.path.simulator.gui.base;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * This Class is the parent of all frame in this application.
 * It hold all the frame common behavior.
 * 
 * @author MohammadAliMezzawi
 *
 */
public abstract class BaseJFrame extends JFrame{
	
	/**
	 * Hold min Width
	 */
	protected int minWidth = 100;
	
	/**
	 * Hold min height
	 */
	protected int minHeight = 100;
	
	/**
	 * Base constructor
	 */
	public BaseJFrame(){
		initDimensions();
	}
	
	/**
	 * This method will center the frame
	 * in the middle of the Screen
	 */
	protected void centerFrame() {
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, this.minWidth, this.minHeight);
		setMinimumSize(new Dimension(minWidth, minHeight));
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		setLocation(x, y);
		
	}
	
	/**
	 * Init mini height
	 */
	protected abstract void initDimensions();

	/**
	 * @return the minWidth
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * @param minWidth the minWidth to set
	 */
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	/**
	 * @return the minHeight
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * @param minHeight the minHeight to set
	 */
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

}
