package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;

public interface IStatusBooleanZZZ {
	public IEnumSetMappedStatusZZZ getEnumObject();
	public void setEnumObject(IEnumSetMappedStatusZZZ objEnum);	
	public boolean getValue();	
	public void setValue(boolean bValue) ;
}
