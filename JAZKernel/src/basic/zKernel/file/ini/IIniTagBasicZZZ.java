package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.IValueMapUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IIniTagBasicZZZ extends ITagBasicZZZ, IParseEnabledZZZ, IExpressionUserZZZ, IValueArrayUserZZZ, IValueMapUserZZZ, IIniStructurePositionUserZZZ, IIniStructurePositionZZZ{
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	
	public String[] parseAsArray(String sExpression) throws ExceptionZZZ;
	public String[] parseAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ;
}
