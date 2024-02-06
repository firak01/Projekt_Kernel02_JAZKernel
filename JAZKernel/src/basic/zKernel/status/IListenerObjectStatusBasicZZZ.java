package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectStatusBasicZZZ extends EventListener{	
	public boolean reactOnStatusLocalEvent(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	public boolean isEventRelevant(IEventObjectStatusBasicZZZ eventStatusBasic) throws ExceptionZZZ;
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusBasic) throws ExceptionZZZ;
	public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusBasic) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusBasic) throws ExceptionZZZ;
	public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusBasic) throws ExceptionZZZ;
	
}
