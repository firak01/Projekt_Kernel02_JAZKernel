package basic.zBasic.component;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalMessageReactRunnableZZZ;

public abstract class AbstractProgramRunnableMonitorZZZ extends AbstractProgramRunnableWithStatusMessageListeningMonitoredZZZ implements IProgramRunnableMonitorZZZ{
	private static final long serialVersionUID = 6586079955658760005L;		
	protected volatile ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> listaRunnableWithStatusMessage = null;//Die Liste der Runnable-Threadfaehigen Objekte, mit Status....
			
	public AbstractProgramRunnableMonitorZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramRunnableMonitorZZZ(String[] saFlag) throws ExceptionZZZ {
		super();	
		AbstractProgramRunnableMonitorNew_(saFlag);
	}
	
	private boolean AbstractProgramRunnableMonitorNew_(String[] saFlagControl) throws ExceptionZZZ {
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
	public void setProgramRunnableList(ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> listaRunnables) {
		this.listaRunnableWithStatusMessage = listaRunnables;
	}
	
	@Override
	public ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> getProgramRunnableList() {
		if(this.listaRunnableWithStatusMessage==null) {
			ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> listaRunnables = new ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> ();
			this.listaRunnableWithStatusMessage = listaRunnables;
		}
		return this.listaRunnableWithStatusMessage;
	}
	
	@Override
	public void addProgramRunnable(IListenerObjectStatusLocalMessageReactRunnableZZZ objProgramRunnable) throws ExceptionZZZ {
		this.getProgramRunnableList().add(objProgramRunnable);
		
		//Registriere diesen Monitor sofort an dem Event werfenden Program
		this.registerForStatusLocalEvent(objProgramRunnable);
		
	}
	
	
	//#### METHODEN
	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
				Thread objThreadMonitor = new Thread(this);
				objThreadMonitor.start();//Damit wird run() aufgerufen, was wiederum start_() als private Methode aufruft
				
				bReturn = true;								
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean startProgramRunnableAll() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			ArrayList<IListenerObjectStatusLocalMessageReactRunnableZZZ> listaProgram = this.getProgramRunnableList();
			if(listaProgram.isEmpty())break main;
			
			for(IListenerObjectStatusLocalMessageReactRunnableZZZ objProgram : listaProgram) {
				boolean bValue = objProgram.start();
				if(!bValue)break main;
			}
		}//end main:
		return bReturn;
	}
	
	
	//#########################################################
	//### aus ISenderObjectStatusLocalMessageSetUserZZZ
