package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;

public abstract class AbstractProgramRunnableWithStatusMessageListeningMonitoredZZZ extends AbstractProgramRunnableWithStatusMessageListeningZZZ implements IStatusLocalMapForMonitoringStatusLocalUserZZZ {
	private static final long serialVersionUID = -1445384815158662362L;
	protected volatile HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet =null;
	
	
	public AbstractProgramRunnableWithStatusMessageListeningMonitoredZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramRunnableWithStatusMessageListeningMonitoredZZZ(String[] saFlag) throws ExceptionZZZ {
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
