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
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormaterZZZ;
import basic.zBasic.util.log.LogStringFormatManagerXmlZZZ;
import basic.zBasic.util.log.LogStringFormatManagerZZZ;
import basic.zBasic.util.log.LogStringFormater4ReflectCodeZZZ;
import basic.zBasic.util.log.LogStringFormaterUtilZZZ;
import basic.zBasic.util.log.LogStringFormaterZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class ReflectCodeZZZ  implements IReflectCodeZZZ, IConstantZZZ{

	//+++ Fuer den Namen der .java - Datei
	public static String getMethodCurrentFileName() throws ExceptionZZZ {
		return getMethodCurrentFileName(1);
	}
	
	/** use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	 * @return Return the name of the routine that called getCurrentMethodName
	 */
	  public static String getMethodCurrentFileName(int iOffset) throws ExceptionZZZ {		  
			String method = null;
			 
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///Siehe Artikel 'The surprisingly simple stack trace Element'");
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();								
				if(stackTrace.length>=4){
					StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iOffset];
					method = element.getFileName();
				}
								
			}else{
				
				//Verarbeitung vor Java 1.4
				method = getMethodCurrentName(iOffset);
			}//End if : Verarbeitung vor Java 1.4
			return method;
	  }
	  
	/** @return Name des JavaFiles, welche die aktuelle Methode aufgerufen hat.
	* lindhaueradmin, 27.04.2024
	*/
	  public static String getMethodCallingFileName() throws ExceptionZZZ {
		  return getMethodCallingFileName_(1); //1 StacktracePostion weiter
	  }
	  
	public static String getMethodCallingFileName(int iOffset) throws ExceptionZZZ {
		return getMethodCallingFileName_(iOffset+1);
	}
	
	private static String getMethodCallingFileName_(int iStacktraceOffset) throws ExceptionZZZ {
		String method = null;
				
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();			
			if(stackTrace.length>=4){					
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
				StackTraceElement element = stackTrace[iPositionInStacktrace];
				method = element.getFileName();
			}
							
		}else{
			
		//Verarbeitung vor Java 1.4
			ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		  
		}//end if java version
		  return method;
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
					StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iStacktraceOffset];
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
				  int iDotPos = methodWithPackageName.lastIndexOf(ReflectCodeZZZ.sPACKAGE_SEPERATOR);
				  if(iDotPos<=-1){
					  method = methodWithPackageName;
				  }else{
					  method = methodWithPackageName.substring(iDotPos+1);
				  }
			  }
			}//End if : Verarbeitung vor Java 1.4
			  return method;
	  }
	  
	  public static String getMethodCurrentNameLined() throws ExceptionZZZ {
		  return ReflectCodeZZZ.getMethodCurrentNameLined_(1,0);//0=StacktraceOffset, 0=iLineOffset
	  }
	  
	  public static String getMethodCurrentNameLined(int iLineOffset) throws ExceptionZZZ {
		  return ReflectCodeZZZ.getMethodCurrentNameLined_(1,iLineOffset);
	  }
	  
	  /**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentNameLined(int iStacktraceOffset, int iLineOffset) throws ExceptionZZZ {		  
		  return getMethodCurrentNameLined_(iStacktraceOffset, iLineOffset);
	  }
	  
	  private static String getMethodCurrentNameLined_(int iStacktraceOffset, int iLineOffset) throws ExceptionZZZ {		  
			String sReturn = null;
			main:{								
				if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					iStacktraceOffset+=1; //Halt wieder und noch eine Zeile tiefer.
					
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					//int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iOffset;
					sReturn = ReflectCodeZZZ.getMethodCurrentName(iStacktraceOffset);
					
					int iLine = ReflectCodeZZZ.getMethodCurrentLine(iStacktraceOffset, iLineOffset); //Berechne die gewünschte Zeile					
					sReturn +=ReflectCodeZZZ.formatMethodCallingLine(iLine); //Berechne den String  für die Zeilenausgabe. Hier kommt ggfs. der KommentarSeparator hinzu															
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
					  int iDotPos = methodWithPackageName.lastIndexOf(ReflectCodeZZZ.sPACKAGE_SEPERATOR);
					  if(iDotPos<=-1){
						  sReturn = methodWithPackageName;
					  }else{
						  sReturn = methodWithPackageName.substring(iDotPos+1);
					  }
				  }				 				  
				}//End if : Verarbeitung vor Java 1.4
				
				//Abschliessenden Trenner für mögliche Folgekommentare, falls nicht schon vorhanden
				if(!StringZZZ.endsWith(sReturn,  IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR)) {
					 sReturn = sReturn + IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
			 	}
								
				//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
			    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
				//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
				
				IStringJustifierZZZ objStringJustifier = SeparatorMessageStringJustifierZZZ.getInstance();
				sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);				
			}//end main:
			return sReturn;
	  }
  
	  public static String getMethodCallingName() throws ExceptionZZZ  {
		  return getMethodCallingName_(1); //1 StacktracePostion weiter
	  }
	  
	  public static String getMethodCallingName(int iStacktraceOffset) throws ExceptionZZZ  {
		  return getMethodCallingName_(iStacktraceOffset+1); //1 StacktracePostion weiter
	  }
	 /**
	 * @return Name der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	private static String getMethodCallingName_(int iStacktraceOffset) {
		String method = null;
		
		
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			if(stackTrace.length>=4){				
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
				StackTraceElement element = stackTrace[iPositionInStacktrace];
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
		  
		  //FGL 20130723: Die Methode VOR der mit .getMethodCurrentName() zur�ckgelieferten Methode.
		  l = tok.nextToken(); // 'at ...<caller to ..... >'
		  
		  // Parse line 3
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
			  int iDotPos = methodWithPackageName.lastIndexOf(ReflectCodeZZZ.sPACKAGE_SEPERATOR);
			  if(iDotPos<=-1){
				  method = methodWithPackageName;
			  }else{
				  method = methodWithPackageName.substring(iDotPos+1);
			  }
		  }
		}//end if java version
		  return method;
	  }
	
	
	
	 public static String formatMethodCallingLine(int iLine) throws ExceptionZZZ {         		 
		 //Merke20240427: Aber für die Eclipse Konosole ist ein xyz.java:iLine besser, dann ist die Codezeile anspringbar.
		 //               Aber dazu muss eh die aufrufende Methode eine Java-Datei verwenden und nicht nur den Klassennamen.		 		 
		 return " - Line " + iLine + ReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
		 
		//geht leider nicht, da wir hier den Dateinamen nicht haben. 
		//String sLine = ReflectCodeZZZ.formatFileCallingLine(iLine);
		//return " (" + sFilePath + sLine + ") ";
		//return sLine;
	 }
	 
	 public static String formatFileCallingLine(int iLine) throws ExceptionZZZ {
		 //Merke: Nach der Zeilenangabe ist # besser. Z.B der Doppelpunkt wird in Vielen Logstrings selbst benutzt.
		 //Merke20240427: Für die Eclipse Konosole ist ein xyz.java:iLine besser, dann ist die Codezeile anspringbar.
		 //               Aber dazu muss eh die aufrufende Methode eine Java-Datei verwenden und nicht nur den Klassennamen.
		 
		 //Merke:         Hinter der Zeilenummer muss ein Leerzeichen sein, sonst erkennt die Eclipse Konsole das nicht.
		 //               Das Leerzeichen muss aber in der aufrufenden Methode gesetzt werden.

		 return IReflectCodeZZZ.sPOSITION_LINENR_SEPARATOR + iLine; 		 
	 }
	 
	 public static String formatFileCallingLineForConsoleClickable(String sFilePath, int iLine) throws ExceptionZZZ {
		//Merke1: Die Postion in der Datei muss in Klammern stehen und es darf hinter der Zeilennummer nix anderes sein.
		//        Zudem darf vor der ersten Klammer auch nix sein ausser ein Leerzeichen.
		//        Nur dann erkennt die Eclipse Konsole dies als Dateiposition in einem Java-File.
		 
		//Merke2: Das Clicken funktioniert nicht, wenn es 2x die gleiche Datei im src-Ordner gibt. 
		//        (vermutlich muesste dann der Pfad mit augegeben werden).
		String sLine = ReflectCodeZZZ.formatFileCallingLine(iLine);
		return " (" + sFilePath + sLine + ") ";
	 }
	 
	
	 /**
	 * @return Zeile im Stacktrace für den Namen der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	public static int getMethodCallingLine() throws ExceptionZZZ {
		return getMethodCallingLine_(1);//1 Stacktracepostion weiter
	}
	
	public static int getMethodCallingLine(int iStacktraceOffset) throws ExceptionZZZ {
		return getMethodCallingLine_(iStacktraceOffset+1);
	}
	
	private static int getMethodCallingLine_(int iStacktraceOffset) throws ExceptionZZZ {
		int iLine = -1;
					
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();			
			if(stackTrace.length>=4){					
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
				StackTraceElement element = stackTrace[iPositionInStacktrace];
				iLine = element.getLineNumber();
			}
							
		}else{
			
		//Verarbeitung vor Java 1.4
			ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		  
		}//end if java version
		  return iLine;
	}
	
	public static int getMethodCurrentLine() throws ExceptionZZZ {
		return getMethodCallingLine_(1); //1 Stacktraceposition weiter, steckt schon in "calling"
	}
	
	public static int getMethodCurrentLine(int iStacktraceOffset, int iLineOffset) throws ExceptionZZZ {
		int iLine = -1;
	 
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			iStacktraceOffset+=1;
			
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///Siehe Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();						
			if(stackTrace.length>=4){
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iStacktraceOffset;
				StackTraceElement element = stackTrace[iPositionInStacktrace];
				iLine = element.getLineNumber();
				iLine = iLine + iLineOffset;
			}
							
		}else{
			
			//Verarbeitung vor Java 1.4
			ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	 
			
			
		}//End if : Verarbeitung vor Java 1.4
		  return iLine;
	}
	  
	  
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getClassCallingName() throws ExceptionZZZ {
		return getClassCallingName_(1); //1 StacktracePostion weiter
	}
	
	/**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCallingName(int iStacktraceOffset) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
			  
			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
					final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
					if(stackTrace.length>=4){
						int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
						StackTraceElement element = stackTrace[iPositionInStacktrace];
						sReturn = element.getClassName();
					}												
				}else{
					
					//Verarbeitung vor Java 1.4
					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
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
	private static String getClassCallingName_(int iStacktraceOffset) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
		  	if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				iStacktraceOffset+=1;
				  
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				//System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				if(stackTrace.length>=4){
					int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iStacktraceOffset;
					StackTraceElement element = stackTrace[iPositionInStacktrace];
					sReturn = element.getClassName();
				}												
			}else{
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
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
	public static String getClassCurrentName() throws ExceptionZZZ {
		  return ReflectCodeZZZ.getClassCallingName_(0);//0, weil Stacktraceposition weiter steckt schon in "calling"
	  }
	
	  /**
		 * @return Name (inkl. Package) der aktuellen Klasse
		 * lindhaueradmin, 23.07.2013
		 * @throws ExceptionZZZ 
		 */
		public static String getClassCurrentName(int iStacktracePositionOffset) throws ExceptionZZZ {
			  return ReflectCodeZZZ.getClassCallingName_(iStacktracePositionOffset);//1 Stacktraceposition weiter steckt schon in "calling"
		  }

