package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferForStatusBooleanMessageZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalBasicUserZZZ extends IObjectWithStatusZZZ{
	public final String sERROR_STATUS_UNAVAILABLE = "this LOCAL status is not available: ";
	public final int iERROR_STATUS_UNAVAILABLE = 51;
	
	//Diese FLAGZ sind in IObjectWithStatusZZZ
//	public enum FLAGZ{
//		STATUSLOCAL_PROOF_VALUE, STATUSLOCAL_PROOF_VALUECHANGED ; 
//	}
		
	//Falls benötigt wird 1 Fehlerstring gespeichert. 
	//Merke: Diese Fehlermeldung ist flexibel und nicht etwa in einem EnumSet hinterlegt.	
	public String getStatusLocalError();
	public void setStatusLocalError(String sError);
	public void resetStatusLocalError();	
	
	//wird nicht gespeichert, darum kein setter. Wert wird nur aus dem Enum geholt
	public String getStatusLocalAbbreviation();
	public String getStatusLocalAbbreviationPrevious();
	public String getStatusLocalDescription();
	public String getStatusLocalDescriptionPrevious();	
	
	//++++ Schaue in der HashMap nach...
	public abstract boolean getStatusLocal(Enum enumStatusIn) throws ExceptionZZZ;
	public abstract boolean getStatusLocal(String sStatusName) throws ExceptionZZZ;
		
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;		
	public abstract boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.	
	
	public abstract boolean[] setStatusLocal(Enum[] enumaStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean[] setStatusLocal(String[] saStatusName, boolean bStatusValue) throws ExceptionZZZ;	
	public String[] getStatusLocal(boolean bStatusValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getStatusLocal(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)

	
	//+++ Verwende IEnumSetMappedStatus. Da kann man viel mehr Informationen unterbringen
	public abstract boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;	
		
	//++++ Aendere mit einem Aufruf mehrere Statuseintraege ab, ohne Berücksichtigung einer GroupId
	public abstract boolean switchStatusLocalAllGroupTo(Enum enumStatusIn) throws ExceptionZZZ; //setze den uebergebenen Status auf true. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf false..
	public abstract boolean switchStatusLocalAllGroupTo(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ; //setze den uebergebenen Status auf den uebergebenen Wert. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf den entsprechend nicht uebergebenen Wert.. 
	public abstract boolean switchStatusLocalAllGroupTo(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //setze den uebergebenen Status auf den uebergebenen Wert. UND FUER ALLE BEKANNTEN STATUSEINTRAEGE auf den entsprechend nicht uebergebenen Wert..
	public abstract boolean switchStatusLocalAllGroupTo(Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	//+++ Aendere mit einem Aufruf mehrere Statuseintraege ab... FUER DIE ANGEGEBENE GRUPPE
	public abstract boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus) throws ExceptionZZZ;
	public abstract boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	
	//+++ Aendere mit einem Aufruf mehrere Statuseintraege ab... FUER DIE ANGEGEBENE GRUPPE
	//    Merke: In IEnumSetMappedStatusZZZ ist die StatusGroupId vorhanden.
	public abstract boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusZZZ enumStatusMapped) throws ExceptionZZZ;
	public abstract boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;	
	public abstract boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusZZZ enumStatusMapped, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;

	//++++ Schaue in der Klasse nach dem enum STATUSLOCAL
	public String[] getStatusLocalAll() throws ExceptionZZZ; 
	public abstract boolean proofStatusLocalExists(Enum enumStatusIn) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalExists(String sStatusName) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalDirectExists(Enum enumStatusIn) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalDirectExists(String sStatusName) throws ExceptionZZZ;
	
	//... diese Methoden werden durch FLAGZ-Eintraege per default aktiviert, können aber auch deaktivert werden.
	//    Geprüft wird der HashMap-Eintrag
	public abstract boolean proofStatusLocalValue(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValue(String sStatusName, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValueChanged(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean proofStatusLocalValueChanged(String sStatusName, boolean bStatusValue) throws ExceptionZZZ;
	
	//... wird auch durch FLAGZ-Eintrag aktiviert, schaut aber im Circular Buffer nach
	public abstract boolean proofStatusLocalMessageChanged(String sMessage) throws ExceptionZZZ;

	//Halte den gesetzten Status als Enum fest, 
	//Merke: Das offer kommt von der intern zum Speichern verwendeten CircularBuffer Klasse
	boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocal) throws ExceptionZZZ;	
	boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocal, boolean bStatusValue) throws ExceptionZZZ;
	public IEnumSetMappedStatusZZZ getStatusLocalEnumCurrent();
	public IEnumSetMappedStatusZZZ getStatusLocalEnumPrevious();
	public IEnumSetMappedStatusZZZ getStatusLocalEnumPrevious(int iIndexStepsBack);
	

	
}
