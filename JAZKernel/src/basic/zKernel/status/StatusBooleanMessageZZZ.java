package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public class StatusBooleanMessageZZZ  extends StatusBooleanZZZ implements IStatusBooleanMessageZZZ{		 
	private String sMessage = null;
	
	public StatusBooleanMessageZZZ() {		
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedZZZ objEnum) {
		super(objEnum);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedZZZ objEnum, boolean bValue) {
		super(objEnum,bValue);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedZZZ objEnum, boolean bValue, String sMessage) {
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

