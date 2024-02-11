package basic.zKernel.status;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public final class EventObjectStatusLocalZZZ extends EventObject implements IEventObjectStatusLocalZZZ, Comparable<IEventObjectStatusLocalZZZ>{
	private IEnumSetMappedStatusZZZ objStatusEnum=null;
	private String sStatusText=null;
	private boolean bStatusValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventObjectStatusLocalZZZ(Object source, String sStatusText, boolean bStatusValue) {
		super(source);
		this.sStatusText = sStatusText;
		this.bStatusValue = bStatusValue;
	}
	
	public EventObjectStatusLocalZZZ(Object source, Enum objFlagEnum, boolean bFlagValue) {
		super(source);
		this.objStatusEnum=(IEnumSetMappedStatusZZZ) objFlagEnum;
		this.bStatusValue = bFlagValue;
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
	public String getStatusText(){
		if(this.objStatusEnum==null) {
			return this.sStatusText;
		}else {
			return this.objStatusEnum.name();
		}
	}
	
	@Override
	public boolean getStatusValue() {
		return this.bStatusValue;
	}

	@Override
	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
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
	     //return this.getNavigatorElementAlias().hashCode();
		   return this.getStatusText().hashCode();
	   }

	

}

