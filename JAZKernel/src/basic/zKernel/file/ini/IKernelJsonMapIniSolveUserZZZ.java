package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelJsonMapIniSolveUserZZZ extends ISolveUserZZZ{
	public void updateValueJsonMapSolvedChanged() throws ExceptionZZZ;
	public void updateValueJsonMapSolvedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueJsonMapSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueJsonMapSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;	
}
