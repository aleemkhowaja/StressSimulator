package com.path.lib.common.util;

import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringEscapeUtils;


import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.log.Log;
import com.path.struts2.lib.common.BaseObject;

@SuppressWarnings("unchecked")
public final class StringUtil
{
    private static final Log log = Log.getInstance();
    public static final int JAVA_TYPE_ESCAPE_UNESCAPE = 1;
    public static final int CSV_TYPE_ESCAPE_UNESCAPE = 2;
    public static final int JAVA_TYPE_ESCAPE_CTRL_CHARS = 3;
    private static final String PROG_REF_KEY = "prog_ref_key";
    public volatile static HashMap<String,List<String>> objectStringPropNames = new HashMap<String, List<String>>();
    /**
     * Private constructor only to prevent instantiation in the class
     */
    private StringUtil()
    {
	log.error("This Class Should not be Instantiated");
    }

    /**
     * check if is lower case String
     * 
     * @param str
     * @param isOptional
     * @return
     */
    public static boolean isLowerAlpha(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-z]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isAlpha(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-zA-Z]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    /**
     * 
     * Used for validating a value against Given Format specially Phone Formats
     * like (000)-000-0000, 000/00/000000,...
     * 
     * @param theValue
     * @param theFormat
     * @return
     */
    public static boolean validateValueForFormat(String theValue, String theFormat)
    {
	if(theValue == null)
	{
	    return false;
	}
	else
	{
	    String regExp = returnRegExpFromFormat(theFormat);
	    Pattern pattern = Pattern.compile(regExp);
	    Matcher matcher = pattern.matcher(theValue);
	    return matcher.matches();
	}
    }

    /**
     * 
     * Used for returning Regular Expression value for the Format provided by PB
     * application like phone formats (000)-000-0000, 000/00/000000,...
     * 
     * @param theFormat
     * @return
     */
    public static String returnRegExpFromFormat(String theFormat)
    {
	String regExpResult = "^";
	String escapeChars = "([$+^)]{}.";
	if(theFormat != null)
	{
	    String[] splitFrmt = theFormat.split("");
	    String currChar = null, prevChar = null;
	    int nbConsecutiveZeros = 0;
	    // start loop from second entry since split("") makes first entry
	    // empty
	    for(int i = 1; i < splitFrmt.length; i++)
	    {
		currChar = splitFrmt[i];
		if("0".equals(currChar))
		{
		    if(prevChar == null || !currChar.equals(prevChar))
		    {
			nbConsecutiveZeros = 1;
		    }
		    else
		    {
			nbConsecutiveZeros++;
		    }
		}
		else
		{
		    if(nbConsecutiveZeros > 0)
		    {
			regExpResult = regExpResult.concat("([0-9]{" + nbConsecutiveZeros + "})");
		    }

		    regExpResult = regExpResult
			    .concat((escapeChars.indexOf(currChar) >= 0 ? "\\" + currChar : currChar));
		    nbConsecutiveZeros = 0;
		}

		prevChar = currChar;
	    }

	    // last occurence
	    if(currChar != null && "0".equals(currChar) && nbConsecutiveZeros > 0)
	    {
		regExpResult = regExpResult.concat("([0-9]{" + nbConsecutiveZeros + "})");
	    }
	}
	return regExpResult;
    }

