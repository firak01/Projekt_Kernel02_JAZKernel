package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public interface IProgramRunnableZZZ extends IProgramZZZ, Runnable{ 		
	//Merke: StartCutom() und startThread89 gibt es in IProgramZZZ, hier geht es darum, dass dieses Program in einer Endlsoscheife laufen darf.
	

	//#############################################################
	//### FLAGZ
	//#############################################################	
	public enum FLAGZ{
		REQUESTSTOP; 
	}	
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(FLAGZ objEnumFlag);
	public boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
}
