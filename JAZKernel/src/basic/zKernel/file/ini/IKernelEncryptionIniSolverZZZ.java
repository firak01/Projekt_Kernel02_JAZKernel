package basic.zKernel.file.ini;

import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;

public interface IKernelEncryptionIniSolverZZZ{
	public enum FLAGZ{
		USEENCRYPTION
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag);
	public void setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue);
}
