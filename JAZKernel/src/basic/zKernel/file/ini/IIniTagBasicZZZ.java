package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IIniTagBasicZZZ extends ITagBasicZZZ, IParseEnabledZZZ, IExpressionUserZZZ, IValueArrayUserZZZ, IIniStructurePositionUserZZZ, IIniStructurePositionZZZ{
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	
	public String[] parseAsArray(String sExpression) throws ExceptionZZZ;
	public String[] parseAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ;
	
	//public Vector<String> parseExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;
	public Vector<String> parseAllVector(String sLineWithExpression) throws ExceptionZZZ;	
}
