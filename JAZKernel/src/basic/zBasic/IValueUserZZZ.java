package basic.zBasic;

import java.util.Vector;

public interface IValueUserZZZ extends IResettableValuesZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert
	public String getValue() throws ExceptionZZZ;
	public void setValue(String sValue) throws ExceptionZZZ;
	public void setValueAsString(Vector<String> vecValue) throws ExceptionZZZ;
	
	public boolean hasAnyValue() throws ExceptionZZZ;
	public boolean hasNullValue() throws ExceptionZZZ;
	//Merke: protected geht in Interface nicht, also direkt so in der abstrakten Klasse
	//protected void hasAnyValue(boolean bHasAnyValue);
	//protected void hasNullValue(boolean bHasNullValue);
	public void hasAnyValue(boolean bHasAnyValue) throws ExceptionZZZ;
	public void hasNullValue(boolean bHasNullValue) throws ExceptionZZZ;		
}
