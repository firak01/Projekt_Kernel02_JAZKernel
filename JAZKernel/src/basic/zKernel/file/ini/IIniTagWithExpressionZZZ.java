package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IIniTagWithExpressionZZZ {	
	public enum FLAGZ{
		USEEXPRESSION
	}
	
	//Das Entry-Objekt wird in den Methoden fortlaufend gefüllt. Damit bekommt man auch Zwischenstände.
	//Z.B. beim entschluesseln eines Werts, kann man so auch den reinen verschluesselten Wert zurueckgreifen.
	//public IKernelConfigSectionEntryZZZ getEntry();  
	//public void setEntry(IKernelConfigSectionEntryZZZ objEntry);
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IIniTagWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ);
	public boolean setFlag(IIniTagWithExpressionZZZ.FLAGZ objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IIniTagWithExpressionZZZ.FLAGZ[] objEnum_IKernelExpressionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IIniTagWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
}
