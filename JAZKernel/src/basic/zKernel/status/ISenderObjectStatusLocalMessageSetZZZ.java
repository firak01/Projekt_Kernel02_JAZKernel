package basic.zKernel.status;

import java.util.ArrayList;
import java.util.EventListener;
import basic.zBasic.ExceptionZZZ;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectStatusLocalMessageSetZZZ{
	public abstract void fireEvent(IEventObjectStatusLocalMessageSetZZZ event);	
	public abstract IEventObjectStatusLocalMessageSetZZZ getEventPrevious();
	public abstract void setEventPrevious(IEventObjectStatusLocalMessageSetZZZ event);
	
	public abstract void removeListenerObjectStatusLocalSet(IListenerObjectStatusLocalMessageSetZZZ objEventListener) throws ExceptionZZZ;
	public abstract void addListenerObjectStatusLocalSet(IListenerObjectStatusLocalMessageSetZZZ objEventListener) throws ExceptionZZZ;	
	public abstract ArrayList<IListenerObjectStatusLocalMessageSetZZZ> getListenerRegisteredAll() throws ExceptionZZZ;	
}
