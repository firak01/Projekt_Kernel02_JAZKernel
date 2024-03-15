package basic.zKernel.status;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalMessageUserZZZ extends IStatusLocalBasicUserZZZ, ICircularBufferStatusBooleanMessageUserZZZ{
	public abstract IStatusBooleanMessageZZZ getStatusLocalObject();
	public abstract boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum);
	public abstract boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum, String sStatusMessage);
	public abstract IStatusBooleanMessageZZZ getStatusLocalObjectPrevious();
	public abstract IStatusBooleanMessageZZZ getStatusLocalObjectPrevious(int iIndexStepsBack);
	
	//... wird auch durch FLAGZ-Eintrag aktiviert, schaut aber im Circular Buffer nach
	public abstract boolean proofStatusLocalMessageChanged(IStatusBooleanMessageZZZ objEnum) throws ExceptionZZZ;
	
	//Erweiterung von IStatusLocalUserZZZ... hier gibt es jetzt eine "Message" fuer den Status
	public abstract boolean switchStatusLocalAsGroupTo(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	//Merke: Das offer kommt von der intern verwendeten CircularBuffer
	public abstract boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;	
	public abstract boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean offerStatusLocal(String sStatusName, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	public abstract boolean offerStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	
	public abstract boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocal, boolean bStatusValue, String sMessage) throws ExceptionZZZ;//Merke: Das offer kommt vom dem intern verwendeten CircularBuffer Klasse
		
	public abstract boolean setStatusLocal(String sStatusName, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	public abstract boolean setStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	public abstract String getStatusLocalMessage(); //das ist dann der aktuellste Wert aus dem CircularBuffer
	public abstract String getStatusLocalMessage(Enum enumStatusIn) throws ExceptionZZZ;  //Das ist der Wert aus der HashMap fuer Message
	public abstract String getStatusLocalMessage(String sStatusName) throws ExceptionZZZ; //Das ist der Wert aus der HashMap fuer Message

	public abstract String getStatusLocalMessagePrevious();
	public abstract String getStatusLocalMessagePrevious(int iIndexStepsBack);
	public abstract boolean replaceStatusLocalMessage(String sStatusMessage); //Merke: Das replace kommt von der intern verwendeten CircularBuffer Klasse
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
