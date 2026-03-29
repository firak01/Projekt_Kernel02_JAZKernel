package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelCallIniParseUserZZZ extends ISolveUserZZZ{
	public void updateValueCallParsedChanged() throws ExceptionZZZ;
	public void updateValueCallParsedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueCallParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueCallParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;	
}
