package basic.zKernel.status;

import java.util.EventObject;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public abstract class AbstractEventObjectStatusLocalSetZZZ extends EventObject implements IEventObjectStatusLocalSetZZZ{
	//Merke: Das Interface comparable kann nicht mehrmals eingebunden werden. Daher in der Ausgangsklasse comparable nutzen und dort die Methoden erstellen.
	protected String sStatusText=null;
	protected int iId;
	protected boolean bStatusValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public AbstractEventObjectStatusLocalSetZZZ(Object source, int iID,  String sStatusText, boolean bStatusValue) {
		super(source);		
		this.sStatusText = sStatusText;
		this.iId = iID;
		this.bStatusValue = bStatusValue;
	}
	
	@Override
	public int getID(){
		return this.iId;
	}
	
	@Override
	public String getStatusText(){		
		return this.sStatusText;
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

	
}