    /**
     * 
     * Used for replacing all consecutive empty lines and trim each line
     * 
     * @param stringToReplace
     * @return
     */
    public static String replaceEmptyLinesAndTrimLines(String stringToReplace)
    {
	if(stringToReplace == null)
	{
	    return null;
	}
	// replace consecutive 2 or more empty lines by single Line
	String resultStr = stringToReplace.replaceAll("(\\s*\\r\\n?|\\s*\\n){2,}", "\r\n");
	// remove spaces from end of each line
	resultStr = resultStr.replaceAll("\\s*\\n|\\s*\\r\\n|\\s*\\r", "\r\n");
	// remove spaces from beginning of each line
	resultStr = resultStr.replaceAll("\\n\\s*|\\r\\n\\s*", "\r\n");
	// trim resulted String
	return resultStr.trim();
    }

 
    /**
     * takes a string of int and returns an array of ints
     * 
     * @param intString the string of ints to be parsed
     * @return array of ints
     */
    public static int[] toIntArray(String theIntString, String delimiter)
    {
	String intString = theIntString;
	if(intString.endsWith(delimiter))
	{
	    intString = intString.substring(0, intString.length() - delimiter.length());
	}
	StringTokenizer st = new StringTokenizer(intString, delimiter);
	int[] intArray = new int[st.countTokens()];
	for(int i = 0; i < intArray.length && st.hasMoreTokens(); i++)
	{
	    intArray[i] = Integer.parseInt(st.nextToken());

	}
	return intArray;
    }

    /**
     * Return the values of the list in a String separated by the separator
     * 
     * @author marwanmaddah
     * @date Feb 1, 2013
     * @param strLst
     * @param separator
     * @return String
     * 
     */
    public static String returnStringFromArray(List<String> strLst, String separator)
    {
	if(strLst != null && !strLst.isEmpty())
	{    
	    return returnStringFromArray(strLst, separator, 0 , strLst.size()-1);
	} 
	return null;
    }

    /**
     * Return the values of the list in a String separated by the separator starting fromElement and going toElement
     * @param strLst
     * @param separator
     * @param fromElement
     * @param toElement
     * @return
     */
    public static String returnStringFromArray(List<String> strLst, String separator, int fromElement , int toElement)
    {
	if(strLst != null && !strLst.isEmpty() && separator != null && fromElement <= toElement
		&& fromElement <= strLst.size() - 1 && toElement <= strLst.size() - 1)
	{
	    StringBuffer result = new StringBuffer();
	    for(int i = fromElement; i <= toElement; i++)
	    {

		result.append(strLst.get(i) + (i == toElement ? "" : separator));
	    }
	    return result.toString();
	}
	return null;
    }



    /**
     * takes a string of doubles and returns an array of doubles
     * 
     * @param doubleString the string of doubles to be parsed
     * @return array of doubles
     */
    public static double[] toDoubleArray(String theDoubleString, String delimiter)
    {
	String doubleString = theDoubleString;
	if(doubleString.endsWith(delimiter))
	{
	    doubleString = doubleString.substring(0, doubleString.length() - delimiter.length());
	}
	StringTokenizer st = new StringTokenizer(doubleString, delimiter);
	double[] doubleArray = new double[st.countTokens()];
	for(int i = 0; i < doubleArray.length && st.hasMoreTokens(); i++)
	{
	    doubleArray[i] = Double.parseDouble(st.nextToken());

	}
	return doubleArray;
    }

    /**
     * takes an Array of int and returns the corresponding string of ints
     * 
     * @param intArray containing the ints to be converted
     * @return String of ints
     */
    public static String toStringArray(int... intArray)
    {
	StringBuffer stringOfInts = new StringBuffer();
	for(int i = 0; i < intArray.length; i++)
	{
	    stringOfInts.append(intArray[i]).append(" ");

	}
	return stringOfInts.toString();
    }

    /**
     * takes a string and returns an array of strings
     * 
     * @param string to be parsed
     * @return array of string
     */
    public static String[] toStringArray(String theInputString, String delimiter)
    {
	String inputString = theInputString;
	if(inputString.endsWith(delimiter))
	{
	    inputString = inputString.substring(0, inputString.length() - delimiter.length());
	}
	StringTokenizer st = new StringTokenizer(inputString, delimiter);
	String[] stringArray = new String[st.countTokens()];
	for(int i = 0; (i < stringArray.length && st.hasMoreTokens()); i++)
	{
	    stringArray[i] = st.nextToken().trim();

	}
	return stringArray;
    }

