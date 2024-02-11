package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalMessageReactUserZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageReactRunnableZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalMessageReactZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalMessageReactZZZ;

public abstract class AbstractProgramWithFlagRunnableOnStatusMessageListeningZZZ extends AbstractProgramWithFlagRunnableZZZ implements IEventBrokerStatusLocalMessageReactUserZZZ, IListenerObjectStatusLocalMessageReactRunnableZZZ{
	protected volatile ISenderObjectStatusLocalMessageReactZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
		
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractProgramWithFlagRunnableOnStatusMessageListeningZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractProgramWithFlagRunnableOnStatusMessageListeningZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	public AbstractProgramWithFlagRunnableOnStatusMessageListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);		
	}
			
	//### aus ISenderObjectStatusLocalMessageReactZZZ#getSenderStatusLocalUsed()
	@Override
	public ISenderObjectStatusLocalMessageReactZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
		if(this.objEventStatusLocalBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender fuer Aenderungen an den Flags zu erstellen, der dann registrierte Objekte ueber Aenderung von Flags informiert
			ISenderObjectStatusLocalMessageReactZZZ objSenderStatusLocal = new KernelSenderObjectStatusLocalMessageReactZZZ();			
			this.objEventStatusLocalBroker = objSenderStatusLocal;
		}		
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageReactZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}
		
	//### aus IEventBrokerStatusLocalReactUserZZZ
	@Override	
	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
	}

	@Override
	public void unregisterForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
	}
	//##########################
		
	@Override
	public abstract boolean reactOnStatusLocalEvent(IEventObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ;
		
	
}
