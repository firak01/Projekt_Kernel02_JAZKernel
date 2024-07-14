package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public interface IValueSolvedUserZZZ extends IValueBufferedUserZZZ{
	
	//Der Raw - Wert vor der (naechsten) Aufloesung
	public VectorExtendedDifferenceZZZ<String>getRawVector();
	public String getRaw();
	public void setRaw(String sRaw);
}
