package basic.zKernel.status;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.moduleExternal.log.watch.ILogFileWatchRunnerZZZ.STATUSLOCAL;
/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public abstract class AbstractEventObjectStatusLocalZZZ extends EventObject implements IEventObjectStatusLocalZZZ, Comparable<IEventObjectStatusLocalZZZ>{
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
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, null, bStatusValue);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, String sEnumName, String sStatusMessage, boolean bStatusValue)  throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, sStatusMessage, bStatusValue);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, sStatusMessage, bStatusValue);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum,  boolean bStatusValue) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, null, bStatusValue);
	}
	
	private boolean AbstractEventObjectStatusLocalNew_(Object source, String sEnumName, Enum objStatusEnum, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		if(objStatusEnum==null) {
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				throw ez;
			}else {
				//Ermittle das Enum aus dem Namen
				IEnumSetMappedStatusZZZ objEnumMapped = StatusLocalHelperZZZ.getStatusLocalEnumMappedAvailableByName(source, sEnumName);
				if(objEnumMapped==null) {
					ExceptionZZZ ez = new ExceptionZZZ( "Status not available for object StatusString '"+ sEnumName + "' (Object-Class: '" + this.getClass() +"')", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					throw ez;
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
		if(StringZZZ.isEmpty(this.sStatusMessage)) {
			if(this.getStatusLocal()==null) {
				return null;
			}else {
				return this.getStatusLocal().getStatusMessage();
			}
		}else {
			return this.sStatusMessage;
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
	   @Override
		public ExceptionZZZ getExceptionObject() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setExceptionObject(ExceptionZZZ objException) {
			// TODO Auto-generated method stub
			
		}

	
}

