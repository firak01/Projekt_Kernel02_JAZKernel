package basic.zBasic.util.string.formater.testV00;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerJustifiedZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ.FLAGZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import junit.framework.TestCase;

public class StringFormatManagerZZZ00Test extends TestCase {
	private StringFormatManagerZZZ objLogManagerTest = null;
	
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
			IStringFormatManagerJustifiedZZZ objLogManager = StringFormatManagerZZZ.getInstance();
			boolean btemp1a = objLogManager.setFlag(IStringFormatManagerZZZ.FLAGZ.DUMMY, true);
			assertTrue(btemp1a);
						
			StringFormatManagerZZZ.destroyInstance();
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Nun mal eine neue Version holen. Das Flag sollte fehlen.
			IStringFormatManagerZZZ objLogManager2 = StringFormatManagerZZZ.getNewInstance();
			
			boolean btemp2a = objLogManager2.getFlag(IStringFormatManagerZZZ.FLAGZ.DUMMY);
			assertFalse(btemp2a);
			
			
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
			sClassName = objDummy.getClass().getSimpleName();

			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, sLog);
			assertNotNull(sLogValue);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);
			
			assertTrue(StringZZZ.contains(sLogValue, sLog));
			assertTrue(StringZZZ.count(sLogValue, sLog)==1);
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
						
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iLogIndex1+sLog.length()> iThreadIndex);
			
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
			sClassName = objDummy.getClass().getSimpleName();
			
			//+++ Bei 1x Strintype soll der Logeintrag nur 1x erscheinen.					
			IEnumSetMappedStringFormatZZZ[] ienumaFormat01= {
							IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
							IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,																						
							};
			
			StringFormatManagerZZZ.getInstance().resetStringIndexRead();
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat01, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der 1x Logeintrag auch nur 1x in der Zeile erscheinen.	
			//a) identischer Fall
			IEnumSetMappedStringFormatZZZ[] ienumaFormat02= {
							IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
							IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
							};
						
			StringFormatManagerZZZ.getInstance().resetStringIndexRead();
			sLogValue = StringFormatManagerZZZ.getInstance().compute(objDummy, ienumaFormat02, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der identische 2x Logeintrag auch 2x in der Zeile erscheinen. Wenn entsprechend uebergeben	
			//b) gleicher Logeintraege	
			StringFormatManagerZZZ.getInstance().resetStringIndexRead();
			sLogValue = StringFormatManagerZZZ.getInstance().compute(objDummy, ienumaFormat02, sLog1, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==2); //!! nun 2x, trotz zweitem STRINGTYPE, aber LINENEXT existiert nicht, bzw. ist nachfolgend
										
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
			StringFormatManagerZZZ.getInstance().resetStringIndexRead();
			sLogValue = StringFormatManagerZZZ.getInstance().compute(objDummy, ienumaFormat02, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
	
	
	public void testCompute_FormatDefined_Using_LINENEXT_log_hinten(){
		try{			
			String sLog1 = null; String sLog2 = null; String sLog3 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iLogIndex3 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag etwas laenger";
			sLog2 = "der zweite Logeintrag";
			sLog3 = "der dritte Logeintrag soll noch laenger sein, trotzdem alle buendig?";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormat03= {
					IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
					};
			
			
			//+++ Bei 2x Strintype soll der Logeintrag nur 1x erscheinen auch mit ARGNEXT.								
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
			//+++ Bei 2x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//b) unterschiedliche Logeintraege							
			
			//TODOGOON: MIT ARGNEXT soll der 2te Logeintrag auf eine andere Zeile Kommen
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex3 = StringZZZ.indexOfLast(sLogValue, sLog3);
			assertTrue(iLogIndex3 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 3x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//c) ARRAY der Logeintraege									
			String[]saLog = new String[3];
			saLog[0]=sLog1;
			saLog[1]=sLog2;
			saLog[2]=sLog3;
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, saLog);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
	
	
	public void testCompute_FormatDefined_Using_LINENEXT_log_voranstehend(){
		try{			
			String sLog1 = null; String sLog2 = null; String sLog3 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iLogIndex3 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag etwas laenger";
			sLog2 = "der zweite Logeintrag";
			sLog3 = "der dritte Logeintrag soll noch laenger sein, trotzdem alle buendig?";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormat03= {
					IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					};
			
			
			//+++ Bei 2x Strintype soll der Logeintrag nur 1x erscheinen auch mit ARGNEXT.								
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht ersetzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//b) unterschiedliche Logeintraege							
			
			//TODOGOON: MIT ARGNEXT soll der 2te Logeintrag auf eine andere Zeile Kommen
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex3 = StringZZZ.indexOfLast(sLogValue, sLog3);
			assertTrue(iLogIndex3 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 3x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//c) ARRAY der Logeintraege			
			String[]saLog = new String[3];
			saLog[0]=sLog1;
			saLog[1]=sLog2;
			saLog[2]=sLog3;
			sLogValue = StringFormatManagerZZZ.getNewInstance().compute(objDummy, ienumaFormat03, saLog);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
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
	
	public void testCompute_FormatDefined_JustifierSorting(){
		try{			
			String sLog1 = null; String sLog2 = null; String sLog3 = null; String sLog4 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iLogIndex3 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag etwas laenger";
			sLog2 = "der zweite Logeintrag kuerzer";
			sLog3 = "der dritte Logeintrag soll noch laenger sein, trotzdem alle buendig?";
			sLog4 = "der vierte Logeintrag, kuerzer + Thread, trotzdem alle buendig?";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormat04= {
					IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR02_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR02_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR02_STRING,    //bewirkt, dass auch die Thread-Spalte immer buendig ist
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR02_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
					};
			
			
			//+++ Merke: Im korrekten Einsatz wird die Liste der Formatanweisungen erst mit CONTROL_LINENEXT_ aufgeteilt
			//           Jede Logzeile wird dann unabhängig voneinenader Formatiert.
			//           Der Justifier ist ein Singleton, so dass die Position der Spaltentrennermarke erhalten bleiben würde,
			//           ohne immer explizit eine neue Instanz zu holen.
			ArrayListZZZ<IStringJustifierZZZ> listaJustifierFiltered04 = StringJustifierManagerZZZ.getNewInstance().getStringJustifierListFiltered(ienumaFormat04);
			assertNotNull(listaJustifierFiltered04);
			assertTrue(listaJustifierFiltered04.size()==2);
			
			IStringJustifierZZZ objJustifierFiltered04_0 = listaJustifierFiltered04.get(0);
			String sJustifierFilteredSeparator04_0 = objJustifierFiltered04_0.getPositionSeparator();
			assertEquals(IStringFormatZZZ.sSEPARATOR_03_DEFAULT, sJustifierFilteredSeparator04_0);
		
			IStringJustifierZZZ objJustifierFiltered04_1 = listaJustifierFiltered04.get(1);
			String sJustifierFilteredSeparator04_1 = objJustifierFiltered04_1.getPositionSeparator();
			assertEquals(IStringFormatZZZ.sSEPARATOR_02_DEFAULT, sJustifierFilteredSeparator04_1);
			
			
			//+++ Nicht unmittelbar Bestandteil des Tests, aber rechne trotzdem mal aus
			//Merke: Weil es eine Position gibt in der letzten Zeile noch die ThreadID auszugeben, kommt eine nahezu leere Zeile
			//       in den Beispielen häufig zustande.
			System.out.println("++++++++++++");
			sLogValue = StringFormatManagerZZZ.getNewInstance().computeJustified(objDummy, ienumaFormat04, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			System.out.println("++++++++++++");
			sLogValue = StringFormatManagerZZZ.getNewInstance().computeJustified(objDummy, ienumaFormat04, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			System.out.println("++++++++++++");
			sLogValue = StringFormatManagerZZZ.getNewInstance().computeJustified(objDummy, ienumaFormat04, sLog1, sLog2, sLog3);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);
						
			System.out.println("++++++++++++");
			sLogValue = StringFormatManagerZZZ.getNewInstance().computeJustified(objDummy, ienumaFormat04, sLog1, sLog2, sLog3, sLog4);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
}
