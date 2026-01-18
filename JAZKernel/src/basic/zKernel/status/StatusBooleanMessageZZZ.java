package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;

public class StatusBooleanMessageZZZ  extends StatusBooleanZZZ implements IStatusBooleanMessageZZZ{		 
	private String sMessage = null;
	
	public StatusBooleanMessageZZZ() {		
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusLocalZZZ objEnum) {
		super(objEnum);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusLocalZZZ objEnum, boolean bValue) {
		super(objEnum,bValue);
	}
	
	public StatusBooleanMessageZZZ(IEnumSetMappedStatusLocalZZZ objEnum, boolean bValue, String sMessage) {
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

