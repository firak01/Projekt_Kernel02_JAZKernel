package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IStatusBooleanMessageZZZ;
import basic.zKernel.status.IStatusBooleanZZZ;
import basic.zKernel.status.IStatusLocalUserMessageZZZ;
import basic.zKernel.status.StatusBooleanMessageZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;

public abstract class AbstractObjectWithStatusZZZ <T> extends AbstractObjectWithFlagZZZ implements IStatusLocalUserMessageZZZ{
	private static final long serialVersionUID = 1L;
	protected volatile HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	
	//Der StatusMessageString. Als Extra Speicher. Kann daher zum Ueberschreiben des Default StatusMessage Werts aus dem Enum genutzt werden.
	protected volatile CircularBufferZZZ<IStatusBooleanMessageZZZ> cbStatusLocal = new CircularBufferZZZ<IStatusBooleanMessageZZZ>(9);	
	protected volatile CircularBufferZZZ<String> cbStatusLocalMessage = new CircularBufferZZZ<String>(9);
	
	//Ein einmaliger Vorgang. Der quasi letzte gemeldete Fehler.
	//Diese Meldung ist flexibel und nicht in irgendeinem EnumSet hinterlegt.
	protected volatile String sStatusLocalError=null;
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusZZZ() {	
		super();
	}
	public AbstractObjectWithStatusZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	public AbstractObjectWithStatusZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}
	public AbstractObjectWithStatusZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
	}
	
	@Override
	public String getStatusLocalError() {
		return this.sStatusLocalError;
	}
	
	@Override 
	public void setStatusLocalError(String sError) {
		this.sStatusLocalError = sError;
	}
	
	
	@Override
	public void resetStatusLocalError() {
		this.sStatusLocalError = null;
	}
	
	
	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObject() {
		return this.getCircularBufferStatusLocal().getLast();
	}
	
	@Override
	public boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum) {
		boolean bReturn = false;
		main:{
			bReturn = this.getCircularBufferStatusLocal().offer(objEnum);
			if(!bReturn) break main;
				
			String sMessage = objEnum.getMessage();
			bReturn = this.getCircularBufferStatusLocalMessage().offer(sMessage);
			if(!bReturn) break main;
						
		}//end main
		return bReturn;
	}

	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObjectPrevious() {
		return this.getCircularBufferStatusLocal().getPrevious();
	}
	
	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObjectPrevious(int iIndexStepsBack) {
		return this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
	}
	
	@Override
	public IEnumSetMappedStatusZZZ getStatusLocalEnumPrevious(int iIndexStepsBack) {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
			if(objStatus==null) break main;
			
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
	
	
	@Override
	public IEnumSetMappedStatusZZZ getStatusLocalEnumCurrent() {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = this.getStatusLocalObject();
			if(objStatus==null) break main;
			
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserBasicZZZ#offerStatusLocalEnum(basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ)
	 */
	@Override
	public boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocalIn) {
		boolean bReturn = false;
		main:{
			bReturn = this.offerStatusLocalEnum(enumStatusLocalIn, true, "");
		}//end main:
		return bReturn;
	}
	
	
	@Override
	public boolean offerStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusLocalIn, boolean bValue, String sMessage) {
		boolean bReturn = false;
		main:{
			IStatusBooleanMessageZZZ objStatus = new StatusBooleanMessageZZZ(enumStatusLocalIn, bValue, sMessage);
			bReturn = this.getCircularBufferStatusLocal().offer(objStatus);
			if(!bReturn)break main;
			
			bReturn = this.getCircularBufferStatusLocalMessage().offer(sMessage);
			if(!bReturn)break main;
	
		}//end main:
		return bReturn;
	}
	
	@Override
	public IEnumSetMappedStatusZZZ getStatusLocalEnumPrevious() {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = this.getCircularBufferStatusLocal().getPrevious();
			if(objStatus==null) break main;
				
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
			
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserBasicZZZ#getStatusLocalString()
	 */
	@Override
	public String getStatusLocalAbbreviation(){
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumCurrent();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}
			
		}//end main:
		return sReturn;
	}
		
	/**
	 * @return
	 * @author Fritz Lindhauer, 11.10.2023, 08:25:54
	 */
	@Override
	public String getStatusLocalAbbreviationPrevious() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}			
		}//end main:
		return sReturn;
	}
		
	@Override
	public String getStatusLocalDescription() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumCurrent();
			if(objEnum!=null) {
				sReturn = objEnum.getDescription();
			}
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public String getStatusLocalDescriptionPrevious() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
			if(objEnum!=null) {
				sReturn = objEnum.getDescription();
			}
			
		}//end main:
		return sReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserMessageZZZ#getStatusLocalMessage()
	 */
	@Override 
	public String getStatusLocalMessage()	{
		return this.getCircularBufferStatusLocalMessage().getLast();
	}
	
	@Override
	public boolean replaceStatusLocalMessage(String sStatusMessageIn) {
		return this.getCircularBufferStatusLocalMessage().replaceLastWith(sStatusMessageIn);
	}
	
	@Override
	public String getStatusLocalMessagePrevious(){
		return this.getCircularBufferStatusLocalMessage().getPrevious();
	}
	
	@Override
	public String getStatusLocalMessagePrevious(int iIndexStepsBack) {
		return this.getCircularBufferStatusLocalMessage().getPrevious(iIndexStepsBack);
	}
	
	//### aus IStatusLocalUserZZZ
	@Override
	abstract public boolean isStatusLocalRelevant(IEnumSetMappedStatusZZZ objEnumStatusIn) throws ExceptionZZZ;


	
	@Override
	public abstract boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ;
	
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
	
	//################## Status Local
	//### aus IEventBrokerStatusLocalSetUserZZZ
