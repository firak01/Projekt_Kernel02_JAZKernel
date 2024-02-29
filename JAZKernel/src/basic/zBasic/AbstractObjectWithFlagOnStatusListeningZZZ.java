package basic.zBasic;

import java.util.HashMap;
import java.util.Set;

import basic.zBasic.component.IProgramMonitorZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

public abstract class AbstractObjectWithFlagOnStatusListeningZZZ <T> extends AbstractObjectWithFlagZZZ<Object> implements IListenerObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusZZZ,String> hmEnumSetForAction = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.	
	

	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public AbstractObjectWithFlagOnStatusListeningZZZ() {	
		super();		
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String sFlag) throws ExceptionZZZ {
		super();
		if(!StringZZZ.isEmpty(sFlag)) this.setFlag(sFlag, true);
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super();
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
			HashMap<IEnumSetMappedStatusZZZ,String>hm= this.getHashMapStatusLocal4Reaction();
			if(hm==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ReactionHashMap NULL => jeder Status ist relevant.";				
				this.logProtocolString(sLog);
				break main;			
			}
			if(hm.isEmpty()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ReactionHashMap Empty => jeder Status ist relevant.";				
				this.logProtocolString(sLog);
				break main;	
			}
						
			IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocalReact.getStatusEnum();			
			String sActionAlias = hm.get(enumStatus);
						
			if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ReactionHashMap hat "+ KernelZFormulaIni_NullZZZ.getExpressionTagEmpty() + " fuer den Status '" + enumStatus.getName() + "'=> ist relevant.";				
				this.logProtocolString(sLog);
				break main;
			}
			
			//Status nicht gepflegt, wenn durchaus andere Statuseintraege vorhanden sind oder gepflegt und ActionAlias Leer => NICHT relevant 
			if(StringZZZ.isEmptyTrimmed(sActionAlias)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ReactionHashMap hat Leerstring fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";				
				this.logProtocolString(sLog);
				bReturn = false;
				break main;
			}
			if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ReactionHashMap hat "+ KernelZFormulaIni_NullZZZ.getExpressionTagEmpty() + " fuer den Status '" + enumStatus.getName() + "'=> ist NICHT relevant.";				
				this.logProtocolString(sLog);
				bReturn = false;
				break main;
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
			//Falls nicht zuständig, mache nix
		    boolean bProof = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
			if(!bProof) break main;
			
			String sLog=null;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
			IEnumSetMappedStatusZZZ enumStatus = eventStatusLocal.getStatusLocal();
			if(enumStatus==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Keinen Status aus dem Event-Objekt erhalten. Breche ab";				
				this.logProtocolString(sLog);
				break main;
			}
			
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sStatusMessage = eventStatusLocal.getStatusMessage();
			
			//+++++++++++++++++++++
			HashMap<IEnumSetMappedStatusZZZ,String>hmEnum = this.getHashMapStatusLocal4Reaction();				
			if(hmEnum==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Keine Mapping Hashmap fuer das StatusMapping vorhanden. Breche ab";
				System.out.println(sLog);
				this.logLineDate(sLog);
				break main;
			}
						
			String sActionAlias = this.getActionAliasString(enumStatus);
			
			//+++++++++++++++++++++
			bReturn = this.reactOnStatusLocalEventCustomAction(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
		
		}//end main:
		return bReturn;
	}
	
	@Override
	abstract public HashMap<IEnumSetMappedStatusZZZ, String> createHashMapStatusLocal4ReactionCustom();

	@Override
	abstract public boolean reactOnStatusLocalEventCustomAction(String sAction, IEnumSetMappedStatusZZZ enumStatus,boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
	
}
