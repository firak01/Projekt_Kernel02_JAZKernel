package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public class StatusBooleanZZZ  implements IStatusBooleanZZZ{		
	private IEnumSetMappedStatusZZZ enumStatus = null; 
	private boolean bValue = false;
	
	public StatusBooleanZZZ() {		
	}
	
	public StatusBooleanZZZ(IEnumSetMappedStatusZZZ objEnum) {
		this.enumStatus = objEnum;
		this.bValue = true;
	}
	
	public StatusBooleanZZZ(IEnumSetMappedStatusZZZ objEnum, boolean bValue) {
		this.enumStatus = objEnum;
		this.bValue = bValue;
	}
	
	@Override
	public IEnumSetMappedStatusZZZ getEnumObject(){
		return this.enumStatus;
	}
	
	@Override
	public void setEnumObject(IEnumSetMappedStatusZZZ objEnum) {
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

