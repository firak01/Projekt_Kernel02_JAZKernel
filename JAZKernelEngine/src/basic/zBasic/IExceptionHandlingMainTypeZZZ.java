package basic.zBasic;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IExceptionHandlingMainTypeZZZ { //nein, das selber ist keine ENUM, also passt das nicht... extends IEnumSetMappedZZZ {
	
	//Dem Objekt dann noch setter mitgeben.
	public void setAbbreviation(String sAbbr);
	public String getAbbreviation();
	
	public void setDescription(String sDescription);	
	public String getDescription();	
}
