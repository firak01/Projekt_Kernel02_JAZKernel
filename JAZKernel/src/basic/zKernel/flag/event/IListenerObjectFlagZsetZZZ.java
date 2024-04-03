package basic.zKernel.flag.event;

import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;

public interface IListenerObjectFlagZsetZZZ extends EventListener{
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ;
	
}
