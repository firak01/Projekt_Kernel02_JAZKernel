/*
 * Created on 24.10.2004
 * Eine kleine Hilfsklasse f�r die Behandlung von Strings.
 */
package basic.zBasic.util.datatype.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.json.JsonArrayZZZ;
import basic.zBasic.util.datatype.json.JsonEasyZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
 
/**

@author 0823 ,date 24.10.2004
*/
/**
 * @author lindhauer
 *
 */
/**
 * @author lindhauer
 *
 */
public class StringZZZ implements IConstantZZZ{
	public static final String  sREPLACE_FAR_FROM_MARK = "<NOREPLACE>";             //Dies wird tempor�r in einen String gesetzt, damit der Wert nicht ersetzt wird.
	
	public static final int iSHORTEN_METHOD_NONE = 0; //in der ToShorten(...)-Funktion verwendete Option, hier "nix kürzen"
	public static final int iSHORTEN_METHOD_VOWEL_LOWERCASE = 1; //in der ToShorten(...)-Funktion verwendete Option, die Vokale zu elimenieren (nur Kelinbuchstaben)
	public static final int iSHORTEN_METHOD_VOWEL_UPPERCASE = 2; //in der ToShorten(...)-Funktion verwendete Option, die Vokale zu elimenieren (nur Großbuchstaben)
	public static final int iSHORTEN_METHOD_VOWEL=3; //Sowohl Groß- als auch Kleinbuschstaben. 
	
	private StringZZZ(){
		//zum 'Verstecken" des Konstruktors
	}
	

	
	
