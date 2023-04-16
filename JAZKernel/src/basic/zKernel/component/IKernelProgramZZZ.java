package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public interface IKernelProgramZZZ  extends IFlagZUserZZZ{ 
	public enum FLAGZ{
		ISKERNELPROGRAM; 
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelProgramZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IKernelProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname
	//kein setProgramName(...), da der Name berechnet wird aus der Klasse
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen.
	public abstract void resetProgramUsed();                      //wird das ISKERNELPROGRAM Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Program entfernt und der Programname/-alias neu geholt.
}
