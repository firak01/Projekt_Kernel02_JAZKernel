package basic.zBasic.util.log;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface ILogStringZZZ{
	//Merke: Es soll folgendes abgebildet werden, z.B. 
	//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
	
	//Das zu wird das Format als Anweisung in ein Array abgelegt, in dem dann die einzelnen Teilkomponenten ausgerechnet werden.
	
	//Merke: TODO Idee ist es das Custom-Format in einer einzigen Zahl zu definieren.
	//Merke: Jedes dieser Argumente ist als Primzahl zu defnieren.
	//       Es gilt Positionswert = Position * Primzahl
	
	//Argumente der compute Methode (sofern vorhanden und != null)
	public static int iARGNEXT01=1;
	public static int iARGNEXT02=2;     //2 verschiedene Varianten fuer Argnext 
	public static int iCLASSNAME=3;
	public static int iTHREAD=5; 
	
	//Formatstring für die Postion eines möglichen Texts (%s) in dem Teilstring
	public static String sARGNEXT01="%s";
	public static String sARGNEXT02="'%s'";  //Merke: Zweite Argnext Variante ist in Hochkommata
	public static String sCLASSNAME="(%s)";
	public static String sTHREAD="[Thread: %s ]";
	
	public String compute(Object obj, String sLog) throws ExceptionZZZ;
	public String compute(Object obj, String sLog01, String sLog02) throws ExceptionZZZ; //Zwei Log String sind normal log01 wäre z.B. ReflectCodeZZZ.getPositionCurrent();
	public String compute(Object obj, String[] saLog) throws ExceptionZZZ;
	public int computeFormatPositionsNumber();
	
	
	/* Beispiel für solch ein Array mit den Angaben wo was stehen soll ist:
	            int[] iaFormat = {
				ILogStringZZZ.iTHREAD,
				ILogStringZZZ.iARGNEXT,
				ILogStringZZZ.iCLASSNAME,
				ILogStringZZZ.iARGNEXT,
			};
			
			Also zuerst die Threadangabe, danach der Wert des 1. Arguments der compute-Methode, dann der Klassenname, danach der Wert des 2. Arguments der compute-Methode
	 */
	public void setFormatPositions(int[]iaFormat);
	public int[] getFormatPositions();
	public int[] getFormatPositionsCustom();
	public int[] getFormatPositionsDefault();
	
	public void setHashMapFormatPositionString(HashMap<Integer,String>hmFormatPostionString);
	public HashMap<Integer,String>getHashMapFormatPositionString();
	public HashMap<Integer,String>getHashMapFormatPositionStringCustom();
	public HashMap<Integer,String>getHashMapFormatPositionStringDefault();
	
	
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
