package basic.zKernel.cache;

import basic.zBasic.ExceptionZZZ;

public interface ICachableObjectZZZ {
	public boolean isCacheSkipped() throws ExceptionZZZ; //Das Arbeiten mit dem Cache wird für das Objekt abgestellt.
	public void isCacheSkipped(boolean bValue) throws ExceptionZZZ;
	
	public boolean wasValueComputed() throws ExceptionZZZ; //Für das Arbeiten mit einem Filter. Hier nur true, wenn der Wert auf einer Berechnung beruht, z.B. aus einer Formel.
	public String getValueForFilter() throws ExceptionZZZ; //Für das Arbeiten mit einem Filter. Hier einen Wert zurückgeben. z.B. Value oder bei Formeln auch RAW.
}
