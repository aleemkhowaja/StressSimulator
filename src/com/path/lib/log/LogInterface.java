package com.path.lib.log;
/**
 * 
 * Copyright 2019, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: DeniskHaddad
 *
 * LogInterface.java used to implement the Log functionality in case needed to be extended in any of the Modules
 */
public interface LogInterface
{
    
    /**
     * Echo an ERROR level severity Error.
     * 
     * @param msg
     *            String
     */
    public void error(String msg);

    /**
     * Logs a message with the stacktrace to the configured loggers.
     * 
     * @param t
     *            Throwable
     * @param msg
     *            String
     */
    public void error(Throwable t, String msg);

    /**
     * Logs a WARNING level message.
     * 
     * @param msg
     *            String
     */
    public void warning(String msg);

    /**
     * Logs an INFO level message
     * 
     * @param msg
     *            String
     */
    public void info(String msg);

    /**
     * Logs a DEBUG level message.
     * 
     * @param msg
     *            String
     */
    public void debug(String msg);
    

    /**
     * Logs the lowest level messages.
     * 
     * @param msg
     *            String
     */
    public void trace(String msg);

    /**
     * Logs config messages
     * 
     * @param msg
     */
    public void config(String msg);

    
}
