package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusMessageUserZZZ;

public abstract class AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ extends AbstractProgramWithStatusOnStatusListeningRunnableZZZ implements IStatusLocalMapForMonitoringStatusMessageUserZZZ {
	private static final long serialVersionUID = -1445384815158662362L;
	protected volatile HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet =null;
	
	
	public AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ(String[] saFlag) throws ExceptionZZZ {
		super();	
		AbstractProgramRunnableWithStatusListeningCascadedNew_(saFlag);
	}
	
	private boolean AbstractProgramRunnableWithStatusListeningCascadedNew_(String[] saFlagControl) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(saFlagControl != null){
				String stemp; boolean btemp;
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 						
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
						
			
		}//end main:
		return bReturn;
	}

	//#### GETTER / SETTER
	@Override
	public abstract boolean start() throws ExceptionZZZ;
	
	//###################################################
	//### FLAGS #########################################
	//###################################################
	
	@Override
	public boolean getFlag(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStatusLocalMapForMonitoringStatusMessageUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	
	
	//### aus IStatusLocalMapForCascadingStatusLocalUserZZZ	
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
	
	@Override
	public abstract HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
			
}
