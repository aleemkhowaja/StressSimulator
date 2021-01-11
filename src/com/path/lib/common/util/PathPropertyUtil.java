package com.path.lib.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.path.bo.common.ConstantsCommon;
import com.path.lib.common.exception.BaseException;
import com.path.lib.log.Log;
import com.path.struts2.lib.common.BaseObject;

public final class PathPropertyUtil
{
    private static final Log log = Log.getInstance();
    
    private volatile static Map<String,Properties> loadedPropsMap = new HashMap<String, Properties>();
    /**
     * Private constructor to prevent class from instantiation
     */
    private PathPropertyUtil()
    {
	log.warning("This class is utility and cannot be instantiated");
    }
    /**
     * 
     * Used for all copies properties from one object to another
     * 
     * @param src
     * @param dest
     * @throws BaseException
     */
    public static void copyProperties(Object src, Object dest) throws BaseException
    {
	try
	{
	    PropertyUtils.copyProperties(dest, src);
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil copyProperties all ");
	    throw new BaseException(e);
	}
    }
    
   /**
     * 
     * Used for  copying Main Class (Not Nested) not null properties from one object to another
     * 
     * @param source
     * @param target
     * @throws BaseException
     */
    public static void copyMainNotNullProperties(Object source, Object target) throws BaseException
    {
	try
	{
	    Assert.notNull(source, "Source must not be null");
	    Assert.notNull(target, "Target must not be null");

	    Class<?> actualEditable = target.getClass();
	    PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);

	    for(PropertyDescriptor targetPd : targetPds)
	    {
		if(targetPd.getWriteMethod() != null)
		{
		    PropertyDescriptor sourcePd = BeanUtils
			    .getPropertyDescriptor(source.getClass(), targetPd.getName());
		    if(sourcePd != null && sourcePd.getReadMethod() != null)
		    {
			try
			{
			    Method readMethod = sourcePd.getReadMethod();
			    if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers()))
			    {
				readMethod.setAccessible(true);
			    }
			    Object value = readMethod.invoke(source);
			    if(value != null)
			    {
				Method writeMethod = targetPd.getWriteMethod();
				if(value instanceof Number
					||  value instanceof Date
					||  value instanceof String)
				{
        				if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers()))
        				{
        				    writeMethod.setAccessible(true);
        				}
        				writeMethod.invoke(target, value);
				}
			    }
			}
			catch(Throwable ex)
			{
			    log.error(ex, "Error in PathPropertyUtil copyNotNullProperties ");
			    throw new BaseException(ex);
			}
		    }
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil copyProperties Not Null ");
	    throw new BaseException(e);
	}
    }
    
    /**
     * copies specificproperties from one object to another
     * 
     * @param src the source object
     * @param dest the destination object
     * @param properties a list of property names that are to be copied. Each
     *            value has the format "srcProperty destProperty". For example,
     *            "name fullName" indicates that you want to copy the src.name
     *            value to dest.fullName. If both the srcProperty and
     *            destProperty property have the same name, you can omit the
     *            destProperty. For example, "name" indicates that you want to
     *            copy src.name to dest.name.
     */
    public static void copyProperties(Object src, Object dest, String... properties) throws BaseException
    {
	copyProperties(src, dest,false, properties);
    }
    
    /**
     * copies specific properties from one object to another
     * 
     * @param src the source object, can be complex object or Map
     * @param dest the destination object, can be complex object or Map
     * @param checkPropExist boolean to specify whether to check if property
     *            exists in both src and dest, if false Exception will be
     *            raised, otherwise property will be ignored
     * @param properties a list of property names that are to be copied. Each
     *            value has the format "srcProperty destProperty". For example,
     *            "name fullName" indicates that you want to copy the src.name
     *            value to dest.fullName. If both the srcProperty and
     *            destProperty property have the same name, you can omit the
     *            destProperty. For example, "name" indicates that you want to
     *            copy src.name to dest.name.
     *            Also property can be specified as nested, example myCO.myVO.description
     *            for Map objects the keys will be /should be full path to the property example Map{myCo.myVo.description="TEST",...}
     */
    public static void copyProperties(Object src, Object dest, boolean checkPropExist, String... properties) throws BaseException
    {
	String srcProperty;
	String[] srcPropSplit, destPropSplit;
	String destProperty;
	Object tempDest, tempSrc, nestedDest;
	try
	{
	    for(String property : properties)
	    {
		tempSrc = src;
		tempDest = dest;
		String[] arr = property.split(" ");
		boolean propAvail = true;// variable to check if the property Exists or not
		// if src property and dest property specified different
		if(arr.length == 2)
		{
		    srcProperty = arr[0];
		    destProperty = arr[1];
		    // check whether src or destination properties are nested
		    srcPropSplit = srcProperty.split("\\.");
		    
		    if(!(src instanceof Map) && srcPropSplit.length > 1)
		    {
			    for(int i = 0; i < srcPropSplit.length - 1; i++)
			    {
				// check if property is readable (Available)
				if(checkPropExist && !PropertyUtils.isReadable(tempSrc, srcPropSplit[i]))
				{
				    propAvail = false;
				    break;
				}
				tempSrc = PropertyUtils.getProperty(tempSrc, srcPropSplit[i]);
			    }
			    // if property not exists in source object then
			    // continue
			    // to next specified property
			    if(!propAvail)
			    {
				continue;
			    }
			    srcProperty = srcPropSplit[srcPropSplit.length - 1];
		    }
		    
		    if(!(dest instanceof Map))
		    {
			propAvail = true; // Reinitialise the availability property
			destPropSplit = destProperty.split("\\.");
			if(destPropSplit.length > 1)
			{
			    nestedDest = dest;
			    for(int i = 0; i < destPropSplit.length - 1; i++)
			    {
				// check if destination property is available
				if(checkPropExist
					&& (!PropertyUtils.isReadable(nestedDest, destPropSplit[i]) || !PropertyUtils
						.isWriteable(nestedDest, destPropSplit[i])))
				{
				    propAvail = false;
				    break;
				}
				tempDest = PropertyUtils.getProperty(nestedDest, destPropSplit[i]);
				if(tempDest == null)
				{
				    tempDest = PropertyUtils.getPropertyType(nestedDest, destPropSplit[i])
					    .newInstance();
				    PropertyUtils.setProperty(nestedDest, destPropSplit[i], tempDest);
				}
				nestedDest = tempDest;
			    }
			    // if property not exists in source object then
			    // continue
			    // to next specified property
			    if(!propAvail)
			    {
				continue;
			    }

			    destProperty = destPropSplit[destPropSplit.length - 1];
			}
		    }

		}
		else // src property and dest property name is the same
		{
		    srcProperty = destProperty = property;
		 // check whether src or destination properties are nested
		    srcPropSplit = srcProperty.split("\\.");
		    if(srcPropSplit.length > 1)
		    {
			nestedDest = dest;
			for(int i = 0; i < srcPropSplit.length - 1; i++)
			{
			 // check if destination property is available
			    if(checkPropExist &&
				( (!(src instanceof Map) && !PropertyUtils.isReadable(tempSrc, srcPropSplit[i]))
				    ||(!(dest instanceof Map) && !PropertyUtils.isWriteable(tempDest, srcPropSplit[i]))))
			    {
				propAvail = false;
				break;
			    }
			    
			    // if moving from Map object then there is not nesting,the key of the map is directly the name of the property
			    if(!(src instanceof Map))
			    {
				tempSrc = PropertyUtils.getProperty(tempSrc, srcPropSplit[i]);
			    }
			    
			    if(!(dest instanceof Map))
			    {
				tempDest = PropertyUtils.getProperty(nestedDest, srcPropSplit[i]);
				if(tempDest == null)
				{
				    tempDest = PropertyUtils.getPropertyType(nestedDest, srcPropSplit[i]).newInstance();
				    PropertyUtils.setProperty(nestedDest, srcPropSplit[i], tempDest);
				}
				nestedDest = tempDest;
			    }
			}
			// if property not exists in source object then continue
			// to next specified property
			if(!propAvail)
			{
			    continue;
			}
			
			if(!(src instanceof Map))
			{
			    srcProperty = srcPropSplit[srcPropSplit.length - 1];
			}

			if(!(dest instanceof Map))
			{
			    destProperty = srcPropSplit[srcPropSplit.length - 1];
			}
		    }
		}
		// check if destination property is available
		if(checkPropExist &&((!(dest instanceof Map) && !PropertyUtils.isWriteable(tempDest, destProperty))
			|| (!(src instanceof Map) && !PropertyUtils.isReadable(tempSrc, srcProperty))))
		{
		    continue;
		}
		
		// setting the property to Destination object
		if(dest instanceof Map && src instanceof Map)
		{
		    ((Map)dest).put(destProperty, ((Map)src).get(srcProperty));
		}
		else
		if(dest instanceof Map)
		{
		    ((Map)dest).put(destProperty, PropertyUtils.getProperty(tempSrc, srcProperty));
		}
		else
		if(src instanceof Map)   
		{
		    PropertyUtils.setProperty(tempDest, destProperty,((Map)src).get(srcProperty));
		}
		else // both Src and Dest are not Maps
		{
		    PropertyUtils.setProperty(tempDest, destProperty, PropertyUtils.getProperty(tempSrc, srcProperty));
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil copyProperties ");
	    throw new BaseException(e);
	}
    }
    /**
     * Method that keeps only specified properties in the Object
     * @param src source object in which the values to keep
     * @param properties String array of properties to keep
     * @return new instance of src Object with only specified properties filled
     * @throws BaseException
     */
    public static Object keepSpecificProperties(Object src, String... properties) throws BaseException
    {
	try
	{
	    Object resultObj = src.getClass().newInstance();
	    copyProperties(src, resultObj, properties);
	    return resultObj;
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil keepSpecificProperties ");
	    throw new BaseException(e);
	}
    }
    /**
     * set specified list of properties to a provided value, that can be null also.
     * 
     * @param src the source object
     * @param theValue the value to set can be null
     * @param properties a list of property names that are to be set as null. Each
     *            value has the format "srcProperty". For example,
     *            "cifCO.cifVO.cifName" indicates that you want to set cifCO.cifVO.cifName
     *            to theValue. 
     */
    public static void resetPropertiesToValue(Object src, Object theValue, String... properties) throws BaseException
    {
	String srcProperty;
	String[] srcPropSplit;
	Object tempSrc;
	try
	{
	    for(String property : properties)
	    {
		tempSrc = src;
		srcProperty = property;

		// check whether src properties is nested
		srcPropSplit = srcProperty.split("\\.");
		if(srcPropSplit.length > 1)
		{
		    for(int i = 0; i < srcPropSplit.length - 1; i++)
		    {
			tempSrc = PropertyUtils.getProperty(tempSrc, srcPropSplit[i]);
		    }
		    srcProperty = srcPropSplit[srcPropSplit.length - 1];
		}
		PropertyUtils.setProperty(tempSrc, srcProperty, theValue);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil resetPropertiesToValue ");
	    throw new BaseException(e);
	}
    }  
 /**
 * user to return value of given property from src Object
 * @param src
 * @param propName
 * @return
 * @throws BaseException
 */
    public static Object returnProperty(Object src, String propName) throws BaseException
    {
	Object result = null;
	try
	{
	    if(src != null)
	    {
		    String[] srcPropSplit = propName.split("\\.");
		    // if moving from Map object then there is not nesting,the key of the map is directly the name of the property
		    if(!(src instanceof Map) && srcPropSplit.length >1)
		    {
			//to check whether the object is not null in order to fetch the nested value inside of it.
			Object tmpPropName = src;
			for(int i = 0; i < srcPropSplit.length; i++)
			{
			    tmpPropName = PropertyUtils.getProperty(tmpPropName, srcPropSplit[i]);
			    result = tmpPropName;
			    if(result==null) 
			    {
				break;
			    }
			}
		    }else 
		    {
			result = PropertyUtils.getProperty(src, propName);
		    }
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "Error in PathPropertyUtil returnProperties ");
	    throw new BaseException(e);
	}
	return result;
    }

    /**
     * Method to set the value for the property in the source object
     * @param src Bean whose property is to be modified
     * @param name name of the property to be modified
     * @param value Value to which this property is to be set
     * @throws BaseException
     */
    public static void setProperty(Object src, String name, Object value)throws BaseException 
    {
	try
	{
	    PropertyUtils.setProperty(src, name, value);
	}
	catch (Exception e) {
	    log.error(e, "Error in PathPropertyUtil setProperty ");
	    throw new BaseException(e);
	}

    }

    /**
     * returns value from properties file
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static String getPathRemotingProp(String propFileName, String propertyName)throws Exception
    {
	return returnPathPropertyFromFile(propFileName, propertyName);
    }
    /**
     * returns value from any property file 
     * @param propertyName
     * @return
     * @throws Exception
     */
    public static String returnPathPropertyFromFile(String paramFileName, String propertyName)throws Exception
    {
	Assert.notNull(paramFileName);
	Assert.notNull(propertyName);
	
	String propValue = null;
	Properties prop = null;
	String propFileName = (paramFileName.indexOf(".properties") > -1) ? paramFileName : paramFileName.concat(".properties"); 
	
	if(loadedPropsMap.containsKey(propFileName))
	{
	    prop = loadedPropsMap.get(propFileName);
	}
	else
	{
	    prop = new Properties();
	    prop.load(PathPropertyUtil.class.getClassLoader().getResourceAsStream(propFileName));
	    loadedPropsMap.put(propFileName, prop);
	}
	if(prop != null)
	{
	    propValue = prop.getProperty(propertyName);
	}
	return propValue;
    }
    
    /**
     * return boolean true if file exists
     * @param paramFileName
     * @return
     * @throws Exception
     */
    public static boolean checkPropertyFileExistance(String paramFileName) throws Exception
    {
	Assert.notNull(paramFileName);
	
	String propFileName = (paramFileName.indexOf(".properties") > -1) ? paramFileName : paramFileName.concat(".properties"); 
	
	URL checkExist = PathPropertyUtil.class.getClassLoader().getResource(propFileName);
	if(checkExist == null)
	{
	    return false;
	}
	else
	{
	    return true;
	}
    }
    
    /**
     * Reset all the Empty String and BigDecimal Properties that are exists in the Object Obj
     * with cach management to the properties that are related to this Object 
     * @author marwanmaddah
     * @date   Nov 3, 2014
     * @param obj void
     *
     */
   
    /**
     * used to reset the properties of the current object 
     * @author marwanmaddah
     * @date   Oct 31, 2014
     * @param theObj
     * @param lstObjProp void
     *
     */
    private static void resetCurrentObject(Object theObj, List<String> lstObjProp)
    {
	try
	{
	        /**
	         * loop on all the properties that are exists inside the Object 
	         */
		for(String currObj:lstObjProp)
		{
		    Object inlineObj    = theObj;
		    String theClassPath = currObj;
		    if(!StringUtil.nullToEmpty(theClassPath).isEmpty())
		    {			
			String[] arrObjNames = theClassPath.split("\\.");
			int arrSize = arrObjNames.length;
			String propName = arrSize > 0? arrObjNames[arrSize-1]:theClassPath; 
			int i = 0;
			while(i < arrSize-1)
			{
			    Object crrObj = inlineObj.getClass().getMethod("get"+ StringUtil.setFirstCharUpper(arrObjNames[i]), null).invoke(inlineObj,null);
			    inlineObj = crrObj;
			    /**
			     * in case any of the objects that are exists in the chain is null
			     * the loop will be stopped
			     */
			    if(crrObj==null)
			    {
				break;
			    }
			    i++;
			}
			/**
			 * in case the inlineObj is null no need to reset 
			 * the properties that are exists inside it
			 */
			if(inlineObj!=null)
			{
			    Object t = PropertyUtils.getProperty(inlineObj, propName);
			    PropertyDescriptor pd = new PropertyDescriptor(propName, inlineObj.getClass());
			    Class theType = pd.getPropertyType();
			    if((theType.isAssignableFrom(String.class) && "".equals(t)) 
				|| 
			       (theType.isAssignableFrom(BigDecimal.class) && ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(t)))
			    {
				PropertyUtils.setProperty(inlineObj, propName, null);   
			    }
			}
		    }
		}
	}
	catch(Exception ex)
	{
	    log.error(ex, "ERROR in resetCurrentObject PathPropertyUtil.java"); 
	}

    }
    /**
     * 
     * @author marwanmaddah
     * @date   Nov 3, 2014
     * @param obj
     * @param objPath
     * @param strList
     * @param bigDecimalList
     * @param isList void
     *
     */
    private static void cachStringAndBigDecimalProp(Object obj,StringBuffer objPath,List<String> strList,List<String> bigDecimalList,boolean isList)
    {
	try
	{
	    
	    /**
	     * get all the properties from theObj
	     */
	    
	    Object theObj = null;
	    /**
	     * isList : it is mean that the type of the Object is a list
	     */
	    if(isList)
	    {
		ArrayList<Object> lst  = (ArrayList<Object>)obj; 
		theObj = (lst).get(0);
		/**
		 * in case the object is a list will reset all the Objects that are exists inside the list 
		 * and in the first entry the properties will be added to the list to add them to the HashMap 
		 */
		for(int i=0;i<lst.size();i++)
		{
		  Object currObj = lst.get(i);
		  /**
		   * in case of first entry the properties will be added to the list to be cached 
		   * otherwise will not be added to avoid the duplication in the cached hashMap
		   */
		  boolean firstEntry = (i==0);
		  cachCurrenObj(currObj, objPath, strList,bigDecimalList,firstEntry);
		}
	    }
	    else
	    {
		theObj = obj;
		cachCurrenObj(theObj, objPath, strList,bigDecimalList,true);
	    }
	}
	catch(Exception ex)
	{
	    log.error(ex,"ERROR in cachStringAndBigDecimalProp PathPropertyUtil.java"); 
	}
    }
    /**
     * Used to cach the properties of the Current Object 
     * @author marwanmaddah
     * @date   Oct 31, 2014
     * @param theObj
     * @param objPath
     * @param list
     * @param firstEntry void
     *
     */
    private static void cachCurrenObj(Object theObj, StringBuffer objPath,List<String> strList, List<String> bigDecimalList,boolean firstEntry)

    {
	StringBuffer currPath;
	try
	{
	    PropertyDescriptor[] disc = PropertyUtils.getPropertyDescriptors(theObj);
	    for(int i = 0; i < disc.length; i++)
	    {
		currPath = new StringBuffer(objPath);
		PropertyDescriptor di = disc[i];
		String propName = di.getName();
		if(!"class".equalsIgnoreCase(propName))
		{
		    Class theType = di.getPropertyType();
		    Object t = PropertyUtils.getProperty(theObj, propName);
		    /**
		     * in case the current property is an instance of Base
		     * Object a recursive call to get and cach all the
		     * properties from all the chain of Objects that are exists
		     * inside the Main Object
		     */
		    if(t instanceof BaseObject)
		    {
			if(StringUtil.nullToEmpty(currPath).isEmpty())
			{
			    currPath = new StringBuffer();
			    currPath.append(propName);
			}
			else
			{
			    currPath.append(".").append(propName);
			}
			/**
			 * to cach the object related to the current property in
			 * a separated entry in the hashMap
			 */
			List<String> crrObjStrList = new ArrayList<String>();
			List<String> crrObjBigDecimalList = new ArrayList<String>();
			cachPropOfCurrObj(t, new StringBuffer(), crrObjStrList,crrObjBigDecimalList);
			/**
			 * recursive call to cach the property that are exists
			 * inside the current Object
			 */
			cachCurrenObj(t,currPath,strList,bigDecimalList,firstEntry);
		    }
		    else if((theType.isAssignableFrom(String.class)|| theType.isAssignableFrom(BigDecimal.class)) 
			    && PropertyUtils.isWriteable(theObj, propName)
			    && PropertyUtils.isReadable(theObj, propName)
			   )
		    {
			/**
			 * if the currentPath is null, so the current property
			 * is a direct property inside the main Object
			 */
			if(StringUtil.nullToEmpty(currPath).isEmpty())
			{
			    currPath = new StringBuffer();
			    currPath.append(propName);
			}
			/**
			 * otherwise it is a property from other object that is
			 * exists inside the Main Object
			 */
			else
			{
			    currPath.append(".").append(propName);
			}
			if(firstEntry)
			{
			    if(theType.isAssignableFrom(String.class))
			    {				
				strList.add(currPath.toString());
			    }
			    else if(theType.isAssignableFrom(BigDecimal.class))
			    {
				bigDecimalList.add(currPath.toString());
			    }
			}
			if((theType.isAssignableFrom(String.class) && "".equals(t)) 
			   || 
			   (theType.isAssignableFrom(BigDecimal.class) && ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(t)))
			{
			    PropertyUtils.setProperty(theObj, propName, null);
			}
		    }
		}
	    }
	}
	catch(Exception ex)
	{
	    log.error(ex, "ERROR IN cachCURRENObj PathPropertyUtil.java");
	}

    }
    /**
     * Used in case of object inside object 
     * to cach the properties that are related to this oBject in an separated entry in the HashMap
     * @author marwanmaddah
     * @date   Oct 31, 2014
     * @param theObj
     * @param objPath
     * @param list void
     *
     */
    private static void cachPropOfCurrObj(Object theObj,StringBuffer objPath,List<String> strList,List<String> bigDecimalList)
    {
	StringBuffer currPath;
	try
	{
	    PropertyDescriptor[] disc = PropertyUtils.getPropertyDescriptors(theObj);
	    for(int i = 0; i < disc.length; i++)
	    {
		currPath = new StringBuffer(objPath);
		PropertyDescriptor di = disc[i];
		String propName = di.getName();
		if(!"class".equalsIgnoreCase(propName))
		{
		    Class theType = di.getPropertyType();
		    Object t = PropertyUtils.getProperty(theObj, propName);
		    if(t instanceof BaseObject)
		    {
			if(StringUtil.nullToEmpty(currPath).isEmpty())
			{
			    currPath = new StringBuffer();
			    currPath.append(propName);
			}
			else
			{
			    currPath.append(".").append(propName); 
			}
			cachPropOfCurrObj(t,currPath,strList,bigDecimalList);
		    }
		    else if((theType.isAssignableFrom(String.class) || theType.isAssignableFrom(BigDecimal.class))
			    && PropertyUtils.isWriteable(theObj, propName)
			    && PropertyUtils.isReadable(theObj, propName))
		    {
			
			if(StringUtil.nullToEmpty(currPath).isEmpty())
			{
			    currPath = new StringBuffer();
			    currPath.append(propName);
			}
			else
			{
			    currPath.append(".").append(propName); 
			}
			if(theType.isAssignableFrom(String.class))
			{
			    strList.add(currPath.toString());
			}
			else if(theType.isAssignableFrom(BigDecimal.class))
			{			    
			    bigDecimalList.add(currPath.toString());
			}
			if((theType.isAssignableFrom(String.class) && "".equals(t)) 
			   || 
			   (theType.isAssignableFrom(BigDecimal.class) && ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(t)))
			{
			    PropertyUtils.setProperty(theObj, propName, null);   
			}
		    }
		}
	    }
	    if(StringUtil.nullToEmpty(objPath).isEmpty() && !strList.isEmpty())
	    {
		StringUtil.objectStringPropNames.put(theObj.getClass().getName(),strList);
	    }
	    if(StringUtil.nullToEmpty(objPath).isEmpty() && !bigDecimalList.isEmpty())
	    {
		NumberUtil.objectBigDecimalPropNames.put(theObj.getClass().getName(),bigDecimalList);
	    }
	}
	catch(Exception ex)
	{
	    log.error(ex,"ERROR in cachPropOfCurrObj PathPropertyUtil.java"); 
	}
    }
    
    /**
     * Utility method to return the type of the property
     * 
     * @param actionObject
     * @param propertyName
     * @return
     */
    public static Class<?> returnPropertyType(Object actionObject, String propertyName)
    {
	Object tmpObject = actionObject;
	Class<?> tmpObjectClass = null;
	try
	{
	    if(tmpObject != null && StringUtil.isNotEmpty(propertyName))
	    {
		String[] nestedProperties = propertyName.split("\\.");
		for(String nestedProperty : nestedProperties)
		{
		    tmpObjectClass = PropertyUtils.getPropertyType(tmpObject, nestedProperty);
		    tmpObject = PropertyUtils.getProperty(tmpObject, nestedProperty);
		}
	    }
	}
	catch(Throwable e)
	{
	    tmpObjectClass = null;
	}
	return tmpObjectClass;
    }
    
    /**
     * method that convert an object to a map
     * 
     * @param src Object that need to be converted
     * @return result HashMap<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> convertToMap(Object src)
    {
	ObjectMapper m = new ObjectMapper();

	// Do not include Null in conversion
	m.setSerializationInclusion(Inclusion.NON_NULL);
	m.disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);

	// Do not add '.0' to the BigDecimal values
	SimpleModule module = new SimpleModule("PathSerializerModule", new Version(1, 0, 0, null));
	module.addSerializer(BigDecimal.class, new ToStringSerializer());
	m.registerModule(module);

	return (HashMap<String, Object>) m.convertValue(src, Map.class);
    }

    /**
     * method that convert the map constructed using the 'convertToMap' method
     * to object.
     * 
     * @param map Map to be converted to Object.
     * @param objClass Class of required Object.
     * @return resulted Object with map data.
     */
    public static Object convertToObject(HashMap<String, Object> map, Class<?> objClass)
    {
	ObjectMapper m = new ObjectMapper();
	// Do not include Null in conversion
	m.setSerializationInclusion(Inclusion.NON_NULL);
	m.disable(Feature.FAIL_ON_UNKNOWN_PROPERTIES);
	return m.convertValue(map, objClass);
    }
    
    /**
     * method used to remove the cached property from loadedPropsMap. 
     * A new call of returnPathPropertyFromFile is needed to reload the property from .properties file 
     * @param propFileName
     */
    public static void removeCachedPropFile(String propFileName)
    {
	if(StringUtil.isNotEmpty(propFileName))
	{
	    loadedPropsMap.remove(propFileName);
	}
    }
}