    /**
     * takes an array of String and returns the array of with each element
     * initialized to ""
     * 
     * @param string to be initialized
     * @return the same array f string with members initialized
     */
    public static void initStringArray(String... inputStringArrayParam)
    {
	//Avoid reassigning parameters
	String[] inputStringArray = inputStringArrayParam;
	for(int i = 0; i < inputStringArray.length; i++)
	{
	    inputStringArray[i] = "";
	}
    }

    /**
     * Takes a String of int and strip from it whatever is in the delimeter
     * array and returns a new string with these chars removed.
     */
    public static String stripString(String stringToStrip, String... delimeterArray)
    {
	String copyOfString = stringToStrip, stringPart1, stringPart2;
	for(int i = 0; i < delimeterArray.length; i++)
	{
	    if(copyOfString.indexOf(delimeterArray[i]) != -1)
	    {
		stringPart1 = copyOfString.substring(0, copyOfString.indexOf(delimeterArray[i]));
		stringPart2 = copyOfString
			.substring(copyOfString.indexOf(delimeterArray[i]) + 1, copyOfString.length());
		copyOfString = stringPart1 + stripString(stringPart2, delimeterArray);
	    }
	}
	return copyOfString;
    }

    /**
     * Takes a String and strip from it whatever is in the delimeter array and
     * returns a new string with these chars removed.
     */
    public static String replaceInString(String stringToStrip, String[] whatArray, String... withArray)
    {
	String copyOfString = stringToStrip, stringPart1, stringPart2;
	for(int i = 0; i < whatArray.length; i++)
	{
	    if(copyOfString.indexOf(whatArray[i]) != -1)
	    {
		stringPart1 = copyOfString.substring(0, copyOfString.indexOf(whatArray[i]));
		stringPart2 = copyOfString.substring(copyOfString.indexOf(whatArray[i]) + whatArray[i].length());
		copyOfString = stringPart1 + withArray[i] + replaceInString(stringPart2, whatArray, withArray);
	    }
	}
	return copyOfString;
    }

    /**
     * Takes a String and strip from it whatever is in the delimeter string and
     * returns a new string with these chars removed.
     * 
     * @see #replaceInString(String stringToStrip, String[] whatArray, String[]
     *      withArray)
     */
    public static String replaceInString(String stringToStrip, String what, String with)
    {
	return replaceInString(stringToStrip, new String[] { what }, new String[] { with });
    }

    /**
     * returns substring of given string from beginning till specified length
     */
    public static String substring(String value, int length)
    {
	if(value == null)
	{
	    return null;
	}
	else
	{
	    if(value.length() <= length)
	    {
		return value;
	    }
	    else
	    {
		return value.substring(0, length);
	    }
	}
    }

    /**
     * 
     * Used for stripping String from start index to specific length,
     * corresponds to PB mid method
     * 
     * @param value Value to Substring
     * @param startIndex Start Index
     * @param length Length to Substring
     * @return
     */
    public static String substring(String value, int startIndex, int theLength)
    {
	String result = "";
	int length = theLength;
	int endIndex = 0;
	if(value != null)
	{
	    if(startIndex > value.length())
	    {
		return "";
	    }
	    if(length > value.length())
	    {
		length = value.length();
	    }
	    endIndex = startIndex - 1 + length;
	    if(endIndex > value.length())
	    {
		endIndex = value.length();
	    }
	    result = value.substring(startIndex - 1, endIndex).trim();
	}

	return result;
    }

    /**
     * returns substring of given string starting from the end
     */
    public static String laststring(String value, int length)
    {
	if(value == null)
	{
	    return null;
	}
	else
	{
	    if(value.length() <= length)
	    {
		return value;
	    }
	    else
	    {
		return value.substring(value.length() - length, value.length());
	    }
	}
    }

