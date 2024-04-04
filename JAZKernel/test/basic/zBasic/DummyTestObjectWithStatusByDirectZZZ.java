package basic.zBasic;

import java.util.EnumSet;
import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class DummyTestObjectWithStatusByDirectZZZ extends AbstractObjectWithStatusZZZ<Object> implements IDummyTestObjectWithStatusByDirectZZZ{
	private static final long serialVersionUID = -3077811336052403537L;

	public DummyTestObjectWithStatusByDirectZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestObjectWithStatusByDirectZZZ() {
		super();
	}
	
	//#######################################################################################
	// STATUS: HIER DIRECT eingebunden	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Die StatusId für Stati, aus dieser Klasse selbst. Nicht die Stati der anderen Klassen.
	//Sollte dann irgendwie einzigartig sein.
	public static int iSTATUSLOCAL_GROUPID=1;
		
	//++++++++++++++++++++++++
	//ALIAS(Gruppenid der Meldung, "Uniquename","Statusmeldung","Beschreibung, wird nicht genutzt....",)
	public enum STATUSLOCAL implements IEnumSetMappedStatusZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		ISSTARTNEW(iSTATUSLOCAL_GROUPID,"isstartnew","ZZZ: DummyTestObjectWithStatusZZZ nicht gestartet",""),
		ISSTARTING(iSTATUSLOCAL_GROUPID,"isstarting","ZZZ: DummyTestObjectWithStatusZZZ startet...",""),		
		ISSTARTED(iSTATUSLOCAL_GROUPID,"isstarted","ZZZ: DummyTestObjectWithStatusZZZ gestartet",""),
		ISSTARTNO(iSTATUSLOCAL_GROUPID,"isstartno","ZZZ: DummyTestObjectWithStatusZZZ nicht gestartet",""),

		ISSTOPPED(iSTATUSLOCAL_GROUPID,"isstopped","ZZZ: DummyTestObjectWithStatusZZZ beendet",""),
				
		HASERROR(iSTATUSLOCAL_GROUPID,"haserror","ZZZ: DummyTestObjectWithStatusZZZ meldet Fehler","");		
		
		private int iStatusGroupId;
		private String sAbbreviation,sStatusMessage,sDescription;
	
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
			this.iStatusGroupId = iStatusGroupId;
		    this.sAbbreviation = sAbbreviation;
		    this.sStatusMessage = sStatusMessage;
		    this.sDescription = sDescription;
		}
	
		public int getStatusGroupId() {
			return this.iStatusGroupId;
		}
		
		public String getAbbreviation() {
		 return this.sAbbreviation;
		}
		
		public String getStatusMessage() {
			 return this.sStatusMessage;
		}
		
		public EnumSet<?>getEnumSetUsed(){
			return STATUSLOCAL.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<STATUSLOCAL> enumClass = STATUSLOCAL.class;
			EnumSet<STATUSLOCAL> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
					
			Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((STATUSLOCAL) obj);
			}
			return set;
			
		}
	
		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
			  EnumSet<E> set=EnumSet.noneOf(enumClass);
			  long mask=1;
			  for (  E e : enumClass.getEnumConstants()) {
			    if ((mask & vector) == mask) {
			      set.add(e);
			    }
			    mask<<=1;
			  }
			  return set;
			}
	
		//+++ Das könnte auch in einer Utility-Klasse sein.
		//the valueOfMethod <--- Translating from DB
		public static STATUSLOCAL fromAbbreviation(String s) {
		for (STATUSLOCAL state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}
	
		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}
	
		@Override
		public String toString() {//Mehrere Werte mit # abtennen
		    return this.sAbbreviation+"="+this.sDescription;
		}
	
		@Override
		public int getIndex() {
			return ordinal();
		}
	
		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}
	
		@Override
		public String getDescription() {
			return this.sDescription;
		}
		//+++++++++++++++++++++++++
	}//End internal Class
	//##### END STATUS DIRECT eingebunden #######################################
	
	
	//###################################################
	//### FLAGS #########################################
	//###################################################
		
	@Override
	public boolean getFlag(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IDummyTestObjectWithStatusByDirectZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//####################################
		//### STATUS: ILogFileWatchMonitorZZZ
		//####################################

		//####### aus IStatusLocalUserZZZ
		@Override
		public boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(objEnumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) objEnumStatusIn;
				String sStatusName = enumStatus.name();
				if(StringZZZ.isEmpty(sStatusName)) break main;
											
				HashMap<String, Boolean> hmFlag = this.getHashMapStatusLocal();
				Boolean objBoolean = hmFlag.get(sStatusName.toUpperCase());
				if(objBoolean==null){
					bFunction = false;
				}else{
					bFunction = objBoolean.booleanValue();
				}
								
			}	// end main:
			
			return bFunction;	
		}

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//+++ OFFER STATUS LOCAL, alle Varianten, gecasted auf dieses Objekt
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		@Override
		public boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(enumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				
				bFunction = this.offerStatusLocal_(enumStatus, "", bStatusValue);				
			}//end main;
			return bFunction;
		}
		
		
		@Override
		public boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(enumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				
				bFunction = this.offerStatusLocal_(enumStatus, sStatusMessage, bStatusValue);				
			}//end main;
			return bFunction;
		}
		
		private boolean offerStatusLocal_(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(enumStatusIn==null) break main;
				
			
				//Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				String sStatusName = enumStatus.name();
				bFunction = this.proofStatusLocalExists(sStatusName);															
				if(!bFunction) {
					String sLog = ReflectCodeZZZ.getPositionCurrent() + "Would like to fire event, but this status is not available: '" + sStatusName + "'";
					this.logProtocolString(sLog);			
					break main;
				}
				
			bFunction = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
			if(!bFunction) {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + "Would like to fire event, but this status has not changed: '" + sStatusName + "'";
				this.logProtocolString(sLog);
				break main;
			}	
			
			//++++++++++++++++++++	
			//Setze den Status nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
			
			//Den enumStatus als currentStatus im Objekt speichern...
			//                   dito mit dem "vorherigen Status"
			//Setze nun das Enum, und damit auch die Default-StatusMessage
			String sStatusMessageToSet = null;
			if(StringZZZ.isEmpty(sStatusMessage)){
				if(bStatusValue) {
					sStatusMessageToSet = enumStatus.getStatusMessage();
				}else {
					sStatusMessageToSet = "NICHT " + enumStatus.getStatusMessage();
				}			
			}else {
				sStatusMessageToSet = sStatusMessage;
			}
			
			String sLog = ReflectCodeZZZ.getPositionCurrent() + "Verarbeite sStatusMessageToSet='" + sStatusMessageToSet + "'";
			this.logProtocolString(sLog);

			//Falls eine Message extra uebergeben worden ist, ueberschreibe...
			if(sStatusMessageToSet!=null) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + "Setze sStatusMessageToSet='" + sStatusMessageToSet + "'";
				this.logProtocolString(sLog);
			}
			//Merke: Dabei wird die uebergebene Message in den speziellen "Ringspeicher" geschrieben, auch NULL Werte...
			this.offerStatusLocalEnum(enumStatus, bStatusValue, sStatusMessageToSet);
			
			
			
			//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
			//Dann erzeuge den Event und feuer ihn ab.	
			if(this.getSenderStatusLocalUsed()==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + "Would like to fire event '" + enumStatus.getAbbreviation() + "', but no objEventStatusLocalBroker available, any registered?";
				this.logProtocolString(sLog);		
				break main;
			}
			
			//Erzeuge fuer das Enum einen eigenen Event. Die daran registrierten Klassen koennen in einer HashMap definieren, ob der Event fuer sie interessant ist.		
			sLog = ReflectCodeZZZ.getPositionCurrent() + "Erzeuge Event fuer '" + sStatusName + "'";		
			this.logProtocolString(sLog);
			IEventObjectStatusLocalZZZ event = new EventObjectStatusLocalZZZ(this,enumStatus, bStatusValue);			
			
			//### GGFS. noch weitere benoetigte Objekte hinzufuegen............
			//...
			
					
			//Feuere den Event ueber den Broker ab.
			sLog = ReflectCodeZZZ.getPositionCurrent() + "Fires event '" + enumStatus.getAbbreviation() + "'";
			this.logProtocolString(sLog);
			this.getSenderStatusLocalUsed().fireEvent(event);
					
			bFunction = true;				
		}	// end main:
		return bFunction;
		}
		
		
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//+++ SET STATUS LOCAL, alle Varianten, gecasted auf dieses Objekt
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(enumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
								
				bFunction = this.offerStatusLocal(enumStatus, null, bStatusValue);
			}//end main:
			return bFunction;
		}
			
		/* (non-Javadoc)
		 * @see basic.zBasic.AbstractObjectWithStatusZZZ#setStatusLocalEnum(basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ, boolean)
		 */
		@Override 
		public boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(enumStatusIn==null) break main;

				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				
				bReturn = this.offerStatusLocal(enumStatus, null, bStatusValue);
			}//end main:
			return bReturn;
		}
		
		//+++ aus IStatusLocalUserMessageZZZ			
		@Override 
		public boolean setStatusLocal(Enum enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(enumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				
				bFunction = this.offerStatusLocal(enumStatus, sMessage, bStatusValue);
			}//end main:
			return bFunction;
		}
			
		@Override 
		public boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(enumStatusIn==null) break main;
				
				IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL enumStatus = (IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL) enumStatusIn;
				
				bReturn = this.offerStatusLocal(enumStatus, sMessage, bStatusValue);
			}//end main:
			return bReturn;
		}				
				
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

}
