package basic.zBasic;

public interface IUrlLogicZZZ extends IConstantZZZ{
	public String getUrl();
	public void setUrl(String sUrl);
	
	
	//Merke: static Methoden können nicht in Interfaces aufgenommen werden.
	//        Darum eine abstracte Klasse verwendet.
	//        Darum die static Methode in der Abstracten Klasse eingebaut, DIE SOFORT EINE FEHLER WIRFT, DASS SIE ÜBERSCHRIEBEN WERDEN MUSS.
	//       public static String getProtocol(String sUrl) throws ExceptionZZZ;
}
