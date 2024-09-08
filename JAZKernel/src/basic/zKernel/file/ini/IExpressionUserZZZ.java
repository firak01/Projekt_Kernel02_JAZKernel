package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IExpressionUserZZZ extends IParseEnabledZZZ{//, IConvertEnabledZZZ{		
	public boolean isExpression(String sExpression) throws ExceptionZZZ;
	
	public String parseAsExpression() throws ExceptionZZZ;
	public String parseAsExpression(String sExpression) throws ExceptionZZZ;

	public Vector<String> parseFirstVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ;
	public Vector<String> parseAllVectorAsExpression(String sLineWithExpression) throws ExceptionZZZ;
	
	
	//Merke: Auf dieser Ebene der Vererbung gibt es keine Kernel Flags, also geht nicht
//	public enum FLAGZ{
//		USEEXPRESSION
//	}
//	
//  ..... getFlag(...), .....	
	
}
