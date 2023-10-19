package basic.zKernel.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import base.files.EncodingMaintypeZZZ.TypeZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.EventObject4ProcessWatchStatusLocalSetZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalSetUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetZZZ;
import basic.zKernel.status.KernelSenderObjectStatusLocalSetZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectWithStatusZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

/**This class receives the stream from a process, which was started by the ConfigStarterZZZ class.
 * This is necessary, because the process will only goon working, if the streams were "catched" by a target.
 * This "catching" will be done in a special thread (one Thread per process).  
 * @author 0823
 *
 */
public abstract class AbstractProcessWatchRunnerZZZ extends AbstractKernelUseObjectWithStatusZZZ implements Runnable, IProcessWatchRunnerZZZ{		
	protected Process objProcess=null; //Der externe process, der hierdurch "gemonitored" werden soll
	protected int iNumber=0;

	public AbstractProcessWatchRunnerZZZ(IKernelZZZ objKernel, Process objProcess, int iNumber, String sFlag) throws ExceptionZZZ{
		super(objKernel);
		String[]saFlag=new String[1];
		saFlag[0]=sFlag;
		ProcessWatchRunnerNew_(objProcess, iNumber, saFlag);
	}
	public AbstractProcessWatchRunnerZZZ(IKernelZZZ objKernel, Process objProcess, int iNumber, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		ProcessWatchRunnerNew_(objProcess, iNumber, saFlag);
	}
	
