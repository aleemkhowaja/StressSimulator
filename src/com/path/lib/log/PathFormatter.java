package com.path.lib.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.path.bo.common.ConstantsCommon;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.StringUtil;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: deniskhaddad
 *
 * PathFormatter.java used to format the message being logged
 */
public class PathFormatter extends java.util.logging.Formatter
{
    private static String newLine = "\n";
    private static Pattern p = Pattern.compile("\\{\\d?\\}");

    public static final int SEVERE = Level.SEVERE.intValue(), WARNING = Level.WARNING.intValue(), INFO = Level.INFO
	    .intValue(), CONFIG = Level.CONFIG.intValue(), FINE = Level.FINE.intValue(),
	    FINER = Level.FINER.intValue(), FINEST = Level.FINEST.intValue(), ALL = 1, OFF = 0;

    static
    {
	try
	{
	    newLine = System.getProperty("line.separator");
	}
	catch(Throwable t)
	{
	    System.err.println("Error getting system line separator for Logging will use \\n\n" + toString(t, false));
	}
    }

    /**
     * Returns a newLine separator
     * 
     * @return
     */
    protected String newLineString()
    {
	return newLine;
    }

    /**
     * Format the given LogRecord. Don't allow any errors to be thrown here
     * 
     *@param record
     *            the log record to be formatted.
     *@return a formatted log record
     */
    
    @Override
    public String format(LogRecord logRec)
    {
	synchronized(this)
	{
	    StringBuffer sb = new StringBuffer(4096);
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
	    try
	    {
		String className = null, fnName = null, lineNB = null, fName = null;
		java.lang.Throwable t = logRec.getThrown();
		
		sb.append("[").append(sdf.format(new Date())+"]");
		// adding details about the user of the message in case PathSolutions Exception is handled
		if(t != null && t instanceof BaseException)
		{
		    BaseException bEx = (BaseException) t;
		    sb.append("[");
		    sb.append(StringUtil.isNotEmpty(bEx.getMsgUsr())? "USR:"+bEx.getMsgUsr():"");
		    sb.append(StringUtil.isNotEmpty(bEx.getMsgAppName())? " APP:"+bEx.getMsgAppName():"");
		    sb.append(StringUtil.isNotEmpty(bEx.getMsgProgRef())? " SCR:"+bEx.getMsgProgRef():"");
		    sb.append(bEx.getMsgCompCode() != null ? " COMP:"+bEx.getMsgCompCode().longValue():"");
		    sb.append(bEx.getMsgBranchCode() != null ? " BRAN:"+bEx.getMsgBranchCode().longValue():"");
		    sb.append("] ");
		}
		

		
		StackTraceElement ste = getCallerClass(null);

		className = ste.getClassName();
		fnName = ste.getMethodName();
		lineNB = String.valueOf(ste.getLineNumber());
		fName = ste.getFileName();

		sb.append(className).append(".").append(fnName).append("(").append(fName).append(":").append(lineNB)
			.append(")");

		sb.append(newLineString());
		sb.append(" [").append(getLevelTrans(logRec)).append("]: ").append(formatMessage(logRec))
			.append(newLineString());

		
		if(t != null)
		{	
		    // Sometimes error in Log appeared as java.lang.NoClassDefFoundError: com/path/lib/log/PathSystemMonitor
		    try
		    {
			sb.append(PathSystemMonitor.monitorSystemIndicators());
		    }
		    catch(Throwable e)
		    {

			sb.append("[WARNING in Reading PathSystemMonitor Information ("+e.getMessage()+") No Worry]");
		    }
		    sb.append("\r\n[App="+ConstantsCommon.returnCurrentAppName()+" Version="+ConstantsCommon.returnAppVersion());
		    sb.append(toString(t, false));
		}

		sb.append(newLineString());
		return sb.toString();
	    }
	    catch(Throwable tt)
	    {
		sb.append("[Internal logger error  plz report this to R&D (Denisk):" + newLineString()
			+ toString(tt, false));
		// append original error if exists
		if(logRec.getThrown() != null)
		{
		    sb.append("Original ERROR: "+toString(logRec.getThrown(), false));
		}
	    }
	    return sb.toString();
	}
	
    }