	public static String crlf() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			try{
				sReturn = System.getProperty("line.separator");
			}catch(PatternSyntaxException pex){
				ExceptionZZZ ez = new ExceptionZZZ("Pattern seems to be no valid RegEx - PatternSyntaxException: " + pex.getMessage(), iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}//end main
		return sReturn;
	}
	
		
	/** Prueft die Zeichen des Strings gegen die Zeichen des uebergebenen Pattern
	 *   Zur Steuerung dient iFlag:
	 *   = -1  => der String enthaelt nur Zeichen, die im Pattern  enthalten sind
	 *   = 0  => der String enthaelt keine Zeichen, die im Pattern enthalten sind.
	 *   = 1  => das uebergebene Pattern wird als RegEx angesehen und auch so behandelt
	 *    
	 *    
	 *    
	* @param sString
	* @param sPattern
	* @param iFlag
	* @return
	* 
	* lindhaueradmin; 13.04.2008 12:01:34
	 * @throws ExceptionZZZ 
	 */
	public static boolean matchesPattern(String sString, String sPattern, int iFlagIn) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sString)){
				ExceptionZZZ ez = new ExceptionZZZ("No string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("No pattern-string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			int iFlag;
			if(iFlagIn < 0){
				iFlag = -1;
			}else if(iFlagIn > 0){
				iFlag = 1;
			}else{
				iFlag = iFlagIn;
			}
			
			if(iFlag == 1){
				//Behandle den Pattern String als RegEx
				try{
				bReturn = sString.matches(sPattern);
				}catch(PatternSyntaxException pex){
					ExceptionZZZ ez = new ExceptionZZZ("Pattern seems to be no valid RegEx - PatternSyntaxException: " + pex.getMessage(), iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else if(iFlag == 0){
				//Fall: Es darf kein Zeichen des Pattern im String gefunden werden
				String stemp = null;
				for(int icount = 0; icount <= sString.length()-1; icount++){
					stemp = sString.substring(icount, icount+1);
					if(sPattern.indexOf(stemp)!=-1 ) break main;  // -1 d.h. nix gefunden 
				}
				bReturn = true;
			}else if(iFlag == -1){
				//Fall: Jedes Zeichen im String muss aus dem Pattern stammen
				String stemp = null;
				for(int icount = 0; icount <= sString.length()-1; icount++){
					stemp = sString.substring(icount, icount+1);
					if(sPattern.indexOf(stemp)==-1 ) break main;  // -1 d.h. nix gefunden 
				}
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
	
	/**Diese Methode ersetzt in einem String alle Teilstrings durch einen neuen Teilstring.
	 * Buch: "Das Java Codebook",  
	 * Merke: Erst ab JDK 1.5  gibt es eine String.replace(...) Methode, die dasselbe leistet.
	   */
	  public static String replace(String textStr, String oldStr, String newStr) {
		  StringBuffer buffer = new StringBuffer();
		  int textLength = textStr.length();
		  int repLength = oldStr.length();
		  int startIndex = 0;
		  int index = textStr.indexOf(oldStr);
		  while((index >= 0) && (index < textLength)) {
			  //Text ohne den zu ersetzenden String an buffer anh�ngen
			  buffer.append(textStr.substring(startIndex, index));
			  //neuen substring anh�ngen
			  buffer.append(newStr);
			  startIndex = index + repLength;
			  index = textStr.indexOf(oldStr, startIndex);
		  }
		  //letzten Teil des Textes anh�ngen
		  buffer.append(textStr.substring(startIndex, textStr.length()));
		  return buffer.toString();
	  }
	  
	  /**Diese Methode ersetzt am Anfang eines Strings alle gesuchten String durch einen neuen Teilstring.
	   */
	  public static String replaceLeft(String sString, String sOld, String sNew) {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				int iCounter = 0;
				boolean bGoon = false;		
				while(!bGoon){
					if(sReturn.startsWith(sOld)){ //das waere dann .strip(...) :    && sReturn.length()>=2){
						iCounter++;
						sReturn = StringZZZ.rightback(sReturn, sOld.length());													
					}else{
						bGoon = true;
					}
				}
				
				//Nun den String von vorne wieder aufbauen
				for(int i=0;i<iCounter;i++) {
					sReturn = sNew + sReturn;
				}
			}//end main:
			return sReturn;
	  }
	  
	  /**Diese Methode ersetzt am Anfang eines Strings alle gesuchten String durch einen neuen Teilstring.
	   */
	  public static String replaceRight(String sString, String sOld, String sNew) {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				int iCounter = 0;
				boolean bGoon = false;		
				while(!bGoon){
					if(sReturn.endsWith(sOld)){ //das waere dann .strip(...) :    && sReturn.length()>=2){
						iCounter++;
						sReturn = StringZZZ.left(sReturn, (sReturn.length() - sOld.length()));													
					}else{
						bGoon = true;
					}
				}
				
				//Nun den String von vorne wieder aufbauen
				for(int i=0;i<iCounter;i++) {
					sReturn = sReturn + sNew;
				}
			}//end main:
			return sReturn;
	  }
	  
	  
	  
	  /** Ersetze in einem String alle Teilstrings durch den neuen Teilstring. ABER: Keine Ersetzung, wenn das Ziel schon in dem Teilstring vorkommt.
	   * Bsp: 
	   * Aus "aaabcbaaaa" soll c durch bcb ersetzt werden.
	   * Dann liefert das normale replace(..) folgendes Ergebnis: aaabbcbb
	   * DIESE FarFrom-Function ersetzt den Teilstring hier aber nicht.
	   * 
	* @param textStr
	* @param oldStr
	* @param newStr
	* @return
	* 
	* lindhaueradmin; 22.03.2009 14:43:21
	 */
	public static String replaceFarFrom(String textStr, String oldStr, String newStr) {
		  String sReturn = null;
		  main:{
			  if(StringZZZ.isEmpty(textStr)){
				  sReturn = textStr;
				  break main; 
			  }
			  if(StringZZZ.isEmpty(oldStr)){
				 sReturn =  textStr;
				 break main;
			  }
			  if(newStr.equals("")){
				  sReturn = StringZZZ.replace(textStr, oldStr, newStr);
				  break main;
			  }
			  
			  
              //+++ umfangreiches Ersetzten +++++++++
			  
			  if(StringZZZ.contains(newStr, oldStr)){
				  /*NEIN, DAS GEHT NICHT MIT NEM TOKENIZER
				 //String nach dem newStr zerlegen....
				StringTokenizer token = new StringTokenizer(textStr, newStr, false);
				sReturn = "";
				while(token.hasMoreTokens()){
					
					 //... Ersetzung in den Teilstrings ...
						String sReplaceOrig = (String) token.nextToken();
						String stemp = StringZZZ.replace(sReplaceOrig, oldStr, newStr);
											   
						 //... Teilstrings wieder zusammenbauen
						if(sReturn.equals("")){
							sReturn = stemp;
						}else{
							sReturn = sReturn + newStr + stemp;
						}
					}//End while
				*/
				  
	//				String nach dem newStr zerlegen.... (Merke: DAS GEHT NICHT MIT DEM TOKENIZER !!!!)
				  String[] saErg = StringZZZ.explode(textStr, newStr);
				  int icount = 0;
				  for(icount=0; icount <= saErg.length-1; icount++){
					  String sReplaceOrig = saErg[icount];
					  String stemp = StringZZZ.replace(sReplaceOrig, oldStr, newStr);
				     
					  //... Teilstrings wieder zusammenbauen
						if(StringZZZ.isEmpty(sReturn)){
							sReturn = stemp;
						}else{
							sReturn = sReturn + newStr + stemp;
						}
			  }
				  
			  }else{
				  sReturn = StringZZZ.replace(textStr, oldStr, newStr);
			  }
		  }//End main
		  return sReturn;
	  }
	
	/** Drehe den String um.
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 09:07:08
	 */
	public static String reverse(String s){
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(s)) break main;
					
			sReturn = new StringBuilder(s).reverse().toString();
		}
		return sReturn;
	}
	  
	 

	  public static String char2String(char c2String){		 
		return String.valueOf(c2String);
	  }
	  
	  /** char, returns the character at position 0. If s2c.lenght() >= 2 an exception will be thrown
	* Lindhauer; 22.04.2006 07:57:58
	 * @param s2c
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static char string2Char(String s2c) throws ExceptionZZZ{
		if(s2c.length() >= 2) {			
			ExceptionZZZ ez = new ExceptionZZZ("The string should only have one character", 101,  ReflectCodeZZZ.getMethodCurrentName(), ""); 
			//doesn�t work. Only works when > JDK 1.4
			//Exception e = new Exception();
			//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			throw ez;
		}
		  return s2c.charAt(0);
	  }
	
	/** char, returns the character at the index-position. Starting from 0.
	* Lindhauer; 22.04.2006 07:59:18
	 * @param s2c
	 * @param iIndex
	 * @return
	 */
	public static char string2Char(String s2c, int iIndex) throws ExceptionZZZ{	
		if(iIndex <= -2 ){
			ExceptionZZZ ez = new ExceptionZZZ("The index can not be smaller than 0", iERROR_PARAMETER_VALUE,  StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
			//doesn�t work. Only works when > JDK 1.4
			//Exception e = new Exception();
			//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			throw ez;
		}
	return s2c.charAt(iIndex);
}

	public static boolean contains(String sString, String sMatch){
		return StringZZZ.contains(sString, sMatch, true);	
	}
	
	/**
	 * //Merke: sString.contains(CharSequence) gibt es erst seit Java 1.5
			//CharSequence sequence = sMatch.subSequence(1, sString.length());
			//if(sString.contains(sequence)  .......
			
	 * @param sString
	 * @param sMatch
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 17.05.2024, 17:09:30
	 */
	public static boolean contains(String sString, String sMatch, boolean bExactMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			String sSub;
			int iMatchLength=sMatch.length();
			int iProof = sString.length()-iMatchLength;			
			if(bExactMatch){
				for(int icount=0; icount <= iProof; icount++){
					sSub = sString.substring(icount, iMatchLength+icount);
					if(sSub.equals(sMatch)){
						bReturn = true;
						break main;
					}
				}
				
			}else {
			
				String sStringLcased = sString.toLowerCase();
				String sMatchLcased = sMatch.toLowerCase();
				
				for(int icount=0; icount <= iProof; icount++){
					sSub = sStringLcased.substring(icount, iMatchLength+icount);
					if(sSub.equals(sMatchLcased)){
						bReturn = true;
						break main;
					}
				}
			}																
		} //end main:		
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTagXml(String sString, String sMatchTagName)throws ExceptionZZZ{
		return StringZZZ.containsAsTagXml(sString, sMatchTagName, true);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTagXml(String sString, String sMatchTagName, boolean bExactMatch)throws ExceptionZZZ{
		return XmlUtilZZZ.containsTag(sString, sMatchTagName, bExactMatch);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTag(String sString, String sMatchTagStarting, String sMatchTagClosing){
		return StringZZZ.containsAsTag(sString, sMatchTagStarting, sMatchTagClosing, true);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTag(String sString, String sMatchTagStarting, String sMatchTagClosing, boolean bExactMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatchTagStarting)) break main;
			
			boolean bAsTagEmpty;
			if(StringZZZ.isEmpty(sMatchTagClosing)) {				
				bAsTagEmpty = XmlUtilZZZ.isTagEmpty(sMatchTagStarting);
			}else {
				bAsTagEmpty = false;
			}
			
			if(bAsTagEmpty) {
				//Einfach, dann wird es eine Contains
				bReturn = StringZZZ.contains(sString, sMatchTagStarting, bExactMatch);
				break main;
			}
			
			//Merke auf Gleichheit der Tags nicht pruefen.
			//Wenn gleiche Tags vorhanden sind als Eingangswert, dann wird durch die unterschiedlichen Indexwerte zumindest sichergestellt, 
			//dass es zwei verschiedene Stellen sind.
						
			//Nun nicht einfach auf contains prüfen, da die Reihenfolge auch zu beachten ist.
			//StartingTag ist immer VOR dem ClosingTag, auch egal ob Verschachtelt.
			//Wegen verschachtelte Tags muss man dann aber von hinten aus prüfen.
			int iStarting; int iClosing;
			if(bExactMatch) {
				iStarting = sString.indexOf(sMatchTagStarting);
				if(iStarting<=-1)break main;
				
				iClosing = sString.lastIndexOf(sMatchTagClosing);
				if(iClosing<=-1)break main;								
			}else {
				iStarting = sString.toLowerCase().indexOf(sMatchTagStarting.toLowerCase());
				if(iStarting<=-1)break main;
				
				iClosing = sString.toLowerCase().lastIndexOf(sMatchTagClosing.toLowerCase());
				if(iClosing<=-1)break main;								
			}
			
			//Nun noch pruefen ob die Reihenfolge der Tags passt
			if(iClosing <= iStarting) break main;
					
			bReturn = true;
		} //end main:		
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String auch nur irgendein Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsBlankAny(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]==' '){
					bReturn=true;
					break main;
				}
			}			
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String nur Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsBlankOnly(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {				
				if(ca[icount]!=' ') {
					break main;
				}				
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String auch nur irgendeinen Kleinbuchstaben enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsLowercaseAny(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isLowercase(ca[icount])){
					bReturn=true;
					break main;
				}
			}			
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String nur Kleinbuchstaben und Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsLowercaseAndBlankOnly(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isLowercase(ca[icount])){					
						break main;
					}
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String auch nur irgendeine Zahle 0-9 enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:16
	 */
	public static boolean containsNumericAny(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
								
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isNumeric(ca[icount])){						
					bReturn = true;
					break main;										
				}
			}
			
			
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String nur Zahlen 0-9 und Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:16
	 */
	public static boolean containsNumericAndBlankOnly(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
								
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isNumeric(ca[icount])){						
						break main;
					}					
				}
			}
			bReturn = true;
			
		}//end main:
		return bReturn;
	}
	
	
	
	/**
	 * @param sString
	 * @return true, wenn der String auch nur irgendeinen Grossbuchstaben enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:44
	 */
	public static boolean containsUppercaseAny(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isUppercase(ca[icount])){					
					bReturn = true;
					break main;					
				}
			}					
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String nur Grossbuchstaben und Leerzeichen enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:44
	 */
	public static boolean containsUppercaseAndBlankOnly(String sString) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isUppercase(ca[icount])){										
						break main;
					}
				}
			}
			bReturn = true;
			
		}//end main:
		return bReturn;
	}
	
	public static boolean containsOnly(String sString, String sMatch) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			char[]ca = sString.toCharArray();
			char[]cmatch = sMatch.toCharArray();
						
			for(int icount = 0 ; icount < ca.length; icount++) {
				for(int icountInner = 0; icount < cmatch.length; icountInner++) {
					if(ca[icount]!=cmatch[icountInner]){												
						break main;
					}
				}					
			}			
			bReturn = true;
		}
		return bReturn;
	}
	
	public static boolean containsOnly(String sString, String sMatch, String sAllowed) {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			char[]callowed = null;
			if(!StringZZZ.isEmpty(sAllowed)) {
				callowed = sAllowed.toCharArray();
			}
			
			char[]ca = sString.toCharArray();
			char[]cmatch = sMatch.toCharArray();
			
						
			for(int icount = 0 ; icount < ca.length; icount++) {			
				if(!CharArrayZZZ.contains(callowed,ca[icount])) {
					for(int icountInner = 0; icount < cmatch.length; icountInner++) {
						if(ca[icount]!=cmatch[icountInner]){												
							break main;
						}
					}					
				}	
			}			
			bReturn = true;
		}
		return bReturn;
	}
	
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equals(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
			
			bReturn = sString.equals(sMatch);
						
		}//end main:
		return bReturn;
	}
	
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equals(String sString, String sMatch, boolean bExactMatch){
		boolean bReturn = false;
		main:{
			if(bExactMatch) {
				bReturn = StringZZZ.equals(sString, sMatch);
				break main;
			}
			
			
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
							
			bReturn = sString.toLowerCase().equals(sMatch.toLowerCase());
						
		}//end main:
		return bReturn;
	}
	
	
	
	/** Hier muss man nicht auf NULL achten, wenn man den String mit einem Character vergleicht.
	 * @param sString
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 27.10.2022, 12:19:10
	 */
	public static boolean equalsIgnoreCase(String sString, char cMatch){
		boolean bReturn = true;
		main:{
			if(sString==null && cMatch == CharZZZ.getEmpty()){
				bReturn = true;
				break main;
			}
			if(sString!=null && cMatch == CharZZZ.getEmpty()){
				bReturn = false;
				break main;				
			}
			if(sString==null && cMatch != CharZZZ.getEmpty()){
				bReturn = false;
				break main;
			}
									
			char[]ca=sString.toCharArray();
			if(ca.length==0) {
				bReturn = false;
				break main;
			}
			
			if(ca.length>=2) {
				bReturn = false;
				break main;
			}
		
			char c = ca[0];
			if(c!=CharZZZ.toUppercase(cMatch) && c!=CharZZZ.toLowercase(cMatch)) {
				bReturn = false;
				break main;
			}		
			
			
		}//end main:
		return bReturn;
	}
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equalsIgnoreCase(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
			
			bReturn = sString.equalsIgnoreCase(sMatch);
						
		}//end main:
		return bReturn;
	}
	
	/* Unter Java String gibt es nur startsWith.*/
	public static boolean endsWith(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			String sSub = sString.substring(sString.length() - iMatchLength, sString.length());
			if(sSub.equals(sMatch)){
				bReturn = true;
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	/* Unter Java String gibt es nur startsWith. Hier wird zusätzlich noch geleistet, dass Groß-/Kleinschreibung egal ist */
	public static boolean endsWithIgnoreCase(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			String sSub = sString.substring(sString.length() - iMatchLength, sString.length());
			if(sSub.equalsIgnoreCase(sMatch)){
				bReturn = true;
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	/* Unter Java String gibt es nur startsWith. Der Vollstaendigkeit halber hier quasi uebernommen. */
	public static boolean startsWith(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			bReturn = sString.startsWith(sMatch);
			
		} //end main:		
		return bReturn;
	}
	
	public static boolean startsWith(String sString, String sMatch, boolean bExactMatch){		
		if(bExactMatch) {
			return StringZZZ.startsWith(sString, sMatch);
		}else {				
			return StringZZZ.startsWithIgnoreCase(sString, sMatch);
		}			
	}
	
	/* Unter Java String gibt es nur startsWith. Hier wird zusätzlich noch geleistet, dass Groß-/Kleinschreibung egal ist */
	public static boolean startsWithIgnoreCase(String sString, String sMatch){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			String sSub = sString.substring(0, iMatchLength);
			if(sSub.equalsIgnoreCase(sMatch)){
				bReturn = true;
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	/** String, analog to LotusScript, return the iLength number of strings from the left
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, int iPos){
		String sReturn=sString;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
				if(iPos<=0) {
					sReturn = "";
					break main;
				}
			}
			if (iPos > sString.length()){
				sReturn = sString;
			}else{
				sReturn = sString.substring(0, iPos);
			}			
		}//END main:
		return sReturn;
	}
	
	/** String, analog to LotusScript, returns the iLength number of strings from the right. Null if sString is null or empty.
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String right(String sString, int iPos){
		String sReturn=sString;
		main:{
			check:{
				if (StringZZZ.isEmpty(sString)) break main;
				
				sReturn = "";
				if(iPos<=0) break main;				
			}
		
			sReturn = sString;
			int iLength = sString.length();
			int iBeginn = iLength-iPos;
			if(iBeginn<=0) break main;			
			if(iBeginn>=iLength) break main;
		
			sReturn = sString.substring(iBeginn);

		}//END main:
		return sReturn;
	}
	
	/** String, analog to LotusScript, returns the iLength number of strings from the right iPos Position. Null if sString is null or empty.
	* @param sString
	* @param iPos, Indexpositon, beginnt mit 0
	* @param iNumberOfCharacter
	* @return
	* 
	* lindhaueradmin; 18.03.2008 06:29:53
	 */
	public static String mid(String sString, int iPos, int iLength){
		String sReturn = sString;
		main:{
			check:{
				if (StringZZZ.isEmpty(sString)) break main;
				
				sReturn = "";
				if(iPos<0) break main;								
				if(iLength<=0) break main;				
				if(iPos > sString.length())	break main;				
			}
		
			int iIndexRight = iPos + iLength;
			
			//Ohne diese Sicherheitsabfrage wirft Java einen IndexOutOfBounds Fehler
			if(iIndexRight>sString.length()){
				iIndexRight = sString.length();
			}
			sReturn = sString.substring(iPos, iIndexRight);		
		}
		return sReturn;
	}
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, String sToFind){
		return StringZZZ.left(sString, sToFind, true);
	}
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, String sToFind, boolean bExactMatch){
		String sReturn=sString;
		main:{
			if (StringZZZ.isEmpty(sString)) break main;
			sReturn = "";
			
			int iIndex;
			if(bExactMatch){
				iIndex = sString.indexOf(sToFind);				
			}else {
				iIndex = sString.toLowerCase().indexOf(sToFind.toLowerCase()); //Hier wird ignoreCase realisiert.							
			}
			
			if(iIndex<= -1) break main;
			if(iIndex-1<= -1) break main;
			
			sReturn = sString.substring(0, iIndex);
			
		}//END main:
		return sReturn;
	}
	
	
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind, STARTING From the position. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* @param sString
	* @param iPosition
	* @param sToFind
	* @return
	* 
	* lindhauer; 06.07.2007 07:24:05
	 */
	public static String left(String sString, int iPosition, String sToFind){
		String sReturn=sString;
		main:{
			if (StringZZZ.isEmpty(sString)) break main;

			sReturn = "";			
			int iStart = 0;
			if(iPosition < 0) {
				iStart = 0;
			}else{ 
				iStart =  iPosition ;  //Weil wir mit rightback den Wert holen
			}
			String sStart = StringZZZ.rightback(sString, iStart); //Hole den String rechts von der angegebenen Position
						
			int iIndex = sStart.indexOf(sToFind);
			if(iIndex<= -1) break main;
			if(iIndex-1<= -1) break main;
			
			sReturn = sStart.substring(0, iIndex);
			
		}//END main:
		return sReturn;
	}
	
	/** This example returns Lennard. 
@LeftBack("Lennard Wallace"; " ")

	This example returns Lennard Wall. 
@LeftBack("Lennard Wallace";3)
	* @param sString
	* @param sToFind
	* 
	* lindhaueradmin; 04.04.2009 13:15:27
	 */
	public static String leftback(String sString, String sToFind){
		return StringZZZ.leftback(sString, sToFind, true);
	}
	
	/** This example returns Lennard. 
@LeftBack("Lennard Wallace"; " ")

	This example returns Lennard Wall. 
@LeftBack("Lennard Wallace";3)
	* @param sString
	* @param sToFind
	* 
	* lindhaueradmin; 04.04.2009 13:15:27
	 */
	public static String leftback(String sString, String sToFind,boolean bExactMatch){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			sReturn = "";
			if(StringZZZ.isEmpty(sToFind)) break main;
			
			int iPosition = -1;
			if(bExactMatch) {			
				iPosition = sString.lastIndexOf(sToFind);				
			}else {
				iPosition = sString.toLowerCase().lastIndexOf(sToFind.toLowerCase());
			}
			if (iPosition == -1) break main;
			
			sReturn = sString.substring(0, iPosition);				
		}
		return sReturn;
	}
	
	public static String leftback(String sString, int iPosFromTheRight){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			sReturn = "";
			if(iPosFromTheRight<=0) break main;
			int iPosition = sString.length()-iPosFromTheRight;
			if (iPosition <= -1) break main;
					
			sReturn = sString.substring(0, iPosition);				
		}
		return sReturn;
	}
	
	public static String letterAtPosition(String sString, int iIndex){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(iIndex<0) {
				sReturn = null;
				break main;
			}
			
			char[] ca = sString.toCharArray();
			char c = ca[iIndex];
			
			sReturn = Character.toString(c);
		}
		return sReturn;
	}
	
	public static String letterFirst(String sString){
		return StringZZZ.letterAtPosition(sString, 0);
	}
	
	public static String letterLast(String sString){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			sReturn = StringZZZ.letterAtPosition(sString, sString.length()-1);
		}
		return sReturn;
	}
	
	
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * 
	 * Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von rechts nach dem Suchstring gesucht.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @return String
	 */
	public static String right(String sString, String sToFind){
		return StringZZZ.right(sString, sToFind, true);
	}
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return String
	 */
	public static String right(String sString, String sToFind, boolean bExactMatch){
		String sReturn=sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
				
			sReturn = "";
			if(StringZZZ.isEmpty(sToFind)) break main;	
			
		
			int iIndex;
			if(bExactMatch){
				iIndex = sString.lastIndexOf(sToFind);				
			}else {

				String sStringLCase = sString.toLowerCase();
				String sToFindLCase = sToFind.toLowerCase();
				
				iIndex = sStringLCase.lastIndexOf(sToFindLCase);
			}
			if(iIndex<= -1) break main;
			
			//die Länge des Strings aufaddieren
			iIndex = iIndex + sToFind.length();
				
			sReturn = sString.substring(iIndex);						
		}//END main:
		return sReturn;
	}
	
	public static String[] explode(String sString, String sDelimiter){
		String[] saReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sDelimiter==null) break main;
			
			saReturn = StringUtils.splitByWholeSeparator(sString, sDelimiter);
		}//end main
		return saReturn;
	}
	
	public static String[] explode(String sString, String[] saDelimiter){
		String[] saReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isNull(saDelimiter)) break main;
			
			//Eine ArrayList aus den Delimitern machen
			ArrayList<String> listaDelim = StringArrayZZZ.toArrayList(saDelimiter);
			
			//Zerlegen mit dem ersten Delimiter
			String sDelimiter = (String) listaDelim.get(0);
			String[] saTemp = StringUtils.splitByWholeSeparator(sString, sDelimiter);  //Sollte hier nicht der Separator im Array enthalten sein ???
			
			//Alle gefundenen Teilstrings erneut zerlegen, aber (!) die Liste der Delimiter um den gerade verarbeiteten reduzieren
			listaDelim.remove(0);
			if(listaDelim.isEmpty()){
				//Damit den letzten Delimiter gefunden. Array zur�ckgeben
				saReturn = saTemp;
				break main;
			}
			
			ArrayList<String> listaStringFound = new ArrayList<String>();//StringArrayZZZ.toArrayList(saTemp);
			for(int icount=0; icount <= saTemp.length-1; icount++){
				String sStringTemp = saTemp[icount];				
				//-1 korrekt ? 
				String[] saDelimTemp = new String[listaDelim.size()-1];
				saDelimTemp = (String[]) listaDelim.toArray(saDelimTemp);
								
				String[] saTemp2 = StringZZZ.explode(sStringTemp, saDelimTemp);
				ArrayList<String> listaTemp = StringArrayZZZ.toArrayList(saTemp2);
				
				//Nun das Ergebnis zu dem bisherigen hinzufuegen
				listaStringFound = (ArrayList<String>) ArrayListZZZ.join(listaStringFound, listaTemp, false);
			}		
			saReturn = new String[listaStringFound.size()-1];
			saReturn = (String[]) listaStringFound.toArray(saReturn);
		}//end main
		return saReturn;
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sLeftSep, String sRightSep) throws ExceptionZZZ{
		return StringZZZ.vecMid(sStringToParse, sLeftSep, sRightSep, true);
	}
	
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ{
		return StringZZZ.vecMid(sStringToParse, sLeftSep, sRightSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sSepLeft, String sSepRight, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sSepLeft, bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepLeft.length());
						
			String sExpressionTagged = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch);
			if(StringZZZ.isEmpty(sExpressionTagged)){
				vecReturn.add("");
				vecReturn.add(sStringToParse);
				vecReturn.add("");
				break main;
			}
			
			
			//nun gibt es einen Ausdruck			
