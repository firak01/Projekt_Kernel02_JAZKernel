package basic.zBasic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import base.collections.CollectionUtil;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericSignificantZZZTest;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;

public class ReflectCodeZZZ  implements IConstantZZZ{
	public static  final int iPOSITION_STACKTRACE_CURRENT = 2;
	public static  final int iPOSITION_STACKTRACE_CALLING = 3;
	
	public static final String sCLASS_METHOD_SEPERATOR = ".";
	public static final String sPACKAGE_SEPERATOR = ".";
	
	public static String getMethodCurrentName(){
		return getMethodCurrentName(1);
	}
	/**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentName(int iOffset) {		  
			String method = null;
			 
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///Siehe Artikel 'The surprisingly simple stack trace Element'");
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();				
//				if(stackTrace.length>=3){
//					int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
//					StackTraceElement element = stackTrace[iPositionInStacktrace];
//					method = element.getMethodName();
//				}
				
				if(stackTrace.length>=4){
					StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iOffset];
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
	  
	  public static String getMethodCurrentNameLined() throws ExceptionZZZ{
		  return ReflectCodeZZZ.getMethodCurrentNameLined(1,0);//0=iLineOffset
	  }
	  
	  public static String getMethodCurrentNameLined(int iLineOffset) throws ExceptionZZZ{
		  return ReflectCodeZZZ.getMethodCurrentNameLined(1,iLineOffset);
	  }
	  
	  /**
		 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
		 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
		   * @return Return the name of the routine that called getCurrentMethodName
		   */
		  public static String getMethodCurrentNameLined(int iOffset, int iLineOffset) throws ExceptionZZZ{		  
				String method = null;
				
				if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					//int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT + iOffset;
					method = ReflectCodeZZZ.getMethodCallingName(iOffset);
					
					int iLine = ReflectCodeZZZ.getMethodCallingLine(iOffset); //Berechne die gewünschte Zeile
					iLine = iLine + iLineOffset;
					method +=ReflectCodeZZZ.formatMethodCallingLine(iLine); //Berechne den String  für die Zeilenausgabe.
										
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
	  
		  public static String getMethodCallingName() {
			  return getMethodCallingName(1);
		  }
		  
	  /**
	 * @return Name der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	public static String getMethodCallingName(int iOffset) {
		String method = null;
		
		
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			if(stackTrace.length>=4){
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
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
	
	
	 public static String formatMethodCallingLine(int iLine){
		 //Merke: Nach der Zeilenangabe ist # besser. Z.B der Doppelpunkt wird in Vielen Logstrings selbst benutzt
		 return " - Line " + iLine + "# ";
		 
	 }
	 
	
	  /**
		 * @return Zeile im Stacktrace für den Namen der Methode, welche die aktuelle Methode aufgerufen hat.
		 * lindhaueradmin, 23.07.2013
		 */
		public static int getMethodCallingLine() throws ExceptionZZZ{
			return getMethodCallingLine(1);
		}
		
		public static int getMethodCallingLine(int iOffset) throws ExceptionZZZ{
			int iLine = -1;
						
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
				if(stackTrace.length>=4){
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
	  
	  
	  /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCurrentName() throws ExceptionZZZ{
		  String sReturn = null;
		  main:{			  			  
			 sReturn = ReflectCodeZZZ.getClassCallingName(0);
		  }//end main:
		  return sReturn;
	  }
	public static String getClassCurrentName(int iOffset) throws ExceptionZZZ{
		  String sReturn = null;
		  main:{			  			  
			 sReturn = ReflectCodeZZZ.getClassCallingName(iOffset);
		  }//end main:
		  return sReturn;
	  }
	
	public static String getClassCallingName() throws ExceptionZZZ{
		return getClassCallingName(1);
	}
	
	  /**
		 * @return Name (inkl. Package) der aktuellen Klasse
		 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
		 */
		public static String getClassCallingName(int iOffset) throws ExceptionZZZ{
			  String sReturn = null;
			  main:{
				  
				  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
						//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
						///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");
						final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
						if(stackTrace.length>=4){
							int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
							StackTraceElement element = stackTrace[iPositionInStacktrace];
							sReturn = element.getClassName();
						}												
					}else{
						
						//Verarbeitung vor Java 1.4
						ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
						
						
//						//Verarbeitung vor Java 1.4
//						  
//					  String sMethodCalling = ReflectCodeZZZ.getMethodCallingName(); //... weil getMethodCurrentName(); DIESE Methode zur�ckgeben w�rde.
//					  if(StringZZZ.isEmpty(sMethodCalling)) break main;
//					  
//					  //Hole alles links von der Methode
//					  sReturn = StringZZZ.leftback(sMethodCalling, ".");
						
					}//end Java Version
			  }//end main:
			  return sReturn;
		  }
		
		public static String  getPositionCurrent() throws ExceptionZZZ{
			String sReturn = null;
			  main:{
				  
				  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
						//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
						///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");

						//!!! Ergaenze diese Zeile um die ThreadId. Das ist wichtig, damit man bei mehreren Threads die Ausgabe passend zuordnen kann.
						long lngThreadID = Thread.currentThread().getId();
						String sThread = "[Thread: "+lngThreadID+"] ";
					  
					  	int iLine = ReflectCodeZZZ.getMethodCallingLine();
					  	String sLine = ReflectCodeZZZ.formatMethodCallingLine(iLine );
						String sLineTotal = sThread + ReflectCodeZZZ.getClassCallingName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR  + ReflectCodeZZZ.getMethodCallingName()  + sLine;
				
						sReturn = sLineTotal;
					}else{
						
							//Verarbeitung vor Java 1.4
						ExceptionZZZ ez = new ExceptionZZZ("Verarbeitung vor Java 1.4 steht (noch) nicht zur Vefügung. '", iERROR_RUNTIME, ReflectCodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	   
					}//end Java Version
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
	public static String getPackagePath(Object obj) throws ExceptionZZZ{
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
	public static String getPackagePathForConstant(Object obj){
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
	public static String getPackagePath(Class objClass)throws ExceptionZZZ{
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
	 */
	public static String getPackagePathForConstant(Class objClass){
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
	public static String getPackagePathByReflection(Class objClass)throws ExceptionZZZ{
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
	public static String getClassNameOnly(Object obj){
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
	public static String getClassNameOnly(Class classOfObject){
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
	 * Namen des �bergeordneten Aufrufers usw.
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
	public static String[] getCallingStack() throws ExceptionZZZ{
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
	public static String[] getCallingStack(String sRegEx) throws ExceptionZZZ{
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
			  saReturn = ArrayListZZZ.toStringArray( listasTemp);
		}//end main
		return saReturn;
	}
	

	
	public static String getClassMethodString() throws ExceptionZZZ{
		String sReturn = "";
		main:{
			String sMethod=ReflectCodeZZZ.getMethodCallingName(1);//0 wäre die aktuelle Methode als aufrufende Methode, wir brauchen aber an dieser Stelle die auf-aufrufende Methode, also 1.
			String sClass = ReflectCodeZZZ.getClassCallingName(1);//0 wäre die aktuelle Methode als aufrufende Methode, wir brauchen aber an dieser Stelle die auf-aufrufende Methode, also 1.
			sReturn = sClass;
			if(!StringZZZ.isEmpty(sMethod)){
				sReturn = sReturn + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
			}
		}
		return sReturn;
	}
	
	public static String getClassMethodString(StackTraceElement objStack) throws ExceptionZZZ{
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
	
	public static String getClassMethodCallingString() throws ExceptionZZZ{
		return ReflectCodeZZZ.getClassMethodCallingString(2);//1 wäre die Methode, die  diese aufruft, aber um die echte auf-aufrufende Methode zu bekommen ein weiteres +1
	}
	public static String getClassMethodCallingString(int iOffset) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sClass = ReflectCodeZZZ.getClassCallingName(iOffset);
			String sMethod = ReflectCodeZZZ.getMethodCallingName(iOffset);
			sReturn = sClass + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;
		}
		return sReturn;
	}
	
	
	/**Hohle den String, der aktuellen Klasse
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:22:51
	 */
	public static String getClassMethodCurrentString() throws ExceptionZZZ{
		return ReflectCodeZZZ.getClassMethodCallingString(1);//1 wäre die Methode, die  diese aufruft
	}
	
	
	
	
	
	/**
	 * Ermittelt den Namen der aufrufenden Funktion.
	 */
	public static String lastCaller(String className)
	{
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
	public static int callStackSize()
	{
        StringWriter sw = new StringWriter();
        (new Throwable()).printStackTrace(new PrintWriter(sw));
        String trace = sw.toString();
        int size = 0;
        for (int nl = 0; nl != -1; nl = trace.indexOf("\n", nl + 1)) {
        	size++;
        }
        return size;
    }
	
	public static StackTraceElement[] getStackTrace(String sRegEx) throws ExceptionZZZ{
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
}
