package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStringZZZ <T extends ICounterStrategyZZZ>{
	public String toString(); //ein technischer debug String.
	
	public int getValueCurrent(); //der gespeicherte wert. Merke: Die Buchstabenkombinationen werden nicht gespeichert, sondern immer errechnet.
	public void setValueCurrent(int iValue);

	public String getStringPrefix();
	public void setStringPrefix(String sPrefix);//String vor dem Counter
	public String getStringSuffix();
	public void setStringSuffix(String sSufffix);//String nach dem Counter
	
	public String getString() throws ExceptionZZZ;//gib den aktuellen String zurück, wie .current, aber mit Prefix und suffix.	
	public String getStringNext() throws ExceptionZZZ;//dito, erhöht den internen Zähler
	public String getStringChange(int iValue) throws ExceptionZZZ;//dito, setzt den internen Zähler
	public String getStringPeekNext() throws ExceptionZZZ;//dito, lässt den internen Zähler unverändert
	public String getStringPeekChange(int iValue) throws ExceptionZZZ;//dito
	
	public String current() throws ExceptionZZZ; //gib den aktuellen String zurück
	public String next() throws ExceptionZZZ; //gib den nächsten String zurück, aber mit Speicherung des internen Wertes.
	public String change(int iValue) throws ExceptionZZZ;
	public String peekNext() throws ExceptionZZZ; //wie next(), ohne den Wert zu speichern.
	public String peekChange(int iValue) throws ExceptionZZZ;//wie change() ohne den Wet zu speichern.

	//Setze den Counter per Zeichen.
	public void setValueCurrent(String sValue) throws ExceptionZZZ;
	
	//20190610: Vor- und Nacharbeiten des Wertsetzens. Hier können ggfs. Füllzeichen entfernt oder hinzugenommen werden.
	public String preValueSetting(String sValue)  throws ExceptionZZZ;
	public String postValueSetting(String sValue) throws ExceptionZZZ;	
	
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
			//darum diese Strategy-Objekte nur in den "Endklassen" verwenden.
	//TODO GOON: Mache das generisch, so dass andere Counter nicht eine weitere Methode haben müssen, 
	//           um ihr Strategy-Objekt zu holen.
	//public ICounterStrategyZZZ getCounterStrategyObject();	
	//public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy);
	public void setCounterStrategyObject(T objCounterStrategy);
	public T getCounterStrategyObject() throws ExceptionZZZ;
	//public void setCounterStrategyObject(T objCounterStrategy);
	
	//Entsprechend des Strategy-Objekts. Nur so kann man hier direkter die Eigenschaft ansprechen.
	public boolean isRightAligned() throws ExceptionZZZ;
	public void isRightAligned(boolean bValue) throws ExceptionZZZ;
}
