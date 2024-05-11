package basic.zBasic.util.datatype.string;

import basic.zBasic.ExceptionZZZ;

public interface IStringJustifierZZZ {

	//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
	//Beispiel:
	//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
	//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.	
	public String getPositionSeparatorDefault();
	public String getPositionSeparator();
	public void setPositionSeparator(String sPositionSeparator);

	public int getInfoPartBoundLeftBehindCurrent();
	public void setInfoPartBoundLeftBehindCurrent(int iIndex);
	public int indexOfInfoPartBoundLeft(String sLog); //Rechne fuer den konkreten Log String die Postion aus.
	public int indexOfInfoPartBoundLeftBehind(String sLog); //Rechne fuer den konkreten Log String die Postion aus.
	public int getInfoPartBoundLeftBehind2use(String sLog);     //Rechne aus, gib aber ggfs. den gespeicherten Wert zurueck, wenn der groesser ist.

	public String justifyInfoPart(String sLog) throws ExceptionZZZ; //versuche den Log-InfoPart ueber alle Zeilen buendig zu machen. Teile auf und verwende zusätzliche \t
		
}
