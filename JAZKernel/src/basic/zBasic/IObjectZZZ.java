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
public interface IObjectZZZ extends IExceptionObjectUserZZZ, IConstantZZZ, Serializable, Cloneable {  //Was nutzt einem schon das ExceptionZZZ - Objekt ohne die ganzen Fehlerkonstansten.
 	public abstract Object clonez() throws ExceptionZZZ;
 	
	public abstract String toString(); //Nutzt intern eine Jakarta-Commons Klasse, wenn nicht anders als in AbstractObjectZZZ ueberschrieben.
}
