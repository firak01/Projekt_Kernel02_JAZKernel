package basic.zBasic;

import static java.lang.System.out;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public class ObjectZZZ <T> implements Serializable, IObjectZZZ, IFlagUserZZZ{
	private static final long serialVersionUID = 1L;

	/**20130721: Erweitert um HashMap und die Enum-Flags, Compiler auf 1.6 geändert
	 * 
	 */
//Flags, die alle Z-Objekte haben
//	public enum FLAGZ{
//		DEBUG, INIT; //Verschoben nach IFlagZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Klasse erben).
//	}
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>(); //Neu 20210402
	private HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>(); //Neu 20220720
	
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public ObjectZZZ() {	
		//Darf so nicht definiert werden, da dieser Konstruktor implizit immer aufgerufen wird. 
		//wenn dieser Defaultkonstruktor nicht explizit definiert ist in der Kindklasse 
		//this.setFlag("init", true);
		//
		//Lösung dies trotzdem zu setzten:
		//rufe im Default Konstuktor der Kindklasse auf:
		//super("init");		
	}
	public ObjectZZZ(String sFlag) throws ExceptionZZZ {
		if(!StringZZZ.isEmpty(sFlag)) this.setFlag(sFlag, true);
	}
	public ObjectZZZ(String[] saFlag) {
		if(saFlag!=null){
			if(saFlag.length>=1){
				for(int icount =0; icount <= saFlag.length-1; icount++){
					if(!StringZZZ.isEmpty(saFlag[icount])){						
						this.setFlag(saFlag[icount], true);
					}
				}
			}
		}
	}
	public ObjectZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				String stemp = sKey;
				boolean btemp = this.setFlagZ(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap).", iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
			}
		}
	}
	
		
	
	//### FlagMethods ##########################
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
	 */
	public boolean getFlagZ(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	@Override
	public boolean getFlag(String sFlagName) {
		//Version Vor Java 1.6
//		boolean bFunction = false;
//	main:{
//		if(StringZZZ.isEmpty(sFlagName)) break main;
//		
//		// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//		// bFunction = super.getFlag(sFlagName);
//		// if(bFunction == true) break main;
//		
//		// Die Flags dieser Klasse setzen
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("debug")){
//			bFunction = this.bFlagDebug;
//			break main;
//		}else if(stemp.equals("init")){
//			bFunction = this.bFlagInit;
//			break main;
//		}else{
//			bFunction = false;
//		}		
//	}	// end main:
//	
//	return bFunction;	
		return this.getFlagZ(sFlagName);
	}
	@Override
	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		//Version Vor Java 1.6
//		boolean bFunction = true;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.setFlag(sFlagName, bFlagValue);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				this.bFlagDebug = bFlagValue;
//				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//				break main;
//			}else if(stemp.equals("init")){
//				this.bFlagInit = bFlagValue;
//				bFunction = true;
//				break main;
//			}else{
//				bFunction = false;
//			}	
//			
//		}	// end main:
//		
//		return bFunction;	
		try {
			return this.setFlagZ(sFlagName, bFlagValue);
		} catch (ExceptionZZZ e) {
			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
			return false;
		}
	}
	
	@Override
	public HashMap<String, Boolean>getHashMapFlagZ(){
		return this.hmFlag;
	}
		
	@Override
	public HashMap<String, Boolean> getHashMapFlagZpassed() {
		return this.hmFlagPassed;
	}
	@Override
	public void setHashMapFlagZpassed(HashMap<String, Boolean> hmFlagPassed) {
		this.hmFlagPassed = hmFlagPassed;
	}
	
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZ(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZ(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZ_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlagZ();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZ();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlagZ(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
		}
		
		public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ(bValueToSearchFor,bLookupExplizitInHashMap);
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				//ArrayList<String>listasFlagPassable=new ArrayList<String>();
				//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ();
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				ArrayList<String>listasFlagPassable=new ArrayList<String>();
				//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
		
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagZExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}

	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
		}//end main:
		return bReturn;
	}
	//##################################################
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++
			/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
			 * 	 Weteire Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
			 */
			public boolean getFlagZLocal(String sFlagName) {
				boolean bFunction = false;
				main:{
					if(StringZZZ.isEmpty(sFlagName)) break main;
												
					HashMap<String, Boolean> hmFlag = this.getHashMapFlagZLocal();
					Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
					if(objBoolean==null){
						bFunction = false;
					}else{
						bFunction = objBoolean.booleanValue();
					}
									
				}	// end main:
				
				return bFunction;	
			}
			
			/** DIESE METHODEN MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
			 * Weitere Voraussetzungen:
			 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
			 * - Innere Klassen müssen auch public deklariert werden.
			 * @param objClassParent
			 * @param sFlagName
			 * @param bFlagValue
			 * @return
			 * lindhaueradmin, 23.07.2013
			 */
			public boolean setFlagZLocal(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
				boolean bFunction = false;
				main:{
					if(StringZZZ.isEmpty(sFlagName)) {
						bFunction = true;
						break main;
					}
								
					bFunction = this.proofFlagZLocalExists(sFlagName);															
					if(bFunction == true){
						
						//Setze das Flag nun in die HashMap
						HashMap<String, Boolean> hmFlag = this.getHashMapFlagZLocal();
						hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
						bFunction = true;								
					}										
				}	// end main:
				
				return bFunction;	
			}
				
			@Override
			public HashMap<String, Boolean>getHashMapFlagZLocal(){
				return this.hmFlagLocal;
			}
			
			@Override
			public void setHashMapFlagZLocal(HashMap<String, Boolean> hmFlagLocal) {
				this.hmFlagLocal = hmFlagLocal;
			}
			
			/**Gibt alle möglichen FlagZ Werte als Array zurück. 
			 * @return
			 * @throws ExceptionZZZ 
			 */
			public String[] getFlagZLocal() throws ExceptionZZZ{
				String[] saReturn = null;
				main:{	
					saReturn = FlagZHelperZZZ.getFlagsZDirectAvailable(this.getClass());				
				}//end main:
				return saReturn;
			}
		
			/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
			 * @return
			 * @throws ExceptionZZZ 
			 */
			public String[] getFlagZLocal(boolean bValueToSearchFor) throws ExceptionZZZ{
				return this.getFlagZLocal_(bValueToSearchFor, false);
			}
			
			public String[] getFlagZLocal(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
				return this.getFlagZLocal_(bValueToSearchFor, bLookupExplizitInHashMap);
			}
			
			private String[]getFlagZLocal_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
				String[] saReturn = null;
				main:{
					ArrayList<String>listasTemp=new ArrayList<String>();
					
					//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
					//                                  Diese kann man nur durch Einzelprüfung ermitteln.
					if(bLookupExplizitInHashMap) {
						HashMap<String,Boolean>hmFlag=this.getHashMapFlagZLocal();
						if(hmFlag==null) break main;
						
						Set<String> setKey = hmFlag.keySet();
						for(String sKey : setKey){
							boolean btemp = hmFlag.get(sKey);
							if(btemp==bValueToSearchFor){
								listasTemp.add(sKey);
							}
						}
					}else {
						//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
						String[]saFlagZ = this.getFlagZLocal();
						
						//20211201:
						//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
						//         auch wenn sie garnicht gesetzt wurden.
						//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
						for(String sFlagZ : saFlagZ){
							boolean btemp = this.getFlagZLocal(sFlagZ);
							if(btemp==bValueToSearchFor ){ //also 'true'
								listasTemp.add(sFlagZ);
							}
						}
					}
					saReturn = listasTemp.toArray(new String[listasTemp.size()]);
				}//end main:
				return saReturn;
			}
			
			/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
			 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
			 * @param name 
			 * @param sFlagName
			 * @return
			 * lindhaueradmin, 23.07.2013
			 * @throws ExceptionZZZ 
			 */
			public boolean proofFlagZLocalExists(String sFlagName) throws ExceptionZZZ{
				boolean bReturn = false;
				main:{
					if(StringZZZ.isEmpty(sFlagName))break main;
					bReturn = FlagZHelperZZZ.proofFlagZDirectExists(this.getClass(), sFlagName);				
				}//end main:
				return bReturn;
			}
				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	
}
