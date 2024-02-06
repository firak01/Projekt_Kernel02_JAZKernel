package basic.zKernel.status;


import java.io.Serializable;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/** Diese Klasse implementiert alles, was benoetigt wird, damit die eigenen Events "Flag hat sich geaendert" abgefeuert werden kann
 *  und auch von den Objekten, die hier registriert sind empfangen wird. Damit fungieren Objekte dieser Klasse als "EventBroker".
 *   
 *   Wichtig: Diese Klasse darf nicht final sein, damit sie von anderen Klassen geerbt werden kann.
 *               Die Methoden dieser Klasse sind allerdings final.
 *               
 *   Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )            
 * @author lindhaueradmin
 *
 */
public class KernelSenderObjectStatusLocalReactZZZ extends AbstractKernelSenderObjectStatusLocalBasicZZZ{
	private static final long serialVersionUID = 8999783685575147532L;	
	
	public KernelSenderObjectStatusLocalReactZZZ() throws ExceptionZZZ{
		super();
	}
	
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
//	@Override                                                                                     //             mehrere Events, an verschiedenen Komponenten, unabhaengig voneinander zu verwalten.
//	//public void fireEvent(IEventObjectStatusLocalMessageReactZZZ event){	
//	public void fireEvent(IEventObjectStatusBasicZZZ event){
//		/* Die Abfrage nach getSource() funktioniert so mit dem Interface noch nicht....
//		 * Auszug aus: KernelSenderComponentSelectionResetZZZ.fireEvent(....)
//		if(event.getSource() instanceof ISenderSelectionResetZZZ){
//			ISenderSelectionResetZZZ sender = (ISenderSelectionResetZZZ) event.getSource();
//			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
//				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) sender.getListenerRegisteredAll().get(i);
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
//				l.doReset(event);
//			}
//		}else{
//			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
//				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) this.getListenerRegisteredAll().get(i);				
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//				l.doReset(event);
//			}
//		}
//		*/
//		
//		main:{
//			if(event==null)break main;
//			
//			try {
//				for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
//					IListenerObjectStatusLocalMessageReactZZZ l = (IListenerObjectStatusLocalMessageReactZZZ) this.getListenerRegisteredAll().get(i);				
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalReactMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//					try {
//						//boolean bStatusLocalChanged = l.changeStatusLocal(event);
//						boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//						if(bStatusLocalReacted) {
//							//Das ist das Problem, wenn das alles basicZZZ ist: System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//						}					
//					} catch (ExceptionZZZ ez) {
//						//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//					}
//				}
//			} catch (ExceptionZZZ e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}//end main:
//	}
//	
//	@Override
//	public void fireEvent(IEventObjectStatusLocalMessageReactZZZ event) {
//		/* Die Abfrage nach getSource() funktioniert so mit dem Interface noch nicht....
//		 * Auszug aus: KernelSenderComponentSelectionResetZZZ.fireEvent(....)
//		if(event.getSource() instanceof ISenderSelectionResetZZZ){
//			ISenderSelectionResetZZZ sender = (ISenderSelectionResetZZZ) event.getSource();
//			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
//				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) sender.getListenerRegisteredAll().get(i);
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
//				l.doReset(event);
//			}
//		}else{
//			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
//				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) this.getListenerRegisteredAll().get(i);				
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//				l.doReset(event);
//			}
//		}
//		*/
//		
//		main:{
//			if(event==null)break main;
//			
//			try {
//				for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
//					IListenerObjectStatusLocalMessageReactZZZ l = (IListenerObjectStatusLocalMessageReactZZZ) this.getListenerRegisteredAll().get(i);				
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalReactMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//					try {
//						//boolean bStatusLocalChanged = l.changeStatusLocal(event);
//						boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//						if(bStatusLocalReacted) {
//							System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//						}					
//					} catch (ExceptionZZZ ez) {
//						//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//					}
//				}
//			} catch (ExceptionZZZ e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}//end main:
//	}	
//	
//	@Override
//	//public IEventObjectStatusLocalMessageReactZZZ getEventPrevious() {
//	public IEventObjectStatusBasicZZZ getEventPrevious() {
//		return this.eventPrevious;
//	}
//	
//	@Override
//	//public void setEventPrevious(IEventObjectStatusLocalMessageReactZZZ event) {
//	public void setEventPrevious(IEventObjectStatusBasicZZZ event) {
//		this.eventPrevious = event;
//	}
//	
//	@Override
//	//public void removeListenerObjectStatusLocalReact(IListenerObjectStatusLocalMessageReactZZZ objEventListener) throws ExceptionZZZ {
//	public void removeListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {		
//		this.getListenerRegisteredAll().remove(objEventListener);
//	}
//	
//	@Override
//	//public void addListenerObjectStatusLocalReact(IListenerObjectStatusLocalMessageReactZZZ objEventListener) throws ExceptionZZZ {	
//	public void addListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
//		this.getListenerRegisteredAll().add(objEventListener);
//	}
//	
//	/* (non-Javadoc)
//	 * @see basic.zKernel.status.ISenderObjectStatusLocalSetZZZ#getListenerRegisteredAll()
//	 */
//	@Override
//	//public ArrayList<IListenerObjectStatusLocalMessageReactZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
//	public ArrayList<IListenerObjectStatusBasicZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
//		return listaLISTENER_REGISTERED;
//	}
//
//	
}