//		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
//	public static String  getPositionCurrentInObject(String sObjectWithMethod, int iLine) throws ExceptionZZZ{
//		String sReturn = null;
//		  main:{
//			  
//			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
//					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
//					///System.out.println("HIER WEITERARBEITEN, gemaes Artikel 'The surprisingly simple stack trace Element'");
//				  	String sLine = ReflectCodeZZZ.formatMethodCallingLine(iLine );
//				  	sReturn =  sObjectWithMethod + sLine;
//				  	
//				}else{
//					
//					//Verarbeitung vor Java 1.4
//					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;	   
//				}//end Java Version
//		  }//end main:
//		  return sReturn;
//	}
		
	public static String  getPositionCurrentInFile(int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		main:{	  
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gemaess Artikel 'The surprisingly simple stack trace Element'");
				int iLevelUsed = iLevel+1;
				
				int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed);
				String sFile = ReflectCodeZZZ.getMethodCallingFileName(iLevelUsed);
			
				sReturn = getPositionCurrentInFile_(sFile, iLine);					  	
			}else{
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	   
			}//end Java Version
	  }//end main:
	  return sReturn;
		
	}
		
	public static String  getPositionCurrentInFile(String sFile, int iLine) throws ExceptionZZZ {
		return getPositionCurrentInFile_(sFile, iLine);
	}
	
	private static String getPositionCurrentInFile_(String sFile, int iLine) throws ExceptionZZZ {
		String sReturn = null;
		main:{	  
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gemaess Artikel 'The surprisingly simple stack trace Element'");
			   sReturn = ReflectCodeZZZ.formatFileCallingLineForConsoleClickable(sFile, iLine);					  	
			}else{
				
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	   
			}//end Java Version
	  }//end main:
	  return sReturn;
	}
		
	public static String  getPositionCurrent() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentSeparated_(null, 1);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++
	public static String  getPositionCalling() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentSeparated_(null, 2);
	}
	
	public static String  getPositionCallingPlus(int iLevelPlus) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentSeparated_(null, 2+iLevelPlus);
	}
	
	public static String getPosition(int iLevel) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentSeparated_(null, iLevel+1);
	}
	
	public static String getPositionCurrentSeparated(int iLevel) throws ExceptionZZZ {
		return getPositionCurrentSeparated_(null, iLevel+1);
	}
		
	public static String getPositionCurrentSeparated(Object obj, int iLevel) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentSeparated_(obj.getClass(), iLevel+1);
	}
	public static String getPositionCurrentSeparated(Class classObj, int iLevel) throws ExceptionZZZ {
		return getPositionCurrentSeparated_(classObj, iLevel+1);
	}
	
	private static String getPositionCurrentSeparated_(Class classObj, int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//Wichtig:
			//Rufe die Methoden zur "Positionsbestimmung" hier in der obersten Funktion auf.
			//in den Funtionen darunter muesste ja alles wieder um 2 Ebenen tiefer definiert werden.
			//Das gilt sowohl für die Zeile als auch für den Dateinamen oder die Methode.
			int iLevelUsed = iLevel+1;
			
			//Merke1: Das reine, aktuelle Objekt kann man auch ueber die Formatierungsanweisung irgendwann in den String einbauen.
			//        Nur die Zeilennummer muss AN DIESER STELLE (!) so errechnet werden.
			//Merke2: Zusaetzliche Separatoren werden im LOGSTRINGFORMAT definiert.
			int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed); 
			String sLine = new Integer(iLine).toString();
			String sFile = ReflectCodeZZZ.getMethodCallingFileName(iLevelUsed);
			String sMethod = ReflectCodeZZZ.getMethodCallingName(iLevelUsed); 

			//TODOGOON20240503: Irgendwie eine ENUM anbieten welche Variante man gerne haette... file oder object zentriert.
			//a) Variante mit dem Dateinamen
			String sPositionInFile = getPositionCurrentInFile(sFile, iLine);
			
			//b) Variante mit Objektname und dahinter iLine
			//String sObjectWithMethod = ReflectCodeZZZ.getClassCallingName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + sMethod;
			//String sPositionInObject =  getPositionCurrentInObject(sObjectWithMethod, iLine);

			//Mit LogString-Klasse
