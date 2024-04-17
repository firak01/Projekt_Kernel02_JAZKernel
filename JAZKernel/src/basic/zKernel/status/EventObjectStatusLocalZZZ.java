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
public final class EventObjectStatusLocalZZZ extends AbstractEventObjectStatusLocalZZZ {
	
		
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 * @throws ExceptionZZZ 
	 */
	public EventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue) throws ExceptionZZZ {
		super(source, sEnumName, bStatusValue);	
	}
	
	public EventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue, String sStatusText) throws ExceptionZZZ {
		super(source, sEnumName, bStatusValue,sStatusText);	
	}
	
	public EventObjectStatusLocalZZZ(Object source, Enum objStatusEnum, boolean bStatusValue, String sStatusText) throws ExceptionZZZ {
		super(source,objStatusEnum, bStatusValue,sStatusText);	
	}
	
	public EventObjectStatusLocalZZZ(Object source, Enum objStatusEnum, boolean bFlagValue) throws ExceptionZZZ {
		super(source, objStatusEnum, bFlagValue);
	}


}

