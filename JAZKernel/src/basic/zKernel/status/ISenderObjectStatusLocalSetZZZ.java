package basic.zKernel.status;

import java.util.ArrayList;
import java.util.EventListener;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IEventObjectFlagZsetZZZ;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectStatusLocalSetZZZ {
	public abstract void fireEvent(IEventObjectStatusLocalSetZZZ event);	
	public abstract IEventObjectStatusLocalSetZZZ getEventPrevious();
	public void setEventPrevious(IEventObjectStatusLocalSetZZZ event);
	
	public abstract void removeListenerObjectStatusLocalSet(EventListener objEventListener) throws ExceptionZZZ;
	public abstract void addListenerObjectStatusLocalSet(EventListener objEventListener) throws ExceptionZZZ;
	public abstract ArrayList<?> getListenerRegisteredAll() throws ExceptionZZZ;
}
