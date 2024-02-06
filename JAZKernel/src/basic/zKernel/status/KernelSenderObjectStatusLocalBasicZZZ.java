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
public class KernelSenderObjectStatusLocalBasicZZZ extends AbstractKernelSenderObjectStatusLocalBasicZZZ{
	private static final long serialVersionUID = 8999783685575147532L;	
	
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#fireEvent(basic.zKernelUI.component.model.KernelEventComponentSelectionResetZZZ)
	 */
 	//private ArrayList<IListenerObjectStatusBasicZZZ> listaLISTENER_REGISTERED = new ArrayList<IListenerObjectStatusBasicZZZ>();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht moeglich ist
	
	public KernelSenderObjectStatusLocalBasicZZZ() throws ExceptionZZZ{
		super();
	}
	
//	 //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
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
//					IListenerObjectStatusBasicZZZ l =  this.getListenerRegisteredAll().get(i);
//					
//					//Das Ziel ist, dass sich an diesem Objekt-Broker unterschiedliche Listnertypen registrieren können.
//					//Damit gibt es das Problem, das nicht jede Listener Klasse über die gleichen Methoden verfügt.
//										
//					//IDEE: Nun mit InstanceOf auf den konkreten CAST verteilen und damit auch die für das Event passende react-Funktion starten.
//
//					//In dieser exemplarischen Kernelklasse wird dies mal gezeigt. 
//					//Die Klassennamen beziehen sich auf das ModuleExternal Projekt....
//					System.out.println(ReflectCodeZZZ.getPositionCurrent()+": TESTTESTTEST Der Name der registrierten Listenerklasse ist '" + l.getClass().getSimpleName() + "'");
//										//z.B.: LogFileCreateMockRunnerZZZ, LogFileWatchListenerExampleZZZ
//										//Merke: Wenn das nur so gestuert werden kann, muss KernelSenderObjectStatusLocalMessageSetZZZ eine abstracte Klasse werden
//										//       und es muss einen daraus erbende Klasse verwendet werden.
//										
//										if(l.getClass().getSimpleName().equals("LogFileCreateMockRunnerZZZ")) {
//											IListenerObjectStatusLocalMessageReactZZZ l2 = (IListenerObjectStatusLocalMessageReactZZZ) l;
//											System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalSetMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//											try {
//												boolean bStatusLocalChanged = l2.reactOnStatusLocalEvent(event);
//												//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//												if(bStatusLocalChanged) {
//													//in basicEvent gibt es diese Methode nicht ... System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//												}					
//											} catch (ExceptionZZZ ez) {
//												//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//												System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//											}
//										}else if(l.getClass().getSimpleName().equals("LogFileWatchListenerExampleZZZ")) {
//											IListenerObjectStatusLocalMessageReactZZZ l2 = (IListenerObjectStatusLocalMessageReactZZZ) l;
//											System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalSetMessageZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
//											try {
//												boolean bStatusLocalChanged = l2.reactOnStatusLocalEvent(event);
//												//boolean bStatusLocalReacted = l.reactOnStatusLocalEvent(event);
//												if(bStatusLocalChanged) {
//													//In basicEvent gibt es diese Methode nicht ... System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " hat auf '" + event.getStatusText() + "' reagiert." );
//												}					
//											} catch (ExceptionZZZ ez) {
//												//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
//												System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalMessageSetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
//											}
//										}
//				}
//			} catch (ExceptionZZZ e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}//end main:
//	}
	
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
//	public IEventObjectStatusBasicZZZ getEventPrevious() {
//		return this.eventPrevious;
//	}
//	
//	@Override
//	public void setEventPrevious(IEventObjectStatusBasicZZZ event) {
//		this.eventPrevious = event;
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
//	@Override
//	public ArrayList<IListenerObjectStatusBasicZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
//		return listaLISTENER_REGISTERED;
//	}	
}

