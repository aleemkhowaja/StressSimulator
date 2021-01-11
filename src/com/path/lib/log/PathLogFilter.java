package com.path.lib.log;

import java.util.HashMap;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class PathLogFilter implements Filter
{
    HashMap lstFilter;
    boolean isFilterEnabled;
    public PathLogFilter( HashMap lstFilter, boolean isFilterEnabled)
    {
	this.lstFilter = lstFilter;
	this.isFilterEnabled = isFilterEnabled;
    }

    /**
     * This method filter the logging based on the longest package till the
     * smallest; I means first it looks to see if a rule is defined @ the
     * level com.path.fullpath then iterats till the first path in order to
     * stop it.
     * @param LogRecord lg
     * @return boolean
     */
    public boolean isLoggable(LogRecord lg)
    {
	if(!isFilterEnabled)
	{
	    return true;
	}
	StackTraceElement ste = PathFormatter.getCallerClass(null);

	String cPath = ste.getClassName();

	boolean isLoggable = true;
	int recLevel = lg.getLevel().intValue();
	if(recLevel != Log.ERROR_LEVEL)
	{
	    Level filterLevel = (Level) lstFilter.get(cPath);

	    // get the log level associated with the finest package.
	    while(filterLevel == null)
	    {
		int dotIdx = cPath.lastIndexOf(".");
		if(dotIdx == -1)
		{
		    break;
		}
		else
		{
		    cPath = cPath.substring(0, dotIdx);
		}
		filterLevel = (Level) lstFilter.get(cPath);
	    }
	    // if no logging def found so this means no logging is defined.
	    if(filterLevel == null)
	    {
		isLoggable = true;
	    }
	    else
	    {
		if(recLevel < filterLevel.intValue())
		{
		    isLoggable = false;
		}
	    }
	}
	return isLoggable;
    }
}
