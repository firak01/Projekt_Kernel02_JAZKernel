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
			String sLog = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog = "der erste Logeintrag";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName()+":";
			
			
			/*  MERKE: DEFAULT FORMATIERUNG IST MOMENTAN:
			    ILogStringZZZ.LOGSTRING.DATE,
				ILogStringZZZ.LOGSTRING.THREADID,
				ILogStringZZZ.LOGSTRING.CLASSNAMESIMPLE,
				ILogStringZZZ.LOGSTRING.CLASSMETHOD,
				ILogStringZZZ.LOGSTRING.STRINGTYPE01,					
				ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION,
			 */
			sLogValue = LogStringZZZ.getNewInstance().compute(objDummy, sLog);
			assertNotNull(sLogValue);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + sLogValue);
			
			assertTrue(StringZZZ.contains(sLogValue, sLog));
			assertTrue(StringZZZ.count(sLogValue, sLog)==1);
										
			assertTrue(StringZZZ.count(sLog,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLog,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
						
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex = StringZZZ.indexOfFirst(sLogValue, sLog);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex+sLog.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex > iClassNameIndex+sClassName.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_FormatDefined(){
		try{
			String sLog = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog = "der erste Logeintrag";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName()+":";
			
			//+++ Bei 1x Strintype soll der Logeintrag nur 1x erscheinen.					
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat01= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,																						
							};
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat01);
		
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog));
			assertTrue(StringZZZ.count(sLogValue, sLog)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex = StringZZZ.indexOfFirst(sLogValue, sLog);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex+sLog.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex > iClassNameIndex+sClassName.length());
			
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag 2x erscheinen.		
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat02= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,	
							ILogStringZZZ.LOGSTRING.STRINGTYPE02,
							};
			
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat01);
						
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog));
			assertTrue(StringZZZ.count(sLogValue, sLog)==2); //!! nun 2x
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex = StringZZZ.indexOfFirst(sLogValue, sLog);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex+sLog.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag soll vor dem Thread stehen
			iLogIndex = StringZZZ.indexOfLast(sLogValue, sLog);
			assertTrue(iLogIndex > iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
	
	public void testCompute_FormatDefined_LogStringArray(){
		try {
			String sLog1 = null; String sLog2 = null;
			String sLogValue = null; String sThread = null; String sClassName = null;
			int iLogIndex = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName()+":";
			
			//+++ Bei 1x Strintype soll der Logeintrag nur 1x erscheinen.					
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat01= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,																						
							};
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat01);
		
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex > iClassNameIndex+sClassName.length());
			
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag 2x erscheinen.		
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat02= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,	
							ILogStringZZZ.LOGSTRING.STRINGTYPE02,
							};
			
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat01);
						
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==2); //!! nun 2x
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag soll vor dem Thread stehen
			iLogIndex = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertTrue(iLogIndex > iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
}//end class
