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

import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.EventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalSetUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;

/**This class receives the stream from a process, which was started by the ConfigStarterZZZ class.
 * This is necessary, because the process will only goon working, if the streams were "catched" by a target.
 * This "catching" will be done in a special thread (one Thread per process).  
 * @author 0823
 *
 */
public abstract class AbstractProcessWatchRunnerZZZ extends KernelUseObjectZZZ implements Runnable, IProcessWatchRunnerZZZ, IEventBrokerStatusLocalSetUserZZZ{	
	private HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	protected ISenderObjectStatusLocalSetZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
	
	TODOGOON20230915;//Auf StatusLocal statt Flags umstellen UND dann ggfs. den Monitor daran "registrieren".

	private Process objProcess=null; //Der externe process, der hierdurch "gemonitored" werden soll
	private int iNumber=0;

	
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
			String sLog = "ProcessWatchRunner #"+ this.getNumber() + " started.";
			this.logLineDate(sLog);
			
			//Solange laufen, bis ein Fehler auftritt oder eine Verbindung erkannt wird.
			do{
				this.writeErrorToLog();
				if(this.getFlag("hasError")) break;

				this.writeOutputToLog();		//Man muss wohl erst den InputStream abgreifen, damit der Process weiterlaufen kann.
				if(this.getFlag("hasConnection")) {
					sLog = "Connection wurde erstellt. Beende ProcessWatchRunner #"+this.getNumber();
					this.logLineDate(sLog);						
					break;
				}
				
				//TODOGOON: Statt des FlagHandling localStatus verwenden...				
				
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				//Merke: Nun aber ueber das enum			
				if(this.objEventStatusLocalBroker!=null) {
					//IEventObjectStatusLocalSetZZZ event = new EventObjectStatusLocalSetZZZ(this,1,ClientMainOVPN.STATUSLOCAL.ISCONNECTED, true);
					//TODOGOON20230914: Woher kommt nun das Enum? Es gibt ja kein konkretes Beispiel
					IEventObjectStatusLocalSetZZZ event = new EventObjectStatusLocalSetZZZ(this,1,(Enum)null, true);
					this.objEventStatusLocalBroker.fireEvent(event);
				}			
				
				
				//Nach irgendeiner Ausgabe enden ist hier falsch, in einer abstrakten Klasse vielleicht richtig, quasi als Muster.
				//if(this.getFlag("hasOutput")) break;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					ExceptionZZZ ez = new ExceptionZZZ("An InterruptedException happened: '" + e.getMessage() + "''", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if( this.getFlag("stoprequested")==true) break;					
		}while(true);
		this.setFlag(IProcessWatchRunnerZZZ.FLAGZ.ENDED,true);
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
		    	this.setFlag("hasError", true);
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
	
