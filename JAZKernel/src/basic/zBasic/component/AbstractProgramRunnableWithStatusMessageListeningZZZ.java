package basic.zBasic.component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.component.AbstractProgramRunnableWithStatusMessageZZZ;
import basic.zBasic.component.IModuleZZZ;
import basic.zBasic.component.IProgramRunnableZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalMessageSetUserZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageReactZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusBasicZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalMessageSetZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalMessageSetZZZ;

public abstract class AbstractProgramRunnableWithStatusMessageListeningZZZ extends AbstractProgramRunnableWithStatusMessageZZZ implements IEventBrokerStatusLocalMessageSetUserZZZ, IListenerObjectStatusLocalMessageSetZZZ{
	private static final long serialVersionUID = 6586079955658760005L;		
	protected volatile ISenderObjectStatusLocalMessageSetZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich AUCH ANDERE Objekte registrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
		
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
	public abstract boolean start() throws ExceptionZZZ, InterruptedException;
	
	//#########################################################
	//### aus ISenderObjectStatusLocalMessageSetUserZZZ
	@Override
	public ISenderObjectStatusLocalMessageSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
		if(this.objEventStatusLocalBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender/Broker fuer Aenderungen am Status zu erstellen, der dann registrierte Objekte ueber Aenderung des Status zu informiert
			ISenderObjectStatusLocalMessageSetZZZ objSenderStatusLocal = new KernelSenderObjectStatusLocalMessageSetZZZ();			
			this.objEventStatusLocalBroker = objSenderStatusLocal;
		}		
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageSetZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}

	@Override
	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
	}

	@Override	
	public void unregisterForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
	}
	
	//####### aus IListenerObjectStatusBasicZZZ
	public abstract boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	public abstract boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public abstract boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public abstract boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
	public abstract boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;	
}
