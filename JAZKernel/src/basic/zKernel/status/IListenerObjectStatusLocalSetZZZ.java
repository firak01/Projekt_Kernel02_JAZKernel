package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;

public interface IListenerObjectStatusLocalSetZZZ extends EventListener, IStatusLocalMapForCascadingStatusLocalUserZZZ{
	public boolean statusLocalChanged(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventStatusLocalRelevant(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;	
	public boolean isStatusLocalChanged(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;	
}
