package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public interface IValueComputedBufferedUserZZZ extends IValueBufferedUserZZZ{
	
	//Der bis originale (ggfs. vor Aufloesen einer Formel) Wert wird hier ablegen, quasi als historischen Wert.
	//Merke: Ein CicularBuffer waere auch gut.
	public VectorExtendedDifferenceZZZ<String>getRawVector() throws ExceptionZZZ;
	public String getRaw() throws ExceptionZZZ;
	public void setRaw(String sRaw) throws ExceptionZZZ;
}
