package basic.zBasic.util.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zBasic.util.file.txt.TxtWriterZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;

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
			LogStringZZZ obLogStringj = LogStringZZZ.getInstance();
			boolean btemp = obLogStringj.setFlag(ILogStringZZZ.FLAGZ.EXCLUDE_THREAD, true);
			assertTrue(btemp);
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testComupteN() {
		try {
			//20240423:
			//MErke: Diese Primfaktorenzerlegung, etc. wird noch niergendwo verwendet
			LogStringZZZ objLogString = LogStringZZZ.getInstance();			
			int iNumber = objLogString.computeFormatPositionsNumber();
			assertTrue(iNumber>0);//Verwende die custom-FormatPosition

			int[]iatest = PrimeFactorizationZZZ.primeFactorsAsIntArray(iNumber);
			System.out.println(iatest);
			
			int iValue = PrimeNumberZZZ.computePositionValueFromPrime(iNumber, 3);
			System.out.println(iValue);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testCompute_FormatDefault() {
		try{
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			String sLog = LogStringZZZ.getInstance().compute(objDummy, "der erste Logeintrag");
			assertNotNull(sLog);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + sLog);
			
			assertTrue(StringZZZ.contains(sLog, "der erste Logeintrag"));
			assertTrue(StringZZZ.count(sLog, "der erste Logeintrag")==1);
										
			assertTrue(StringZZZ.count(sLog,"[Thread:")==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLog,"(")==0);
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_FormatDefined(){
		try{
						
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			IEnumSetMappedLogStringZZZ[] ienumaFormat= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.ARGNEXT01,
							ILogStringZZZ.LOGSTRING.THREADID,				
							ILogStringZZZ.LOGSTRING.ARGNEXT02,												
							};
			LogStringZZZ.getInstance().setFormatPositionsMapped(ienumaFormat);
			
			String sLog = LogStringZZZ.getInstance().compute(objDummy, "der erste Logeintrag");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLog);			
			
			assertTrue(StringZZZ.contains(sLog, "der erste Logeintrag"));
			assertTrue(StringZZZ.count(sLog, "der erste Logeintrag")==1);
										
			assertTrue(StringZZZ.count(sLog,"THREAD")==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLog,"(")==1);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
}//end class
