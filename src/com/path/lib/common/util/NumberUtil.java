package com.path.lib.common.util;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import com.path.bo.common.ConstantsCommon;
import com.path.lib.common.exception.BaseException;
import com.path.lib.log.Log;

/**
 * 
 * Copyright 2012, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: DeniskHaddad
 *
 * NumberUtil.java used to  manipulate number objects
 */
public final class NumberUtil
{
    /**
     * private constructor to prevent the class being instantiated since all
     * methods are static
     */
    private NumberUtil()
    {
	Log.getInstance().error("This Class is utility class cannot be instantiated");
    }
 private static final Log log = Log.getInstance();
 public volatile static HashMap<String,List<String>> objectBigDecimalPropNames = new HashMap<String, List<String>>();
 /**
  * check if is number and Ratio (between zero and 100)
  * @param val
  * @return boolean
  */
 public static boolean isNumberRatio(Object val) throws BaseException
 {
 	if(val instanceof Number && (NumberUtil.toDouble(val) >= 0 && NumberUtil.toDouble(val) <= 100))
 	{
 	    return true;
 	}
 	return false;
 }
 
 
 /**
  * Method Used to return the format given Number of Decimal digits
  * Powerbuilder function:  f_currency_mask
  * @param cyDec number of digits after decimal
  * @return
  */
 public static String currencyMask(BigDecimal cyDec) throws BaseException
 {
 String theFormat = "#,##0";
 if(NumberUtil.nullToZero(cyDec).intValue() > 0)
 {
     theFormat = theFormat.concat(".");
     String nbDecimalZeros = fill("0", cyDec);
     theFormat = theFormat.concat(nbDecimalZeros);
 }
 return theFormat;
 }
   
 /**
  * corresponds to PB fill function that return a what String with nbTimes
  * 
  * @param what what to return
  * @param nbTimes how many times concatenated
  * @return
  */
 public static String fill(String what, BigDecimal nbTimes)
 {

     if(nbTimes == null || what == null)
     {
	 return what;
     }
     
     StringBuffer toRet = new StringBuffer();
     for(int i = 0; i < nbTimes.intValue(); i++)
     {
 	toRet.append(what);
     }
     // substring if constructed result length larger than nbTimes provided
     if(toRet.length() > nbTimes.intValue() && nbTimes.intValue() >= 0)
     {
	 toRet.setLength(nbTimes.intValue());
     }
     return toRet.toString();
 }

     /**
      * @param amount = Amount to be validated
      * @param minAmount = Minimum amount of the range
      * @param maxAmount = Maximum amount of the range
      * @param includeEqualInValidation = Flag to consider Min and Max amount in
      *            the condition if the flag is TRUE checking as follows ( amount
      *            >= minAmount || amount <= MaxAmount ) if the flag is FALSE
      *            checking as follows ( amount > minAmount || amount < MaxAmount
      *            )
      * @return
      */
     public static boolean checkValueBetween(BigDecimal amount, BigDecimal minAmount, BigDecimal maxAmount,
 	    boolean includeEqualInValidation)
     {
 	if(includeEqualInValidation && amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <=0 )
 	{
 	    return true;
 	}
 	else
 	if(!includeEqualInValidation && amount.compareTo(minAmount) > 0 && amount.compareTo(maxAmount) <0)
 	{
 		return true;
 	}
 	return false;
     }
 /**
  * Replaces the null with the zero
  */
 public static String nullToZero(String s)
 {
  if(s == null || s.equals("null") || s.trim().length() == 0)
  {
   return "0";
  }
  else
  {
   return s;
  }
 }

 /**
  * Replaces the null by Zero when provided with a BigDecimal
  * @param b BigDecimal
  * @return BigDecimal
  */
 public static BigDecimal nullToZero(BigDecimal b)
 {
  if(isEmptyDecimal(b))
  {
   return BigDecimal.ZERO;
  }
  else
  {
   return b;
  }
 }
 /**
  * 
  * Used for replacing the null by Given Big Decima
  * 
  * @param b BigDecimal Value to be Checked
  * @param theValue
  * @return BigDecimal Correct Value
  */
 public static BigDecimal nullEmptyToValue(BigDecimal b,BigDecimal theValue)
 {
     if(isEmptyDecimal(b))
     {
	 return theValue;
     }
     else
     {
	 return b;
     }
 }

 /**
  * Replaces the null by the Zero when provided with an Integer
  * @param i Integer
  * @return int
  */
 public static int nullToZero(Integer i)
 {
  if(i == null)
  {
   return 0;
  }
  else
  {
   return i.intValue();
  }
 }

