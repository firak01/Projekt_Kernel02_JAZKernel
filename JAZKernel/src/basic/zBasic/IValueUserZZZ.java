package basic.zBasic;

public interface IValueUserZZZ{

	//Der bis dahin (ggfs. durch aufloesen einer Formel) ermittelte Wert
	public String getValue();
	public void setValue(String sValue);
	
	public boolean hasAnyValue();
	public boolean hasNullValue();
	//Merke: protected geht in Interface nicht, also direkt so in der abstrakten Klasse
	//protected void hasAnyValue(boolean bHasAnyValue);
	//protected void hasNullValue(boolean bHasNullValue);
}
