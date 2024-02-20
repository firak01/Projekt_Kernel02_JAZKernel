package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public interface IProgramZZZ extends IModuleUserZZZ, IFlagZUserZZZ{	
	public boolean startCustom() throws ExceptionZZZ;//das was ggfs. in run() aufgerufen wird. Merke: run() wird ja beim Thread-Start ausgeführt.
	public boolean reset() throws ExceptionZZZ;
	
	
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname
	//kein setProgramName(...), da der Name berechnet wird aus der Klasse
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen.
	public abstract void resetProgramUsed();                      //wird das ISKERNELPROGRAM Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Program entfernt und der Programname/-alias neu geholt.
	
	//#############################################################
	//### FLAGZ
	//#############################################################	
	public enum FLAGZ{
		ISPROGRAM; 
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(FLAGZ objEnumFlag);
	public boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
