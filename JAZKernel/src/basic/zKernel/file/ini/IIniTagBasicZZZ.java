package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.IValueMapUserZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IIniTagBasicZZZ extends ITagBasicZZZ, IExpressionUserZZZ, IValueArrayUserZZZ, IValueMapUserZZZ, IIniStructurePositionUserZZZ, IIniStructurePositionZZZ{
	
//	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ;
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ;
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ;
//	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	
	public String[] parseAsArray(String sExpression) throws ExceptionZZZ;
	public String[] parseAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ;
}
