package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalUserZZZ extends IStatusLocalBasicUserZZZ, ICircularBufferStatusBooleanUserZZZ{	
	public abstract IStatusBooleanZZZ getStatusLocalObject();
	public abstract void setStatusLocalObject(IStatusBooleanZZZ objEnum);
	public abstract IStatusBooleanZZZ getStatusLocalObjectPrevious();
	public abstract IStatusBooleanZZZ getStatusLocalObjectPrevious(int iIndexStepsBack);
	
	//Merke: Das offer kommt von der intern verwendeten CircularBuffer
	public abstract boolean offerStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;	
	public abstract boolean offerStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
		
	public abstract boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocal, boolean bStatusValue);//Merke: Das offer kommt vom dem intern verwendeten CircularBuffer Klasse
			
	public abstract boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean setStatusLocalEnum(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;
		
	
}
