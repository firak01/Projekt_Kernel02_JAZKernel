package basic.zBasic;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IObjectZZZ extends IConstantZZZ, Serializable {  //Was nutzt einem schon das ExceptionZZZ - Objekt ohne die ganzen Fehlerkonstansten.
 	public abstract ExceptionZZZ getExceptionObject();
 	public abstract void setExceptionObject(ExceptionZZZ objException);
 	
 	public String toString(); //Nutzt intern eine Jakarta-Commons Klasse
}