//			String[]saParts = new String[3];
//			saParts[0] = sMethod;
//			saParts[1] = sPositionInFile;
//			saParts[2] = sPOSITION_MESSAGE_SEPARATOR; //Den Trenner zum Kommentar hier schon einbauen. Im Formater wird dann daran die Bündigkeit per justifier gemacht.
			
//			ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
//			sReturn = LogStringFormatManagerZZZ.getInstance().compute(objFormater, classObj, saParts);

			
			//Erweitere um Separatoren
			LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString = new LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String>();
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_HASHMAP, sMethod);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING_BY_HASHMAP, ReflectCodeZZZ.sPOSITION_FILE_SEPARATOR + sFile);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILELINE_STRING_BY_HASHMAP, ReflectCodeZZZ.sPOSITION_LINENR_SEPARATOR + sLine);			
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_HASHMAP, ReflectCodeZZZ.sPOSITION_IN_FILE_SEPARATOR + sPositionInFile);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING, "");
						
			
			//Erzeuge hier den String als NICHT - XML Variante. 
			//ACHTUNG: Die Tags aus dieser Variante koennen dann von dem LogStringFormatManagerZZZ (oder auch wieder vom LogStringFormatManagerXmlZZZ)
			//         NICHT in ein Format gebracht werden, bei dem die Reihenfolge veraendert wurde
			ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
			sReturn = LogStringFormatManagerZZZ.getInstance().computeJustified(objFormater, hmLogString);
			
			//20251128; Der MessageSeparator ist nun eine eigene Formatanweisung
			//Abschliessenden Trenner für Folgekommentare
			//sReturn = sReturn + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			
			//IStringJustifierZZZ objStringJustifier = StringJustifierZZZ.getInstance();
			//sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);
		}//end main:
		return sReturn;
	}
	
	
	//####################
	
	/** Alte Version, vor dem Entwickeln der Formater - Klassen. Hier fehlt der Dateiname.
	 *  Trotzdem schon mit einem "Bündigmacher" für mögliche Folgekommentare.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 16.11.2025, 21:30:35
	 */
	public static String  getPositionCurrentSimple() throws ExceptionZZZ {
		return getPositionCurrentSimple_(null, 1);
	}
	
	private static String  getPositionCurrentSimple_(Class classObj, int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		  main:{
			  
				//Wichtig:
				//Rufe die Methoden zur "Positionsbestimmung" hier in der obersten Funktion auf.
				//in den Funtionen darunter muesste ja alles wieder um 1 Ebenen tiefer definiert werden.
				//Das gilt sowohl für die Zeile als auch für den Dateinamen oder die Methode.
				int iLevelUsed = iLevel+1;
			
			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");
                   
				    //Das "simple" ist, das hier die Position so einfach ohne FORMATANWEISUNG zusammengebaut wird.
				  	int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed);
				  	String sLine = ReflectCodeZZZ.formatMethodCallingLine(iLine );//enthält möglicheweise schon den Kommentartrenner
					sReturn = ReflectCodeZZZ.getClassCallingName(iLevelUsed) + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + ReflectCodeZZZ.getMethodCallingName(iLevelUsed)  + sLine;
											
					//Abschliessenden Trenner für mögliche Folgekommentare, falls nicht schon vorhanden
					if(!StringZZZ.endsWith(sReturn,  IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR)) {
						 sReturn = sReturn + IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
				 	}
					
					//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
				    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
					//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
					
					//IStringJustifierZZZ objStringJustifier = StringJustifierZZZ.getInstance();
					//sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);
				}else{
					
						//Verarbeitung vor Java 1.4
					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	   
				}//end Java Version
		  }//end main:
		  return sReturn;
	}
	
	//##########################
	public static String  getPositionCallingSimple() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCallingSimple_(2);
	}
	
	private static String getPositionCallingSimple_(int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		  main:{
			 if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				int iLevelUsed = iLevel+1;
				 
				//Das "simple" ist, das hier die Position so einfach ohne FORMATANWEISUNG zusammengebaut wird.
				int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed);
				String sLine = ReflectCodeZZZ.formatMethodCallingLine(iLine); //enthaelt moeglicherweis schon den Trenner für Folgekommentare
				sReturn = ReflectCodeZZZ.getClassCallingName(iLevelUsed) + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + ReflectCodeZZZ.getMethodCallingName(iLevelUsed)  + sLine;
										
				//Abschliessenden Trenner für mögliche Folgekommentare, falls nicht schon vorhanden
				if(!StringZZZ.endsWith(sReturn,  IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR)) {
					 sReturn = sReturn + IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
			 	}
			 }else{
					
				//Verarbeitung vor Java 1.4
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	   
			}//end Java Version
		}//end main:
		return sReturn;
	}
	
	//#####################################################################################
	//####################
	public static String  getPositionCurrentXml() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXml_(null, 1);
	}
	
	//####################
	public static String  getPositionCallingXml() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXml_(null, 2);
	}
	
	public static String  getPositionCallingXmlPlus(int iLevelPlus) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXml_(null, 2+iLevelPlus);
	}
	
	public static String getPositionXml(int iLevel) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXml_(null, iLevel+1);
	}
	
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String getPositionCurrentXml(int iLevel) throws ExceptionZZZ {
		return getPositionCurrentXml_(null, iLevel+1);
	}
	
	private static String getPositionCurrentXml_(Class objClass, int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//Wichtig:
			//Rufe die Methoden zur "Positionsbestimmung" hier in der obersten Funktion auf.
			//in den Funtionen darunter muesste ja alles wieder um 2 Ebene tiefer definiert werden.
			//Das gilt sowohl für die Zeile als auch für den Dateinamen oder die Methode.
			int iLevelUsed = iLevel+1;
			
			//FGL20251118: 
			//Formatanweisungen hier zu verwenden ist uebertrieben. (Extra Code zur Ansicht in getPositionCurrentXmlFormated() )
			//Hier wird der LogStringFormatManagerXml verwendet um diesen String zu errechnen.
			//Dabei ist der String der aktuellen Position eh nur für andere Log-Formatierungen die Quelle für Daten.
			//Also, einfach nur die Tags ausrechnen und zusammenpacken:
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			String sClassname = ReflectCodeZZZ.getClassCallingName(iLevelUsed);
			ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME, sClassname);
			String sClassnameTag = objTagClassname.getElementString();
			
			//Merke: Das reine, aktuelle Objekt kann man auch ueber die Formatierungsanweisung irgendwann in den String einbauen.
			//       Nur die Zeilennummer, etc. muss AN DIESER STELLE (!) so errechnet werden.			
			int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed);
			ITagByTypeZZZ objTagLine = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.LINENUMBER, iLine);
			String sLineTag = objTagLine.getElementString();
			
			String sFile = ReflectCodeZZZ.getMethodCallingFileName(iLevelUsed);
			ITagByTypeZZZ objTagFile = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME, sFile);
			String sFileTag = objTagFile.getElementString();
			
			String sMethod = ReflectCodeZZZ.getMethodCallingName(iLevelUsed);
			ITagByTypeZZZ objTagMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.METHOD, sMethod);
			String sMethodTag = objTagMethod.getElementString();
			
			//TODOGOON20240503: Irgendwie eine ENUM anbieten welche Form man gerne haette... file oder object zentriert.
			//a) Variante mit ,,, abc.java:iLine --- Merke: Damit wird die Position in der Eclipse Konsole clickbar.
			String sPositionInFile = getPositionCurrentInFile(sFile, iLine);
			ITagByTypeZZZ objTagPositionInFile = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE, sPositionInFile);
			String sPositionInFileTag = objTagPositionInFile.getElementString();
			
			//b) Variante mit Objektname und dahinter iLine
			//String sObjectWithMethod = ReflectCodeZZZ.getClassCallingName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + sMethod;
			//String sPositionInObject =  getPositionCurrentInObject(sObjectWithMethod, iLine);
			
			sReturn = sClassnameTag + sMethodTag + sFileTag + sLineTag + sPositionInFileTag; 
					
			//umgib den Tag mit dem <positioncurrent> Tag
			ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
			sReturn = objTagPositionCurrent.getElementString();	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Noch weitere Informationen voranstellen (ausserhalb des positioncurrent-Tags
			long lngThreadId = Thread.currentThread().getId();     
			String sThreadId = LongZZZ.longToString(lngThreadId);
			ITagByTypeZZZ objTagThreadId = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID, sThreadId);
			String sThreadIdTag = objTagThreadId.getElementString();
				
			GregorianCalendar d = new GregorianCalendar();
	        Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	        Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	        Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	        Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	        Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE));
	        String sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	               + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();		
	        ITagByTypeZZZ objTagDate = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.DATE, sDate);
	 		String sDateTag = objTagDate.getElementString();
	 							
			String sReturnPre = sThreadIdTag + sDateTag;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//20251128; Der MessageSeparator ist nun eine eigene Formatanweisung
			//Abschliessenden Trenner für mögliche Folgekommentare
			//ABER: NICHT BEIM XML-FORMAT!!!!
			//sReturn = sReturnPre + sReturn + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
			
