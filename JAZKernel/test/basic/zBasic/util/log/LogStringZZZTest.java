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
			int iLogIndex1 = -1;  int iLogIndex2 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
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
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_FormatDefined_Using_Single_Double_Array(){
		try{
			String sLog1 = null; String sLog2 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag";
			sLog2 = "der zweite Logeintrag";
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
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag aber nur 1x in der Zeile erscheinen.	
			//a) identischer Fall
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat02= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,	
							ILogStringZZZ.LOGSTRING.STRINGTYPE02,
							};
			
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat02);
						
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//wiederholter (erster) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertEquals(iLogIndex1, iLogIndex2);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der identische Logeintrag auch 2x in der Zeile erscheinen. Wenn entsprechend uebergeben	
			//b) gleicher Logeintraege		
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat02);
			
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==2); //!! nun 2x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//wiederholter (erster) Logeintrag ist vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2 > iLogIndex1 + sLog1.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag auch 2x in der Zeile erscheinen.	
			//c) unterschiedliche Logeintraege			
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat02);
						
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1, sLog2);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x,
			
			assertTrue(StringZZZ.contains(sLogValue, sLog2));
			assertTrue(StringZZZ.count(sLogValue, sLog2)==1); //!! nun 1x,
			
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag 
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2> iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
	
	public void testCompute_FormatDefined_Using_ARGNEXT(){
		try{			
			String sLog1 = null; String sLog2 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag";
			sLog2 = "der zweite Logeintrag";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName()+":";
			
			//+++ Bei 2x Strintype soll der Logeintrag nur 1x erscheinen auch mit ARGNEXT.					
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormat03= {
							ILogStringZZZ.LOGSTRING.CLASSNAME,						
							ILogStringZZZ.LOGSTRING.STRINGTYPE01,
							ILogStringZZZ.LOGSTRING.THREADID,	
							ILogStringZZZ.LOGSTRING.ARGNEXT,
							ILogStringZZZ.LOGSTRING.STRINGTYPE02
							};
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat03);
		
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der identische Logeintrag auch 2x in der Zeile erscheinen. Wenn entsprechend uebergeben	
			//b) gleicher Logeintraege		
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat03);
			
			TODOGOO: MIT ARGNEXT soll der 2te Logeintrag auf eine andere Zeile Kommen
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1, sLog1);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==2); //!! nun 2x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//wiederholter (erster) Logeintrag ist vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2 > iLogIndex1 + sLog1.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag auch 2x in der Zeile erscheinen.	
			//c) unterschiedliche Logeintraege			
			LogStringZZZ.getNewInstance().setFormatPositionsMapped(ienumaFormat03);
						
			sLogValue = LogStringZZZ.getInstance().compute(objDummy, sLog1, sLog2);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Hier erst geht der Logeintrag los...:" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x,
			
			assertTrue(StringZZZ.contains(sLogValue, sLog2));
			assertTrue(StringZZZ.count(sLogValue, sLog2)==1); //!! nun 1x,
			
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag 
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2> iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
}//end class
