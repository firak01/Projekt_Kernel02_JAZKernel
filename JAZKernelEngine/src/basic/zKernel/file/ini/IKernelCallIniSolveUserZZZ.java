package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelCallIniSolveUserZZZ extends ISolveUserZZZ{
	public void updateValueCallSolvedChanged() throws ExceptionZZZ;
	public void updateValueCallSolvedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueCallSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueCallSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;	
}
