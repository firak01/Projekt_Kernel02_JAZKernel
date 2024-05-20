package basic.zBasic;

import basic.zKernel.KernelLogZZZ;

public class ObjectZZZ extends AbstractObjectZZZ{
	
	//#####################
	//### 20240520:
	//### (Ehemalige) nicht statische Methoden aus ILogZZZ als hier static zur Verfuegung stellen.
	//### Damit koennen diese auch in static Methoden genutzt werden.
	//### Also nicht static Methoden sind "logProtoclString" ausgelegt...
	//#####################	
		
	public static void logLine(String sLog) throws ExceptionZZZ{
		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
		System.out.println(sPositionCalling + sLog);
	}
	
	public static void logLineWithDate(String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(sLog);		
		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
		System.out.println(sPositionCalling + sTemp);
	}
	
	public static void logLineDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(obj, sLog);		
		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
		System.out.println(sPositionCalling + sTemp);
	}

	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
}
