package basic.zBasic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import basic.zBasic.util.datatype.string.StringZZZ;

public class ReflectCodeZZZ  implements IConstantZZZ{
	public static  final int iPOSITION_STACKTRACE_CURRENT = 2;
	public static  final int iPOSITION_STACKTRACE_CALLING = 3;

	/**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentName() {		  
			String method = null;
			 
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///Siehe Artikel 'The surprisingly simple stack trace Element'");

				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				if(stackTrace.length>=3){
					StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT];
					method = element.getMethodName();
				}
				
//				OutputStream out = System.out;
//				for (StackTraceElement element : stackTrace) {
//					final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//					+ " in class " + element.getClassName() + " [on line number "
//					+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//					
//					try{					
//						out.write(exceptionMsg.getBytes());
//					} catch (IOException ioEx){
//						System.err.println(
//						"IOException encountered while trying to write "
//						+ "StackTraceElement data to provided OutputStream.\n"
//						+ ioEx.getMessage() );
//					}
//				}
				
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
				  int iDotPos = methodWithPackageName.lastIndexOf(".");
				  if(iDotPos<=-1){
					  method = methodWithPackageName;
				  }else{
					  method = methodWithPackageName.substring(iDotPos+1);
				  }
			  }
			}//End if : Verarbeitung vor Java 1.4
			  return method;
	  }
	  
	  public static String getMethodCurrentNameLined(){
		  return ReflectCodeZZZ.getMethodCurrentNameLined(0);
	  }
	  
	  /**
		 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
		 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
		   * @return Return the name of the routine that called getCurrentMethodName
		   */
		  public static String getMethodCurrentNameLined(int iOffset) {		  
				String method = null;
				
				if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					method = ReflectCodeZZZ.getMethodCallingName();
					
					int iLine = ReflectCodeZZZ.getMethodCallingLine() + iOffset; //Berechne die gew�nschte Zeile
					
					method +=ReflectCodeZZZ.getMethodCallingLine(iLine); //Berechne den String  f�r die Zeilenausgabe.
					
					
//					OutputStream out = System.out;
//					for (StackTraceElement element : stackTrace) {
//						final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//						+ " in class " + element.getClassName() + " [on line number "
//						+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//						
//						try{					
//							out.write(exceptionMsg.getBytes());
//						} catch (IOException ioEx){
//							System.err.println(
//							"IOException encountered while trying to write "
//							+ "StackTraceElement data to provided OutputStream.\n"
//							+ ioEx.getMessage() );
//						}
//					}
					
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
					  int iDotPos = methodWithPackageName.lastIndexOf(".");
					  if(iDotPos<=-1){
						  method = methodWithPackageName;
					  }else{
						  method = methodWithPackageName.substring(iDotPos+1);
					  }
				  }
				}//End if : Verarbeitung vor Java 1.4
				  return method;
		  }
	  
	  /**
	 * @return Name der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	public static String getMethodCallingName() {
		String method = null;
		
		
		if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
			//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
			///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");

			final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			if(stackTrace.length>=4){
				StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING];
				method = element.getMethodName();
			}
			
//			OutputStream out = System.out;
//			for (StackTraceElement element : stackTrace) {
//				final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//				+ " in class " + element.getClassName() + " [on line number "
//				+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//				
//				try{					
//					out.write(exceptionMsg.getBytes());
//				} catch (IOException ioEx){
//					System.err.println(
//					"IOException encountered while trying to write "
//					+ "StackTraceElement data to provided OutputStream.\n"
//					+ ioEx.getMessage() );
//				}
//			}
			
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
			  int iDotPos = methodWithPackageName.lastIndexOf(".");
			  if(iDotPos<=-1){
				  method = methodWithPackageName;
			  }else{
				  method = methodWithPackageName.substring(iDotPos+1);
			  }
		  }
		}//end if java version
		  return method;
	  }
	
	
	 public static String getMethodCallingLine(int iLine){
		 
		 return " - Line: " + iLine;
		 
	 }
	  /**
		 * @return Name der Methode, welche die aktuelle Methode aufgerufen hat.
		 * lindhaueradmin, 23.07.2013
		 */
		public static int getMethodCallingLine() {
			int iLine = -1;
			
			
			if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
				//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
				///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");

				final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				if(stackTrace.length>=4){
					StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING];
					iLine = element.getLineNumber();
				}
				