 /**
  * Replaces the null by the Zero when provided with a Double
  * @param d Double
  * @return double
  */
 public static double nullToZero(Double d)
 {
  if(d == null)
  {
   return 0;
  }
  else
  {
   return d.doubleValue();
  }
 }

 /**
  * Replaces the null by the Zero when provided with a Float
  * @param f Float
  * @return float
  */
 public static float nullToZero(Float f)
 {
  if(f == null)
  {
   return 0;
  }
  else
  {
   return f.floatValue();
  }
 }
 
 /**
  * 
  * Used for add leading Zeros to any numeric Number, if Numeric is Decimal points then it will be truncated
  * 
  * @param numeric any numeric Object , decimal points will be truncated
  * @param nbLength Length of the result to be obtained with leading Zeros 
  * @return
  */
 public static String addLeadingZeros(Number numeric,int nbLength)
 {
    Double theNumerDbl = toDoubleObj(numeric);
    Integer theNumerInt  = theNumerDbl.intValue();
    String thePattern = "%0"+nbLength+"d";
    return String.format(thePattern, theNumerInt);
 }

 /**
  * Converts an object input to a double.
  * @param o Object
  * @return double
  */
 public static double toDouble(java.lang.Object o)
 {
  if(o == null)
  {
   return 0;
  }
  else if(o.toString().equals(""))
  {
    return 0;
  }
   else
   {
    return Double.parseDouble(o.toString());
   }
 }
 /**
  * Converts an object input to a Double.
  * @param o Object
  * @return Double
  */
 public static Double toDoubleObj(java.lang.Object o)
 {
	 if(o == null)
	 {
	     return Double.valueOf(0);
	 }
	 else if(o.toString().equals(""))
	 {
	     return Double.valueOf(0);
	 }
	 else
	 {
	     return(Double.valueOf(o.toString()));
	 }
 }

 /**
  * Converts an object input to an int.
  * @param o Object
  * @return int
  */
 public static int toInt(java.lang.Object o)
 {
  if(o == null)
  {
   return 0;
  }
  else if(o.toString().equals(""))
  {
    return 0;
  }
   else
   {
    return Integer.parseInt(o.toString());
   }
 }
 /**
  * Converts an object input to an Integer.
  * @param o Object
  * @return int
  */
 public static Integer toInteger(java.lang.Object o)
 {
	 if(o == null)
	 {
	     return 0;
	 }
	 else if(o.toString().equals(""))
	 {
	     return 0;
	 }
	else
	{
	    return Integer.valueOf(o.toString());
	}
 }



 /**
  * Return a formated number using the group-separator and decimal-separator
  * @param val Object
  * @param pattern String
  * @param groupsepa String
  * @param decimalsepa String
  * @return String
  */
 public static String format(Object val, String pattern, char groupsepa, char decimalsepa)
 {
     if(val == null)
     {
	 return "";
     } 
     try
     {
	 DecimalFormat _df = new DecimalFormat();
	 _df.setRoundingMode(RoundingMode.HALF_UP);
	 _df.applyPattern(pattern);

	 String standardFrmt =  _df.format(val);
	 // need first to replace by #grpSep# so that in case the group separator is equal to dot . the replacement will be correct
	 String returnFrmt =  standardFrmt.replace(",","#grp#");
	 returnFrmt = returnFrmt.replace(".",decimalsepa+"");
	 returnFrmt = returnFrmt.replace("#grp#",groupsepa+"");
	 return returnFrmt;

     }
     catch(Exception ex)
     {
	 log.error(ex, "[NumberUtil] Error caught in method format");
	 return "";
     }
}

 /**
  * Return the int formatted according to the given pattern String
  * @param val int
  * @param pattern String
  * @return String
  */
 public static String format(int val, String pattern)
 {
  return format(Integer.valueOf(val), pattern);
 }

 /**
  * Return the double formatted according to the given pattern String
  * @param val double
  * @param pattern String
  * @return String
  */
 public static String format(double val, String pattern)
 {
  return format(Double.valueOf(val), pattern);
 }

 /**
  * Return the float formatted according to the given pattern String
  * @param val float
  * @param pattern String
  * @return String
  */
 public static String format(float val, String pattern)
 {
  return format(Float.valueOf(val), pattern);
 }

 /**
  * returns the int value of the input string.
  * If the input string is null or empty string it returns zero else it returns the value of Integer.parseInt call
  * @param val String
  * @return int
  */
 public static int parseInt(String val)
 {
  if(val == null || val.trim().length() == 0)
  {
   return 0;
  }
  else
  {
   return Integer.parseInt(val);
  }
 }

