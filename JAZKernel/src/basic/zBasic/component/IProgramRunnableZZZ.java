package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public interface IProgramRunnableZZZ extends IProgramZZZ, Runnable{ 
	public enum FLAGZ{
		REQUESTSTOP; 
	}	
	//Merke: Start gibt es in IProgramZZZ, hier geht es darum, dass dieses Program in einer Endlsoscheife laufen darf.
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag);
	public boolean setFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IProgramRunnableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname
	//kein setProgramName(...), da der Name berechnet wird aus der Klasse
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen.
	public abstract void resetProgramUsed();                      //wird das ISKERNELPROGRAM Flag neu auf true gesetzt, dann ist angeraten das durchzuführen. Somit würde ein ggfs. ererbtes anderes Program entfernt und der Programname/-alias neu geholt.
	
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
