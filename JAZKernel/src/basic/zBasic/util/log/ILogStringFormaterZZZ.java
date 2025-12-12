package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;

public interface ILogStringFormaterZZZ extends ILogStringFormatComputerZZZ, ILogStringFormatComputerJaggedZZZ, ILogStringFormatComputerJustifiedZZZ {
	//+++++++++++++++++++++++++++++++
		public int computeFormatPositionsNumber() throws ExceptionZZZ;
		
		
		/* Beispiel für solch ein Array IEnumSetMappedLogStringFormatZZZ[] mit den Angaben wo was stehen soll ist:
		            int[] iaFormat = {
					ILogStringZZZ.iTHREAD,
					ILogStringZZZ.iARGNEXT,
					ILogStringZZZ.iCLASSNAME,
					ILogStringZZZ.iARGNEXT,
				};
				
				Also zuerst die Threadangabe, danach der Wert des 1. Arguments der compute-Methode, dann der Klassenname, danach der Wert des 2. Arguments der compute-Methode
		 */
		public void setFormatPositionsMapped(IEnumSetMappedLogStringFormatZZZ[]ienumaMappedFormat);
		public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMapped() throws ExceptionZZZ;
		public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMappedDefault() throws ExceptionZZZ;
		public IEnumSetMappedLogStringFormatZZZ[]getFormatPositionsMappedCustom() throws ExceptionZZZ;
		
		public void setHashMapFormatPositionString(HashMap<Integer,String>hmFormatPostionString);
		public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ;
		public HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;
		public HashMap<Integer,String>getHashMapFormatPositionStringDefault() throws ExceptionZZZ;
		
		
		//###########################		
		//Zurücksetzen von allem, auch des StringJustifiers
		public boolean reset() throws ExceptionZZZ;
		
		//Zuruecksetzen, z.B. des Indexwerts, true wenn etwas zurueckzusetzen war.
		public boolean resetStringIndexRead() throws ExceptionZZZ;	
		
		//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
		public boolean hasStringJustifierPrivate() throws ExceptionZZZ;
		
		//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
		//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.
	    public IStringJustifierZZZ getStringJustifier() throws ExceptionZZZ;
	    public void setStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ;
	    
	    
	    //Methode, mit der zu einer anderen LogZeilen - Verarbeitung gesprungen werden kann.
	    //Das wird durch den iLINENEXT Parameter in der Formatierungsanweisung gesteuert.
	    //public int getStringIndexStart() throws ExceptionZZZ;
	    //public void setStringIndexStart(int iIndexStart) throws ExceptionZZZ;
		public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ;
		public void setStringIndexRead(ArrayListUniqueZZZ<Integer>listaintStringIndexRead) throws ExceptionZZZ;
		
		//#############################################################
		//### FLAGZ
		//#############################################################
		public enum FLAGZ{
			DUMMY,EXCLUDE_THREAD, EXCLUDE_CLASSNAME
		}
			
		boolean getFlag(FLAGZ objEnumFlag);
		boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
		boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
		
		
		
		//#######################################################################################
		// STATUS	
	    //............ hier erst einmal nicht .....................
		
}