//WICHTIG: DEN XML BASIERTEN STRING NICHT(!!!) BUENDIG MACHEN. DA DIE TAGS DIE GRENZE GAAAANZ WEIT NACH RECHTS SCHIEBEN
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//			
//			IStringJustifierZZZ objStringJustifier = StringJustifierZZZ.getInstance();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);
			
			sReturn = sReturnPre + sReturn;
		}//end main:
		return sReturn;
	}
	
	//#################################################################
	//####################
	public static String  getPositionCurrentXmlFormated() throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXmlFormated_(null, 1);
	}
	
	public static String getPositionXmlFormated(int iLevel) throws ExceptionZZZ {
		return ReflectCodeZZZ.getPositionCurrentXmlFormated_(null, iLevel+1);
	}
		
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String getPositionCurrentXmlFormated(int iLevel) throws ExceptionZZZ {
		return getPositionCurrentXmlFormated_(null, iLevel+1);
	}
	
	
	private static String getPositionCurrentXmlFormated_(Class objClass, int iLevel) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//Wichtig:
			//Rufe die Methoden zur "Positionsbestimmung" hier in der obersten Funktion auf.
			//in den Funtionen darunter muesste ja alles wieder um 2 Ebene tiefer definiert werden.
			//Das gilt sowohl für die Zeile als auch für den Dateinamen oder die Methode.
			int iLevelUsed = iLevel+1;
			
			//FGL20251118: 
			//Formatanweisungen hier zu verwenden ist ein alternativer Ansatz
			//Hier wird der LogStringFormatManagerXml verwendet um diesen String zu errechnen.
			//Da diese Strings eh in XML umgewandelt werden sollen, uebergeben wir keine XML Tags an die zugrundeliegende HashMap,
			//sondern die einfachen Strings.
		
			String sClassname = ReflectCodeZZZ.getClassCallingName(iLevelUsed);
			
			//Merke: Das reine, aktuelle Objekt kann man auch ueber die Formatierungsanweisung irgendwann in den String einbauen.
			//       Nur die Zeilennummer, etc. muss AN DIESER STELLE (!) so errechnet werden.			
			int iLine = ReflectCodeZZZ.getMethodCallingLine(iLevelUsed);
			String sLine = new Integer(iLine).toString();
			
			String sFile = ReflectCodeZZZ.getMethodCallingFileName(iLevelUsed);
						
			String sMethod = ReflectCodeZZZ.getMethodCallingName(iLevelUsed);
						
			//TODOGOON20240503: Irgendwie eine ENUM anbieten welche Form man gerne haette... file oder object zentriert.
			//a) Variante mit ,,, abc.java:iLine --- Merke: Damit wird die Position in der Eclipse Konsole clickbar.
			String sPositionInFile = getPositionCurrentInFile(sFile, iLine);
			
			//b) Variante mit Objektname und dahinter iLine
			//String sObjectWithMethod = ReflectCodeZZZ.getClassCallingName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + sMethod;
			//String sPositionInObject =  getPositionCurrentInObject(sObjectWithMethod, iLine);
			
			//FGL20251118: 
			//Hier wird der LogStringFormatManagerXml verwendet um diesen String zu errechnen.
			//Dabei ist der String der aktuellen Position eh nur für andere Log-Formatierungen die Quelle für Daten.
			//Das ist etwas uebertrieben. Wird aber hier als alternativer Ansatz implementiert.
			
			
			//Hier müssen nun die PRO und POST Formatierungsanweisungen definiert werden
			//und sie müssen vor und dahinter geschrieben werden.
			LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogString = new LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String>();
			
			//Ansatzpunkt für weiter Tags als Format			
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAMESIMPLE_XML_BY_HASHMAP, sClassname);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_HASHMAP, sMethod);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILELINE_XML_BY_HASHMAP, sLine);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_XML_BY_HASHMAP, sFile);
			hmLogString.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML_BY_HASHMAP, sPositionInFile);
			
			//Erzeuge hier den String als XML Variante. 
			//Grund: Die Tags aus dieser Variante koennen dann von dem LogStringFormatManagerZZZ (oder auch wieder vom LogStringFormatManagerXmlZZZ)
			//       in ein Format gebracht werden, bei dem die Reihenfolge veraendert wurde
			ILogStringFormaterZZZ objFormater = new LogStringFormater4ReflectCodeZZZ();
			sReturn = LogStringFormatManagerXmlZZZ.getInstance().compute(objFormater, hmLogString);
		
			//umgib den Tag mit dem <positioncurrent> Tag
			ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
			sReturn = objTagPositionCurrent.getElementString();	
			
			//++++++++++++++++++++++++++++++++++++
			//Noch weitere Informationen voranstellen (ausserhalb des positioncurrent-Tags)
			long lngThreadId = Thread.currentThread().getId();     
			String sThreadId = LongZZZ.longToString(lngThreadId);
			
			GregorianCalendar d = new GregorianCalendar();
	        Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	        Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	        Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	        Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	        Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE));
	        String sDate = iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	                     + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString();		
	       	
			LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLogStringPre = new LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String>();			
			hmLogStringPre.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_XML_BY_HASHMAP, sDate);
			hmLogStringPre.put(ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_XML_BY_HASHMAP, sThreadId);
			
			//Erzeuge hier den String als XML Variante. 
			//Grund: Die Tags aus dieser Variante koennen dann von dem LogStringFormatManagerZZZ (oder auch wieder vom LogStringFormatManagerXmlZZZ)
			//       in ein Format gebracht werden, bei dem die Reihenfolge veraendert wurde
			ILogStringFormaterZZZ objFormaterPre = new LogStringFormater4ReflectCodeZZZ();
			String sReturnPre = LogStringFormatManagerXmlZZZ.getInstance().compute(objFormater, hmLogStringPre);			
			sReturn = sReturnPre + sReturn;
			
			//+++++++++++++++++++++++++++++++++++++
			//20251128; Der MessageSeparator ist nun eine eigene Formatanweisung
			//Abschliessenden Trenner für mögliche Folgekommentare
			//Aber beim XML Format gibt es diese nicht!!!
			//sReturn = sReturnPre + sReturn + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;

