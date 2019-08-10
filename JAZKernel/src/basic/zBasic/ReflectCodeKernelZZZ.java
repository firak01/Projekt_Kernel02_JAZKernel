package basic.zBasic;


/** Reflection-Klassen, die sich auf die Kernel-Klassen biezieht.
 *  Die Methoden hinsichtlich der Klasse ReflectCodeZZZ sind hierin enthalten, 
 *  aber wenn es z.B. darum geht einen aufrufenden Methodennamen zu ermitteln, schränkt man sich auf die KernelZZZ Klassen ein. 
 *  
 * 
 * @author lindhaueradmin, 08.08.2019, 08:52:07
 * 
 */
public class ReflectCodeKernelZZZ implements IConstantZZZ{
	public static String sREGEX_KERNEL_CLASS = "(ZZZ$|ZZZ(T|t)est)$"; //Also: Am Ende des Klassennamens soll ZZZ stehen, bzw. ZZZTest, aber nicht der Reflection-Klassenname 
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * 
	 * Besonderheit:
	 * Schränke dies auf ZZZ-Klassen ein 
	 * UND filtere ggfs. aufrufenden ReflectCode - Klassen aus, also z.B. den Aufruf selbst.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 12.06.2019, 10:08:16
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack() throws ExceptionZZZ{
		//Schränke die Suche auf Kernel-Klassen ein.
		String sRegEx = ReflectCodeKernelZZZ.sREGEX_KERNEL_CLASS; 	
		return ReflectCodeZZZ.getCallingStack(sRegEx);
	}
	
	public static StackTraceElement[] getStackTrace() throws ExceptionZZZ{
		//Schränke die Suche auf Kernel-Klassen ein.
		String sRegEx = ReflectCodeKernelZZZ.sREGEX_KERNEL_CLASS;
		return ReflectCodeZZZ.getStackTrace(sRegEx);
	}
	
	public static StackTraceElement getCallingStackTraceElement() throws ExceptionZZZ{
		StackTraceElement objReturn = null;
		main:{
			objReturn = ReflectCodeKernelZZZ.getCallingStackTraceElement(0);
		}//end main:
		return objReturn;
	}
	
	public static StackTraceElement getCallingStackTraceElement(int iOffset) throws ExceptionZZZ{
		StackTraceElement objReturn = null;
		main:{
			StackTraceElement[] stackTrace = ReflectCodeKernelZZZ.getStackTrace();
			if(stackTrace.length>=4){
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
				objReturn = stackTrace[iPositionInStacktrace];				
			}		
		}//end main:
		return objReturn;
	}
	
	
	public static String getCallingClassMethod() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			StackTraceElement objStack = ReflectCodeKernelZZZ.getCallingStackTraceElement();
			sReturn = ReflectCodeZZZ.getClassMethodString(objStack);			
		}//end main
		return sReturn;
	}
	
	

}
