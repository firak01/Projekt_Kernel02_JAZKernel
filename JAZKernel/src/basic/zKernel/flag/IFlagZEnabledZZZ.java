package basic.zKernel.flag;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;

public interface IFlagZEnabledZZZ{
	
	//#############################################################
	//### FLAGZ und FLAG - BASIS METHODEN
	//#############################################################	
	public final String sERROR_FLAG_UNAVAILABLE = "this flag is not available: ";
	public final int iERROR_FLAG_UNAVAILABLE = 50;

	public enum FLAGZ{
		DEBUG, INIT ; //20170307 - Verschoben aus ObjectZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Java spezifischen Klasse erben).
	}
	
	//KONVENTION: 
	//Das Z im Methodennamen ...FlagZ... wird nur für Methoden verwendet, die ein Array zurueckliefern.
	public abstract boolean getFlag(String sFlagName);
	public abstract boolean setFlag(String sFlagName, boolean bValue) throws ExceptionZZZ;;
	public abstract boolean[] setFlag(String[] saFlagName, boolean bValue) throws ExceptionZZZ;;
	public abstract boolean proofFlagExists(String sFlag) throws ExceptionZZZ; //Wird per METHOD.INVOKE(...) aufgerufen, muss darum in jeder Klasse - per Vererbung - vorhanden sein.
	public abstract boolean proofFlagSetBefore(String sFlag) throws ExceptionZZZ; //Prueft auf die Existenz des Flags in der HashMap.
	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag);
	public abstract boolean setFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlag(IFlagZEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	//20230730 Merke: In IEventBrokerFlagZsetUserZZZ gibt es noch diese Besonderheit, mit der Enum - Werte gesetzt werden können. Die werden dann an Events uebergeben und koennen weitere Informationen enthalten.
	//boolean setFlag(Enum enumFlag, boolean bFlagValue) throws ExceptionZZZ;
	//public abstract boolean getFlag(Enum a) throws ExceptionZZZ;
	
	public abstract boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ; //Prueft auf die Existenz des Flags in der HashMap.
	
	
	//202211228: Leider wird dann als Methode nur das FLAGZ in den erbenden Klassen eingesetzt.
	//           Und das ist "ambigous". Es geht also das Interface verloren.
	//           Damit ist diese Methode nicht schnell in die erbenden Klassen hinzuzufügen.
	//public abstract void setFlag(IFlagUserZZZ.FLAGZ objEnumFlag, boolean bValue);
	//public abstract void getFlag(IFlagUserZZZ.FLAGZ objEnumFlag);
	//public abstract <T extends IFlagUserZZZ.FLAGZ> void setFlag(T objEnumFlag, boolean bValue);
	
	public abstract HashMap<String, Boolean>getHashMapFlag();
	public abstract boolean resetFlags() throws ExceptionZZZ;
	
	public String[] getFlagZ(boolean bFlagValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZ(boolean bFlagValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZ() throws ExceptionZZZ;
	
	public abstract HashMap<String, Boolean>getHashMapFlagPassed();
	public abstract void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed);
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ;
	public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ;
	public String[] getFlagZ_passable(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ;//Hole alle auf true gesetzten Flags....
	
	//20250816: Damit können Flags von einem Objekt zu einem anderen "vererbt bzw. uebergeben" werden.
	//          Nicht relevante Flags wie "INIT", "DEBUG" werden dabei ignoriert.
	public abstract int adoptFlagZrelevantFrom(IFlagZEnabledZZZ objUsingFlagZ, boolean bValueToSearchFor) throws ExceptionZZZ;
}