//WICHTIG: DEN XML BASIERTEN STRING NICHT(!!!) BUENDIG MACHEN. DA DIE TAGS DIE GRENZE GAAAANZ WEIT NACH RECHTS SCHIEBEN			
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			IStringJustifierZZZ objStringJustifier = StringJustifierZZZ.getInstance();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);
		}//end main:
		return sReturn;
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
	public static String getPackagePath(Object obj) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
			  if(obj == null) break main;
			  
			  Class objClass = obj.getClass();
			  sReturn = ReflectCodeZZZ.getPackagePath(objClass);
		  
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
	public static String getPackagePathForConstant(Object obj) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
			  if(obj == null) break main;
			  
			  Class objClass = obj.getClass();
			  sReturn = ReflectCodeZZZ.getPackagePathForConstant(objClass);
		  
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
	public static String getPackagePath(Class objClass)throws ExceptionZZZ {
		  String sReturn = null;
		  main:{			 
				  if(objClass == null) break main;
				  
				  Package objPackage  = objClass.getPackage();
				  if(objPackage!=null) {
					  sReturn = objPackage.getName(); //Merke: Wenn Klassen in einem JAR-File zusammengefasst werden, dann haben sie ein leeres Package
					  sReturn =StringZZZ.replace(sReturn,  ReflectCodeZZZ.sPACKAGE_SEPERATOR, File.separator);
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
	public static String getPackagePathForConstant(Class objClass) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{			 
				  if(objClass == null) break main;
				  
				  Package objPackage  = objClass.getPackage();
				  if(objPackage!=null) {
					  sReturn = objPackage.getName(); //Merke: Wenn Klassen in einem JAR-File zusammengefasst werden, dann haben sie ein leeres Package
					  sReturn =StringZZZ.replace(sReturn,  ReflectCodeZZZ.sPACKAGE_SEPERATOR, File.separator);
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
	public static String getPackagePathByReflection(Class objClass)throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
			  try {
				  if(objClass == null) break main;
				
				//Merke: Fuer einen anderen Konstruktor, z.B. int,int,double
				//final Myclass v = MyClass.class.getConstructor(
				//	   int.class, int.class, double.class).newInstance(_xval1,_xval2,_pval);
				  				  
				  Object obj = objClass.getConstructor().newInstance();				
				  sReturn = ReflectCodeZZZ.getPackagePath(obj);				  
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
	public static String getClassNameOnly(Object obj) throws ExceptionZZZ {
		 String sReturn = null;
		  main:{
			  check:{
				  if(obj == null) break main;
			  }//END check:
		  
		  Class objClass = obj.getClass();
		  String sNameTotal = objClass.getName();
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ReflectCodeZZZ.sPACKAGE_SEPERATOR);
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
	public static String getClassNameOnly(Class classOfObject) throws ExceptionZZZ {
		 String sReturn = null;
		  main:{
			  check:{
				  if(classOfObject == null) break main;
			  }//END check:
		  
		  String sNameTotal = classOfObject.getName();
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ReflectCodeZZZ.sPACKAGE_SEPERATOR);
		  while(objToken.hasMoreTokens()){
			  sReturn = objToken.nextToken();
		  }
		  		  
		  }//END Main:
		return sReturn;			
	}
	
	
	/**
	 * Liefert den Namen einer aufrufenden Methode oder deren Aufrufer
	 * @param callerID - gibt an, wieviele Aufrufe oberhalb der aufrufenden Methode
	 * ausgegeben werden sollen. 0 liefert den Namen der aufrufenden Methode, 1 den
	 * Namen des uebergeordneten Aufrufers usw.
	 * @return - Name der Aufrufenden Methode
	 */
	public static String getCaller(int callerID) throws ExceptionZZZ  {
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
	public static String[] getCallingStack() throws ExceptionZZZ {
		return ReflectCodeZZZ.getCallingStack(null);
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * @param sClassnameFilterRegEx : Schränke die Array-Einträge ein, auf Klassen, die diesen RegEx Ausdruck haben.
	 * @return
	 * @author Fritz Lindhauer, 16.06.2019, 07:42:03
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack(String sRegEx) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{
		ArrayList<String>listasTemp = new ArrayList<String>();
		String stemp; boolean btemp;
		//org.apache.regexp.RE objReFilter = null;
		Matcher matcher = null;
		Pattern pattern = null;
			  
		//#####################DEBUG 						
		//Filtere diese ReflectCodeZZZ-Klasse selber aus.
		String sClassNameRegexed = StringZZZ.replace(ReflectCodeZZZ.class.getName(), ReflectCodeZZZ.sPACKAGE_SEPERATOR, "\\" + ReflectCodeZZZ.sPACKAGE_SEPERATOR);
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
						if(iposition>=ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT){	//Man willl den Stactrace erst ab der aktuellen Methode und nicht den oben durchgeführten "Thread-Aufruf";																			
							//if(objReFilter==null){
							if(pattern==null){
								stemp = ReflectCodeZZZ.getClassMethodString(element);									
								listasTemp.add(stemp);
							}else{
								stemp = element.getClassName();
																
								//btemp = objReFilter.match(stemp);
								matcher = pattern.matcher(stemp);
								btemp=matcher.find();
								
								if(btemp) {
									stemp = ReflectCodeZZZ.getClassMethodString(element);									
									listasTemp.add(stemp);
								}
							}
						}//end if
					}//end for
										
				}else{
					
					//Verarbeitung vor Java 1.4
					ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	  
				}//end Java Version
			  saReturn = ArrayListUtilZZZ.toStringArray( listasTemp);
		}//end main
		return saReturn;
	}
	

	
	public static String getClassMethodString() throws ExceptionZZZ {
		String sReturn = "";
		main:{
			String sMethod=ReflectCodeZZZ.getMethodCurrentName();//+1 Stacktrace steckt schon in in "calling", wir brauchen hier aber die auf-aufrufende Position
			String sClass = ReflectCodeZZZ.getClassCurrentName();//+1 Stacktrace steckt schon in in "calling", wir brauchen hier aber die auf-aufrufende Position
			sReturn = sClass;
			if(!StringZZZ.isEmpty(sMethod)){
				sReturn = sReturn + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
			}
		}
		return sReturn;
	}
	
	public static String getClassMethodString(StackTraceElement objStack) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objStack==null){
				//Hier mal vollkommen ungenerisch die Exception bauen. Hintergrund: Wenn diese Methode in den Reflect-Klassen verwendet wird, fürchte ich sonst eine Eindlosschleife.
				ExceptionZZZ ez = new ExceptionZZZ("StackTraceElement fehlt'", iERROR_PARAMETER_MISSING, ReflectCodeKernelZZZ.class, "getClassMethodString(StackTraceElement objStack)");
				throw ez;	
			}
			
			String sMethod = objStack.getMethodName();
			String sClass = objStack.getClassName();
			sReturn = sClass + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
		}//end main
		return sReturn;
	}
	
	public static String getClassMethodCallingString() throws ExceptionZZZ {
		return ReflectCodeZZZ.getClassMethodCallingString(1);//1 Stacktraceposition weiter 
	}
	public static String getClassMethodCallingString(int iOffset) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sClass = ReflectCodeZZZ.getClassCallingName(iOffset+1);//1 Stacktraceposition weiter
			String sMethod = ReflectCodeZZZ.getMethodCallingName(iOffset+1);//1 Stacktraceposition weiter
			sReturn = sClass + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
		}
		return sReturn;
	}
	
	
	/**Hohle den String, der aktuellen Klasse
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:22:51
	 */
	public static String getClassMethodCurrentString() throws ExceptionZZZ {
		return ReflectCodeZZZ.getClassMethodCallingString();//1 Stacktraceposition weiter steckt schon in "calling"
	}
	
	
	
	
	
	/**
	 * Ermittelt den Namen der aufrufenden Funktion.
	 */
	public static String lastCaller(String className) throws ExceptionZZZ {
		String lastCaller = "";
		for (int i = 1; true; i++) {
			lastCaller = getCaller(i);
			if (lastCaller == null) {
				return "keine aufrufende Methode";
			}
			if (lastCaller.indexOf(className + ReflectCodeZZZ.sPACKAGE_SEPERATOR) < 0) {
				return lastCaller;
			}
		}
	}
	
	/**
	 * Ermittelt die Tiefe des Aufrufstacks.
	 */
	public static int callStackSize() throws ExceptionZZZ {
        StringWriter sw = new StringWriter();
        (new Throwable()).printStackTrace(new PrintWriter(sw));
        String trace = sw.toString();
        int size = 0;
        for (int nl = 0; nl != -1; nl = trace.indexOf("\n", nl + 1)) {
        	size++;
        }
        return size;
    }
	
	public static StackTraceElement[] getStackTrace(String sRegEx) throws ExceptionZZZ {
		StackTraceElement[] objaReturn = null;
		main:{			
			ArrayList<StackTraceElement>listaTemp = new ArrayList<StackTraceElement>();
			String stemp; boolean btemp;
	
			Matcher matcher = null;
			Pattern pattern = null;
				  
			//#####################DEBUG 						
			//Filtere diese ReflectCodeZZZ-Klasse selber aus.
			String sClassNameRegexed = StringZZZ.replace(ReflectCodeZZZ.class.getName(), ReflectCodeZZZ.sPACKAGE_SEPERATOR, "\\" + ReflectCodeZZZ.sPACKAGE_SEPERATOR);
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
					if(iposition>=ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT){	//Man willl den Stactrace erst ab der aktuellen Methode und nicht den oben durchgeführten "Thread-Aufruf";																			
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
				ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
				
			}//end Java Version
			objaReturn = listaTemp.toArray(new StackTraceElement[listaTemp.size()]);
		}//end main:
		return objaReturn;
	}
	
	/**Jetzt hat man die Postionsangaben mit Tags versehen, will sie dann aber wieder raus haben.
	 * Das ist z.B. im LogStringFormatManagerZZZ der Fall, der die LogStrings ohne "stoerende" Tags ausgeben will. 
	 * 
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.11.2025, 18:39:28
	 */
	public static String removePositionCurrentTagPartsFrom(String sXml) throws ExceptionZZZ {
		String sReturn = sXml;
		main:{
			if(StringZZZ.isEmpty(sXml)) break main;
			
			//Entferne nun die in ReflectCodeZZZ.getPositionCurrent() -sinnvollerweise - hinzugenommenen XML Tags
			//ITagByTypeZZZ objTagLine = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.LINENUMBER);
			ITagTypeZZZ objTagTypeDate = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.DATE);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeDate, "");
			
			ITagTypeZZZ objTagTypeThreadId = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeThreadId, "");

			ITagTypeZZZ objTagTypeClassName = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeClassName, "");
			
			ITagTypeZZZ objTagTypeLine = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.LINENUMBER);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeLine, "");
			
			//ITagByTypeZZZ objTagFile = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME);
			ITagTypeZZZ objTagTypeFile = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeFile, "");
					
			//ITagByTypeZZZ objTagMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.METHOD);
			ITagTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.METHOD);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypeMethod, "");
			
			//ITagByTypeZZZ objTagPosition = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE);
			ITagTypeZZZ objTagTypePosition = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypePosition, "");
			
			//ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT);
			ITagTypeZZZ objTagTypePositionCurrent = TagByTypeFactoryZZZ.createTagTypeByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT);
			sReturn = XmlUtilTagByTypeZZZ.replaceTagParts(sReturn, objTagTypePositionCurrent, "");						
		}
		return sReturn;
	}
}
