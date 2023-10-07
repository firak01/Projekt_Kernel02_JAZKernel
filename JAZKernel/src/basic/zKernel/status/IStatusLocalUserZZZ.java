package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Flags" abzufragen.
 * Das sind Flags, die so speziell sind, das sie in der Klasse direkt abgelegt werden,
 * noch nicht einmal in einem Interface, da sie von anderen Klassen nicht benötigt werden sollten. 
 * 
 * @author Fritz Lindhauer, 21.07.2022, 08:48:45
 * 
 */
public interface IStatusLocalUserZZZ{
	public final String sERROR_STATUS_UNAVAILABLE = "this LOCAL flag is not available: ";
	public final int iERROR_STATUS_UNAVAILABLE = 51;
	
	//Beispiel für das einzubinde Enum als Flag, das NUR in der konkreten Klasse direkt eingebunden werden kann
	//s. KernelJPanelCascadedZZZ
//	public enum FLAGZLOCAL {
//		SKIPDEBUGUI;
//	}	

	//KONVENTION: 	
	public abstract HashMap<String, Boolean>getHashMapStatusLocal();
	public abstract void setHashMapStatusLocal(HashMap<String, Boolean> hmStatusLocal);	
	
	public abstract boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ;
	public abstract boolean getStatusLocal(String sStatusName) throws ExceptionZZZ;
	public abstract boolean setStatusLocal(Enum objEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	public abstract boolean[] setStatusLocal(Enum[] objaEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean[] setStatusLocal(String[] saStatusName, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalExists(Enum objEnumStatusIn) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalExists(String sStatusName) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalChanged(Enum objEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalChanged(String sStatusName, boolean bStatusValue) throws ExceptionZZZ;

	
	
	public String[] getStatusLocal(boolean bStatusValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getStatusLocal(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getStatusLocal() throws ExceptionZZZ;
}
