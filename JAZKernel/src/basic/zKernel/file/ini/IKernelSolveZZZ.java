package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelSolveZZZ extends ISolveZZZ{
		public IKernelConfigSectionEntryZZZ solveAsEntryNew(String sExpression) throws ExceptionZZZ;
		public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression) throws ExceptionZZZ;
		public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ;
		public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ;
		
		//angedacht...
		//public String[] solveAsArray(String sExpression) throws ExceptionZZZ;
		//public String[] solveAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ;	
}
