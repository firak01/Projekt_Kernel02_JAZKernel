/*
 * Created on 24.10.2004
 * Eine kleine Hilfsklasse f�r die Behandlung von Strings.
 */
package basic.zBasic.util.datatype.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
 
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
	
		
	/** Pr�ft die Zeichen des Strings gegen die Zeichen des �bergebenen Pattern
	 *   Zur Steuerung dient iFlag:
	 *   = -1  => der String enth�lt nur Zeichen, die im Pattern  enthalten sind
	 *   = 0  => der String enth�lt keine Zeichen, die im Pattern enthalten sind.
	 *   = 1  => das �bergebene Pattern wird als RegEx angesehen und auch so behandelt
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
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			//Merke: sString.contains(CharSequence) gibt es erst seit Java 1.5
			//CharSequence sequence = sMatch.subSequence(1, sString.length());
			//if(sString.contains(sequence)  .......
			
			String sSub;
			int iMatchLength=sMatch.length();
			int iProof = sString.length()-iMatchLength;
			for(int icount=0; icount <= iProof; icount++){
				sSub = sString.substring(icount, iMatchLength+icount);
				if(sSub.equals(sMatch)){
					bReturn = true;
					break main;
				}
			}					
		} //end main:		
		return bReturn;
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
		String sReturn=null;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
				if(iPos<=-1)break main;
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
		String sReturn=null;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
				if(iPos<=-1)break main;
			}
		int iLength = sString.length();
		int iBeginn = iLength-iPos;
		
		if(iBeginn>iLength || iBeginn<0 ){
			sReturn = sString;		
		}else{		
			sReturn = sString.substring(iBeginn);
		}
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
		String sReturn = null;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
				if(iPos<=-1)break main;
				if(iLength<=0){
					sReturn = "";
					break main;
				}
				if(iPos > sString.length()){
					sReturn = "";
					break main;
				}
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
		String sReturn=null;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
			}
		int iIndex = sString.indexOf(sToFind);
		if(iIndex<= -1) break main;
		if(iIndex-1<= -1) {
			sReturn = new String("");
		}else{
			sReturn = sString.substring(0, iIndex);
		}
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
		String sReturn=null;
		main:{
			if (sString==null) break main;
			if(sString.equals("")) break main;
			int iStart = 0;
			if(iPosition < 0) {
				iStart = 0;
			}else{ 
				iStart =  iPosition ;  //Weil wir mit rightback den Wert holen
			}
			String sStart = StringZZZ.rightback(sString, iStart); //Hole den String rechts von der angegebenen Position
			
			int iIndex = sStart.indexOf(sToFind);
			if(iIndex<= -1) break main;
			if(iIndex-1<= -1) {
				sReturn = new String("");
			}else{
				sReturn = sStart.substring(0, iIndex);
			}
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
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) break main;
			
			int iPosition = sString.lastIndexOf(sToFind);
			if (iPosition == -1) break main;
		
			sReturn = sString.substring(0, iPosition);				
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
		String sReturn=null;
		main:{
			check:{
				if (sString==null) break main;
				if(sString.equals("")) break main;
			}
		int iIndex = sString.lastIndexOf(sToFind);
		if(iIndex<= -1) break main;
		
		//die Länge des Strings aufaddieren
		iIndex = iIndex + sToFind.length();
				
		sReturn = sString.substring(iIndex);

		}//END main:
		return sReturn;
	}
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @return String
	 */
	public static String right(String sString, String sToFind, boolean bIgnoreCase){
		String sReturn=null;
		main:{
				if(!bIgnoreCase){
					sReturn = StringZZZ.right(sString, sToFind);
				}else{
					
					check:{
						if (sString==null) break main;
						if(sString.equals("")) break main;
					}
				
					String sStringLCase = sString.toLowerCase();
					String sToFindLCase = sToFind.toLowerCase();
					
					int iIndex = sStringLCase.lastIndexOf(sToFindLCase);
					if(iIndex<= -1) break main;
				
					//die L�nge des Strings aufaddieren
					iIndex = iIndex + sToFind.length();
						
					sReturn = sString.substring(iIndex);				
				}
		
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
			if(saDelimiter==null) break main;
			if(saDelimiter.length==0) break main;
			
			//Eine ArrayList aus den Delimitern machen
			ArrayList listaDelim = StringArrayZZZ.toArrayList(saDelimiter);
			
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
			
			ArrayList listaStringFound = new ArrayList();//StringArrayZZZ.toArrayList(saTemp);
			for(int icount=0; icount <= saTemp.length-1; icount++){
				String sStringTemp = saTemp[icount];				
				//-1 korrekt ? 
				String[] saDelimTemp = new String[listaDelim.size()-1];
				saDelimTemp = (String[]) listaDelim.toArray(saDelimTemp);
								
				String[] saTemp2 = StringZZZ.explode(sStringTemp, saDelimTemp);
				ArrayList listaTemp = StringArrayZZZ.toArrayList(saTemp2);
				
				//Nun das Ergebnis zu dem bisherigen hinzuf�gen
				listaStringFound = ArrayListZZZ.join(listaStringFound, listaTemp, false);
			}		
			saReturn = new String[listaStringFound.size()-1];
			saReturn = (String[]) listaStringFound.toArray(saReturn);
		}//end main
		return saReturn;
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zur�ck. Links, Mitte, Rechts. Falls die Trenner zur�ckgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector vecMidFirst(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
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
			
			String sLeft = StringZZZ.left(sStringToParse, sLeftSep);
			if(sLeft==null) sLeft="";
			/*
			if(StringZZZ.isEmpty(sLeft))
				{
				vecReturn.add(sStringToParse);
				vecReturn.add("");
				vecReturn.add("");
				break main;
				}
				*/
			
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length());
			/*
			if(StringZZZ.isEmpty(sRemainingTagged)){
				vecReturn.add(sStringToParse);
				vecReturn.add("");
				vecReturn.add("");
				break main;
			}
			*/
			
			String sExpressionTagged = StringZZZ.left(sRemainingTagged, sRightSep);
			if(StringZZZ.isEmpty(sExpressionTagged)){
				vecReturn.add(sStringToParse);
				vecReturn.add("");
				vecReturn.add("");
				break main;
			}
			
			
			//nun gibt es einen Ausdruck
			sExpressionTagged = sExpressionTagged + sRightSep;
			
			String sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length());
			if(sRight==null) sRight = "";
			
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.add(sLeft);
			
			if(bReturnSeparators ==true){
				vecReturn.add(sExpressionTagged);
			}else{
				String sExpression = StringZZZ.right(sExpressionTagged, sExpressionTagged.length()-sLeftSep.length());
				sExpression = StringZZZ.left(sExpression, sExpression.length()-sRightSep.length());
				vecReturn.add(sExpression);
			}
			vecReturn.add(sRight);
		}
		return vecReturn;
	}
	
	/** String, concatenate the string the number of times.
	 * e.g. .repeat("xyz", 2) will return "xyzxyz".
	* 0823; 28.05.2006 10:53:01
	 * @param sString
	 * @param iTimes
	 * @return String
	 */
	public static String repeat(String sString, int iTimes){
		String sReturn = "";
		main:{
			check:{
				if(sString==null) break main;
				if(iTimes <= 0) break main;
				
				sReturn = sString;
				if(sString.equals("")) break main;						
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
		String sReturn = null;
		main:{
			check:{
				if(sString==null) break main;
				if(sDelimiter==null) break main;
				if(lPosition <= 0) break main;
				if(sString.equals("")||sDelimiter.equals("")){ 
					sReturn = "";
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
	
	public static boolean isBlank(String sString){
		return StringUtils.isBlank(sString);
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
	
	
	/**    Checks if the String contains only unicode digits.
	 * 	Uses Jakarta commons.lang.
	* @return boolean
	* @param sString
	* lindhaueradmin; 24.10.2006 09:16:35
	 */
	public static boolean isNumeric(String sString){
		return StringUtils.isNumeric(sString);
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
	public static String toCamelCase(String s, String sDelimiter){
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(s)) break main;
			if(StringZZZ.isEmpty(sDelimiter)){
				sReturn = s;
				break main;
			}
			
			//+++++++++++
		   String[] parts = s.split(sDelimiter);
		   
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
	public static String toCamelCase(String s){
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(s)) break main;
					
			//+++++++++++
		  		   
		   for (int icount=0; icount <= s.length()-2; icount++){
			   String cur = s.substring(icount,icount+1);
			   String part = s.substring(icount+1,icount+2); //der n�chste teilstring
			   if(StringZZZ.isCapitalized(part)){
				   //Wenn der Folgebuchstabe ein Großbuchstabe ist, dann den aktuellen Buchstaben zum Kleinbuchstaben umwandeln
				   sReturn = sReturn + cur.toLowerCase();
			   }else{
				   //ansonsten den Wert beibehalten
				   sReturn = sReturn + cur;
			   }
		   }
		   
		   //Den letzten Wert �bernehmen.
		   sReturn = sReturn + s.substring(s.length()-1, s.length() );
		   
	}//endmain:
		   return sReturn;
	}

		public static String toProperCase(String s) {
			String sReturn = "";
			main:{
				if(StringZZZ.isEmpty(s)) break main;
				sReturn = s.substring(0, 1).toUpperCase() +  s.substring(1).toLowerCase();
			}
		    return sReturn;
		}
		
		public static String toShorten(String s, int iShortenMethodType, int iOffset) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(null==s) break main;
			
				if(StringZZZ.isEmpty(s)) {
					sReturn = new String("");
					break main;
				}
								
				//Ersetz werden nur Worte >= 3 Buchstaben mehr als das OFFSET
				if(s.length() <= 3 + iOffset){
					sReturn = s;
					break main;
				}
				
				String sOffset = s.substring(0,iOffset);
				String sToParse = s.substring(iOffset);
				
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
				
											
//				String sReplace = StringZZZ.midBounds(sString2Parse, 1, 1);
//				boolean bTest = objReVowel.match(sReplace);
//				if(bTest==true){
//					sReturn = sString2Parse;
//					break main;
//				}
				
				
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
		public static InputStream toInputStream(String s){
			InputStream objReturn=null;;
			try {
				objReturn = new ByteArrayInputStream(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
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
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(iPosition >= sString.length()) break main;
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
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) break main;
			
			int iPosition = sString.indexOf(sToFind);
			if (iPosition == -1) break main;
			
			sReturn = sString.substring(iPosition+ sToFind.length(), sString.length());
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
	public static String midLeftRight(String sString, String sLeft, String sRight){
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight)){
				sReturn = sString;
				break main;
			}
			
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.left(sString, sRight);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft);
				break main;
			}
			
			sReturn = StringZZZ.rightback(sString, sLeft);
			sReturn = StringZZZ.left(sReturn, sRight);
		
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

			//sReplace = objRe4.subst(sReplace, "ß"); /Ersetzt auch den zus�tzlichen Buchstaben VOR dem ss, da er in die RegEx mit aufgenommen worden ist.
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
		String sReturn = "";
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
		String sReturn = "";
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
						
			if(myDynamicAbbreviator!=null){
				int iLeft = iMaxCharactersAllowed - myDynamicAbbreviator.length();
				sReturn = StringZZZ.left(sSource, iLeft);						
				sReturn = sReturn + myDynamicAbbreviator;
				break main;
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
			sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
			
		}//end main:
		return sReturn;
	}
	
	public static String abbreviateStrict(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ{
		String sReturn = "";
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
			if(iMaxCharactersAllowed==2 && sSource.length()>=3){
				myDynamicAbbreviator = new String(".");				
			}
			if(iMaxCharactersAllowed==3 && sSource.length()>=3){
				myDynamicAbbreviator = new String("..");				
			}
						
			if(myDynamicAbbreviator!=null){
				sReturn = StringZZZ.left(sSource, 1);
				sReturn = sReturn + myDynamicAbbreviator;
				break main;
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
			sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
			
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
	public static Integer[] allIndexOf(String sSource, String[] saString2Find ){
		ArrayList listaReturn = new ArrayList();
		main:{
			if(isEmpty(sSource)) break main;
			if(saString2Find==null)break main;
			if(saString2Find.length<=0) break main;
			
			for(int icount=0; icount < saString2Find.length; icount ++){
				String sCurrent = sSource;
				int iIndex=-1;
				int iIndexAdded = 0;
				int iLengthOld = 0;
				do{
					//iIndex = StringZZZ.firstIndexOf(sCurrent, saString2Find);
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
		}//END main:
		
		if(listaReturn.size()>=1){
			Integer[] intaReturn = new Integer[listaReturn.size()];
			intaReturn = (Integer[]) listaReturn.toArray(intaReturn);
			return intaReturn;
		}else{
			return null;
		}
	}




	/** int, Gibt den ersten index wert zur�ck, der existiert. Falls keiner der zu suchenden Strings existiert, wird -1 zur�ckgegeben.
	* Lindhauer; 13.05.2006 09:22:14
	 * @param sSource, der String, der durchsucht wird.
	 * @param saString2Find, das Array der Strings, die gesucht werden. D.h. man kann also nach mehreren Strings suchen.
	 * @return
	 */
	public static int firstIndexOf(String sSource, String[] saString2Find){
		int iReturn=-1; //Merke: -1 wird auch zur�ckgegeben, falls der Teilstring nicht im String enthalten ist.
		main:{
				if(isEmpty(sSource))break main;
				if(saString2Find==null)break main;
				if(saString2Find.length<=0) break main;
			
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
		
		//Falls die L�nge von iEndCur unver�ndert geblieben ist, dann wurde nix gefunden. Es wird -1 zur�ckgegeben
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
	public static ArrayList findSorted(String sString, String[] saPattern){
		ArrayList listaString = new ArrayList();
		main:{
			if(isEmpty(sString)) break main;
			if(saPattern==null) break main;
			if(saPattern.length==0) break main;
			
			
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
	
	/** 'Heuristische' L�sung einen 'sprechenden Key' / eine Abk�rzung zu generieren.
	 * Es werden Gro�buchstaben und Zahlen im �bergebenenString verwendet.
	 * @author Fritz Lindhauer
	 *
	 * @param name
	 * @return
	 */
	public static String toUpperCaseNumberAbbreviation(String name){
		String key = new String("");
		main:{
			if(name==null || name.equals(""))break main;
			
			boolean isNumeric=false;
			for(int iIndex=0; iIndex<=name.length()-1; iIndex++){
				String s = name.substring(iIndex, iIndex+1);
				try{
					@SuppressWarnings("unused")
					Integer intObj = Integer.parseInt(s);
				   isNumeric = true;
				}catch(Exception e){
					isNumeric = false;
				}
				
				if(s.toUpperCase().equals(s) || isNumeric){
					key+=s;
				}
			}				
		}
		return key;
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein Anf�hrungszeichen weg, trimme wieder, ...  schneide Anf�hrungszeichen weg, usw. bis es kein passendes Paar Anf�hrungszeichen links und rechts mehr gibt.
	 * 
	 */
	public static String trimQuotationMarked(String sString){
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
		
			sReturn = sString.trim();
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.startsWith("\"") && sReturn.endsWith("\"")){
					sReturn = StringZZZ.midBounds(sReturn, 1, 1); //Schneide die Anf�hrungszeichen links und rechts weg
					sReturn = sReturn.trim();
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
}// END class