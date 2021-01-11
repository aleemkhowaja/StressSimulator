package com.path.simulator.util;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JOptionPane;


public class Notifier {
	
	private final static int ERROR_MSG = 0;
	private final static int INFO_MSG = 1;
	private final static int WARNING_MSG = 2;
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void infoMsg(String text){
		infoMsg(null, text);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void infoMsg(Frame frame,String text){
		showMessage(text,INFO_MSG,"Info",frame);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void infoPopup(String text){
		//showPopup(text,INFO_MSG);
	}
	
	/**
	 * Display warning message
	 * @param text
	 */
	public static void warningMsg(String text){
		warningMsg(null,text);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void warningMsg(Frame frame,String text){
		showMessage(text,WARNING_MSG,"warning",frame);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void warningPopup(String text){
		//showPopup(text,INFO_MSG);
	}
	
	/**
	 * Display Error message
	 * @param text
	 */
	public static void errorMsg(String text){
		errorMsg(null,text);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void errorMsg(Frame frame,String text){
		showMessage(text,ERROR_MSG,"error",frame);
	}
	
	/**
	 * Display Info message
	 * @param text
	 */
	public static void errorPopup(String text){
		//showPopup(text,INFO_MSG);
	}
	
	
	private static void showMessage(String text, int msgType, String title, Frame frame) {
		
		int frameState = null == frame ? Frame.NORMAL
			:frame.getState();
		
		if( 0 == frameState){
			displayDisalog(text, msgType , title);
		}else{
			//showPopup(text,msgType);
		}
		
	}
	/**
	 * Display warning message
	 * @param text
	 */
	private static void displayDisalog(String text, int msgType , String title){
	    Toolkit.getDefaultToolkit().beep();
	    JOptionPane optionPane = new JOptionPane(text,msgType);
	    JDialog dialog = optionPane.createDialog(title);
	    dialog.setAlwaysOnTop(true);
	    dialog.setVisible(true);
	}
	
	
	
	private static MessageType getMessageType(int type){
		
		HashMap<Integer,MessageType> hm = new HashMap<>();
		hm.put(new Integer(0), MessageType.ERROR);
		hm.put(new Integer(1), MessageType.INFO);
		hm.put(new Integer(2), MessageType.WARNING);
		
		return hm.get(type);
	}
	
	
}
