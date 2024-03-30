package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.StatusLocalEventHelperZZZ;

public abstract class AbstractObjectWithStatusOnStatusListeningZZZ <T> extends AbstractObjectWithStatusZZZ<Object> implements IListenerObjectStatusLocalZZZ {
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusZZZ,String> hmEnumSetForAction = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.	
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusOnStatusListeningZZZ() {	
		super();
	}
	public AbstractObjectWithStatusOnStatusListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	public AbstractObjectWithStatusOnStatusListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}
	public AbstractObjectWithStatusOnStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
	}
		
	
	//######################################################################
	//### FLAGZ: aus IListenerObjectStatusLocalZZZ                 #########
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
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
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
			if(!ArrayUtilZZZ.isEmpty(saLog)) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusOnStatusListening (" + this.getClass().getName() + ")";
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
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		return this.reactOnStatusLocalEvent4Action(eventStatusLocal);
	}
	
	@Override
	public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sLog;

			if(eventStatusLocal==null) {				 
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocal.getStatusEnum();
			if(enumStatus==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject has no EnumStatus", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				 throw ez;
			}
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigene Reaction.
		    boolean bProof = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
			if(!bProof) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusOnStatusListening ("+this.getClass().getName()+") - Zum Reagieren: KEINE gemappte Reaktion für den Status aus dem Event-Objekt ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
			
			HashMap<IEnumSetMappedStatusZZZ,String>hmEnum = this.getHashMapStatusLocal4Reaction();				
			if(hmEnum==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusOnStatusListening ("+this.getClass().getName()+") - Zum Reagieren: KEINE Mapping Hashmap fuer das StatusMapping vorhanden. Breche ab";
				this.logProtocolString(sLog);
				break main;
			}
			
			//Hole den ActionAlias
			ReferenceArrayZZZ<String>objReturnReferenceLog = new ReferenceArrayZZZ<String>();
			String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmEnum, objReturnReferenceLog);
			String[] saLog = objReturnReferenceLog.get();
			if(!ArrayUtilZZZ.isEmpty(saLog)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusOnStatusListening ("+this.getClass().getName()+") - Zum Reagieren: KEINE gemappte Reaktion für den Status aus dem Event-Objekt ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
				this.logProtocolString(sLog);
				this.logProtocolString(saLog);
			}
			
			if(StringZZZ.isEmpty(sActionAlias)) {
				sLog = "=> Event ist NICHT relevant.";
				this.logProtocolString(sLog);
				break main;
			}
			
			//Mache die Reaktion
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sStatusMessage = eventStatusLocal.getStatusMessage();
			bReturn = this.reactOnStatusLocalEvent4ActionCustom(sActionAlias, enumStatus, bStatusValue, sStatusMessage);			
		}//end main:
		return bReturn;
	}
	
	@Override
	abstract public HashMap<IEnumSetMappedStatusZZZ, String> createHashMapStatusLocal4ReactionCustom();

	
	@Override
	abstract public boolean reactOnStatusLocalEvent4ActionCustom(String sAction, IEnumSetMappedStatusZZZ enumStatus,boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	
}


