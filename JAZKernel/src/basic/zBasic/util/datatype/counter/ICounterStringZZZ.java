package basic.zBasic.util.datatype.counter;

public interface ICounterStringZZZ {
	public String toString(); //ein technischer debug String.
	
	public int getValueCurrent(); //der gespeicherte wert. Merke: Die Buchstabenkombinationen werden nicht gespeichert, sondern immer errechnet.
	public void setValueCurrent(int iValue);

	public String getStringPrefix();
	public void setStringPrefix(String sPrefix);//String vor dem Counter
	public String getStringSuffix();
	public void setStringSuffix(String sSufffix);//String nach dem Counter
	
	public String getString();//gib den aktuellen String zurück, wie .current, aber mit Prefix und suffix.
	public String getStringFor(int iValue);
	public String getStringNext();//dito
	public String getStringIncreased();//dito
	
	public String current(); //gib den aktuellen String zurück
	public String next(); //gib den nächsten String zurück, aber mit Speicherung ders internen Wertes.	
	public String increased(); //wie next(), ohne den Wert zu speichern.
}
