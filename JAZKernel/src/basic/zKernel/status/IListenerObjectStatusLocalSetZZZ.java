package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectStatusLocalSetZZZ extends EventListener, IStatusLocalMapForCascadingStatusLocalUserZZZ{	
	public boolean changeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	public boolean isEventRelevant(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByClass(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocalValue(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
}
