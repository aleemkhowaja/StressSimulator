package com.path.lib.vo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.path.struts2.lib.common.BaseObject;

public class BaseVO extends BaseObject
{
    private BigDecimal baseCurrencyCode;
    Object auditObj;
    private transient Map<String,String> nullUpdMap; // map of Fields that need to set to null, needed for Date  fields, since String and bigdecimal can be managed by empty String and emptyBigDecimal, But can be used for all types
    
    /**
     * Method to add the null field to consider in VO MApper to test if field to set as null
     * @param fieldName String Field Name == Column Name
     */
    public void addNullFldToMap(String fieldName)
    {
	if(nullUpdMap == null)
	{
	    nullUpdMap = new HashMap<String,String>();
	}
	nullUpdMap.put(fieldName, null);
    }
    /**
     * Method to remove the null field if added using addNullFldToMap
     * @param fieldName String Field Name == Column Name
     */
    public void removeNullFldFromMap(String fieldName)
    {
	if(nullUpdMap != null)
	{
	    nullUpdMap.remove(fieldName);
	}
    }
    
    /**
     * Method to check the null field if added to the nullUpd Map
     * @param fieldName String Field Name == Column Name
     */
    public boolean checkNullFldInMap(String fieldName)
    {
	if(nullUpdMap != null && fieldName != null && nullUpdMap.containsKey(fieldName))
	{
	   return true;
	}
	return false;
    }

    public Object getAuditObj()
    {
        return auditObj;
    }

    public void setAuditObj(Object auditObj)
    {
        this.auditObj = auditObj;
    }

    public BigDecimal getBaseCurrencyCode()
    {
        return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(BigDecimal baseCurrencyCode)
    {
        this.baseCurrencyCode = baseCurrencyCode;
    }

}
