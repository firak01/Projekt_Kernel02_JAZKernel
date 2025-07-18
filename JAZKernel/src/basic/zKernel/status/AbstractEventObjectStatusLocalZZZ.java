package basic.zKernel.status;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ILogZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringZZZ;
import basic.zKernel.KernelLogZZZ;

/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public abstract class AbstractEventObjectStatusLocalZZZ extends EventObject implements ILogZZZ, IEventObjectStatusLocalZZZ, Comparable<IEventObjectStatusLocalZZZ>{
	//Merke: Das Interface comparable kann nicht mehrmals eingebunden werden. Daher in der Ausgangsklasse comparable nutzen und dort die Methoden erstellen.
	protected IEnumSetMappedStatusZZZ objStatusEnum=null;
	protected String sStatusMessage=null;
	protected boolean bStatusValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public AbstractEventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue)  throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, bStatusValue, null);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue, String sStatusMessage)  throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, bStatusValue, sStatusMessage);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, bStatusValue, sStatusMessage);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum,  boolean bStatusValue) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, bStatusValue, null);
	}
	
	private boolean AbstractEventObjectStatusLocalNew_(Object source, String sEnumName, Enum objStatusEnum, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		if(objStatusEnum==null) {
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				throw ez;
			}else {
				String sLog;
				
				//Ermittle das Enum aus dem Namen
				IEnumSetMappedStatusZZZ objEnumMapped = StatusLocalAvailableHelperZZZ.searchEnumMappedByName(source, sEnumName, true);
				if(objEnumMapped==null) {
					
					//20240319: Wenn es ein Monitor-Objekt ist, dann kann der Status auch aus der cascadedHashMap stammen.
					if(source instanceof IStatusLocalMapForMonitoringStatusLocalUserZZZ) {
						sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum not found in normal Status: '" + sEnumName + "', this is a monitor Objekt therefore a search in the cascading Hashmap may will succeed. ";
						this.logProtocolString(sLog);
						
						IStatusLocalMapForMonitoringStatusLocalUserZZZ sourceAsMonitor = (IStatusLocalMapForMonitoringStatusLocalUserZZZ) source;
						HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ> hmFromMonitor = sourceAsMonitor.getHashMapEnumSetForCascadingStatusLocal();
						
						Set<IEnumSetMappedStatusZZZ> setKeyFromMonitor = hmFromMonitor.keySet();
						Iterator<IEnumSetMappedStatusZZZ> itKeyFromMonitor = setKeyFromMonitor.iterator();																
						while(itKeyFromMonitor.hasNext()) {
							IEnumSetMappedStatusZZZ objKey = (IEnumSetMappedStatusZZZ) itKeyFromMonitor.next();
							if(objKey.getName().equalsIgnoreCase(sEnumName)) {
								objEnumMapped = objKey;
								sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum '" + sEnumName + "' found in the cascading Hashmap as Key. ";
								this.logProtocolString(sLog);
								break;
							}
							
							IEnumSetMappedStatusZZZ objValue = (IEnumSetMappedStatusZZZ) hmFromMonitor.get(objKey);
							if(objValue.getName().equalsIgnoreCase(sEnumName)) {
								objEnumMapped = objValue;
								sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum '" + sEnumName + "'  found in the cascading Hashmap as Value. ";
								this.logProtocolString(sLog);
								break;
							}
						}
					}
										
					if(objEnumMapped==null) {
						ExceptionZZZ ez = new ExceptionZZZ( "Status not available for Source-Object ("+source.getClass().getName()+ ") - StatusString '"+ sEnumName + "' (Object-Class: '" + this.getClass() +"')", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						throw ez;
					}
				}
				this.setStatusLocal(objEnumMapped);
			}
		}else {
			this.setStatusLocal(objStatusEnum);
		}
		
		this.setStatusMessage(sStatusMessage);		
		this.setStatusValue(bStatusValue);
		return true;
	}
	
	
	@Override
	public String getStatusText(){
		if(this.getStatusLocal()==null) {
			return this.getStatusMessage();
		}else {
			return this.getStatusLocal().getName();
		}
	}

	@Override
	public String getStatusAbbreviation(){
		if(this.getStatusLocal()==null) {
			return this.getStatusText();
		}else {
			return this.getStatusLocal().getAbbreviation();
		}
	}
	
	@Override
	public String getStatusMessage(){
		if(this.sStatusMessage==null) { //Wenn NULL als Meldung eingeht, dann gib den Standardwert zurueck.
			if(this.getStatusLocal()==null) {
				return null;
			}else {
				return this.getStatusLocal().getStatusMessage();
			}
		}else {
			return this.sStatusMessage; //Merke: Ein Leerstring waere damit ein erlaubter Meldungstext
		}		
	}
	
	@Override 
	public void setStatusMessage(String sStatusMessage) {
		this.sStatusMessage = sStatusMessage;
	}
	
	@Override
	public boolean getStatusValue() {
		return this.bStatusValue;
	}
	
	@Override
	public void setStatusValue(boolean bValue) {
		this.bStatusValue = bValue;
	}

	@Override
	public Enum getStatusEnum() {
		return (Enum) this.objStatusEnum;
	}
	
	@Override
	public IEnumSetMappedStatusZZZ getStatusLocal() {
		return this.objStatusEnum;
	}
	
	@Override
	public void setStatusLocal(Enum objEnum) {
		this.objStatusEnum = (IEnumSetMappedStatusZZZ) objEnum;
	}
	
	@Override
	public void setStatusLocal(IEnumSetMappedStatusZZZ objEnumSet) {
		this.objStatusEnum = objEnumSet;
	}
	
	
	//### Aus dem Interface Comparable	
	@Override
	public int compareTo(IEventObjectStatusLocalZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			String sTextToCompare = o.getStatusText();
			boolean bValueToCompare = o.getStatusValue();
			
			String sText = this.getStatusText();
			boolean bValue = this.getStatusValue();
			
			if(sTextToCompare.equals(sText) && bValueToCompare==bValue) iReturn = 1;
			
			
		}
		return iReturn;
	}

	 /**
	   * Define equality of state.
	   */
	   @Override 
	   public boolean equals(Object aThat) {
	     if (this == aThat) return true;
	     if (!(aThat instanceof EventObjectStatusLocalZZZ)) return false;
	     EventObjectStatusLocalZZZ that = (EventObjectStatusLocalZZZ)aThat;
	     
	     String sTextToCompare = that.getStatusText();
		 boolean bValueToCompare = that.getStatusValue();
			
			String sText = this.getStatusText();
			boolean bValue = this.getStatusValue();
	     
			if(sTextToCompare.equals(sText) && bValueToCompare==bValue) return true;
			
	     return false;     
	   }

	   /**
	   * A class that overrides equals must also override hashCode.
	   */
	   @Override 
	   public int hashCode() {
		   return this.getStatusText().hashCode();
	   }
	   
	   
	   //#################################################################
	   //### aus IObjectZZZ
	   //Meine Variante Objekte zu clonen
		@Override
		public Object clonez() throws ExceptionZZZ {
			try {
				return this.clone();
			}catch(CloneNotSupportedException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
					
			}
		}
		
		//#################################################################
		//### aus ILogZZZ
		@Override
		public synchronized void logLineDate(String sLog) throws ExceptionZZZ {
			String sTemp = KernelLogZZZ.computeLineDate(this, sLog);
			ObjectZZZ.logLine(sTemp);		
		}
		
		//++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public synchronized void logProtocolString(String sLog) throws ExceptionZZZ{
			this.logProtocolString(null, sLog);
		}
		
		@Override
		public synchronized void logProtocolString(String[] saLog) throws ExceptionZZZ{
			this.logProtocolString(null, saLog);
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String sLog) throws ExceptionZZZ{
			String sLogUsed;
			if(obj==null) {
				sLogUsed = LogStringZZZ.getInstance().compute(sLog);			
			}else {
				sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog);			
			}
			System.out.println(sLogUsed);
		}

		@Override
		public synchronized void logProtocolString(Object obj, String[] saLog) throws ExceptionZZZ{
			main:{
				if(ArrayUtilZZZ.isNull(saLog)) break main;
				
				if(obj==null) {
					for(String sLog : saLog) {
						this.logProtocolString(sLog);
					}
				}else {
					for(String sLog : saLog) {
						this.logProtocolString(obj, sLog);
					}	
				}
				
			}//end main:
		}
		

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public synchronized void logProtocolString(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			this.logProtocolString(null, sLog, ienumMappedLogString);
		}
		
		@Override
		public synchronized void logProtocolString(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			this.logProtocolString(null, saLog, ienumaMappedLogString);
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sLogUsed;
			if(obj==null) {
				sLogUsed = LogStringZZZ.getInstance().compute(sLog, ienumMappedLogString);
			}else {
				sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog, ienumMappedLogString);
			}
			System.out.println(sLogUsed);
		}
		
		@Override
		public synchronized void logProtocolString(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				this.logProtocolString(saLog);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : saLog) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocolString(sLog,ienumaMappedLogString[iIndex]);
						iIndex++;
					}else {
						this.logProtocolString(saLog);
					}
				}
			}else {
				for(String sLog : saLog) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocolString(obj, sLog,ienumaMappedLogString[iIndex]);
						iIndex++;
					}else {
						this.logProtocolString(saLog);
					}
				}			
			}
		}//end main:
		}
}

