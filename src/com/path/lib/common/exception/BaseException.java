package com.path.lib.common.exception;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
*
* <p>Title: PathCommon Library
*
* <p>Checked exception that is used to throw other exception
* This class extends java.lang.Exception.Exception
*
* <p>Copyright: Copyright (c) 2010
*
* <p>Company: Pathsolutions
*
*/
public class BaseException extends Exception implements Serializable
{
		/**
		 * Constructor for PathSolException class
		 *
		 */
 public BaseException()
 {
  super();
 }
 /**
  * Construct an exception when provided with a message
  * @param message String
  */
 public BaseException(String message, Integer msgType)
 {
     super(message);
     this.msgType = msgType;
 }
/**
 * Construct an exception when provided with a message
 * @param message String
 */
 public BaseException(String message)
 {
  super(message);
 }
 /**
  * Construct an exception when provided with a message
  * @param message String
  */
 public BaseException(String message,Object retValue)
 {
     super(message);
     this.setRetValue(retValue);
 }
 /**
  * Construct an exception when provided with a message, original cause and return object
  * @param cause Throwable original Exception
  * @param message String
  */
 public BaseException(Throwable cause,String message,Object retValue)
 {
     super(message,cause);
     this.setRetValue(retValue);
 }
/**
 * Construct an exception when provided with a Throwable
 * @param cause Throwable
 */
 public BaseException(Throwable cause)
 {
  super(cause);
 }
 /**
  * Construct an exception message with parameters
  * @param message String
  * @param params String[]
  */
  public BaseException(String message, String... params)
  {
   super(message);
   this.params = params;	  
  }

/**
 * Construct an exception when provided with both a String and a Throwable
 * @param message String
 * @param cause Throwable
 */
 public BaseException(String message, Throwable cause)
 {
  super(message, cause);
 }
 
