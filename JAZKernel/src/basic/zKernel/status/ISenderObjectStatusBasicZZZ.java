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
public interface ISenderObjectStatusBasicZZZ extends EventListener{
	public abstract void fireEvent(IEventObjectStatusBasicZZZ event);	
	public abstract IEventObjectStatusBasicZZZ getEventPrevious();
	public abstract void setEventPrevious(IEventObjectStatusBasicZZZ event);
	
	public abstract void removeListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
	public abstract void addListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;	
	public abstract ArrayList<IListenerObjectStatusBasicZZZ> getListenerRegisteredAll() throws ExceptionZZZ;
}
