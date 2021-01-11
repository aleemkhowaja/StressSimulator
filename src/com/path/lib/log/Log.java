package com.path.lib.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import org.springframework.core.io.support.PropertiesLoaderUtils;


import com.path.bo.common.ConstantsCommon;


/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code
 * 
 * @author: deniskhaddad
 *
 * Log.java used to detect ERROR (highest value), WARNING, INFO, CONFIG, DEBUG, Entry/Exit,
 * TRACE (lowest value) This logger can use other features @ development time:
 * filtering packages & classes logs and display of method times when entering &
 * exiting. Note that the time logging may not work correctly in the case of
 * recursive functions.
 */
public class Log implements LogInterface
{
    private volatile static Log me;
    private static Object lock = new Object();
    //levelAllEnabledTime is added to check on log level ALL after a predefined interval in order to reset in to ERROR
    //logByUserEnabledTime is added to check if log by user was enabled then after a predefined interval equal to levelALLInterval. in order to remove the Log instance created for this user
    private Long levelAllEnabledTime, logByUserEnabledTime;
    //the interval of time in seconds used to keep the log level ALL before switching it back to ERROR. by default, if not defined it will be 300s
    private Integer levelALLInterval;
    boolean isFilterEnabled, isTimeEnabled;

    HashMap lstFilter;
    Map lstTimes;
    Properties pLogs;
    static final int ERROR_LEVEL = Level.SEVERE.intValue();

    public static final int ERROR = 0, WARNING = 1, INFO = 2, CONFIG = 3, DEBUG = 4, METHOD = 5, TRACE = 6, ALL = 7,
    OFF = 8;

    ConsoleHandler cHandler;
    FileHandler defaultFileHandler, xmlFileHandler;
    Logger zLogger;

    String defaultName, isLogPerAppEnabled, postFixAppName = "", xmlName, defaultLogPath = "./logs/", logSystemDetails, sensitiveUrlPattern = null;
    int defaultSize, defaultCount, xmlSize, xmlCount;

    Level consoleLevel, defaultLevel, xmlLevel, generalLogLevel;
    int currentDayOfYear;
    
    private String logByUserName = null;
    private volatile static Map<String, Log> logByUserMap = new HashMap<String, Log>();
    
    /**
     * 
     * Used for setting the Name to be added to the Log File, if Log per Application is Enabled
     * 
     */
    private void applyAppPostfix()
    {
	try
	{
	    if("1".equals(isLogPerAppEnabled))
	    {
		Properties appLog = PropertiesLoaderUtils.loadAllProperties("AppPathLogs.properties");

		if(appLog.isEmpty())
		{
		    System.out.println("AppPathLogs.properties file not found in the classpath or No Properties defined inside.\nYou have to add this Path to the application and define the Properies Properly.");
		}
		else
		{
		    postFixAppName = appLog.getProperty("path.logger.file.default.postfix.name","");
		}
	    }
	}
	catch(Exception exx)
	{
	    System.out.println("Error in Loading and reading AppPathLogs.properties Contact Administrator");
	    exx.printStackTrace();
	}
    }
    
    protected Log(String logByUserName)
    {
	if(logByUserName == null || logByUserName.isEmpty())
	{
	    System.out.println("******************Initiating JDK logger [com.path.lib.log]******************");
	}
	this.logByUserName = logByUserName;
	initParams();
	String loggerName = "Path";
	if(!postFixAppName.isEmpty())
	{
	    loggerName = (postFixAppName + "_").concat(loggerName);
	}
	
	if(logByUserName != null && !logByUserName.isEmpty())
	{
	    loggerName = logByUserName +(( postFixAppName.isEmpty() ) ? ConstantsCommon.returnCurrentAppName() :  postFixAppName).concat(loggerName);
	}
	zLogger = Logger.getLogger(loggerName);
	
	
	zLogger.setUseParentHandlers(false);
	currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);

