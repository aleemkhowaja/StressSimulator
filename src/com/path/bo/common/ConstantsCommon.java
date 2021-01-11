package com.path.bo.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import com.path.lib.common.util.StringUtil;

public class ConstantsCommon
{
    public static final String APP_VERSION = "0300000000000391";
    public static final String APP_NAME = "RET";
    // Application common component version, specified during build process
    public static final String COMMON_COMP_VERSION = "-------";
    public static final String APP_BUILD_DATE_TIME = "";
    
    //display version
    public static final String DISPlAY_APP_VERSION = "CSM J13 0 0RC 77";
    
    // ITRS_J14_0_0RC_49
    // CSM_J13_0_0RC_77
    /**
     * Mappers encryption management
     */
    public static final String MAPPERS_ENCRYPTION_PASS = "ENC_PASS";
    /**
     * Internal Build Version ApplicationName.Year.DayOfYear.BuildWithinTheDay
     */
    public static final String APP_INTERNAL_BUILD_VERSION = "CSM.13.121.1";

    public static final String FILE_ENCODING = "UTF8";
    
    public static final String FILE_ENCODING_CP1256 = "windows-1256";

    public static final String ENCODING_TYPE_ASCII = "ASCII";
    
    public static final String ENCODING_TYPE_UTF = "UTF-8";
    
    // decimal and Group Separators as Global
    public static String PATH_GROUP_SEPARATOR ;
    public static String PATH_DECIMAL_SEPARATOR;

    // black list flag
    public static final String BLACKLISTENABLED = "1";

    // login Password Page REdirection
    public static final int NO_OLD_PWD_UPON_LOGIN = 2;
    public static final int OLD_PWD_UPON_LOGIN_EXSTS = 3;
    public static final int OLD_PWD_UPON_PWD_EXPIRY = 4;
    public static final int PASSWD_SET_BY_ADMIN = 5;
    public static final String USER_PWD_FREQ_CHANGE_NONE = "N";

 // TP#705200 Vijayakumar M on 28/08/2018
    public static final String SHARED_AMOUNT = "S";
    public static final String AVAILABLE_FOR_NEXT_DISBURSEMENT = "A";
    
    // attribute used to get session Object
    public static final String SESSION_VO_ATTR = "sesVO";
    // Constant to specify whether to Enable or Disable Cache on Server Side
    public static final int ENABLE_CACHE = 1;
    // disable static files(js,css) caching 
    public volatile static int ENABLE_STATIC_CACHING;
    // Enable tracking of transactions' update after approve changes 
    public volatile static int ENABLE_TRACKING_CHANGES;
    // Constant to specify if the screen is for Customization Mode
    public volatile static int SCREEN_CONFIG = 1;
    // Constant to specify view SQL icon is available in Customization screen
    public volatile static int SCREEN_CONFIG_VIEW_SQL = 1;
    // whether the Tooltip translation will be enabled by the component or Not.
    public volatile static int ENABLE_TOOLTIP_TRANS = 1;
    // whether to enable reference DB Translation Sending
    public volatile static int ENABLE_REFERENCE_DB_TRANS_PUSH;

    // whether to enable record log feature
    public volatile static int ENABLE_RECORDS_LOG_FEATURE;

    public static volatile int CURR_DBMS_ORACLE;
    public static volatile int CURR_DBMS_SYBASE;
    public static volatile int CURR_DBMS_SQLSERVER;
    public static final String ORACLE_DBMS = "oracle";
    public static final String SYBASE_DBMS = "sybase";
    public static final String SQLSERVER_DBMS = "sqlserver";
    public static final int DATABASE_DISCONNECTION_CODE = 0;
    public static final int DATABASE_DISCONNECTION_CODE_ORA = 17002;
    // to trace SQL session upon DB access
    public static volatile boolean SQL_SESSION_TRACE_CODE;
    // to trace All SQLs and not just update/delete/procedure
    public static volatile boolean SQL_SESSION_TRACE_ALL_CODE;
    // flag to enable trace of HTTP sessions in side SQL sessions
    public static volatile boolean SQL_SESSION_HTTP_TRACE_CODE;
    // help Server URL initialized at Client
    public volatile static String HELP_SERVER_URL;

    //440134 Translation Keys with Non Case Sensitivity BLME Issue
    public static volatile BigDecimal DB_CASE_SENSITIVITY = BigDecimal.TEN;
    
    // S_APP Type Column , no exact meaning for every value
    public static final String CUSTOMER_TYPE_A = "A";
    public static final String CUSTOMER_TYPE_N = "N";

    // User Statuses
    public static final String USR_LOGGED_STS = "L";
    public static final String USR_SUSPENDED_STS = "S";
    public static final String USR_DELETED_STS = "D";
    public static final String USR_TO_BE_DELETED_STS = "T";
    public static final String USR_AUTHORIZED_STS = "A";
    public static final String USR_NEW_STS = "N";
    public static final String USR_REACTIVATE_STS = "R";

    // User Preferred Language
    public static final String PREFERED_LANG_LATIN = "L";
    public static final String PREFERED_LANG_ARABIC = "A";
    public static final String PREFERED_LANG_BOTH = "B";
    public static final String SPECIAL_CHARACTERS = "S";

    // Teller Statuses
    public static final String TELLER_DELETED_STS = "D";
    public static final String TELLER_SUSPENDED_STS = "S";
    public static final String TELLER_INACTIVE_STS = "I";

    // show short long Name indicator
    public static final String INDICATOR_BOTH_BRIEF_LONG_NAME = "B";
    public static final String INDICATOR_LONG_NAME = "L";

    // CRUD statuses
    public static final String CRUD_MAINTAIN = "R";
    public static final String CRUD_APPROVE = "P";
    public static final String CRUD_SUSPEND = "S";
    // Document Lookup statuses
    public static final String STATUS_ISSUED = "I";
    public static final String STATUS_APPROVED = "P";
    public static final String STATUS_CANCELED = "N";
    public static final String STATUS_CLOSED = "Z";
    public static final String STATUS_V_REJECTED = "V";
    
    // kaldanaf - lookup constants     
    public static final BigDecimal TRX_TYPE_AMEND_MARGIN = BigDecimal.valueOf(1075);
    public static final BigDecimal TRX_TYPE_SUSPENSE_SETTLEMENT = BigDecimal.valueOf(1080);
    public static final BigDecimal TRX_TYPE_OUTSIDE_CHARGES = BigDecimal.valueOf(1085);
    public static final BigDecimal TRX_TYPE_OTHER_COMMISSION = BigDecimal.valueOf(2035);
    
    
    // from margin lookup status
    public static final String MARGIN_LOOKUP_STATUS = "'" + STATUS_ISSUED + "','" + STATUS_APPROVED
    + "','" +  STATUS_CLOSED + "','" + STATUS_V_REJECTED + "','" + STATUS_CANCELED + "'";
    
    public static final String MARGIN_LOOKUP_STATUS_LC = "'" + STATUS_ISSUED + "','" 
    +  STATUS_CLOSED + "','" + STATUS_V_REJECTED + "','" + STATUS_CANCELED + "'";
    
    // from suspense settlement lookup status
    public static final String SUSPENSE_SETTLEMENT_LOOKUP_STATUS = "'" + STATUS_APPROVED + "','" + STATUS_CLOSED + "'";
    
    public static final String SUSPENSE_SETTLEMENT_LOOKUP_LC = "'" + STATUS_ISSUED + "','" 
    +  STATUS_CLOSED + "'";

    // Payment Methods
    public static final String PAYMENT_METHOD_DEMAND_DRAFT = "D";

    // Applications
    public static final String RET_APP_NAME = "RET";
    public static final String IBIS_APP_NAME = "IBIS"; // IBIS is the Dashboard
    public static final String usmAppName = "USM";
    public static final String SADS_APP_NAME = "SADS";
    public static final String REP_APP_NAME = "REP";
    public static final String RADM_APP_NAME = "RADM";
    public static final String IMAL_APP_NAME = "IMAL";
    public static final String ACC_APP_NAME = "ACC";
    public static final String NV_APP_NAME = "NV";
    public static final String TODO_APP_NAME = "TODO";
    public static final String TFA_APP_NAME = "TFA";
    public static final String FMS_APP_NAME = "FMS";
    public static final String IIS_APP_NAME = "IIS";
    public static final String PROV_APP_NAME = "PROV";
    public static final String ITRS_APP_NAME = "ITRS";
    public static final String LEND_APP_NAME = "LEND";
    public static final String ICOR_APP_NAME = "ICOR";
    public static final String IRET_APP_NAME = "IRET";
    public static final String LCOR_APP_NAME = "LCOR";
    public static final String LRET_APP_NAME = "LRET";
    public static final String IVRL_APP_NAME = "IVRL";
    public static final String IMIG_APP_NAME = "IMIG";
    public static final String IPRC_APP_NAME = "IPRC";
    public static final String IRVL_APP_NAME = "IRVL";
    public static final String ITR_APP_NAME = "ITR";// FATCA, ITR module
    public static final String ESHR_APP_NAME = "ESHR";
    public static final String ESPL_APP_NAME = "ESPL";
    public static final String ALERT_APP_NAME = "ALRT";
    public static final String IMCO_APP_NAME = "IMCO";
    public static final String UPG_APP_NAME = "UPG";
    public static final String CMS_APP_NAME = "CMS"; // Cache Management Application
    public static final String IFRS_APP_NAME = "SCOR";
    public static final String PROF_APP_NAME = "PROF";
    public static final String RCSA_APP_NAME = "RCSA";
    public static final String OADM_APP_NAME = "OADM"; //OMNI CHANNEL ADMIN
    public static final String IMEN_APP_NAME = "IMEN"; //integration manager
    public static final String MON_APP_NAME = "MON"; // monitor Module
    public static final String SWEN_APP_NAME = "SWEN"; // Swift Engine
    public static final String SWFT_APP_NAME = "SWFT"; // Swift
    public static final String SCHD_APP_NAME = "SCHD"; // Scheduler
    public static final String ALRE_APP_NAME = "ALRE"; // Alert Engine
    public static final String SFEN_APP_NAME = "SFEN"; // Safe  Engine
    public static final String RAS_APP_NAME = "RAS"; // Reuter Automation server
    public static final String CDG_APP_NAME = "CDG"; // Channel Deliver Gateway
    public static final String SFTL_APP_NAME = "SFTL"; // Safe Tools
    public static final String PROC_PB_APP_NAME = "PROC"; //Proc PB application
    public static final String API_APP_NAME = "API"; //Integration application
    public static final String OAPI_APP_NAME =  "OAPI"; //Open API 907642
    public static final String PROJECT_FINANCE_APP_NAME = "PRFN"; //project finance application TP 742180
    public static final String SOA_APP_NAME = "SOASIMILATION"; //to management the calls that will comes from SOA web services there is no such application.
    public static final String JBPM_APP_NAME = "JBPMSIMILATION"; //to management the calls that will comes from JBPM web services there is no such application.
    public static final String ASSETS_PARAM_APP_NAME = "PMSP";//assets param module tp 984569
    //flag to indicate wheteher application must login with comp branch ,comp only on nothing.
    public static final String COMP_BRANCH = "0";
    public static final String ONLY_COMP = "1";
    public static final String NO_COMP_BRANCH = "2";

