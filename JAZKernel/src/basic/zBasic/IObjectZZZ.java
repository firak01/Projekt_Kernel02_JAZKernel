package basic.zBasic;

import java.util.HashMap;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IObjectZZZ extends IConstantZZZ {  //Was nutzt einem schon das ExceptionZZZ - Objekt ohne die ganzen Fehlerkonstansten.
 	public abstract ExceptionZZZ getExceptionObject();
 	public abstract void setExceptionObject(ExceptionZZZ objException);
 	
 	public void logLineDate(String sLog); //Nutzt intern KernelLogZZZ-statische Methode;
 	public String toString(); //Nutzt intern eine Jakarta-Commons Klasse
}
