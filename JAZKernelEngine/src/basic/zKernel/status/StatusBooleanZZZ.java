package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;

public class StatusBooleanZZZ  implements IStatusBooleanZZZ{		
	private IEnumSetMappedStatusLocalZZZ enumStatus = null; 
	private boolean bValue = false;
	
	public StatusBooleanZZZ() {		
	}
	
	public StatusBooleanZZZ(IEnumSetMappedStatusLocalZZZ objEnum) {
		this.enumStatus = objEnum;
		this.bValue = true;
	}
	
	public StatusBooleanZZZ(IEnumSetMappedStatusLocalZZZ objEnum, boolean bValue) {
		this.enumStatus = objEnum;
		this.bValue = bValue;
	}
	
	@Override
	public IEnumSetMappedStatusLocalZZZ getEnumObject(){
		return this.enumStatus;
	}
	
	@Override
	public void setEnumObject(IEnumSetMappedStatusLocalZZZ objEnum) {
		this.enumStatus = objEnum;
	}
	
	@Override
	public boolean getValue() {
		return this.bValue;
	}

	@Override
	public void setValue(boolean bValue) {
		this.bValue = bValue; 
		
	}
	
}

