package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IKernelModuleZZZ extends IFlagZEnabledZZZ {
	public enum FLAGZ{
		ISKERNELMODULE;
	}	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelModuleZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelModuleZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelModuleZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IKernelModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean proofFlagSetBefore(IKernelModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public static String sMODULE_UNDEFINED="Modul_undefined";
	
	
	//Merke: Diese Methoden sind in AbstractKernelModule definiert. Müssen in Klassen, die nicht davon erben können extra implementiert werden.
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des uebergeordneten JFrames. Ist der Package und Klassenname	
	//public abstract void setModuleName(String sModuleName);      //Nein, solle es nicht geben, da der Modulname aus der Klasse berechnet wird!!! Siehe: return KernelUIZZZ.getModuleUsedName(this);
	
		
	public abstract void resetModuleUsed();                      //wird das ISKERNELMODULE Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Modul entfernt und der Modulname neu geholt.
	public abstract boolean reset();
	
	
}
