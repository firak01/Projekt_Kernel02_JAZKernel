package basic.zBasic.util.datatype.string;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormaterZZZ;
import custom.zKernel.ILogZZZ;

public interface IStringJustifierZZZ {
	public static String sSEPARATOR_MESSAGE_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;//IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
	public static String sSEPARATOR_01_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
	public static String sSEPARATOR_02_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT;
	public static String sSEPARATOR_03_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT;
	
	public boolean reset() throws ExceptionZZZ;
	
	//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
	//Beispiel:
	//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
	//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.	
	public String getPositionSeparatorDefault() throws ExceptionZZZ;
	public String getPositionSeparator() throws ExceptionZZZ;
	public void setPositionSeparator(String sPositionSeparator);

	public String getPositionIdentifierDefault();
	public String getPositionIdentifier();
	public void setPositionIdentifier(String sPositionIdentifier);

	public int getInfoPartBoundLeftBehindCurrent();
	public void setInfoPartBoundLeftBehindCurrent(int iIndex);
	public void setInfoPartBoundLeftBehindIncreased(int iIndexMayIncrease);
	public int indexOfInfoPartBoundLeft(String sLog); //Rechne fuer den konkreten Log String die Postion aus.
	public int indexOfInfoPartBoundLeftBehind(String sLog); //Rechne fuer den konkreten Log String die Postion aus.
	public int getInfoPartBoundLeftBehind2use(String sLog);     //Rechne aus, gib aber ggfs. den gespeicherten Wert zurueck, wenn der groesser ist.

	public String justifyInfoPart(String sLog) throws ExceptionZZZ; //versuche den Log-InfoPart ueber alle Zeilen buendig zu machen. Teile auf und verwende zusätzliche \t
		
}