 /**
  * Construct an exception when provided with both a String and a Throwable
  * @param message String
  * @param cause Throwable
  */
  public BaseException(Integer errorCode)
  {
	 super("Message To be Retrieved from CTS Messages"+errorCode);
     this.setErrorCode(errorCode);	
  }
 /**
  * Construct an exception when provided with both a error Code and a Throwable
  * @param cause
  * @param errorCode
  */
  public BaseException(Throwable cause, Integer errorCode)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode,cause);
      this.setErrorCode(errorCode);
  }
  /**
   * Construct an exception when provided with both a error Code and a REturn Value
   * @param messageCode Integer
   * @param returnValue custom return Object
   * @param cause Throwable
   */
  public BaseException(Integer errorCode, Object returnValue)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode);
      this.setErrorCode(errorCode);	
      this.setRetValue(returnValue);
  }
  
  /**
   * Construct an exception when provided with both a code and Message Identifier and a Throwable
   * @param errorCode error code from CTSMESSAGE
   * @param msgIdent msg identifier (developer technical custom String)
   * @param cause Throwable
   */
   public BaseException(Integer errorCode, String msgIdent)
   {
      super("Message To be Retrieved from CTS Messages"+errorCode);
      this.setErrorCode(errorCode);
      this.setMsgIdent(msgIdent);
   }
   /**
    * Construct an exception when provided with both a code and Message Identifier and a Throwable
    * @param errorCode error code from CTSMESSAGE
    * @param msgIdent msg identifier (developer technical custom String)
    * @param returnValue custom return Object
    * @param cause Throwable
    */
   public BaseException(Integer errorCode, String msgIdent, Object returnValue)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode);
       this.setErrorCode(errorCode);
       this.setMsgIdent(msgIdent);
       this.setRetValue(returnValue);
   }
  
  /**
   * constructs exception with Error code and Message type
   * @param errorCode error code from CTSMESSAGE
   * @param msgType msg type code (error or confirm ...)
   */
  public BaseException(Integer errorCode, Integer msgType)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode+ "  MsgType "+msgType);
      this.setErrorCode(errorCode);	
      this.setMsgType(msgType);
  }
  /**
   * constructs exception with Error code and Message type
   * @param errorCode error code from CTSMESSAGE
   * @param msgType msg type code (error or confirm ...)
   * @param returnValue Object return Object with the Exception
   */
  public BaseException(Integer errorCode, Integer msgType, Object returnValue)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode+ "  MsgType "+msgType);
      this.setErrorCode(errorCode);	
      this.setMsgType(msgType);
      this.setRetValue(returnValue);
  }
  
  /**
   * constructs exception with Error code and Message type
   * @param errorCode error code from CTSMESSAGE
   * @param msgType msg type code (error or confirm ...)
   * @param msgIdent msg identifier (developer technical custom String)
   */
  public BaseException(Integer errorCode, Integer msgType, String msgIdent)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode+ "  MsgType "+msgType+ "  msgIdent "+msgIdent);
      this.setErrorCode(errorCode);	
      this.setMsgType(msgType);
      this.setMsgIdent(msgIdent);
  }
  /**
   * constructs exception with Error code and Message type
   * @param errorCode error code from CTSMESSAGE
   * @param msgType msg type code (error or confirm ...)
   * @param returnValue custom return Object
   * @param msgIdent msg identifier (developer technical custom String)
   */
  public BaseException(Integer errorCode, Integer msgType, String msgIdent,Object returnValue)
  {
      super("Message To be Retrieved from CTS Messages"+errorCode+ "  MsgType "+msgType+ "  msgIdent "+msgIdent);
      this.setErrorCode(errorCode);	
      this.setMsgType(msgType);
      this.setMsgIdent(msgIdent);
      this.setRetValue(returnValue);
  }
  
  /**
   * Construct a BO throwable exception by error code
   * @param errorCode Integer error code to be read from databse
   * @param params String[] parameters of the message
   */
   public BaseException(Integer errorCode, String... params)
   {
	   super("Message To be Retrieved from CTS Messages"+errorCode+" with Params");
	   this.errorCode = errorCode;
	   this.params = params; 
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    * @param returnValue Object return Object with the Exception
    */
   public BaseException(Integer errorCode, String[] params, Object returnValue)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params");
       this.errorCode = errorCode;
       this.params = params; 
       this.retValue = returnValue;
   }
   /**
    * Construct a BO throwable exception by error code
    * @param cause Throwable original cause of exception
    * @param errorCode Integer error code to be read from database
    * @param params String[] parameters of the message
    * @param returnValue Object return Object with the Exception
    */
   public BaseException(Throwable cause, Integer errorCode, String[] params, Object returnValue)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params",cause);
       this.errorCode = errorCode;
       this.params = params; 
       this.retValue = returnValue;
   }
   /**
    * Construct an exception message with parameters
    * @param message String
    * @param params String[]
    * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
    */
   public BaseException(Integer errorCode, String[] params,Boolean concatParams)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params concatParams "+concatParams);
       this.errorCode = errorCode;
       this.params = params;
       this.concatParams = concatParams;
   }
   /**
    * Construct an exception message with parameters
    * @param e Throwable original cause exception
    * @param message String
    * @param params String[]
    * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
    */
   public BaseException(Throwable e,Integer errorCode, String[] params,Boolean concatParams)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params concatParams "+concatParams,e);
       this.errorCode = errorCode;
       this.params = params;
       this.concatParams = concatParams;
   }
   /**
    * Construct an exception message with parameters
    * @param message String
    * @param params String[]
    * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
    * @param msgIdent msg identifier (developer technical custom String)
    */
   public BaseException(Integer errorCode, String[] params,Boolean concatParams, String msgIdent)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params  concatParams "+concatParams+"  msgIdent "+msgIdent);
       this.errorCode = errorCode;
       this.params = params;
       this.concatParams = concatParams;
       this.msgIdent = msgIdent;
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    * @param msgType  msg type code (error or confirm ...)
    */
   public BaseException(Integer errorCode, String[] params, Integer msgType)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params");
       this.errorCode = errorCode;
       this.params = params; 
       this.msgType = msgType;
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    * @param msgType  msg type code (error or confirm ...)
    * @param returnValue Object return Object with the Exception
    */
   public BaseException(Integer errorCode, String[] params, Integer msgType, Object returnValue)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params");
       this.errorCode = errorCode;
       this.params = params; 
       this.msgType = msgType;
       this.retValue = returnValue;
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    * @param msgType  msg type code (error or confirm ...)
    * @param msgIdent msg identifier (developer technical custom String)
    */
   public BaseException(Integer errorCode, String[] params, Integer msgType, String msgIdent)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params  msgIdent "+msgIdent);
       this.errorCode = errorCode;
       this.params = params; 
       this.msgType = msgType;
       this.msgIdent = msgIdent;
   }
   /**
    * Construct a throwable exception by error code , parameters, concatenation and type and identifier
    * @param errorCode Integer error code to be read from database
    * @param params String[] parameters of the message
    * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
    * @param msgType type of message (error, confirm, ...)
    * @param msgIdent identifier fro a messge to distinguish for which area it is related
    */
   public BaseException(Integer errorCode, String[] params,Boolean concatParams,Integer msgType, String msgIdent)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params  concatParams "+concatParams+"  msgIdent "+msgIdent);
       this.errorCode = errorCode;
       this.params = params;
       this.concatParams = concatParams;
       this.msgIdent = msgIdent;
       this.msgType = msgType;
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    * @param msgType  msg type code (error or confirm ...)
    * @param msgIdent msg identifier (developer technical custom String)
    * @param returnValue custom return Object
    */
   public BaseException(Integer errorCode, String[] params, Integer msgType, String msgIdent, Object returnValue)
   {
       super("Message To be Retrieved from CTS Messages"+errorCode+" with Params  msgIdent "+msgIdent);
       this.errorCode = errorCode;
       this.params = params; 
       this.msgType = msgType;
       this.msgIdent = msgIdent;
       this.retValue = returnValue;
   }
   /**
    * Construct a BO throwable exception by List of Exceptions
    * @param exceptionsLst list of Exceptions
    */
   public BaseException(ArrayList<BaseException> exceptionsLst)
   {
       this.exceptionsLst = exceptionsLst;
   }
   /**
    * Construct a BO throwable exception by List of Exceptions
    * @param exceptionsLst list of Exceptions
    * @param msgIdent msg identifier (developer technical custom String)
    */
   public BaseException(ArrayList<BaseException> exceptionsLst, String msgIdent)
   {
       this.exceptionsLst = exceptionsLst;
       this.msgIdent = msgIdent;
   }
   /**
    * Construct a BO throwable exception by List of Exceptions
    * @param exceptionsLst list of Exceptions
    * @param msgType  msg type code (error or confirm ...)
    */
   public BaseException(ArrayList<BaseException> exceptionsLst, Integer msgType)
   {
       this.exceptionsLst = exceptionsLst;
       this.msgType = msgType;
   }
   /**
    * Construct a BO throwable exception by List of Exceptions
    * @param exceptionsLst list of Exceptions
    * @param msgType  msg type code (error or confirm ...)
    * @param msgIdent msg identifier (developer technical custom String)
    */
   public BaseException(ArrayList<BaseException> exceptionsLst, Integer msgType, String msgIdent)
   {
       this.exceptionsLst = exceptionsLst;
       this.msgType = msgType;
       this.msgIdent = msgIdent;
   }

