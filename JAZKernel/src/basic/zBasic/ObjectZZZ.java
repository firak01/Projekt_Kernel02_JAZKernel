package basic.zBasic;

import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.LogZZZ;

public class ObjectZZZ extends AbstractObjectWithExceptionZZZ{
	
	//#####################
	//### Methoden hier static zur Verfuegung stellen.
	//### Damit koennen diese auch in static Methoden genutzt werden.
	//#####################	
	
	//#############################################
	//### log... ohne Protocol sind für die Ausgabe auf der Konsole gedacht.
	//###        Daher wird hier system.out verwendet.
    //##############################################
	

	//##### Gib den String aus.
	//  Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//  Das dann jeweils als Variante mit einer Klasse als Argument
	//public static void logLine(String sLog) throws ExceptionZZZ{		
	public void logLine(String sLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLine(this.getClass(), sLog);
		System.out.println(sTemp);
	}
	
	//public static void logLine(String[] saLog) throws ExceptionZZZ{		
	public void logLine(String[] saLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLine(this.getClass(), saLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLine(obj, sLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLine(obj, saLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLine(classObj, sLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Class classObj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLine(classObj, saLog);
		System.out.println(sTemp);
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static void logLineDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLineDate(obj, sLog);				
		System.out.println(sTemp);
	}
	
	public static void logLineDate(Object obj, String[] saLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLineDate(obj, saLog);				
		System.out.println(sTemp);
	}
	
	public static void logLineDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLineDate(classObj, sLog);				
		System.out.println(sTemp);
	}
	public static void logLineDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		String sTemp = LogZZZ.computeLineDate(classObj, saLog);				
		System.out.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	public static void logLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLineDateWithPosition(obj, 1, sLog);
		System.out.println(sTemp);
	}
	
	public static void logLineDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = LogZZZ.computeLineDateWithPosition(obj, 1, saLog);
		System.out.println(sTemp);
	}
	
	public static void logLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = LogZZZ.computeLineDateWithPosition(classObj, 1, sLog);				
		System.out.println(sTemp);
	}
	
	public static void logLineDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = LogZZZ.computeLineDateWithPosition(classObj, saLog);				
		System.out.println(sTemp);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
}
