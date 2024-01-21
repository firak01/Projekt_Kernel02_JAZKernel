package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectStatusLocalMessageSetZZZ extends EventListener{	
	public boolean changeStatusLocal(IEventObjectStatusLocalMessageSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalMessageSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalMessageSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalMessageSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalMessageSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
}
