package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;


/**Dieses Interface enthaelt Methoden, die von den Klassen implementiert werden muessen, 
 * die den Kernel eigenen Event verwalten sollen.
 * 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectStatusBasicUserZZZ{
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	public void registerForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ;
	public void unregisterForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ;
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ;
}
