package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ILogZZZ;
import basic.zBasic.AbstractObjectWithStatusZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.IStatusLocalMapForCascadingStatusLocalUserZZZ;
import custom.zKernel.LogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelUseObjectWithStatusListeningCascadedZZZ extends AbstractKernelUseObjectWithStatusZZZ implements IKernelUserZZZ, IKernelContextUserZZZ, IListenerObjectStatusLocalSetZZZ, IStatusLocalMapForCascadingStatusLocalUserZZZ {
	//Wie in AbstractObjectWithStatusListeningZZZ
	protected volatile HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> hmEnumSet =null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.

	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 */
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(){		
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	
	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);		
	}
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ{
		super(objKernel, sFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);
	}
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt		
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);
	}
	
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelZZZ objKernel, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(objKernel, hmFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, null);				
	}
	
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext) throws ExceptionZZZ{
		super(objKernel,objKernelContext);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectWithStatusListeningNew_(objKernel, null, objKernelContext);						
	}
	
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		KernelUseObjectWithStatusListeningNew_(null, objKernelUsing, null);
	}
	
	public AbstractKernelUseObjectWithStatusListeningCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing, saFlag);
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
		public boolean changeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ{
			boolean bReturn = false;
			
			main:{
				//Falls nicht zuständig, mache nix
			    boolean bProof = this.isEventRelevant2ChangeStatusLocal(eventStatusLocalSet);
				if(!bProof) break main;
				
				String sLog=null;
				
				//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
				IEnumSetMappedZZZ enumStatus = eventStatusLocalSet.getStatusEnum();
				if(enumStatus==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Keinen Status aus dem Event-Objekt erhalten. Breche ab";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
				}
				
				//+++++++++++++++++++++
				HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>hmEnum = this.getHashMapEnumSetForCascadingStatusLocal();				
				if(hmEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Keine Mapping Hashmap fuer das StatusMapping vorhanden. Breche ab";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
				}
				
				//+++++++++++++++++++++
				
				IEnumSetMappedStatusZZZ objEnum = hmEnum.get(enumStatus);							
				if(objEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Keinen gemappten Status für en Status aus dem Event-Objekt erhalten. Breche ab";
					System.out.println(sLog);
					this.logLineDate(sLog);
					break main;
				}
				
				
				
				//Nur so als Beispiel, muss ueberschrieben werden:
				//Lies den Status (geworfen vom Backend aus)
				String sStatus = eventStatusLocalSet.getStatusText();
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Methode muss ueberschrieben werden.");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sStatus='"+sStatus+"'");
			}//end main:
			return bReturn;
		}
		
		
		@Override
		public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalSetZZZ eventStatusLocalSet)
				throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{	
				bReturn = this.proofStatusLocalValueChanged(sStatusString, bStatusValue);	
			}//end main:
			if(bReturn) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+ ": Status changed to '"+sStatusString+"', Value="+bStatusValue;
				System.out.println(sLog);
			    this.getLogObject().WriteLineDate(sLog);			
			}else {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+ ": Status remains '"+sStatusString+"', Value="+bStatusValue;
				System.out.println(sLog);
			    this.getLogObject().WriteLineDate(sLog);
			}
			return bReturn;
		}
		
		//### aus IStatusLocalMapForCascadingStatusLocalUserZZZ	
		@Override
		public HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> getHashMapEnumSetForCascadingStatusLocal() {
			if(this.hmEnumSet==null) {
				this.hmEnumSet = this.createHashMapEnumSetForCascadingStatusLocalCustom();
			}
			return this.hmEnumSet;
		}

		@Override
		public void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet) {
			this.hmEnumSet = hmEnumSet;
		}
		
		@Override
		public abstract HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
		
}//end class