//	@Override
//	public ISenderObjectStatusLocalMessageSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
//		if(this.objEventStatusLocalBroker==null) {
//			//++++++++++++++++++++++++++++++
//			//Nun geht es darum den Sender/Broker fuer Aenderungen am Status zu erstellen, der dann registrierte Objekte ueber Aenderung des Status zu informiert
//			ISenderObjectStatusLocalMessageSetZZZ objSenderStatusLocal = new KernelSenderObjectStatusLocalMessageSetZZZ();			
//			this.objEventStatusLocalBroker = objSenderStatusLocal;
//		}		
//		return this.objEventStatusLocalBroker;
//	}
//
//	@Override
//	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageSetZZZ objEventSender) {
//		this.objEventStatusLocalBroker = objEventSender;
//	}
//
//	@Override
//	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
//		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
//	}
//
//	@Override	
//	public void unregisterForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
//		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
//	}
//	
//	//####### aus IListenerObjectStatusBasicZZZ
//	public abstract boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
//	public abstract boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
//	public abstract boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
//	public abstract boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
//	public abstract boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusBasicZZZ eventStatusLocalSet) throws ExceptionZZZ;
	
	
	@Override
	public boolean offerStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) {
				break main;
			}
			
			IProgramRunnableMonitorZZZ.STATUSLOCAL enumStatus = (IProgramRunnableMonitorZZZ.STATUSLOCAL) enumStatusIn;
			
			bFunction = this.offerStatusLocal_(iIndexOfProcess, enumStatus, sStatusMessage, bStatusValue);				
		}//end main;
		return bFunction;
	}
	
	private boolean offerStatusLocal_(int iIndexOfProcess, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) break main;
			
		
	    //Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
		IProgramRunnableMonitorZZZ.STATUSLOCAL enumStatus = (IProgramRunnableMonitorZZZ.STATUSLOCAL) enumStatusIn;
		String sStatusName = enumStatus.name();
		bFunction = this.proofStatusLocalExists(sStatusName);															
		if(!bFunction) {
			String sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor for Process would like to fire event, but this status is not available: '" + sStatusName + "'";
			System.out.println(sLog);
			this.logProtocolString(sLog);			
			break main;
		}
			
		bFunction = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
		if(!bFunction) {
			String sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor would like to fire event, but this status has not changed: '" + sStatusName + "'";
			System.out.println(sLog);
			this.logProtocolString(sLog);
			break main;
		}	
		
		//++++++++++++++++++++	
		//Setze den Status nun in die HashMap
		HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
		hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
		
		//Den enumStatus als currentStatus im Objekt speichern...
		//                   dito mit dem "vorherigen Status"
		//Setze nun das Enum, und damit auch die Default-StatusMessage
		String sStatusMessageToSet = null;
		if(StringZZZ.isEmpty(sStatusMessage)){
			if(bStatusValue) {
				sStatusMessageToSet = enumStatus.getStatusMessage();
			}else {
				sStatusMessageToSet = "NICHT " + enumStatus.getStatusMessage();
			}			
		}else {
			sStatusMessageToSet = sStatusMessage;
		}
		
		String sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerMain verarbeite sStatusMessageToSet='" + sStatusMessageToSet + "'";
		System.out.println(sLog);
		this.logProtocolString(sLog);

		//Falls eine Message extra uebergeben worden ist, ueberschreibe...
		if(sStatusMessageToSet!=null) {
			sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerMain setze sStatusMessageToSet='" + sStatusMessageToSet + "'";
			System.out.println(sLog);
			this.logProtocolString(sLog);
		}
		//Merke: Dabei wird die uebergebene Message in den speziellen "Ringspeicher" geschrieben, auch NULL Werte...
		this.offerStatusLocalEnum(enumStatus, bStatusValue, sStatusMessageToSet);
		
		
		
		//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
		//Dann erzeuge den Event und feuer ihn ab.	
		if(this.getSenderStatusLocalUsed()==null) {
			sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor for Process would like to fire event '" + enumStatus.getAbbreviation() + "', but no objEventStatusLocalBroker available, any registered?";
			System.out.println(sLog);
			this.logProtocolString(sLog);		
			break main;
		}
		
		//Erzeuge fuer das Enum einen eigenen Event. Die daran registrierten Klassen koennen in einer HashMap definieren, ob der Event fuer sie interessant ist.		
		sLog = ReflectCodeZZZ.getPositionCurrent() + ": Erzeuge Event fuer '" + sStatusName + "'";
		System.out.println(sLog);
		this.logProtocolString(sLog);
		IEventObjectStatusBasicZZZ event = new EventObjectStatusLocalZZZ(this, enumStatus, bStatusValue);			
//		event.setApplicationObjectUsed(this.getMainObject().getApplicationObject());
					
		//das ClientStarterObjekt nun auch noch dem Event hinzufuegen
//		sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor for Process iIndex= '" + iIndexOfProcess + "'";
//		System.out.println(sLog);
//		this.logProtocolString(sLog);
//		if(iIndexOfProcess>=0) {
//			event.setServerConfigStarterObjectUsed(this.getMainObject().getServerConfigStarterList().get(iIndexOfProcess));
//		}		
		
		sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor for Process fires event '" + enumStatus.getAbbreviation() + "'";
		System.out.println(sLog);
		this.logProtocolString(sLog);
		this.getSenderStatusLocalUsed().fireEvent(event);
				
		bFunction = true;				
	}	// end main:
	return bFunction;
	}
	
	//#######################################
	@Override
	public boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocal(Enum enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sMessage, boolean bStatusValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, String sMessage, boolean bStatusValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocalEnum(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped,
			boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setStatusLocalEnum(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, String sMessage,
			boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	//#########################################################
	
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IEnumSetMappedStatusZZZ getStatusLocalEnumPrevious(int iIndexStepsBack) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//#########################################################
	
	@Override
	abstract public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;

	@Override
	abstract public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal)throws ExceptionZZZ;

	@Override
	abstract public boolean isEventRelevantByClass2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet)throws ExceptionZZZ;

	@Override
	abstract public boolean isEventRelevantByStatusLocal2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet)throws ExceptionZZZ;

	@Override
	abstract public boolean isEventRelevantByStatusLocalValue2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalSet)throws ExceptionZZZ;

	@Override
	abstract public boolean isEventRelevant(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ;

	
}