    /**
     * Replaces the null string with empty
     */
    public static String nullToEmpty(Object obj)
    {
	if(obj == null)
	{
	    return "";
	}
	else
	{
	    return obj.toString();
	}
    }

    /**
     * Replaces the null or empty string with given Value
     */
    public static String nullEmptyToValue(Object obj, Object toValue)
    {
	if(obj == null)
	{
	    if(toValue == null)
	    {
		return "";
	    }
	    else
	    {
		return toValue.toString();
	    }
	}
	else
	{
	    if("".equals(obj.toString()))
	    {
		if(toValue == null)
		{
		    return "";
		}
		else
		{
		    return toValue.toString();
		}
	    }
	    else
	    {
		return obj.toString();
	    }
	}
    }

    /**
     * Replaces the null String by an empty String
     * 
     * @param nullString String
     * @return String
     */
    public static String nullToEmpty(String nullString)
    {
	if(nullString == null || "null".equals(nullString))
	{
	    return "";
	}
	else
	{
	    return nullString.trim();
	}
    }

    /**
     * Split up the string stringToSplit into N parts, returning a list
     * containing the parts even if the last part may be smaller than the
     * others.
     * 
     * @param stringToSplit the string to split as the name indicates
     * @param numberOfParts the number of parts you wish to split the string
     *            into
     * @return a list containing all the parts of the String
     * 
     * @see #splitEqually
     */
    public static List splitNParts(String stringToSplit, int numberOfParts)
    {
	int stopIndex = (stringToSplit.length() + numberOfParts - 1) / numberOfParts;
	return splitEqually(stringToSplit, stopIndex);
    }

    /**
     * Split up the string stringToSplit into parts of N size, returning a list
     * containing the parts even if the last part may be smaller than the
     * others.
     * 
     * @param stringToSplit the string to split as the name indicates
     * @param partSize the size of the part you want to split the string into
     * @return a list containing all the parts of the String
     */
    public static List splitEqually(String stringToSplit, int partSize)
    {
	if(partSize > stringToSplit.length())
	{
	    return null;
	}
	else
	{
	    ArrayList<String> stringLst = new ArrayList<String>();
	    for(int i = 0; i < partSize; i++)
	    {
		int beginIndex = partSize * i;
		int endIndex = partSize * i + partSize;
		if(endIndex > stringToSplit.length())
		{
		    stringLst.add(stringToSplit.substring(beginIndex));
		    break;
		}
		else
		{
		    stringLst.add(stringToSplit.substring(beginIndex, endIndex));
		}
	    }
	    return stringLst;
	}
    }

    /**
     * Changes the special characters of the input String into their escape-code
     * equivalent: For example: < = &lt; > = &gt; & = &amp; " = &quot; \'=
     * &apos;
     * 
     * @param theString
     * @return
     */
    public static String escapeString(String theStr)
    {
	StringBuffer str = new StringBuffer();
	String theString = theStr;
	theString = nullToEmpty(theString).trim();

	int len = theString.length();
	for(int i = 0; i < len; i++)
	{
	    char ch = theString.charAt(i);
	    switch (ch)
	    {
		case '<':
		{
		    str.append("&lt;");
		    break;
		}
		case '>':
		{
		    str.append("&gt;");
		    break;
		}
		case '&':
		{
		    str.append("&amp;");
		    break;
		}
		case '"':
		{
		    str.append("&quot;");
		    break;
		}
		case '\'':
		{
		    str.append("&apos;");
		    break;
		}
		case '\r':
		case '\n':
		{
		    str.append("&#");
		    str.append(Integer.toString(ch));
		    str.append(';');
		    break;
		}
		    // else, default append char
		default:
		{
		    str.append(ch);
		    break;
		}
	    }
	}
	return str.toString();
    }

