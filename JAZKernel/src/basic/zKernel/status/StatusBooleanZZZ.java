package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public class StatusBooleanZZZ  implements IStatusBooleanZZZ{		
	private IEnumSetMappedZZZ enumStatus = null; 
	private boolean bValue = false;
	
	public StatusBooleanZZZ() {		
	}
	
	public StatusBooleanZZZ(IEnumSetMappedZZZ objEnum) {
		this.enumStatus = objEnum;
		this.bValue = true;
	}
	
	public StatusBooleanZZZ(IEnumSetMappedZZZ objEnum, boolean bValue) {
		this.enumStatus = objEnum;
		this.bValue = bValue;
	}
	
	@Override
	public IEnumSetMappedZZZ getEnumObject(){
		return this.enumStatus;
	}
	
	@Override
	public void setEnumObject(IEnumSetMappedZZZ objEnum) {
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

