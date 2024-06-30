package basic.zKernel.file.ini;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public interface IValueSolvedUserZZZ {

	//Der bis dahin aufgeloeste Wert
	public VectorExtendedDifferenceZZZ<String> getValueVector();
	public String getValue();
	public void setValue(String sValue);
	
	//Der Raw - Wert vor der (naechsten) Aufloesung
	public VectorExtendedDifferenceZZZ<String>getRawVector();
	public String getRaw();
	public void setRaw(String sRaw);
}
