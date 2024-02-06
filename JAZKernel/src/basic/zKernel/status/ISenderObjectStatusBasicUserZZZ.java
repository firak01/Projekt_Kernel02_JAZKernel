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
public interface ISenderObjectStatusBasicUserZZZ{
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	//public abstract void setSenderStatusLocalUsed(ISenderObjectStatusBasicZZZ objEventSender);
	
	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
	public void unregisterForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
	public boolean reactOnStatusLocalEvent(IEventObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
}
