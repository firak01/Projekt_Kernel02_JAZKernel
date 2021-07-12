package basic.zKernel;

import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKerneJsonIniSolverZZZ;

public interface IKernelExpressionIniConverterUserZZZ extends IKernelExpressionIniSolverZZZ, IKerneJsonIniSolverZZZ{
	
	//20190718: Merke: Durch KernelConfigEntryZZZ Klasse ist das nicht mehr notwendig.
//	public boolean isValueConverted();
//	public void isValueConverted(boolean bStatus);
//	public String getValueRaw();
//	public void setValueRaw(String sValueRaw);
}
