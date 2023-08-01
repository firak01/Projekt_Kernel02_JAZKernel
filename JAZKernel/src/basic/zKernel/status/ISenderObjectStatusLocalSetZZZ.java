package basic.zKernel.status;

import java.util.ArrayList;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectStatusLocalSetZZZ {
	public abstract void fireEvent(IEventObjectStatusLocalSetZZZ event);

	public abstract void removeListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener);

	public abstract void addListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener);
	
	public abstract ArrayList<IListenerObjectStatusLocalSetZZZ> getListenerRegisteredAll();
}
