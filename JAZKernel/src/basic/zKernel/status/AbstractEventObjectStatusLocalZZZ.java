package basic.zKernel.status;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
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
	public AbstractEventObjectStatusLocalZZZ(Object source, String sStatusMessage, boolean bStatusValue) {
		super(source);		
		this.setStatusMessage(sStatusMessage);		
		this.setStatusValue(bStatusValue);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum,  boolean bStatusValue) {
		super(source);		
		this.setStatusLocal(objStatusEnum);
		this.setStatusValue(bStatusValue);
	}
	
	@Override
	public String getStatusText(){
		if(this.objStatusEnum==null) {
			return this.getStatusMessage();
		}else {
			return this.objStatusEnum.getName();
		}
	}
	
	@Override
	public String getStatusMessage(){		
		return this.sStatusMessage;
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

