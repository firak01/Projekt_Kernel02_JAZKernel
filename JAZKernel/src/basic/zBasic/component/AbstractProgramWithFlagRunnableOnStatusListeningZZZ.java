package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalMessageReactUserZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalMessageSetUserZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalReactUserZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalSetUserZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageReactZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalReactZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusBasicZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalMessageReactZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalReactZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalBasicZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalReactZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalSetZZZ;

public abstract class AbstractProgramWithFlagRunnableOnStatusListeningZZZ extends AbstractProgramWithFlagRunnableZZZ implements IEventBrokerStatusLocalReactUserZZZ, IListenerObjectStatusLocalReactZZZ{
	protected volatile ISenderObjectStatusLocalReactZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
	
	public AbstractProgramWithFlagRunnableOnStatusListeningZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractProgramWithFlagRunnableOnStatusListeningZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	public AbstractProgramWithFlagRunnableOnStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);		
	}
	
	//### aus ISenderObjectStatusLocalReactUserZZZ
	@Override
	public ISenderObjectStatusLocalReactZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {	
		if(this.objEventStatusLocalBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender fuer Aenderungen an den Flags zu erstellen, der dann registrierte Objekte ueber Aenderung von Flags informiert
			ISenderObjectStatusLocalReactZZZ objSenderStatusLocal = new KernelSenderObjectStatusLocalReactZZZ();			
			this.objEventStatusLocalBroker = objSenderStatusLocal;
		}		
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalReactZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}
	
	
	//### aus IEventBrokerStatusLocalSetUserZZZ
	@Override	
	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
	}

	@Override
	public void unregisterForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
	}
		
	//############################

	@Override
	public abstract boolean reactOnStatusLocalEvent(IEventObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
	
	
	
}
