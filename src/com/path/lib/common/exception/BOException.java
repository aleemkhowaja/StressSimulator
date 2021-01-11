package com.path.lib.common.exception;

import java.util.ArrayList;

public class BOException
 extends BaseException
{
/**
 * Construct a BO exception
 *
 */
 public BOException()
 {
  super();
 }
 /**
  * Construct a BO exception message
  * @param message String
  */
 public BOException(String message, Integer msgType)
 {
     super(message,msgType);
 }
/**
 * Construct a BO exception message
 * @param message String
 */
 public BOException(String message)
 {
  super(message);
 }
 /**
  * Construct a BO exception message
  * @param message String
  */
 public BOException(String message,Object retValue)
 {
     super(message,retValue);
 }
 /**
  * Construct a BO exception message
  * @param cause Throwable original Exception
  * @param message String
  */
 public BOException(Throwable cause, String message,Object retValue)
 {
     super(cause,message,retValue);
 }
/**
 * Construct a BO throwable exception
 * @param cause Throwable
 */
 public BOException(Throwable cause)
 {
  super(cause);
 }
/**
 * Construct a BO throwable exception message
 * @param message String
 * @param cause Throwable
 */
 public BOException(String message, Throwable cause)
 {
  super(message, cause);
 }
 
 /**
  * Construct a BO throwable exception message with parameters
  * @param message String
  * @param params String[]
  */
  public BOException(String message, String... params)
  {
   super(message, params);
  }
  
  /**
   * Construct a BO throwable exception by error code
   * @param errorCode Integer
   */
   public BOException(Integer errorCode)
   {
     super(errorCode);
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer
    * @param retValue Object return Object with the Exception
    */
   public BOException(Integer errorCode, Object retValue)
   {
       super(errorCode,retValue);
   }
  /**
   * Construct a BO throwable exception by error code and original Exception
   * @param cause original exception
   * @param errorCode error code
   */
   public BOException(Throwable cause, Integer errorCode)
   {
       super(cause,errorCode);
   }
   /**
    * 
    * @param exceptionsLst
    * @param msgType
    */
   public BOException(ArrayList<BaseException> exceptionsLst, Integer msgType)
   {
       super(exceptionsLst,msgType);
   }
   /**
    *  Construct a BO throwable exception by Exception List
    * @param exceptionsLst
    * @param msgType
    * @param msgIdent
    */
   public BOException(ArrayList<BaseException> exceptionsLst, Integer msgType, String msgIdent)
   {
       super(exceptionsLst,msgType,msgIdent);
   }
   /**
    * 
    * @param exceptionsLst
    */
   public BOException(ArrayList<BaseException> exceptionsLst)
   {
       super(exceptionsLst);
   }
   /**
    * Construct a BO throwable exception by Exception List
    * @param exceptionsLst
    * @param msgIdent
    */
   public BOException(ArrayList<BaseException> exceptionsLst, String msgIdent)
   {
       super(exceptionsLst,msgIdent);
   }
   
   /**
    * Construct an exception when provided with both a code and Message Identifier and a Throwable
    * @param errorCode error code from CTSMESSAGE
    * @param msgIdent msg identifier (developer technical custom String)
    * @param cause Throwable
    */
    public BOException(Integer errorCode, String msgIdent)
    {
       this.setErrorCode(errorCode);
       this.setMsgIdent(msgIdent);
    }
    /**
     * Construct an exception when provided with both a code and Message Identifier and a Throwable
     * @param errorCode error code from CTSMESSAGE
     * @param msgIdent msg identifier (developer technical custom String)
     * @param retValue Object return Object with the Exception
     */
    public BOException(Integer errorCode, String msgIdent, Object retValue)
    {
	this.setErrorCode(errorCode);
	this.setMsgIdent(msgIdent);
	this.setRetValue(retValue);
    }
   
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer
    * @param msgType Integer type of message (error, confirm, ...)
    */
   public BOException(Integer errorCode, Integer msgType)
   {
       super(errorCode,msgType);
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer
    * @param msgType Integer type of message (error, confirm, ...)
    * @param retValue Object return Object with the Exception
    */
   public BOException(Integer errorCode, Integer msgType, Object retValue)
   {
       super(errorCode,msgType,retValue);
   }
   
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer
    * @param msgType Integer type of message (error, confirm, ...)
    * @param msgIdent
    */
   public BOException(Integer errorCode, Integer msgType, String msgIdent)
   {
       super(errorCode,msgType, msgIdent);
   }
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer
    * @param msgType Integer type of message (error, confirm, ...)
    * @param msgIdent
    * @param retValue Object return Object with the Exception
    */
   public BOException(Integer errorCode, Integer msgType, String msgIdent, Object retValue)
   {
       super(errorCode,msgType, msgIdent,retValue);
   }
   
   /**
    * Construct a BO throwable exception by error code
    * @param errorCode Integer error code to be read from databse
    * @param params String[] parameters of the message
    */
    public BOException(Integer errorCode, String... params)
    {
      super(errorCode,params);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from databse
     * @param params String[] parameters of the message
     * @param retValue Object return Object with the Exception
     */
    public BOException(Integer errorCode, String[] params, Object retValue)
    {
	super(errorCode,params,retValue);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param cause Throwable original exception cause if any
     * @param errorCode Integer error code to be read from database
     * @param params String[] parameters of the message
     * @param retValue Object return Object with the Exception
     */
    public BOException(Throwable cause, Integer errorCode, String[] params, Object retValue)
    {
	super(cause,errorCode,params,retValue);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from database
     * @param params String[] parameters of the message
     * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
     */
    public BOException(Integer errorCode, String[] params,Boolean concatParams)
    {
      super(errorCode,params, concatParams); 
    }
    /**
     * Construct a BO throwable exception by error code
     * @param cause Original Exception
     * @param errorCode Integer error code to be read from database
     * @param params String[] parameters of the message
     * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
     */
    public BOException(Throwable cause, Integer errorCode, String[] params,Boolean concatParams)
    {
	super(cause,errorCode,params, concatParams); 
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from database
     * @param params String[] parameters of the message
     * @param concatParams Boolean specify whether to Concatenate Parameters in one Parameter values
     * @param msgType type of message (error, confirm, ...)
     * @param msgIdent identifier fro a messge to distinguish for which area it is related
     */
    public BOException(Integer errorCode, String[] params,Boolean concatParams,Integer msgType, String msgIdent)
    {
      super(errorCode,params,concatParams,msgType,msgIdent);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from databse
     * @param params String[] parameters of the message
     * @param msgType type of message (error, confirm, ...)
     */
    public BOException(Integer errorCode, String[] params, Integer msgType)
    {
	super(errorCode,params,msgType);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from databse
     * @param params String[] parameters of the message
     * @param msgType type of message (error, confirm, ...)
     * @param retValue Object return Object with the Exception
     */
    public BOException(Integer errorCode, String[] params, Integer msgType, Object retValue)
    {
	super(errorCode,params,msgType,retValue);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from databse
     * @param params String[] parameters of the message
     * @param msgType type of message (error, confirm, ...)
     * @param msgIdent
     */
    public BOException(Integer errorCode, String[] params, Integer msgType, String msgIdent)
    {
    	super(errorCode,params,msgType,msgIdent);
    }
    /**
     * Construct a BO throwable exception by error code
     * @param errorCode Integer error code to be read from databse
     * @param params String[] parameters of the message
     * @param msgType type of message (error, confirm, ...)
     * @param msgIdent
     * @param retValue Object return Object with the Exception
     */
    public BOException(Integer errorCode, String[] params, Integer msgType, String msgIdent, Object retValue)
    {
	super(errorCode,params,msgType,msgIdent,retValue);
    }
}
