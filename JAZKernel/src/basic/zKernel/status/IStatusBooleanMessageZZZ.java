package basic.zKernel.status;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IStatusBooleanMessageZZZ extends IStatusBooleanZZZ{
	public String getMessage();	//Das ist ggfs. eine abweichende Message von Default-Wert aus der Enumeration
	public void setMessage(String sMessage) ;
}
