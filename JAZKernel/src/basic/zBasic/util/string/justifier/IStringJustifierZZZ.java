package basic.zBasic.util.string.justifier;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.util.string.formater.ILogStringFormatZZZ;
import basic.zBasic.util.string.formater.ILogStringFormaterZZZ;
import custom.zKernel.ILogZZZ;

public interface IStringJustifierZZZ {
	public static String sSEPARATOR_MESSAGE_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;//IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
	public static String sSEPARATOR_01_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
	public static String sSEPARATOR_02_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT;
	public static String sSEPARATOR_03_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT;
	public static String sSEPARATOR_04_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_04_DEFAULT;
	public static String sSEPARATOR_POSITION_DEFAULT=ILogStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT;
	
	public boolean reset() throws ExceptionZZZ;
	
	//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
	//Beispiel:
	//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
	//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.	
	public String getPositionSeparatorDefault() throws ExceptionZZZ;
	public String getPositionSeparator() throws ExceptionZZZ;
	public void setPositionSeparator(String sPositionSeparator) throws ExceptionZZZ;

	public String getPositionIdentifierDefault() throws ExceptionZZZ;
	public String getPositionIdentifier() throws ExceptionZZZ;
	public void setPositionIdentifier(String sPositionIdentifier) throws ExceptionZZZ;

	public int getInfoPartBoundLeftBehindCurrent() throws ExceptionZZZ;
	public void setInfoPartBoundLeftBehindCurrent(int iIndex) throws ExceptionZZZ;
	public void setInfoPartBoundLeftBehindIncreased(int iIndexMayIncrease) throws ExceptionZZZ;
	public int indexOfInfoPartBoundLeft(String sLog) throws ExceptionZZZ; //Rechne fuer den konkreten Log String die Postion aus.
	public int indexOfInfoPartBoundLeftBehind(String sLog) throws ExceptionZZZ; //Rechne fuer den konkreten Log String die Postion aus.
	public int getInfoPartBoundLeftBehind2use(String sLog) throws ExceptionZZZ;     //Rechne aus, gib aber ggfs. den gespeicherten Wert zurueck, wenn der groesser ist.

	public String justifyInfoPart(String sLog) throws ExceptionZZZ; //versuche den Log-InfoPart ueber alle Zeilen buendig zu machen. Teile auf und verwende zusätzliche \t
		
}
