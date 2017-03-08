package basic.zBasic.util.datatype.integer;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;

public class IntegerArrayZZZTest extends TestCase implements IConstantZZZ {
//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufr�umen um tearDown().
	
//	Objekt, das getestet werden soll
	private IntegerArrayZZZ objArrayTest;
	private IntegerArrayZZZ objArraySorted;

	protected void setUp(){
		try{
			Integer[] iaTest = {new Integer(3), new Integer(4), new Integer(1)};
			objArrayTest = new IntegerArrayZZZ(iaTest);
			
			
			Integer[] iaSorted ={new Integer(1), new Integer(2), new Integer(3)};
			objArraySorted = new IntegerArrayZZZ(iaSorted);

		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		    			
	}//END setup
	 
	public void testPlus(){
		try{
			//Die statische Methode testen.
			Integer[] iaTest = {new Integer(3), new Integer(4), new Integer(1)};
			Integer[] iaResult = IntegerArrayZZZ.plus(iaTest, 1);
			 
			assertEquals(iaResult[0].intValue(), 4); 
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	
	
}