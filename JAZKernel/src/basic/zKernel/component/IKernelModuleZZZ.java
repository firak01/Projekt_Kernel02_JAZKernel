/**
 * lindhaueradmin, 06.07.2013
 */
package basic.zKernel.component;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.flag.IFlagZZZ;


/**Erweitere den Kernel um Methoden, mit denen aus der .ini - Datei der Kernelkonfiguration Parameter ausgelesen werden k�nnen.
 * Da das Kernelobjekt die Voraussetzung ist, aber nicht jede Klasse die Modulkonfiguration nutzen soll, ist dies eine Erweiterung.
 * 20180402: Manche Klassen sollen zwar Ihren Modulnamen, etc. angeben können, brauchen aber nicht das Kernel Objekt... darum nicht mehr: extends IKernelUserZZZ {
 * @author lindhaueradmin
 *
 * lindhaueradmin, 06.07.2013
 */
public interface IKernelModuleZZZ extends IFlagZZZ { //FGL 20180402: Manche Klassen sollen zwar Ihren Modulnamen, etc. angeben können, brauchen aber nicht das Kernel Objekt... darum nicht mehr: extends IKernelUserZZZ {
	public enum FLAGZ{
		ISKERNELMODULE; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}	
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des �bergeordneten JFrames. Ist der Package und Klassenname
}
