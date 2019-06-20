package basic.zBasic.util.datatype.counter;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
//import basic.zBasic.KernelSingletonVIA;
//import basic.zKernel.IKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class CounterHandlerSingleton_AlphanumericSignificantZZZ {
	private static CounterHandlerSingleton_AlphanumericSignificantZZZ objCounterSingleton = null; //muss static sein, wg. getInstance()!!!
	
	private HashMap<String,ICounterAlphanumericSignificantZZZ>hmCounter=new HashMap<String, ICounterAlphanumericSignificantZZZ>();
	int iStart;;
	ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;
	int iCounterLengthDefault = 4;
	char cCounterFillingDefault = 0;
	
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
			HashMap<String,ICounterAlphanumericSignificantZZZ> hmCounter = this.getCounterHashMap();
			for(String sCalling : saCalling){
				objCounter = hmCounter.get(sCalling);
				if(objCounter!=null)break ;
			}
									
			//Wenn es solch einen String nicht gibt, erzeuge neuen Counter und speichere in Hashmap unter dem aktuellen Methodennamen.
			if(objCounter==null){
				int iStart = this.getValueStart();
				ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = this.getCounterStrategy();
				CounterByCharacterAsciiFactoryZZZ objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
				objCounter = objCounterFactory.createCounter(objCounterStrategy, iStart);
				
				String sCalling = ReflectCodeZZZ.getMethodCallingName();
				this.setCounterFor(sCalling, objCounter);
			}
		}
		return objCounter;
	}
	
	private void setCounterFor(String sCalling, ICounterAlphanumericSignificantZZZ objCounter){
		this.getCounterHashMap().put(sCalling, objCounter);
	}
	
	private ICounterAlphanumericSignificantZZZ getCounterFor(String sKey){
		ICounterAlphanumericSignificantZZZ objCounter = null;
		main:{
			
		}
		return objCounter;
	}
	
	private HashMap<String,ICounterAlphanumericSignificantZZZ> getCounterHashMap(){
		return this.hmCounter;
	}
	
	public int getValueStart(){
		return this.iStart;
	}
	public void setValueStart(int iStart){
		this.iStart = iStart;
	}
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategy(){
		ICounterStrategyAlphanumericSignificantZZZ objReturn = null;
		main:{
			objReturn = this.objCounterStrategy;
			if(objReturn==null){
				objReturn = new CounterStrategyAlphanumericSignificantZZZ();
				int iLength = this.getCounterLengthDefault();
				objReturn.setCounterLength(iLength);
				char cFilling = this.getCounterFillingDefault();
				objReturn.setCounterFilling(cFilling);
			}
		}//end main:
		return objReturn;
	}
	public void setCounterStrategy(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
	
	public void setCounterLengthDefault(int iCounterLengthDefault){
		this.iCounterLengthDefault = iCounterLengthDefault;		
	}
	public int getCounterLengthDefault(){
		return this.iCounterLengthDefault;
	}
	public void setCounterFillingDefault(char cCounterFillingDefault){
		this.cCounterFillingDefault = cCounterFillingDefault;
	}
	public char getCounterFillingDefault(){
		return this.cCounterFillingDefault;
	}
}
