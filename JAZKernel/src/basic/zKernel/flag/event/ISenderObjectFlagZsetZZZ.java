package basic.zKernel.flag.event;

import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;




/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectFlagZsetZZZ {
	public abstract void fireEvent(IEventObjectFlagZsetZZZ event);
	public abstract IEventObjectFlagZsetZZZ getEventPrevious();
	public void setEventPrevious(IEventObjectFlagZsetZZZ event);
	
	public abstract void removeListenerObjectFlagZset(IListenerObjectFlagZsetZZZ objEventListener);

	public abstract void addListenerObjectFlagZset(IListenerObjectFlagZsetZZZ objEventListener);
	
	//Damit soll sichergestellt werden, dass jedes Objekt sich nur 1x registriert. 
	//Merke: Wenn ein Objekt mehrmals registriert wird, kann es sein, das beim 2ten feuern des gleichen Events an das gleich Objekt unerwuenschte Nebeneffekte auftreten.
	public abstract ArrayListUniqueZZZ<IListenerObjectFlagZsetZZZ> getListenerRegisteredAll();
}
