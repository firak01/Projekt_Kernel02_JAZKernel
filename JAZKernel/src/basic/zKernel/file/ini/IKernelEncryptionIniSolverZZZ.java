package basic.zKernel.file.ini;

import basic.zBasic.util.crypt.code.ICryptUserZZZ;

public interface IKernelEncryptionIniSolverZZZ extends ICryptUserZZZ{
	public enum FLAGZ{
		USEENCRYPTION
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name(), gib den Typen an, damit es so in der Entwicklungsumgebung, in der Vorschlagsliste steht.
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ);	
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue);
	public boolean[]setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue);
}
