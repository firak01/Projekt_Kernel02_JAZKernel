package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicsZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IIniTagSimpleZZZ extends ITagBasicsZZZ, IKernelConfigSectionEntryUserZZZ, IComputableExpressionZZZ {
	public Vector<String> computeExpressionFirstVector(String sExpression) throws ExceptionZZZ;
	public Vector<String> computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
	public Vector<String> computeAsExpressionFirstVector(String sExpression) throws ExceptionZZZ;
	public Vector<String> computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	public String[] computeAsArray(String sLineWithExpression, String sDelimiterIn) throws ExceptionZZZ;
}
