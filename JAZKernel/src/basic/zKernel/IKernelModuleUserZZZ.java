/**
 * lindhaueradmin, 06.07.2013
 */
package basic.zKernel;

import basic.zBasic.ExceptionZZZ;


/**Erweitere den Kernel um Methoden, mit denen aus der .ini - Datei der Kernelkonfiguration Parameter ausgelesen werden können.
 * Das das Kernelobjekt die Voraussetzung ist, aber nicht jede Klasse die Modulkonfiguration nutzen soll, ist dies eine Erweiterung.
 * 
 * @author lindhaueradmin
 *
 * lindhaueradmin, 06.07.2013
 */
public interface IKernelModuleUserZZZ extends IKernelUserZZZ {
	public abstract String getModuleName();    //Z.B. der Name des übergeordneten JFrames. Ist der Package und Klassenname
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname		
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen. 
}
