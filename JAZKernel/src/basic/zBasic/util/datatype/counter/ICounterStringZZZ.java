package basic.zBasic.util.datatype.counter;

public interface ICounterStringZZZ {
	public String toString();
	public String getPrefix();
	public void setPrefix(String sPrefix);//String vor dem Counter
	public String getSuffix();
	public void setSuffix(String sSufffix);//String nach dem Counter
	
	public String current(); //gib den aktuellen String zurück
	public String next(); //gib den nächsten String zurück, aber mit Speicherung ders internen Wertes.	
	public String increased(); //wie next(), ohne den Wert zu speichern.
}
