package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IParseUserZZZ extends IKernelParseEnabledZZZ{
	public boolean isParse(String sExpression) throws ExceptionZZZ;
	
	public void updateValueParseCalled() throws ExceptionZZZ;
	public void updateValueParseCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParseCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueParseCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, String sExpression) throws ExceptionZZZ;
	
	public void updateValueParsed() throws ExceptionZZZ;
	public void updateValueParsed(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParsed(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueParsed(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueParsedChanged() throws ExceptionZZZ;
	public void updateValueParsedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
}