    // opt used in applications
    public static final String REPORTS_OPT = "R00";
    public static final String REPORTS_OPT1 = "R000";
    public static final String REPORTS_OPT2 = "R0";
    public static final String REPORTS_OPT3 = "0000";// TFA Reports Menu Prog REF
    public static final String REPORTS_OPT4 = "U000";// FMS Reports Menu Prog REF
    public static final String SETTINGS_CONFIG_OPT = "SCRSETTCONF";
    public static final String LABELING_CONFIG_OPT = "LBLTRANSCONF";
    public static final String LOV_TRANSLATION_TAB = "LOVTRANSTAB";
    public static final String DEFAULT_PRINTER_AXS = "USRPRNTAXS";
    public static final String DASH_PORTAL_WIDGET_LIST_OPT = "SYSTWDGTLST";
    public static final String DASH_WKSPCE_WIDGET_CUST_OPT = "WKSPCEWDGTCUST";
    public static final String DASH_ASSGN_USR_WIDGET_OPT = "ASSGNPRTLUSR";
    public static final String SWITCH_VIEW_OPT = "SWITCHVIEW";
    public static final String SAVE_AS_OPT = "SAVEASSCR";
    public static final String ALLOW_DISABLE_USER_CUSTOMIZATION_OPT = "DSBLUSRCUSTAXS";				
    public static final String NEW_SESSION_OPT = "NEWSESSION";
    public static final String SESSION_FORCE_LOGOUT = "FORCELOGOUT";
    public static final String USR_ALLWD_PRTLT_AXS = "USRALLWDPRTLT";
    public static final String TECH_DETAILS_OPT = "TECHDETAILS";
    public static final String TECH_LOGS_OPT = "TECHLOGSAXS";
    public static final String BTN_CONFING_OPT = "SCRBTNCONF";
    public static final String USER_THEME_OPT = "USERTHEMECONF";
    /**
     * Set User Default Preferences
     */
    public static final String GRID_COL_SORT_OPT = "GRIDSORTAXS";
    public static final String DYN_CLNT_PARAMS_EDIT_OPT = "DYNCLTPRMSSAV";
    public static final String DYN_CLNT_PARAMS_APPROVE_OPT = "DYNCLTPRMSAPP";
    public static final String DYN_CLNT_PARAMS_COLS_EDIT_OPT = "DYNCLTPRMSCOLS";
    public static final String GLOBAL_ACTIVITY_OPT = "GLOBALACTAXS";
    public static final String DYN_SCR_GEN_OPT = "DYNSCRGEN";
    public static final String EXP_IMP_CUST_OPT = "EXPIMPCUSTAXS";
    public static final String SET_AS_DEFAULT_PAGE_OPT = "SETDFLTPAGAXS";

    public static final String LANGUAGE_ENGLISH = "EN";
    public static final String LANGUAGE_ARABIC = "AR";
    public static final String LANGUAGE_FRENCH = "FR";
    public static final String LANGUAGE_FARISI = "FA";
    public static final String LANGUAGE_RUSSIAN = "RU";
    public static final String LANGUAGE_TURKISH = "TK";

    public static final String PROGREF_ROOT = "ROOT";
    public static final BigDecimal CUST_ARABIC_FIELD = BigDecimal.ONE;
    public static final BigDecimal CUST_LATIN_FIELD = BigDecimal.valueOf(2);

    public static final String CIF_APP_NAME = "CIF";
    public static final String TRX_APP_NAME = "TRX";
    public static final String SUKUK_APP_NAME = "SKK";
    public static final String DIRECT_INVEST_APP_NAME = "DINV";
    public static final String ASSETS_APP_NAME = "PMS";
    public static final String PROC_APP_NAME = "MTS";   
    public static final String AML_APP_NAME = "AML";
    
    public static final String PENDING_STATUS = "P";// LBedrane  on 18/01/2018 copying KIB170005 US = 605700 from PB to Java 
    

    public static final String ACCOUNT_SIGN_DEBIT = "D"; 
    public static final String ACCOUNT_SIGN_CREDIT = "C";
    public static final String ACCOUNT_SIGN_BOTH = "B";

    // BY JIHAD CTSCONTROL

    public static final Integer CBK_KUWAIT = 1;
    public static final Integer PMA_Palestine = 2;
    public static final Integer BMA_BAHREIN = 3;
    public static final Integer QCB_QUATAR = 4;
    public static final Integer SAMA_KSA = 5;
    public static final Integer CB_JORDAN = 6;
    public static final Integer BOE_ENGLAND = 7;
    public static final Integer SBP_PAKISTAN = 8;
    public static final Integer CBS_SYRIA = 9;
    public static final Integer BNM_Malaysia = 10;
    public static final Integer CBK_KENYA = 11;
    public static final Integer CBL_LEBANON = 12;
    public static final Integer CBS_SUDAN = 13;
    public static final Integer CBS_SRILANKA = 14;
    public static final Integer CBY_YEMEN = 15;
    public static final Integer CBOS_SUDAN = 16;
    public static final Integer BA_ALGERIA = 17;
    public static final Integer CBUAE_UAE = 18;
    public static final Integer CBI_IRAQ = 19;
    public static final Integer MMA_MALDIVES = 20;
    public static final Integer CENTRAL_BANK_OF_GUINEE = 21;
    public static final Integer CENTRAL_BANK_OF_SENEGAL = 22;
    public static final Integer CENTRAL_BANK_OF_NIGER = 23;
    public static final Integer CENTRAL_BANK_OF_MAURITANIE = 24;
    public static final Integer CENTRAL_BANK_OF_OMAN = 25;

    /**
     * Status of CIF
     */
    public static final String CIF_INACTIVE = "I";
    public static final String CIF_ACTIVE = "A";
    public static final String CIF_SUSPENDED = "S";
    public static final String CIF_DELETED = "D";
    public static final String CIF_COMPLETED = "C";
    public static final String CIF_ALL = "X";
    public static final String CIF_DRAFT = null;

    // CIF Resident and non Resident Flag
    public static final String CIF_RESIDENT = "R";
    public static final String CIF_NOT_RESIDENT = "S";
    public static final String CIF_RESIDENT_AND_NOT_RESIDENT = "B";

    // CIF Branch Selection Flag (raFlag in PB)
    public static final String CIF_All_BRANCHES = "I";
    public static final String CIF_SPECIFIC_BRANCHES = "N";

    // BIC CODE limit
    public static final BigDecimal BIC_CODE_LIMIT_12 = BigDecimal.valueOf(12);
    public static final BigDecimal BIC_CODE_LIMIT_11 = BigDecimal.valueOf(11);

    public static final BigDecimal EMPTY_BIGDECIMAL_VALUE = BigDecimal.valueOf(-9999999);

    // BS_CONTRA
    public static final String ON_BALANCE_SHEET = "B";
    public static final String OFF_BALANCE_SHEET = "C";
    public static final String ON_OFF_BALANCE_SHEET = "X";

    // General Ledger Type
    public static final String INTERNAL_GEN_LEDGER_TYPE = "I";
    public static final String NON_INTERNAL_GEN_LEDGER_TYPE = "N";

    public static final String XOR = "X";

    // CB_IND
    public static final String CB_CLIENT = "C";

    // Trs Type for CTSCTR
    public static final String TRS_CHEQUEBOOK = "6";
    public static final String TRS_MCE = "M";
    public static final String TRS_FROMTO_CHEQUEBOOK = "8";
    public static final BigDecimal TRS_TYPE_LOV_TYPE = BigDecimal.valueOf(201);

    /**
     * End of Link to other transaction messages BLOCK
     */

    // General Ledger Type
    public static final String INTERNAL_GL_TYPE = "I";
    public static final String NON_INTERNAL_GL_TYPE = "N";
    public static final String BOTH_GL_TYPE = "X";

    // RIFATT AFFECTION
    public static final String NOT_APPLICABLE_AFFECTION = "0";
    public static final String PASSBOOK_AFFECTION = "1";
    public static final String CHEQUEBOOK_AFFECTION = "2";

    public static final String CATEGORY_TERM = "T";
    public static final String CATEGORY_DEMAND = "D";

    public static final BigDecimal DET_IND_REACTIVATE_CHARGE = BigDecimal.valueOf(6);

    // Message types used in BOExceptions to specify error, confirm,...
    public static final Integer ERROR_MSG_TYPE = 1;
    public static final Integer CONFIRM_MSG_TYPE = 2;
    public static final Integer MENU_INFO_MSG_TYPE = 3; // used for Left Menu
    // exceptions only
    public static final Integer WARNING_INFO_MSG_TYPE = 4;

    public static final String INACTIVE_ACCOUNT = "I";
    public static final String ACTIVE_ACCOUNT = "A";
    public static final String OPENED_ACCOUNT = "O";
    public static final String DORMANT_ACCOUNT = "T";
    public static final String DELETED_ACCOUNT = "D";
    public static final String APPLY_FOR_CLOSURE_ACCOUNT = "P";
    public static final String SUSPENDED_ACCOUNT = "S";
    public static final String GMI_FIXED = "F";
    public static final String GMI_GENERAL = "G";
    public static final String GMI_INTERNAL = "I";
    public static final Integer CURRENCY_LEGNTH = 3;
    public static final Integer GL_LEGNTH = 6;
    public static final Integer CIF_LEGNTH = 8;
    public static final Integer SL_LEGNTH = 3;

    // password change constants
    public static final String CASE_UPPER = "C";
    public static final String CASE_LOWER = "S";
    public static final String CASE_COMBINED = "B";
    public static final String CASE_NUMERIC_ONLY = "U";
    public static final String CASE_CHAR_ONLY = "H";
    public static final String CASE_CHAR_AND_NUM = "B";

    public static final String CSM_APP_NAME = "RET";
    
    // Rabih
    // CIFCONTROL AND_OR Flag to set the conditions operation type of the
    // Blacklist query
    public static final String CIF_AND = "A";
    public static final String CIF_OR = "O";

    // Black list table names abbreviation
    public static final String BLCK_MOS = "M";
    public static final String BLCK_OFAC = "O";
    public static final String BLCK_UN = "U";

    // AMF STATUS
    public static final String STATUS_CREATED = "C";

    // BRANCH SESSION STATUS
    public static final String BRANCH_SESSION_OPEN = "O";
    public static final String BRANCH_SESSION_CLOSE = "C";

