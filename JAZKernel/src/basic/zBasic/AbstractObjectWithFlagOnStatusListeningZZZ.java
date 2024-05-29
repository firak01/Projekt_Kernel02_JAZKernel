package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalUserZZZ;
import basic.zKernel.status.StatusLocalEventHelperZZZ;

public abstract class AbstractObjectWithFlagOnStatusListeningZZZ <T> extends AbstractObjectWithFlagZZZ<Object> implements IListenerObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusZZZ,String> hmEnumSetForAction = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.	
	

	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public AbstractObjectWithFlagOnStatusListeningZZZ() {	
		super();		
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractObjectWithFlagOnStatusListeningNew_();
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractObjectWithFlagOnStatusListeningNew_();
	}
	
	
	private boolean AbstractObjectWithFlagOnStatusListeningNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getFlag("init")) break main;
			
			//Merke: Das ist nicht moeglich, da ohne einen eigenen STATUS kein BrokerObjekt!!!
			//Das Programm sollte sich ggfs. am eigenen ObjectBroker registrieren.
			//Ansonsten bleibt nur die reaction4Action-Methode.
			//if(this.getFlag(IListenerObjectStatusLocalZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT)) {
			//	this.getSenderStatusLocalUsed().addListenerObject(this);
			//}
			
			bReturn = true;
		}
		return bReturn;
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super();
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
	
	//######################################################################
	//### FLAGZ: aus IListenerObjectStatusLocalZZZ                 ##########################
	//######################################################################
	@Override
	public boolean getFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	//####################
	//### STATUS
	//####################
	@Override
	public HashMap<IEnumSetMappedStatusZZZ, String> getHashMapStatusLocal4Reaction() {
		if(this.hmEnumSetForAction==null) {
			HashMap<IEnumSetMappedStatusZZZ, String> hmEnumSetForAction = this.createHashMapStatusLocal4ReactionCustom();
			this.hmEnumSetForAction = hmEnumSetForAction;
		}
		return this.hmEnumSetForAction;
	}

	@Override
	public void setHashMapStatusLocal4Reaction(HashMap<IEnumSetMappedStatusZZZ, String> hmEnumSetForAction) {
		this.hmEnumSetForAction = hmEnumSetForAction;
	}
	
			
	@Override
	public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			bReturn = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
			if(bReturn) break main;
			
			bReturn = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
			if(bReturn) break main;
									
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!this.isEventRelevant2ChangeStatusLocalByClass(eventStatusLocal)) break main;			
			if(!this.isEventRelevant2ChangeStatusLocalByStatusLocalValue(eventStatusLocal)) break main;
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalReact) throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			String sLog;
			
			//1. Hole die HashMap für die Aktionen pro reinkommenden Status.
			//   Gibt es sie nicht oder sie ist leer, wird jeder Event - unabhaengig von dem Status - weiter verfolgt.
			HashMap<IEnumSetMappedStatusZZZ,String>hmStatusLocal4Reaction= this.getHashMapStatusLocal4Reaction();
			
			//2. Static - Methode, die ueber alle Klassen gleich ist anwenden.
			//   Log-String als CallByValue Ersatzloesung mit einem Referenz-Objekt
			ReferenceArrayZZZ<String>objReferenceLog = new ReferenceArrayZZZ<String>();
			bReturn = StatusLocalEventHelperZZZ.isEventRelevant4ReactionOnStatusLocal(eventStatusLocalReact, hmStatusLocal4Reaction,objReferenceLog);
			
			String[] saLog = objReferenceLog.get();
			if(!ArrayUtilZZZ.isNull(saLog)) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + "From referenced Log:";
				this.logProtocolString(sLog);
				this.logProtocolString(saLog);
			}						
		}//end main:			
		return bReturn;
	}
	
	@Override
	public String getActionAliasString(IEnumSetMappedStatusZZZ enumStatus) {
		String sReturn = null;
		main:{
			if(enumStatus==null)break main;
						
			HashMap<IEnumSetMappedStatusZZZ,String>hm= this.getHashMapStatusLocal4Reaction();
			if(hm==null) break main;			
			if(hm.isEmpty()) break main;
			
			sReturn = hm.get(enumStatus);
		}//end main:
		return sReturn;
	}
	
	//-----------------------------------------	
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{;			
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocal.getStatusEnum();
			if(enumStatus==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject has no EnumStatus", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			//Soll reagiert werden?
//			String sStatusName = enumStatus.getName();
//			boolean bStatusValue = eventStatusLocal.getStatusValue();
			boolean bQueryReactOnStatusLocal = this.queryReactOnStatusLocalEvent(eventStatusLocal);
			if(!bQueryReactOnStatusLocal)break main;
			
			bReturn = this.reactOnStatusLocalEvent4Action(eventStatusLocal);			
		}//end main;
		return bReturn;
	}
	
	@Override
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		//s. AbstractObjectWithStatusOnStatusLirening
		boolean bReturn = false;		
		main:{
			String sLog;
			
			//+++ Pruefe, ob der ActionAlias (anhand von IEnumSetMappedStatusZZZ) in der reactionHashmapCustom vorhanden ist.
			boolean bProof = this.queryReactOnStatusLocalEventCustom(eventStatusLocal);
			if(!bProof) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"Zum Reagieren: QueryReactCustom ergibt false ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean queryReactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		//s. AbstractObjectWithStatusOnStatusLirening
		boolean bReturn = false;		
		main:{
			String sLog;
			
			//+++ Pruefe, ob der ActionAlias (anhand von IEnumSetMappedStatusZZZ) in der reactionHashmapCustom vorhanden ist.
			boolean bProof = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
			if(!bProof) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+"=> KEINE gemappte Reaktion für den Status aus dem Event-Objekt ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocal.getStatusEnum();
			if(enumStatus==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject has no EnumStatus", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			//Soll reagiert werden?
			//String sStatusName = enumStatus.getName();
			//boolean bStatusValue = eventStatusLocal.getStatusValue();
			boolean bQueryReactOn = this.queryReactOnStatusLocalEvent4Action(eventStatusLocal);
			if(!bQueryReactOn)break main;
			
			String sLog;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigene Reaction.
			HashMap<IEnumSetMappedStatusZZZ,String>hmEnum = this.getHashMapStatusLocal4Reaction();				
			if(hmEnum==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+"=> KEINE Hashmap StatusLocal4Reaction vorhanden. Breche ab";
				this.logProtocolString(sLog);
				break main;
			}
			
			//Hole den ActionAlias
			ReferenceArrayZZZ<String>objReturnReferenceLog = new ReferenceArrayZZZ<String>();
			String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmEnum, objReturnReferenceLog);
			String []saLog = objReturnReferenceLog.get();
			if(!ArrayUtilZZZ.isNull(saLog)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+ "Log aus der Ermittlung des ActionAlias folgt...)";
				this.logProtocolString(sLog);
				this.logProtocolString(saLog);
			}
			
			if(StringZZZ.isEmpty(sActionAlias)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+"=> sActionAlias ist leer. Event ist NICHT relevant. Breche ab.";
				this.logProtocolString(sLog);
				break main;
			}
			
			//Mache die Reaktion
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sStatusMessage = eventStatusLocal.getStatusMessage();
			bReturn = this.reactOnStatusLocal4Action(sActionAlias, enumStatus, bStatusValue, sStatusMessage);			
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean queryReactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.queryReactOnStatusLocal4ActionCustom(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean reactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			boolean bProof = this.queryReactOnStatusLocal4Action(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
			if(!bProof) break main;
			
			bReturn = this.reactOnStatusLocal4ActionCustom(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
		}//end main:
		return bReturn;
	}
	
	
	@Override
	abstract public HashMap<IEnumSetMappedStatusZZZ, String> createHashMapStatusLocal4ReactionCustom();

	@Override
	abstract public boolean reactOnStatusLocal4ActionCustom(String sAction, IEnumSetMappedStatusZZZ enumStatus,boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	
}
