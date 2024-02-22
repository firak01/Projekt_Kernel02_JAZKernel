package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_NullZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

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
		public HashMap<IEnumSetMappedStatusZZZ, String> getHashMapStatusLocalReaction() {
			if(this.hmEnumSetForAction==null) {
				HashMap<IEnumSetMappedStatusZZZ, String> hmEnumSetForAction = this.createHashMapStatusLocalReactionCustom();
				this.hmEnumSetForAction = hmEnumSetForAction;
			}
			return this.hmEnumSetForAction;
		}

		@Override
		public void setHashMapStatusLocalReaction(HashMap<IEnumSetMappedStatusZZZ, String> hmEnumSetForAction) {
			this.hmEnumSetForAction = hmEnumSetForAction;
		}
		
		@Override
		abstract public HashMap<IEnumSetMappedStatusZZZ, String> createHashMapStatusLocalReactionCustom();

		
		@Override
		public boolean isEventRelevant(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				
				bReturn = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
				if(bReturn) break main;
										
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(!this.isEventRelevantByClass2ChangeStatusLocal(eventStatusLocal)) break main;
				if(!this.isEventRelevantByStatusLocal2ChangeStatusLocal(eventStatusLocal)) break main;
				if(!this.isEventRelevantByStatusLocalValue2ChangeStatusLocal(eventStatusLocal)) break main;
				
				bReturn = true;
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalReact) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
				
				//1. Hole die HashMap für die Aktionen pro reinkommenden Status.
				//   Gibt es sie nicht oder sie ist leer, wird jeder Event - unabhaengig von dem Status - weiter verfolgt.
				HashMap<IEnumSetMappedStatusZZZ,String>hm= this.getHashMapStatusLocalReaction();
				if(hm==null) break main;			
				if(hm.isEmpty()) break main;
				
				/* muss man instanceof verwenden???		
				IEnumSetMappedStatusZZZ objEnumMapped = eventStatusLocalReact.getStatusLocal();					
				if(objEnumMapped instanceof IProgramMonitorZZZ.STATUSLOCAL) {
					if(objEnumMapped.equals(IProgramMonitorZZZ.STATUSLOCAL.ISSTARTING)) {
		
					}
				}
				*/
				
				IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocalReact.getStatusEnum();			
				String sActionAlias = hm.get(enumStatus);
				
				//Status nicht vorhanden oder ActionAlias NULL => NICHT relevant 
				if(sActionAlias==null) {
					bReturn = false;
					break main;
				}
				if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) {
					bReturn = false;
					break main;
				}
									
				if(StringZZZ.isEmptyTrimmed(sActionAlias)) break main;
				if(StringZZZ.equalsIgnoreCase(sActionAlias,KernelZFormulaIni_EmptyZZZ.getExpressionTagEmpty())) break main;
				
				bReturn = false;
			}//end main:			
			return bReturn;
		}
		
		@Override
		public String getActionAliasString(IEnumSetMappedStatusZZZ enumStatus) {
			String sReturn = null;
			main:{
				if(enumStatus==null)break main;
							
				HashMap<IEnumSetMappedStatusZZZ,String>hm= this.getHashMapStatusLocalReaction();
				if(hm==null) break main;			
				if(hm.isEmpty()) break main;
				
				sReturn = hm.get(enumStatus);
			}//end main:
			return sReturn;
		}
}
