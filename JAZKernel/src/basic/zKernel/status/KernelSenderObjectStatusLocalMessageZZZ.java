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
public class KernelSenderObjectStatusLocalMessageZZZ  extends AbstractKernelSenderObjectStatusLocalBasicZZZ{
	private static final long serialVersionUID = 8999783685575147532L;	
																						  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht moeglich ist	
	public KernelSenderObjectStatusLocalMessageZZZ() throws ExceptionZZZ{
		super();
	}
	
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
//	@Override                                                                                     //             mehrere Events, an verschiedenen Komponenten, unabhaengig voneinander zu verwalten.	
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
//					IListenerObjectStatusLocalMessageSetZZZ l = (IListenerObjectStatusLocalMessageSetZZZ) this.getListenerRegisteredAll().get(i);				
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalSetMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//					try {
//						boolean bStatusLocalChanged = l.reactOnStatusLocalEvent(event);
//						//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//						if(bStatusLocalChanged) {
//							//Das ist das Probelm wenn alles basicZZZ ist: System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
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

	
//	@Override
//	public void fireEvent(IEventObjectStatusLocalMessageSetZZZ event) {
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
//					//IListenerObjectStatusLocalMessageSetZZZ l = (IListenerObjectStatusLocalMessageSetZZZ) this.getListenerRegisteredAll().get(i);				
//					IListenerObjectStatusBasicZZZ l =  this.getListenerRegisteredAll().get(i);
//					//System.out.println(typeof l);
//					//IDEE: Nun mit InstanceOf auf den konkreten CAST verteilen und damit auch die für das Event passende react-Funktion starten.
//					System.out.println("TESTTESTTEST: " + l.getClass().getSimpleName());
//					//z.B.: LogFileCreateMockRunnerZZZ, LogFileWatchListenerExampleZZZ
//					//Merke: Wenn das nur so gestuert werden kann, muss KernelSenderObjectStatusLocalMessageSetZZZ eine abstracte Klasse werden
//					//       und es muss einen daraus erbende Klasse verwendet werden.
//					
//					if(l.getClass().getSimpleName().equals("LogFileCreateMockRunnerZZZ")) {
//						IListenerObjectStatusLocalMessageReactZZZ l2 = (IListenerObjectStatusLocalMessageReactZZZ) l;
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalSetMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//						try {
//							boolean bStatusLocalChanged = l2.reactOnStatusLocalEvent(event);
//							//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//							if(bStatusLocalChanged) {
//								System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageReactZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//							}					
//						} catch (ExceptionZZZ ez) {
//							//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//							System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageReactZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//						}
//					} else if(l.getClass().getSimpleName().equals("LogFileWatchRunnerZZZ")) {
//						IListenerObjectStatusLocalMessageSetZZZ l2 = (IListenerObjectStatusLocalMessageSetZZZ) l;
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//						try {
//							boolean bStatusLocalChanged = l2.reactOnStatusLocalEvent(event);
//							//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//							if(bStatusLocalChanged) {
//								System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//							}					
//						} catch (ExceptionZZZ ez) {
//							//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//							System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//						}
//					}else if(l.getClass().getSimpleName().equals("LogFileWatchListenerMonitorExampleZZZ")) {
//						IListenerObjectStatusLocalMessageReactZZZ l2 = (IListenerObjectStatusLocalMessageReactZZZ) l;
//						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageReactZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//						try {
//							boolean bStatusLocalChanged = l2.reactOnStatusLocalEvent(event);
//							//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//							if(bStatusLocalChanged) {
//								System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageReactZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//							}					
//						} catch (ExceptionZZZ ez) {
//							//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//							System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageReactZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//						}
//					}
//					
//					
//					
//					
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
//	public IEventObjectStatusBasicZZZ getEventPrevious() {
//		return this.eventPrevious;
//	}
//	
//	@Override	
//	public void removeListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
//		this.getListenerRegisteredAll().remove(objEventListener);
//	}
//	
//	@Override	
//	public void addListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
//		this.getListenerRegisteredAll().add(objEventListener);
//	}
//	
//	
//	@Override
//	public ArrayList<IListenerObjectStatusBasicZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
//		return listaLISTENER_REGISTERED;
//	}
//
//	@Override
//	public void setEventPrevious(IEventObjectStatusBasicZZZ event) {
//		this.eventPrevious = event;
//	}
}

