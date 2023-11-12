package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" Werte abzufragen.
 */
public interface IStatusLocalUserZZZ extends IStatusLocalUserBasicZZZ, ICircularBufferStatusBooleanUserZZZ{	
	public abstract IStatusBooleanZZZ getStatusLocalObject();
	public abstract void setStatusLocalObject(IStatusBooleanZZZ objEnum);
	public abstract IStatusBooleanZZZ getStatusLocalObjectPrevious();
	public abstract IStatusBooleanZZZ getStatusLocalObjectPrevious(int iIndexStepsBack);
	
	void setStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocal, boolean bStatusValue);
}
