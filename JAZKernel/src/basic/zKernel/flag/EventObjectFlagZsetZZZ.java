package basic.zKernel.flag;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zKernel.status.EventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public final class EventObjectFlagZsetZZZ extends EventObject implements IEventObjectFlagZsetZZZ, Comparable<IEventObjectFlagZsetZZZ>{
	private Enum objFlagEnum=null;
	private String sFlagText=null;
	private int iId;
	private boolean bFlagValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public EventObjectFlagZsetZZZ(Object source, int iID,  String sFlagText, boolean bFlagValue) {
		super(source);
		this.sFlagText = sFlagText;
		this.iId = iID;
		this.bFlagValue = bFlagValue;
	}
	
	public EventObjectFlagZsetZZZ(Object source, int iID,  Enum objFlagEnum, boolean bFlagValue) {
		super(source);
		this.objFlagEnum=objFlagEnum;
		this.iId = iID;
		this.bFlagValue = bFlagValue;
	}
	
	@Override
	public int getID(){
		return this.iId;
	}
	
	@Override
	public Enum getFlagEnum() {
		return this.objFlagEnum;
	}
	
	@Override
	public String getFlagText(){
		if(this.objFlagEnum==null) {
			return this.sFlagText;
		}else {
			return this.objFlagEnum.name();
		}
	}
	
	@Override
	public boolean getFlagValue() {
		return this.bFlagValue;
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
	public int compareTo(IEventObjectFlagZsetZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			String sTextToCompare = o.getFlagText();
			boolean bValueToCompare = o.getFlagValue();
			
			String sText = this.getFlagText();
			boolean bValue = this.getFlagValue();
			
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
	     if (!(aThat instanceof EventObjectFlagZsetZZZ)) return false;
	     EventObjectFlagZsetZZZ that = (EventObjectFlagZsetZZZ)aThat;
	     
	     String sTextToCompare = that.getFlagText();
		 boolean bValueToCompare = that.getFlagValue();
			
			String sText = this.getFlagText();
			boolean bValue = this.getFlagValue();
	     
			if(sTextToCompare.equals(sText) && bValueToCompare==bValue) return true;
			
	     return false;     
	   }

	   /**
	   * A class that overrides equals must also override hashCode.
	   */
	   @Override 
	   public int hashCode() {
	     //return this.getNavigatorElementAlias().hashCode();
		   return this.getFlagText().hashCode();
	   }

}