	/** In dieser Methode werden die Ausgabezeilen eines Batch-Prozesses ( cmd.exe ) 
	 *  aus dem Standard - Output gelesen.
	 *  - Sie werden in das Kernel-Log geschrieben.
	 *  - Sie werden hinsichtlich bestimmter Schluesselsaetze analysiert,
	 *    um z.B. den erfolgreichen Verbindungsaufbau mitzubekommen.
	 *  
	 *  Merke: 
	 *  Merke1: Der aufgerufene OVPN-Prozess stellt irgendwann das schreiben ein
					//Das ist dann z.B. der letzte Eintrag
					//0#Sat Sep 02 07:39:48 2023 us=571873 NOTE: --mute triggered... 
					//Der wert wird in der OVPN-Konfiguration eingestellt, z.B.:
					//mute=20  
				
	 * Merke2: Wie über einen Erfolg benachrichtigen?
			   Wenn die Verbindung erstellt wird, steht folgendes im Log.
			   
TCP connection established with [AF_INET]192.168.3.116:4999
0#Sat Sep 02 12:53:10 2023 us=223095 TCPv4_CLIENT link local: [undef]
0#Sat Sep 02 12:53:10 2023 us=223095 TCPv4_CLIENT link remote: [AF_INET]192.168.3.116:4999
0#Sat Sep 02 12:53:10 2023 us=223095 TLS: Initial packet from [AF_INET]192.168.3.116:4999, sid=75fbf19d 73f20fdc
0#Sat Sep 02 12:53:10 2023 us=363726 VERIFY OK: depth=1, C=DE, ST=PREUSSEN, L=BERLIN, O=OpenVPN, OU=TEST, CN=PAUL.HINDENBURG, name=PAUL.HINDENBURG, emailAddress=paul.hindenburg@mailinator.com\09
0#Sat Sep 02 12:53:10 2023 us=363726 VERIFY OK: depth=0, C=DE, ST=PREUSSEN, L=BERLIN, O=OpenVPN, OU=TEST, CN=HANNIBALDEV06VM_SERVER, name=HANNIBALDEV06VM, emailAddress=paul.hindenburg@mailinator.com\09
0#Sat Sep 02 12:53:10 2023 us=551235 Data Channel Encrypt: Cipher 'BF-CBC' initialized with 128 bit key
0#Sat Sep 02 12:53:10 2023 us=551235 WARNING: INSECURE cipher with block size less than 128 bit (64 bit).  This allows attacks like SWEET32.  Mitigate by using a --cipher with a larger block size (e.g. AES-256-CBC).
0#Sat Sep 02 12:53:10 2023 us=551235 Data Channel Encrypt: Using 160 bit message hash 'SHA1' for HMAC authentication
0#Sat Sep 02 12:53:10 2023 us=551235 Data Channel Decrypt: Cipher 'BF-CBC' initialized with 128 bit key
0#Sat Sep 02 12:53:10 2023 us=551235 WARNING: INSECURE cipher with block size less than 128 bit (64 bit).  This allows attacks like SWEET32.  Mitigate by using a --cipher with a larger block size (e.g. AES-256-CBC).
0#Sat Sep 02 12:53:10 2023 us=551235 Data Channel Decrypt: Using 160 bit message hash 'SHA1' for HMAC authentication
0#Sat Sep 02 12:53:10 2023 us=551235 Control Channel: TLSv1.2, cipher TLSv1/SSLv3 DHE-RSA-AES256-GCM-SHA384, 1024 bit RSA
0#Sat Sep 02 12:53:10 2023 us=551235 [HANNIBALDEV06VM_SERVER] Peer Connection Initiated with [AF_INET]192.168.3.116:4999
0#Sat Sep 02 12:53:13 2023 us=20060 SENT CONTROL [HANNIBALDEV06VM_SERVER]: 'PUSH_REQUEST' (status=1)
0#Sat Sep 02 12:53:13 2023 us=176313 PUSH: Received control message: 'PUSH_REPLY,ping 10,ping-restart 240,ifconfig 10.0.0.2 10.0.0.1'
0#Sat Sep 02 12:53:13 2023 us=176313 OPTIONS IMPORT: timers and/or timeouts modified
0#Sat Sep 02 12:53:13 2023 us=176313 OPTIONS IMPORT: --ifconfig/up options modified
0#Sat Sep 02 12:53:13 2023 us=176313 do_ifconfig, tt->ipv6=0, tt->did_ifconfig_ipv6_setup=0
0#Sat Sep 02 12:53:13 2023 us=176313 ******** NOTE:  Please manually set the IP/netmask of 'OpenVPN2' to 10.0.0.2/255.255.255.252 (if it is not already set)
0#Sat Sep 02 12:53:13 2023 us=176313 open_tun, tt->ipv6=0
0#Sat Sep 02 12:53:13 2023 us=176313 TAP-WIN32 device [OpenVPN2] opened: \\.\Global\{9B00449E-0F90-4137-A063-CEA05D846AD8}.tap
0#Sat Sep 02 12:53:13 2023 us=176313 TAP-Windows Driver Version 9.9 
0#Sat Sep 02 12:53:13 2023 us=176313 TAP-Windows MTU=1500
0#Sat Sep 02 12:53:13 2023 us=176313 Sleeping for 3 seconds...
2023-9-2_12_53: Thread # 0 not jet ended or has reported an error.
0#Sat Sep 02 12:53:16 2023 us=176370 Successful ARP Flush on interface [4] {9B00449E-0F90-4137-A063-CEA05D846AD8}
0#Sat Sep 02 12:53:17 2023 us=410769 TEST ROUTES: 0/0 succeeded len=0 ret=0 a=0 u/d=down
0#Sat Sep 02 12:53:17 2023 us=410769 Route: Waiting for TUN/TAP interface to come up...
0#Sat Sep 02 12:53:18 2023 us=645168 TEST ROUTES: 0/0 succeeded len=0 ret=1 a=0 u/d=up
0#Sat Sep 02 12:53:18 2023 us=645168 WARNING: this configuration may cache passwords in memory -- use the auth-nocache option to prevent this
0#Sat Sep 02 12:53:18 2023 us=645168 Initialization Sequence Completed
					 
	 *  
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.09.2023, 07:35:31
	 */
	public void writeOutputToLog() throws ExceptionZZZ{	
		main:{
			try{
				check:{
					if(this.objProcess==null){
						ExceptionZZZ ez = new ExceptionZZZ("Process-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//END check:
			
				BufferedReader in = new BufferedReader( new InputStreamReader(objProcess.getInputStream()) );
				for ( String s; (s = in.readLine()) != null; ){
				    //System.out.println( s );
					this.getLogObject().WriteLine(this.getNumber() +"#"+ s);
					this.setFlag(IProcessWatchRunnerZZZ.FLAGZ.HASOUTPUT, true);
					
					boolean bAny = this.analyseInputLineCustom(s);
														
					Thread.sleep(20);
					boolean bStopRequested = this.getFlag(IProcessWatchRunnerZZZ.FLAGZ.STOPREQUEST);
					if( bStopRequested) break main;
				}								
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: '" + e.getMessage() + "'", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InterruptedException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InterruptedException happend: '"+ e.getMessage() + "'", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//END main:
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
//			}else if(stemp.equals("hasoutput")){
//				bFunction = bFlagHasOutput;
//				break main;
//			}else if(stemp.equals("hasinput")){
//				bFunction = bFlagHasInput;
//				break main;
//			}else if(stemp.equals("stoprequested")){
//				bFunction = bFlagStopRequested;
//				break main;
//			}
		}//end main:
		return bFunction;
	}
	
	

	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
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
//		}else if(stemp.equals("hasoutput")){
//			bFlagHasOutput = bFlagValue;
//			bFunction = true;
//			break main;
//		}else if(stemp.equals("hasinput")){
//			bFlagHasInput = bFlagValue;
//			bFunction = true;
//			break main;
//		}else if(stemp.equals("stoprequested")){
//			bFlagStopRequested = bFlagValue;
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

	//### aus IEventBrokerStatusLocalSetUserZZZ
	@Override
	public ISenderObjectStatusLocalSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalSetZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}
	
	//######
	@Override
	public HashMap<String, Boolean> getHashMapStatusLocal() {
		return this.hmStatusLocal;
	}

	@Override
	public void setHashMapStatusLocal(HashMap<String, Boolean> hmStatusLocal) {
		this.hmStatusLocal = hmStatusLocal;
	}

	@Override
	public boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(objEnumStatusIn==null) {
				break main;
			}
			
			//Merke: Bei einer anderen Klasse, die dieses DesingPattern nutzt, befindet sich der STATUSLOCAL in einer anderen Klasse
			AbstractProcessWatchRunnerZZZ.STATUSLOCAL enumStatus = (STATUSLOCAL) objEnumStatusIn;
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
	public boolean getStatusLocal(String sStatusName) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName)) break main;
										
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			Boolean objBoolean = hmStatus.get(sStatusName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}

	@Override
	public boolean setStatusLocal(Enum objEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(objEnumStatusIn==null) {
				break main;
			}
		//return this.getStatusLocal(objEnumStatus.name());
		//Nein, trotz der Redundanz nicht machen, da nun der Event anders gefeuert wird, nämlich über das enum
		
	    //Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
	    AbstractProcessWatchRunnerZZZ.STATUSLOCAL enumStatus = (STATUSLOCAL) objEnumStatusIn;
		String sStatusName = enumStatus.name();
		bFunction = this.proofStatusLocalExists(sStatusName);															
		if(bFunction == true){
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
		
			//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
			//Dann erzeuge den Event und feuer ihn ab.
			//Merke: Nun aber ueber das enum			
			if(this.objEventStatusLocalBroker!=null) {
				IEventObjectStatusLocalSetZZZ event = new EventObjectStatusLocalSetZZZ(this,1,enumStatus, bStatusValue);
				this.objEventStatusLocalBroker.fireEvent(event);
			}			
			bFunction = true;								
		}										
	}	// end main:
	return bFunction;
	}