//	@Override
//	public ISenderObjectStatusLocalSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
//		return this.objEventStatusLocalBroker;
//	}
//
//	@Override
//	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalSetZZZ objEventSender) {
//		this.objEventStatusLocalBroker = objEventSender;
//	}
	
	//###### aus IStatusLocalUser
	@Override
	public HashMap<String, Boolean> getHashMapStatusLocal() {
		return this.hmStatusLocal;
	}

	@Override
	public void setHashMapStatusLocal(HashMap<String, Boolean> hmStatusLocal) {
		this.hmStatusLocal = hmStatusLocal;
	}
	
	//### aus ICircularBufferStatusBooleanMessageUserZZZ
	@Override
	public CircularBufferZZZ<IStatusBooleanMessageZZZ> getCircularBufferStatusLocal(){		
		return this.cbStatusLocal;
	}
	
	@Override
	public void setCircularBufferStatusLocal(CircularBufferZZZ<IStatusBooleanMessageZZZ> cb){
		this.cbStatusLocal = cb;
	}
	
	@Override
	public CircularBufferZZZ<String> getCircularBufferStatusLocalMessage(){		
		return this.cbStatusLocalMessage;
	}
	
	@Override
	public void setCircularBufferStatusLocalMessage(CircularBufferZZZ<String> cb){
		this.cbStatusLocalMessage = cb;
	}
	
	
	//++++++++++++++++++++++++++++++++
	//### aus ICircularBufferStatusBooleanUserBasicZZZ 
	@Override
	public void debugCircularBufferStatusLocal() throws ExceptionZZZ {
		int iStepsMax = this.getCircularBufferStatusLocal().getCapacity();
		this.debugCircularBufferStatusLocal(iStepsMax);
	}
	
	@Override
	public void debugCircularBufferStatusLocal(int iStepsMax) throws ExceptionZZZ {		
		String sLog="";
		int iStepsToSearchBackwardsTEST=-1;
		boolean bGoonTEST = false;
		IEnumSetMappedStatusZZZ objStatusLocalPreviousTEST = null;
		do {	
			iStepsToSearchBackwardsTEST = iStepsToSearchBackwardsTEST + 1; 
			int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsToSearchBackwardsTEST);
			sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST: Vorheriger Status= " + iStepsToSearchBackwardsTEST + " | Verwendeter Index= " + iIndex;
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
			this.logLineDate(sLog);
			
			objStatusLocalPreviousTEST = (IEnumSetMappedStatusZZZ) this.getStatusLocalEnumPrevious(iStepsToSearchBackwardsTEST);
			if(objStatusLocalPreviousTEST==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST: Kein weiterer entsprechend weit entfernter vorheriger Status vorhanden";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
				this.logLineDate(sLog);
				bGoonTEST=true;
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST : Der " + iStepsToSearchBackwardsTEST + " Schritte vorherige Status im Main ist. GroupId/Abbreviation: " + objStatusLocalPreviousTEST.getStatusGroupId() + "/'" + objStatusLocalPreviousTEST.getAbbreviation()+"'.";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
				this.logLineDate(sLog);							
			}
			if(iStepsToSearchBackwardsTEST>=iStepsMax) bGoonTEST=true;											
		}while(!bGoonTEST);						
	}
	
	
	@Override
	public void debugCircularBufferStatusLocalMessage() throws ExceptionZZZ {
		int iStepsMax = this.getCircularBufferStatusLocalMessage().getCapacity();
		this.debugCircularBufferStatusLocalMessage(iStepsMax);
	}
	
	//### aus ICircularBufferStatusBooleanMessageUserZZZ
	@Override
	public void debugCircularBufferStatusLocalMessage(int iStepsMax) throws ExceptionZZZ {		
		String sLog="";
		int iStepsToSearchBackwardsTEST=-1;
		boolean bGoonTEST = false;
		IEnumSetMappedStatusZZZ objStatusLocalPreviousTEST = null;
		do {	
			iStepsToSearchBackwardsTEST = iStepsToSearchBackwardsTEST + 1; 
			int iIndex = this.getCircularBufferStatusLocalMessage().computeIndexForStepPrevious(iStepsToSearchBackwardsTEST);
			sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST: Vorheriger Status= " + iStepsToSearchBackwardsTEST + " | Verwendeter Index= " + iIndex;
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
			this.logLineDate(sLog);
			
			objStatusLocalPreviousTEST = (IEnumSetMappedStatusZZZ) this.getStatusLocalEnumPrevious(iStepsToSearchBackwardsTEST);
			if(objStatusLocalPreviousTEST==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST: Kein weiterer entsprechend weit entfernter vorheriger Status vorhanden";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
				this.logLineDate(sLog);
				bGoonTEST=true;
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST : Der " + iStepsToSearchBackwardsTEST + " Schritte vorherige Status im Main ist. GroupId/Abbreviation: " + objStatusLocalPreviousTEST.getStatusGroupId() + "/'" + objStatusLocalPreviousTEST.getAbbreviation()+"'.";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
				this.logLineDate(sLog);		
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": TEST : Message='" + this.getStatusLocalMessagePrevious(iStepsToSearchBackwardsTEST) + "'";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
				this.logLineDate(sLog);	
			}
			if(iStepsToSearchBackwardsTEST>=iStepsMax) bGoonTEST=true;											
		}while(!bGoonTEST);						
	}

	@Override
	public int getStatusLocalGroupIdFromCurrent() {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObject();
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;
	}
	
	@Override
	public int getStatusLocalGroupIdFromPrevious() {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObjectPrevious();
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;
	}
	
	@Override
	public int getStatusLocalGroupIdFromPrevious(int iIndexStepsBack) {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObjectPrevious(iIndexStepsBack);
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;

	}
	
	@Override
	public int searchStatusLocalGroupIdPreviousDifferentFromCurrent() throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			int iReturnTemp=iReturn;
			String sLog;
			
			//+++ Hole die aktuelle GroupId
			int iGroupIdCurrent = this.getStatusLocalGroupIdFromCurrent();
			if(iGroupIdCurrent==-1) break main;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			//boolean bGoon = false;
			IEnumSetMappedStatusZZZ objStatusLocalPrevious = null;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();
			//do {					
				for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
					int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
					this.logLineDate(sLog);
					
					objStatusLocalPrevious = (IEnumSetMappedStatusZZZ) this.getStatusLocalEnumPrevious(iStepsPrevious);
					if(objStatusLocalPrevious==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": Kein entsprechend weit entfernter vorheriger Status vorhanden";
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
						this.logLineDate(sLog);
						break main;
					}else {				
						//Frage nach dem Status im Backend nach...
						iReturnTemp = objStatusLocalPrevious.getStatusGroupId();
						
						sLog = ReflectCodeZZZ.getPositionCurrent()+": Der " + iStepsPrevious + " Schritte vorherige Status im Main ist. GroupId/Abbreviation: " + objStatusLocalPrevious.getStatusGroupId() + "/'" + objStatusLocalPrevious.getAbbreviation()+"'.";
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
						this.logLineDate(sLog);	
						
						if(iReturnTemp!=iGroupIdCurrent) {
							iReturn = iReturnTemp;
							break main;
						}
					}
				}//end for															
			//}while(!bGoon);
			
		}//end main:
		return iReturn;
	}

	//+++
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent() throws ExceptionZZZ {
		return this.searchStatusLocalGroupCurrent(false);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent(boolean bWithInterruption) throws ExceptionZZZ {
		int iStatusLocalGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, bWithInterruption);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious() throws ExceptionZZZ {		
		return this.searchStatusLocalGroupPrevious(false);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious(boolean bWithInterruption) throws ExceptionZZZ {
		int iStatusLocalGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, bWithInterruption);
	}

	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, false);
	}
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		ArrayList<IStatusBooleanZZZ>listaReturn=null;
		main:{						
			//+++ Hole die aktuelle GroupId			
			if(iStatusLocalGroupId<=-1) break main;
			listaReturn = new ArrayList<IStatusBooleanZZZ>();
			
			
			String sLog;
			int iGroupIdTemp=-1;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			//boolean bGoon = false;
			//IEnumSetMappedStatusZZZ objStatusLocalPrevious = null;
			IStatusBooleanZZZ objStatusLocalPrevious = null;
			boolean bGroupPreviousFound=false;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();
			//do {					
				for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
					int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
					this.logLineDate(sLog);
					
					objStatusLocalPrevious = (IStatusBooleanZZZ) this.getStatusLocalObjectPrevious(iStepsPrevious);					
					if(objStatusLocalPrevious==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": Kein entsprechend weit entfernter vorheriger Status vorhanden";
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
						this.logLineDate(sLog);
						break main;
					}else {				
						//Frage nach dem Status im Backend nach...
						iGroupIdTemp = objStatusLocalPrevious.getEnumObject().getStatusGroupId();
						
						sLog = ReflectCodeZZZ.getPositionCurrent()+": Der " + iStepsPrevious + " Schritt(e) vorherige Status im Main ist. GroupId/Abbreviation: " + iGroupIdTemp + "/'" + objStatusLocalPrevious.getEnumObject().getAbbreviation()+"'.";
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
						this.logLineDate(sLog);	
						
						if(iGroupIdTemp==iStatusLocalGroupId) {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": Event mit gemappten Status gefunden: " + objStatusLocalPrevious.getEnumObject().getAbbreviation();
							System.out.println(sLog);
							this.logLineDate(sLog);
							
							
//							//ggfs. nicht aufnehmen, also quasi weiter schrittweise zurück, wenn der Status ein Steuerevent ist...,d.h. ohne Icon
//							if(objStatusLocalPrevious!=null) {
//								objEnum = (ClientTrayStatusTypeZZZ) hmEnum.get(objStatusLocalPrevious);			
//								if(objEnum!=null) {					
//									if(StringZZZ.isEmpty(objEnum.getIconFileName())){
//										iStepsToSearchBackwards=1;
//										
//										sLog = ReflectCodeZZZ.getPositionCurrent()+": Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";
//										System.out.println(sLog);
//										this.getMainObject().logProtocolString(sLog);							
//									}else {
//										bGoon=true;
//									}
//								}
//							}
							
							//ggfs. doch nicht aufnehmen, wenn es sich um ein steuer-Eintrag handelt.
							if(!objStatusLocalPrevious.getEnumObject().getAbbreviation().equalsIgnoreCase("PREVIOUSEVENTRTYPE")) {														
								listaReturn.add((IStatusBooleanZZZ) objStatusLocalPrevious);
								if(!bWithInterruption) {
									bGroupPreviousFound=true;
								}
							}else {
								sLog = ReflectCodeZZZ.getPositionCurrent()+": Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";
								System.out.println(sLog);
								this.logLineDate(sLog);	
							}
						}else {
							//Wurde im Fall: "Ohne Unterbrechung" dann wieder eine andere GroupId gefunden, ist ende
							if(bGroupPreviousFound)break main;
						}
					}
				}//end for															
			//}while(!bGoon);
			
		}//end main:
		return listaReturn;
	}
	
	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer() throws ExceptionZZZ {
		int iGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupIndexLowerInBuffer(iGroupId);
	}
	
	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexLowerInBuffer(iGroupId, false);
	}

	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexInBuffer_(iGroupId,bWithInterruption,"LOWER");
	}
	
	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer() throws ExceptionZZZ {
		int iGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupIndexUpperInBuffer(iGroupId);
	}
	
	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexUpperInBuffer(iGroupId, false);
	}

	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexInBuffer_(iGroupId,bWithInterruption,"UPPER");
	}
		
	public int searchStatusLocalGroupIndexInBuffer_(int iGroupId, boolean bWithInterruption, String sFlagControl) throws ExceptionZZZ {
		int iReturn = -1;
		main:{			
			if(iGroupId<=-1) break main;
			
			boolean bLower=false;
			if(!(StringZZZ.equals(sFlagControl, "") || StringZZZ.equalsIgnoreCase(sFlagControl, "LOWER") || StringZZZ.equalsIgnoreCase(sFlagControl, "UPPER"))){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Flag '" + sFlagControl +"', does not exist. Expected 'UPPER','LOWER',''.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}else if(StringZZZ.equals(sFlagControl,"") || StringZZZ.equalsIgnoreCase(sFlagControl, "LOWER")) {
				bLower=true;
			}
			
			
//			String sLog;
			int iGroupIdTemp=-1;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			IStatusBooleanZZZ objStatusLocalPrevious = null;
			boolean bGroupPreviousFound=false;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();	
			for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
				int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
//				this.logLineDate(sLog);
				
				objStatusLocalPrevious = (IStatusBooleanZZZ) this.getStatusLocalObjectPrevious(iStepsPrevious);					
				if(objStatusLocalPrevious==null) {
//					sLog = ReflectCodeZZZ.getPositionCurrent()+": Kein entsprechend weit entfernter vorheriger Status vorhanden";
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
//					this.logLineDate(sLog);
					break main;
				}else {				
					//Frage nach dem Status im Backend nach...
					iGroupIdTemp = objStatusLocalPrevious.getEnumObject().getStatusGroupId();
					
//					sLog = ReflectCodeZZZ.getPositionCurrent()+": Der " + iStepsPrevious + " Schritt(e) vorherige Status im Main ist. GroupId/Abbreviation: " + iGroupIdTemp + "/'" + objStatusLocalPrevious.getEnumObject().getAbbreviation()+"'.";
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);					
//					this.logLineDate(sLog);	
					
					if(iGroupIdTemp==iGroupId) {
//						sLog = ReflectCodeZZZ.getPositionCurrent()+": Event mit gemappten Status gefunden: " + objStatusLocalPrevious.getEnumObject().getAbbreviation();
//						System.out.println(sLog);
//						this.logLineDate(sLog);
						
						//ggfs. doch nicht aufnehmen, wenn es sich um ein steuer-Eintrag handelt.
						if(!objStatusLocalPrevious.getEnumObject().getAbbreviation().equalsIgnoreCase("PREVIOUSEVENTRTYPE")) {														
							iReturn = iIndex;																		
							if(!bLower) {
								break main;								
							}else {
								bGroupPreviousFound = true;	//Das ist fuer den UPPER Eintrag nicht wichtig. Aber fuer den UPPER Eintrag mit INTERRUPTION
							}							
						}else {
//							sLog = ReflectCodeZZZ.getPositionCurrent()+": Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";
//							System.out.println(sLog);
//							this.logLineDate(sLog);	
						}
					}else {
						if(bLower) {							
							//das ist fuer den UPPER Eintrag nicht wichtig
							if(bWithInterruption) {
								if(bGroupPreviousFound) {
//									sLog = ReflectCodeZZZ.getPositionCurrent()+": Weitere Gruppe gefunden. Unterbrechung erlaubt. Weiter mit diesem vorlaeufigen Index bis zum Ende: " + iIndex;
//									System.out.println(sLog);
//									this.logLineDate(sLog);
								}
							}else {	
								if(bGroupPreviousFound) {
//									sLog = ReflectCodeZZZ.getPositionCurrent()+": Weitere Gruppe gefunden. Keine Unterbrechung suchen. Beende mit diesem Index: " + iIndex;
//									System.out.println(sLog);
//									this.logLineDate(sLog);								
									break main;
								}
							}
						}
					}
				}
			}					
		}//end main:
		return iReturn;
		
		
		
		
	}	
	
	//++++++++++++
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserBasicZZZ#setStatusLocal(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean offerStatusLocal(String sStatusName, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bStatusExists = this.setStatusLocal(sStatusName, bStatusValue);
			if(!bStatusExists)break main;
				
			//ueberschreibe die Defaultmessage (aus dem enum) durch eine eigene...
			this.replaceStatusLocalMessage(sStatusMessage);
			
			bReturn=true;
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
									
			bReturn=this.offerStatusLocal(sStatusName, null, bStatusValue);
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean setStatusLocal(String sStatusName, String sMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
									
			bReturn=this.offerStatusLocal(sStatusName, sMessage, bStatusValue);
		}//end main:
		return bReturn;		
	}
	
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;

	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	@Override
	public abstract boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;

	@Override
	public abstract boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ;

	
	@Override
	public abstract boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;
	
	@Override
	public abstract boolean setStatusLocalEnum(IEnumSetMappedStatusZZZ enumStatusMapped, String sMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	@Override
	public abstract boolean setStatusLocalEnum(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ;

	@Override
	public abstract boolean setStatusLocalEnum(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, String sMessage, boolean bStatusValue) throws ExceptionZZZ;

	
	@Override
	public boolean switchStatusLocalAllGroupTo(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			Set<String> setKey = hmStatus.keySet();
			for(String sKey : setKey) {
				hmStatus.put(sKey, !bStatusValue);				
			}
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);			
			bReturn=true;
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(Enum enumStatus) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(-1, enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(Enum enumStatus, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(-1, enumStatus, null, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalAsGroupTo(Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(-1, enumStatus, sStatusMessage, bStatusValue);	
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(int iIndex, Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		return this.switchStatusLocalAsGroupTo_(iIndex, enumStatus, sStatusMessage, bStatusValue);
	}
	
	private boolean switchStatusLocalAsGroupTo_(int iIndex, Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(iIndex, -1, enumStatus, sStatusMessage, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusZZZ enumStatusMapped) throws ExceptionZZZ{
		int iGroupId = enumStatusMapped.getStatusGroupId();
		Enum enumStatus = (Enum)enumStatusMapped;
		return this.switchStatusLocalForGroupTo_(-1, iGroupId, enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ{
		int iGroupId = enumStatusMapped.getStatusGroupId();
		Enum enumStatus = (Enum)enumStatusMapped;
		return this.switchStatusLocalForGroupTo_(-1, iGroupId, enumStatus, null, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iIndex, int iGroupId, Enum enumStatus) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(iIndex, iGroupId, enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iIndex, int iGroupId, Enum enumStatus, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(iIndex, iGroupId, enumStatus, null, bStatusValue);
	}
		
	@Override
	public boolean switchStatusLocalForGroupTo(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iGroupId = enumStatusMapped.getStatusGroupId();
			Enum enumStatus = (Enum) enumStatusMapped;
			bReturn = this.switchStatusLocalForGroupTo(iIndexOfProcess, iGroupId, enumStatus, null, bStatusValue);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iIndexOfProcess, IEnumSetMappedStatusZZZ enumStatusMapped, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iGroupId = enumStatusMapped.getStatusGroupId();
			Enum enumStatus = (Enum) enumStatusMapped;
			bReturn = this.switchStatusLocalForGroupTo(iIndexOfProcess, iGroupId, enumStatus, sStatusMessage, bStatusValue);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iIndex, int iGroupId, Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		return this.switchStatusLocalForGroupTo_(iIndex, iGroupId, enumStatus, sStatusMessage, bStatusValue);
	}
	
	private boolean switchStatusLocalForGroupTo_(int iIndex, int iGroupId, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(enumStatusIn==null) break main;
			String sStatusName = enumStatusIn.name().toUpperCase();
			
			bReturn = this.offerStatusLocal(iIndex, enumStatusIn, sStatusMessage, bStatusValue);
			if(!bReturn)break main;
			
			//Hole die StatusNamen der angegebenen Gruppe
			if(iGroupId==-1) {
				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey) {				
					if(!sKey.equals(sStatusName)) hmStatus.put(sKey, !bStatusValue); //Setze die anderen Werte, als der uebergebene Status
				}			
			}else {
				String[] saStatusForGroup = StatusLocalHelperZZZ.getStatusLocalForGroup(this.getClass(), iGroupId);
				if(saStatusForGroup==null) break main;
				
				//Setze den Status nun in die HashMap DIESER GUPPE
				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey) {				
					if(!sKey.equals(sStatusName)) {//Setze die anderen Werte, als der uebergebene Status
						if(StringArrayZZZ.contains(saStatusForGroup, sKey)) { //...aber nur in die Gruppe fuer die GroupId
							hmStatus.put(sKey, !bStatusValue);
						}
					}
				}					
			}															
		}//end main:
		return bReturn;		
	}
	
	@Override
	public abstract boolean offerStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
		
	@Override
	public abstract boolean offerStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	//nun durch FLAGZ ersetzt... @Override
	//public abstract boolean offerStatusLocal(int iIndexOfProcess, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue, String[]saFlagControl) throws ExceptionZZZ;
	
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
	public boolean proofStatusLocalValue(Enum objEnumStatus, boolean bValue) throws ExceptionZZZ {
		return this.proofStatusLocalValue(objEnumStatus.name(), bValue);
	}
	
	@Override
	public boolean proofStatusLocalValue(String sStatusName, boolean bValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			boolean bFlag = this.getFlag(IObjectWithStatusZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUE);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
			
			bReturn = this.proofStatusLocalValueChanged(sStatusName, bValue);
			if(!bReturn) {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + " This status has not changed: '" + sStatusName + "'";					
				this.logLineDate(sLog);
				break main;
			}
			
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalValueChanged(Enum objEnumStatus, boolean bValue) throws ExceptionZZZ {
		return this.proofStatusLocalValueChanged(objEnumStatus.name(), bValue);
	}

	
	
	@Override
	public boolean proofStatusLocalValueChanged(String sStatusName, boolean bValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			boolean bFlag = this.getFlag(IObjectWithStatusZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
						
			HashMap<String,Boolean>hmStatusLocal = this.getHashMapStatusLocal();
			bReturn = StatusLocalHelperZZZ.proofStatusLocalChanged(hmStatusLocal, sStatusName, bValue);
			if(!bReturn) {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + " This status has a value to be ignored: '" + sStatusName + "'";				
				this.logLineDate(sLog);
				break main;
			}
			
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
	public String[] getStatusLocalAll() throws ExceptionZZZ {
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
				String[]saStatus = this.getStatusLocalAll();
				
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
	
	//###############################
	//### Flags
	//###############################
	
	//### Aus IObjectWithStatusZZZ
	@Override
	public boolean getFlag(IObjectWithStatusZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IObjectWithStatusZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IObjectWithStatusZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IObjectWithStatusZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IObjectWithStatusZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IObjectWithStatusZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
}