//				OutputStream out = System.out;
//				for (StackTraceElement element : stackTrace) {
//					final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//					+ " in class " + element.getClassName() + " [on line number "
//					+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//					
//					try{					
//						out.write(exceptionMsg.getBytes());
//					} catch (IOException ioEx){
//						System.err.println(
//						"IOException encountered while trying to write "
//						+ "StackTraceElement data to provided OutputStream.\n"
//						+ ioEx.getMessage() );
//					}
//				}
				
			}else{
				
			//Verarbeitung vor Java 1.4
			  
			}//end if java version
			  return iLine;
		  }
	  
	  
	  /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 */
	public static String getClassCurrentName(){
		  String sReturn = null;
		  main:{
			  
			  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
					//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
					///System.out.println("HIER WEITERARBEITEN, gemäß Artikel 'The surprisingly simple stack trace Element'");

					final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
					if(stackTrace.length>=4){
						StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CURRENT];
						sReturn = element.getClassName();
					}
					
//					OutputStream out = System.out;
//					for (StackTraceElement element : stackTrace) {
//						final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//						+ " in class " + element.getClassName() + " [on line number "
//						+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//						
//						try{					
//							out.write(exceptionMsg.getBytes());
//						} catch (IOException ioEx){
//							System.err.println(
//							"IOException encountered while trying to write "
//							+ "StackTraceElement data to provided OutputStream.\n"
//							+ ioEx.getMessage() );
//						}
//					}
					
				}else{
					
						//Verarbeitung vor Java 1.4
						  
					  String sMethodCalling = ReflectCodeZZZ.getMethodCallingName(); //... weil getMethodCurrentName(); DIESE Methode zur�ckgeben w�rde.
					  if(StringZZZ.isEmpty(sMethodCalling)) break main;
					  
					  //Hole alles links von der Methode
					  sReturn = StringZZZ.leftback(sMethodCalling, ".");
				}//end Java Version
		  }//end main:
		  return sReturn;
	  }
	
	  /**
		 * @return Name (inkl. Package) der aktuellen Klasse
		 * lindhaueradmin, 23.07.2013
		 */
		public static String getClassCallingName(){
			  String sReturn = null;
			  main:{
				  
				  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
						//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
						///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");

						final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
						if(stackTrace.length>=4){
							StackTraceElement element = stackTrace[ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING];
							sReturn = element.getClassName();
						}
						
//						OutputStream out = System.out;
//						for (StackTraceElement element : stackTrace) {
//							final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//							+ " in class " + element.getClassName() + " [on line number "
//							+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//							
//							try{					
//								out.write(exceptionMsg.getBytes());
//							} catch (IOException ioEx){
//								System.err.println(
//								"IOException encountered while trying to write "
//								+ "StackTraceElement data to provided OutputStream.\n"
//								+ ioEx.getMessage() );
//							}
//						}
						
					}else{
						
							//Verarbeitung vor Java 1.4
							  
					}//end Java Version
			  }//end main:
			  return sReturn;
		  }
		
		public static String  getPositionCurrent(){
			String sReturn = null;
			  main:{
				  
				  if(ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(ReflectEnvironmentZZZ.sJAVA4)){
						//Verarbeitung ab Java 1.4: Hier gibt es das "StackTrace Element"
						///System.out.println("HIER WEITERARBEITEN, gem�� Artikel 'The surprisingly simple stack trace Element'");

					  	int iLine = ReflectCodeZZZ.getMethodCallingLine();
					  	String sLine = ReflectCodeZZZ.getMethodCallingLine(iLine );
						String sLineTotal = ReflectCodeZZZ.getClassCallingName() + "."  + ReflectCodeZZZ.getMethodCallingName()  + sLine;
						
						
//						OutputStream out = System.out;
//						for (StackTraceElement element : stackTrace) {
//							final String exceptionMsg = "Exception thrown from " + element.getMethodName()
//							+ " in class " + element.getClassName() + " [on line number "
//							+ element.getLineNumber() + " of file " + element.getFileName() + "]\n";
//							
//							try{					
//								out.write(exceptionMsg.getBytes());
//							} catch (IOException ioEx){
//								System.err.println(
//								"IOException encountered while trying to write "
//								+ "StackTraceElement data to provided OutputStream.\n"
//								+ ioEx.getMessage() );
//							}
//						}
						
						sReturn = sLineTotal;
					}else{
						
							//Verarbeitung vor Java 1.4
							  
					}//end Java Version
			  }//end main:
			  return sReturn;
		}
	  
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 */
	public static String getPackagePath(Object obj){
		  String sReturn = null;
		  main:{
			  check:{
				  if(obj == null) break main;
			  }//END check:
		  
		  Class objClass = obj.getClass();
		  Package objPackage  = objClass.getPackage();
		  if(objPackage!=null) {
			  sReturn = objPackage.getName(); //Merke: Wenn Klassen in einem JAR-File zusammengefasst werden, dann haben sie ein leeres Package
			  sReturn =StringZZZ.replace(sReturn, ".", File.separator);
		  }else{
			  sReturn = ".";
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
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ".");
		  while(objToken.hasMoreTokens()){
			  sReturn = objToken.nextToken();
		  }
		  		  
		  }//END Main:
		return sReturn;			
	}
	
	/** Gibt den reinen Klassennamen (also ohne . oder / ) zur�ck.
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
		  
		  StringTokenizer objToken = new StringTokenizer(sNameTotal, ".");
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
	 */
	public static String[] getCallingStack(){
		String[] saReturn=null;
		main:{
			
		}//end main
		return saReturn;
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
			if (lastCaller.indexOf(className + ".") < 0) {
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
}