	@Override
	public boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofStatusLocalExists(sStatusName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
				hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
				
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				if(this.objEventStatusLocalBroker!=null) {
					IEventObjectStatusLocalSetZZZ event = new EventObjectStatusLocalSetZZZ(this,1,sStatusName.toUpperCase(), bStatusValue);
					this.objEventStatusLocalBroker.fireEvent(event);
				}
				
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;
	}

	@Override
	public boolean[] setStatusLocal(Enum[] objaEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumStatusIn)) {
				baReturn = new boolean[objaEnumStatusIn.length];
				int iCounter=-1;
				for(Enum objEnumStatus:objaEnumStatusIn) {
					iCounter++;
					boolean bReturn = this.setStatusLocal(objEnumStatus, bStatusValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean[] setStatusLocal(String[] saStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saStatusName)) {
				baReturn = new boolean[saStatusName.length];
				int iCounter=-1;
				for(String sStatusName:saStatusName) {
					iCounter++;
					boolean bReturn = this.setStatusLocal(sStatusName, bStatusValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofStatusLocalExists(Enum objEnumStatus) throws ExceptionZZZ {
		return this.proofStatusLocalExists(objEnumStatus.name());
	}

	@Override
	public boolean proofStatusLocalExists(String sStatusName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			bReturn = StatusLocalHelperZZZ.proofStatusLocalDirectExists(this.getClass(), sStatusName);				
		}//end main:
		return bReturn;
	}

	@Override
	public String[] getStatusLocal(boolean bStatusValueToSearchFor) throws ExceptionZZZ {
		return this.getStatusLocal_(bStatusValueToSearchFor, false);
	}

	@Override
	public String[] getStatusLocal(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ {
		return this.getStatusLocal_(bStatusValueToSearchFor, bLookupExplizitInHashMap);
	}

	@Override
	public String[] getStatusLocal() throws ExceptionZZZ {
		String[] saReturn = null;
		main:{	
			saReturn = StatusLocalHelperZZZ.getStatusLocalDirectAvailable(this.getClass());				
		}//end main:
		return saReturn;
	}
	
	private String[]getStatusLocal_(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			ArrayList<String>listasTemp=new ArrayList<String>();
			
			//FALLUNTERSCHEIDUNG: Alle gesetzten Status werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
			//                                  Diese kann man nur durch Einzelprüfung ermitteln.
			if(bLookupExplizitInHashMap) {
				HashMap<String,Boolean>hmStatus=this.getHashMapStatusLocal();
				if(hmStatus==null) break main;
				
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey){
					boolean btemp = hmStatus.get(sKey);
					if(btemp==bStatusValueToSearchFor){
						listasTemp.add(sKey);
					}
				}
			}else {
				//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
				String[]saStatus = this.getStatusLocal();
				
				//20211201:
				//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
				//         auch wenn sie garnicht gesetzt wurden.
				//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
				for(String sStatus : saStatus){
					boolean btemp = this.getStatusLocal(sStatus);
					if(btemp==bStatusValueToSearchFor ){ //also 'true'
						listasTemp.add(sStatus);
					}
				}
			}
			saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//end main:
		return saReturn;
	}
	

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++
		
		//Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
			//ALIAS("Uniquename","Statusmeldung","Beschreibung, wird nicht genutzt....",)
			public enum STATUSLOCAL implements IEnumSetMappedZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{	
				ISSTARTED("isstarted","ProcessWatchRunner",""),
				HASCONNECTION("hasconnection","ProcessWatchRunner ist mit dem Process verbunden",""),
				HASOUTPUT("hasoutput","Prozess hat Output",""),
				HASINPUT("hasinput","Prozess hat Input",""),
				ISSTOPPED("isended","ProcessWatchRunner ist beendet",""),
				HASERROR("haserror","Ein Fehler ist aufgetreten","");
											
			private String sAbbreviation,sStatusMessage,sDescription;

			//#############################################
			//#### Konstruktoren
			//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
			//In der Util-Klasse habe ich aber einen Workaround gefunden.
			STATUSLOCAL(String sAbbreviation, String sStatusMessage, String sDescription) {
			    this.sAbbreviation = sAbbreviation;
			    this.sStatusMessage = sStatusMessage;
			    this.sDescription = sDescription;
			}

			public String getAbbreviation() {
			 return this.sAbbreviation;
			}
			
			public String getStatusMessage() {
				 return this.sStatusMessage;
			}
			
			public EnumSet<?>getEnumSetUsed(){
				return STATUSLOCAL.getEnumSet();
			}

			/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
			@SuppressWarnings("rawtypes")
			public static <E> EnumSet getEnumSet() {
				
			 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
				//String sFilterName = "FLAGZ"; /
				//...
				//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				
				//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
				Class<STATUSLOCAL> enumClass = STATUSLOCAL.class;
				EnumSet<STATUSLOCAL> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
				
				//Merke: In einer anderen Klasse, die dieses DesingPattern nutzt, befinden sich die Enums in einer anderen Klasse
				for(Object obj : AbstractProcessWatchRunnerZZZ.class.getEnumConstants()){
					//System.out.println(obj + "; "+obj.getClass().getName());
					set.add((STATUSLOCAL) obj);
				}
				return set;
				
			}

			//TODO: Mal ausprobieren was das bringt
			//Convert Enumeration to a Set/List
			private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
				  EnumSet<E> set=EnumSet.noneOf(enumClass);
				  long mask=1;
				  for (  E e : enumClass.getEnumConstants()) {
				    if ((mask & vector) == mask) {
				      set.add(e);
				    }
				    mask<<=1;
				  }
				  return set;
				}

			//+++ Das könnte auch in einer Utility-Klasse sein.
			//the valueOfMethod <--- Translating from DB
			public static STATUSLOCAL fromAbbreviation(String s) {
			for (STATUSLOCAL state : values()) {
			   if (s.equals(state.getAbbreviation()))
			       return state;
			}
			throw new IllegalArgumentException("Not a correct abbreviation: " + s);
			}

			//##################################################
			//#### Folgende Methoden bring Enumeration von Hause aus mit. 
					//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
			@Override
			public String getName() {	
				return super.name();
			}

			@Override
			public String toString() {//Mehrere Werte mit # abtennen
			    return this.sAbbreviation+"="+this.sDescription;
			}

			@Override
			public int getIndex() {
				return ordinal();
			}

			//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
			@Override
			public int getPosition() {
				return getIndex()+1; 
			}

			@Override
			public String getDescription() {
				return this.sDescription;
			}
			//+++++++++++++++++++++++++
			}//End internal Class
}//END class