//			String sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sRightSep.length());//Das wuerde bei verschachtelten XML Strings funktionieren.			
//			//String sRight = StringZZZ.leftback(sRemainingTagged, sRightSep); //Das wirkt aber nicht bei verschachtelten XML Strings...
//			if(sRight==null) sRight = "";
			
			//nun gibt es einen Ausdruck			
			//String sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sRightSep.length()); //Das wuerde bei verschachtelten XML Strings funktionieren
			//String sMid = StringZZZ.left(sRemainingTagged, sRightSep, bExactMatch); //Das wirkt aber nicht bei verschachtelten XML Strings..., statt dessen wird tatsächlich der erste passende String geholt.
			String sMid = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch); //Das wirkt aber nicht bei verschachtelten XML Strings..., statt dessen wird tatsächlich der erste passende String geholt.
			String sRight = new String("");
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sSepRight.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				//sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sMid.length()-sRightSep.length());
				if(sRight==null) sRight = "";
			}
						
			if(vecReturn!=null) {
				//Nun die Werte in den ErgebnisVector zusammenfassen
				vecReturn.replace(sLeft, sMid, sRight, bReturnSeparators, sSepLeft, sSepRight);
				
//				if(bReturnSeparators ==true && !StringZZZ.isEmpty(sMid)){
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);
//					if(!StringZZZ.isEmpty(sLeft)){
//						vecReturn.add(0, sLeft + sLeftSep);
//					}else {
//						vecReturn.add(0, sLeftSep);
//					}
//										
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
//					vecReturn.add(1, sMid); //zentral wichtig: In der Mitte immer das "Extrakt"
//					
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);											
//					if(!StringZZZ.isEmpty(sRight)){
//						vecReturn.add(2, sRightSep + sRight);
//					}else {
//						vecReturn.add(2, sRightSep);
//					}
//				}else if(bReturnSeparators ==false && !StringZZZ.isEmpty(sMid)){
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
//					if(!StringZZZ.isEmpty(sLeft)){
//						vecReturn.add(0, sLeft);
//					}else {
//						vecReturn.add(0, "");
//					}
//					
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
//					vecReturn.add(1, sMid);
//					
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
//					if(!StringZZZ.isEmpty(sRight)){
//						vecReturn.add(2, sRight);
//					}else {
//						vecReturn.add(2, "");
//					}
//				} else if(bReturnSeparators ==true && StringZZZ.isEmpty(sMid)){
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
//					if(!StringZZZ.isEmpty(sLeft)){
//						vecReturn.add(0, sLeft + sLeftSep);
//					}else {
//						vecReturn.add(0, sLeftSep);
//					}
//					
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
//					vecReturn.add(1, "");
//					
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
//					if(!StringZZZ.isEmpty(sRight)){
//						vecReturn.add(2, sRightSep + sRight);
//					}else {
//						vecReturn.add(2, sRightSep);
//					}
//				} else {
//					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
//					if(!StringZZZ.isEmpty(sLeft)){
//						vecReturn.add(0, sLeft);
//					}else {
//						vecReturn.add(0, "");
//					}
//					
//					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
//					vecReturn.add(1, sMid);
//					
//					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
//					if(!StringZZZ.isEmpty(sRight)){
//						vecReturn.add(2, sRight);
//					}else {
//						vecReturn.add(2, "");
//					}
//				}
			}//end vecReturn != null			
		}//end main:
		return vecReturn;		
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls der Trenner selbst zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Diese Methode dient z.B. dazu einen leeren Tag zu bearbeiten.
	 *        Ansonsten sind 2 Trenner notwendig.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String> vecMidFirst(String sStringToParse, String sMidSep, boolean bReturnSeparators) throws ExceptionZZZ{
		return StringZZZ.vecMidFirst(sStringToParse, sMidSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls der Trenner selbst zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Diese Methode dient z.B. dazu einen leeren Tag zu bearbeiten.
	 *        Ansonsten sind 2 Trenner notwendig.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirst(String sStringToParse, String sSepMid, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepMid)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sSepMid,bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepMid.length());
			String sRight = sRemainingTagged;
									
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sRight, bReturnSeparators, sSepMid);
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String> vecMidFirst(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ{
		return StringZZZ.vecMidFirst(sStringToParse, sLeftSep, sRightSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirst(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sLeftSep)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sRightSep)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sLeftSep,bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sLeftSep.length());
						
			String sExpressionTagged = StringZZZ.left(sRemainingTagged, sRightSep, bExactMatch);//!!!nur left, wg. "First", anders als sonst leftback!!!
			if(StringZZZ.isEmpty(sExpressionTagged)){
				vecReturn.add("");
				vecReturn.add(sStringToParse);
				vecReturn.add("");
				break main;
			}
			
			
			//nun gibt es einen Ausdruck			
			//String sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sRightSep.length()); //Das wuerde bei verschachtelten XML Strings funktionieren
			String sMid = StringZZZ.left(sRemainingTagged, sRightSep, bExactMatch); //Das wirkt aber nicht bei verschachtelten XML Strings..., statt dessen wird tatsächlich der erste passende String geholt.
			String sRight = "";
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				//hmm...bei path ist das falsch, da man ein Zeichen hinzumogelt, korrektur auch woanders gut?
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sMid.length()-sRightSep.length());
				
				//das ist allgemein ein Fehler, funktioniert nur explizit beim IniPathSolver!!!
				//sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sMid.length());
			}
			
			if(vecReturn!=null) {
				//Nun die Werte in den ErgebnisVector zusammenfassen
				if(bReturnSeparators ==true && !StringZZZ.isEmpty(sMid)){
					if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
					if(!StringZZZ.isEmpty(sLeft)){
						vecReturn.add(0, sLeft + sLeftSep);
					}else {
						vecReturn.add(0, sLeftSep);
					}
										
					if(vecReturn.size()>=2) vecReturn.removeElementAt(1);						
					vecReturn.add(1, sMid);//zentral wichtig: In der Mitte immer das "Extrakt".
					
					if(vecReturn.size()>=3) vecReturn.removeElementAt(2);											
					if(!StringZZZ.isEmpty(sRight)){
						vecReturn.add(2, sRightSep + sRight);
					}else {
						vecReturn.add(2,  sRightSep);
					}
				}else if(bReturnSeparators ==false && !StringZZZ.isEmpty(sMid)){
					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
					if(!StringZZZ.isEmpty(sLeft)){
						vecReturn.add(0, sLeft);
					}else {
						vecReturn.add(0, "");
					}
					
					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
					vecReturn.add(1, sMid);
					
					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
					if(!StringZZZ.isEmpty(sRight)){
						vecReturn.add(2, sRight);
					}else {
						vecReturn.add(2, "");
					}
				
				} else if(bReturnSeparators ==true && StringZZZ.isEmpty(sMid)){
					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
					if(!StringZZZ.isEmpty(sLeft)){
						vecReturn.add(0, sLeft + sLeftSep);
					}else {
						vecReturn.add(0, sLeftSep);
					}
					
					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
					vecReturn.add(1, "");
					
					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
					if(!StringZZZ.isEmpty(sRight)){
						vecReturn.add(2, sRightSep + sRight);
					}else {
						vecReturn.add(2, sRightSep);
					}
				}  else {
					if(vecReturn.size()>=1) vecReturn.removeElementAt(1);						
					if(!StringZZZ.isEmpty(sLeft)){
						vecReturn.add(0, sLeft);
					}else {
						vecReturn.add(0, "");
					}
					
					if(vecReturn.size()>=2) vecReturn.removeElementAt(2);						
					vecReturn.add(1, "");
					
					if(vecReturn.size()>=3) vecReturn.removeElementAt(3);						
					if(!StringZZZ.isEmpty(sRight)){
						vecReturn.add(2, sRight);
					}else {
						vecReturn.add(2, "");
					}
				}
			}//end vecReturn!=null
		}//end main:
		return vecReturn;
	}
	
	public static Vector3ZZZ<String>vecSplitFirst(String sStringToParse, String sSeparator, boolean bReturnSeparators) throws ExceptionZZZ {		
		return StringZZZ.vecSplitFirst(sStringToParse, sSeparator, bReturnSeparators, true);
	}
	
	public static Vector3ZZZ<String>vecSplitFirst(String sStringToParse, String sSeparator, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ {
		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSeparator)){				
				ExceptionZZZ ez = new ExceptionZZZ("Separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sSeparator,bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSeparator.length());
			if(sRemainingTagged==null) sRemainingTagged="";
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.add(sLeft);
			
			if(bReturnSeparators ==true){				
				vecReturn.add(sSeparator);
			}else {
				vecReturn.add("");
			}
						
			vecReturn.add(sRemainingTagged);									
		}//end main:
		return vecReturn;
	}
	
	/** Fülle vor den String Leerzeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:53
	 */
	public static String padLeft(String sInput, int length) {
		 return String.format("%" + length + "s", sInput);	
	}
	
	/** Fülle vor den String das übergebene Zeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:53
	 */
	public static String padLeft(String sInput, int length, char cFiller) {
		return String.format("%1$" + length + "s", sInput).replace(' ', cFiller);
	}
	
	/** Fülle hinter den String Leerzeichen, bis zur angegebenen Anzahl Zeichen gesamt. 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:48
	 */
	public static String padRight(String sInput, int length) {
		 return String.format("%-" + length + "s", sInput);  
	}
	
	/** Fülle vor den String das übergebene Zeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:48
	 */
	public static String padRight(String sInput, int length, char cFiller) {
		return String.format("%1$-" + length + "s", sInput).replace(' ', cFiller);
	}
	
	/** String, concatenate the string the number of times.
	 * e.g. .repeat("xyz", 2) will return "xyzxyz".
	* 0823; 28.05.2006 10:53:01
	 * @param sString
	 * @param iTimes
	 * @return String
	 */
	public static String repeat(String sString, int iTimes){
		String sReturn = sString;
		main:{
			check:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(iTimes <= 0) {
					sReturn = "";
					break main;
				}						
				if(iTimes==1) break main;				
			}//END check:
		
			for(int icount=2; icount <= iTimes; icount ++){
			 sReturn = sReturn + sString;	
			}
			
			/* Aus "Java f�r Domino.". Ist das besser ???? Nutzt StringBuffer !!!
  private static String times(String s, int n)
    {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < n; i++) {
			b.append(s);
		}
		return b.toString();
	}
			 */
			
		}//END main:
		return sReturn;
	}
	
	/** Wiederhole die Zeichen x mal.
	 *  Merke: Die padLeft / padRight Methoden machen so etwas ähnliches mit Füllzeichen
	 *         aber auf eine andere Weise.
	 * @param c
	 * @param icount
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:18:41
	 */
	public static final String repeatChar (char c, int icount) {
		String sReturn = "";
		main:{
			check:{
				if(icount <=0) break main;
			}
			StringBuffer sb = new StringBuffer();
			for (int k=0;k<icount;k++) {
				sb.append(c);
			}
			sReturn = sb.toString();
		}//END main:
		return sReturn;
	}
	
	public static String word(String sString, String sDelimiter, long lPosition){
		String sReturn = sString;
		main:{
			check:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sDelimiter)) break main;
				if(lPosition <= 0) {
					sReturn = null;
					break main;								
				}
		}//END check
		
		long lcount=0;
		StringTokenizer objToken = new StringTokenizer(sString, sDelimiter);
		while( objToken.hasMoreTokens()){
			lcount++;
			sReturn = objToken.nextToken();
			if(lcount ==lPosition) break main;
		}
		
		//Falls man hierhin kommt, hat man den String an der Stelle nicht gefunden.
		sReturn = "";
		
		}//END main
		return sReturn;
	}
	
	/**returns true if the string is empty or null.
	 * FGL: D.h. NULL oder Leerstring 
	 * 
	 * Uses Jakarta commons.lang.
	 * @param sString
	 * @return, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 08:52:50
	 */
	public static boolean isEmpty(String sString){
		return StringUtils.isEmpty(sString);
	}
	
	public static boolean isEmptyNull(String sString){		
		if(sString==null) return true;
		if(sString.equals("")) return true;
		return false;
	}
	
	public static boolean isEmptyTrimmed(String sString){		
		if(sString==null) return true;
		if(sString.trim().equals("")) return true;
		return false;
	}
	
	public static boolean isFloat(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			//check if float
		    try{
		        Float.parseFloat(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not float
		    }
		}//end main:
		return bReturn;		
	}
	
	/** Alles was Integer ist, ist dann nicht Float.
	 *   Auch wenn Float.parseFloat(sString) keine NumberFormatException werfen würde.
	 * @param sString
	 * @return
	 */
	public static boolean isFloatExplizit(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			//check if Integer
			if(StringZZZ.isInteger(sString)) break main;
			
			//check if float
		    try{
		        Float.parseFloat(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not float
		    }
		}//end main:
		return bReturn;		
	}
	
	public static boolean isInteger(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			 //check if int
		    try{
		        Integer.parseInt(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not int
		    }
		}//end main:
		return bReturn;		
	}
	

	
	/**returns true if the string is empty or null or containing blanks only.
	 * FGL: D.h. NULL oder Leerstring oder ein String mit nur Leerzeichen drin.
	 * 
	 * Uses Jakarta commons.lang.
	 * @param sString
	 * @return, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 08:52:50
	 */
	public static boolean isBlank(String sString){
		return StringUtils.isBlank(sString);
	}
	
	public static boolean isJson(String sString) throws ExceptionZZZ {
		return JsonEasyZZZ.isJsonValid(sString);
	}
	
	public static boolean isWhitespace(String sString){
		return StringUtils.isWhitespace(sString);
		/*
		String sLineProof = sLine.trim();
		if(StringZZZ.isEmpty(sLineProof)){
			bReturn = true;
			break main;
		}*/
	}
	
	
	/** true, wenn der erste Buchstabe ein Kleinbuchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:15:12
	 */
	public static boolean isLowerized(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toLowerCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}
	
	
	/** true, wenn der erste Buchstabe ein Gro�buchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:16:44
	 */
	public static boolean isCapitalized(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toUpperCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}

	/** Checks if the CharSequence contains only Unicode letters.

null will return false. An empty CharSequence (length()=0) will return false.

 StringUtils.isAlpha(null)   = false
 StringUtils.isAlpha("")     = false
 StringUtils.isAlpha("  ")   = false
 StringUtils.isAlpha("abc")  = true
 StringUtils.isAlpha("ab2c") = false
 StringUtils.isAlpha("ab-c") = false
 
	 * 
	 * 
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 14.01.2023, 08:45:10
	 */
	public static boolean isAlphabet(String sString){
		return StringUtils.isAlpha(sString);
	}
	
	/** Checks if the CharSequence contains only Unicode letters or digits.

null will return false. An empty CharSequence (length()=0) will return false.

 StringUtils.isAlphanumeric(null)   = false
 StringUtils.isAlphanumeric("")     = false
 StringUtils.isAlphanumeric("  ")   = false
 StringUtils.isAlphanumeric("abc")  = true
 StringUtils.isAlphanumeric("ab c") = false
 StringUtils.isAlphanumeric("ab2c") = true
 StringUtils.isAlphanumeric("ab-c") = false
 
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 14.01.2023, 08:47:53
	 */
	public static boolean isAlphanumeric(String sString){
		return StringUtils.isAlphanumeric(sString);
	}
	
	/**    Checks if the String contains only unicode digits.
	 * 	Uses Jakarta commons.lang.
	* @return boolean
	* @param sString
	* lindhaueradmin; 24.10.2006 09:16:35
	 */
	public static boolean isNumeric(String sString){
		return StringUtils.isNumeric(sString);
	}
	public static boolean isNumericWithPrefix(String sString) {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=1) break main; //Für einen Wert mit Vorzeichen muss es zumindest 2 Zeichen geben....
			
			//Hole das 1. Zeichen.
			char c = sString.toCharArray()[0];
			boolean bHasNumericPrefix = CharZZZ.isNumericPrefix(c);
			if(!bHasNumericPrefix) break main;
			
			String sRest = StringZZZ.rightback(sString, 1);
			if(StringZZZ.isNumeric(sRest)) bReturn = true;
						
		}//end main:
		return bReturn;		
	}
	
	/** Beispiel für ein Palindrom: "ANNA".
	 *   Ein einzelner Buchstabe ist kein Palindrom.
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 12:45:46
	 */
	public static boolean isPalindrom(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=1) break main;
			
			String sStringReversed = StringZZZ.reverse(sString);
			if(sStringReversed.equals(sString)){
				bReturn = true;
			}			
		}
		return bReturn;
	}
	
	/** true, wenn der erste Buchstabe ein Kleinbuchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:15:12
	 */
	public static boolean isUpperized(String sString){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toUpperCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}
	
	/**https://stackoverflow.com/questions/46116882/how-to-check-for-consecutive-double-character-in-a-java-string
	 * @param str
	 * @return
	 * @author Fritz Lindhauer, 17.11.2020, 11:30:57
	 */
	public static boolean hasConsecutiveDuplicateCharacter(String str) {
	    Pattern p = Pattern.compile("(.)\\1");
	    Matcher m = p.matcher(str);
	    return m.find();
	}
	
	public static boolean hasConsecutiveDuplicateCharacter(String str, char cToFind) {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(str))break main;
			
			String sToFind = CharZZZ.toString(cToFind);
			if(StringZZZ.isEmpty(sToFind)) break main;
						
			Pattern p = Pattern.compile("("+sToFind+")\\1");
		    Matcher m = p.matcher(str);
		    bReturn = m.find();
		}//end main:
		return bReturn;						    
	}
	
	/**@param str
	 * @return
	 * @author Fritz Lindhauer, 17.11.2020, 11:30:57
	 */
	public static boolean endsWithConsecutiveDuplicateCharacter(String str) {
	    Pattern p = Pattern.compile("(.)\\1$");
	    Matcher m = p.matcher(str);
	    return m.find();
	}
	
	/** Packt ein Stringarray und einen String zusammen.
	 *  Meke: In Java 8 gibt es diese Funktion schon. Hier für andere Java Versionen einsetzbar.
	 * @param saString
	 * @param sString
	 * @return
	 */
	public static String join(String[] saString, String sString, String sFlag) throws ExceptionZZZ {
		//FGL: Erst ab Java 8: return String.join(File.separator, pathElements);
    	String sReturn = new String("");
	    main:{    		
			if(StringZZZ.isEmpty(sFlag)){
				sFlag = "BEFORE";				
			}

	        String[] saTemp = StringArrayZZZ.plusString(saString, File.separator, sFlag);		
	       	sReturn = StringArrayZZZ.implode(saTemp);
	    }//End main
    	return sReturn;
	}
	
	/** The first letter of the string will become a capital letter. E.g. "abcd" => "Abcd"
	 * Uses Jakarta commons.lang.
	* @param sString
	* @return
	* 
	* lindhauer; 13.06.2007 06:58:41
	 */
	public static String capitalize(String sString){
		return StringUtils.capitalize(sString);
	}
	
	/** The first letter of the String will become lowercase. E.g. "CAT" => "cAT"
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:28:13
	 */
	public static String uncapitalize(String sString){
		return StringUtils.uncapitalize(sString);
	}
	
	
	/** THIS_IS_AN_EXAMPLE_STRING wird zu ThisIsAnExampleString    , wenn der Delimiter "_" ist.
	* @param s
	* @param sDelimiter
	* @return
	* 
	* lindhauer; 20.09.2011 14:46:19
	 */
	public static String toCamelCase(String sString, String sDelimiter){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sDelimiter)) break main;
			
			//+++++++++++
		   String[] parts = sString.split(sDelimiter);
		   
		   for (int icount=0; icount <= parts.length-1; icount++){
			   String part = parts[icount];
			   sReturn = sReturn + toProperCase(part);
		   }
		}//endmain:
	   return sReturn;
	}
	
	/** Dieser CamelCase arbeitet ohne Delimiter. 
	 *   Wenn der nachfolgende Buchstabe ein Gro�buchstabe ist, wird der aktuelle Buchstabe zum Kleinbuchstaben.
	* @param s
	* @return
	* 
	* lindhauer; 20.09.2011 14:54:55
	 */
	public static String toCamelCase(String sString){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
					
			//+++++++++++
		   sReturn = "";
		   for (int icount=0; icount <= sString.length()-2; icount++){
			   String cur = sString.substring(icount,icount+1);
			   String part = sString.substring(icount+1,icount+2); //der n�chste teilstring
			   if(StringZZZ.isCapitalized(part)){
				   //Wenn der Folgebuchstabe ein Großbuchstabe ist, dann den aktuellen Buchstaben zum Kleinbuchstaben umwandeln
				   sReturn = sReturn + cur.toLowerCase();
			   }else{
				   //ansonsten den Wert beibehalten
				   sReturn = sReturn + cur;
			   }
		   }
		   
		   //Den letzten Wert uebernehmen.
		   sReturn = sReturn + sString.substring(sString.length()-1, sString.length() );
		   
		}//endmain:
		return sReturn;
	}

		public static String toProperCase(String sString) {
			String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				sReturn = sString.substring(0, 1).toUpperCase() +  sString.substring(1).toLowerCase();
			}
		    return sReturn;
		}
		
		public static String toShorten(String sString, int iShortenMethodType, int iOffset) throws ExceptionZZZ{
			String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
								
				//Ersetz werden nur Worte >= 3 Buchstaben mehr als das OFFSET
				if(sString.length() <= 3 + iOffset){
					sReturn = sString;
					break main;
				}
				
				String sOffset = sString.substring(0,iOffset);
				String sToParse = sString.substring(iOffset);
				
				org.apache.regexp.RE objReRemove = null;
				switch(iShortenMethodType){
				case StringZZZ.iSHORTEN_METHOD_NONE:
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL_LOWERCASE:					
					objReRemove = new org.apache.regexp.RE("[aeiou]\\B");				//als Beispiel für einen sinnvollen Parameter: Entferne alle Vokale...Wortende:		string.replaceAll("[aeiou]\\B", "")
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL_UPPERCASE:					
					objReRemove = new org.apache.regexp.RE("[AEIOU]\\B");				
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL:					
					objReRemove = new org.apache.regexp.RE("[aAeEiIoOuU]\\B");		
					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Not configured ShortenMethodType.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
				}
								
			if(objReRemove!=null){
				sReturn = objReRemove.subst(sToParse,"");
			}else{
				sReturn = sToParse; //ausser Spesen nix gewesen....
			}
			sReturn = sOffset + sReturn;
				
			}
		    return sReturn;
		}
		
		
		/* http://www.kodejava.org/examples/266.html
		 * Von FGL angepasst
		 */
		public static InputStream toInputStream(String sString){
			InputStream objReturn=null;
			main:{
				if(sString==null) break main;			
				try {
					objReturn = new ByteArrayInputStream(sString.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return objReturn;
		}
						
		public static JsonArrayZZZ toJsonArray(String sString) throws ExceptionZZZ {
			JsonArrayZZZ objReturn = null;
			main:{
				objReturn = new JsonArrayZZZ(sString);											
			}//end main
			return objReturn;
		}
		
	     /* 
	      * To convert the InputStream to String we use the
	      * Reader.read(char[] buffer) method. We iterate until the
	      * Reader return -1 which means there's no more data to
	      * read. We use the StringWriter class to produce the string.
	      * 
	      * http://www.kodejava.org/examples/266.html
	      * Von FGL angepasst
	      */
		 public String InputStream2String(InputStream is) {
			 String sReturn = null;
			 try{
			 if (is != null) {				 
				 Writer writer = new StringWriter();
				 char[] buffer = new char[1024];
					 Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					 int n;
					 while ((n = reader.read(buffer)) != -1) {
						 writer.write(buffer, 0, n);
					 }				
				  sReturn =  writer.toString();
			 	} else {        
			 	  sReturn = new String("");
			 	}
			 }catch(Exception e){
			 	e.printStackTrace();
			 } finally {
				 try{
					 is.close();
				 }catch(IOException e){
					 e.printStackTrace();
				 }
			 }
			 return sReturn;
		 }

	
	/** Counts the occurance of the second String in the first one.
	 *    Like countMatches()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:56:01
	 */
	public static int count(String sString, String sToFind){
		return StringUtils.countMatches(sString, sToFind);
	}
	
	/** Counts the occurance of the second String in the first one.
	 *    Like count()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:59:27
	 */
	public static int countMatches(String sString, String sToFind){
		return StringUtils.countMatches(sString, sToFind);
	}
	
	public static int countSubstring(String sString, String sToFind){
		int iReturn=0;
		main:{
			if(StringZZZ.isEmpty(sString) || StringZZZ.isEmpty(sToFind)){
				break main;
			}
			
			//Also: Es wird geguckt, ob der String noch im restlichen Teistring vorhanden ist,
			//        Falls ja, setze die Indexposition so, dass der restliche Teilstring hinter dem zuletzt gefundenen String beginnt.
			//        Dann wieder gucken... usw.
			int idx = 0;
			while((idx=sString.indexOf(sToFind, idx)) != -1){
				iReturn++;
				idx+=sToFind.length();
			}
					
		}//end main:
		return iReturn;
	}
	
	public static int countChar(String sString, char cToFind){
		int iReturn=0;
		main:{
			//Merke: Als primitiver Datentyp kann char nicht null sein
			if(StringZZZ.isEmpty(sString)){
				break main;
			}
			
			for(int i=0; i<sString.length(); i++){
				if(sString.charAt(i)==cToFind){
					iReturn++;
				}
			}
			
		}//end main:
		return iReturn;
	}
	
	public static int countChar(String sString, Character objChar){
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sString) || objChar == null){
				break main;
			}
			char cToFind = objChar.charValue();
			iReturn = StringZZZ.countChar(sString, cToFind);
		}		
		return iReturn;
	}
	
	
	/** Returns the right part, but iPosition is counted from the left.
	 * e.g. StringZZZ.rightback("abcde", 2) will return "cde".
	 * 
	 * This example returns "nard Wallace." 
@RightBack("Lennard Wallace";3) 
	 * 
	* @param sString
	* @param iPosition
	* @return
	* 
	* lindhauer; 15.06.2007 00:52:08
	 */
	public static String rightback(String sString, int iPosition){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			
			if(iPosition >= sString.length()) {
				sReturn = "";
				break main;
			}
			if(iPosition <= 0){
				sReturn = sString;
				break main;
			}
			
			sReturn = sString.substring(iPosition, sString.length());
		}
		return sReturn;
	}
	
	/**Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 * @param sString
	 * @param sToFind
	 * @return
	 */
	public static String rightback(String sString, String sToFind){
		return StringZZZ.rightback(sString, sToFind, true);
	}
	
	/**Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 *  bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return
	 */
	public static String rightback(String sString, String sToFind, boolean bExactMatch){
		String sReturn = "";
		main:{
			
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) break main;
			
			int iIndex;
			if(bExactMatch) {
				iIndex = sString.indexOf(sToFind);				
			}else {
			
				String sStringLCase = sString.toLowerCase();
				String sToFindLCase = sToFind.toLowerCase();
			
				iIndex = sStringLCase.indexOf(sToFindLCase);
			}
			if(iIndex<= -1) break main;
		
			//die Länge des Strings aufaddieren
			iIndex = iIndex + sToFind.length();
				
			sReturn = sString.substring(iIndex);
		}
		return sReturn;
	}
	
	
	/** returns the string cut on the left and on the right
	* @param sString
	* @param iLeftPosition
	* @param iRightPosition
	* @return
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, int iLeftPosition, int iRightPosition){
		String sReturn = "";
		main:{
			
			if(StringZZZ.isEmpty(sString))break main;
			if ((iLeftPosition + iRightPosition) > sString.length()) break main;
						
			sReturn = StringZZZ.rightback(sString, iLeftPosition);
			if(StringZZZ.isEmpty(sReturn)) break main;
			if(iRightPosition-iLeftPosition >= sReturn.length()){
				sReturn = "";
				break main;
			}
			
			sReturn = StringZZZ.left(sReturn, (iRightPosition - iLeftPosition));
			if(StringZZZ.isEmpty(sReturn)) break main;			
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString
	* @param iLeftPosition
	* @param iRightPosition
	* @return
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midBounds(String sString, int iLeftPositionFromLeft, int iRightPositionFromRight){
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			int iRightPosition = sString.length() - iRightPositionFromRight;
			if(iRightPosition <= iLeftPositionFromLeft) break main;
			
			sReturn = StringZZZ.midLeftRight(sString, iLeftPositionFromLeft, iRightPosition);
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightback(String sString, String sLeft, String sRight){
		return StringZZZ.midLeftRightback(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightback(String sString, String sLeft, String sRight, boolean bExactMatch){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
			
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.left(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				break main;
			}
			
			if(StringZZZ.equals(sLeft, sRight, bExactMatch)) {
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch); //dann also wie in midLeftRight(...)
				sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			}else {
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);
			}
		
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: [[test]]
	* @param sLeft    e.g.: [
	* @param sRight  e.g.: ]
	* @return   ==>          test
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, String sLeft, String sRight){
		return StringZZZ.midLeftRight(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, String sLeft, String sRight, boolean bExactMatch){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
								
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
			sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			

		
		}
		return sReturn;
	}
	
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: [[test]]
	* @param sLeft    e.g.: [
	* @param sRight  e.g.: ]
	* @return   ==>          test
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightIntersect(String sString, String sLeft, String sRight){
		return StringZZZ.midLeftRightbackIntersect(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightbackIntersect(String sString, String sLeft, String sRight, boolean bExactMatch){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
			
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			 
			//Normalerweise wird aber das midLeftRightback Ergebnis erwartet.
//			sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
//			sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			
			//Merke: Nur nach links und rechts abzuprüfen ist nun logisch richtig. 
			//       Um sie vom "erwarteten midLeftRighback Ergebnis abzugrenzen, bekommt diese Methode daher einen besonderen Namen "...Intersected(...)
			
			if(StringZZZ.equals(sLeft, sRight, bExactMatch)) {
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch); //dann also wie in midLeftRight(...)
				sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			}else {
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);
			}
		
		}
		return sReturn;
	}
	
	/** Ersetze ae, ue, ss, oe in dem übergebenen String.
	 *    Dabei wird versucht besonderheiten zu Berücksichtigen:
	 *    - Ersetzt werden nur Worte >= 4 Buchstaben (so bleibt z.B. Suez bestehen).
	 *    
	 *    ae) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    
	 *    oe) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *          So bleibt Oelde bestehen.
	 *          
	 *    ss) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    	    So bleibt WaffenSS bestehen. (mir fiel kein anderes Wort ein, sorry).
	 *    
	 *    ue) Es wird das ganze Wort betrachtet.
	 *         
	 *          
	 *     Tip: Aufgrund der vielen Ausnahmen sollte man vorher prüfen, ob es nicht schon einen Umlaut gibt:
	 *     org.apache.regexp.RE objReUmlaut = new org.apache.regexp.RE("[ÖöÜüÄäß]");
			boolean bHasUmlaut = objReUmlaut.match(sAllRaw);
	 *          
	* @param sString2Parse
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.09.2008 09:19:44
	 */
	public static String replaceCharacterGerman(String sString2Parse)throws ExceptionZZZ{
		String sReturn = "";
		main:{
			//Ersetz werden nur Worte >= 4 Buchstaben, die Kleingeschreiben sind So bleibt z.B. Suez bestehne
			if(sString2Parse.length() <= 3){
				sReturn = sString2Parse;
				break main;
			}
			
			
			//leider ist es mir nicht gelungen das alles auf einen streich zu definieren und zu ersetzen        org.apache.regexp.RE objRe = new org.apache.regexp.RE("[(oe)]|[(ae)]|[(ue)]|[(ss)]");
			//Daher werden 4 Objekte definiert.
			
			//als Beispiel für einen sinnvollen Parameter			objRe1.setMatchFlags(org.apache.regexp.RE.MATCH_CASEINDEPENDENT); 			
			// stemp = objRe.subst(stemp, "[ö]|[ä]|[ü]|[ß]");//objRe.subst("[(oe)][(ae)][(ue)][(ss)]","[ö][ä][ü][ß]"); oder ähnlich.
			//als ein Beispiel für ne statische Methode: String stemp2 =org.apache.regexp.RE.simplePatternToFullRegularExpression("oe");
			//Merke: Als Beispiel für die Prüfung: 					//boolean bMatch = objRe.match(stemp);					   			   
			org.apache.regexp.RE objReOe = new org.apache.regexp.RE("(oe)");			
			org.apache.regexp.RE objReAe = new org.apache.regexp.RE("(ae)");
			org.apache.regexp.RE objReUe1 = new org.apache.regexp.RE("(ue)");
			
								
			//Problem: Es wird auch der erste Buchstabe mit umgewandelt, bei: sReplace = objRe3.subst(sReplace, "ü"); //Daher wird .match(sValue) verwendet  
			//Groß- und Kleinbuchstaben der deutschen Umlaute (sofern Großbuchstabe vorhanden) wg. ANSI Problematik.
			org.apache.regexp.RE objReUe = new org.apache.regexp.RE("([a-pr-zA-PR-Z0-9öÖÜäÄß]){1}(ue)");  //nicht das kleine ü selber und kein q
			//also kein q, wie z.B. in Nilquelle vor dem ue. ue am Anfang darf aber umgewandelt werden.
			//Auch nicht Queen
			
			//Problem: Beim Replacen wird hier auch der erste Buchstabe mit umgewandelt.
			//org.apache.regexp.RE objReSs = new org.apache.regexp.RE("([a-xzA-XZ0-9]){1}(ss)|^(ss)");   //wie. z.B. in Odyssee, hier nicht umwandeln. Das würde Asseln umwandlen, weil ss am Anfang stehen dürfte.
			//org.apache.regexp.RE objReSs = new org.apache.regexp.RE("([a-xzA-XZ0-9öÖüÜäÄ]){1}(ss)");   //nicht ß selber und kein y 																																	//wie. z.B. in Odyssee, hier nicht umwandeln.
                                                                                                         //(nicht y, 1  mal vorkommend, und danach ss oder ss am Anfang)
			org.apache.regexp.RE objReSs = new org.apache.regexp.RE("([b-dB-Df-hF-Hj-nJ-Np-tP-Tv-xzV-XZ0-9öÖüÜäÄ]){1}(ss)");  //nicht ß selber und kein y (wg. Odyssee) 
													//wie. z.B. in Odyssee, hier nicht umwandeln.
			//auch kein Vokal vor dem ss (a, e, i, o, u) führt zum Umwandlen.
			//wie z.B. in Ereignisse, Essen, Kassel

			//ss am anfang wird nicht umgewandelt, da links und rechts ein Buchstabe abgeschnitten wird
			//Asseln wird nicht umgewandelt, da beim betrachteten String sseln vorne ein Buchstabe fehlt.
            //(nicht y, 1  mal vorkommend, und danach ss oder ss am Anfang)
		
			
			org.apache.regexp.RE objReVowel = new org.apache.regexp.RE("[aeou]{3,}");  //es dürfen keine 3 Vokale aufeinander folgen z.B. aus "treuer" soll nicht "treür" werden. 
						
			String sReplace = StringZZZ.midBounds(sString2Parse, 1, 1);
			boolean bTest = objReVowel.match(sReplace);
			if(bTest==true){
				sReturn = sString2Parse;
				break main;
			}
			
			
		
		
		if(!sReplace.equals("")){//Merke: Ist ein Wort z.B. nur ein Buchstabe, so würde er verdoppelt beim Zusammenbauen
			sReplace = objReOe.subst(sReplace, "ö");
			sReplace = objReAe.subst(sReplace, "ä");

			//sReplace = objRe4.subst(sReplace, "ß"); /Ersetzt auch den zusaetzlichen Buchstaben VOR dem ss, da er in die RegEx mit aufgenommen worden ist.
			boolean bMatch = objReSs.match(sReplace);
			if(bMatch==true){
				sReplace = StringZZZ.replace(sReplace, "ss", "ß");
			}   
		
//			2b) Wieder zusammenbauen
			sReturn = StringZZZ.left(sString2Parse, 1) + sReplace + StringZZZ.right(sString2Parse, 1);
		}//end if sReplace!=""
		
		//DAS Ü Wird auf den ganzen String bezogen
		//sReplace = objRe3.subst(sReplace, "ü"); //Ersetzt auch den zusätzlichen Buchstaben VOR dem ue, da er in die RegEx mit aufgenommen worden ist.
		//Umzuwandeln sind nur Buchstaben in Worten, die länger als 4 Buchstaben sind
		if(sReturn.length()>=5){
			boolean bMatch = objReUe.match(sReturn);//Nicht das erste Zeichen matched!
			if(bMatch==true){
				sReturn = StringZZZ.replace(sReturn, "ue", "ü");
			}								
		}
		if(sReturn.length()>=4 && !StringZZZ.isCapitalized(sReturn)){
			boolean bMatch = objReUe1.match(sReturn);
			if(bMatch==true){
				sReturn = StringZZZ.replace(sReturn, "ue", "ü");
			}								
		}
		
		
		}///end main
		return sReturn;
	}
	
	public static String replaceCharacterGermanFromSentence(String sString2ParseIn) throws ExceptionZZZ{
		String sReturn = sString2ParseIn;
		main:{
			if(StringZZZ.isEmptyNull(sString2ParseIn)) break main;
			
			//Ersetz werden nur Worte >= 4 Buchstaben, die keinen großen Anfangsbuchstaben haben. So bleibt z.B. Suez bestehen
			String sString2Parse = sString2ParseIn.trim();
			if(sString2Parse.length() <= 3){
				sReturn = sString2Parse;
				break main;
			}
			
			//++++ Satz in Einzelworte zerlegen...
			StringTokenizer tokenSentence = new StringTokenizer(sString2Parse, " ", false);
			while(tokenSentence.hasMoreTokens()){				
				String sReplaceOrig = (String) tokenSentence.nextToken();
				String stemp = StringZZZ.replaceCharacterGerman(sReplaceOrig);
				
				//++++ ... und wieder zusammenbauen
				if(sReturn.equals("")){
					sReturn = stemp;
				}else{
					sReturn = sReturn + " " + stemp;
				}
			}//End while						
		}//end main:
		return sReturn;
	}

	public static String abbreviateDynamic(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ{
		return abbreviateDynamic_(sSource, iMaxCharactersAllowed, true);
	}
	
	public static String abbreviateDynamicLeft(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ{
		return abbreviateDynamic_(sSource, iMaxCharactersAllowed, false);
	}
	
	private static String abbreviateDynamic_(String sSource, int iMaxCharactersAllowed, boolean bRight) throws ExceptionZZZ{
		String sReturn = sSource;
		main:{
			if(StringZZZ.isEmptyNull(sSource)) break main;			
			if(iMaxCharactersAllowed >= sSource.length()){
				sReturn = sSource;
				break main;
			}
			if(iMaxCharactersAllowed<=1){
				ExceptionZZZ ez = new ExceptionZZZ("MaxCharactersAllowed to small. Must be >= 2.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String myDynamicAbbreviator=null;
			if(sSource.length()- iMaxCharactersAllowed == 1){
				myDynamicAbbreviator = new String(".");				
			}
			if(sSource.length()- iMaxCharactersAllowed == 2){
				myDynamicAbbreviator = new String("..");				
			}
			
			if(bRight) {
				if(myDynamicAbbreviator!=null){
					int iLeft = iMaxCharactersAllowed - myDynamicAbbreviator.length();
					sReturn = StringZZZ.left(sSource, iLeft);						
					sReturn = sReturn + myDynamicAbbreviator;
					break main;
				}
								
				sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
				break main;
			}else {				
				if(myDynamicAbbreviator!=null){
					int iRight = iMaxCharactersAllowed - myDynamicAbbreviator.length();
					sReturn = StringZZZ.right(sSource, iRight);						
					sReturn = myDynamicAbbreviator + sReturn;
					break main;
				}
				
				sReturn = StringZZZ.right(sSource, iMaxCharactersAllowed-3);
				sReturn = "..." + sReturn;
			}
		
		
		/* Merke, aus der Doku.
If str is less than maxWidth characters long, return it.
Else abbreviate it to (substring(str, 0, max-3) + "...").
If maxWidth is less than 4, throw an IllegalArgumentException.
In no case will it return a String of length greater than maxWidth.

StringUtils.abbreviate(null, *)      = null
StringUtils.abbreviate("", 4)        = ""
StringUtils.abbreviate("abcdefg", 6) = "abc..."
StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
StringUtils.abbreviate("abcdefg", 4) = "a..."
StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException

		 */
		
		
	}//end main:
	return sReturn;
	}
	
	public static String abbreviateStrict(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ{
		return abbreviateStrict_(sSource, iMaxCharactersAllowed,true);
	}
	
	public static String abbreviateStrictFromRight(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ{
		return abbreviateStrict_(sSource, iMaxCharactersAllowed,false);
	}
	
	private static String abbreviateStrict_(String sSource, int iMaxCharactersAllowed, boolean bFromLeft) throws ExceptionZZZ{
		String sReturn = sSource;
		main:{
			if(StringZZZ.isEmptyNull(sSource)) break main;			
			if(iMaxCharactersAllowed >= sSource.length()) break main;
			
			if(iMaxCharactersAllowed<=1){
				ExceptionZZZ ez = new ExceptionZZZ("MaxCharactersAllowed to small. Must be >= 2.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String myDynamicAbbreviator=null;
			if(iMaxCharactersAllowed==2 && sSource.length()>=3){
				myDynamicAbbreviator = new String(".");				
			}
			if(iMaxCharactersAllowed==3 && sSource.length()>=3){
				myDynamicAbbreviator = new String("..");				
			}
			
			if(bFromLeft) {
				if(myDynamicAbbreviator!=null){
					sReturn = StringZZZ.left(sSource, 1);
					sReturn = sReturn + myDynamicAbbreviator;
					break main;
				}				
				sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
				break main;
			}else {
				if(myDynamicAbbreviator!=null){
					sReturn = StringZZZ.right(sSource, 1);
					sReturn = myDynamicAbbreviator + sReturn;
					break main;
				}
				sReturn = StringZZZ.right(sSource, iMaxCharactersAllowed-3);
				sReturn = "..." + sReturn;
			}
		
		
		/* Merke, aus der Doku.
If str is less than maxWidth characters long, return it.
Else abbreviate it to (substring(str, 0, max-3) + "...").
If maxWidth is less than 4, throw an IllegalArgumentException.
In no case will it return a String of length greater than maxWidth.

StringUtils.abbreviate(null, *)      = null
StringUtils.abbreviate("", 4)        = ""
StringUtils.abbreviate("abcdefg", 6) = "abc..."
StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
StringUtils.abbreviate("abcdefg", 4) = "a..."
StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException

		 */
		
		
	}//end main:
	return sReturn;
	}

	/** Gibt ein Array aller Index-Startpostionen zurück. Das Array ist NICHT sortiert !!!
	* @param sSource, der durchsuchte String.
	* @param saString2Find, die Strings, die gesucht werden. D.h. es können mehrere sein.
	* @return
	* 
	* lindhauer; 30.06.2007 11:43:38
	 */
	public static Integer[] indexOfAll(String sSource, String[] saString2Find ){
		Integer[] intaReturn=null;
		main:{
			if(isEmpty(sSource)) break main;
			if(ArrayUtilZZZ.isNull(saString2Find)) break main;
			
			ArrayList<Integer> listaReturn = new ArrayList<Integer>();
			for(int icount=0; icount < saString2Find.length; icount ++){
				String sCurrent = sSource;
				int iIndex=-1;
				int iIndexAdded = 0;
				int iLengthOld = 0;
				do{
					iIndex = sCurrent.indexOf(saString2Find[icount]);
					if(iIndex >= 0){
						iIndexAdded += iIndex+iLengthOld;
						Integer intIndexAdded = new Integer(iIndexAdded);
						listaReturn.add(intIndexAdded);
						
						//Nun den String vorne abschneiden !!!
						iLengthOld = saString2Find[icount].length();
						sCurrent = rightback(sCurrent, iIndex+ iLengthOld);//den Index um die L�nge des zu suchenden String serweitern !!!
					}
				}while(iIndex >= 0 & isEmpty(sCurrent)==false);
			}//END for
			
			if(listaReturn.size()>=1){
				intaReturn = new Integer[listaReturn.size()];
				intaReturn = listaReturn.toArray(intaReturn);
			}
		}//END main:
		
		return intaReturn;
	}
	
	/** Haenge an den String den anderen String an, aber nur die Zeichen, die in dem vorherigen String noch fehlen!
	 * 
	 * @param sString
	 * @param sToAppend
	 * @return
	 * @author Fritz Lindhauer, 05.11.2022, 09:09:25
	 */
	public static String appendMissing(String sString, String sToAppend) {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)&&StringZZZ.isEmpty(sToAppend)) break main;
			if(StringZZZ.isEmpty(sToAppend)) break main;						
			if(StringZZZ.isEmpty(sString)) {
				sReturn = sToAppend;
				break main;
			}
			
			//entferne aus dem anzuhängenden String die Zeichen, die in dem Ausgangsstring vorhanden sind
			char[] caString = sString.toCharArray();
			String sToAppendReal = StringZZZ.stripCharacters(sToAppend, caString);
			if(!StringZZZ.isEmpty(sToAppendReal)) {
				sReturn = sString + sToAppendReal;
			}else {
				sReturn = sString;
			}
		}//end main:
		return sReturn;
	}

	/** wie indexOf, allerdings wird die Laenge des Strings daraufgerechnet.
	 *  Der index ist also der Anfang des dahinterliegenden Teils
	 *  
	 * @param sSource
	 * @param sString2Find
	 * @return
	 * @author Fritz Lindhauer, 05.05.2024, 08:53:59
	 */
	public static int indexOfFirstBehind(String sSource, String sString2Find) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp = sSource.indexOf(sString2Find);
			if(itemp==-1)break main;
			
			iReturn = itemp + sString2Find.length();
		}//end main:
		return iReturn;
	}
	
	
	public static int indexOfFirst(String sSource, String sString2Find) {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			iReturn = sSource.indexOf(sString2Find);

		}//end main:
		return iReturn;
	}


	/** int, Gibt den ersten index wert zurueck, der existiert. Falls keiner der zu suchenden Strings existiert, wird -1 zur�ckgegeben.
	* Lindhauer; 13.05.2006 09:22:14
	 * @param sSource, der String, der durchsucht wird.
	 * @param saString2Find, das Array der Strings, die gesucht werden. D.h. man kann also nach mehreren Strings suchen.
	 * @return
	 */
	public static int indexOfFirst(String sSource, String[] saString2Find){
		int iReturn=-1; //Merke: -1 wird auch zur�ckgegeben, falls der Teilstring nicht im String enthalten ist.
		main:{
				if(isEmpty(sSource))break main;
				if(ArrayUtilZZZ.isNull(saString2Find)) break main;
			
			int iEndCur=sSource.length() + 1;
			for(int icount=0; icount < saString2Find.length; icount ++){
				int iEnd = sSource.indexOf(saString2Find[icount]);
				if (iEnd >= 0){
					//Nur Strings, die auch vorkommen, "spielen mit"
					if(iEnd==0){
						//Falls man den ersten Index erreicht hat, dann ist schon vorzeitig schluss.
						iReturn = 0;
						break main;
					}else{
						if(iEnd<iEndCur)	iEndCur = iEnd;					
					}						
				}
			}//END for
			
			//Falls die Laenge von iEndCur unveraendert geblieben ist, dann wurde nix gefunden. Es wird -1 zurueckgegeben
			if(iEndCur==sSource.length()+1) break main;
			iReturn = iEndCur;
		
		}//END main:
		return iReturn;
	}




	/** Erzeugt eine ArrayList, welche die Strings, die in saPattern enthalten sind in der korrekten Reihenfolge und Anzahl wiedergibt.
	 * 
	 * sString = "eins  zwei  drei  eins  zwei  vier  acht sechzehn"
	 * saPattern = {"drei", "zwei"};
	 * 
	 * ===> {"zwei", "drei", "zwei"} als ArrayList
	 * 
	* @param sString
	* @param saPattern
	* @return
	* 
	* lindhaueradmin; 19.03.2007 12:57:39
	 */
	public static ArrayList<String> findSorted(String sString, String[] saPattern){
		ArrayList<String> listaString = new ArrayList<String>();
		main:{
			if(isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isNull(saPattern)) break main;
			
			
			//Idee: In einer Schleife auf Vorhandenheit des Strings des Pattern-Arrays pr�fen  (von links nach rechts).
			//        Der niedrigere Indexwert gewinnt (am besten auch in ein Array packen).
			//        Aus diesem Indexwert und der L�nge des jeweiligen "Pattern" Strings bekommt man dann einen Reststring, der wiederum in der Schleife auf Vorhandenheit gepr�ft werden muss.
			int[] iaPattern = new int[saPattern.length];					
			String sRemaining = new String(sString);			
								
			do{
				int iLength = sRemaining.length();			
				for(int icount = 0; icount <= saPattern.length-1;icount++){
					String stemp = saPattern[icount];
					int itemp = sRemaining.indexOf(stemp);
					if(itemp >=0){
						iaPattern[icount] = itemp;
					}else{
						iaPattern[icount] = iLength + 1; //String wurde nicht gefunden, darum wird hier der h�chstm�gliche Wert plus 1 in das auszuwertende Array geschrieben.					
					}				
				}
				
				//Nun das int - Array auswerten. Der niedrigste Wert gewinnt.
				int itemp = MathZZZ.min(iaPattern);
							
				//Falls der niedrigeste Wert kleiner ist als die L�nge des Strings, muss die Index-Position des Werts ermittelt werden.
				int iIndex=-1;
				if(itemp <= iLength){
					for(int i=0; i<=iaPattern.length-1;i++){
						if(itemp==iaPattern[i]){						
							iIndex = i;
							break;
						}
					}
				}
				
				if(iIndex >= 0){
					String sValue=saPattern[iIndex];
					listaString.add(sValue);	
					sRemaining = right(sRemaining, sRemaining.length() - (iaPattern[iIndex]+sValue.length()));
				}else{
					//Es konnte keinerlei String mehr gefunden werden
					break main;
				}
			}while(sRemaining.length()>=1);
		}//end main
		return listaString;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es werden Grossbuchstaben und Zahlen im �bergebenenString verwendet.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toUpperCaseNumberAbbreviation(String sString){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			sReturn = "";
			boolean isNumeric=false;
			for(int iIndex=0; iIndex<=sString.length()-1; iIndex++){
				String s = sString.substring(iIndex, iIndex+1);
				try{
					@SuppressWarnings("unused")
					Integer intObj = Integer.parseInt(s);
				   isNumeric = true;
				}catch(Exception e){
					isNumeric = false;
				}
				
				if(s.toUpperCase().equals(s) || isNumeric){
					sReturn+=s;
				}
			}				
		}
		return sReturn;
	}
	
	/**
	 * @param sString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.11.2022, 08:50:02
	 * 
	 * siehe auch UnicodeZZZ.toByteArray(string) und .from(byte[])
	 */
	public static String toUtf8(String sString) throws ExceptionZZZ{		
		String sReturn=sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			try {
				sReturn = new String(sString.getBytes(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				ExceptionZZZ ez = new ExceptionZZZ("UnsupportedEncodingException", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main
		return sReturn;
	}
	
	/**Merke: So einfach kann man sonst nicht nach float parsen
	 * @param sString
	 * @return
	 */
	public static float toFloat(String sString){
		float fReturn=0.0f;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			if(StringZZZ.isFloat(sString)){
				Float fltValue = new Float(sString);
				fReturn = fltValue.floatValue();
			}else{
				int iValue = Integer.parseInt(sString);	
				fReturn = (float) iValue;
			}
		}//end main:
		return fReturn;
	}
	
	public static String asHtml(String sString) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			
			if(StringZZZ.isEmpty(sString)){
				sReturn = "<html><body></body></html>";
				break main;
			}
			
			sReturn = StringZZZ.toHtml(sString);
			

			if(!StringZZZ.startsWithIgnoreCase(sString, "<html>")){
				if(!StringZZZ.startsWithIgnoreCase(sString, "<body>")) {
					sReturn = "<body>" + sReturn;					
				}
				sReturn = "<html>" + sReturn;				
			}
			
			if(!StringZZZ.endsWithIgnoreCase(sString, "</html>")){
				if(!StringZZZ.endsWithIgnoreCase(sString, "</body>")) {
					sReturn = sReturn + "</body>";
				}
				sReturn = sReturn + "</html>";
			}			
		}//end main:
		return sReturn;
	}
	
	public static String toHtml(String sString) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			sReturn = StringZZZ.toHtmlEscaped(sString);
			sReturn = StringZZZ.toHtmlCrlf(sString);
			
			//Merke: Will man die HTML - Tags noch haben, dann StringZZZ.asHtml(...)
		}
		return sReturn;
	}
	public static String toHtmlSimple(String sString){
		/*
		 You should do some replacements on the text programmatically. Here are some clues:

    All Newlines should be converted to "<br>\n" (The \n for better readability of the output).
    All CRs should be dropped (who uses DOS encoding anyway).
    All pairs of spaces should be replaced with " &nbsp;"
    Replace "<" with "&lt;"
    Replace "&" with "&amp;"
    All other characters < 128 should be left as they are.
    All other characters >= 128 should be written as "&#"+((int)myChar)+";", to make them readable in every encoding.
    To autodetect your links, you could either use a regex like "http://[^ ]+", or "www.[^ ]" and convert them like JB Nizet said. to "<a href=\""+url+"\">"+url+"</a>", but only after having done all the other replacements.

		  String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
Pattern patt = Pattern.compile(str);
Matcher matcher = patt.matcher(plain);
plain = matcher.replaceAll("<a href=\"$1\">$1</a>");
		 */
		StringBuilder builder = new StringBuilder();
	    boolean previousWasASpace = false;
	    for( char c : sString.toCharArray() ) {
	        if( c == ' ' ) {
	            if( previousWasASpace ) {
	                builder.append("&nbsp;");
	                previousWasASpace = false;
	                continue;
	            }
	            previousWasASpace = true;
	        } else {
	            previousWasASpace = false;
	        }
	        switch(c) {
	            case '<': builder.append("&lt;"); break;
	            case '>': builder.append("&gt;"); break;
	            case '&': builder.append("&amp;"); break;
	            case '"': builder.append("&quot;"); break;
	            case '\n': builder.append("<br>"); break;
	            // We need Tab support here, because we print StackTraces as HTML
	            case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
	            default:
	                if( c < 128 ) {
	                    builder.append(c);
	                } else {
	                    builder.append("&#").append((int)c).append(";");
	                }    
	        }
	    }
	    return builder.toString();
	}
	
	public static String toHtmlCrlf(String sString) throws ExceptionZZZ{
		String sReturn = StringZZZ.replace(sString, StringZZZ.crlf(), "<br>");
		return sReturn;
	}
	
	
	public static String toHtmlEscaped(String sString){
		String sReturn = StringEscapeUtils.escapeHtml(sString);
		return sReturn;
	}
	
	
	/** Merke: Integer.parseInt(...) wirft beispielsweise eine java.lang.NumberFormatException, wenn man einen float-String, (z.B. "2.0") übergibt.
	 *              Das wird hier vermieden.
	 * @param sValue
	 */
	public static int toInteger(String sValue){
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sValue)) break main;
			
			if(StringZZZ.isFloat(sValue)){
				Float fltValue = new Float(sValue);
				iReturn = fltValue.intValue();
			}else{
				iReturn = Integer.parseInt(sValue);	
			}
		}
		return iReturn;
	}
	
	public static String trim(String sString) {
		return StringZZZ.trim(sString," ");
	}
	
	public static String trim(String sString, String sStringToBeTrimmed) {
		String sReturn = StringZZZ.trimLeft(sString, sStringToBeTrimmed);
		sReturn = StringZZZ.trimRight(sString, sStringToBeTrimmed);
		return sReturn;
	}
	
	public static String trimLeft(String sString, String sStringToBeTrimmed){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			
			sReturn = StringZZZ.trimLeft(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			
		}//end main:
		return sReturn;
	}
	
	public static String trimLeft(String sString, String sStringToBeTrimmed, int iStringLengthMinRemainingIn){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			int iStringLengthMinRemaining;
			if(iStringLengthMinRemainingIn<0){
				iStringLengthMinRemaining = 0;
			}else{
				iStringLengthMinRemaining = iStringLengthMinRemainingIn;
			}
			
			int iStringIndexMinRemaining = 1 + iStringLengthMinRemaining;			
			boolean bGoon = false;			
			while(!bGoon){
				if(sReturn.startsWith(sStringToBeTrimmed) && sReturn.length()>=iStringIndexMinRemaining){
					sReturn = StringZZZ.rightback(sReturn, sStringToBeTrimmed.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	public static String trimRight(String sString, String sStringToBeTrimmed){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			
			sReturn = StringZZZ.trimRight(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			
		}//end main:
		return sReturn;
	}
	
	public static String trimRight(String sString, String sStringToBeTrimmed, int iStringLengthMinRemainingIn){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			int iStringLengthMinRemaining;
			if(iStringLengthMinRemainingIn<0){
				iStringLengthMinRemaining = 0;
			}else{
				iStringLengthMinRemaining = iStringLengthMinRemainingIn;
			}
			
			int iStringIndexMinRemaining = 1 + iStringLengthMinRemaining;			
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.endsWith(sStringToBeTrimmed) && sReturn.length()>=iStringIndexMinRemaining){
					sReturn = StringZZZ.leftback(sReturn, sStringToBeTrimmed.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	
	/* Trimme den String, schneide links und rechts jeweils das Markierungszeichen weg, trimme wieder, ...  schneide Markierungszeichen weg, usw. bis es kein passendes Paar Markierungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimMarked(String sString, String sMark){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
		
			sReturn = sString.trim();
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.startsWith(sMark) && sReturn.endsWith(sMark)){
					sReturn = StringZZZ.midBounds(sReturn, sMark.length(), sMark.length()); //Schneide die Markierungszeichen links und rechts weg
					sReturn = sReturn.trim();
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches/doppeltes Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches/doppeltes Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimAnyQuoteMarked(String sString){
		String sReturn = null;		
		main:{
			sReturn = sString;
			if(StringZZZ.isEmptyTrimmed(sString))break main;
			
			boolean bGoon = false;
			while(!bGoon) {
				String sCompare = sReturn;
				sReturn = StringZZZ.trimSingleQuoteMarked(sReturn);
				sReturn = StringZZZ.trimDoubleQuoteMarked(sReturn);
				
				if(sCompare.equals(sReturn)) bGoon=true;
			}
		}//end main:
		return sReturn;
	}

	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimSingleQuoteMarked(String sString){
		return StringZZZ.trimMarked(sString, "'");
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein doppeltes Anfuehrungszeichen weg, trimme wieder, ...  schneide doppeltes Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimDoubleQuoteMarked(String sString){
		return StringZZZ.trimMarked(sString, "\"");
	}
	
	
	
	/* Trimme den String, schneide links und rechts jeweils ein Anfuehrungszeichen weg, trimme wieder, ...  schneide Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimQuotationMarked(String sString){
		return StringZZZ.trimDoubleQuoteMarked(sString);		
	}
	
	public static String stripCharacters(String sString, char[]caToStrip) {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(CharArrayZZZ.isEmpty(caToStrip)) break main;

			char[]caString = sString.toCharArray();
			char[]caReturn = new char[(caString.length)];
			int iPositionLast=-1;
			for(char c : caString) {
					if(CharArrayZZZ.contains(caToStrip,c)) {
							//nix machen
					}else {
						iPositionLast++;
						caReturn[iPositionLast]=c;
					}
			}
			sReturn = CharArrayZZZ.toString(caReturn,iPositionLast);
		}//end main:
		return sReturn;
	}
	
	/* Anders als beim trimMarked werden hier Leerzeichen nicht getrimmt. 
	 */
	public static String stripMarked(String sString, String sMark){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;

			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.startsWith(sMark) && sReturn.endsWith(sMark)){
					sReturn = StringZZZ.midBounds(sReturn, sMark.length(), sMark.length()); //Schneide die Markierungszeichen links und rechts weg					
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anf�hrungszeichen weg, trimme wieder, ...  schneide einfaches Anf�hrungszeichen weg, usw. bis es kein passendes Paar Anf�hrungszeichen links und rechts mehr gibt.	 
	 */
	public static String stripSingleQuoteMarked(String sString){
		return StringZZZ.stripMarked(sString, "'");
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anf�hrungszeichen weg, trimme wieder, ...  schneide einfaches Anf�hrungszeichen weg, usw. bis es kein passendes Paar Anf�hrungszeichen links und rechts mehr gibt.	 
	 */
	public static String stripDoubleQuoteMarked(String sString){
		return StringZZZ.stripMarked(sString, "\"");
	}
	
	/* Anders als beim trimQuotationMarked werden hier Leerzeichen nicht getrimmt. 
	 */
	public static String stripQuotationMarked(String sString){
		return StringZZZ.stripDoubleQuoteMarked(sString);		
	}
	
	/** Entferne den String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimLeft(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sString, String sStringToBeStripped){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeStripped)) break main;
			
			boolean bGoon = false;			
			while(!bGoon){
				if(sReturn.startsWith(sStringToBeStripped) && sReturn.length()>=2){
					sReturn = StringZZZ.rightback(sReturn, sStringToBeStripped.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sString, String[] saStringsToBeStripped){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(saStringsToBeStripped==null) break main;
									
			boolean bGoon = false;
			while(!bGoon){
				bGoon = true;
				String sStringTemp = "";
				for(String sStringToBeStripped:saStringsToBeStripped){
					if(StringZZZ.startsWithIgnoreCase(sReturn, sStringToBeStripped)){
						bGoon = false;
						sStringTemp = StringZZZ.rightback(sReturn, sStringToBeStripped.length());
						sReturn = sStringTemp;
					}
				}				
			}
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparators(String sString){
		String sReturn = sString;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sString)) break main;
			
			sReturn = StringZZZ.stripFileSeparatorsLeft(sString);
			sReturn = StringZZZ.stripFileSeparatorsRight(sReturn);
			if(sReturn.equals(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)) {
				sReturn = "";
			}
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparatorsLeft(String sString){
		String sReturn = sString;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeStripped = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sReturn = StringZZZ.stripLeft(sString, saStringsToBeStripped);
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von rechts kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String sStringToBeStripped){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeStripped)) break main;
			
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.endsWith(sStringToBeStripped) && sReturn.length()>=2){
					sReturn = StringZZZ.leftback(sReturn, sStringToBeStripped.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparatorsRight(String sString){
		String sReturn = sString;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeStripped = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sReturn = StringZZZ.stripRight(sString, saStringsToBeStripped);
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von rechts kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String[] saStringsToBeStripped){
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(saStringsToBeStripped==null) break main;
									
			boolean bGoon = false;
			while(!bGoon){
				bGoon = true;
				String sStringTemp = "";
				for(String sStringToBeStripped:saStringsToBeStripped){
					if(StringZZZ.endsWithIgnoreCase(sReturn, sStringToBeStripped)){
						bGoon = false;
						sStringTemp = StringZZZ.leftback(sReturn, sStringToBeStripped.length());
						sReturn = sStringTemp;
					}
				}				
			}
		}//end main:
		return sReturn;
	}
	
}// END class