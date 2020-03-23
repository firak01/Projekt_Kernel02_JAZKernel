package basic.zBasic.util.datatype.integer;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;

public class IntegerZZZTest extends TestCase implements IConstantZZZ {
//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufr�umen um tearDown().
	

	protected void setUp(){
//		try{
//			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
		
		    			
	}//END setup
	 
	public void testParseIntFromRight(){
		//try{
			String sTest = new String("---1");
			Integer intValue = IntegerZZZ.parseIntFromRight(sTest);			 
			assertEquals(intValue.intValue(), -1); 
			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	 }
	
	public void testParseAbsolutFromRight(){
		//try{
			String sTest = new String("---1");
			Integer intValue = IntegerZZZ.parseAbsolutFromRight(sTest);			 
			assertEquals(intValue.intValue(), 1); 
			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	 }
	
}