    /**
     * Method For Escaping string
     * 
     * @param strToEsc string to escape
     * @param escapType type to escape CSV_TYPE_ESCAPE, JAVA_TYPE_ESCAPE
     * @return
     */
    public static String escapeCharactersInString(String strToEsc, int escapType)
    {
	String result = null;
	if(strToEsc != null)
	{
	    if(CSV_TYPE_ESCAPE_UNESCAPE == escapType)
	    {
		result = StringEscapeUtils.escapeCsv(strToEsc);
	    }
	    else if(JAVA_TYPE_ESCAPE_CTRL_CHARS == escapType)
	    {
		result = escapeCtrlChars(strToEsc);
	    }
	    else
	    {
		result = StringEscapeUtils.escapeJava(strToEsc);
	    }
	}
	return result;
    }

    /**
     * Method For un-escaping string
     * 
     * @param strToEsc string to un-escape
     * @param escapType type to escape CSV_TYPE_ESCAPE, JAVA_TYPE_ESCAPE
     * @return
     */
    public static String unEscapeCharactersInString(String strToEsc, int escapType)
    {
	String result = null;
	if(strToEsc != null)
	{
	    if(CSV_TYPE_ESCAPE_UNESCAPE == escapType)
	    {
		result = StringEscapeUtils.unescapeCsv(strToEsc);
	    }
	    else
	    {
		result = StringEscapeUtils.unescapeJava(strToEsc);
	    }
	}
	return result;
    }

    /**
     * Returns the Substring starting with the given param.
     * 
     * @param post String
     * @param param String
     * @return string
     */
    public static String getParam(String post, String theParam)
    {
	String value = null;
	String param = theParam;
	try
	{
	    param = param.concat("=");

	    int idx = post.indexOf(param);
	    if(idx == -1)
	    {
		log.debug("Query String not sent");
	    }
	    else
	    {
		int valPos = idx + param.length(); // the position of the
		// begining of the value of
		// the parameter requested
		int endIdx = post.indexOf("&", valPos);

		if(endIdx > 0)
		{
		    value = post.substring(valPos, endIdx);
		}
		else
		{
		    value = post.substring(valPos);
		}
	    }
	}
	catch(Exception ex)
	{
	    log.error(ex.getMessage());
	}
	return value;
    }

/**
  * This method transform every character that a value bigger or equals than 128 to it's
  * unicode representations.
  * This method changes the following characters too: '&' '<'
  * <br>This method use the java unicode internal representation
  * in order to do the conversions.
  * Thus, we got a very good performance (check grid below)<br>
  *
  <TABLE WIDTH="640" CELLPADDING="0" CELLSPACING="0">
  <TR>
    <TD><B>Input String size</B></TD>
    <TD><B>0% changes</B></TD>
    <TD><B>75% changes</B></TD>
    <TD><B>100% changes</B></TD>
  </TR>
  <TR>
    <TD><B>1 K</B></TD>
    <TD> ~ 0 ms</TD>
    <TD>~ 16 ms</TD>
    <TD>~ 16 ms</TD>
  </TR>
  <TR>
    <TD><B>10 K</B></TD>
    <TD>~ 0 ms</TD>
    <TD>~ 21 ms</TD>
    <TD>~ 35 ms</TD>
  </TR>
  <TR>
    <TD><B>100 K</B></TD>
    <TD>~ 20 ms</TD>
    <TD>~ 120 ms</TD>
    <TD>~ 140 ms</TD>
  </TR>
  <TR>
    <TD><B>1 M</B></TD>
    <TD>~ 100 ms</TD>
    <TD>~ 800 ms</TD>
    <TD>~ 1100 ms</TD>
  </TR>
  </TABLE>
  *
  * <b>Note</b>: that those number simulate and aggressive environment which is too much memory consuming;
  * In fact the generation of the input strings was dynamic (@ each method call) and in a random manner.
  * e.g.: in real production, the # will be about very offen 2 or 3 times less than the above table.
  * @param inString String
  * @return String
  */

