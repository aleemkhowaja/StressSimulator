package com.path.lib.log;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Locale;

public class PathSystemMonitor
{
    public static String monitorSystemIndicators()
    {/*
      * 
      * long getCommittedVirtualMemorySize(): Returns the amount of virtual
      * memory that is guaranteed to be available to the running process in
      * bytes, or -1 if this operation is not supported.
      * 
      * long getFreePhysicalMemorySize(): Returns the amount of free physical
      * memory in bytes.
      * 
      * long getFreeSwapSpaceSize(): Returns the amount of free swap space in
      * bytes.
      * 
      * double getProcessCpuLoad(): Returns the "recent cpu usage" for the Java
      * Virtual Machine process.
      * 
      * long getProcessCpuTime(): Returns the CPU time used by the process on
      * which the Java virtual machine is running in nanoseconds. The returned
      * value is of nanoseconds precision but not necessarily nanoseconds
      * accuracy. This method returns -1 if the the platform does not support
      * this operation
      * 
      * double getSystemCpuLoad(): Returns the "recent cpu usage" for the whole
      * system. This value is a double in the [0.0,1.0] interval. A value of 0.0
      * means that all CPUs were idle during the recent period of time observed,
      * while a value of 1.0 means that all CPUs were actively running 100% of
      * the time during the recent period being observed. All values betweens
      * 0.0 and 1.0 are possible depending of the activities going on in the
      * system. If the system recent cpu usage is not available, the method
      * returns a negative value.
      * 
      * long getTotalPhysicalMemorySize(): Returns the total amount of physical
      * memory in bytes.
      * 
      * long getTotalSwapSpaceSize(): Returns the total amount of swap space in
      * bytes.
      */
	StringBuffer monitorDetails = new StringBuffer();
	if("1".equals(Log.getInstance().getLogSystemDetails()))
	{
	    monitorDetails.append("System Indicators [\r\n");
	    monitorDetails.append("OS Name = " + System.getProperty("os.name") + " Version = "+System.getProperty("os.name") + " Arch="+System.getProperty("os.arch") +"\r\n");
	    monitorDetails.append("Java Version = " + System.getProperty("java.version") + "\r\n");
	    OperatingSystemMXBean operatingSystemMXBean;
	    try
	    {
		operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		int mb = 1024 * 1024;
		for(Method method : operatingSystemMXBean.getClass().getDeclaredMethods())
		{
		    method.setAccessible(true);
		    String methodName = method.getName();
		    if(methodName.startsWith("get") && Modifier.isPublic(method.getModifiers()))
		    {
			methodName = methodName.substring(3);
			Object value, origValue = null;
			try
			{
			    value = method.invoke(operatingSystemMXBean);
			    origValue = value;
			    if(value instanceof Number)
			    {
				if(methodName.toLowerCase(Locale.ENGLISH).contains("size"))
				{
				    value = ((Long) value / mb) + " MB";
				    origValue = origValue + " bytes";
				}
				else if(methodName.toLowerCase(Locale.ENGLISH).contains("time"))
				{
				    double timeinSec = ((Number) value).doubleValue() / 1000000000;
				    value = BigDecimal.valueOf(timeinSec).toPlainString() + " sec";
				    origValue = origValue + " nanoseconds";
				}
				else if(methodName.toLowerCase(Locale.ENGLISH).contains("load"))
				{
				    long perceUsage = BigDecimal.valueOf(((Number) value).doubleValue() * 100)
					    .longValue();
				    if(perceUsage < 0)
				    {
					value = "Usage is not available";
				    }
				    else
				    {
					value = BigDecimal.valueOf(perceUsage).toPlainString() + " %";
					origValue = origValue + " range 0-1";
				    }
				}
			    }

			}
			catch(Exception e)
			{
			    value = e;
			} // try
			monitorDetails.append(methodName + " = " + value + " (" + origValue + ")\r\n");
		    } // if
		}
	    }
	    catch(Exception e)
	    {
		System.out.println("Error in PathSystemMonitor, please check with Path Solutions R&D Team.");
		e.printStackTrace();
	    } 
	    monitorDetails.append("]\r\n");
	    operatingSystemMXBean = null;
	}
	return monitorDetails.toString();
    }
}
