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

import base.util.abstractArray.ArrayUtil;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.flag.EventObjectFlagZsetZZZ;
import basic.zKernel.flag.IEventBrokerFlagZsetUserZZZ;
import basic.zKernel.flag.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.ISenderObjectFlagZsetZZZ;
import basic.zKernel.flag.KernelSenderObjectFlagZsetZZZ;
import basic.zKernel.flag.json.FlagZHelperZZZ;
import basic.zKernel.flag.json.IFlagZZZ;
import custom.zKernel.LogZZZ;

public class ObjectZZZ <T> implements Serializable, IObjectZZZ, ILogZZZ, IFlagZUserZZZ, IEventBrokerFlagZsetUserZZZ, IFlagZLocalUserZZZ{
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
	
	protected ISenderObjectFlagZsetZZZ objEventFlagZBroker = null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines Flags per Event informiert zu werden.
	
	
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
	public ObjectZZZ(String[] saFlag) throws ExceptionZZZ {
		if(saFlag!=null){
			if(saFlag.length>=1){
				for(int icount =0; icount <= saFlag.length-1; icount++){
					if(!StringZZZ.isEmpty(saFlag[icount])){						
						boolean bFound = this.setFlag(saFlag[icount], true);
						if(!bFound) System.out.println(ReflectCodeZZZ.getPositionCurrent()+" - Flag not available: '" + saFlag[icount] +"'");
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
				boolean btemp = this.setFlag(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap).", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
			}
		}
	}
	
		
	
	//### Aus IFlagUserZZZ ##########################
	@Override
	public boolean getFlag(IFlagZUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IFlagZUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IFlagZUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFlagZUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IFlagZUserZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}	
	
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen muessen auch public deklariert werden.(non-Javadoc)
	 */
	public boolean getFlag(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
//	@Override
//	public boolean getFlag(String sFlagName) {
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
//		return this.getFlag(sFlagName);
//	}
//	@Override
//	public boolean setFlag(String sFlagName, boolean bFlagValue) {
//		//Version Vor Java 1.6
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
//		try {
//			return this.setFlag(sFlagName, bFlagValue);
//		} catch (ExceptionZZZ e) {
//			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
//			return false;
//		}
//	}
	
	
	@Override
	public boolean[] setFlag(String[] saFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saFlagName)) {
				baReturn = new boolean[saFlagName.length];
				int iCounter=-1;
				for(String sFlagName:saFlagName) {
					iCounter++;
					boolean bReturn = this.setFlag(sFlagName, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	
	@Override
	public HashMap<String, Boolean>getHashMapFlag(){
		return this.hmFlag;
	}
		
	@Override
	public HashMap<String, Boolean> getHashMapFlagPassed() {
		return this.hmFlagPassed;
	}
	@Override
	public void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed) {
		this.hmFlagPassed = hmFlagPassed;
	}
	
	/**Gibt alle möglichen FlagZ Werte als Array zurück. 
	 * @return
	 * @throws ExceptionZZZ 
	 */
	@Override
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
	@Override
	public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
		return this.getFlagZ_(bValueToSearchFor, false);
	}
		
	@Override
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
				HashMap<String,Boolean>hmFlag=this.getHashMapFlag();
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
					boolean btemp = this.getFlag(sFlagZ);
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
	@Override
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
		return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
	}
	
	@Override
	public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
		return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
	}
		
	private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			
			//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
			String[] saFlag = this.getFlagZ(bValueToSearchFor,bLookupExplizitInHashMap);
			
			//2. Hole alle FlagZ der Zielklasse
			String[] saFlagTarget = objUsingFlagZ.getFlagZ();
			
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
	@Override
	public String[] getFlagZ_passable(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
		return this.getFlagZ_passable_(objUsingFlagZ);
	}
		
	private String[] getFlagZ_passable_(IFlagZUserZZZ objUsingFlagZ) throws ExceptionZZZ{
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
	@Override
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);								
				
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				if(this.objEventFlagZBroker!=null) {
					IEventObjectFlagZsetZZZ event = new EventObjectFlagZsetZZZ(this,1,sFlagName.toUpperCase(), bFlagValue);
					this.objEventFlagZBroker.fireEvent(event);
				}
				
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
	@Override
	public boolean proofFlagExists(String sFlagName) throws ExceptionZZZ{
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
	@Override
	public boolean getFlagLocal(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
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
	@Override
	public boolean setFlagLocal(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagLocalExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
			
	@Override
	public boolean[] setFlagLocal(String[] saFlag, boolean bValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saFlag)) {
				baReturn = new boolean[saFlag.length];
				int iCounter=-1;
				for(String sFlag:saFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(sFlag, bValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
				
	@Override
	public HashMap<String, Boolean>getHashMapFlagLocal(){
		return this.hmFlagLocal;
	}
	
	@Override
	public void setHashMapFlagLocal(HashMap<String, Boolean> hmFlagLocal) {
		this.hmFlagLocal = hmFlagLocal;
	}
	
	/**Gibt alle möglichen FlagZ Werte als Array zurück. 
	 * @return
	 * @throws ExceptionZZZ 
	 */
	@Override
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
	@Override
	public String[] getFlagZLocal(boolean bValueToSearchFor) throws ExceptionZZZ{
		return this.getFlagZLocal_(bValueToSearchFor, false);
	}
		
	@Override
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
				HashMap<String,Boolean>hmFlag=this.getHashMapFlagLocal();
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
					boolean btemp = this.getFlagLocal(sFlagZ);
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
	@Override
	public boolean proofFlagLocalExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZDirectExists(this.getClass(), sFlagName);				
		}//end main:
		return bReturn;
	}
				
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### Aus dem Interface IEventBrokerFlagZUserZZZ
	@Override
	public ISenderObjectFlagZsetZZZ getSenderFlagUsed() throws ExceptionZZZ {
		if(this.objEventFlagZBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender fuer Aenderungen an den Flags zu erstellen, der dann registrierte Objekte ueber Aenderung von Flags informiert
			ISenderObjectFlagZsetZZZ objSenderFlag = new KernelSenderObjectFlagZsetZZZ();
			this.objEventFlagZBroker = objSenderFlag;
		}
		return this.objEventFlagZBroker;
	}

	@Override
	public void setSenderFlagUsed(ISenderObjectFlagZsetZZZ objSenderFlag) {
		this.objEventFlagZBroker = objSenderFlag;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.flag.IEventBrokerFlagZsetUserZZZ#registerForFlagEvent(basic.zKernel.flag.IListenerObjectFlagZsetZZZ)
	 */
	@Override
	public void registerForFlagEvent(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderFlagUsed().addListenerObjectFlagZset(objEventListener);
	}
	
	@Override
	public void unregisterForFlagEvent(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderFlagUsed().removeListenerObjectFlagZset(objEventListener);
	}
	
	
	@Override
	public boolean setFlag(Enum enumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumFlag==null) {
				break main;
			}
			
			String sFlagName = enumFlag.name();
			//Nein, trotz der Redundanz nicht machen, da nun der Event anders gefeuert wird, nämlich über das enum
			//bFunction = this.setFlag(sFlagName, bFlagValue);	
			
			bFunction = this.proofFlagExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
			
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				//Merke: Nun aber ueber das enum			
				if(this.objEventFlagZBroker!=null) {
					IEventObjectFlagZsetZZZ event = new EventObjectFlagZsetZZZ(this,1,enumFlag, bFlagValue);
					this.objEventFlagZBroker.fireEvent(event);
				}
				
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;
	}
	
	//++++++++++++++++++++++++

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		String sTemp = KernelLogZZZ.computeLineDate(sLog);
		System.out.println(sTemp);		
	}
	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
}