	if(!generalLogLevel.equals(Level.OFF))
	{
	    //checking if a logger exists in Logger.getLogger() with the same name, this can occur when the portal and services war for the same application are deployed on the same server
	    //in this case we should not initialize again the defaultFileHandler with the same file name because it will create an additional log file .log1 and the logs will be duplicated.
	    //that's why we should use the same handlers already available in zlogger. Noting that under Tomcat 8 and Tomcat 9 Logger.getLogger(loggerName) will return always
	    // a new logger that's why the .log and .log1 will be always created. while in Websphere or Jboss the Logger.getLogger(loggerName) will return the previously created zlogger
	    // and only 1 .log will be created and used by the portal and the services applications
	    boolean newLogger = true;
	    Handler[] handlersArray = zLogger.getHandlers();
	    
	    if(logByUserName == null && handlersArray != null && handlersArray.length > 0)
	    {
		newLogger = false;
		for(Handler handlerItem : handlersArray)
		{
		    if(handlerItem instanceof ConsoleHandler)
		    {
			cHandler = (ConsoleHandler)handlerItem;
		    }
		    else if(handlerItem instanceof FileHandler)
		    {
			//we cannot check if the defaultFileHandler formatter instance of PathFormatter, it will return false,
			//we will check on the class name of the formatter
			String formatterClassName = handlerItem.getFormatter().getClass().toString();
			if(formatterClassName.equals(XMLFormatter.class.toString()) 
				|| handlerItem.getFormatter() instanceof XMLFormatter)
			{
			    xmlFileHandler = (FileHandler)handlerItem;
			}
			else if(formatterClassName.equals(PathFormatter.class.toString()) 
				|| handlerItem.getFormatter() instanceof PathFormatter)
			{
			    defaultFileHandler = (FileHandler)handlerItem;
			}
		    }
		}
	    }
	    if(newLogger && !consoleLevel.equals(Level.OFF))
	    {
		mgtConsoleLog();
	    }

	    if(newLogger && defaultName != null)
	    {
		try
		{
		    //initFileHandler();
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
	    }

	    if(newLogger && xmlName != null)
	    {
		try
		{
		    String pth = "./logs/";
		    xmlFileHandler = new FileHandler(pth + xmlName + "_%g.log", xmlSize * 1024 * 1024, xmlCount, true);
		    xmlFileHandler.setFormatter(new XMLFormatter());
		    xmlFileHandler.setLevel(xmlLevel);
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
		if(xmlFileHandler != null)
		{
		    zLogger.addHandler(xmlFileHandler);
		}
	    }

	    setLogLevelHandlers(cHandler, defaultFileHandler, xmlFileHandler);
	}
	zLogger.setLevel(generalLogLevel);

	if(isFilterEnabled)
	{
	    initFilter();
	    Enumeration propsEnum = pLogs.propertyNames();
	    while(propsEnum.hasMoreElements())
	    {
		String pName = (String) propsEnum.nextElement();
		if(pName.indexOf("filter") == 0)
		{
		    lstFilter.put(pName.substring(pName.indexOf(".") + 1), parseLevel(pLogs.getProperty(pName)));
		}
	    }
	}

	if(isTimeEnabled)
	{
	    lstTimes = Collections.synchronizedMap(new HashMap());
	}
    }

    /**
     * This function initializes the filter and it's component. if this is the
     * first time the filter is used, then it creates it and attaches it to the
     * main logger else it just clears the list of filters map.
     * 
     * @throws SecurityException
     */
    private void initFilter() throws SecurityException
    {
	if(lstFilter == null)
	{
	    lstFilter = new HashMap();
	    zLogger.setFilter(new PathLogFilter(lstFilter, isFilterEnabled));
	}
	else
	{
	    lstFilter.clear();
	}
    }

    /**
     * Sets user defined log level for specific classes and packages xx.yy.zz
     * 
     * @param cHandler
     *            ConsoleHandler
     * @param defaultFileHandler
     *            FileHandler
     * @param xmlFileHandler
     *            FileHandler
     */
    private void setLogLevelHandlers(ConsoleHandler cHandler, FileHandler defaultFileHandler, FileHandler xmlFileHandler)
    {
	Enumeration propsEnum = pLogs.propertyNames();
	Logger logLevelsLegger = null;
	while(propsEnum.hasMoreElements())
	{
	    String pName = (String) propsEnum.nextElement();
	    int loglevelIndex = pName.toLowerCase(Locale.ENGLISH).indexOf("loglevel");

	    // Console log config
	    if(loglevelIndex > 0)
	    {
		String name = pName.substring(0, loglevelIndex - 1);
		Level level = parseLevel(pLogs.getProperty(pName));

		logLevelsLegger = Logger.getLogger(name);
		logLevelsLegger.setUseParentHandlers(false);
		logLevelsLegger.setLevel(level);

		if(cHandler != null)
		{
		    logLevelsLegger.addHandler(cHandler);
		}
		if(defaultFileHandler != null)
		{
		    logLevelsLegger.addHandler(defaultFileHandler);
		}
		if(xmlFileHandler != null)
		{
		    logLevelsLegger.addHandler(xmlFileHandler);
		}
	    }

	}

    }

    
    
    /**
     * possibility to change level at Runtime
     * @param theLevel
     */
    public void changeLogLevel(String theLevel)
    {
	if(theLevel != null)
	{
	    Level theLev = parseLevel(theLevel);
	    me.defaultFileHandler.setLevel(theLev);
	    zLogger.setLevel(theLev);
	    setDefaultLevel(theLev);
	    //in case the log level is set to ALL , we need to update the levelAllEnabledTime to check on it on each trace written to log file
	    if(logByUserName == null && "ALL".equals(theLevel) && Level.SEVERE.equals(generalLogLevel))
	    {
		me.levelAllEnabledTime = System.currentTimeMillis();
	    }
	}
    }
    
    /**
     * function used to automatically resetting log level to ERROR after switching it manually to ALL from the screen
     */
    public void resetLogLevelAll()
    {
	if(logByUserName == null && me.levelAllEnabledTime != null && levelALLInterval != null && Level.ALL.equals(getDefaultLevel())
		&& Level.SEVERE.equals(generalLogLevel) && (System.currentTimeMillis() - me.levelAllEnabledTime) > levelALLInterval * 1000)
	{
	    String message = null;
	    // in case of portal application the CommonLibBOImpl will not be found in classpath
	    boolean inPresentationLayer = true;
	    try
	    {
		Class.forName("com.path.bo.common.impl.CommonLibBOImpl");
	    }
	    catch(ClassNotFoundException e)
	    {
		inPresentationLayer = false;
	    }
	    
	    if(inPresentationLayer)
	    {
		message = "Automatically Updating Log Level at presentation layer to ERROR for " + ((postFixAppName == null || postFixAppName.isEmpty()) ? "PATH" : postFixAppName);
		
	    }
	    else
	    {
		message = "Automatically Updating Log Level at services layer to ERROR for " + ((postFixAppName == null || postFixAppName.isEmpty()) ? "PATH" : postFixAppName);
	    }
	    
	    //do not use debug() to avoid infinite loop. use zLogger.fine instead
	    me.zLogger.info(message);
	    
	    me.levelAllEnabledTime = null;
	    changeLogLevel("SEVERE");
	}

    }
    /**
     * Reads the parameters from the 'PathLogs.properties' and initilize the
     * logger. the PathLog.properties is read from the "classpath" of the
     * system. You have to be sure that only one file PathLogs.properties exists
     * in the classpath otherwise the behavior will be undefined.
     */
    private void initParams()
    {
	try
	{
	    pLogs = new Properties();

	    pLogs = PropertiesLoaderUtils.loadAllProperties("PathLogs.properties");

	    if(pLogs== null || pLogs.isEmpty())
	    {
		System.out
		.println("PathLogs.properties file not found in the classpath;\nYou have to configure this file in the classpath of your server.");
	    }

	    generalLogLevel = parseLevel(pLogs.getProperty("path.logger.level", "OFF"));
	    consoleLevel = parseLevel(pLogs.getProperty("path.logger.console.level", "OFF"));
	    //initialize the levelALLInterval and in case log level set to ALL 
	    if(Level.SEVERE.equals(generalLogLevel))
	    {
		String levelALLIntervalValue = pLogs.getProperty("path.logger.level.all.deactivation.interval");
		levelALLInterval = Integer.parseInt((levelALLIntervalValue == null || levelALLIntervalValue.isEmpty() ? "300" : levelALLIntervalValue));
	    }
	    defaultName = pLogs.getProperty("path.logger.file.default.name", "PathSol");
	    
	    // check if log folder location is specified then assign it to defaultLogPath
	    String defaultFldLoc = pLogs.getProperty("path.logger.file.default.folder", defaultLogPath).trim();
	    if(!defaultFldLoc.endsWith("/") && !defaultFldLoc.endsWith("\\"))
	    {
		defaultFldLoc = defaultFldLoc.concat("/");
	    }
	    defaultLogPath = defaultFldLoc;
	    
	    // check if folder specification provided in file Name
	    if(defaultName.indexOf("/") > 0)
	    {
		defaultLogPath = defaultLogPath + defaultName.substring(0,defaultName.lastIndexOf("/")+1);
		defaultName = defaultName.substring(defaultName.lastIndexOf("/")+1);
	    }
	    // setting property of Enable Log per Application
	    isLogPerAppEnabled = pLogs.getProperty("path.logger.file.default.applogenabled","0");

	    applyAppPostfix();
	    if(logByUserName != null && !logByUserName.isEmpty())
	    {
		//escape special characters by underscore "_" and keep only alpha and digits characters 
		String escapedLogByUserName = logByUserName.replaceAll("[^a-zA-Z0-9]+","_");
		defaultName = ( postFixAppName.isEmpty() ) ? ConstantsCommon.returnCurrentAppName() :  postFixAppName  + "_" + escapedLogByUserName + "_" + defaultName;
	    }
	    else
	    {
		if(!postFixAppName.isEmpty())
		{
		    defaultName = postFixAppName + "_" + defaultName;
		}
	    }

	    defaultSize = Integer.parseInt(pLogs.getProperty("path.logger.file.default.sizelimit", "1"));
	    defaultCount = Integer.parseInt(pLogs.getProperty("path.logger.file.default.countcycle", "1"));
	    defaultLevel = parseLevel(pLogs.getProperty("path.logger.file.default.level", "ERROR"));
	    logSystemDetails = pLogs.getProperty("path.logger.file.default.sysmonitor", "0").trim();

	    xmlName = pLogs.getProperty("path.logger.file.xml.name");
	    xmlSize = Integer.parseInt(pLogs.getProperty("path.logger.file.xml.sizelimit", "1"));
	    xmlCount = Integer.parseInt(pLogs.getProperty("path.logger.file.xml.countcycle", "1"));
	    xmlLevel = parseLevel(pLogs.getProperty("path.logger.file.xml.level", "OFF"));
	    isFilterEnabled = pLogs.getProperty("path.logger.filter", "false").equalsIgnoreCase("true");
	    isTimeEnabled = pLogs.getProperty("path.logger.logtime", "false").equalsIgnoreCase("true");
	    sensitiveUrlPattern = pLogs.getProperty("path.logger.sensitive.url.pattern","").trim();
	}
	catch(Exception ex)
	{
	    ex.printStackTrace();
	}
    }
    
    public Boolean returnInfoLevel()
    {
	if(defaultLevel == Level.SEVERE || defaultLevel == Level.WARNING)//ERROR level
	{
	    return Boolean.FALSE;
	}
	return Boolean.TRUE;
    }
    /**
     * Method that return a property of sensitive regular expression pattern, to replace the request url while debugging in the logs
     * @return the pattern if exists null if not defined
     */
    public String returnSensetiveInfoPattern()
    {
	if(sensitiveUrlPattern != null && !sensitiveUrlPattern.isEmpty())
	{
	    return sensitiveUrlPattern;
	}
	return null;
    }

    /**
     * This method read a string (usualy comming from the config file) and
     * transform it to the java log level
     * 
     * @param tmp
     *            String
     * @return Level
     */
    private Level parseLevel(String tmp)
    {
	if(tmp == null || tmp.equalsIgnoreCase("OFF"))
	{
	    return Level.OFF;
	}
	else if("ERROR".equalsIgnoreCase(tmp) || "SEVERE".equalsIgnoreCase(tmp))
	{
	    return Level.SEVERE;
	}
	else if("WARNING".equalsIgnoreCase(tmp))
	{
	    return Level.WARNING;
	}
	else if("INFO".equalsIgnoreCase(tmp))
	{
	    return Level.INFO;
	}
	else if("CONFIG".equalsIgnoreCase(tmp))
	{
	    return Level.CONFIG;
	}
	else if("DEBUG".equalsIgnoreCase(tmp))
	{
	    return Level.FINE;
	}
	else if("METHOD".equalsIgnoreCase(tmp))
	{
	    return Level.FINER;
	}
	else if("TRACE".equalsIgnoreCase(tmp))
	{
	    return Level.FINEST;
	}
	else
	{
	    return Level.ALL;
	}
    }

    private static void changeFileName(Log log)
    {
	if(log.shouldChangeFileName() && log.defaultFileHandler != null)
	{
	    synchronized(lock)
	    {
		try
		{
		    // should call the mgt function
		    log.currentDayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		    log.zLogger.removeHandler(log.defaultFileHandler);
		    log.defaultFileHandler.close();
		    log.defaultFileHandler = null;
		   // log.initFileHandler();

		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
	    }
	}

    }
    
   
    
    /**
     * function returning a message indicating the time remaining before closing the log by user
     * @param userName
     * @return
     */
    public static Map returnLogByUserRemainingTime(String userName)
    {
	if(userName != null && !userName.isEmpty())
	{
	    if(logByUserMap.containsKey(userName))
	    {
		Log logByUser = logByUserMap.get(userName);
		if(logByUser.levelALLInterval!= null)
		{
			Date logInitDate = new Date(logByUser.logByUserEnabledTime);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(logInitDate);
			cal.add(Calendar.SECOND, logByUser.levelALLInterval);
			Date logRemainingDate = cal.getTime();
			
			long diffInMilliSec = logRemainingDate.getTime() - System.currentTimeMillis();
			if(diffInMilliSec > 0)
			{
			    long seconds = (diffInMilliSec / 1000) % 60;
		            long minutes = (diffInMilliSec / (1000 * 60)) % 60;
		            Map<String,Long> resultMap = new HashMap<String,Long>();	
		            resultMap.put("seconds", seconds);
		            resultMap.put("minutes", minutes);
		            return resultMap;
			}
		}

	    }
	}
	return null;
    }
    
    /**
     * function that will enable the log by user on current node
     * @param appName
     * @param userName
     */
    public static boolean isLogByUserEnabled(String userName)
    {
	if(userName != null && !userName.isEmpty())
	{
	    return logByUserMap.containsKey(userName);
	}
	return false;
    }
    
    /**
     * function to enable / disable the log by user
     * @param appName
     * @param userName
     * @param enableFlag
     */
    public static void updateLogByUser(String userName, boolean enableFlag)
    {
	if(enableFlag)
	{
	    enableLogByUser(userName);
	}
	else
	{
	    disableLogByUser(userName);
	}
    }
    
    /**
     * function that will enable the log by user on current node
     * @param appName
     * @param userName
     */
    private static void enableLogByUser(String userName)
    {
	if(userName != null && !userName.isEmpty())
	{
	    if(!logByUserMap.containsKey(userName))
	    {
		Log logByUser = new Log(userName);
		
		Level theLev = logByUser.parseLevel("ALL");
		logByUser.defaultFileHandler.setLevel(theLev);
		logByUser.zLogger.setLevel(theLev);
		logByUser.setDefaultLevel(theLev);
		logByUser.levelAllEnabledTime = null;
		logByUser.logByUserEnabledTime = System.currentTimeMillis();
		
		logByUserMap.put(userName,logByUser);
		
		//adding info in user's log and in global log
		logByUser.zLogger.info("Initiating JDK logger for user " + userName);
		if(me != null)
		{
		    logByUser.zLogger.info("Initiating JDK logger for user " + userName);
		}
	    }
	}
    }
    
    /**
     * function that will disable the log by user on current node
     * @param appName
     * @param userName
     */
    private static void disableLogByUser(String userName)
    {
	if(userName != null && !userName.isEmpty())
	{
	    if(logByUserMap.containsKey(userName))
	    {
		
		Log logByUser = logByUserMap.get(userName);
		if(logByUser != null)
		{
    		    //adding info in user's log and in global log
		    logByUser.zLogger.info("Removing JDK logger for user " + userName);
		    if(me != null)
		    {
			me.zLogger.info("Removing JDK logger for user " + userName);
		    }
		    
		    if(logByUser.defaultFileHandler != null)
		    {
			logByUser.zLogger.removeHandler(logByUser.defaultFileHandler);
			logByUser.defaultFileHandler.close();
			logByUser.defaultFileHandler = null;
		    }

		    if(logByUser.cHandler != null)
		    {
			logByUser.zLogger.removeHandler(logByUser.cHandler);
			logByUser.cHandler.close();
			logByUser.cHandler = null;
		    }

		    if(logByUser.xmlFileHandler != null)
		    {
			logByUser.zLogger.removeHandler(logByUser.xmlFileHandler);
			logByUser.xmlFileHandler.close();
			logByUser.xmlFileHandler = null;
		    }

		    logByUser = null;
		    logByUserMap.remove(userName);
		}
	    }
	}
    }
    
    /**
     * This method return an instance for the Log. the Log object is a
     * singleton.
     * 
     * @return Log
     */

    public static Log getInstance()
    {
	if(me == null)
	{
	    synchronized(lock)
	    {
		if(me == null)
		{
		    me = new Log(null);
		}
	    }
	}
	
	changeFileName(me);
	String logByUserKey = ""; //returnLogByUserKey();
	if(logByUserKey != null)
	{	
	    if(me.logByUserMap.containsKey(logByUserKey))
	    {
		changeFileName(me.logByUserMap.get(logByUserKey));
	    }
	}
	
	return me;
    }

    private void insertLogByUser(Level level,String msg, Throwable t)
    {
	String logByUserKey = ""; //returnLogByUserKey();
	if(logByUserKey != null)
	{
	    Log logByUser = logByUserMap.get(logByUserKey);
	    if(logByUser != null)
	    {
		if(level.equals(Level.SEVERE))
		{
		    if(t == null)
		    {
			logByUser.zLogger.severe(msg);
		    }
		    else
		    {
			logByUser.zLogger.log(Level.SEVERE, msg, t);
		    }
		}
		else if(level.equals(Level.WARNING))
		{
		    logByUser.zLogger.warning(msg);
		}
		else if(level.equals(Level.INFO))
		{
		    logByUser.zLogger.info(msg);
		}
		else if(level.equals(Level.FINE))
		{
		    logByUser.zLogger.fine(msg);
		}
		else if(level.equals(Level.FINER))
		{
		    logByUser.zLogger.finer(msg);
		}
		else if(level.equals(Level.FINEST))
		{
		    logByUser.zLogger.finest(msg);
		}
		else if(level.equals(Level.CONFIG))
		{
		    logByUser.zLogger.config(msg);
		}
		
		//automatically close the log by user after a predefined time equal to levelALLInterval
		if(logByUser.logByUserEnabledTime != null && logByUser.levelALLInterval != null 
			&& (System.currentTimeMillis() - logByUser.logByUserEnabledTime) > logByUser.levelALLInterval * 1000)
		{
		    disableLogByUser(logByUserKey);
		}
	    }
	}
    }
    
    /**
     * Echo an ERROR level severity Error.
     * 
     * @param msg
     *            String
     */
    public void error(String msg)
    {
	zLogger.severe(msg);
	insertLogByUser(Level.SEVERE, msg, null);
    }

    /**
     * Logs a message with the stacktrace to the configured loggers.
     * 
     * @param t
     *            Throwable
     * @param msg
     *            String
     */
    public void error(Throwable t, String msg)
    {
	zLogger.log(Level.SEVERE, msg, t);
	insertLogByUser(Level.SEVERE, msg, t);
    }

    /**
     * Logs a WARNING level message.
     * 
     * @param msg
     *            String
     */
    public void warning(String msg)
    {
	zLogger.warning(msg);
	resetLogLevelAll();
	insertLogByUser(Level.WARNING, msg, null);
    }

    /**
     * Logs an INFO level message
     * 
     * @param msg
     *            String
     */
    public void info(String msg)
    {
	zLogger.info(msg);
	resetLogLevelAll();
	insertLogByUser(Level.INFO, msg, null);
    }

    /**
     * Logs a DEBUG level message.
     * 
     * @param msg
     *            String
     */
    public void debug(String msg)
    {
	zLogger.fine(msg);
	resetLogLevelAll();
	insertLogByUser(Level.FINE, msg, null);
    }

    /**
     * Logs the entry of the method. The output log contains the full Class name
     * the method name and a dump to the parameters value.
     * 
     * @param params
     *            Object[]
     */
    public void entering(Object... params)
    {
	if(zLogger.isLoggable(Level.FINER) && isLoggable(Log.METHOD))
	{
	    Throwable t = new Throwable();
	    String cName = null, fName = null;
	    StackTraceElement zCaller = t.getStackTrace()[1];
	    cName = zCaller.getClassName();
	    fName = zCaller.getMethodName();

	    if(params == null || params.length == 0)
	    {
		zLogger.entering(cName, fName);
	    }
	    else
	    {
		if(params.length == 1 && params[0] != null)
		{
		    zLogger.entering(cName, fName, trimLoggedString(params[0].toString()));
		}
		else
		{
		    Object[] trimmedParams = new Object[params.length];
		    Object tmp = null;
		    for(int i = 0; i < params.length; i++)
		    {
			tmp = params[i];
			trimmedParams[i] = (tmp == null) ? null : trimLoggedString(tmp.toString());
		    }
		    zLogger.entering(cName, fName, trimmedParams);
		}
	    }
	    if(isTimeEnabled)
	    {
		String uName = getCallerUName();
		lstTimes.put(uName, Long.valueOf(System.currentTimeMillis()));
	    }
	}
    }

    private String trimLoggedString(String initValue)
    {
	String trimmedStr = null;
	if(initValue.length() > 524288)
	{
	    trimmedStr = initValue.substring(0, 524288);
	}
	else
	{
	    trimmedStr = initValue;
	}
	return trimmedStr;
    }

    /**
     * Logs the Exit of a method; the output contains the full class name, the
     * method and the returned value.
     * 
     * @param params
     *            Object
     */
    public void exiting(Object params)
    {
	if(zLogger.isLoggable(Level.FINER) && isLoggable(Log.METHOD))
	{
	    Throwable t = new Throwable();
	    String cName = null, fName = null;
	    StackTraceElement zCaller = t.getStackTrace()[1];
	    cName = zCaller.getClassName();
	    fName = zCaller.getMethodName();
	    if(params == null)
	    {
		zLogger.exiting(cName, fName);
	    }
	    else
	    {
		zLogger.exiting(cName, fName, trimLoggedString(params.toString()));
	    }
	    if(isTimeEnabled)
	    {
		long eTime = 0;
		String uName = getCallerUName();
		Long tmp = (Long) lstTimes.remove(uName);
		if(tmp == null)
		{
		    eTime = -1;
		}
		else
		{
		    eTime = System.currentTimeMillis() - tmp.longValue();
		}
		zLogger.fine("PERFORMANCE logs for " + cName.substring(cName.lastIndexOf(".") + 1) + "." + fName + ": "
			+ eTime + " ms");
	    }
	}
    }

    /**
     * Logs the lowest level messages.
     * 
     * @param msg
     *            String
     */
    public void trace(String msg)
    {
	zLogger.finest(msg);
	resetLogLevelAll();
	insertLogByUser(Level.FINER, msg, null);
    }

    /**
     * Logs config messages
     * 
     * @param msg
     */
    public void config(String msg)
    {
	zLogger.config(msg);
	resetLogLevelAll();
	insertLogByUser(Level.CONFIG, msg, null);
    }

    /**
     * This method is used to check if a logging level is enable. It can be used
     * in the case of the logging message that the developer construct is time
     * consuming. eg: When constructing the array of arguments for the entering
     * logs.
     * 
     * @param lLevel
     *            int
     * @return boolean
     */

    public boolean isLoggable(int lLevel)
    {
	boolean globalLoggable = isLoggableCommon(zLogger, lLevel);
	String logByUserKey = "";// returnLogByUserKey();
	if(logByUserKey != null && logByUserMap.containsKey(logByUserKey))
	{	
	    //in case log enabled by user we should return true regardless the global log level because the log by user had the level ALL
	    return true;
	}
	return globalLoggable;
    }
    
    
    /**
     * common method is used to check if a logging level is enable. It can be used
     * in the case of the logging message that the developer construct is time
     * consuming. eg: When constructing the array of arguments for the entering
     * logs.
     * 
     * @param lLevel
     *            int
     * @return boolean
     */

    private boolean isLoggableCommon(Logger zLogger, int lLevel)
    {
	switch (lLevel)
	{
	    case METHOD:
		return zLogger.isLoggable(Level.FINER);
	    case TRACE:
		return zLogger.isLoggable(Level.FINEST);
	    case DEBUG:
		return zLogger.isLoggable(Level.FINE);
	    case ERROR:
		return zLogger.isLoggable(Level.SEVERE);
	    case WARNING:
		return zLogger.isLoggable(Level.WARNING);
	    case INFO:
		return zLogger.isLoggable(Level.INFO);
	    case CONFIG:
		return zLogger.isLoggable(Level.CONFIG);
	    default:
		return false;
	}
    }

    /**
     * This function maps from the int constants to the Level object.
     * 
     * @param lLevel
     *            int
     * @return Level
     */
    public Level mapToLevel(int lLevel)
    {
	switch (lLevel)
	{
	    case METHOD:
		return Level.FINER;
	    case TRACE:
		return Level.FINEST;
	    case DEBUG:
		return Level.FINE;
	    case ERROR:
		return Level.SEVERE;
	    case WARNING:
		return Level.WARNING;
	    case INFO:
		return Level.INFO;
	    case CONFIG:
		return Level.CONFIG;
	    case ALL:
		return Level.ALL;
	    default:
		return Level.OFF;
	}
    }



    /**
     * Initialize the Console handler if not already initialized, and set the
     * level of the logging.
     * 
     * @throws SecurityException
     */
    private void mgtConsoleLog() throws SecurityException
    {
	if(cHandler == null)
	{
	    cHandler = new ConsoleHandler();
	    zLogger.addHandler(cHandler);
	    cHandler.setFormatter(new PathFormatter());
	}
	cHandler.setLevel(consoleLevel);
    }

    /**
     * This method tries to get a unique call name from a function in order to
     * be used as a unique token to log the entry and exit of the method. The
     * algorithm used is the concatenation of the classname, the method name and
     * the position of the call on the stack
     * 
     * @return String
     */
    public String getCallerUName()
    {
	int[] stackPos = new int[1];
	StackTraceElement ste = PathFormatter.getCallerClass(stackPos);
	return ste.getClassName() + "@" + ste.getMethodName() + "@" + stackPos[0];
    }

    /**
     * This method decides if we should change the filename of the file where we
     * are logging
     * 
     * @return
     */
    public boolean shouldChangeFileName()
    {
	int today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
	return today != currentDayOfYear;
    }

    /**
     * Returns the java.util.logging.Logger used by the class
     * 
     * @return Logger
     */
    public Logger getLogDriver()
    {
	return zLogger;
    }

    /**
     * This method handles the console and its handler
     * 
     * @return ConsoleHandler
     */
    public ConsoleHandler getConsoleHandler()
    {
	return cHandler;
    }

    /**
     * This method returns the defaultFilehandler used
     * 
     * @return
     */
    public FileHandler getDefaultFileHandler()
    {
	return defaultFileHandler;
    }

    /**
     * This method returns the defaultLevel property
     * 
     * @return
     */
    public Level getDefaultLevel()
    {
	return defaultLevel;
    }

    /**
     * This method returns the consoleLevel property
     * 
     * @return
     */
    public Level getConsoleLevel()
    {
	return consoleLevel;
    }

    /**
     * This method returns the defaultName property
     * 
     * @return
     */
    public String getDefaultName()
    {
	return defaultName;
    }

    /**
     * This method returns the defaultCount property
     * 
     * @return
     */
    public int getDefaultCount()
    {
	return defaultCount;
    }

    /**
     * This method returns the defaultSize property
     * 
     * @return
     */
    public int getDefaultSize()
    {
	return defaultSize;
    }

    /**
     * This method sets the defaultFileHandler property
     * 
     * @param defaultFileHandler
     */
    public void setDefaultFileHandler(FileHandler defaultFileHandler)
    {
	this.defaultFileHandler = defaultFileHandler;
    }

    /**
     * This method sets the defaultLevel property
     * 
     * @param defaultLevel
     */
    public void setDefaultLevel(Level defaultLevel)
    {
	this.defaultLevel = defaultLevel;
    }

    /**
     * This method sets the consoleLevel property
     * 
     * @param consoleLevel
     */
    public void setConsoleLevel(Level consoleLevel)
    {
	this.consoleLevel = consoleLevel;
    }

    /**
     * This method sets the defaultName property
     * 
     * @param defaultName
     */
    public void setDefaultName(String defaultName)
    {
	this.defaultName = defaultName;
    }

    /**
     * This method sets the defaultCount property
     * 
     * @param defaultCount
     */
    public void setDefaultCount(int defaultCount)
    {
	this.defaultCount = defaultCount;
    }

    /**
     * This method sets the defaultSize property
     * 
     * @param defaultSize
     */
    public void setDefaultSize(int defaultSize)
    {
	this.defaultSize = defaultSize;
    }

    /**
     * This method sets the isFilterEnabled property
     * 
     * @param action
     */
    public void enabledFilter(boolean action)
    {
	isFilterEnabled = action;
    }

    /**
     * This method returns the isFilterEnabled property
     * 
     * @return
     */
    public boolean isFilterEnabled()
    {
	return isFilterEnabled;
    }

    /**
     * This method sets the enabledTimer property
     * 
     * @param action
     */
    public void enabledTimer(boolean action)
    {
	isTimeEnabled = action;
    }

    /**
     * This method returns the isTimeEnabled property
     * 
     * @return
     */
    public boolean isTimeEnabled()
    {
	return isTimeEnabled;
    }

    /**
     * This method initializes the lstTimes list to a new collection of hashMaps
     * 
     */
    public void initializeTimeList()
    {
	lstTimes = Collections.synchronizedMap(new HashMap());
    }

    /**
     * This method returns the lstTimes property
     * 
     * @return
     */
    public Map getTimeList()
    {
	return lstTimes;
    }

    /**
     * This method returns the lstFilter property
     * 
     * @return
     */
    public HashMap getFilterList()
    {
	return lstFilter;
    }

    public String getLogSystemDetails()
    {
        return logSystemDetails;
    }

}
