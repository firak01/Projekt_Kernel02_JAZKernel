package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IConversionUserZZZ extends IConvertEnabledZZZ{		
	public boolean isConversion(String sExpression) throws ExceptionZZZ;
	
	public String convertAsExpression() throws ExceptionZZZ;
	public String convertAsExpression(String sExpression) throws ExceptionZZZ;

	//public Vector<String> parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ;
	//public Vector<String> parseAllVectorAsExpression(String sExpression) throws ExceptionZZZ;
	
	
	//Merke: Auf dieser Ebene der Vererbung gibt es keine Kernel Flags, also geht nicht
//	public enum FLAGZ{
//		USECONVERSION
//	}
//	
//  ..... getFlag(...), .....	
	
}
