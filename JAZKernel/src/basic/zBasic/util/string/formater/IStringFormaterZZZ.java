package basic.zBasic.util.string.formater;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;

public interface IStringFormaterZZZ extends IStringFormatComputerZZZ, IStringFormatComputerJaggedZZZ {//, ILogStringFormatComputerJustifiedZZZ {
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
		public void setFormatPositionsMapped(IEnumSetMappedStringFormatZZZ[]ienumaMappedFormat);
		public IEnumSetMappedStringFormatZZZ[]getFormatPositionsMapped() throws ExceptionZZZ;
		public IEnumSetMappedStringFormatZZZ[]getFormatPositionsMappedDefault() throws ExceptionZZZ;
		public IEnumSetMappedStringFormatZZZ[]getFormatPositionsMappedCustom() throws ExceptionZZZ;
		
		public void setHashMapFormatPositionString(HashMap<Integer,String>hmFormatPostionString);
		public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ;
		public HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;
		public HashMap<Integer,String>getHashMapFormatPositionStringDefault() throws ExceptionZZZ;
		
		
				
		
		//#############################################################
		//### FLAGZ
		//#############################################################
		public enum FLAGZ{
			DUMMY,EXCLUDE_THREAD, EXCLUDE_CLASSNAME
		}
			
		boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
		boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
		boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
		boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
		
			
		//#######################################################################################
		// STATUS	
	    //............ hier erst einmal nicht .....................
		
}
