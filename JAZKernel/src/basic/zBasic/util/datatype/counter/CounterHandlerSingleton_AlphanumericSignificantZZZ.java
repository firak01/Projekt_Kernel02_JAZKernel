package basic.zBasic.util.datatype.counter;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
//import basic.zBasic.KernelSingletonVIA;
//import basic.zKernel.IKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class CounterHandlerSingleton_AlphanumericSignificantZZZ {
	private static CounterHandlerSingleton_AlphanumericSignificantZZZ objCounterSingleton = null; //muss static sein, wg. getInstance()!!!
	
	private HashMap<String,ICounterAlphanumericSignificantZZZ>hmCounter=new HashMap<String, ICounterAlphanumericSignificantZZZ>();
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @param objFrame
	 * @throws ExceptionZZZ
	 */	
	private CounterHandlerSingleton_AlphanumericSignificantZZZ(){
		super(); 
	}
	
	public static CounterHandlerSingleton_AlphanumericSignificantZZZ getInstance() throws ExceptionZZZ{
		if(objCounterSingleton==null){
			objCounterSingleton = new CounterHandlerSingleton_AlphanumericSignificantZZZ();
			
			//Merke: Hier ohne Kernel-Objekt arbeiten.
			//!!! Das Singleton Kernel Objekt holen und setzen !!!
//			KernelSingletonVIA objKernel = KernelSingletonVIA.getInstance();
//			objApplicationSingleton.setKernelObject(objKernel);			
		}
		return objCounterSingleton;		
	}
	
//	public static CounterAlphanumericSingletonZZZ getInstance(IKernelZZZ objKernel) throws ExceptionZZZ{
//		if(objApplicationSingleton==null){
//			objApplicationSingleton = new ApplicationSingletonVIA(objKernel);
//		}
//		return objApplicationSingleton;		
//	}
	
	private ICounterAlphanumericSignificantZZZ getCounterFor() throws ExceptionZZZ{
		ICounterAlphanumericSignificantZZZ objCounter = null;
		main:{
			//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
			String[] saCalling = ReflectCodeZZZ.getCallingStack();
			
			//Suche in der Hashmap nach einem String des Klassen.Methodennamens, der schon in der HashMap gespeichert ist.
			//Wird er gefunden, dann soll der Counter wiederverwendet werden.
			
			
			//Wenn es solch einen String nicht gibt, erzeuge neuen Counter und speichere in Hashmap unter dem aktuellen Methodennamen.
		}
		return objCounter;
	}
	
	private ICounterAlphanumericSignificantZZZ getCounterFor(String sKey){
		ICounterAlphanumericSignificantZZZ objCounter = null;
		main:{
			
		}
		return objCounter;
	}
}
