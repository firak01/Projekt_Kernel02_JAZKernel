package basic.zKernel.flag;

import java.util.ArrayList;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectFlagZsetZZZ {
	public abstract void fireEvent(IEventObjectFlagZsetZZZ event);

	public abstract void removeListenerObjectFlagZset(IListenerObjectFlagZsetZZZ objEventListener);

	public abstract void addListenerObjectFlagZset(IListenerObjectFlagZsetZZZ objEventListener);
	
	public abstract ArrayList<IListenerObjectFlagZsetZZZ> getListenerRegisteredAll();
}