    public static final String REMITTANCE_ALL_CURRENCIES = "A";
    public static final String REMITTANCE_TRX_CURRENCY = "T";
    public static final String REMITT_BY_HEAD_TELLER = "1";
    public static final String REMITT_TYPE_PRIZE_BOND = "PB";
    
    // BlackList confirmation
    public static final String BLACKLISTED_CIF_CONFIRMATION = "BLACKLISTED_CIF_CONFIRMATION";
    public static final String SUSPICIOUS_CIF_CONFIRMATION = "SUSPICIOUS_CIF_CONFIRMATION";

    public static final String NO_DATA_FOUND = "No Data Found found";

    // Close Account
    public static final String SHOW_DEBIT_CARD_ACC_LIST = "SHOW_DEBIT_CARD_ACC_LIST";
    
    // YES/NO
    public static final String YES = "Y";
    public static final String NO = "N";
    public static final String OPTIONAL = "O";
    public static final String CONSIDER_ONCE_FOR_MULTI_TRX = "1";
    public static final BigDecimal YES_NO_OPTION = BigDecimal.valueOf(25);
    public static final BigDecimal LOV_TRANS_ID_YES_NO = BigDecimal.valueOf(43);

    public static final String ZERO = "0";
    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String JV = "JV";
    public static final BigDecimal STATUS_LOV = BigDecimal.valueOf(32);
    public static final String SHOW_ALL_TRX = "1";
    public static final String MINIMUM_DATE = "01/01/1900";
    public static final String MAXIMUM_DATE = "31/12/2100";
    // JIHAD COMPANIES
    public static final String EXTEND_MAT_DTE_HOL_ON = "1";
    public static final String EXTEND_MAT_DTE_HOL_OFF = "0";

    //
    // yesr status
    public static final String YEAR_STATUS_CLOSED = "C";

    // Scan fixed values
    // Scanned Documents path (in the repository)
    public static final String SCANNED_PATH = "scanned";
    
    // Scanned Image extension
    public static final String SCANNED_EXT = ".jpg";

    // Panini Documents path (in the repository)
    public static final String PANINI_PATH = "panini";
    public static final String TWAIN_PATH = "TWAIN";

    // Panini Image extension
    public static final String PANINI_EXT = ".jpg";

    // Signature constants
    // Signed Documents path (in the repository)
    public static final String SIGNED_PATH = "signed";

    // Topaz Signature Pad tablet Model
    public static final String SIGN_PAD_MODEL = "SignatureGem1X5";

    // Topaz Signature Pad tablet Port
    public static final String SIGN_PAD_PORT = "HID1";

    // Signed Image extension
    public static final String SIGNED_EXT = "JPG";

    public static final String TRXTYPE_BLOCK_MODIFY = "1";
    public static final String NEW_LINE = "\n";

    // PTH_CTRL

    public static final String UNIT_IS_MANDATORY = "1";
    public static final String SHOW_UNIT = "2";
    public static final String DONT_SHOW_UNIT = "3";

    public static final String LOW_STAT_CHG_DATE_OFF = "0";
    public static final String LOW_STAT_CHG_DATE_ON = "1";

    public static final String SIGN_MODE_OFF = "0";
    public static final String SIGN_MODE_ON = "1";

    public static final BigDecimal ACCOUNT_STATUS_LOV_TYPE = BigDecimal.valueOf(64);
    //Bug # 346812 -- [John Massaad] 
    public static final BigDecimal MANUAL_JV_STATUS_LOV_TYPE = BigDecimal.valueOf(262);
    //Bug # 346812 -- [John Massaad] 
    // Passbook layout
    public static final BigDecimal PASSBOOK_LAYOUT_LOV_TYPE = BigDecimal.valueOf(114);
    public static final BigDecimal PASSBOOK_STATUS_LOV_TYPE = BigDecimal.valueOf(91);
    /**
     * cards LOV Types
     */
    public static final BigDecimal CARDS_MANAGEMENT_STATUS_LOV_TYPE = BigDecimal.valueOf(92);
    public static final BigDecimal CARDS_MANAGEMENT_PRIMSUPP_LOV_TYPE = BigDecimal.valueOf(14);
    public static final BigDecimal LOST_DOC_STATUS_LOV_TYPE = BigDecimal.valueOf(81);

    // COMMON CONSTANTS
    public static final String LESS_THAN = "LT";
    public static final String GREATER_THAN = "GT";

    // multiply / divide exchange rate
    public static final String MULTIPLY = "M";
    public static final String DIVIDE = "D";
    public static final String MID_RATE = "M";

    // Flag for identifying modes such as on load, change events etc...
    public static final String ON_LOAD_MODE = "load";
    public static final String ON_CHANGE_MODE = "change";

    // s_app
    public static final String GEN_ACC_DESC_CIF_YES = "Y";
    public static final String GEN_ACC_DESC_CIF_NO = "N";

    // CTSCONTROL.SHOW_OFFICER_IN_TRX
    public static final String SHOW_OFFICER_IN_TRX_YES = "1";
    public static final String SHOW_OFFICER_IN_TRX_NO = "0";
    //

    public static final String DELETE_CIF_COLLAT_ON = "1";
    public static final String DELETE_CIF_COLLAT_OFF = "0";
    //

    // ACC_NV_CONTROL

    public static final String APPL_DATE_IS_SUB_DATE_YES = "1";
    public static final String APPL_DATE_IS_SUB_DATE_NO = "0";

    public static final String DIV_DEPT_NOT_APPLICABLE = "0";
    public static final String DIV_DEPT_FROM_CIF = "1";
    public static final String DIV_DEPT_FROM_TELLER = "2";

    public static final String DO_NOT_SHOW_IBAN = "0";
    public static final String SHOW_IBAN = "1";
    public static final String SHOW_IBAN_GENERATE = "2";

    public static final BigDecimal MAXIMUM_CIF = BigDecimal.valueOf(99999999);
    public static final BigDecimal MAXIMUM_SL = BigDecimal.valueOf(999);

    public static final BigDecimal MAXIMUM_AMOUNT = BigDecimal.valueOf(99999999999.999);
    // ADED BY JIHADL
    public static final BigDecimal MAXIMUM_EXCH_RATE = BigDecimal.valueOf(9999999.999999);
    public static final BigDecimal MAXIMUM_RATE = BigDecimal.valueOf(100);
    //

    // OPT details
    public static final String OPT_URL = "OPT_URL";
    public static final String OPT_WINDOW_NAME = "WINDOW_NAME";
    public static final String OPT_IV_CRUD = "IV_CRUD";
    public static final String PARENT_REF = "PARENT_REF";
    public static final String OPT_REFERENCE = "OPT_REFERENCE";
    public static final String SAVED_AS_OPT_URL = "SAVED_AS";
    public static final String DYNAMIC_OPT_SAVED_SCREEN = "3"; 
    public static final String DYNAMIC_OPT_URL = "DYNAMIC";
    public static final String DYN_SCREEN_ID   = "DYN_SCREEN_ID";
    public static final String DYNAMIC_OPT_DYN_SCREEN = "5";
    
    public static final String REPORTS_OPT_URL = "REPORT";
    //TP 475542
    public static final String TARGET_APP_URL = "TARGET_APP";
    public static final String PDF_REP_FORMAT = "PDF";
    public static final String DOC_REP_FORMAT = "DOC";
    public static final String XLS_REP_FORMAT = "XLS";
    public static final String HTML_REP_FORMAT = "HTML";
    public static final String CSV_REP_FORMAT = "CSV";
    public static final String CSV_EXT_REP_FORMAT = "CSVEXT";
    public static final String EXCEL_ROW_DATA_REP_FORMAT = "RDXLS";
    public static final String TEXT_ROW_DATA_REP_FORMAT = "RDTXT";
    public static final String SQL_REP_FORMAT = "SQL";
    public static final String ODS_REP_FORMAT = "ODS";
    public static final String OPT_FCR_EXTENSION = "D00";
    public static final String REP_DYNAMIC_PREV_PAGE = "dynamicPrevPage";
    public static final String REP_DYNAMIC_PAGE = "dynamicPage";
    public static final String PDF_EXT = "pdf";
    public static final String DOC_EXT = "docx";
    public static final String XLSX_EXT = "xlsx";
    public static final String XLS_EXT = "xls";
    public static final String TXT_EXT = "txt";
    public static final String SQL_EXT = "sql";
    public static final String HTML_EXT = "html";
    public static final String TXT_FILE = "TXTFILE";
    public static final String DAT_EXT = "DAT";
    public static final String ZIP_EXT = "zip";
    public static final String JRXML_EXT = "jrxml";
    public static final BigDecimal FILE_FORMAT_LOV_TYPE = BigDecimal.valueOf(8);
    /**
     * Dashboard
     */
    public static final String FROM_DASHBOARD = "fromDashboard";
    public static final BigDecimal DASH_ALL_BRANCHES = BigDecimal.valueOf(2);
    public static final BigDecimal DASH_LOGIN_BRANCH = BigDecimal.ONE;
    public static final BigDecimal SAFEBOXES_LOV_TYPE = BigDecimal.valueOf(109);
    public static final BigDecimal CHEQUEBOOK_LOV_TYPE = BigDecimal.valueOf(38);
    public static final BigDecimal PERIODICITY_LOV_TYPE = BigDecimal.valueOf(12);
    public static final BigDecimal PROCESS_LOV_TYPE = BigDecimal.valueOf(197);
    public static final BigDecimal CREDIT_AVAILABILITY_LOV_TYPE = BigDecimal.valueOf(198);
    public static final BigDecimal STATUS_LOV_TYPE = BigDecimal.valueOf(10);
    public static final String INVESTMENT_QUERY_REF = "Q360N05";
    public static final BigDecimal PORTFOLIO_POSITION_SUMMARY_LOV_TYPE = BigDecimal.valueOf(227);
    public static final String DASH_ALL_SECURITIES = "All Securities";
    public static final BigDecimal FORBID_RESTRICT_TRX_LOV_TYPE = BigDecimal.valueOf(482);
    public static final BigDecimal FORBID_PRODUCT_LOV_TYPE = BigDecimal.valueOf(483);
    public static final BigDecimal REMITTANCE_TYPE_LOV_TYPE = BigDecimal.valueOf(231);
    public static final BigDecimal INVESTMENT_STATUS_LOV_TYPE = BigDecimal.valueOf(187);
    public static final String ASSETS_ACC = "A";
    public static final String LIABILTIES_ACC = "L";
    // Message used to inform fill the mandatory smart fields,...
    public static final String SMART_MAND_INFO_MSG = "SMART_MAND_INFO_MSG";

    // DB refrence for depositStatus
    public static final BigDecimal CLEAR_VOID_STS_LOV_TYP_ID = BigDecimal.valueOf(117);

