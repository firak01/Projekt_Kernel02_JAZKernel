package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import junit.framework.TestCase;

public class ExpressionIniUtilZZZTest extends TestCase{

	
	public void testIsExpressionAny() {
		try {
			boolean bValue=false;
			String sString=null; String[]saString=null;
			
			
			sString = "abcd";
			bValue = ExpressionIniUtilZZZ.isExpressionAny(sString);
			assertFalse(bValue);
			
			saString = TestUtilZZZ.createStringsUsed_ExpressionAny();
			for(String sTemp : saString) {
				bValue = ExpressionIniUtilZZZ.isExpressionAny(sString);
				assertTrue(bValue);
			}
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
