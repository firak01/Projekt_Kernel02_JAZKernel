package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusMessageUserZZZ;

public abstract class AbstractObjectWithStatusOnStatusListeningZZZ <T> extends AbstractObjectWithStatusZZZ implements IListenerObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;
	
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
	
	
	//### aus IStatusLocalUserZZZ
	@Override
	abstract public boolean isStatusLocalRelevant(IEnumSetMappedStatusZZZ objEnumStatusIn) throws ExceptionZZZ;

	///### aus IListenerObjectStatusLocalZZZ
	@Override
	abstract public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	@Override
	abstract public boolean isEventRelevant(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	@Override
	abstract public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	@Override
	abstract public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	@Override
	abstract public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	@Override
	abstract public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;
	
	
	
	
}
