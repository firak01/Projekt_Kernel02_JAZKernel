package basic.zBasic;

public interface ILogZZZ {
	public void logLineDate(String sLog) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	//Merke: 20240130 Weil es in AbstractMainOVPN diese Methode gibt hier aufgenommen. Damit muss man die Codestellen nicht alle durch logLineDate ersetzen, wenn man mal ein Snippet daraus nutzt.
	public void logProtocolString(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public void logProtocolString(String[] saLog) throws ExceptionZZZ;
}
