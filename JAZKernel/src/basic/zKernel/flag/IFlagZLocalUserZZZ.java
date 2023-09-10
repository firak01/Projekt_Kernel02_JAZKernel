package basic.zKernel.flag;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Flags" abzufragen.
 * Das sind Flags, die so speziell sind, das sie in der Klasse direkt abgelegt werden,
 * noch nicht einmal in einem Interface, da sie von anderen Klassen nicht benötigt werden sollten. 
 * 
 * @author Fritz Lindhauer, 21.07.2022, 08:48:45
 * 
 */
public interface IFlagZLocalUserZZZ{
	public final String sERROR_FLAG_UNAVAILABLE = "this LOCAL flag is not available: ";
	public final int iERROR_FLAG_UNAVAILABLE = 51;
	
	//Beispiel für das einzubinde Enum als Flag, das NUR in der konkreten Klasse direkt eingebunden werden kann
	//s. KernelJPanelCascadedZZZ
//	public enum FLAGZLOCAL {
//		SKIPDEBUGUI;
//	}	
	
	//KONVENTION: 
	//Das Z im Methodennamen ...FlagZ... wird nur für Methoden verwendet, die ein Array zurueckliefern.
	public abstract HashMap<String, Boolean>getHashMapFlagLocal();
	public abstract void setHashMapFlagLocal(HashMap<String, Boolean> hmFlagLocal);	
	
	public abstract boolean getFlagLocal(String sFlag) throws ExceptionZZZ;
	public abstract boolean setFlagLocal(String sFlag, boolean bValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.	
	public abstract boolean[] setFlagLocal(String[] saFlag, boolean bValue) throws ExceptionZZZ; 
	public abstract boolean proofFlagLocalExists(String sFlag) throws ExceptionZZZ; //Wird per METHOD.INVOKE(...) aufgerufen, muss darum in jeder Klasse - per Vererbung - vorhanden sein.
	public abstract boolean proofFlagLocalSetBefore(String sFlagName) throws ExceptionZZZ;
	
	public String[] getFlagZLocal(boolean bFlagValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZLocal(boolean bFlagValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZLocal() throws ExceptionZZZ;
	
	

}
