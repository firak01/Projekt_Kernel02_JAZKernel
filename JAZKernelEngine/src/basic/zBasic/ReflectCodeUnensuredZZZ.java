package basic.zBasic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.longs.LongZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormaterZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormater4ReflectCodeZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zKernel.AbstractKernelLogZZZ;

/**Hintergrund dieser Klasse ist, das die statischen Methoden in der Klasse ReflectCodeZZZ immer eine Exception werfen.
* Ich möchte diese Methoden aber z.B. in der Erstellung von Werten eines Enums verwenden oder bei Konstanten.
* Daher duerfen (wie generell bei Feld - Initialisierungen) keine checked Exceptions geworfen werden.
* 
* Lösung: Rufe die Methoden der Klasse mit den Exceptions auf, fange diese aber einfach ab.
*         Beachte hier aber, das es 1 Codezeilenaufruf mehr ist.
*         Private Methoden braucht es hier nicht.
* 
* @author Fritz Lindhauer, 19.11.2025, 07:15:09
* 
*/
public class ReflectCodeUnensuredZZZ  implements IReflectCodeZZZ, IConstantZZZ{

	private ReflectCodeUnensuredZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	//+++ Fuer den Namen der .java - Datei
	public static String getMethodCurrentFileName() {
		try {
			return ReflectCodeZZZ.getMethodCurrentFileName(1); 
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/** use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	 * @return Return the name of the routine that called getCurrentMethodName
	 */
	  public static String getMethodCurrentFileName(int iOffset) {	
		try {
			return ReflectCodeZZZ.getMethodCurrentFileName(iOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	  
	/** @return Name des JavaFiles, welche die aktuelle Methode aufgerufen hat.
	* lindhaueradmin, 27.04.2024
	*/
	  public static String getMethodCallingFileName() throws ExceptionZZZ {
		  try {
			return ReflectCodeZZZ.getMethodCallingFileName(1); //1 StacktracePostion weiter
		  } catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	  }
	  
	public static String getMethodCallingFileName(int iOffset) throws ExceptionZZZ {		
		try {
			return ReflectCodeZZZ.getMethodCallingFileName(iOffset + 1); //1 StacktracePostion weiter
		  } catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	//++++++++++++++++++++++ Für den Namen der Klasse und Methode, ohne den Dateinamen
	public static String getMethodCurrentName() throws ExceptionZZZ  {
		return getMethodCurrentName(1);//1 StacktracePostion weiter
	}
	
	/**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentName(int iStacktraceOffset) throws ExceptionZZZ  {		  
			String method = null;
			 
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				iStacktraceOffset+=1;
				
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///Siehe Artikel 'The surprisingly simple stack trace Element'");
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();								
				if(stackTrace.length>=4){
					StackTraceElement element = stackTrace[ReflectCodeUnensuredZZZ.iPOSITION_STACKTRACE_CURRENT + iStacktraceOffset];
					method = element.getMethodName();
				}
								
			}else{
				
			//Verarbeitung vor Java 1.4
			
			  ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  PrintWriter pw = new PrintWriter(baos);
			  (new Throwable()).printStackTrace(pw);
			  pw.flush();
			  String stackTrace = baos.toString();
			  pw.close();

			  StringTokenizer tok = new StringTokenizer(stackTrace, "\n");
			  String l = tok.nextToken(); // 'java.lang.Throwable'
			  String t = null;
			  l = tok.nextToken(); // 'at ...getCurrentMethodName'
			  l = tok.nextToken(); // 'at ...<caller to getCurrentRoutine>'
			  	  
			  // Parse line 2
			  tok = new StringTokenizer(l.trim(), " <(");
			  try{
			  t = tok.nextToken(); // 'at'
			  t = tok.nextToken(); // '...<caller to getCurrentRoutine>'
			  }catch(NoSuchElementException nsee){
				  t=null;
			  }
			  
			  //!!! Falls hier nix ist, dann hat sich der Aufbau ge�ndert.
			  if(t==null){
				  tok = new StringTokenizer(l.trim(), "(");
				  try{
					  t = tok.nextToken();
				  }catch(NoSuchElementException nsee){
					  t=null;
				  }
			  }
			  
			  if(t!=null){
				  String methodWithPackageName = t;
				  int iDotPos = methodWithPackageName.lastIndexOf(ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR);
				  if(iDotPos<=-1){
					  method = methodWithPackageName;
				  }else{
					  method = methodWithPackageName.substring(iDotPos+1);
				  }
			  }
			}//End if : Verarbeitung vor Java 1.4
			  return method;
	  }
	  
	  public static String getMethodCurrentNameLined() {
		  return ReflectCodeUnensuredZZZ.getMethodCurrentNameLined_(1,0);//0=StacktraceOffset, 0=iLineOffset
	  }
	  
	  public static String getMethodCurrentNameLined(int iLineOffset) {
		  return ReflectCodeUnensuredZZZ.getMethodCurrentNameLined_(1,iLineOffset);
	  }
	  
	  /**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentNameLined(int iStacktraceOffset, int iLineOffset) {		  
		  return getMethodCurrentNameLined_(iStacktraceOffset, iLineOffset);
	  }
	  
	 
	  public static String getMethodCallingName() {
		  return getMethodCallingName_(1); //1 StacktracePostion weiter
	  }
	  
	  public static String getMethodCallingName(int iStacktraceOffset) {
		  return getMethodCallingName_(iStacktraceOffset+1); //1 StacktracePostion weiter
	  }
		
	 public static String formatMethodCallingLine(int iLine) {         		 
		 //Merke20240427: Aber für die Eclipse Konosole ist ein xyz.java:iLine besser, dann ist die Codezeile anspringbar.
		 //               Aber dazu muss eh die aufrufende Methode eine Java-Datei verwenden und nicht nur den Klassennamen.		 		 
		 //return " - Line " + iLine + ReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
		 String sCommentSeparator = StringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
		 return " - Line " + iLine + sCommentSeparator;
		 
		//geht leider nicht, da wir hier den Dateinamen nicht haben. 
		//String sLine = ReflectCodeZZZ.formatFileCallingLine(iLine);
		//return " (" + sFilePath + sLine + ") ";
		//return sLine;
	 }
	 
	 public static String getFileCallingLine(String sFilePath, int iLine) {
		 String sReturn = ReflectCodeUnensuredZZZ.formatFileCallingLine(iLine);
		 sReturn = sFilePath + sReturn;
		 return sReturn;
	 }
	 
	 public static String formatFileCallingLine(int iLine) {
		 //Merke: Nach der Zeilenangabe ist # besser. Z.B der Doppelpunkt wird in Vielen Logstrings selbst benutzt.
		 //Merke20240427: Für die Eclipse Konosole ist ein xyz.java:iLine besser, dann ist die Codezeile anspringbar.
		 //               Aber dazu muss eh die aufrufende Methode eine Java-Datei verwenden und nicht nur den Klassennamen.
		 
		 //Merke:         Hinter der Zeilenummer muss ein Leerzeichen sein, sonst erkennt die Eclipse Konsole das nicht.
		 //               Das Leerzeichen muss aber in der aufrufenden Methode gesetzt werden.

		 return IReflectCodeZZZ.sPOSITION_LINENR_SEPARATOR + iLine; 		 
	 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	 public static String formatFileCallingLine(String sFilePath, int iLine) {
			//Merke1: Die Postion in der Datei muss in Klammern stehen und es darf hinter der Zeilennummer nix anderes sein.
			//        Zudem darf vor der ersten Klammer auch nix sein ausser ein Leerzeichen.
			//        Nur dann erkennt die Eclipse Konsole dies als Dateiposition in einem Java-File.
			 
			//Merke2: Das Clicken funktioniert nicht, wenn es 2x die gleiche Datei im src-Ordner gibt. 
			//        (vermutlich muesste dann der Pfad mit augegeben werden).
			String sReturn = sFilePath + ReflectCodeUnensuredZZZ.formatFileCallingLine(iLine);				
			return IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT + sReturn + IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT;
		 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	 public static String formatFileCallingLineForConsole(String sFilePath, int iLine) {
			//Merke1: Die Postion in der Datei muss in Klammern stehen und es darf hinter der Zeilennummer nix anderes sein.
			//        Zudem darf vor der ersten Klammer auch nix sein ausser ein Leerzeichen.
			//        Nur dann erkennt die Eclipse Konsole dies als Dateiposition in einem Java-File.
			 
			//Merke2: Das Clicken funktioniert nicht, wenn es 2x die gleiche Datei im src-Ordner gibt. 
			//        (vermutlich muesste dann der Pfad mit augegeben werden).
			String sReturn = getPositionCurrentInFile_(sFilePath, iLine);				
			return sReturn;
		 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	public static String formatFileCallingLineForConsoleClickable(String sFilePath, int iLine) {
		//Merke1: Die Postion in der Datei muss in Klammern stehen und es darf hinter der Zeilennummer nix anderes sein.
		//        Zudem darf vor der ersten Klammer auch nix sein ausser ein Leerzeichen.
		//        Nur dann erkennt die Eclipse Konsole dies als Dateiposition in einem Java-File.
		 
		//Merke2: Das Clicken funktioniert nicht, wenn es 2x die gleiche Datei im src-Ordner gibt. 
		//        (vermutlich muesste dann der Pfad mit augegeben werden).		
		String sReturn = ReflectCodeUnensuredZZZ.formatFileCallingLineForConsole(sFilePath, iLine);
		return " " + sReturn;
	 }
	 
	
	 /**
	 * @return Zeile im Stacktrace für den Namen der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	public static int getMethodCallingLine() {
		return getMethodCallingLine_(1);//1 Stacktracepostion weiter
	}
	
	public static int getMethodCallingLine(int iStacktraceOffset) {
		return getMethodCallingLine_(iStacktraceOffset+1);
	}
	
	public static int getMethodCurrentLine() {
		return getMethodCallingLine_(1); //1 Stacktraceposition weiter, steckt schon in "calling"
	}
	
	public static int getMethodCurrentLine(int iStacktraceOffset, int iLineOffset) {
		int iLine = -1;
	 
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			iStacktraceOffset+=1;
			
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///Siehe Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();						
			if(stackTrace.length>=4){
				int iPositionInStacktrace = ReflectCodeUnensuredZZZ.iPOSITION_STACKTRACE_CURRENT + iStacktraceOffset;
				StackTraceElement element = stackTrace[iPositionInStacktrace];
				iLine = element.getLineNumber();
				iLine = iLine + iLineOffset;
			}
							
		}else{
			
			//Verarbeitung vor Java 1.4
			ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
			throw ez;	 
			
			
		}//End if : Verarbeitung vor Java 1.4
		  return iLine;
	}
	  
	  
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getClassCallingName() {
		return getClassCallingName_(1); //1 StacktracePostion weiter
	}
	
	/**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCallingName(int iStacktraceOffset) {
		  String sReturn = null;
		  main:{
			  
			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
					final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
					if(stackTrace.length>=4){
						int iPositionInStacktrace = ReflectCodeUnensuredZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
						StackTraceElement element = stackTrace[iPositionInStacktrace];
						sReturn = element.getClassName();
					}												
				}else{
					
					//Verarbeitung vor Java 1.4
					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
					throw ez;	
				
				}//end Java Version
		  }//end main:
		  return sReturn;
	  }
	
		
	 /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCurrentName() {
		  return ReflectCodeUnensuredZZZ.getClassCallingName_(0);//0, weil Stacktraceposition weiter steckt schon in "calling"
	  }
	
	 /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCurrentName(int iStacktracePositionOffset) {
		  return ReflectCodeUnensuredZZZ.getClassCallingName_(iStacktracePositionOffset);//1 Stacktraceposition weiter steckt schon in "calling"
	  }
	
	public static String getPositionCurrentInFile() {
		return getPositionCurrentInFile(1);
	}
	
	
	
	public static String  getPositionCurrentInFile(int iLevel) {
		String sReturn = null;
		main:{	  
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gemaess Artikel 'The surprisingly simple stack trace Element'");
				int iLevelUsed = iLevel+1;
				
				int iLine = ReflectCodeUnensuredZZZ.getMethodCallingLine(iLevelUsed);
				String sFile = ReflectCodeUnensuredZZZ.getMethodCallingFileName(iLevelUsed);
			
				sReturn = getPositionCurrentInFile_(sFile, iLine);					  	
			}else{
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
				throw ez;	   
			}//end Java Version
	  }//end main:
	  return sReturn;
		
	}
		
	public static String  getPositionCurrentInFile(String sFile, int iLine) {
		return getPositionCurrentInFile_(sFile, iLine);
	}
	
	
	//#######################################################
	public static String getPositionCurrentInFileForConsoleClickable() {
		return getPositionCurrentInFileForConsoleClickable(1);
	}
	
	public static String  getPositionCurrentInFileForConsoleClickable(int iLevel) {
		String sReturn = null;
		main:{	  
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gemaess Artikel 'The surprisingly simple stack trace Element'");
				int iLevelUsed = iLevel+1;
				
				int iLine = ReflectCodeUnensuredZZZ.getMethodCallingLine(iLevelUsed);
				String sFile = ReflectCodeUnensuredZZZ.getMethodCallingFileName(iLevelUsed);
			
				sReturn = getPositionInFileForConsoleClickable_(sFile, iLine);					  	
			}else{
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
				throw ez;	   
			}//end Java Version
	  }//end main:
	  return sReturn;
		
	}
	
	public static String  getPositionCurrentInFileForConsoleClickable(String sFile, int iLine) {
		return getPositionInFileForConsoleClickable_(sFile, iLine);
	}
	
	public static String  getPositionInFileForConsoleClickable(String sFile, int iLine) {
		return getPositionInFileForConsoleClickable_(sFile, iLine);
	}
	
		
	public static String  getPositionCurrent() {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(null, 1);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++
	public static String  getPositionCalling() {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(null, 2);
	}
	
	//wie getPositionCallingPlus(iLevelPlus)
	public static String  getPositionCalling(int iLevelPlus) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(null, 2+iLevelPlus);
	}
	
	//wie getPositionCalling(iLevelPlus)
	public static String  getPositionCallingPlus(int iLevelPlus) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(null, 2+iLevelPlus);
	}
	
	public static String getPosition(int iLevel) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(null, iLevel+1);
	}
	
	public static String getPositionCurrentSeparated(int iLevel) {
		return getPositionCurrentSeparated_(null, iLevel+1);
	}
		
	public static String getPositionCurrentSeparated(Object obj, int iLevel) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentSeparated_(obj.getClass(), iLevel+1);
	}
	public static String getPositionCurrentSeparated(Class classObj, int iLevel) {
		return getPositionCurrentSeparated_(classObj, iLevel+1);
	}
	
		
	
	//####################
	
	/** Alte Version, vor dem Entwickeln der Formater - Klassen. Hier fehlt der Dateiname.
	 *  Trotzdem schon mit einem "Bündigmacher" für mögliche Folgekommentare.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 16.11.2025, 21:30:35
	 */
	public static String  getPositionCurrentSimple() {
		return getPositionCurrentSimple_(null, 1);
	}
	
	
	//##########################
	public static String  getPositionCallingSimple() {
		return ReflectCodeUnensuredZZZ.getPositionCallingSimple_(2);
	}
	
	//#####################################################################################
	//####################
	public static String  getPositionCurrentXml() {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXml_(null, 1);
	}
	
	//####################
	public static String  getPositionCallingXml() {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXml_(null, 2);
	}
	
	//wie getPositionCallingXmlPlus(iLevelPlus)
	public static String  getPositionCallingXml(int iLevelPlus) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXml_(null, 2+iLevelPlus);
	}
	
	//wie getPositionCallingXml(iLevelPlus)
	public static String  getPositionCallingXmlPlus(int iLevelPlus) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXml_(null, 2+iLevelPlus);
	}
	
	public static String getPositionXml(int iLevel) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXml_(null, iLevel+1);
	}
	
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String getPositionCurrentXml(int iLevel) {
		return getPositionCurrentXml_(null, iLevel+1);
	}
	
	//#################################################################
	//####################
	public static String  getPositionCurrentXmlFormated() {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXmlFormated_(null, 1);
	}
	
	public static String getPositionXmlFormated(int iLevel) {
		return ReflectCodeUnensuredZZZ.getPositionCurrentXmlFormated_(null, iLevel+1);
	}
		
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String getPositionCurrentXmlFormated(int iLevel) {
		return getPositionCurrentXmlFormated_(null, iLevel+1);
	}
	
	
		  
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   	Falls eine Class übergeben wird, kommt java.lang raus.
  		Das ist nicht gewünscht. Es sollte ein Objekt der Klasse instantiert werden 
		und davon der Pfad geholt werden
		z.B. String sPackagePath = ReflectCodeZZZ.getPackagePath(DebugWriterHtmlByXsltZZZ.class);
		
		Darum eine neue Methode mit Class als Argument.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getPackagePath(Object obj) {
		  String sReturn = null;
		  main:{
			  if(obj == null) break main;
			  
			  Class objClass = obj.getClass();
			  sReturn = ReflectCodeUnensuredZZZ.getPackagePath(objClass);
		  
		  }//END Main:
		return sReturn;			
	  }
	
	 
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   *  REMARK: THIS METHOD THROWS NO EXCEPTION. THEN IT CAN BE USED FOR THE DECLARATION OF STATIC VARIABLES
	   *  
	   	Falls eine Class übergeben wird, kommt java.lang raus.
		Das ist nicht gewünscht. Es sollte ein Objekt der Klasse instantiert werden 
		und davon der Pfad geholt werden
		z.B. String sPackagePath = ReflectCodeZZZ.getPackagePath(DebugWriterHtmlByXsltZZZ.class);
		
		Darum eine neue Methode mit Class als Argument.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getPackagePathForConstant(Object obj) {
		  String sReturn = null;
		  main:{
			  if(obj == null) break main;
			  
			  Class objClass = obj.getClass();
			  sReturn = ReflectCodeUnensuredZZZ.getPackagePathForConstant(objClass);
		  
		  }//END Main:
		return sReturn;			
	  }
	
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 */
	public static String getPackagePath(Class objClass) {
		  String sReturn = null;
		  main:{			 
				  if(objClass == null) break main;
				  
				  Package objPackage  = objClass.getPackage();
				  if(objPackage!=null) {
					  sReturn = objPackage.getName(); //Merke: Wenn Klassen in einem JAR-File zusammengefasst werden, dann haben sie ein leeres Package
					  sReturn =StringZZZ.replace(sReturn,  ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR, File.separator);
				  }else{
					  sReturn = ".";
				  }				  
		  }//END Main:
		return sReturn;			
	  }
	
	 /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   *  REMARK: THIS METHOD THROWS NO EXCEPTION. THEN IT CAN BE USED FOR THE DECLARATION OF STATIC VARIABLES 
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ  
	 */
	public static String getPackagePathForConstant(Class objClass) {
		  String sReturn = null;
		  main:{			 
				  if(objClass == null) break main;
				  
				  Package objPackage  = objClass.getPackage();
				  if(objPackage!=null) {
					  sReturn = objPackage.getName(); //Merke: Wenn Klassen in einem JAR-File zusammengefasst werden, dann haben sie ein leeres Package
					  sReturn =StringZZZ.replace(sReturn,  ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR, File.separator);
				  }else{
					  sReturn = ".";
				  }				  
		  }//END Main:
		return sReturn;			
	  }
	
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 */
	public static String getPackagePathByReflection(Class objClass) {
		  String sReturn = null;
		  main:{
			  try {
				  if(objClass == null) break main;
				
				//Merke: Fuer einen anderen Konstruktor, z.B. int,int,double
				//final Myclass v = MyClass.class.getConstructor(
				//	   int.class, int.class, double.class).newInstance(_xval1,_xval2,_pval);
				  				  
				  Object obj = objClass.getConstructor().newInstance();				
				  sReturn = ReflectCodeUnensuredZZZ.getPackagePath(obj);				  
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		  }//END Main:
		return sReturn;			
	  }
	
	
	
	/**  Gibt den reinen Klassennamen (also ohne . oder / ) zur�ck.
	 * Falls "this" in einer statischen Methode aufgerufen wird, so gibt es die gleiche Methode noch mit einem erwarteten class-Object als Parameter.
	 * 
	 * Merke: obj.getClass().getName() gibt auch den Pfad zur�ck. 
	 * 
	* @param obj
	* @return
	* 
	* lindhauer; 25.02.2008 10:40:28
	 */
	public static String getClassNameOnly(Object obj) {
		 String sReturn = null;
		  main:{
			  check:{
				  if(obj == null) break main;
			  }//END check:
		  
		  Class objClass = obj.getClass();
		  String sNameTotal = objClass.getName();
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR);
		  while(objToken.hasMoreTokens()){
			  sReturn = objToken.nextToken();
		  }
		  		  
		  }//END Main:
		return sReturn;			
	}
	
	/** Gibt den reinen Klassennamen (also ohne . oder / ) zurück.
	 *   Diese Methode ist in statischen methoden zu verwenden, wenn kein "this" erlaubt ist.
	* @param classOfObject
	* @return
	* 
	* lindhauer; 26.02.2008 09:39:50
	 */
	public static String getClassNameOnly(Class classOfObject) {
		 String sReturn = null;
		  main:{
			  check:{
				  if(classOfObject == null) break main;
			  }//END check:
		  
		  String sNameTotal = classOfObject.getName();
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR);
		  while(objToken.hasMoreTokens()){
			  sReturn = objToken.nextToken();
		  }
		  		  
		  }//END Main:
		return sReturn;			
	}
	
	public static String getClassFileName(Object obj) {
		String sReturn = null;
		main:{
			Class classObj = obj.getClass();
			sReturn = ReflectCodeUnensuredZZZ.getClassFileName(classObj);
		}//end main:
		return sReturn;
	}
	
	public static String getClassFileName(Class classObj) {
		return classObj.getSimpleName() + ".java";		
	}
	
	
	public static String getClassFilePath(Object obj) {
		String sReturn = null;
		main:{
			Class classObj = obj.getClass();
			sReturn = ReflectCodeUnensuredZZZ.getClassFilePath(classObj);
		}//end main:
		return sReturn;
	}
	
	public static String getClassFilePath(Class classObj) {
		String sReturn = null;
		main:{			
			if(classObj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeUnensuredZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			String sFileName = ReflectCodeUnensuredZZZ.getClassFileName(classObj);	
			
			Package objPackage = classObj.getPackage();
			if(objPackage==null) {
				String sLog = "ReflectCodeZZZ.getClassFilePath(...): Package not available by classloader";
				ReflectCodeUnensuredZZZ.printStackTrace(sLog);
				
				sReturn = sFileName;
				break main;
			}
			
			String sPackage = classObj.getPackage().getName();
			String sDirectory = StringZZZ.replace(sPackage,".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
			
			 //NEIN: ENDLOSSCHLEIFE weil darin ebenfalls geloggt wird.
            //String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
            //ALSO: Einfacher halten.
			String sFilePathTotal = sDirectory + StringZZZ.char2String(IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR) + sFileName;
			
			sReturn = sFilePathTotal;
		}//end main:
		return sReturn;
	}
	
	/**
	 * Liefert den Namen einer aufrufenden Methode oder deren Aufrufer
	 * @param callerID - gibt an, wieviele Aufrufe oberhalb der aufrufenden Methode
	 * ausgegeben werden sollen. 0 liefert den Namen der aufrufenden Methode, 1 den
	 * Namen des uebergeordneten Aufrufers usw.
	 * @return - Name der Aufrufenden Methode
	 */
	public static String getCaller(int callerID) {
		int stack_base = callerID+4;
		// +1 to ignore "Throwable" line, +1 to ignore this method
		StringWriter sw = new StringWriter();
		(new Throwable()).printStackTrace(new PrintWriter(sw));
		String trace = sw.toString();
		int linestart = -1;
		for (int i = 0; i < stack_base; i++) {
			linestart = trace.indexOf("\n", linestart + 1);
		}
		return trace.substring(linestart + 5, trace.indexOf("(", linestart + 5));
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * @return
	 * @author Fritz Lindhauer, 12.06.2019, 10:08:16
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack() {
		return getCallingStack(null);
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * @param sClassnameFilterRegEx : Schränke die Array-Einträge ein, auf Klassen, die diesen RegEx Ausdruck haben.
	 * @return
	 * @author Fritz Lindhauer, 16.06.2019, 07:42:03
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack(String sRegEx) {
		String[] saReturn=null;
		main:{
		ArrayList<String>listasTemp = new ArrayList<String>();
		String stemp; boolean btemp;
		//org.apache.regexp.RE objReFilter = null;
		Matcher matcher = null;
		Pattern pattern = null;
			  
		//#####################DEBUG 						
		//Filtere diese ReflectCodeZZZ-Klasse selber aus.
		String sClassNameRegexed = StringZZZ.replace(ReflectCodeUnensuredZZZ.class.getName(), ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR, "\\" + ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR);
		sClassNameRegexed = StringZZZ.stripRight(sClassNameRegexed, "ZZZ");
		
		//Das Finden des passenden RegEx Ausdrucks war etws komplizierter.....
				//Folgendes funktioniert: Es wird nur Fahrrad gefunden
				/* Anwendung des "negiven lookaheads"
				Matcher matcherTest = null;
				Pattern patternTest = null;
				String[] saTest = {"Auto", "Fahrrad", "Aua","Ausgibig"};
				String sFilterRegExTest = "^(?!Au)[A-Z][a-z]";
				try{
					  patternTest = Pattern.compile(sFilterRegExTest);
				  }catch(Exception e){
					  e.printStackTrace();
				  }
				 for(String sElement :saTest){
					 matcherTest = patternTest.matcher(sElement);
					 btemp=matcherTest.find();
					
					if(btemp) {
						System.out.println("GEFUNDEN");
					}else{
						System.out.println("nix");
					}
				 }
				 */
				
		//https://stackoverflow.com/questions/8610743/how-to-negate-any-regular-expression-in-java
		/*
		You need to add anchors. The original regex (minus the unneeded parentheses):
		/.{0,4}
		...matches a string that contains a slash followed by zero to four more characters. But, because you're using the matches() method it's automatically anchored, as if it were really:
		^/.{0,4}$
		To achieve the inverse of that, you can't rely on automatic anchoring; you have to make at least the end anchor explicit within the lookahead. You also have to "pad" the regex with a .* because matches() requires the regex to consume the whole string:
		(?!/.{0,4}$).*
		But I recommend that you explicitly anchor the whole regex, like so:
		^(?!/.{0,4}$).*$
		It does no harm, and it makes your intention perfectly clear, especially to people who learned regexes from other flavors like Perl or JavaScript. The automatic anchoring of the matches() method is highly unusual.
		*/
				
				
				//Versuche mit "negativem Voraussschauen"		
				//https://stackoverflow.com/questions/7317043/regex-not-operator
				//     \((?!2001)\)
				
				//https://www.regextester.com/15
				//		/^((?!badword).)*$/gm
				// und den Test Strings, dabei hilft /gm die Zeilenumbrücke aufzulösen:
				/*
				 badword
				 test
				 one two
				 abadwords
				 three
				*/
				
				//Negative Lookahead: Filtere vorher alle Klassen mit ReflectCode im Namen aus. 
				//sRegEx = "^((?!ReflectCode).)*"+ sRegEx; //hard coded variante, mit nur dem Klassennamen
				//sRegEx = "^((?!"+sClassNameRegexed+").)*"+ sRegEx;//dynamische Variante mit dem Packagenamen und dem Pfad der tatsächlich verwendeten Klasse.
				String sClassNameFilterRegEx = null;
				if(StringZZZ.isEmpty(sRegEx)){
					sClassNameFilterRegEx = "^((?!"+sClassNameRegexed+").)[A-Za-z\\.]";
				}else{
					sClassNameFilterRegEx = "^((?!"+sClassNameRegexed+").)*"+ sRegEx;//dynamische Variante mit dem Packagenamen und dem Pfad der tatsächlich verwendeten Klasse.
				}
			  			  			  
			//######################			 
			  //Negative Lookahead: Filtere vorher alle Klassen mit ReflectCode im Namen aus. 			  
			  if(!StringZZZ.isEmpty(sClassNameFilterRegEx)){ //einmal den RegEx Ausdruck erzeugen und nicht jedesmal in der Schleife.
				  //objReFilter = new org.apache.regexp.RE(sClassnameFilterRegEx);
				  try{
					  pattern = Pattern.compile(sClassNameFilterRegEx);
				  }catch(Exception e){
					  System.out.println("Error for RegEx: '" + sClassNameFilterRegEx + "'");
					  e.printStackTrace();
				  }
			  }
			  				
			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");

					final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
					int iposition=0;
					for(StackTraceElement element : stackTrace){
						iposition++;
						if(iposition>=ReflectCodeUnensuredZZZ.iPOSITION_STACKTRACE_CURRENT){	//Man willl den Stactrace erst ab der aktuellen Methode und nicht den oben durchgeführten "Thread-Aufruf";																			
							//if(objReFilter==null){
							if(pattern==null){
								stemp = ReflectCodeUnensuredZZZ.getClassMethodString(element);									
								listasTemp.add(stemp);
							}else{
								stemp = element.getClassName();
																
								//btemp = objReFilter.match(stemp);
								matcher = pattern.matcher(stemp);
								btemp=matcher.find();
								
								if(btemp) {
									stemp = ReflectCodeUnensuredZZZ.getClassMethodString(element);									
									listasTemp.add(stemp);
								}
							}
						}//end if
					}//end for
										
				}else{
					
					//Verarbeitung vor Java 1.4
					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
					throw ez;	  
				}//end Java Version
			  saReturn = ArrayListUtilZZZ.toStringArray( listasTemp);
		}//end main
		return saReturn;
	}
	

	
	public static String getClassMethodString() {
		String sReturn = "";
		main:{
			String sMethod=ReflectCodeUnensuredZZZ.getMethodCurrentName();//+1 Stacktrace steckt schon in in "calling", wir brauchen hier aber die auf-aufrufende Position
			String sClass = ReflectCodeUnensuredZZZ.getClassCurrentName();//+1 Stacktrace steckt schon in in "calling", wir brauchen hier aber die auf-aufrufende Position
			sReturn = sClass;
			if(!StringZZZ.isEmpty(sMethod)){
				sReturn = sReturn + ReflectCodeUnensuredZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
			}
		}
		return sReturn;
	}
	
	public static String getClassMethodString(StackTraceElement objStack) {
		String sReturn = null;
		main:{
			if(objStack==null){
				//Hier mal vollkommen ungenerisch die Exception bauen. Hintergrund: Wenn diese Methode in den Reflect-Klassen verwendet wird, fürchte ich sonst eine Eindlosschleife.
				ExceptionZZZ ez = new ExceptionZZZ("StackTraceElement fehlt'", iERROR_PARAMETER_MISSING, ReflectCodeKernelZZZ.class, "getClassMethodString(StackTraceElement objStack)");
				throw ez;	
			}
			
			String sMethod = objStack.getMethodName();
			String sClass = objStack.getClassName();
			sReturn = sClass + ReflectCodeUnensuredZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
		}//end main
		return sReturn;
	}
	
	public static String getClassMethodCallingString() {
		return ReflectCodeUnensuredZZZ.getClassMethodCallingString(1);//1 Stacktraceposition weiter 
	}
	public static String getClassMethodCallingString(int iOffset) {
		String sReturn = null;
		main:{
			String sClass = ReflectCodeUnensuredZZZ.getClassCallingName(iOffset+1);//1 Stacktraceposition weiter
			String sMethod = ReflectCodeUnensuredZZZ.getMethodCallingName(iOffset+1);//1 Stacktraceposition weiter
			sReturn = sClass + ReflectCodeUnensuredZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
		}
		return sReturn;
	}
	
	
	/**Hohle den String, der aktuellen Klasse
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:22:51
	 */
	public static String getClassMethodCurrentString() {
		return ReflectCodeUnensuredZZZ.getClassMethodCallingString();//1 Stacktraceposition weiter steckt schon in "calling"
	}
	
	
	
	
	
	/**
	 * Ermittelt den Namen der aufrufenden Funktion.
	 */
	public static String lastCaller(String className) {
		String lastCaller = "";
		for (int i = 1; true; i++) {
			lastCaller = getCaller(i);
			if (lastCaller == null) {
				return "keine aufrufende Methode";
			}
			if (lastCaller.indexOf(className + ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR) < 0) {
				return lastCaller;
			}
		}
	}
	
	/**
	 * Ermittelt die Tiefe des Aufrufstacks.
	 */
	public static int callStackSize() {
        StringWriter sw = new StringWriter();
        (new Throwable()).printStackTrace(new PrintWriter(sw));
        String trace = sw.toString();
        int size = 0;
        for (int nl = 0; nl != -1; nl = trace.indexOf("\n", nl + 1)) {
        	size++;
        }
        return size;
    }
	
	public static StackTraceElement[] getStackTrace() {		
		return getStackTrace__(1);
	}
	
	public static StackTraceElement[] getStackTrace(int iStartingLevelIn) {
		int iStartingLevel = iStartingLevelIn + 1;		
		return getStackTrace__(iStartingLevel);
	}
	
	//+++++++++++++++++
	public static StackTraceElement[] getStackTraceCalling() { 		
		return getStackTraceCalling__(1);
	}
	
	public static StackTraceElement[] getStackTraceCalling(int iStartingLevelIn) {
		int iStartingLevel = iStartingLevelIn + 1;		
		return getStackTraceCalling__(iStartingLevel);
	}
	
	public static StackTraceElement[] getStackTrace(String sRegEx) {
		StackTraceElement[] objaReturn = null;
		main:{			
			ArrayList<StackTraceElement>listaTemp = new ArrayList<StackTraceElement>();
			String stemp; boolean btemp;
	
			Matcher matcher = null;
			Pattern pattern = null;
				  
			//#####################DEBUG 						
			//Filtere diese ReflectCodeZZZ-Klasse selber aus.
			String sClassNameRegexed = StringZZZ.replace(ReflectCodeUnensuredZZZ.class.getName(), ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR, "\\" + ReflectCodeUnensuredZZZ.sPACKAGE_SEPERATOR);
			sClassNameRegexed = StringZZZ.stripRight(sClassNameRegexed, "ZZZ");
			
			
			String sClassNameFilterRegEx = null;
			if(StringZZZ.isEmpty(sRegEx)){
				sClassNameFilterRegEx = "^((?!"+sClassNameRegexed+").)[A-Za-z\\.]";
			}else{
				sClassNameFilterRegEx = "^((?!"+sClassNameRegexed+").)*"+ sRegEx;//dynamische Variante mit dem Packagenamen und dem Pfad der tatsächlich verwendeten Klasse.
			}
				  			  			  
			//######################			 
			 //Negative Lookahead: Filtere vorher alle Klassen mit ReflectCode im Namen aus. 			  
			 if(!StringZZZ.isEmpty(sClassNameFilterRegEx)){ //einmal den RegEx Ausdruck erzeugen und nicht jedesmal in der Schleife.
				  try{
					  pattern = Pattern.compile(sClassNameFilterRegEx);
				  }catch(Exception e){
					  System.out.println("Error for RegEx: '" + sClassNameFilterRegEx + "'");
					  e.printStackTrace();
				  }
			  }
				  				
			 if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				int iposition=0;
				for(StackTraceElement element : stackTrace){
					iposition++;
					if(iposition>=ReflectCodeUnensuredZZZ.iPOSITION_STACKTRACE_CURRENT){	//Man willl den Stactrace erst ab der aktuellen Methode und nicht den oben durchgeführten "Thread-Aufruf";																			
						if(pattern==null){													
							listaTemp.add(element);
						}else{
							stemp = element.getClassName();																				
							matcher = pattern.matcher(stemp);
							btemp=matcher.find();									
							if(btemp) {															
								listaTemp.add(element);
							}
						}
					}//end if
				}//end for									
			}else{		
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeUnensuredZZZ.class, ReflectCodeUnensuredZZZ.getMethodCurrentName());
				throw ez;
				
			}//end Java Version
			objaReturn = listaTemp.toArray(new StackTraceElement[listaTemp.size()]);
		}//end main:
		return objaReturn;
	}
	
	
	public static String getStackTraceString() {
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(1);
		return ReflectCodeUnensuredZZZ.stackTraceToString(stacktrace);
	}
	
	public static String[] getStackTraceStringArray() {
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(1);
		return ReflectCodeUnensuredZZZ.stackTraceToStringArray(stacktrace);
	}
	
	public static String[] getStackTraceStringArray(int iLevelIn) {
		int iLevel = iLevelIn + 1;
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(iLevel);
		return ReflectCodeUnensuredZZZ.stackTraceToStringArray(stacktrace);
	}
	
	public static String getStackTraceCallingString() {
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(2); //+1wg. nicht nur den direkten Calling String, sondern noch einen weiteren.
		return ReflectCodeUnensuredZZZ.stackTraceToString(stacktrace);
	}
	
	public static String[] getStackTraceCallingStringArray() {
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(2);
		return ReflectCodeUnensuredZZZ.stackTraceToStringArray(stacktrace);
	}
	
	public static String[] getStackTraceCallingStringArray(int iLevelIn) {
		int iLevel = 2 + iLevelIn;
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(iLevel);
		return ReflectCodeUnensuredZZZ.stackTraceToStringArray(stacktrace);
	}
	
	public static String getStackTraceCallingString(int iLevelIn) {
		int iLevel = iLevelIn + 1;
		StackTraceElement[]stacktrace=ReflectCodeUnensuredZZZ.getStackTraceCalling__(iLevel); //+1wg. nicht nur den direkten Calling String, sondern noch einen weiteren.
		return ReflectCodeUnensuredZZZ.stackTraceToString(stacktrace);
	}
	
	public static String stackTraceToString(StackTraceElement[] stackTrace) {
	    StringBuilder sb = new StringBuilder();
	    for (StackTraceElement e : stackTrace) {
	        sb.append("\tat ").append(e.toString()).append(System.lineSeparator());
	    }
	    return sb.toString();
	}
	
	public static String[] stackTraceToStringArray(StackTraceElement[] stacktrace) {
		String[]saReturn=null;
		main:{
			ArrayList<StackTraceElement>listasTemp = new ArrayList<StackTraceElement>();
			for(StackTraceElement element : stacktrace){
				listasTemp.add(element);				
			}//end for
			
			saReturn = ArrayListUtilZZZ.toStringArray( listasTemp);
		}//end main
		return saReturn;
	}
	
	public static void printStackTrace(StackTraceElement[] stackTrace) {
		if(stackTrace==null) {
			ExceptionZZZ ez = new ExceptionZZZ("StackTraceElement[]", iERROR_PARAMETER_MISSING, ReflectCodeUnensuredZZZ.class.getName(), ReflectCodeUnensuredZZZ.getMethodCurrentName());
			throw ez;
		}
		
		String sValue = ReflectCodeUnensuredZZZ.stackTraceToString(stackTrace);
		System.out.println(sValue);
	}
	
	public static void printStackTrace() {
		StackTraceElement[] stackTrace = ReflectCodeUnensuredZZZ.getStackTrace__(3);
		ReflectCodeUnensuredZZZ.printStackTrace(stackTrace);
	}
	
	public static void printStackTrace(String sMessage) {
		String[]saStacktrace = ReflectCodeUnensuredZZZ.getStackTraceCallingStringArray();
		String[]saReturn=StringArrayZZZ.prepend(sMessage, saStacktrace);
		String sReturn = StringArrayZZZ.implode(saReturn, StringZZZ.crlf()+"\tat ");
		System.out.println(sReturn);
	}
	
	
	/**Jetzt hat man die Postionsangaben mit Tags versehen, will sie dann aber wieder raus haben.
	 * Das ist z.B. im LogStringFormatManagerZZZ der Fall, der die LogStrings ohne "stoerende" Tags ausgeben will. 
	 * 
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.11.2025, 18:39:28
	 */
	public static String removePositionCurrentTagPartsFrom(String sXml) {
		String sReturn = sXml;
		main:{
			if(StringZZZ.isEmpty(sXml)) break main;
			
			//Entferne nun die in ReflectCodeZZZ.getPositionCurrent() -sinnvollerweise - hinzugenommenen XML Tags			
			ITagTypeZZZ objTagTypeDate = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.DATE);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeDate, "");
			
			ITagTypeZZZ objTagTypeThreadId = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeThreadId, "");

			ITagTypeZZZ objTagTypeClassName = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeClassName, "");
			
			ITagTypeZZZ objTagTypeLine = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.LINENUMBER);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeLine, "");
						
			ITagTypeZZZ objTagTypeFile = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeFile, "");
								
			ITagTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.METHOD);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeMethod, "");
			
			ITagTypeZZZ objTagTypePosition = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypePosition, "");
						
			ITagTypeZZZ objTagTypePositionCurrent = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypePositionCurrent, "");						
		}
		return sReturn;
	}
}