/**
 * variable that will hold the errorCode of the message
 */ 
private Integer errorCode;
private String[] params;
private Integer msgType;
private Object retValue;
/**
 * User Name to whom the message is related to
 */
private String msgUsr;
/**
 * Screen to which the message is related to
 */
private String msgProgRef;
/**
 * Company Code to which the message is related to
 */
private BigDecimal msgCompCode;
/**
 * Branch Code to which the message is related to
 */
private BigDecimal msgBranchCode;
/**
 * Application Name to which the message is related to
 */
private String msgAppName;
/**
 *  Identifier for the message which can distinguish one BO Exception from another 
 *  Useful in confirmation message
 */
private String msgIdent;//
/**
 * variable to initialise the title key of the BO Exception in case it is not constructed from errorCode.
 */
private String msgTitleKey;

public String getMsgIdent() {
	return msgIdent;
}
public void setMsgIdent(String msgIdent) {
	this.msgIdent = msgIdent;
}

private ArrayList<BaseException> exceptionsLst;
private boolean concatParams;

public Integer getErrorCode()
{
	return errorCode;
}
public void setErrorCode(Integer errorCode)
{
	this.errorCode = errorCode;
}
public String[] getParams()
{
	return params;
}
public Integer getMsgType()
{
    return msgType;
}
public void setMsgType(Integer msgType)
{
    this.msgType = msgType;
}
public ArrayList<BaseException> getExceptionsLst()
{
    return exceptionsLst;
}
public void setExceptionsLst(ArrayList<BaseException> exceptionsLst)
{
    this.exceptionsLst = exceptionsLst;
}
public boolean isConcatParams()
{
    return concatParams;
}
public void setConcatParams(boolean concatParams)
{
    this.concatParams = concatParams;
}
public Object getRetValue()
{
    return retValue;
}
public void setRetValue(Object retValue)
{
    this.retValue = retValue;
}
public String getMsgTitleKey()
{
    return msgTitleKey;
}
public void setMsgTitleKey(String msgTitleKey)
{
    this.msgTitleKey = msgTitleKey;
}

public String getMsgUsr()
{
    return msgUsr;
}
public void setMsgUsr(String msgUsr)
{
    this.msgUsr = msgUsr;
}

public String getMsgProgRef()
{
    return msgProgRef;
}
public void setMsgProgRef(String msgProgRef)
{
    this.msgProgRef = msgProgRef;
}

public BigDecimal getMsgCompCode()
{
    return msgCompCode;
}
public void setMsgCompCode(BigDecimal msgCompCode)
{
    this.msgCompCode = msgCompCode;
}
public BigDecimal getMsgBranchCode()
{
    return msgBranchCode;
}
public void setMsgBranchCode(BigDecimal msgBranchCode)
{
    this.msgBranchCode = msgBranchCode;
}

public String getMsgAppName()
{
    return msgAppName;
}
public void setMsgAppName(String msgAppName)
{
    this.msgAppName = msgAppName;
}
}
