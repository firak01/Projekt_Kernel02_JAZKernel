package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalMessageUserZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageReactRunnableZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalMessageZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalMessageZZZ;

public abstract class AbstractProgramRunnableWithStatusMessageListeningZZZ extends AbstractProgramRunnableWithStatusMessageZZZ implements IEventBrokerStatusLocalMessageUserZZZ, IListenerObjectStatusLocalMessageReactRunnableZZZ{
	private static final long serialVersionUID = 6586079955658760005L;		
	protected volatile ISenderObjectStatusLocalMessageZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich AUCH ANDERE Objekte registrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
		
	public AbstractProgramRunnableWithStatusMessageListeningZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramRunnableWithStatusMessageListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super();	
		AbstractProgramRunnableWithStatusListeningNew_(saFlag);
	}
	
	private boolean AbstractProgramRunnableWithStatusListeningNew_(String[] saFlagControl) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(saFlagControl != null){
				String stemp; boolean btemp;
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 						
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
						
			//Das Programm sollte sich auf jeden Fall am eigenen ObjectBroker registrieren
			this.getSenderStatusLocalUsed().addListenerObject(this);
		}//end main:
		return bReturn;
	}

	//#### GETTER / SETTER
	@Override
	public abstract boolean start() throws ExceptionZZZ;
	
	//#########################################################
	//### aus ISenderObjectStatusLocalMessageSetUserZZZ
	@Override
	public ISenderObjectStatusLocalMessageZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
		if(this.objEventStatusLocalBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender/Broker fuer Aenderungen am Status zu erstellen, der dann registrierte Objekte ueber Aenderung des Status zu informiert
			ISenderObjectStatusLocalMessageZZZ objSenderStatusLocal = new KernelSenderObjectStatusLocalMessageZZZ();			
			this.objEventStatusLocalBroker = objSenderStatusLocal;
		}		
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}

	@Override
	public void registerForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
	}

	@Override	
	public void unregisterForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
	}
	
	//####### aus IListenerObjectStatusLocalZZZ
	@Override
	public abstract boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	
	@Override
	public abstract boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
	@Override
	public abstract boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
	@Override
	public abstract boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
	@Override
	public abstract boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet) throws ExceptionZZZ;	
}
