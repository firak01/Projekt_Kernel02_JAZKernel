package basic.zKernel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ILogZZZ;
import basic.zBasic.IObjectWithStatusZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IListenerObjectFlagZsetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusLocalMapForStatusLocalUserZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;
import custom.zKernel.LogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelUseObjectListeningZZZ extends AbstractKernelUseObjectZZZ implements IListenerObjectFlagZsetZZZ, IListenerObjectStatusLocalZZZ, IStatusLocalMapForStatusLocalUserZZZ {
	private static final long serialVersionUID = 6048997985909418786L;
	
	//Merke: Das Objekt selbst hat keinen Status. Es nimmt aber Statusaenderungen vom Main-Objekt entgegen und mapped diese auf sein "Aussehen"
	//       Wie in AbstractObjectWithStatusListeningZZZ wird für das Mappen des reinkommenden Status auf ein Enum eine Hashmap benötigt.
	protected volatile HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedZZZ> hmEnumSet =null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.
		
	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 */
	public AbstractKernelUseObjectListeningZZZ(){		
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	public AbstractKernelUseObjectListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	
	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectListeningZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelUseObjectListeningNew_();		
	}
	public AbstractKernelUseObjectListeningZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ{
		super(objKernel, sFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_();
	}
	public AbstractKernelUseObjectListeningZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt		
		KernelUseObjectListeningNew_();
	}
	
	public AbstractKernelUseObjectListeningZZZ(IKernelZZZ objKernel, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(objKernel, hmFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_();				
	}
	
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectListeningZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext) throws ExceptionZZZ{
		super(objKernel, objKernelContext);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_();						
	}
	
	public AbstractKernelUseObjectListeningZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		KernelUseObjectListeningNew_();
	}
	
	public AbstractKernelUseObjectListeningZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing,saFlag);
		KernelUseObjectListeningNew_();
	}
	
	private boolean KernelUseObjectListeningNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
							
			bReturn = true;
		}//end main;
		return bReturn;
	}
	
	//+++ aus IStatusLocalMapForStatusLocalUserZZZ
	@Override
	public HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedZZZ> getHashMapEnumSetForStatusLocal() {
		if(this.hmEnumSet==null) {
			this.hmEnumSet = this.createHashMapEnumSetForStatusLocalCustom();
		}
		return this.hmEnumSet;
	}

	@Override
	public void setHashMapEnumSetForStatusLocal(HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedZZZ> hmEnumSet) {
		this.hmEnumSet = hmEnumSet;
	}
	
	@Override
	public HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedZZZ> createHashMapEnumSetForStatusLocalCustom() {
		HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedZZZ>hmReturn = new HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedZZZ>();
		main:{
			
			//Beispiel:
			//Reine Lokale Statuswerte kommen nicht aus einem Event und werden daher nicht gemapped. 
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISSTARTNEW, ServerTrayStatusTypeZZZ.NEW);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISSTARTING, ServerTrayStatusTypeZZZ.STARTING);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISSTARTED, ServerTrayStatusTypeZZZ.STARTED);
//			
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERSTARTNEW, ServerTrayStatusTypeZZZ.STARTED);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERSTARTING, ServerTrayStatusTypeZZZ.LISTENERSTARTING);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERSTARTED, ServerTrayStatusTypeZZZ.LISTENERSTARTED);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERSTARTNO, ServerTrayStatusTypeZZZ.PREVIOUSEVENTRTYPE);//Wieder einen Status im Menue zurueckgehen
//			
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERCONNECTED, ServerTrayStatusTypeZZZ.CONNECTED);
//			hmReturn.put(IServerMainOVPN.STATUSLOCAL.ISLISTENERINTERRUPTED, ServerTrayStatusTypeZZZ.INTERRUPTED);
//			
//			
//			//++++++++++++++++++++++++
//			//Berechne den wirklichen Typen anschliessend, dynamisch. Es wird auf auf einen vorherigen Event zugegriffen durch eine zweite Abfrage
//			hmReturn.put(IClientMainOVPN.STATUSLOCAL.ISPINGSTOPPED, ClientTrayStatusTypeZZZ.PREVIOUSEVENTRTYPE);
//			
//			//+++++++++++++++++++++++
//			
//			hmReturn.put(IClientMainOVPN.STATUSLOCAL.HASPINGERROR, ClientTrayStatusTypeZZZ.FAILED);
//			hmReturn.put(IClientMainOVPN.STATUSLOCAL.HASERROR, ClientTrayStatusTypeZZZ.ERROR);
		}//end main:
		return hmReturn;
	}
	
	
	@Override
	public boolean isEventRelevant(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
}//end class

