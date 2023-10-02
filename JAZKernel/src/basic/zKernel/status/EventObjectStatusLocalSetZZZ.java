package basic.zKernel.status;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public class EventObjectStatusLocalSetZZZ extends EventObject implements IEventObjectStatusLocalSetZZZ, Comparable<IEventObjectStatusLocalSetZZZ>{
	private Enum objStatusEnum=null;	
	private String sStatusText=null;
	private int iId;
	private boolean bStatusValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventObjectStatusLocalSetZZZ(Object source, int iID,  String sStatusText, boolean bStatusValue) {
		super(source);		
		this.sStatusText = sStatusText;
		this.iId = iID;
		this.bStatusValue = bStatusValue;
	}
	
	public EventObjectStatusLocalSetZZZ(Object source, int iID,  Enum objStatusEnum, boolean bStatusValue) {
		super(source);		
		this.objStatusEnum=objStatusEnum;		
		this.iId = iID;
		this.bStatusValue = bStatusValue;
	}
	
	@Override
	public int getID(){
		return this.iId;
	}
	
	@Override
	public Enum getStatusEnum() {
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
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
	}

	//### Aus dem Interface Comparable	
	@Override
	public int compareTo(IEventObjectStatusLocalSetZZZ o) {
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
	
   @Override 
   public boolean equals(Object aThat) {
     if (this == aThat) return true;
     if (!(aThat instanceof EventObjectStatusLocalSetZZZ)) return false;
     EventObjectStatusLocalSetZZZ that = (EventObjectStatusLocalSetZZZ)aThat;
     
     String sTextToCompare = that.getStatusText();
	 boolean bValueToCompare = that.getStatusValue();
		
		String sText = this.getStatusText();
		boolean bValue = this.getStatusValue();
     
		if(sTextToCompare.equals(sText) && bValueToCompare==bValue) return true;
		
     return false;     
   }

   /** A class that overrides equals must also override hashCode.*/
   @Override 
   public int hashCode() {
	   return this.getStatusText().hashCode();
   }
}

