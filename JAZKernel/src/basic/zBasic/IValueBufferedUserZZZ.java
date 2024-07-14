package basic.zBasic;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public interface IValueBufferedUserZZZ extends IValueUserZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert hier ablegen, quasi als historischen Wert.
	//Merke: Ein CicularBuffer waere auch gut.
	public VectorExtendedDifferenceZZZ<String> getValueVector();
}
