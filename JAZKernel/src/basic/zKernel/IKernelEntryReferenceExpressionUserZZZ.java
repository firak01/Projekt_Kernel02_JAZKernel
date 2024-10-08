package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.file.ini.IExpressionUserZZZ;

//### Damit bleiben die Werte in der Variablen IKernelConfigSectionEntryZZZ objEntry erhalten 
public interface IKernelEntryReferenceExpressionUserZZZ extends IKernelConfigSectionEntryUserZZZ, IExpressionUserZZZ{
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		
	public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ;		
	public Vector<String>parseFirstVector(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
}
