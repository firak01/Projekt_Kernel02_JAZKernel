package basic.zKernel.status;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectStatusLocalSetZZZ extends EventListener{
	public boolean statusLocalChanged(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public boolean isStatusLocalRelevant(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
}
