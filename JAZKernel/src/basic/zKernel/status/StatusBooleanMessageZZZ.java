package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public class StatusBooleanMessageZZZ  extends StatusBooleanZZZ implements IStatusBooleanMessageZZZ{		 
	private String sMessage = null;
	
	public StatusBooleanMessageZZZ() {		
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusZZZ objEnum) {
		super(objEnum);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusZZZ objEnum, boolean bValue) {
		super(objEnum,bValue);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusZZZ objEnum, boolean bValue, String sMessage) {
		this(objEnum,bValue);
		this.setMessage(sMessage);
	}
	@Override
	public String getMessage() {
		return this.sMessage;
	}

	@Override
	public void setMessage(String sMessage) {
		this.sMessage = sMessage;
	}
	
}