 /**
  * returns the double value of the input string.
  * If the input string is null or empty string it returns zero else it returns the value of Double.parseDouble call
  * @param val String
  * @return double
  */
 public static double parseDouble(String val)
 {
  if(val == null || val.trim().length() == 0)
  {
   return 0;
  }
  else
  {
   return Double.parseDouble(val);
  }
 }
/**
 * check if the given string is number or not
 * @param val
 * @return
 */
 public static boolean isNumber(String val)
 {
	 if(val == null || val.trim().isEmpty())
	 {
		 return false;
	 }
	 else
	 {
		 Pattern patternForNumber = Pattern.compile("^[+-]?(([0-9]+\\.?[0-9]+)|[0-9]+)");
		 return patternForNumber.matcher(val).matches();
	 }
 }
/**
 * 
 * Used for returning true if the number is empty value received from the client side or not available 
 * 
 * @param myNumber Number to check
 * @return true if BigDecimal is empty
 */
 public static boolean isEmptyDecimal(BigDecimal myNumber)
 {
     return (myNumber == null || ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(myNumber));
 }
 
 /**
  * 
  * Used for converting emptyBigDecimal to Null
  * 
  * @param myNumber Number to check
  * @return  BigDecimal / null is empty
  */
  public static BigDecimal emptyDecimalToNull(BigDecimal myNumber)
  {
      if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(myNumber))
      {
 	 return null;
      }
      else
      {
 	 return myNumber;
      }
  }
  
  /**
   * 
   * Used for converting emptyBigDecimal to Value
   * 
   * @param myNumber Number to check
   * @return  BigDecimal / zero is empty
   */
   public static BigDecimal emptyDecimalToZero(BigDecimal myNumber)
   {
       if(myNumber == null || ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(myNumber))
       {
  	 return BigDecimal.ZERO;
       }
       else
       {
  	 return myNumber;
       }
   }
 /**
 * Round a double value's decimal to specific no of decimal, Round UP is performed  
 * @param theVal
 * @param periods
 * @return double
 */
    public static double round(double theVal, int periods)
    {
	BigDecimal result = roundToBigDecimal(BigDecimal.valueOf(theVal),periods);
	return result.doubleValue();
    }
/**
 * Round a double value's decimal to specific no of decimal, Round up is applied
 * @param theVal
 * @param periods
 * @returndouble
 */
    public static Double round(Double theVal, Long periods)
    {
	Double val = theVal;
	if(val == null)
	{
	    return null;
	}
	BigDecimal result = roundToBigDecimal(BigDecimal.valueOf(theVal).doubleValue(),periods);
	return result.doubleValue();
    }

 
    
 
/**
 * Return Big decimal which is round the decimal of double to precision 
 * there was a issue when we convert the double to big decimal it is return 56.56499999999.....
 * @param unrounded
 * @param precision
 * @return
 */
public static BigDecimal roundToBigDecimal(double unrounded, Long precision) {
	int roundingMode = BigDecimal.ROUND_HALF_UP;
	int precisionInt = precision.intValue();
	BigDecimal bd = BigDecimal.valueOf(unrounded);
	return bd.setScale(precisionInt, roundingMode);
}
/**
 * Return Big decimal which is round the decimal of BigDecimal to precision 
 * there was a issue when we convert the double to big decimal it is return 56.56499999999.....
 * @param unrounded
 * @param precision
 * @return
 */
public static BigDecimal roundToBigDecimal(BigDecimal unrounded, int precision) {
    	BigDecimal bd = nullToZero(unrounded);
	return bd.setScale(precision, BigDecimal.ROUND_HALF_UP);
}

    /**
     * Truncate the decimal
     * 
     * @param theVal
     * @param precision
     * @return
     */
    public static double truncate(double theVal, Long precision)
    {
	double val = theVal;
	DecimalFormat twoDForm = new DecimalFormat("#.############");
	val = Double.valueOf(twoDForm.format(val));
	double multiplier = Math.pow(10, precision);
	return Math.floor(multiplier * val) / multiplier;
    }

/**
 * check if is number and positive
 * @param val
 * @return boolean
 */
public static boolean isNumberPositive(Object val) throws BaseException
{
	if(val instanceof Number && NumberUtil.toDouble(val) > 0 )
	{
	    return true;
	}
	return false;
}
/**
 * check if is number and negative
 * @param val
 * @return boolean
 */
public static boolean isNumberNegative(Object val) throws BaseException
{
	if(val instanceof Number && NumberUtil.toDouble(val) < 0 )
	{
	    return true;
	}
	return false;
}


}