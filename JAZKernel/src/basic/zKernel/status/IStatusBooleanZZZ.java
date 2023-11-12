package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IStatusBooleanZZZ {
	public IEnumSetMappedZZZ getEnumObject();
	public void setEnumObject(IEnumSetMappedZZZ objEnum);	
	public boolean getValue();	
	public void setValue(boolean bValue) ;
}
