package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IStatusBooleanZZZ;
import basic.zKernel.status.IStatusLocalUserMessageZZZ;
import basic.zKernel.status.IStatusLocalMapForCascadingStatusLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IStatusBooleanMessageZZZ;
import basic.zKernel.status.IStatusLocalUserZZZ;
import basic.zKernel.status.StatusBooleanMessageZZZ;
import basic.zKernel.status.StatusBooleanZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;

public abstract class AbstractObjectWithStatusZZZ <T> extends AbstractObjectWithFlagZZZ implements IStatusLocalUserMessageZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	
	//Der StatusMessageString. Als Extra Speicher. Kann daher zum Ueberschreiben des Default StatusMessage Werts aus dem Enum genutzt werden.
	protected CircularBufferZZZ<IStatusBooleanMessageZZZ> cbStatusLocal = new CircularBufferZZZ<IStatusBooleanMessageZZZ>(9);	
	protected CircularBufferZZZ<String> cbStatusLocalMessage = new CircularBufferZZZ<String>(9);
	
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
	public IEnumSetMappedZZZ getStatusLocalEnumPrevious(int iIndexStepsBack) {
		IEnumSetMappedZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
			if(objStatus==null) break main;
			
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
	
	
	@Override
	public IEnumSetMappedZZZ getStatusLocalEnumCurrent() {
		IEnumSetMappedZZZ objReturn = null;
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
	public boolean offerStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocalIn) {
		boolean bReturn = false;
		main:{
			bReturn = this.offerStatusLocalEnum(enumStatusLocalIn, true, "");
		}//end main:
		return bReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserBasicZZZ#setStatusLocalEnum(basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ, boolean)
	 */
	@Override
	public boolean offerStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocalIn, boolean bValue, String sMessage) {
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
	public IEnumSetMappedZZZ getStatusLocalEnumPrevious() {
		IEnumSetMappedZZZ objReturn = null;
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
	
	//### aus IStatusLocalUserZZZ
	@Override
	abstract public boolean isStatusLocalRelevant(IEnumSetMappedZZZ objEnumStatusIn) throws ExceptionZZZ;


	
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
			
			bReturn=true;
		}//end main:
		return bReturn;		
	}
	
	
	
	
	
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;

	@Override
	public abstract boolean setStatusLocal(int iIndexOfProcess, Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
		
	@Override
	public boolean switchStatusLocalAsGroupTo(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
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
	public boolean switchStatusLocalAsGroupTo(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo(enumStatusIn, null, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalAsGroupTo(Enum enumStatusIn, String sMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(enumStatusIn==null) break main;
			String sStatusName = enumStatusIn.name().toUpperCase();
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			Set<String> setKey = hmStatus.keySet();
			for(String sKey : setKey) {
				if(!sKey.equals(sStatusName)) hmStatus.put(sKey, !bStatusValue);				
			}
			
			bReturn = this.offerStatusLocal(enumStatusIn, sMessage, bStatusValue);			
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
