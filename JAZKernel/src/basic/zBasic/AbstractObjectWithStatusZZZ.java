package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IStatusLocalUserZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;

public abstract class AbstractObjectWithStatusZZZ <T> extends AbstractObjectZZZ implements IStatusLocalUserZZZ{
	private static final long serialVersionUID = 1L;

	protected HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	protected IEnumSetMappedZZZ enumStatusLocal = null; 
	protected IEnumSetMappedZZZ enumStatusLocalPrevious = null; 
	
	//Ein Status String, zum Ueberschreiben des Default Status Werts aus dem Enum
	protected String sStatusLocal = null;            
	protected String sStatusLocalPrevious = null;   

	//Ein StatusMessage String, zum Ueberschreiben des Default StatusMessage Werts aus dem Enum
	protected String sStatusLocalMessage=null;
	protected String sStatusLocalMessagePrevious=null;
	
	
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
	
	public IEnumSetMappedZZZ getStatusLocalEnum() {
		return this.enumStatusLocal;
	}
	public void setStatusLocalEnum(IEnumSetMappedZZZ enumStatusLocalIn) {
		main:{	
			//Zuruecksetzen ggfs. ueberschriebener Werte
			this.setStatusLocalString(null);
			this.setStatusLocalMessage(null);
		
			//NULL und Previous
			if(enumStatusLocalIn == null) {	
				if(this.getStatusLocalEnum()!=null) {
					this.setStatusLocalEnumPrevious(this.enumStatusLocal);
					this.enumStatusLocal=enumStatusLocalIn;							
				}
				break main;
			}
			
			//GGFS. mit Vergleich
			IEnumSetMappedZZZ enumStatusLocal = this.getStatusLocalEnum();
			if(enumStatusLocal==null) {
				this.setStatusLocalEnumPrevious(enumStatusLocal);
				this.enumStatusLocal = enumStatusLocalIn;
			}else {
				if(!enumStatusLocalIn.equals(enumStatusLocal)) {
					this.setStatusLocalEnumPrevious(enumStatusLocal);
					this.enumStatusLocal = enumStatusLocalIn;					
				}
			}			
		}//end main:		
	}
		
	public IEnumSetMappedZZZ getStatusLocalEnumPrevious() {
		return this.enumStatusLocalPrevious;
	}
	
	@Override
	public void setStatusLocalEnumPrevious(IEnumSetMappedZZZ enumStatusLocal) {
		this.enumStatusLocalPrevious = enumStatusLocal;
		this.setStatusLocalStringPrevious(null);
		this.setStatusLocalMessagePrevious(null);
	}
	
	/**
	 * @return
	 * @author Fritz Lindhauer, 11.10.2023, 08:25:40
	 */
	@Override
	public String getStatusLocalString(){
		String sReturn = null;
		main:{
			if(this.sStatusLocal!=null) {
				sReturn = this.sStatusLocal;
				break main;				
			}
			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnum();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}
			
		}//end main:
		return sReturn;
	}
		
	
	/**
	 * @param sStatusLocal
	 * @author Fritz Lindhauer, 11.10.2023, 08:25:45
	 */
	@Override
	public void setStatusLocalString(String sStatusIn) {		
		main:{			
			if(sStatusIn == null) {
				if(this.sStatusLocal!=null) {
					this.setStatusLocalStringPrevious(this.sStatusLocal);
					this.sStatusLocal = null;
				}
				break main;
			}
			
			String sStatus = this.getStatusLocalString();
			if(sStatus==null) {
				this.setStatusLocalStringPrevious(sStatus);
				this.sStatusLocal = sStatusIn;
			}else {
				if(!sStatus.equals(sStatusLocalPrevious)) {				
					this.setStatusLocalStringPrevious(sStatus);
					this.sStatusLocal = sStatusIn;					
				}
			}
		}//end main:
	}
	
	/**
	 * @return
	 * @author Fritz Lindhauer, 11.10.2023, 08:25:54
	 */
	@Override
	public String getStatusLocalStringPrevious() {
		String sReturn = null;
		main:{
			if(this.sStatusLocalPrevious!=null) {
				sReturn =  this.sStatusLocalPrevious;
				break main;				
			}
			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}			
		}//end main:
		return sReturn;
	}
	
	/**
	 * @param sStatusPrevious
	 * @author Fritz Lindhauer, 11.10.2023, 08:26:03
	 */
	@Override
	public void setStatusLocalStringPrevious(String sStatusPrevious) {
		this.sStatusLocalPrevious = sStatusPrevious;
	}
	
	@Override
	public String getStatusLocalDescription() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnum();
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
	
	@Override 
	public String getStatusLocalMessage()	{
		String sReturn = null;
		main:{
			if(this.sStatusLocalMessage!=null) {
				sReturn =  this.sStatusLocalMessage;
				break main;				
			}
			
//			//Merke: Erst in OVPN-Klassen gibt es enum mit Message
//			IEnumSetMappedZZZ.STATUSLOCAL objEnum = (IEnumSetMappedZZZ.STATUSLOCAL)this.getStatusLocalEnum();
//			if(objEnum!=null) {
//				sReturn = objEnum.getMessage();
//			}			
		}//end main:
		return sReturn;
	}
	
	@Override
	public void setStatusLocalMessage(String sStatusMessageIn) {
		main:{			
			if(sStatusMessageIn == null) {
				if(this.sStatusLocalMessage!=null) {
					this.setStatusLocalStringPrevious(this.sStatusLocalMessage);
					this.sStatusLocalMessage = null;
				}				
				break main;
			}

			String sStatusMessage = this.getStatusLocalMessage();
			if(sStatusMessage==null) {
				this.setStatusLocalMessagePrevious(sStatusMessage);
				this.sStatusLocalMessage = sStatusMessageIn;
			}else {
				if(!sStatusMessageIn.equals(sStatusMessage)) {
					this.setStatusLocalMessagePrevious(sStatusMessage);
					this.sStatusLocalMessage = sStatusMessageIn;				
				}
			}
		}//end main:
	}
	
	@Override
	public String getStatusLocalMessagePrevious(){
		String sReturn = null;
		main:{
			if(this.sStatusLocalMessage!=null) {
				sReturn =  this.sStatusLocalMessage;
				break main;				
			}
			
			//Merke: Erst in OVPN-Klassen gibt es enum mit Message
//			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
//			if(objEnum!=null) {
//				sReturn = objEnum.getMessage();
//			}			
		}//end main:
		return sReturn;
	}
	
	@Override
	public void setStatusLocalMessagePrevious(String sStatusMessage) {
		this.sStatusLocalMessagePrevious = sStatusMessage;		
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
	public boolean setStatusLocal(String sStatusName, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bStatusExists = this.setStatusLocal(sStatusName, bStatusValue);
			if(!bStatusExists)break main;
				
			//ueberschreibe die Defaultmessage (aus dem enum) durch eine eigene...
			this.setStatusLocalMessage(sStatusMessage);
			
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
			
			//TODOGOON: 20231028 - Nun wurde alles aus enum umgestellt, auch die Message holen.
			//                     Man muss also nur mit dem Namen: Hole das Enum und damit kann es weitergehen.
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
			
			this.setStatusLocalMessage(sStatusLocalMessage);
			
			bReturn=true;
		}//end main:
		return bReturn;		
	}