    public static String toUnicode(String inStr)
    {
	String inString = inStr;
	StringBuffer sb = new StringBuffer((int) (inString.length() * 1.5));
	int charCode = 0;

	char[] inChars = new char[inString.length()];
	inString.getChars(0, inChars.length, inChars, 0);
	inString = null;

	int lastPos = 0;

	for(int i = 0; i < inChars.length; ++i)
	{
	    charCode = inChars[i];
	    if(charCode >= 128 || charCode == 38 || charCode == 60)
	    {
		sb.append(inChars, lastPos, (i - lastPos));
		sb.append("&#").append(charCode).append(";");
		lastPos = i + 1;
	    }
	}
	sb.append(inChars, lastPos, (inChars.length - lastPos));
	return sb.toString();
    }

    /**
     * method to encode a entered Label title (by setHeaderTitle function) cz
     * all labels with special characters entered within setHeaderTitle function
     * has been encoded with my personal encoding, so it was requierd to be
     * decoded when exporting csv
     * 
     * @param unicodeString
     * @return
     */
    public static String unicodeToString(String unicodeStr)
    {
	String unicodeString = unicodeStr;
	if((unicodeString == null || unicodeString.equals("") || unicodeString.equals("&#160;"))
		|| (unicodeString.indexOf(";") < 0 && unicodeString.indexOf("#") < 0))
	{
	    return unicodeString;
	}

	unicodeString = unicodeString.replaceAll("&#38;", "");
	StringTokenizer strTk = new StringTokenizer(unicodeString, ";");
	StringBuffer strResult = new StringBuffer();
	String interResult = "";
	while(strTk.hasMoreTokens())
	{
	    interResult = strTk.nextToken();
	    interResult = interResult.replaceAll("&#", "");
	    interResult = interResult.replaceAll("#", "");
	    strResult.append((char) (Integer.parseInt(interResult)));
	}
	return strResult.toString();
    }

    /**
     * Ecode a string using a java.net.URLEncoder
     * 
     * @param val String
     * @param encoding String
     * @return String
     */
    public static String encode(String val, String encoding)
    {
	String encodedStr = null;
	try
	{
	    encodedStr = java.net.URLEncoder.encode(val, encoding);
	}
	catch(UnsupportedEncodingException ex)
	{
	    log.error(ex, "");
	}
	return encodedStr;
    }

    /**
     * Decode a string using a java.net.URLDecoder
     * 
     * @param val String
     * @param decoding String
     * @return String
     */
    public static String decode(String val, String decoding)
    {
	String decodedStr = null;
	try
	{
	    decodedStr = java.net.URLDecoder.decode(val, decoding);
	}
	catch(UnsupportedEncodingException ex)
	{
	    log.error(ex, "");
	}
	return decodedStr;
    }

