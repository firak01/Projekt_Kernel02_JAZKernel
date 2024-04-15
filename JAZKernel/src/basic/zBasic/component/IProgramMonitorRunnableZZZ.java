package basic.zBasic.component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;

public interface IProgramMonitorRunnableZZZ extends IProgramMonitorZZZ, IProgramRunnableZZZ {
	//Merke: StartCutom() gibt es in IProgramZZZ, hier geht es darum, dass dieses Program in einer Endlsoscheife laufen darf.
	public boolean startAsThread() throws ExceptionZZZ;      //hier wird ein eigener Thread erzeugt.

	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
