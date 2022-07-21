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
public interface IFlagLocalUserZZZ{
	public final String sERROR_FLAG_UNAVAILABLE = "this LOCAL flag is not available: ";
	public final int iERROR_FLAG_UNAVAILABLE = 51;
	
	//Beispiel für das einzubinde Enum als Flag, das NUR in der konkreten Klasse direkt eingebunden werden kann
	//s. KernelJPanelCascadedZZZ
//	public enum FLAGZLOCAL {
//		SKIPDEBUGUI;
//	}	
	
	public abstract boolean proofFlagZLocalExists(String sFlag) throws ExceptionZZZ; //Wird per METHOD.INVOKE(...) aufgerufen, muss darum in jeder Klasse - per Vererbung - vorhanden sein.		
	public String[] getFlagZLocal(boolean bFlagValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZLocal(boolean bFlagValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZLocal() throws ExceptionZZZ;
	public abstract HashMap<String, Boolean>getHashMapFlagZLocal();
	public abstract void setHashMapFlagZLocal(HashMap<String, Boolean> hmFlagLocal);	
}
