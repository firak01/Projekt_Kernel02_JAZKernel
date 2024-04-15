package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.IMonitorObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusBooleanZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;
import basic.zKernel.status.IStatusLocalUserZZZ;
import basic.zKernel.status.StatusBooleanZZZ;
import basic.zKernel.status.StatusLocalAvailableHelperZZZ;

public abstract class AbstractObjectWithStatusMonitoringZZZ <T> extends AbstractObjectWithStatusOnStatusListeningZZZ implements IMonitorObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> hmEnumSet = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.

	
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusMonitoringZZZ() {	
		super();
	}
	public AbstractObjectWithStatusMonitoringZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	public AbstractObjectWithStatusMonitoringZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}
	public AbstractObjectWithStatusMonitoringZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
	}
	
	
	//### aus IStatusLocalMapForMonitoringStatusMessageUserZZZ	
	@Override
	public HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> getHashMapEnumSetForCascadingStatusLocal() {
		if(this.hmEnumSet==null) {
			this.hmEnumSet = this.createHashMapEnumSetForCascadingStatusLocalCustom();
		}
		return this.hmEnumSet;
	}
	
	@Override
	public void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet) {
		this.hmEnumSet = hmEnumSet;
	}
	
	//---------- der Monitor erweitert dies um reactOnStatusLocalEvent4Monitor ....
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{	
		boolean bReturn;
		
		//1. Monitor: D.h. einen Status weiterleiten. Das muss zuerst passieren.
		bReturn = this.reactOnStatusLocalEvent4Monitor(eventStatusLocal);
		
		//falls ein Status weitergeleitet wurde, kann der Event ja nicht von der Monitor-Klasse selbst kommen.
		//in dem Fall warten wir darauf, dass der Weitergeleitete Event (diesmal von der Monitor-Klasse selbst) hier eintrifft.
		//WICHTIG: TROTZDEM DIE REACTION AUSFÜHREN .... if(!bReturn) {
		//MERKE:   Da der Monitor sich immer an sich selbst registriert, kann man dann hier auch die neu geworfenen STATUS hinzufuegen.
		
			String sLog =  ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - Ohne gemappten Status: Rufe CustomReaktionsmethode auf (reactOnStatusLocalEvent4Action)";
			this.logProtocolString(sLog);
			
			//	2. Eigene Action... das hat das Ziel, das dadurch ja ggfs. wieder neue Events geworfen werden können
			bReturn = this.reactOnStatusLocalEvent4Action(eventStatusLocal);
		//}
		
		return bReturn;
	}
	
	@Override
	public boolean reactOnStatusLocalEvent4Monitor(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {
				  ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject not provided", this.iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 						
				  throw ez;
			}
			
			//Merke: Das Nachsehen in der HashMap ist ja quasi eine Relevanzpruefung. Darum an dieser Stelle nicht noch extra pruefen.
			//Falls nicht zuständig, setze keinen Status
			//boolean bProofStatus = this.isEventRelevant4MonitorOnStatusLocal(eventStatusLocal);
			//if(!bProofStatus) break main;
			
			
			String sLog=null;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
			IEnumSetMappedStatusZZZ enumStatusIn = eventStatusLocal.getStatusLocal();
			if(enumStatusIn==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - Keinen Status aus dem Event-Objekt erhalten. Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
			
			HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmStatus=this.getHashMapEnumSetForCascadingStatusLocal();
			IEnumSetMappedStatusZZZ enumStatusOut = hmStatus.get(enumStatusIn); 
			if(enumStatusOut==null) {
				sLog =  ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - KEINEN Gemappten Status gefunden. Setze also keinen eigenen Status.";
				this.logProtocolString(sLog);
				break main; //Wenn der Status nicht gemappt ist, wird auch nichts gesetzt.
			}else {			
				sLog =  ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - Gemappten Status gefunden... Setze dazu den passenden eigenen Status.";
				this.logProtocolString(sLog);
			}
			
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sStatusMessage = eventStatusLocal.getStatusMessage();
			this.setStatusLocalEnum(enumStatusOut, sStatusMessage, bStatusValue);
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean isEventRelevant4MonitorOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {
				  ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject not provided", this.iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 						
				  throw ez;
			}
			
			String sLog;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
			IEnumSetMappedStatusZZZ enumStatusIn = eventStatusLocal.getStatusLocal();
			if(enumStatusIn==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - Keinen Status aus dem Event-Objekt erhalten. Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
			
			HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmStatus=this.getHashMapEnumSetForCascadingStatusLocal();
			IEnumSetMappedStatusZZZ enumStatusOut = hmStatus.get(enumStatusIn); 
			if(enumStatusOut==null) {
				sLog =  ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - KEINEN Gemappten Status gefunden. Also Event NICHT mit Monitor-Objekt weiter verarbeitbar.";
				this.logProtocolString(sLog);
				break main; //Wenn der Status nicht gemappt ist, wird auch nichts gesetzt.
			}else {			
				sLog =  ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring (" + this.getClass().getName() + ") - Gemappten Status gefunden. Also Event mit Monitor-Objekt weiter verarbeitbar.";
				this.logProtocolString(sLog);				
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ#createHashMapEnumSetForCascadingStatusLocalCustom()
	 */
	@Override
	public abstract HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
	
	
	
	//######################################################################
	//### FLAGZ: aus IStatusLocalMapForMonitoringStatusMessageUserZZZ   ####
	//######################################################################
	@Override
	public boolean getFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	
	//######################################################################
	//### FLAGZ: aus IStatusLocalMapForMonitoringStatusMessageUserZZZ   ####
	//######################################################################
	@Override
	public boolean getFlag(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IMonitorObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}		
}
