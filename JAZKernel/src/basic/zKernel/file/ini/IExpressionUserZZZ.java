package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertableZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;

public interface IExpressionUserZZZ extends IParseEnabledZZZ, IConvertableZZZ{		
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
