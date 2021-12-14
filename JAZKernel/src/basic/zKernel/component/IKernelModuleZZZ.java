package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IKernelModuleZZZ extends IFlagUserZZZ {
	public enum FLAGZ{
		ISKERNELMODULE;
	}	
	public static String sMODULE_UNDEFINED="Modul_undefined";
	
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des uebergeordneten JFrames. Ist der Package und Klassenname
	public abstract void resetModuleUsed();                      //wird das ISKERNELMODULE Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Modul entfernt und der Modulname neu geholt.
}
