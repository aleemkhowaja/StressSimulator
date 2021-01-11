package com.path.lib.common.exception;


public class DAOException
 extends BaseException
{
/**
 * Construct an excpetion for DAO classes
 *
 */
 public DAOException()
 {
  super();
 }
/**
 * Construct an exception message for DAO classes
 * @param message String
 */
 public DAOException(String message)
 {
  super(message);
 }
/**
 * Construct a throwable exception for DAO classes
 * @param cause Throwable
 */
 public DAOException(Throwable cause)
 {
  super(cause);
 }
/**
 * Construct a throwable exception message for DAO classes
 * @param message String
 * @param cause Throwable
 */
 public DAOException(String message, Throwable cause)
 {
  super(message, cause);
 }
 /**
  * Construct a DAO throwable exception message with parameters
  * @param message String
  * @param params String[]
  */
  public DAOException(String message, String... params)
  {
   super(message, params);
  }
 /**
  * Construct a DAO throwable exception by error code
  * @param errorCode Integer
  */
  public DAOException(Integer errorCode)
  {
    super(errorCode);
  }
}
