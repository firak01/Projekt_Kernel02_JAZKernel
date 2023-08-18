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
public class EventObjectStatusLocalSetZZZ extends EventObject implements IEventObjectStatusLocalSetZZZ{
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
	}

	
}

