package basic.zKernel;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithStatusMonitoringZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalUserZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelUseObjectWithStatusMonitoringZZZ extends AbstractObjectWithStatusMonitoringZZZ implements IKernelUserZZZ, IKernelContextUserZZZ, IListenerObjectStatusLocalZZZ, IStatusLocalMapForMonitoringStatusLocalUserZZZ {
	//Wie in AbstractObjectWithStatusListeningZZZ
	
	//Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.
	protected volatile HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> hmEnumSet =null; 

	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 */
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(){		
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	
	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);		
	}
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ{
		super(sFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);
	}
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(saFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt		
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);
	}
	
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelZZZ objKernel, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);				
	}
	
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext) throws ExceptionZZZ{
		super();//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, objKernelContext);						
	}
	
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super();
		KernelUseObjectWithStatusListeningNew_(null, objKernelUsing, null);
	}
	
	public AbstractKernelUseObjectWithStatusMonitoringZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		KernelUseObjectWithStatusListeningNew_(null, objKernelUsing, null);
	}
	
	private boolean KernelUseObjectWithStatusListeningNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing, IKernelContextZZZ objKernelContext) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{					
			//+++++++++++++++++++++++++++++++
			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}
	
	
	//##### aus IListenerObjectStatusLocalSetZZZ
		/* (non-Javadoc)
		 * @see basic.zKernel.status.IListenerObjectStatusLocalSetZZZ#changeStatusLocal(basic.zKernel.status.IEventObjectStatusLocalSetZZZ)
		 */
		@Override
		public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
			boolean bReturn = false;			
			main:{		
				if(eventStatusLocal==null) {				 
					 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					 throw ez;
				}
				
				boolean bValue = eventStatusLocal.getStatusValue();
				if(!bValue) {
					if(!this.getFlag(IListenerObjectStatusLocalZZZ.FLAGZ.STATUSLOCAL_REACT_ON_VALUEFALSE)) {
						break main; //Nur auf FALSE-Werte reagieren, wenn entsprechendes FLAG gesetzt ist					
					}
				}
				
				String sLog=null;
				
				IEnumSetMappedStatusZZZ enumStatus = (IEnumSetMappedStatusZZZ) eventStatusLocal.getStatusLocal();
				//IEnumSetMappedZZZ enumStatus = eventStatusLocal.getStatusLocal();
				if(enumStatus==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+ "ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - Keinen Status aus dem Event-Objekt erhalten. Breche ab";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
				}
				
				
				//Falls nicht zuständig, mache nix
			    boolean bProof = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
				if(!bProof) break main;
								
				//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
				HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmEnum = this.getHashMapEnumSetForCascadingStatusLocal();				
				if(hmEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+ "ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - Keine Mapping Hashmap fuer das StatusMapping vorhanden. Breche ab";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
				}
				
				//+++++++++++++++++++++
				
				IEnumSetMappedStatusZZZ objEnum = hmEnum.get(enumStatus);							
				if(objEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - Keinen gemappten Status für en Status aus dem Event-Objekt erhalten. Breche ab";					
					this.logProtocolString(sLog);
					break main;
				}
				
				
				
				//Nur so als Beispiel, muss ueberschrieben werden:
				//Lies den Status (geworfen vom Backend aus)
				if(eventStatusLocal instanceof IEventObjectStatusLocalZZZ) {
					IEventObjectStatusLocalZZZ eventStatusLocalReact = (IEventObjectStatusLocalZZZ) eventStatusLocal;					
					String sStatus = eventStatusLocalReact.getStatusMessage();
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - Methode muss ueberschrieben werden.");
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - sStatus='"+sStatus+"'");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"ObjectWithStatusMonitoring ("+ this.getClass().getName()+" - Event ist kein instanceof IEventObjectStatusLocalZZZ. Breche ab.";					
					this.logProtocolString(sLog);
				}
				
			}//end main:
			return bReturn;
		}
		
		@Override
		public abstract HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
		
}//end class

