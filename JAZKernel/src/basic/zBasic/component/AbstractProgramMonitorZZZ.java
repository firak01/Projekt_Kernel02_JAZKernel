package basic.zBasic.component;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.AbstractObjectWithStatusMonitoringZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

public abstract class AbstractProgramMonitorZZZ extends AbstractObjectWithStatusMonitoringZZZ implements IProgramMonitorZZZ{
	private static final long serialVersionUID = 6586079955658760005L;	
	protected volatile IModuleZZZ objModule=null; //Das Modul, in der KernelUI - Variante wäre das die Dialogbox aus der das Program gestartet wird.	
	protected volatile String sProgramName = null;
	protected volatile String sModuleName = null;
	
	protected volatile ArrayList<IProgramRunnableZZZ> listaRunnable = null;//Die Liste der Runnable-Threadfaehigen Objekte, mit Status....
			
	public AbstractProgramMonitorZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramMonitorZZZ(String[] saFlag) throws ExceptionZZZ {
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
	public void setProgramRunnableList(ArrayList<IProgramRunnableZZZ> listaRunnables) {
		this.listaRunnable = listaRunnables;
	}
	
	@Override
	public ArrayList<IProgramRunnableZZZ> getProgramRunnableList() {
		if(this.listaRunnable==null) {
			ArrayList<IProgramRunnableZZZ> listaRunnables = new ArrayList<IProgramRunnableZZZ> ();
			this.listaRunnable = listaRunnables;
		}
		return this.listaRunnable;
	}
	
	@Override
	public void addProgramRunnable(IProgramRunnableZZZ objProgramRunnable) throws ExceptionZZZ {
		this.getProgramRunnableList().add(objProgramRunnable);
		
		//Registriere diesen Monitor sofort an dem Event werfenden Program, aber nur wenn das Interface passt.
		if(objProgramRunnable instanceof IListenerObjectStatusLocalZZZ) {
			this.registerForStatusLocalEvent((IListenerObjectStatusLocalZZZ) objProgramRunnable);
		}
	}
	
	
	//#### METHODEN	
	@Override
	public boolean start() throws ExceptionZZZ {
		return this.startCustom();
	}
	
	@Override
	public boolean startProgramRunnableAll() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			ArrayList<IProgramRunnableZZZ> listaProgram = this.getProgramRunnableList();
			if(listaProgram.isEmpty())break main;
			
			for(IProgramRunnableZZZ objProgram : listaProgram) {
				boolean bValue = objProgram.startAsThread();
				if(!bValue)break main;
			}
		}//end main:
		return bReturn;
	}
	
	
	@Override
	abstract public boolean startCustom() throws ExceptionZZZ;
	
	//### Aus IProgramZZZ
		@Override
		public String getProgramName(){
			if(StringZZZ.isEmpty(this.sProgramName)) {
				if(this.getFlag(IProgramZZZ.FLAGZ.ISPROGRAM.name())) {
					this.sProgramName = this.getClass().getName();
				}
			}
			return this.sProgramName;
		}
		
		@Override
		public String getProgramAlias() throws ExceptionZZZ {		
			return null;
		}
			
		@Override
		public void resetProgramUsed() {
			this.sProgramName = null;
		}

		@Override
		public boolean reset() throws ExceptionZZZ {
			this.resetProgramUsed();
			this.resetModuleUsed();
			this.resetFlags();
			return true;
		}
		

		//###########################################
		//### FLAGZ IProgramZZZ
		//###########################################
		@Override
		public boolean getFlag(IProgramZZZ.FLAGZ objEnumFlag) {
			return this.getFlag(objEnumFlag.name());
		}
		@Override
		public boolean setFlag(IProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}
		
		@Override
		public boolean[] setFlag(IProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IProgramZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
		
		@Override
		public boolean proofFlagExists(IProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
			}
		
		@Override
		public boolean proofFlagSetBefore(IProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}	

		//###########################################
		//### FLAGZ IModuleUserZZZ
		//###########################################
		@Override
		public boolean getFlag(IModuleUserZZZ.FLAGZ objEnumFlag) {
			return this.getFlag(objEnumFlag.name());
		}
		@Override
		public boolean setFlag(IModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}
		
		@Override
		public boolean[] setFlag(IModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IModuleUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
		
		@Override
		public boolean proofFlagExists(IModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
		
		//### Aus IKernelModuleUserZZZ	
		@Override
		public String readModuleName() throws ExceptionZZZ {
			String sReturn = null;
			main:{
				IModuleZZZ objModule = this.getModule();
				if(objModule!=null) {
					sReturn = objModule.getModuleName();
				}
			}//end main:
			return sReturn;
		}
		
		@Override
		public String getModuleName() throws ExceptionZZZ{
			if(StringZZZ.isEmpty(this.sModuleName)) {
				this.sModuleName = this.readModuleName();
			}
			return this.sModuleName;
		}
		
		@Override
		public void setModuleName(String sModuleName){
			this.sModuleName=sModuleName;
		}
		
		@Override
		public void resetModuleUsed() {
			this.objModule = null;
			this.sModuleName = null;
		}
		
		@Override
		public IModuleZZZ getModule() {
			return this.objModule;
		}
		
		@Override
		public void setModule(IModuleZZZ objModule) {
			this.objModule = objModule;
		}
	
	
	//###################################################
	//### FLAGS IProgramMonitorZZZ ######################
	//###################################################
	
	@Override
	public boolean getFlag(IProgramMonitorZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IProgramMonitorZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IProgramMonitorZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IProgramMonitorZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IProgramMonitorZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IProgramMonitorZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
		
	//############################
	//### STATUS
	//############################
	@Override
	public boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(objEnumStatusIn==null) {
				break main;
			}
			
			//Merke: Bei einer anderen Klasse, die dieses DesingPattern nutzt, befindet sich der STATUSLOCAL in einer anderen Klasse
			IProgramMonitorZZZ.STATUSLOCAL enumStatus = (IProgramMonitorZZZ.STATUSLOCAL) objEnumStatusIn;
			String sStatusName = enumStatus.name();
			if(StringZZZ.isEmpty(sStatusName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapStatusLocal();
			Boolean objBoolean = hmFlag.get(sStatusName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	

	@Override
	public boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectWithStatusZZZ#offerStatusLocal(int, java.lang.Enum, java.lang.String, boolean)
	 */
	@Override
	public boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) {
				break main;
			}
			
			IProgramMonitorZZZ.STATUSLOCAL enumStatus = (IProgramMonitorZZZ.STATUSLOCAL) enumStatusIn;
			
			bFunction = this.offerStatusLocal_(enumStatus, sStatusMessage, bStatusValue);				
		}//end main;
		return bFunction;
	}
	
	private boolean offerStatusLocal_(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) break main;
			
		
	    //Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
		IProgramMonitorZZZ.STATUSLOCAL enumStatus = (IProgramMonitorZZZ.STATUSLOCAL) enumStatusIn;
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
	
	
	//##########################################################

}