	private void ProcessWatchRunnerNew_(Process objProcess, int iNumber, String[] saFlagControl) throws ExceptionZZZ{		
		main:{			
			check:{
				if(saFlagControl != null){
					String stemp; boolean btemp;
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							  
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
								
				if(objProcess==null){
					ExceptionZZZ ez = new ExceptionZZZ("Process - Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//END check
	
			this.objProcess = objProcess;
			this.iNumber = iNumber;
		}//END main:
	}
	
	public void run() {
		
		main:{
			try{
				check:{
					
				}//END check:
				String sLog = ReflectCodeZZZ.getPositionCurrent() + " ProcessWatchRunner started for Process #"+ this.getNumber();
				System.out.println(sLog);
				this.logLineDate(sLog);
				
				//Solange laufen, bis ein Fehler auftritt oder eine Verbindung erkannt wird.
				do{
					this.writeOutputToLogPLUSanalyse();		//Man muss wohl erst den InputStream abgreifen, damit der Process weiterlaufen kann.
					
					//Versuch an das spezielle Enum der Klasse heranzukommne
					//Enum objEnum = this.getEnumStatusLocalUsed(); //Aber daraus kann man nicht auf den Konstanten Enum-Namen zugreifen
					//Idee... STATUSLOCAL in einem Interface definieren....
					boolean bHasConnection = this.getStatusLocal(AbstractProcessWatchRunnerZZZ.STATUSLOCAL.HASCONNECTION);										
					if(bHasConnection) {
						sLog = "Connection wurde erstellt. Beende ProcessWatchRunner #"+this.getNumber();
						this.logLineDate(sLog);						
						break;
					}
					
					//System.out.println("FGLTEST02");
					this.writeErrorToLog();
					boolean bError = this.getStatusLocal(AbstractProcessWatchRunnerZZZ.STATUSLOCAL.HASERROR);
					if(bError) break;
										
					//Merke: Da die Events speziell sind, ist dies nichts für eine Abstrakte Klasse.
					//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
					//Dazu braucht es IEventBrokerStatusLocalSetUserZZZ
					//Dann erzeuge den Event und feuer ihn ab.
					//Merke: Nun aber ueber das enum			
//					if(this.getSenderStatusLocalUsed()!=null) {
//						//IEventObjectStatusLocalSetZZZ event = new EventObjectStatusLocalSetZZZ(this,1,ClientMainOVPN.STATUSLOCAL.ISCONNECTED, true);
//						//TODOGOON20230914: Woher kommt nun das Enum? Es gibt ja kein konkretes Beispiel
//						IEventObjectStatusLocalSetZZZ event = new EventObject4ProcessWatchStatusLocalSetZZZ(this,1,(ProcessWatchRunnerZZZ.STATUSLOCAL)null, true);
//						this.getSenderStatusLocalUsed().fireEvent(event);
//					}			
					
					
					//Nach irgendeiner Ausgabe enden ist hier falsch, in einer abstrakten Klasse vielleicht richtig, quasi als Muster.
					//if(this.getFlag("hasOutput")) break;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						ExceptionZZZ ez = new ExceptionZZZ("An InterruptedException happened: '" + e.getMessage() + "''", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					boolean bStopRequested = this.getFlag(IProcessWatchRunnerZZZ.FLAGZ.STOPREQUEST);
					if(bStopRequested) break;				
			}while(true);
			this.setStatusLocal(AbstractProcessWatchRunnerZZZ.STATUSLOCAL.ISSTOPPED, true);
			this.getLogObject().WriteLineDate("ProcessWatchRunner #"+ this.getNumber() + " ended.");
						
		}catch(ExceptionZZZ ez){
			try {
				this.getLogObject().WriteLineDate(ez.getDetailAllLast());
				System.out.println(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				System.out.println(ez.getDetailAllLast());
				e.printStackTrace();
			}
		}
		}//END main
	}
	
	//MErke: Die genaue Analyse muss im konkreten Process Watch Runner gemacht werden.
	public abstract void writeOutputToLogPLUSanalyse() throws ExceptionZZZ;
	
	public void writeErrorToLog() throws ExceptionZZZ{
		main:{			
			try{
				check:{
					if(this.objProcess==null){
						ExceptionZZZ ez = new ExceptionZZZ("Process-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//END check:
			   		
			    BufferedReader err = new BufferedReader(new InputStreamReader(objProcess.getErrorStream()) );
			    for ( String s; (s = err.readLine()) != null; ){
				      //System.out.println( s );
			    	this.getLogObject().WriteLine(this.getNumber() + "# ERROR: "+ s);
			    	this.setStatusLocal(AbstractProcessWatchRunnerZZZ.STATUSLOCAL.HASERROR, true);
			    	Thread.sleep(20);			
			    	if( this.getFlag("stoprequested")==true) break main;
				}
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: '" + e.getMessage() + "'", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InterruptedException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InterruptedException happend: '"+ e.getMessage() + "'", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//END Main:	
	}
	

	
	/**TODO Ich weiss noch garnicht, was ich senden soll und wie es gehen kann.
	 * Ziel w�re es z.B. mit der Test F4 den Process herunterzufahren.
	 * @param sOut
	 * @throws ExceptionZZZ, 
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 07.07.2006 - 17:29:31
	 */
	public void sendStringToProcess(String sOut) throws ExceptionZZZ{
		main:{
			try{
				check:{
					if(this.objProcess==null){
						ExceptionZZZ ez = new ExceptionZZZ("Process-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(sOut==null)  break main; //Absichltich keine Exception					
				}//END check:
			
				BufferedWriter out = new BufferedWriter( new OutputStreamWriter(objProcess.getOutputStream()) );
				out.write(sOut);
			
				this.getLogObject().WriteLineDate(this.getNumber() +"# STRING SEND TO PROCESS: "+ sOut);
				this.setFlag("hasInput", true);
				
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: '" + e.getMessage() + "'", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//END main:
	}
	
	//###### FLAGS
	
	//### Aus IProcessWatchRunnerZZZ, analog zu IFlagUserZZZ ##########################
		@Override
		public boolean getFlag(IProcessWatchRunnerZZZ.FLAGZ objEnumFlag) {
			return this.getFlag(objEnumFlag.name());
		}
		@Override
		public boolean setFlag(IProcessWatchRunnerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}
		
		@Override
		public boolean[] setFlag(IProcessWatchRunnerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IProcessWatchRunnerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
		public boolean proofFlagExists(IProcessWatchRunnerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}	
		
		@Override
		public boolean proofFlagSetBefore(IProcessWatchRunnerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}	
	
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used: 
	- hasError
	- hasOutput
	- hasInput
	- stoprequested
	 */
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
		
			//getting the flags of this object
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("haserror")){
//				bFunction = bFlagHasError;
//				break main;
//			}else if(stemp.equals("hasconnection")) {
//				bFunction = bFlagHasConnection;
//				break main;
//			}
			
		}//end main:
		return bFunction;
	}
	
	

	/**
	 * @see AbstractKernelUseObjectZZZ.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 	- hasError
	- hasOutput
	- hasInput
	- stoprequested
	 * @throws ExceptionZZZ 
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
		boolean bFunction = false;
		main:{			
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
		
		//setting the flags of this object
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("haserror")){
//			bFlagHasError = bFlagValue;
//			bFunction = true;
//			break main;
//		}else if(stemp.equals("hasconnection")) {
//			bFlagHasConnection = bFlagValue;
//			bFunction = true;
//			break main;
//		}

		}//end main:
		return bFunction;
	}
	
	
	
	//###### GETTER / SETTER
	
	//+++ aus IProcessWatchRunnerZZZ
	
	/**Returns the process - object passed as a parameter of the constructor 
	 * Hint: Therefore is no setter-method available
	 * @return Process
	 *
	 * javadoc created by: 0823, 06.07.2006 - 16:48:57
	 */
	@Override
	public Process getProcessObject(){
		return this.objProcess;
	}
	
	@Override
	public void setProcessObject(Process objProcess) {
		this.objProcess = objProcess;
	}
	
	/**Returns a number passed as a parameter of the constructor
	 * This number should allow the object to identify itself. E.g. when writing to the log.
	 * @return int
	 *
	 * javadoc created by: 0823, 06.07.2006 - 17:52:34
	 */
	@Override
	public int getNumber(){
		return this.iNumber;
	}
	
	@Override
	public void setNumber(int iNumber) {
		this.iNumber = iNumber;
	}
	
	@Override
	public abstract boolean analyseInputLineCustom(String sLine) throws ExceptionZZZ;

	
	//### Aus IStatusLocalUserZZZ
	
	//Merke: Hier wird zusätzlich noch ein Event gefeuert
	@Override
	public boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofStatusLocalExists(sStatusName);															
			if(bFunction){
				
				bFunction = this.proofStatusLocalChanged(sStatusName, bStatusValue);
				if(bFunction) {		
					
					//Holes die HashMap
					HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
					//Setze den erwiesenermassen geaenderten Status nun in die HashMap
					hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
					
					//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
					//Dann erzeuge den Event und feuer ihn ab.
//					if(this.getSenderStatusLocalUsed()!=null) {
//						IEventObjectStatusLocalSetZZZ event = new EventObject4ProcessWatchStatusLocalSetZZZ(this,1,sStatusName.toUpperCase(), bStatusValue);
//						this.getSenderStatusLocalUsed().fireEvent(event);
//					}
					
					bFunction = true;
				}
			}										
		}	// end main:
		
		return bFunction;
	}
		
    //### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getEnumStatusLocalClass(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return STATUSLOCAL.class;    	
    }


}//END class