    // Reports arguments
    public static final String REPORT_ARGUMENT_SEPARATOR = "~#~";
    public static final String CTS_REP_ARG_COMPANY = "C";
    public static final String CTS_REP_ARG_BRANCH = "B";
    public static final String CTS_REP_ARG_TRX_NO = "N";
    public static final String CTS_REP_ARG_FROM_TRX = "F";
    public static final String CTS_REP_ARG_TO_TRX = "T";
    public static final String CTS_REP_ARG_CURRENCY = "Y";
    public static final String CTS_REP_ARG_GL = "G";
    public static final String CTS_REP_ARG_CIF = "I";
    public static final String CTS_REP_ARG_SL = "S";
    public static final String CTS_REP_ARG_TRX_TYPE = "P";
    public static final String CTS_REP_ARG_USER_ID = "U";
    public static final String CTS_REP_ARG_DATE = "D";
    public static final int PATHSOL_INTERNAL_ERROR = 600; // custom error code

    public static final String CREATE_MODE = "C";
    public static final String READ_MODE = "R";
    public static final String UPDATE_MODE = "U";
    public static final String DELETE_MODE = "D";

    /**
     * LOV Order MODE, the order by which the list of values will be sorted
     */
    public static final String LOV_ORDER_NONE = "NONE";
    public static final String LOV_ORDER_VALUE = "VALUE";
    public static final String LOV_ORDER_BY_SPECIFIC_ORDER = "ORDER";

    public static final String GENDER_MALE = "M";
    public static final String GENDER_FEMALE = "F";

    // teller Cash Balance Report References
    public static final String TELLER_CASH_BALANCE_OPT = "L000L";
    public static final String REPORT_TELLER_CASH_BALANCE = "RETTLCSBL";
    public static final String REPORT_TELLER_CASH_BALANCE_TOTAL = "RETTLCSTO";

    // dashboard Access right
    public static final String DASH_TOTAL_ASS_LIAB_OPT = "Q360N01";
    public static final String DASH_FIN_APP_APR_LINE_OPT = "Q360N02";
    public static final String DASH_CUSTOMER_GARDING_OPT = "Q360N03";
    public static final String DASH_FINANCING_DEALS_OPT = "Q360N04";
    public static final String DASH_INVESTMENTS_OPT = "Q360N05";
    public static final String DASH_PRODUCTS_OPT = "Q360N06";
    public static final String DASH_OTHER_OPT = "Q360N07";
    public static final String DASH_TREASURY_OPT = "Q360N08";
    public static final String DASH_CHEQUES_OPT = "Q360N09";
    public static final BigDecimal DASH_REMITTANCE_STATUS_LOV_TYPE = BigDecimal.valueOf(30);

    // Running Dates Access Rights
    public static final String USER_RUNNING_DATE_OPT = "A201";
    public static final String APP_RUNNING_DATE_OPT = "A20";
    public static final String ALL_SECURITIES_KEY = "All_Securities_key";

    /**
     * used to get the list of Users that have access to reactivate the
     * suspended users
     */
    public static final String USERS_OPT_REACTIVATE = "M006R";

    // Added by jihadlamaa Op Orgin TFA
    public static final String ORIGIN_TFA = "F";
    public static final String ORIGINE_DAILY = "D";

    // Header line
    public static final BigDecimal HEADER_LINE_ZERO = BigDecimal.valueOf(0);

    // JV TYPES line
    public static final BigDecimal TFA_JV_TYPE_961 = BigDecimal.valueOf(961);
    // decimal point of exchrate
    public static final BigDecimal EXCH_RATE_DECIMAL_PT = BigDecimal.valueOf(6);

    public static final BigDecimal MINIMUM_YEAR = BigDecimal.valueOf(1900);

    // IIS deals prog REF needed in Additional Fields
    public static final String INV_DEAL_PROG_REFERENCE = "IIST02";

    // Exch rate Type
    public static final String CASH_RATE = "C";
    public static final String TRANSFER_RATE = "T";
    //

    // IBISCOUNTER TRX TYPE
    public static final String IBIS_TRX_TYPE_001 = "001";
    public static final String IBIS_TRX_TYPE_002 = "002";
    public static final String IBIS_TRX_TYPE_003 = "003";
    public static final String IBIS_TRX_TYPE_005 = "005";
    public static final String IBIS_TRX_TYPE_045 = "045";
    public static final String IBIS_TRX_TYPE_060 = "060";
    public static final String IBIS_TRX_TYPE_1 = "1";
    public static final String IBIS_TRX_TYPE_122 = "122";
    public static final String IBIS_TRX_TYPE_123 = "123";
    public static final String IBIS_TRX_TYPE_900 = "900";
    public static final String IBIS_TRX_TYPE_910 = "910";
    public static final String IBIS_TRX_TYPE_API = "API";
    public static final String IBIS_TRX_TYPE_ARC = "ARC";
    public static final String IBIS_TRX_TYPE_ATM = "ATM";
    public static final String IBIS_TRX_TYPE_DOF = "DOF";
    public static final String IBIS_TRX_TYPE_DUE = "DUE";
    public static final String IBIS_TRX_TYPE_FMA = "FMA";
    public static final String IBIS_TRX_TYPE_GAC = "GAC";
    public static final String IBIS_TRX_TYPE_HDT = "HDT";
    public static final String IBIS_TRX_TYPE_HST = "HST";
    public static final String IBIS_TRX_TYPE_IAC = "IAC";
    public static final String IBIS_TRX_TYPE_JVT = "JVT";
    public static final String IBIS_TRX_TYPE_KAC = "KAC";
    public static final String IBIS_TRX_TYPE_PSR = "PSR";
    public static final String IBIS_TRX_TYPE_PTS = "PTS";
    public static final String IBIS_TRX_TYPE_TJV = "TJV";
    public static final String IBIS_TRX_TYPE_TMP = "TMP";

    // Document Types
    public static final String DOC_TYPE_IMPORT_LC = "O";
    public static final String DOC_TYPE_EXPORT_LC = "I";
    public static final String DOC_TYPE_LG = "G";
    public static final String DOC_TYPE_IMPORT_BILL = "N";
    public static final String DOC_TYPE_EXPORT_BILL = "U";
    public static final String DOC_TYPE_TRX = "T";
    public static final String DOC_TYPE_IMPORT_BILL_DECLARATION = "C";
    public static final String DOC_TYPE_DOMICILATION = "D";
    public static final String DOC_TYPE_D11 = "1";
    public static final String DOC_TYPE_D10 = "0";
    public static final String DOC_TYPE_ATTESTATION_SEVICES = "3";

    // Document Type lov type
    public static final BigDecimal DOCUMENT_TYPE_LOV_TYPE = BigDecimal.valueOf(131);
    public static final BigDecimal LC_LOV_TYPE = BigDecimal.valueOf(263);
    public static final BigDecimal SETTLEMENT_METHOD_LOV_TYPE = BigDecimal.valueOf(272);
    public static final BigDecimal LG_STATUS_LOV_TYPE = BigDecimal.valueOf(390);
    public static final BigDecimal BILL_STATUS_LOV_TYPE = BigDecimal.valueOf(397);
    public static final BigDecimal TRANSFER_MODE_LOV_TYPE = BigDecimal.valueOf(40);

    // Funding Methods
    public static final String FUND_METHOD_LG_FINANCING = "E";
    public static final String FUND_METHOD_LG_APPLICANT_ACCOUNT = "C";
    public static final String FUND_METHOD_FINANCING = "I";
    public static final String FUND_METHOD_APPLICANT_ACCOUNT = "B";

    // TRX_TYPE ACCEPTANCE
    public static final BigDecimal TRX_TYPE_ACCEPTANCE = BigDecimal.valueOf(1040);

    // these constant sis used to set the buinse criteria required
    // f i need all GMI types so i set my criteria TfaCommonConstant.GMI_ALL
    // if i need general and fixed so TfaCommonConstant.GMI_FIXED_GEN

    public static final String GMI_ALL = "'" + ConstantsCommon.GMI_GENERAL + "','" + ConstantsCommon.GMI_FIXED + "','"
	    + ConstantsCommon.GMI_INTERNAL + "'";
    public static final String GMI_FIXED_GEN = "'" + ConstantsCommon.GMI_GENERAL + "','" + ConstantsCommon.GMI_FIXED
	    + "'";
    public static final String GMI_FIXED_INTERNAL = "'" + ConstantsCommon.GMI_INTERNAL + "','"
	    + ConstantsCommon.GMI_FIXED + "'";
    public static final String GMI_GEN_INTERNAL = "'" + ConstantsCommon.GMI_INTERNAL + "','"
	    + ConstantsCommon.GMI_GENERAL + "'";

    public static final String GMI_INCLUDE_INTERNAL = "'" + ConstantsCommon.GMI_INTERNAL + "'";

    public static final String ACTION_TYPE_MANDATORY = "MANDATORY";
    public static final String ACTION_TYPE_VISIBLE = "VISIBLE";
    public static final String ACTION_TYPE_READONLY = "READONLY";
    public static final String ACTION_TYPE_LABEL = "LABEL";
    public static final String ACTION_TYPE_VALUE = "VALUE";
    public static final String ACTION_TYPE_DECFORMAT = "DECFORMAT";
    public static final String ACTION_TYPE_MAXLENGTH = "MAXLENGTH";
    public static final String ACTION_TYPE_TXTFORMAT = "TXTFORMAT";
    public static final String ACTION_TYPE_LEAD_ZEROS = "LEAD_ZEROS";
    public static final String ACTION_TYPE_DATE_VALUE = "DATE_VALUE";

    // P_RET_GET_NEXT_WORKING_DAY
    public static final String NEXT_WORKING_DAY_BY_COMPANY = "COMP";
    public static final String NEXT_WORKING_DAY_BY_CURRENCY = "CUR";
    public static final String NEXT_WORKING_DAY_BY_BRANCH = "BRANCH";
    public static final String NEXT_WORKING_DAY_BY_COUNTRY = "COUNTRY";
    
    // suspend period type (working or calendar)
    public static final String SUSPEND_TYPE_WORKING = "W";
    public static final String SUSPEND_TYPE_CALENDAR = "C";

    public static final BigDecimal APP_IS_WEB_AND_PB = BigDecimal.valueOf(2);
    public static final BigDecimal APP_IS_WEB_ONLY = BigDecimal.ONE;

