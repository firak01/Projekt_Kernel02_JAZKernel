package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import junit.framework.TestCase;

public class ExpressionIniUtilZZZTest extends TestCase{

	
	public void testIsExpressionDefaultAny() {
		try {
			boolean bValue=false;
			String sString=null; String[]saString=null;
			
			
			sString = "abcd";
			bValue = ExpressionIniUtilZZZ.isExpressionDefaultAny(sString);
			assertFalse(bValue);
			
			saString = TestUtilZZZ.createStringsUsed_ExpressionAny();
			for(String sTemp : saString) {
				System.out.println(sTemp);
				bValue = ExpressionIniUtilZZZ.isExpressionDefaultAny(sTemp);
				assertTrue(bValue);
			}
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
