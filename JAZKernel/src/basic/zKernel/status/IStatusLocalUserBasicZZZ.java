package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalUserBasicZZZ extends IObjectWithStatusZZZ{
	public final String sERROR_STATUS_UNAVAILABLE = "this LOCAL status is not available: ";
	public final int iERROR_STATUS_UNAVAILABLE = 51;
	
	//Diese FLAGZ sind in IObjectWithStatusZZZ
//	public enum FLAGZ{
//		STATUSLOCAL_PROOF_VALUE, STATUSLOCAL_PROOF_VALUECHANGED ; 
//	}
		
	
	//++++ Schaue in der HashMap nach...
	public abstract boolean getStatusLocal(Enum enumStatusIn) throws ExceptionZZZ;
	public abstract boolean getStatusLocal(String sStatusName) throws ExceptionZZZ;
		
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;		
	public abstract boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.	
	
	public abstract boolean[] setStatusLocal(Enum[] enumaStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean[] setStatusLocal(String[] saStatusName, boolean bStatusValue) throws ExceptionZZZ;	
	public String[] getStatusLocal(boolean bStatusValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getStatusLocal(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)

	//++++ Aendere mit einem Aufruf mehrere Statuseintraege ab
	//public abstract boolean switchStatusLocal(Enum enumStatusIn) throws ExceptionZZZ;//setze den Status von true auf false und umgekehrt
	//public abstract boolean switchStatusLocalAsGroupTo(Enum enumStatusIn) throws ExceptionZZZ; //setze den uebergebenen Status auf von true auf false oder umgekehrt. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf den nicht geswitchten Wert. 
	public abstract boolean switchStatusLocalAsGroupTo(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ; //setze den uebergebenen Status auf den uebergebenen Wert. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf den entsprechend nicht uebergebenen Wert.. 
	public abstract boolean switchStatusLocalAsGroupTo(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //setze den uebergebenen Status auf den uebergebenen Wert. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf den entsprechend nicht uebergebenen Wert..
	

	
	
	
	//++++ Schaue in der Klasse nach dem enum STATUSLOCAL
	public String[] getStatusLocalAll() throws ExceptionZZZ; 
	public abstract boolean proofStatusLocalExists(Enum enumStatusIn) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalExists(String sStatusName) throws ExceptionZZZ;
	
	//... diese Methoden werden durch FLAGZ-Eintraege per default aktiviert, können aber auch deaktivert werden.
	public abstract boolean proofStatusLocalValue(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValue(String sStatusName, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValueChanged(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValueChanged(String sStatusName, boolean bStatusValue) throws ExceptionZZZ;
	

	public boolean isStatusLocalRelevant(IEnumSetMappedZZZ enumStatusIn) throws ExceptionZZZ;
	
	//Halte den gesetzten Status als Enum fest, 
	//Merke: Das offer kommt von der intern zum Speichern verwendeten CircularBuffer Klasse
	boolean offerStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocal);		
	public IEnumSetMappedZZZ getStatusLocalEnumCurrent();
	public IEnumSetMappedZZZ getStatusLocalEnumPrevious();
	public IEnumSetMappedZZZ getStatusLocalEnumPrevious(int iIndexStepsBack);
	
	
	//wird nicht gespeichert, darum kein setter. Wert wird nur aus dem Enum geholt
	public String getStatusLocalAbbreviation();
	public String getStatusLocalAbbreviationPrevious();
	public String getStatusLocalDescription();
	public String getStatusLocalDescriptionPrevious();	
}
