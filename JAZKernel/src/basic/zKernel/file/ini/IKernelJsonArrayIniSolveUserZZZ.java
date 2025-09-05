package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelJsonArrayIniSolveUserZZZ extends ISolveUserZZZ{
	public void updateValueJsonArraySolvedChanged() throws ExceptionZZZ;
	public void updateValueJsonArraySolvedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueJsonArraySolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueJsonArraySolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;	
}
