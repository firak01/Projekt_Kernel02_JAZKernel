package basic.zBasic.util.counter;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
//import basic.zBasic.KernelSingletonVIA;
//import basic.zKernel.IKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class CounterHandlerSingleton_AlphanumericSignificantZZZ {
	private static CounterHandlerSingleton_AlphanumericSignificantZZZ objCounterSingleton = null; //muss static sein, wg. getInstance()!!!
	
	private HashMap<String,ICounterAlphanumericSignificantZZZ>hmCounter=new HashMap<String, ICounterAlphanumericSignificantZZZ>();
	ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;
	
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
	
	public ICounterAlphanumericSignificantZZZ getCounterFor() throws ExceptionZZZ{
		ICounterAlphanumericSignificantZZZ objCounter = null;
		main:{
			
			//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
			String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
						
			//Suche in der Hashmap nach einem String des Klassen.Methodennamens, der schon in der HashMap gespeichert ist.
			//Wird er gefunden, dann soll der Counter wiederverwendet werden.			
			HashMap<String,ICounterAlphanumericSignificantZZZ> hmCounter = this.getCounterHashMap();
			for(String sCallingTemp : saCalling){
				objCounter = hmCounter.get(sCallingTemp);
				if(objCounter!=null)break ;
			}
									
			//Wenn es solch einen String nicht gibt, erzeuge neuen Counter und speichere in Hashmap unter dem aktuellen Methodennamen.
			if(objCounter==null){
				CounterByCharacterAsciiFactoryZZZ objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
				
				ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = this.getCounterStrategy();//Merke: Wenn die CounterStrategy neu erstellt wird, dann wird die ExterneInitialisierungsmethode über den StackTrace geholt.								
				int iStart = objCounterStrategy.getCounterStart();

				objCounter = objCounterFactory.createCounter(objCounterStrategy, iStart);
				
				//Ermittle die Aufrufende Methode. Alle gültig..
				//String sCalling = ReflectCodeKernelZZZ.getClassMethodExternalCallingString();
				//String sCalling = saCalling[saCalling.length-1]; //die vorherige Methode ist im StackTrace die mit dem höchsten Index
				//... aber das erscheint mir am sichersten...
				String sCalling = objCounterStrategy.getClassMethodCallingString();
				this.setCounterFor(sCalling, objCounter);
			}
		}
		return objCounter;
	}
	
	public void setCounterFor(String sCalling, ICounterAlphanumericSignificantZZZ objCounter){
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
		
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategy() throws ExceptionZZZ{
		ICounterStrategyAlphanumericSignificantZZZ objReturn = null;
		main:{
			objReturn = this.objCounterStrategy;
			if(objReturn==null){
				objReturn = new CounterStrategyAlphanumericSignificantZZZ();
				
				//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
				String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
				//wichtig: Momentan ist die getCounterStrategy() Methode die Initialisierungsmethode.  Diese aber überschreiben..
				objReturn.setClassMethodCallingString(saCalling[saCalling.length-1]);//die vorherige Methode ist im StackTrace die mit dem höchsten Index
				
			}
		}//end main:
		return objReturn;
	}
	public void setCounterStrategy(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
	

}
