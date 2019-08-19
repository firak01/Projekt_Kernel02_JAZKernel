package basic.zKernel.cache;

public interface ICachableObjectZZZ {
	public boolean isCacheSkipped(); //Das Arbeiten mit dem Cache wird für das Objekt abgestellt.
	public void isCacheSkipped(boolean bValue);
	
	public boolean wasValueComputed(); //Für das Arbeiten mit einem Filter. Hier nur true, wenn der Wert auf einer Berechnung beruht, z.B. aus einer Formel.
	public String getValueForFilter(); //Für das Arbeiten mit einem Filter. Hier einen Wert zurückgeben. z.B. Value oder bei Formeln auch RAW.
}
