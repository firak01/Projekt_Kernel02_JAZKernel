package basic.zKernel.flag;


import java.io.Serializable;
import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelUseObjectZZZ;

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
public class KernelSenderObjectFlagZsetZZZ implements ISenderObjectFlagZsetZZZ, Serializable{
	private static final long serialVersionUID = 8999783685575147532L;
	public KernelSenderObjectFlagZsetZZZ() throws ExceptionZZZ{		
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#fireEvent(basic.zKernelUI.component.model.KernelEventComponentSelectionResetZZZ)
	 */
	private ArrayList<IListenerObjectFlagZsetZZZ> listaLISTENER_REGISTERED = new ArrayList();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden 
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
	                                                                                          //             mehrere Events, an verschiedenen Komponenten, unabh�ngig voneinander zu verwalten.
	public final void fireEvent(IEventObjectFlagZsetZZZ event){	
		/* Die Abfrage nach getSource() funktioniert so mit dem Interface noch nicht....
		if(event.getSource() instanceof ISenderSelectionResetZZZ){
			ISenderSelectionResetZZZ sender = (ISenderSelectionResetZZZ) event.getSource();
			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) sender.getListenerRegisteredAll().get(i);
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
				l.doReset(event);
			}
		}else{
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) this.getListenerRegisteredAll().get(i);				
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				l.doReset(event);
			}
		}
		*/
		
		main:{
			if(event==null)break main;
			
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerObjectFlagZsetZZZ l = (IListenerObjectFlagZsetZZZ) this.getListenerRegisteredAll().get(i);				
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# IListenerObjectFlagZsetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				try {
					l.flagChanged(event);
				} catch (ExceptionZZZ ez) {
					//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# IListenerObjectFlagZsetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast() );					
				}
			}
			
		}//end main:
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#removeSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void removeListenerObjectFlagZset(IListenerObjectFlagZsetZZZ l){
		this.getListenerRegisteredAll().remove(l);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#addSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	public final void addListenerObjectFlagZset(IListenerObjectFlagZsetZZZ l){
		this.getListenerRegisteredAll().add(l);
	}
	
	public final ArrayList<IListenerObjectFlagZsetZZZ> getListenerRegisteredAll(){
		return listaLISTENER_REGISTERED;
	}
}