    public static String setFirstCharUpper(String name)
    {
	return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String setFirstCharLower(String name)
    {
	return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    /**
     * Method that replaces all special characters in the string and the leading
     * numbers
     * 
     * @param name
     * @return
     */
    public static String escapeSpecialCharWithLeadingNum(String theName)
    {
	String name = theName;
	if(name != null)
	{
	    String prevTest = null;
	    name = name.replaceAll("[^\\w]", "");
	    while(!name.equals(prevTest))
	    {
		prevTest = name;
		name = name.replaceAll("^[0-9]", "");
	    }
	}
	return name;
    }

    /**
     * Method that removes \n \t \r space in the string
     * 
     * @param name
     * @return
     */
    public static String removeNewLineTabSpace(String theName)
    {
	String name = theName;
	if(name != null)
	{
	    name = name.replaceAll("[\\r\\t\\n ]*", "");
	}
	return name;
    }

    public static boolean isUpperLowerAlphaAndNumberAndSpecialCharacterWithoutSpace(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    return !isAlphanumeric(str, false) && !isAlphaWithSpecialCharacter(str, false)
		    && !isUpperAlphaWithNumberWithSpecialCharacter(str, false)
		    && !isLowerAlphaWithNumberWithSpecialCharacter(str, false)
		    && isUpperLowerAlphaWithNumberWithSpecialCharacterWithoutSpace(str, false);
	}
	return isOptional;
    }

    public static boolean isNotEmpty(String str)
    {
	return str != null && str.length() > 0;
    }

    public static boolean isEmptyString(String str)
    {
	return str == null || str.length() == 0;
    }
    public static boolean isAlphanumeric(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-zA-Z0-9]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isAlphaWithSpecialCharacter(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-zA-Z\\W_]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isUpperAlphaWithNumberWithSpecialCharacter(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[A-Z0-9\\W_]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isLowerAlphaWithNumberWithSpecialCharacter(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-z0-9\\W_]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isUpperLowerAlphaWithNumberWithSpecialCharacterWithoutSpace(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-zA-Z0-9\\W_&&[\\S]]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isUpperLowerAlphaAndNumber(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    return !isUpperAlphaNumeric(str, false) && !isLowerAlphaNumeric(str, false)
		    && isAlphaAndNumeric(str, false);
	}
	return isOptional;
    }

    public static boolean isUpperAlphaNumeric(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[A-Z0-9]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches() && !isUpperAlpha(str, false) && !isNumeric(str, false);
	}
	return isOptional;
    }

    public static boolean isLowerAlphaNumeric(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[a-z0-9]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches() && !isLowerAlpha(str, false) && !isNumeric(str, false);
	}
	return isOptional;
    }

    public static boolean isAlphaAndNumeric(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    return !isAlpha(str, true) && !isNumeric(str, true) && isAlphanumeric(str, true);
	}
	return isOptional;
    }

    public static boolean isNumeric(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[0-9]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    public static boolean isUpperAlpha(String str, boolean isOptional)
    {
	if(isNotEmpty(str))
	{
	    Pattern pattern = Pattern.compile("[A-Z]*");
	    Matcher matcher = pattern.matcher(str);
	    return matcher.matches();
	}
	return isOptional;
    }

    /**
     * Method that escapes the following chars: (they can be unescaped by using
     * the method unEscapeCharactersInString(String, JAVA_TYPE_ESCAPE_UNESCAPE)
     * 
     * \b backspace \t horizontal tab \n linefeed \f form feed \r carriage
     * return
     */
    private static String escapeCtrlChars(String str)
    {
	if(str == null)
	{
	    return "";
	}
	int sz;
	sz = str.length();
	StringBuffer retVal = new StringBuffer();
	for(int i = 0; i < sz; i++)
	{
	    char ch = str.charAt(i);
	    switch (ch)
	    {
		case '\b':
		    retVal.append('\\');
		    retVal.append('b');
		    break;
		case '\n':
		    retVal.append('\\');
		    retVal.append('n');
		    break;
		case '\t':
		    retVal.append('\\');
		    retVal.append('t');
		    break;
		case '\f':
		    retVal.append('\\');
		    retVal.append('f');
		    break;
		case '\r':
		    retVal.append('\\');
		    retVal.append('r');
		    break;
		default:
		    retVal.append(ch);
		    break;
	    }

	}
	return retVal.toString();
    }

    /*used in PathJsonUtil and Added to be able to remove old xwork-core jar when upgrading struts core to 2.3.34*/
    public static boolean isBlank(String str)
    {
	int strLen;
	if(str == null || (strLen = str.length()) == 0)
	{
	    return true;
	}
	for(int i = 0; i < strLen; i++)
	{
	    if((Character.isWhitespace(str.charAt(i)) == false))
	    {
		return false;
	    }
	}
	return true;
    }
    
    /*used in PathJsonUtil and Added to be able to remove old xwork-core jar when upgrading struts core to 2.3.34*/
    public static boolean isNotBlank(String str)
    {
	return !isBlank(str);
    }
    
}
