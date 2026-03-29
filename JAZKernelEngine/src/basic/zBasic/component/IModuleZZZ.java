package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IModuleZZZ extends IFlagZEnabledZZZ {
	public static String sMODULE_UNDEFINED="Modul_undefined";
	
	//Merke: Diese Methoden sind in AbstractKernelModule definiert. Müssen in Klassen, die nicht davon erben können extra implementiert werden.
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des uebergeordneten JFrames. Ist der Package und Klassenname	
	//public abstract void setModuleName(String sModuleName);      //Nein, solle es nicht geben, da der Modulname aus der Klasse berechnet wird!!! Siehe: return KernelUIZZZ.getModuleUsedName(this);
		
	public abstract void resetModuleUsed();                      //wird das ISKERNELMODULE Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Modul entfernt und der Modulname neu geholt.
	public abstract void reset();
	
	//#############################################################
	//### FLAGZ
	//#############################################################	
	public enum FLAGZ{
		ISMODULE;
	}	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IModuleZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IModuleZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean proofFlagSetBefore(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
