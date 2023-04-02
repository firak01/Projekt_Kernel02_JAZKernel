package basic.zKernel.flag;

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
public final class EventObjectFlagZsetZZZ extends EventObject implements IEventObjectFlagZsetZZZ{
	private String sFlagText;
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
	
	public int getID(){
		return this.iId;
	}
	public String getFlagText(){
		return this.sFlagText;
	}
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
}

