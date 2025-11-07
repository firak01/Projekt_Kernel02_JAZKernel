package basic.zBasic.util.log;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import junit.framework.TestCase;

public class LogStringZZZTest  extends TestCase{
	
	

	protected void setUp(){
//		try {			
//			
//					
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup

	public void testContructor() {
		try{
			//Das soll ein Singleton sein. Einmal definiert, ueberall verfuegbar.
			LogStringZZZ objLogStringj = LogStringZZZ.getInstance();
			boolean btemp1a = objLogStringj.setFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD, true);
			assertTrue(btemp1a);
			
			boolean btemp1b = objLogStringj.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD);
			assertTrue(btemp1b);
						
			LogStringZZZ.destroyInstance();
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Nun mal eine neue Version holen. Das Flag sollte fehlen.
			LogStringZZZ objLogStringj2 = LogStringZZZ.getInstance();
			
			boolean btemp2a = objLogStringj2.getFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD);
			assertFalse(btemp2a);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testComputeN() {
		try {
			//20240423:
			//MErke: Diese Primfaktorenzerlegung, etc. wird noch nirgendwo verwendet
			LogStringZZZ objLogString = LogStringZZZ.getNewInstance();			
			int iNumber = objLogString.computeFormatPositionsNumber();
			assertTrue(iNumber>0);//Verwende die custom-FormatPosition

			int[]iatest = PrimeFactorizationZZZ.primeFactorsAsIntArray(iNumber);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": '" + IntegerArrayZZZ.implode(iatest) + "'");
			
			int iValue = PrimeNumberZZZ.computePositionValueFromPrime(iNumber, 3);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + iValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testCompute_FormatDefault() {
		try{
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			String sLog = LogStringZZZ.getNewInstance().compute(objDummy, "der erste Logeintrag");
			assertNotNull(sLog);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + sLog);
			
			assertTrue(StringZZZ.contains(sLog, "der erste Logeintrag"));
			assertTrue(StringZZZ.count(sLog, "der erste Logeintrag")==1);
										
			assertTrue(StringZZZ.count(sLog,"[Thread:")==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLog,objDummy.getClass().getSimpleName()+":")==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
						
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_FormatDefined(){
		try{
						
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,				
							ILogStringZZZ.LOGSTRING.STRINGTYPE02,												
							};
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat);
			
			String sLog = LogStringZZZ.getInstance().compute(objDummy, "der erste Logeintrag");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLog);			
			
			assertTrue(StringZZZ.contains(sLog, "der erste Logeintrag"));
			assertTrue(StringZZZ.count(sLog, "der erste Logeintrag")==1);
										
			assertTrue(StringZZZ.count(sLog,"[Thread:")==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLog,objDummy.getClass().getSimpleName()+":")==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
}//end class
