package com.path.struts2.lib.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.path.bo.common.ConstantsCommon;


public class BaseObject implements Serializable
{
    private static final long serialVersionUID = 1L;
    /**
     * default constructor to initialise some audit properties
     */
    public BaseObject()
    {
    }
    private String traceUserId;
    private String traceProgRef;
    private String traceAppName;
    // Http Session Id used to link the session to SQL session
    private String httpSessionIdForLink;
   
    public String getHttpSessionIdForLink()
    {
        return httpSessionIdForLink;
    }
    private int isOracle;
    private int isSybase;
    private int isSQLServer;
    private BigDecimal bpmTaskId;
    private String bpmUserName;
    private String bpmUserPass;
    private transient HashMap<String,List<String>> maskParamsResult;

    /**
    * @author Alim Khowaja TP 863259 
    * checked the action request required connection for other database. values to be send e.g true/false 
    */
    private Boolean useConnection;
    
    //exclude the below properties from json serialization in response
   
    public int getIsOracle()
    {
	return this.isOracle;
    }
   
    public int getIsSybase()
    {
	return this.isSybase;
    }

    public void setIsOracle(int isOracle)
    {
	this.isOracle = isOracle;
    }

    public void setIsSybase(int isSybase)
    {
	this.isSybase = isSybase;
    }

    
    public BigDecimal getEmptyBigDecimalValue()
    {
	return ConstantsCommon.EMPTY_BIGDECIMAL_VALUE;
    }
   
    public String getTraceUserId()
    {
        return traceUserId;
    }

   
    public String getTraceProgRef()
    {
        return traceProgRef;
    }
   
    public String getTraceAppName()
    {
        return traceAppName;
    }
    /**
     * method to set the private attributes for tracing, in case called from code
     * @param appName application Name
     * @param userName User Id performing the operation
     * @param progRef Screen Reference
     */
    public void applyTraceProps(String appName, String userName, String progRef)
    {
	// check if trace enabled or HTTP trace enabled
	if(ConstantsCommon.SQL_SESSION_TRACE_CODE || ConstantsCommon.SQL_SESSION_HTTP_TRACE_CODE)
	{
	    forceApplyTraceProps(appName, userName, progRef, null);
	}
    }
    /**
     method to set the private attributes for tracing, in case called from code and httpSession Id
     * @param appName application Name
     * @param userName User Id performing the operation
     * @param progRef Screen Reference
     * @param httpSessionId Http Session Id
     */
    public void applyTraceProps(String appName, String userName, String progRef, String httpSessionId)
    {
	applyTraceProps(appName, userName, progRef);
	
	this.httpSessionIdForLink = httpSessionId;
    }
    /**
     * method to forces setting the private attributes for tracing, in case called from code
     * @param appName application Name
     * @param userName User Id performing the operation
     * @param progRef Screen Reference
     * @param httpsessionId User Http Session ID
     */
    public void forceApplyTraceProps(String appName, String userName, String progRef, String httpSessionId)
    {
	    this.traceAppName = appName;
	    this.traceUserId = userName;
	    this.traceProgRef = progRef;
	    
	    this.httpSessionIdForLink = httpSessionId;
    }
    
   
    public BigDecimal getBpmTaskId()
    {
        return bpmTaskId;
    }
    public void setBpmTaskId(BigDecimal bpmTaskId)
    {
	this.bpmTaskId = bpmTaskId;
    }
   
    public String getBpmUserName()
    {
        return bpmUserName;
    }
    public void setBpmUserName(String bpmUserName)
    {
        this.bpmUserName = bpmUserName;
    }
   
    public String getBpmUserPass()
    {
        return bpmUserPass;
    }
    public void setBpmUserPass(String bpmUserPass)
    {
        this.bpmUserPass = bpmUserPass;
    }
   
    public int getIsSQLServer()
    {
        return isSQLServer;
    }
    public void setIsSQLServer(int isSQLServer)
    {
        this.isSQLServer = isSQLServer;
    }
   
    public HashMap<String, List<String>> getMaskParamsResult()
    {
        return maskParamsResult;
    }
    /**
     * Method to specify which Fields/Columns to be masked in result of SQL in the Log Files upon log Level ALL
     * @param listOfFieldsToMask String Array of Field Names to be Masked
     */
   
    public void applyMaskResult(String[] listOfFieldsToMask)
    {
	if(listOfFieldsToMask != null)
	{
	    if(maskParamsResult == null)
	    {
		maskParamsResult = new HashMap<String, List<String>>();
	    }
	    maskParamsResult.put("resultMask", Arrays.asList(listOfFieldsToMask));
	}
    }
    
    /**
     * Method to specify which Params to be masked in input of SQL in the Log Files upon log Level ALL
     * @param listOfPramsOrderToMask String Array of Order/Property Names of Parameters to be Masked,
     * if same parameter include twice then all orders need to be mentioned to be Masked, otherwise mentioned the property Name
     * Example new String[]{"1","2","4"}, new String[]{"userId","compCode","4"}
     * first parameter has order "1"
     */
   
    public void applyMaskParams(String[] listOfPramsOrderToMask)
    {
	if(listOfPramsOrderToMask != null)
	{
	    if(maskParamsResult == null)
	    {
		maskParamsResult = new HashMap<String, List<String>>();
	    }
	    // need to convert to ArrayList to be able to modify inside mypatis for related order
	    ArrayList<String> theList = new ArrayList<String>(Arrays.asList(listOfPramsOrderToMask));
	    maskParamsResult.put("paramMask",theList);
	}
    }
   
    public Boolean getUseConnection()
    {
        return useConnection;
    }
    public void setUseConnection(Boolean useConnection)
    {
        this.useConnection = useConnection;
    }
  }