    // PCS related Constants used in Reports
    public static final String PFT_LOG_DETAIL_FROM = "FROM";
    public static final String PFT_LOG_DETAIL_TO = "TO";
    public static final String PFT_DAYS_BASIS = "A";
    public static final String PFT_BASE_CY = "Y";
    public static final String PFT_CY_PTMETHOD = "1";
    public static final BigDecimal PROFIT_CALCULATION_MONTHLY_ACCUMULATION = BigDecimal.valueOf(2);
    public static final BigDecimal PROFIT_CALCULATION_MONTHLY = BigDecimal.ONE;
    public static final BigDecimal PFT_DAYS360 = BigDecimal.valueOf(30);
    public static final BigDecimal PARAMETERS_CATEG_ID = BigDecimal.ONE;
    public static final String DESIGNER_PROG_REF = "RD00R";

    // process types used in activateProcessExec comonMethod
    public static final String PROCESS_PREIODICAL = "PERI";
    public static final String PROCESS_POSTING = "POST";
    public static final String PROCESS_ACTIVE_FLAG = "Y";
    public static final String PROCESS_INACTIVE_FLAG = "N";

    // Dashboard translations used in dashboard portal actions
    public static final String MAXIMIZE_KEY = "maximize_key";
    public static final String MINIMIZE_KEY = "minimize_key";
    public static final String DELETE_KEY = "delete_key";
    public static final String REFRESH_KEY = "refresh_key";
    public static final String RESIZE_KEY = "resize_key";
    public static final String ASSIGN_KEY = "assign_key";
    public static final String CUSTOMIZE_KEY = "customize_key";
    public static final String ASSET_QUERY_POSITION_REF = "R001Q";
    public static final String ASSETS_QUERY_POS_CALL_SOURCE = "QUERYPOSITION";

    // Remittance Types
    public static final String REMITTANCE_TYPE_TC = "TC";
    public static final String REMITTANCE_TYPE_DD = "DD";

    // Languages List
    public static final BigDecimal LANGUAGES_LOV_TYPE = BigDecimal.valueOf(412);
    public static final BigDecimal USER_STATUS_LOV_TYPE = BigDecimal.valueOf(290);

    /**
     * Used to test Expressions with dummy data, used in Customisation
     * expressions
     */
    public final static String USER_ID_EXP_VAR = "userId";
    public final static String COMP_CODE_EXP_VAR = "companyCode";
    public final static String COMP_NAME_EXP_VAR = "companyName";
    public final static String BRANCH_CODE_EXP_VAR = "branchCode";
    public final static String BRANCH_NAME_EXP_VAR = "branchName";
    public final static String USER_FIRST_NAME_EXP_VAR = "userFirstName";
    public final static String USER_LAST_NAME_EXP_VAR = "userLastName";
    public final static String BASE_CURR_NAME_EXP_VAR = "baseCurrencyName";
    public final static String IS_USER_TELLER_EXP_VAR = "isTellerUser";
    public final static String LOGIN_USER_PROFILE_VAR = "loggedInUserProfile";
    public final static String RUNNING_DATE_VAR = "runningDate";    
    public final static String USER_PROFILE_VAR = "userProfile()";
    public final static String SQL_RESULT_VAR = "sqlResult()";
    public final static String ENTITY_CIF_TYPE_DISPLAY = "cifType";
    public final static String ENTITY_VT_TYPE_DISPLAY = "vtType";
    public final static String ENTITY_CIF_TYPE_LOV     = "CIFTYPE";
    public final static String ENTITY_VT_TYPE_LOV     = "VTTYPE";
    public final static String ENTITY_TRX_TYPE_DISPLAY = "trxType";
    public final static String ENTITY_TRX_TYPE_LOV     = "TRXTYPE";
    public final static String ENTITY_COUNTRY_ID_DISPLAY = "countryId";
    public final static String ENTITY_COUNTRY_ID_LOV     = "COUNTRYID";    
    public final static String SC_HASH_CODE	       = "hashCode";
    public final static String INPUT_FIELD_VALUE = "inputFieldValue";
    public final static List<Map<String, Object>> expBoolTestData = new ArrayList<Map<String, Object>>();
    static
    {
	Map<String, Object> dataForExpr = new HashMap<String, Object>();
	dataForExpr.put(USER_ID_EXP_VAR, null);
	dataForExpr.put(COMP_CODE_EXP_VAR, null);
	dataForExpr.put(COMP_NAME_EXP_VAR, null);
	dataForExpr.put(BRANCH_CODE_EXP_VAR, null);
	dataForExpr.put(BRANCH_NAME_EXP_VAR, null);
	dataForExpr.put(USER_FIRST_NAME_EXP_VAR, null);
	dataForExpr.put(USER_LAST_NAME_EXP_VAR, null);
	dataForExpr.put(BASE_CURR_NAME_EXP_VAR, null);
	dataForExpr.put(IS_USER_TELLER_EXP_VAR, null);
	dataForExpr.put(USER_PROFILE_VAR, null);
	dataForExpr.put(SQL_RESULT_VAR, null);
	dataForExpr.put(LOGIN_USER_PROFILE_VAR, null);
	dataForExpr.put(RUNNING_DATE_VAR, null);
	expBoolTestData.add(dataForExpr);
    }
    //IBISATRX
    public static final String ACTIVE_TRX_REPORT_REF = "RETMATRX";
    public static final String ACTIVE_TRX_USER_ID = "X";
    public static final BigDecimal ACTIVE_TRX_TELLER_CODE = BigDecimal.valueOf(999);
    
    /**
     * concatenated to avoid Jenkins static IP restriction
     */ 
    public static final String LOCALHOST_TOMCAT_WIN7 = "0" + ":0" + ":0" + ":0" + ":0" + ":0" + ":0" + ":1";
    public static final String LOCALHOST = "localhost";
    /**
     * concatenated to avoid Jenkins static IP restriction
     */
    public static final String LOCALHOST_IP = "127.0" + ".0.1";
    /**
     * column types for grid
     */
    public static final String COLUMN_DATE_TYPE = "date";
    public static final String COLUMN_NUMBER_TYPE = "number";
    public static final String COLUMN_TEXT_TYPE = "text";
    public static final BigDecimal ENTITY_TYPE_LOV = BigDecimal.valueOf(500);

    // menu showing Mode
    public static final BigDecimal CATEGORIZED_MENU_MODE = BigDecimal.ONE;
    public static final BigDecimal FLAT_MENU_MODE = BigDecimal.ZERO;
    public static final BigDecimal CUST_EXPRESSION_VALUE = BigDecimal.valueOf(2);
    public static final BigDecimal CUST_HIDE_BUSINESS_EXPRESSION_VALUE = BigDecimal.valueOf(3);    
    //for verify button
    public static final String OPT_VERIFY_BTN="VR00";
    public static final String REPORT_NB_PAGES="repNbrPages";
    public static final String REP_PRINT_PDF = "1";
    public static final String REPORT_HAS_DATA="repHasData";
    public static final String PROG_TYPE_ROOT ="R";
    public static final String PROG_TYPE_PROGRAM = "P";
    
    public static final String datetime="DATETIME";
    public static final String CONNECTION="CONNECTION";
    
    public static final String PHONE_REPLACE_WHAT = "0";
    public static final String PHONE_REPLACE_WITH = "#";
    public static final String PHONE_PARENTHESIS_OPEN = "(";
    public static final String PHONE_PARENTHESIS_CLOSE = ")";
    public static final String PHONE_PLUS = "+";
    public static final String PHONE_REGEX = "(-)|/|(\\+)|[*()]";
    public static final String TIN_REGEX = "(_)|(-)|/|(\\+)|[*()]";
    
    //for the id type checking
    public static final String DEFAULT_DATA_TYPE="0";
    public static final String ALPHA_DATA_TYPE="1";
    public static final String NUMERIC_DATA_TYPE="2";
    public static final String ALPHA_NUMERIC_DATA_TYPE="3";
    
    public static final String FOM_OPT ="F00I1";
    // FMS OPTs
    public static final String FMS_OPT_SUSPEND_SUBLIMIT ="T008MSS";
    public static final String FMS_OPT_REACTIVATE_SUBLIMIT ="T008MRS";
    public static final String FMS_OPT_VERIFY_RELEASE = "T014RVVR";
    public static final String FMS_OPT_VERIFY_RELEASE_UND_CATEG = "T007RVVR";
    public static final String FMS_OPT_CUST_GRAD_UPD_AFT_APPR = "T003UP";

    public static final String SQB_QRY_TYPE ="sqb";

    public static final String QBE_QRY_TYPE ="qbe";
    public static final String EXPORT_SPECIFIC_APP ="5";
    
    //Record ATTACHMENT flags
    public static final String OPEN_RECORD = "R";
    public static final String OPEN_RECORD_LOG = "L";
    public static final String OPEN_ENTITY = "T";
    public static final String OPEN_ENTITY_LOG = "O";
    
    public static final String OUTPUT_LAYOUT_FREE_FORM = "2";
    public static final String OUTPUT_LAYOUT_TABULAR = "1";
    public static final String DEFAULT_PROG_REF = "Default";
    
    /**
     * hide and override business management
     */
    public static final BigDecimal HIDE_AND_OVERRIDE_BUSINESS = BigDecimal.valueOf(4);
    public static final BigDecimal HIDE_AND_OVERRIDE_BUSINESS_EXPR = BigDecimal.valueOf(3);
    
    
    /*report parameter types*/
    public static final String PARAM_TYPE_NUMBER= "NUMBER";
    public static final String PARAM_TYPE_VARCHAR2 = "VARCHAR2";
    public static final String PARAM_TYPE_DATE = "DATE";

    public static final String PREF_OBJECT_TYPE_GRID = "GRID";
    public static final String PREF_OBJECT_TYPE_TAB  = "TAB";
    
    /*Reporting - order by string to be added to the query*/
    public static final String ORDER_BY_STR = "ORDER BY ";
    
    //FCR
    public static final String FCR_COMP_CODE = "RA_COMP_CODE";
    public static final String FCR_TEMPL_CODE = "RA_TEMPLATE_CODE";
    public static final String FCR_COL_TMPLT_CODE = "RA_COL_TMPLT";
    public static final String FCR_REP_TYPE = "RA_REP_TYPE";
    public static final String FCR_ROW_TO_COL = "ROW_TO_COL";
    public static final String FCR_CURRENCY = "REP_CURRENCY";
    public static final String FCR_AP_REF = "APP_REF";
    public static final String FCR_SUMMARIZED_OPT = "R0020";
    public static final String FCR_DETAILED_OPT = "R00201";
    public static final String OPT_FCR_STANDARD = "R0020D00,R00201D00";
    public static final String AP_TOPOF="AP_TOPOF";
    public static final String FCR_FCR_REF="FCR_REF";    
    public static final String FCR_REP_TYPE_SUMMARIZED="S";
    public static final String FCR_RA_USER_ID="RA_USERID";
    public static final String FCR_CV_CURRENCY="CV_CURRENCY";
    public static final String FCR_EXCLUDE_CIF="EXCLUDE_CIF";
    public static final String FCR_CY_DEC="CY_DEC";    
    public static final String FCR_STANDARD_FLAG_YN="STANDARD_FLAG_YN";   
    public static final String FCR_AS_VT="AS_VT";
    public static final String FCR_RA_LANG="RA_LANG";
    public static final String FCR_WITH_DEC="W";
    public static final String FCR_DEFLT_VAL_TRADE_DTE="D";
    public static final String FCR_RA_TEMPLATE_HEADER = "RA_TEMPLATE_HEADER";
    public static final String FCR_APP = "RA_APP";
    public static final String FCR_PROG_REF = "RA_REF";
    public static final String RA_REP_LANG="RA_REP_LANG";

