package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.file.ini.ISubstituteUserZZZ;

//### Damit bleiben die Werte in der Variablen IKernelConfigSectionEntryZZZ objEntry erhalten 
public interface IKernelEntryReferenceSubstituteUserZZZ extends IKernelConfigSectionEntryUserZZZ, ISubstituteUserZZZ{
	public String substitute(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public String substitute(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		
	public String substituteParsed(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ;		
	public String substituteParsed(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String substituteParsedPost(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public String substituteParsedPost(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	public String substituteParsedPostCustom(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public String substituteParsedPostCustom(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
	
	public String substituteParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;

}
