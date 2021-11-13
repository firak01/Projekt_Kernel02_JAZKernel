package basic.zBasic.util.datatype.binary;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.integer.IntegerZZZ;
import junit.framework.TestCase;

public class BinaryTokenizerZZZTest  extends TestCase implements IConstantZZZ {
//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufr�umen um tearDown().
	
	protected void setUp(){
//		try{
//			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
		
		    			
	}//END setup
	 
	public void testcreateBinaryTokensRemainder() {
		//try{
			int iValue01 = 7;
			int[] iaValue01 = BinaryTokenizerZZZ.createBinaryTokensRemainder(iValue01);
			assertNotNull(iaValue01);
			assertEquals(iaValue01.length, 3);
			for(int iCount=0;iCount<iaValue01.length;iCount++) {
				int itemp = iaValue01[iCount];
				assertTrue(itemp==1);
			}
			
			//++++++++++++++++++++++++++++++++++
			int iValue02 = 5;
			int[] iaValue02 = BinaryTokenizerZZZ.createBinaryTokensRemainder(iValue02);
			assertNotNull(iaValue02);
			assertEquals(iaValue02.length, 3);
			for(int iCount=0;iCount<iaValue02.length;iCount++) {
				int itemp = iaValue02[iCount];
				if(iCount==1) {
					assertTrue(itemp==0);
				}else {
					assertTrue(itemp==1);
				}
			}
			
			int iValue03 = 425;
			int[] iaValue03 = BinaryTokenizerZZZ.createBinaryTokensRemainder(iValue03);
			assertNotNull(iaValue03);
			assertEquals(iaValue03.length, 9);
			for(int iCount=0;iCount<iaValue03.length;iCount++) {
				
				//nur Stichproben
				if(iCount==0) {
					int itemp = iaValue03[iCount];
					assertTrue(itemp==1);
				}else if(iCount==iaValue03.length-1) {
					int itemp = iaValue03[iCount];
					assertTrue(itemp==1);
				}
				
			}
			
			
	//		}catch(ExceptionZZZ ez){
	//		fail("Method throws an exception." + ez.getMessageLast());
	//	}
	}
	
	public void createBinaryTokens(){
		//try{
		int iValue01 = 7;
		boolean[] baValue01 = BinaryTokenizerZZZ.createBinaryTokens(iValue01);
		assertNotNull(baValue01);
		assertEquals(baValue01.length, 3);
		for(int iCount=0;iCount<baValue01.length;iCount++) {
			boolean btemp = baValue01[iCount];
			assertTrue(btemp);
		}
		
		//++++++++++++++++++++++++++++++++++
		int iValue02 = 5;
		boolean[] baValue02 = BinaryTokenizerZZZ.createBinaryTokens(iValue02);
		assertNotNull(baValue02);
		assertEquals(baValue02.length, 3);
		for(int iCount=0;iCount<baValue02.length;iCount++) {
			boolean btemp = baValue02[iCount];
			if(iCount==1) {
				assertFalse(btemp);
			}else {
				assertTrue(btemp);
			}
		}
		
		int iValue03 = 425;
		boolean[] baValue03 = BinaryTokenizerZZZ.createBinaryTokens(iValue03);
		assertNotNull(baValue03);
		assertEquals(baValue03.length, 9);
		for(int iCount=0;iCount<baValue03.length;iCount++) {
			
			//nur Stichproben, erste und letzte Zahl
			if(iCount==0) {
				boolean btemp = baValue03[iCount];
				assertTrue(btemp);
			}else if(iCount==baValue03.length-1) {
				boolean btemp = baValue03[iCount];
				assertTrue(btemp);
			}
			
		}
		
		
//		}catch(ExceptionZZZ ez){
//		fail("Method throws an exception." + ez.getMessageLast());
//	}
	 }

}
