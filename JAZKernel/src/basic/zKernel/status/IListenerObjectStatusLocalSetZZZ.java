package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectStatusLocalSetZZZ extends EventListener{	
	public boolean changeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
}
