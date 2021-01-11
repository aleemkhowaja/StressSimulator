package com.path.struts2.lib.common;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Copyright 2012, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * @author: DeniskHaddad
 * 
 * BaseSC.java used to extend all Search Criteria Object in common Way
 */
public class BaseSC extends BaseObject
{
    private BigDecimal compCode; // global way to define Company code in all SC
    private BigDecimal branchCode;// global way to define Branch code in all SC
    private String userId;
    private Date runningDate; // Running Date
    private String preferredLanguage;// Preferred Language "A" or "L"
    private String sectionKey; // The key of the section/pop-up name where a
			       // defined item (labelKey) exist
    private String labelKey; // The Label key of a certain item to be thrown
			     // within a BOException message
    private BigDecimal baseCurrencyCode; // base currency Code

    private String crudMode; // corresponds to iv_crud;
    private BigDecimal lovTypeId; // lov Type id to fetch translation
    private String currAppName;// Name of the Application
    private int isRTLDir;// 1 means Right To left Direction, 0 Otherwise
    public String getCrudMode()
    {
	return crudMode;
    }

    public void setCrudMode(String crudMode)
    {
	this.crudMode = crudMode;
    }

    public BigDecimal getLovTypeId()
    {
	return lovTypeId;
    }

    public void setLovTypeId(BigDecimal lovTypeId)
    {
	this.lovTypeId = lovTypeId;
    }

    public String getCurrAppName()
    {
	return currAppName;
    }

    public void setCurrAppName(String currAppName)
    {
	this.currAppName = currAppName;
    }

    public BigDecimal getCompCode()
    {
	return compCode;
    }

    public void setCompCode(BigDecimal compCode)
    {
	this.compCode = compCode;
    }

    public BigDecimal getBranchCode()
    {
	return branchCode;
    }

    public void setBranchCode(BigDecimal branchCode)
    {
	this.branchCode = branchCode;
    }

    public String getUserId()
    {
	return userId;
    }

    public void setUserId(String userId)
    {
	this.userId = userId;
    }

    public Date getRunningDate()
    {
	return runningDate;
    }

    public void setRunningDate(Date runningDate)
    {
	this.runningDate = runningDate;
    }

    public String getPreferredLanguage()
    {
	return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage)
    {
	this.preferredLanguage = preferredLanguage;
    }

    public String getSectionKey()
    {
	return sectionKey;
    }

    public void setSectionKey(String sectionKey)
    {
	this.sectionKey = sectionKey;
    }

    public String getLabelKey()
    {
	return labelKey;
    }

    public void setLabelKey(String labelKey)
    {
	this.labelKey = labelKey;
    }

    public BigDecimal getBaseCurrencyCode()
    {
	return baseCurrencyCode;
    }

    public void setBaseCurrencyCode(BigDecimal baseCurrencyCode)
    {
	this.baseCurrencyCode = baseCurrencyCode;
    }

    public int getIsRTLDir()
    {
        return isRTLDir;
    }

    public void setIsRTLDir(int isRTLDir)
    {
        this.isRTLDir = isRTLDir;
    }
}
