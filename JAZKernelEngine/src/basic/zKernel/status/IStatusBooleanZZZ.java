package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;

public interface IStatusBooleanZZZ {
	public IEnumSetMappedStatusLocalZZZ getEnumObject();
	public void setEnumObject(IEnumSetMappedStatusLocalZZZ objEnum);	
	public boolean getValue();	
	public void setValue(boolean bValue) ;
}