    public static final String SCHED_PROG_REF = "SCD01";

    public static final String TRUE = "true";
    
    public static final String PORTAL_REP_WIDGET = "REPORT_";
    
    public static final String COMP_AR_NAME_SESSION= "companyArabName";
    
    public static final String USD_ISO_CODE = "USD";
    
    //FCR ARGUMENTS
    public static final String ARG_RA_TYPE="RA_TYPE";
    public static final String ARG_RA_FORMAT = "RA_FORMAT";
    public static final String ARG_ROW_TO_COL = "ROW_TO_COL";
    public static final String ARG_RA_TYPE_NAME="param~RA_TYPE~VARCHAR2";
    public static final String GRP_BY_SEC_BRIEF_NAME="U";
    public static final String BR_CODE_BR_NAME="B";
    public static final String ARG_TEMPL_CODE_NAME= "param~RA_TEMPLATE_CODE~NUMBER";
    public static final String ARG_COL_TMP_CODE= "param~RA_COL_TMPLT~NUMBER";
    public static final String FALSE = "false";
    
    //REPORT ARGUMENTS
    public static final BigDecimal SESSION_ARG_SOURCE =BigDecimal.valueOf(2) ;
    public static final BigDecimal TRANS_SESSION_ARG_SOURCE =BigDecimal.valueOf(8) ;
    public static final BigDecimal REP_LANG_ARG_SOURCE = BigDecimal.valueOf(9);
    
    //NUMBER TO WORKDS CONVERSION
    public static final String CONVERT_TO_ENGLISH = "1";
    public static final String CONVERT_TO_ARABIC = "2";
    public static final String CONVERT_TO_FRENCH = "3";
    
