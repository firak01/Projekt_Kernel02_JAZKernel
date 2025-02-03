package basic.zBasic;

public interface IValueUserZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert
	public String getValue() throws ExceptionZZZ;
	public void setValue(String sValue) throws ExceptionZZZ;
	
	public boolean hasAnyValue() throws ExceptionZZZ;
	public boolean hasNullValue() throws ExceptionZZZ;
	//Merke: protected geht in Interface nicht, also direkt so in der abstrakten Klasse
	//protected void hasAnyValue(boolean bHasAnyValue);
	//protected void hasNullValue(boolean bHasNullValue);
	public void hasAnyValue(boolean bHasAnyValue) throws ExceptionZZZ;
	public void hasNullValue(boolean bHasNullValue) throws ExceptionZZZ;
	
	public void reset() throws ExceptionZZZ;
}
