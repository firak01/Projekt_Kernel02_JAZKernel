/**
 * lindhaueradmin, 06.07.2013
 */
package basic.zKernel;

import basic.zBasic.ExceptionZZZ;


/**Erweitere den Kernel um Methoden, mit denen aus der .ini - Datei der Kernelkonfiguration Parameter ausgelesen werden k�nnen.
 * Da das Kernelobjekt die Voraussetzung ist, aber nicht jede Klasse die Modulkonfiguration nutzen soll, ist dies eine Erweiterung.
 * 20180402: Manche Klassen sollen zwar Ihren Modulnamen, etc. angeben können, brauchen aber nicht das Kernel Objekt... darum nicht mehr: extends IKernelUserZZZ {
 * @author lindhaueradmin
 *
 * lindhaueradmin, 06.07.2013
 */
public interface IKernelModuleUserZZZ{ //FGL 20180402: Manche Klassen sollen zwar Ihren Modulnamen, etc. angeben können, brauchen aber nicht das Kernel Objekt... darum nicht mehr: extends IKernelUserZZZ {
	public abstract String getModuleName();    //Z.B. der Name des �bergeordneten JFrames. Ist der Package und Klassenname
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname		
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen. 
}