    //PWD RESTRICTION_TYPE
    public static final String SYS_RESTRICTION_TYPE = "1";
    public static final String VALUE_RESTRICTION_TYPE = "0";
    //SYS_PARAM_PWD_RESTRICTION
    public static final int BIRTH_DATE_RESTRICTION = 1;
    public static final int USER_ID_RESTRICTION = 2;
    public static final int FIRST_NAME_RESTRICTION_TYPE = 3;
    public static final int MIDDLE_NAME_RESTRICTION_TYPE = 4;
    public static final int LAST_NAME_RESTRICTION_TYPE = 5;
    public static final int MOBILE_PHONE_RESTRICTION_TYPE = 6;
    public static final int OFFICE_NUMBER_RESTRICTION_TYPE = 7;
    public static final int HOME_PHONE_RESTRICTION_TYPE = 8;
    public static final int REJECT_REPEAT_RESTRICTION = 9;
    public static final int REJECT_NO_SPECIAL_CHAR_RESTRICTION_TYPE = 10;
    public static final int SEQUENTIAL_CHAR_RESTRICTION_TYPE = 11;
    public static final int USER_ID_INCLUDE_RESTRICTION_TYPE = 12;
    
    
    // REPORTCO PROPERTIES THAT WILL BE USED ON REPORT EXECUTION
    public static final List<String> retRepIdByProgRef_PROPS = Collections.unmodifiableList(Arrays.asList("APP_NAME",
	    "PROG_REF"));
    public static final List<String> retQryArgMapping_PROPS = Collections.unmodifiableList(Arrays.asList("REPORT_ID",
	    "reportArgumentId", "defaultSrc"));
    public static final List<String> returnQryResult_PROPS = Collections.unmodifiableList(Arrays.asList("isSybase",
	    "qryId", "connId", "hmQryParam", "nbRec", "recToskip", "filters", "searchCols", "sortOrder", "compCode", "branchCode", "userId", "currAppName",
	    "colSearchQuery", "searchQuery", "whereQuery", "argId","reportId","argVal"));
    public static final List<String> retRepArgDepList_PROPS = Collections.unmodifiableList(Arrays.asList("REPORT_ID",
	    "reportArgumentId", "defaultSrc", "reportArgumentId", "reportArgumentName"));
    public static final List<String> getLookupListMap_PROPS = Collections.unmodifiableList(Arrays.asList("LOV_TYPE_ID",
	    "LANG_CODE"));
    public static final List<String> returnXslName_PROPS = Collections.unmodifiableList(Arrays.asList("templateCode",
	    "COMP_CODE"));
    public static final List<String> createDynamicRowDataJrxml_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "JRXML_FILE", "jasperDesignFieldsMap"));
    public static final List<String> printReportRet_PROPS = Collections.unmodifiableList(Arrays.asList("pagesNbr",
	    "hasData", "reportBytes", "outputFormat", "reportBytes2","cifMap","hasPagination","paginationCount"));
    public static final List<String> getLookupListMapRet_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "LOV_TYPE_ID", "VALUE_CODE", "VALUE_DESC"));
    public static final List<String> retQryArgMappingRet_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "irpQueryArgsMappingVO.REPORT_ID", "irpQueryArgsMappingVO.REPORT_ARGUMENT_ID", "irpQueryArgsMappingVO.QUERY_ID",
	    "irpQueryArgsMappingVO.QUERY_ARG_NAME", "irpQueryArgsMappingVO.REPORT_MAPPED_ARG_NAME",
	    "irpQueryArgsMappingVO.STATIC_VALUE", "irpQueryArgsMappingVO.DEFAULT_SOURCE", "argumentCO.ARGUMENT_SOURCE"));
    public static final List<String> retRepArgDepListRet_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "ARGUMENT_ID", "ARGUMENT_NAME", "ARGUMENT_TYPE", "ARGUMENT_SOURCE"));
    public static final List<String> retDBFieldsRet_PROPS = Collections.unmodifiableList(Arrays.asList("FIELD_ALIAS",
	    "ENTITY_DB_NAME", "ENTITY_ALIAS", "FIELD_DB_NAME", "FIELD_DATA_TYPE", "PARENT_INDEX"));
    public static final List<String> returnConnectionsListRet_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "CONN_ID", "CONN_DESC"));
    
    public static final String REPORT_FORMAT_STR = "Report Format";
    public static final String REPORT_ADVICE_CALL = "Advice Call";
    public static final String BETWEEN_OPERATOR = "bet";
    public static final String BETWEEN_OPERATOR_CODE = ">=<=";
    public static final String STRING_IMPORT = "java.lang.String";
    public static final String BIGDECIMAL_IMPORT = "java.math.BigDecimal";
    public static final String FCR_MAIN_REPORT_REF = "FCR_MAIN_REP";
    public static final String FCR_applName = "applName";
    public static final String FCR_COL_TEMPL_CODE = "RA_COL_TMPLT";

    public static final String FILE_EXP_IMP_PROG_REF = "FEI00EF";
    
    //constant report parameters name
    public static final String LANG_PARAM = "l";
    public static final String FORMAT_PARAM = "var_format";
    public static final String DB_PARAM = "var_db";
    public static final String MENU_ID_PARAM = "var_menuId";
    public static final String FILTER_ID_PARAM="filterId";
    public static final String REPORT_ID_PARAM = "r_i";
    public static final String REPORT_ARG_PARAM = "r_a_p";
    public static final String APP_NAME_PARAM = "a";
    public static final String DYNAMIC_SCREEN_PARAM = "d_p";
    public static final String HEAD_FOOT_PARAM = "var_noHeadAndFoot";
    public static final String REPORT_NAME_PARAM = "var_reportName";
    public static final String CSV_SEPARTOR_PARAM = "var_separator";
    public static final String PARAM_TILDA = "param~";
    public static final String PARAM_H="paramH";
    public static final String P_PARAM_UNDERSCORE="p_param_";
    public static final String P_SELECTED=" Selected";
    public static final String MULTI_P_ROOT = "{\"root\":[";
    public static final String REP_ROOT="root";
    public static final String SESSION_TIMEOUT_PARAM="session_timeout_param";
    public static final String PEP_PRINTER_NAME="PRINTER_NAME";
    public static final String REP_SERVICE_CALL="REPORT_SERVICE_CALL";
    public static final String REP_PATH="REP_PATH";
    public static final String REP_PROC_FOLDER="MTS";
    public static final String REP_NBCOPIES_PRINT = "nbCopiesToPrint";
    
    //report argument types
    public static final String REP_ARG_TYPE_QRY="3";
    
    public static final String DEFAULT_LDAP_PORT = "389";
    // this is field related PTH_CTR.AD_AUTH_YN
    public static final String AUTH_LDAP_ENABLED = "2";
    public static final String CONNECTIVITY_PROTOCOL_LDAP  = "L";
    public static final String CONNECTIVITY_PROTOCOL_LDAPS = "S";
    public static final String USER_LOGIN_CRITERIA_IMAL = "I";
    public static final String USER_LOGIN_CRITERIA_LDAP = "L";
    public static final String CHECK_HOLIDAY_BY_COMP_BRANCH = "0";
    public static final String CHECK_HOLIDAY_BY_COMP = "1";
    public static final String CHECK_HOLIDAY_BY_BRANCH = "2";
    
    public static final String RELEASE_VERSION_R12_2_2 = "R12_2_2";
    public static final String WEB_SERVICE_PROG_REF = "webservice";
    
    public static final String SECURITY_TOKEN_VERIFICATION_SUCCESS = "0";
    public static final String ACTION_TYPE_SAVE_NEW = "saveNew";
    public static final String INTEGRATION_SERVICEURL = "integration.serviceURL";
    public static final String DMS_DETAILED_PARAMS = "dms.detailed.params";
    //782723 SFSCI180009 - Integration with document management system
    public static final BigDecimal DMS_TYPE_INFINITY = new BigDecimal("3");
    public static final BigDecimal DMS_EXPRESSION_FIELD_TYPE = new BigDecimal("3");
    //582096  FIB - Docuware Integration - Copy to R14.0.15 with Multi Index applying
    public static final String INTEGRATION_GED_DEAULT_URL = "integration.ws.ged.DefaultURL";
    public static final String INTEGRATION_WS_GED_URL = "integration.ws.ged.URLL";
    public static final BigDecimal DMS_TYPE_DOCUWARE = new BigDecimal("1");
    public static final BigDecimal DMS_TYPE_E_ARCHIVE = new BigDecimal("2");
    //
    public static final String CUSTOM_INTEGRATION_SERVICEURL = "customIntegration.serviceURL";

    //Dynamic Client Params
    public static final String IS_PRIMARY_KEY = "Y";
    public static final String OPERATION_TYPE_APPROVE = "A";
    public static final String OPERATION_TYPE_DELETE = "D";
    public static final String OPERATION_TYPE_NEW = "N";
    public static final String OPERATION_TYPE_MODIFY = "M";
    public static final String OPERATION_TYPE_EDIT_COLS = "E";
    public static final String OLD_RECORD = "old";
    public static final String NEW_RECORD = "new";
    public static final String ACTIVE_RECORD = "A";
    public static final String APPROVED_RECORD = "P";
    public static final String PS_TABLE_NAME_PREFIX = "INT_PARAM_";
    public static final String LOOKUP_COMP_CODE = "#COMPCODE";
    public static final String LOOKUP_BRANCH_CODE = "#BRANCHCODE";
    public static final String LOOKUP_BASE_CURRENCY = "#BASECURRENCY";
    public static final String LOOKUP_USER_ID = "#USERID";
    public static final String COMBOBOX_CONTROL_TYPE = "C";
    public static final String TEXT_CONTROL_TYPE = "T";
    public static final String DATE_CONTROL_TYPE = "D";
    public static final String LIVESEARCH_CONTROL_TYPE = "S";
    public static final String DYNAMIC_STATUS_COLUMN = "RECORDSTATUS";
    
    public static final BigDecimal DATA_TYPE_LOV_TYPE = BigDecimal.valueOf(661);
    public static final String DATA_TYPE_STRING  = "String";
    public static final String DATA_TYPE_DATE    = "Date";
    public static final String DATA_TYPE_NUMBER  = "Number";
    public static final String DATA_TYPE_BOOLEAN = "Boolean";
    public static final Map<Integer, String> ELEMENT_DATA_TYPE_MAP = new HashMap<Integer, String>();
    static
    {
	ELEMENT_DATA_TYPE_MAP.put(1,DATA_TYPE_STRING);
	ELEMENT_DATA_TYPE_MAP.put(2,DATA_TYPE_DATE);
	ELEMENT_DATA_TYPE_MAP.put(3,DATA_TYPE_NUMBER);
	ELEMENT_DATA_TYPE_MAP.put(4,DATA_TYPE_BOOLEAN);
    }
    public static final String ELEMENT_TYPE_TEXTFIELD = "1";
    public static final String ELEMENT_TYPE_LABEL = "2";
    public static final String ELEMENT_TYPE_COMBOBOX = "3";
    public static final String ELEMENT_TYPE_DATEPICKER = "4";
    public static final String ELEMENT_TYPE_RADIOBUTTON = "5";
    public static final String ELEMENT_TYPE_CHECKBOX = "6";
    public static final String ELEMENT_TYPE_LIVESEARCH = "7";
    public static final String ELEMENT_TYPE_BUTTON = "8";
    public static final String ELEMENT_TYPE_TEXTAREA = "9";
    public static final String ELEMENT_TYPE_PANEL = "10";
    public static final String ELEMENT_TYPE_FILE = "11";
    public static final String ELEMENT_TYPE_GRID = "12";

    public static final String LAYOUT_TYPE_TEXTFIELD = "textfield";
    public static final String LAYOUT_TYPE_HIDDEN = "hidden";
    public static final String LAYOUT_TYPE_LABEL = "label";
    public static final String LAYOUT_TYPE_COMBOBOX = "comboBox";
    public static final String LAYOUT_TYPE_DATEPICKER = "datePicker";
    public static final String LAYOUT_TYPE_RADIOBUTTON = "radioButton";
    public static final String LAYOUT_TYPE_CHECKBOX = "checkBox";
    public static final String LAYOUT_TYPE_LIVESEARCH = "livesearch";
    public static final String LAYOUT_TYPE_BUTTON = "button";
    public static final String LAYOUT_TYPE_TEXTAREA = "textArea";
    public static final String LAYOUT_TYPE_PANEL = "panel";
    public static final String LAYOUT_TYPE_FILE = "file";
    public static final String LAYOUT_TYPE_GRID = "grid";
    public static final String LAYOUT_TYPE_RANGE = "range";
    public static final String LAYOUT_TYPE_TAB = "tab";

    public static final Map<Integer, String> ELEMENT_LAYOUT_TYPE_MAP = new HashMap<Integer, String>();
    static
    {
	ELEMENT_LAYOUT_TYPE_MAP.put(1,LAYOUT_TYPE_TEXTFIELD);
	ELEMENT_LAYOUT_TYPE_MAP.put(2,LAYOUT_TYPE_LABEL);
	ELEMENT_LAYOUT_TYPE_MAP.put(3,LAYOUT_TYPE_COMBOBOX);
	ELEMENT_LAYOUT_TYPE_MAP.put(4,LAYOUT_TYPE_DATEPICKER);
	ELEMENT_LAYOUT_TYPE_MAP.put(5,LAYOUT_TYPE_RADIOBUTTON);
	ELEMENT_LAYOUT_TYPE_MAP.put(6,LAYOUT_TYPE_CHECKBOX);
	ELEMENT_LAYOUT_TYPE_MAP.put(7,LAYOUT_TYPE_LIVESEARCH);
	ELEMENT_LAYOUT_TYPE_MAP.put(8,LAYOUT_TYPE_BUTTON);
	ELEMENT_LAYOUT_TYPE_MAP.put(9,LAYOUT_TYPE_TEXTAREA);
	ELEMENT_LAYOUT_TYPE_MAP.put(10,LAYOUT_TYPE_PANEL);
	ELEMENT_LAYOUT_TYPE_MAP.put(11,LAYOUT_TYPE_FILE);
	ELEMENT_LAYOUT_TYPE_MAP.put(12,LAYOUT_TYPE_GRID);
	ELEMENT_LAYOUT_TYPE_MAP.put(13,LAYOUT_TYPE_RANGE);
	ELEMENT_LAYOUT_TYPE_MAP.put(14,LAYOUT_TYPE_TAB);
    }
    public static final String PROPERTY_CODE_COLNAME    ="colName";
    public static final String PROPERTY_CODE_DATE       ="date";
    public static final String PROPERTY_CODE_HEIGHT     ="height";
    public static final String PROPERTY_CODE_HIJRI      ="hijri";
    public static final String PROPERTY_CODE_DATE_DEFLT_VAL ="dateDefVal";    
    public static final String PROPERTY_CODE_ID         ="id";
    public static final String PROPERTY_CODE_LEFT       ="left";
    public static final String PROPERTY_CODE_MAXLENGTH  ="maxLength";
    public static final String PROPERTY_CODE_MODE       ="mode";
    public static final String PROPERTY_CODE_NAME       ="name";
    public static final String PROPERTY_CODE_NBFORMAT   ="nbFormat";
    public static final String PROPERTY_CODE_NBFRMTTER  ="nbFrmtter";
    public static final String PROPERTY_CODE_NBZRONTALW ="nbZroNtAlw";
    public static final String PROPERTY_CODE_NBNEGNTALW ="nbNegNtAlw";
    public static final String PROPERTY_CODE_OPTIONS    ="options";
    public static final String PROPERTY_CODE_QUERY      ="query";
    public static final String PROPERTY_CODE_READONLY   ="readOnly";
    public static final String PROPERTY_CODE_REQUIRED   ="required";
    public static final String PROPERTY_CODE_SOURCE     ="source";
    public static final String PROPERTY_CODE_TIME       ="time";
    public static final String PROPERTY_CODE_TOP        ="top";
    public static final String PROPERTY_CODE_VALIDATE   ="validate";
    public static final String PROPERTY_CODE_VALUE      ="value";
    public static final String PROPERTY_CODE_VISIBLE    ="visible";
    public static final String PROPERTY_CODE_WIDTH      ="width";
    public static final String PROPERTY_CODE_TITLE      ="title";
    public static final String PROPERTY_CODE_LOOKUPDESC ="lookupDesc";
    public static final String PROPERTY_CODE_LABELFOR 	="labelFor";
    public static final String PROPERTY_CODE_RELATEDCOL	="relatedCol";
    public static final String PROPERTY_CODE_ONCHANGE   ="onChange";
    //Bug 514831 Handling Radio Group label
    public static final String PROPERTY_CODE_GROUP_LABEL="groupLabel";
    public static final String PROPERTY_CODE_FILE_NAME	="fileName";
    public static final String PROPERTY_CODE_EDITABLE	="editable";
    public static final String PROPERTY_CODE_TABLE_NAME	="tableName";
    public static final String PROPERTY_CODE_DBL_CLICK	="dblClick";
    public static final String PROPERTY_CODE_COLPROPS	="colProps";
    public static final String PROPERTY_CODE_RETRIEVALCONDITION = "retCond";
    public static final String PROPERTY_CODE_ENABLESEARCH = "enblSearch";
    //Date Picker Min & Max Date Properties 
    public static final String PROPERTY_CODE_MINDATE	="minDate";
    public static final String PROPERTY_CODE_MAXDATE	="maxDate";
    public static final String PROPERTY_CODE_DATEFORMAT	="dateFormat";
    public static final String PROPERTY_CODE_PLACEHOLDER = "plcehldr";
    public static final String PROPERTY_CODE_TAG         = "tag";
    public static final String PROPERTY_CODE_AUTO_SEARCH_ONY = "atoSrchOly";
    public static final String PROPERTY_CODE_SINGLE_INPUT = "sngleInput";
    public static final String PROPERTY_CODE_LABELLEFT = "labelLeft";
    public static final String PROPERTY_CODE_LABELRIGHT = "labelRight";
    
    //addTab
    public static final String PROPERTY_CODE_ADD_TAB = "addTab";
    
    public static final BigDecimal ELEMENT_PROPERTY_LOV_TYPE = new BigDecimal(660);
    public static final BigDecimal LIVESEARCH_ELEMENT_TYPE = BigDecimal.valueOf(7);
    public static final BigDecimal CONTROL_TYPE_LOV_ID = new BigDecimal(658);
    public static final String ARG_QRY_LIVESEARCH_WITHOUT_DESC ="1";
    public static final String ARG_QRY_LIVESEARCH_WITH_DESC ="2";
    public static final String ARG_QRY_COMBO ="3";
    public static final String REP_CONN_ID = "Connection Identifier";
    public static final String REPORT_RETRIEVAL_APP_NAME = "Retrieved From Application";
    // variable used as key to include Path info details into running thread
    public static final String PATH_INFO_KEY = "PATH_INFO";
    public static final String PATH_LOG_USER_NAME_KEY = "PATH_LOG_USER_NAME";
    public static final String INTERNAL_SCREEN_BTN_SOURCE = "1";
    public static final String SCRIPT_BTN_SOURCE = "2";
    public static final String EXTERNEL_BTN_SOURCE = "3";
    public static final String SUBMIT_BTN_SOURCE = "4";
    public static final String DELETE_BTN_SOURCE = "5";

    // the LOV that represents the button activity types in screen customization
    public static final BigDecimal BUTTON_ACTIVITY_LOV_TYPE = BigDecimal.valueOf(685);

    //Cluster Operations control types
    public static final String CLUSTER_OPERATION_LOCKED = "L";
    public static final String CLUSTER_OPERATION_RUNING = "R";
    // To enable control on clustered operations
    public static volatile boolean GLOBAL_CLUSTER_ENABLE;
    
    // To enable http parameter encryption
    public static volatile boolean SECURITY_ENCRYPTPARAMS_ENABLED;
    // To enable dynamic key for parameter encryption
    public static volatile boolean SECURITY_ENCRYPTPARAMS_DYNAMIC_PWD;
    
    // Super user status
    public static final String SUPER_USER_STATUS = "1";
    public static final String BRANCH_KEY = "B";
    public static final String SESSION_KEY = "C";
    
    
    //Label Translation is modified by client
    public static final String IS_CLIENT_MODIFIED = "Y";
    
    
    // delete saved as screens 
    public static final String DELETE_SAVED_AS_SCREENS_PARENT = "1";
    public static final String DELETE_SAVED_AS_SCREENS_SERIES = "1";

    //FILTERS
    public static final List<String> retFiltersListMap_PROPS = Collections.unmodifiableList(Arrays.asList("FILTER_ID",
	    "CREATED_BY", "FILTER_NAME", "PUBLIC_YN", "DEFAULT_YN"));
    public static final List<String> retFiltersArgsListMap_PROPS = Collections.unmodifiableList(Arrays.asList(
	    "FILTER_ID", "REPORT_ID", "ARGUMENT_ID", "ARGUMENT_VALUE"));
    public static final String REP_ARGS_FILTERS_SCREEN="argsFilters";
    public static final String FILTER_ID_COL = "FILTER_ID";
    public static final String REPORT_ID_COL = "REPORT_ID";
    public static final String ARGUMENT_ID_COL = "ARGUMENT_ID";
    public static final String ARGUMENT_VALUE_COL = "ARGUMENT_VALUE";
    
    //Customization export type
    public static final String CUSTOM_EXPORT_FIELD = "field";
    public static final String CUSTOM_EXPORT_OBJECT = "object";
    public static final Boolean APPLY_DYN_EXPRESSION = Boolean.FALSE;//.TRUE;
    public static final BigDecimal REQUIRED_STRONG_VALUE = BigDecimal.ONE;
    public static final BigDecimal READONLY_STRONG_VALUE = BigDecimal.ZERO;
    public static final BigDecimal VISIBLE_STRONG_VALUE  = BigDecimal.ONE;
    
    public static final String PHOTO_EXTENSION_JPEG = "JPEG";
    public static final String PHOTO_EXTENSION_JPG = "JPG";
    public static final String PHOTO_EXTENSION_BMP = "BMP";
    public static final String PHOTO_EXTENSION_PNG = "PNG";
    public static final String PHOTO_EXTENSION_GIF = "GIF";

    //Audit Additional field types LOV
    //439073 ADD support for additional fields labels
    public static final BigDecimal AUDIT_ADD_FIELD_TYPES = BigDecimal.valueOf(821);
    public static final String ORDINARY_FIELD_TYPE_KEY   = "ordinary_field_type_key";
    
    public static final String DYN_ELEM_MODE_VARCHAR = "1";
    public static final String DYN_ELEM_MODE_NUMERIC = "2";
    
    public static final String SESSION_CHECKING_MSG_KEY = "session_checking_msg_key";
    public static final String PERIODICAL_PROCESS_IN_PROGRESS_KEY = "periodical_process_in_progress_key";
    public static final String QUERY_NO_DEFINED_SYNTAX = "QUERY_NO_DEFINED_SYNTAX";
    public static final String TRX_TYPE_CACHED_WITH_TRX_NO = "WITH_TRX_NO";
    
  //BMOUPI170521 - CLASSIFY WRITEOFF xlsx not working
    public static final String XLSX_FILE_EXTENSION = ".XLSX";
    public static final String XLS_FILE_EXTENSION  = ".XLS";
    
    
    //captcha keys
    public static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";
    public static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";
    public static final String KAPTCHA_CHAR_STRING = "kaptcha.textproducer.char.string";
    public static final String KAPTCHA_CHAR_LENGTH = "kaptcha.textproducer.char.length";
    public static final String KAPTCHA_FONT_SIZE = "kaptcha.textproducer.font.size";
    /**
     * Method needed to return the Constants Common Version, to avoid including static version upon compilation into Files 
     * @return
     */
    public static String returnAppVersion()
    {
	return APP_VERSION;
    }
    /**
     * Method needed to return the Constants Common Display Version, to avoid including static version upon compilation into Files 
     * @return
     */
    public static String returnAppDisplayVersion()
    {
	return DISPlAY_APP_VERSION;
    }
    /**
     * method used to return the app numeric version from the DISPlAY_APP_VERSION which will contains only numbers 
     * @return
     */
    public static String returnAppNumericVersion()
    {
	return StringUtil.nullToEmpty(DISPlAY_APP_VERSION).replaceAll("\\D+","");
    }
    /**
     * Method needed to return the Constants Common Display Version, to avoid including static version upon compilation into Files 
     * @return
     */
    public static String returnAppInterBuildVersion()
    {
	return APP_INTERNAL_BUILD_VERSION;
    }
    /**
     * Method needed to return the constants Common APP_NAME to avoid including static APP Name upon compilation into files.
     * @return
     */
    public static String returnCurrentAppName()
    {
	return APP_NAME;
    }
    /**
     * Method needed to return the constants Common APP_BUILD_DATE_TIME to avoid including static APP_BUILD_DATE_TIME upon compilation into files.
     * @return
     */
    public static String returnAppBuildDate()
    {
	return APP_BUILD_DATE_TIME;
    }
    /**
     * Method needed to return the constants Common COMMON_COMP_VERSION to avoid including static COMMON_COMP_VERSION upon compilation into files.
     * @return
     */
    public static String returnAppCommonCompVersion()
    {
	return COMMON_COMP_VERSION;
    }
    //447471 Masking the info of VIP customers
    public static final String FIXED_MATURITY_ACCOUNTS_SCREEN = "A001";
    public static final String GENERAL_ACCOUNTS_SCREEN = "A002";
    public static final Map<String,String> RET_MASK_SCREENS = new HashMap<String, String>();
    static
    {
        RET_MASK_SCREENS.put(FOM_OPT,"cifKey");
        RET_MASK_SCREENS.put(FIXED_MATURITY_ACCOUNTS_SCREEN,"amfKey");
        RET_MASK_SCREENS.put(GENERAL_ACCOUNTS_SCREEN,"amfKey");
    }
    public static final String MASKING_THE_NAME = "N";
    public static final String MASKING_THE_SALARY = "S";
    public static final String MASKING_ALL = "A";
    public static final String CIF_NO_NAME = "CIF_NO";
    public static final String CIF_SUB_NO_NAME = "CIF_SUB_NO";
    public volatile static Timer timerExec;//efarah 07/04/2017 EWBI170270
    public volatile static Timer timerInExec;//efarah 07/04/2017 EWBI170270
    
    //Bassam Eid BUG# 512170 - 25/04/2017
    //Added this flag as requested by Jihad Lamaa to "temporary" switch between IIAB and BURG
    //Special Condition implementation until adding a control record flag when implementing IIAB
    public static final String APPLY_BURG_SPECIAL_COND_BEHAVIOR = "Y";
    //////////////////////////////////////
    /**
     * used for access rights on the Customizations OPTs. 
     */
    public static final String CUST_MAINTENANCE_OPT = "CUSTMT";
    public static final String CUST_APPROVE_OPT     = "CUSTAP";
    public static final String NO_MIR_MODE = "NOMIR";
    
    //session closure
    public static final String FROM_FORCE_CLOSE_BFR_CHCK_TRX = "2";
    public static final String FROM_FORCE_CLOSE_AFTR_PROCEED = "1";
    
    // TP 771818
    public static final String DATA_TYPE_ENTITY_DATE = "D";
    public static final String DATA_TYPE_ENTITY_VARCHAR = "V";
    public static final String DATA_TYPE_ENTITY_DATETIME = "DT";
    public static final String DATA_TYPE_ENTITY_NUMERIC = "DT";
    
    //TP 789174 EMPLOYEE STATUSES
    public static final String EMP_STATUS_APPROVED = "A";
    public static final String EMP_STATUS_DISCHARGED = "C";
    
    public static final String SCAN_AX_VERSION = "1,0,6,0";

    public static final String OMNI_INTERNET_BANKING = "OIBK";
    public static final String OMNI_MOBILE_BANKING = "OMBK";
    public static final String OMNI_FACEBOOK_BANKING = "OFBK";
    public static final String OMNI_TWITTER_BANKING = "OTBK";
    public static final String DYNC_FILE_DIR = "dynamic_screen";
    
    //paramters names for global activity method
    public static final String APPNAME = "APP_NAME";
    public static final String SCREEN_REF = "SCREEN_REF";
    public static final String IV_CRUD = "IV_CRUD";
    public static final String LANGUAGE = "LANGUAGE";
    public static final String SCREEN_PRIMARY_KEY = "SCREEN_PRIMARY_KEY";
    public static final String SESSIONCO="SESSIONCO";
    
    public static final BigDecimal LOV_TYPE_DATASOURCE = BigDecimal.valueOf(1748);
    public static final String DATASOURCE_BY_QUERY = "Q";
    public static final String DATASOURCE_BY_GLOBAL_ACTIVITY = "G";
    public static final String DATASOURCE_BY_STATIC = "S";
    public static final String DS_REST_TMP_TABLE_PREFIX = "DS_REST_TMP_";

    // Jishnu.G 05/11/2019 ; SUPT190250 ;AMANA190071
    public static final BigDecimal SOURCE_MAPPING_LOV_TYPE = BigDecimal.valueOf(1787);
    public static final BigDecimal AGGREGATE_LOV_TYPE = BigDecimal.valueOf(1788);
    public static final BigDecimal SOURCE_MAPPING_STANDARD_INPUT = BigDecimal.valueOf(5);
    public static final BigDecimal SOURCE_MAPPING_SESSION_VARIABLE = BigDecimal.valueOf(3);
    
    // TP#897711 SUPT190250 - Nazim Jassar ; 29/11/2019 [START]
    public static final String SUM = "1";
    public static final String AVERAGE = "2";
    // TP#897711 SUPT190250 - Nazim Jassar ; 29/11/2019 [END]    
}

