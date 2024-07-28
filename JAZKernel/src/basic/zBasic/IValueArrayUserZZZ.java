package basic.zBasic;

import java.util.ArrayList;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;

public interface IValueArrayUserZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert
	public VectorExtendedDifferenceZZZ<ArrayList<String>> getValueArrayListVector() throws ExceptionZZZ;
	
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ;
	public void setValue(ArrayList<String> listasValue) throws ExceptionZZZ;
	
	public boolean isArrayValue() throws ExceptionZZZ;
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ;
	//Merke: protected geht in Interface nicht, also direkt so in der abstrakten Klasse
	//protected void isArrayValue(boolean bHasArrayValue);
}
