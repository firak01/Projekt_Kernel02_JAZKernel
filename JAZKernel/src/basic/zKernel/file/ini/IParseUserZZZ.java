package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IParseUserZZZ extends IKernelParseEnabledZZZ{
	public boolean isParse(String sExpression) throws ExceptionZZZ;
	
	public void updateValueParseCalled() throws ExceptionZZZ;
	public void updateValueParseCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParseCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueParseCalled(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueParsed() throws ExceptionZZZ;
	public void updateValueParsed(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParsed(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueParsed(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueParsedChanged() throws ExceptionZZZ;
	public void updateValueParsedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueParsedChanged(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueParsedChanged(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
}
