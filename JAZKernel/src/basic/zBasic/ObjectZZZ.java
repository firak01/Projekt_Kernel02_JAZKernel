package basic.zBasic;

import basic.zKernel.KernelLogZZZ;

public class ObjectZZZ extends AbstractObjectWithExceptionZZZ{
	
	//#####################
	//### 20240520:
	//### (Ehemalige) nicht statische Methoden aus ILogZZZ als hier static zur Verfuegung stellen.
	//### Damit koennen diese auch in static Methoden genutzt werden.
	//### Also nicht static Methoden sind "logProtoclString" ausgelegt...
	//#####################	
	
	//##### Gib den String aus.
	//  Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//  Das dann jeweils als Variante mit einer Klasse als Argument
	public static void logLine(String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(sLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(obj, sLog);
		System.out.println(sTemp);
	}
	
	public static void logLine(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(classObj, sLog);
		System.out.println(sTemp);
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static void logLineDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(obj, sLog);				
		System.out.println(sTemp);
	}
	
	public static void logLineDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(classObj, sLog);				
		System.out.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	public static void logLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(obj, sLog);
		System.out.println(sTemp);
	}
	
	public static void logLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(classObj, sLog);				
		System.out.println(sTemp);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
}
