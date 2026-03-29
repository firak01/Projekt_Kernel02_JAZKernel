package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IProgramRunnableZZZ extends IProgramZZZ, Runnable{ 		
	//Merke: StartCutom() gibt es in IProgramZZZ, hier geht es darum, dass dieses Program in einer Endlsoscheife laufen darf.
	public boolean startAsThread() throws ExceptionZZZ;      //hier wird ein eigener Thread erzeugt.

	//#############################################################
	//### FLAGZ
	//#############################################################	
	public enum FLAGZ{
		REQUEST_STOP; 
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
}
