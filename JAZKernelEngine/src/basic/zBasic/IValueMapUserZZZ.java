package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractList.VectorDifferenceZZZ;

public interface IValueMapUserZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert
	public VectorDifferenceZZZ<HashMap<String,String>> getValueHashMapVector() throws ExceptionZZZ;
	
	public HashMap<String,String> getValueHashMap() throws ExceptionZZZ;
	public void setValue(HashMap<String,String> mapssValue) throws ExceptionZZZ;
	
	public boolean isMapValue() throws ExceptionZZZ;
	public void isMapValue(boolean bIsMapValue) throws ExceptionZZZ;
}