//	public boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ {
//	boolean bFunction = false;
//	main:{
//		if(StringZZZ.isEmpty(sStatusName)) {
//			bFunction = true;
//			break main;
//		}
//					
//		bFunction = this.proofStatusLocalExists(sStatusName);															
//		if(bFunction){
//			
//			bFunction = this.proofStatusLocalChanged(sStatusName, bStatusValue);
//			if(bFunction) {		
//				
//				//Holes die HashMap
//				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
//				//Setze den erwiesenermassen geaenderten Status nun in die HashMap
//				hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
//				
//				
//				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
//				//Dann erzeuge den Event und feuer ihn ab.
////				if(this.objEventStatusLocalBroker!=null) {
////					IEventObjectStatusLocalSetZZZ event = new EventObject4ProcessWatchStatusLocalSetZZZ(this,1,sStatusName.toUpperCase(), bStatusValue);
////					this.objEventStatusLocalBroker.fireEvent(event);
////				}
//				
//				bFunction = true;
//			}
//		}										
//	}	// end main:
//	
//	return bFunction;
//}

	
	
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ;
//	@Override
//	public boolean setStatusLocal(Enum objEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
//		boolean bFunction = false;
//		main:{
//			if(objEnumStatusIn==null) {
//				break main;
//			}
//		//return this.getStatusLocal(objEnumStatus.name());
//		//Nein, trotz der Redundanz nicht machen, da nun der Event anders gefeuert wird, nämlich über das enum
//		
//	    //Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
//	    ProcessWatchRunnerZZZ.STATUSLOCAL enumStatus = (STATUSLOCAL) objEnumStatusIn;
//		String sStatusName = enumStatus.name();
//		bFunction = this.proofStatusLocalExists(sStatusName);															
//		if(bFunction == true){
//			
//			//Setze das Flag nun in die HashMap
//			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
//			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
//		
//			//Merke: Da die Events speziell sind, ist dies nichts für eine Abstrakte Klasse.
//			//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
//			//Dazu braucht es IEventBrokerStatusLocalSetUserZZZ
//			//Dann erzeuge den Event und feuer ihn ab.
//			//Merke: Nun aber ueber das enum		
////			if(this.getSenderStatusLocalUsed()!=null) {
////				IEventObjectStatusLocalSetZZZ event = new EventObject4ProcessWatchStatusLocalSetZZZ(this,1,enumStatus, bStatusValue);
////				this.getSenderStatusLocalUsed().fireEvent(event);
////			}			
//			bFunction = true;								
//		}										
//	}	// end main:
//	return bFunction;
//	}
	
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserZZZ#setStatusLocal(java.lang.Enum, java.lang.String, boolean)
	 */
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, int iIndex, boolean bStatusValue) throws ExceptionZZZ;
		
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
	@Override
	public abstract boolean setStatusLocal(Enum enumStatusIn, int iIndex, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ;
	
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
	public boolean proofStatusLocalChanged(Enum objEnumStatus, boolean bValue) throws ExceptionZZZ {
		return this.proofStatusLocalChanged(objEnumStatus.name(), bValue);
	}

	@Override
	public boolean proofStatusLocalChanged(String sStatusName, boolean bValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			
			HashMap<String,Boolean>hmStatusLocal = this.getHashMapStatusLocal();
			bReturn = StatusLocalHelperZZZ.proofStatusLocalChanged(hmStatusLocal, sStatusName, bValue);
			
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

	
}
