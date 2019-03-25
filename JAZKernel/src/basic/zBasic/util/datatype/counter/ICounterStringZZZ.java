package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterStringZZZ {
	public String toString(); //ein technischer debug String.
	
	public int getValueCurrent(); //der gespeicherte wert. Merke: Die Buchstabenkombinationen werden nicht gespeichert, sondern immer errechnet.
	public void setValueCurrent(int iValue);

	public String getStringPrefix();
	public void setStringPrefix(String sPrefix);//String vor dem Counter
	public String getStringSuffix();
	public void setStringSuffix(String sSufffix);//String nach dem Counter
	
	public String getString() throws ExceptionZZZ;//gib den aktuellen String zurück, wie .current, aber mit Prefix und suffix.
	public String getStringFor(int iValue) throws ExceptionZZZ;
	public String getStringNext() throws ExceptionZZZ;//dito
	public String getStringIncreased() throws ExceptionZZZ;//dito
	
	public String current() throws ExceptionZZZ; //gib den aktuellen String zurück
	public String next() throws ExceptionZZZ; //gib den nächsten String zurück, aber mit Speicherung ders internen Wertes.	
	public String increased() throws ExceptionZZZ; //wie next(), ohne den Wert zu speichern.
}
