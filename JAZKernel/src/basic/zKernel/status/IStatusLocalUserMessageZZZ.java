package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalUserMessageZZZ extends IStatusLocalUserBasicZZZ, ICircularBufferStatusBooleanMessageUserZZZ{
	public abstract IStatusBooleanMessageZZZ getStatusLocalObject();
	public abstract boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum);
	public abstract IStatusBooleanMessageZZZ getStatusLocalObjectPrevious();
	public abstract IStatusBooleanMessageZZZ getStatusLocalObjectPrevious(int iIndexStepsBack);
	
	//Erweiterung von IStatusLocalUserZZZ... hier gibt es jetzt eine "Message" fuer den Status
	public abstract boolean switchStatusLocalAsGroupTo(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	
	//Merke: Das offer kommt von der intern verwendeten CircularBuffer
	public abstract boolean offerStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;	
	public abstract boolean offerStatusLocal(String sStatusName, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	
	public abstract boolean offerStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocal, boolean bStatusValue, String sMessage);//Merke: Das offer kommt vom dem intern verwendeten CircularBuffer Klasse
		
	public abstract String getStatusLocalMessage();
	public abstract String getStatusLocalMessagePrevious();
	public abstract boolean replaceStatusLocalMessage(String sStatusMessage); //Merke: Das replace kommt von der intern verwendeten CircularBuffer Klasse
}
