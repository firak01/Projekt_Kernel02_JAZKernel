package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IIniTagWithConversionZZZ {	
	public enum FLAGZ{
		USECONVERSION
	}
	
	//Das Entry-Objekt wird in den Methoden fortlaufend gefüllt. Damit bekommt man auch Zwischenstände.
	//Z.B. beim entschluesseln eines Werts, kann man so auch den reinen verschluesselten Wert zurueckgreifen.
	//public IKernelConfigSectionEntryZZZ getEntry();  
	//public void setEntry(IKernelConfigSectionEntryZZZ objEntry);
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IIniTagWithConversionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ);
	public boolean setFlag(IIniTagWithConversionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IIniTagWithConversionZZZ.FLAGZ[] objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