    /**
     * This method returns the levelTrance property
     * 
     * @param logRec
     * @return
     */
    private String getLevelTrans(LogRecord logRec)
    {
	String zLevelName = null;
	int logLevel = logRec.getLevel().intValue();

	if(logLevel == SEVERE)
	{
	    zLevelName = "ERROR";
	}
	else if(logLevel == WARNING)
	{
	    zLevelName = "WARNING";
	}
	else if(logLevel == INFO)
	{
	    zLevelName = "INFO";
	}
	else if(logLevel == CONFIG)
	{
	    zLevelName = "CONFIG";
	}
	else if(logLevel == FINE)
	{
	    zLevelName = "DEBUG";
	}
	else if(logLevel == FINER)
	{
	    zLevelName = "METHOD";
	}
	else if(logLevel == FINEST)
	{
	    zLevelName = "TRACE";
	}

	return zLevelName;
    }

    /**
     * 
     * @param callIndex
     * @return
     */
    static StackTraceElement getCallerClass(int... callIndexParam)
    {
	//Avoid reassigning parameters
	int[] callIndex = callIndexParam;
	Throwable t = new Throwable();
	StackTraceElement[] ste = t.getStackTrace();
	int callerPos = 0;

	try
	{
	    // find a way to detect the calling class.
	    boolean foundJDKLog = false;
	    for(callerPos = 0; callerPos < ste.length; callerPos++)
	    {
		if(foundJDKLog)
		{
		    if(!ste[callerPos].getClassName().equals("java.util.logging.Logger")
		       && !ste[callerPos].getClassName().equals("com.path.lib.log.Log")
		       && !ste[callerPos].getClassName().equals("org.jboss.logmanager.Logger"))
			{
			    foundJDKLog = true;
			    if(callerPos < ste.length - 1)
			    {
				// next stack is Log or jboss Logger
				if(ste[callerPos++].getClassName().equals("com.path.lib.log.Log")
				   || ste[callerPos].getClassName().equals("org.jboss.logmanager.Logger")
					
					)
				{
				    callerPos++;
				}
				else
				{
				    callerPos--;
				}
			    }
			    break;
			}
		}
		else
		{
		    if(ste[callerPos].getClassName().equals("java.util.logging.Logger")
			    || ste[callerPos].getClassName().equals("com.path.lib.log.Log")
			    || ste[callerPos].getClassName().equals("org.jboss.logmanager.Logger")
			    )
		    {
			foundJDKLog = true;
		    }		    
		}
	    }
	    if(callIndex != null)
	    {
		callIndex[0] = callerPos;
	    }
	    
	    if(callerPos >= ste.length)
	    {
		callerPos  = ste.length - 1;
	    }
	}
	catch(Exception ex)
	{
	    callerPos = 0;
	    System.out.println("Please to report this error to the R&D (Denisk) department");
	    ex.printStackTrace();
	}
	return ste[callerPos];
    }

    /**
     * This method formats the message that is logged
     */
    @Override
    public String formatMessage(LogRecord logRec)
    {
	Object params[] = null;
	String msg = logRec.getMessage(), paramText = null;

	params = logRec.getParameters();
	if(params != null && params.length != 0)
	{
	    int idxParam = 0;

	    Matcher m = p.matcher(msg);
	    StringBuffer sb = new StringBuffer(1024);
	    while(m.find())
	    {
		if(idxParam == 0)
		{
		    paramText = "(" + toString(params[idxParam], true);
		}
		else
		{
		    paramText = ", " + toString(params[idxParam], true);
		}
		m.appendReplacement(sb, paramText);
		idxParam++;
	    }
	    sb.append(")");
	    m.appendTail(sb);
	    msg = sb.toString();
	}

	return msg;
    }

    /**
     * 
     * @param obj
     * @param isParam
     * @return
     */
    public static String toString(Object obj, boolean isParam)
    {
	if(obj == null)
	{
	    return "null";
	}
	else if(obj instanceof Throwable)
	{
	    Throwable e = (Throwable) obj;
	    if(isParam)
	    {
		return e.toString();
	    }
	    else
	    {
		StringWriter strWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(strWriter);
		e.printStackTrace(printWriter);
		return strWriter.toString();
	    }
	}
	else
	{
	    if(obj instanceof Date)
	    {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
		return "'" + sdf.format(obj) + "'";
	    }
	    else
	    {
		return "'" + obj.toString().replaceAll("\\$", "_") + "'";
	    }
	}
    }

}
