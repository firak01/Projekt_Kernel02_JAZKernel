package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zKernel.KernelContextZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import junit.framework.TestCase;

public class LogZZZTest extends TestCase{
	private KernelZZZ objKernelTest;
	private LogZZZ objLogTest;
	
	private static final String strTEST_ENTRY01_DEFAULT = new String("bla");
	private static final String strTEST_ENTRY02_DEFAULT = new String("blub");
	
	
	protected void setUp(){
		try {
			//objKernelTest = new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",(String)null);
			objKernelTest = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			/* damit wird logObject null und der test darf nicht weitergehen
			String[] a = {"init"};
			objKernelTest = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",a);		
			*/
			
			objLogTest = objKernelTest.getLogObject();
			
			//TestKonfiguration pruefen
			assertNotNull("LogObject provided by KernelObject is null.", objLogTest);
			assertFalse(objLogTest.getFlag("init")==true); //Nun waere init falsch
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testComputeLine_CUSTOM() {
		try {
			int itemp;
			String sLog1=null; String sLog2=null;
			String sValue=null; String sMid1=null; String sMid2=null;
			String sValueExpectedStart=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bValue=false;
			
			boolean bStartsWith=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
			
			//##############################################
			//Hier die verschiedenen Custom-Formate durchspielen. 
			//Dann muss aber jedes Mal die Ueberpruefung des Ergebnisses eine andere sein. (anfang, mitte, ende).
			//Ueberlass das dem DEFAULT-Test
			//
			//wichtig ist hier nur, das jeder Log-Text 1x erscheint.
			//egal was man konfiguriert: CONTROL_SEPARATORMESSAGE_STRING ist nur 1x im Ergebnis.
			//                           Andere Separatoren können häufiger im Ergebnis sein.
			//###############################################
			
			//Das CUSTOM-Format			
			IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
					 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
			 };
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";
			sValue = objLogTest.computeLine(iaFormat, sLog1);
			System.out.println("LogZZZTest.testComputeLine_CUSTOM(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			
			sValue = objLogTest.computeLine(iaFormat, sLog2, sLog1); //auch wenn log2 kuerzer als log1 ist, erwarte ich dass die Ausgabe buendig ist 
			System.out.println("LogZZZTest.testComputeLine_CUSTOM(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog2 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testComputeLine_DEFAULT() {
		try {
			String sLog1=null; String sLog2=null;
			String sValue=null; String sMid1=null; String sMid2=null;
			String sValueExpectedStart=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bStartsWith=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";
			sValue = objLogTest.computeLine(sLog1);
			System.out.println("LogZZZTest.testComputeLine_DEFAULT(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T]";
			sValueExpectedMid1 = ("[A00/]" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "").trim();
			sValueExpectedMid2 = ("[A01]" + sLog1 + "[/A01]" + "[A00/]").trim();
			sValueExpectedEnd = ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid1 = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedMid2);
			sMid1 = sMid1.trim();
			bMid1found = sMid1.equals(sValueExpectedMid1);
			assertTrue(bMid1found);
			
			sMid2 = StringZZZ.mid(sValue, sValueExpectedMid1, sValueExpectedEnd);
			sMid2 = sMid2.trim();
			bMid2found = sMid2.equals(sValueExpectedMid2);
			assertTrue(bMid2found);
			
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			
			sValue = objLogTest.computeLine(sLog1, sLog2);
			System.out.println("LogZZZTest.testComputeLine_DEFAULT(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T]";
			sValueExpectedMid1 = ("[A00/]" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "[A01]"+ sLog1 +"[/A01]").trim();
			sValueExpectedMid2 = ("[A00/]" + ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT).trim();
			sValueExpectedEnd = "[A01]"+ sLog2 +"[/A01]";
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid1 = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedMid2);
			sMid1 = sMid1.trim();
			bMid1found = sMid1.equals(sValueExpectedMid1);
			assertTrue(bMid1found);
			
			sMid2 = StringZZZ.mid(sValue, sValueExpectedMid1, sValueExpectedEnd);
			sMid2 = sMid2.trim();
			bMid2found = sMid2.equals(sValueExpectedMid2);
			assertTrue(bMid2found);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGetPathDetailAll(){		
		try {
			assertEquals("c:\\fglKernel\\kernellog", objLogTest.getDirectory());
			assertEquals("ZKernelLog_test.txt", objLogTest.getFilename());	
			
		
			//20080106 Ein Log kann auch speziell auf Programmebene definiert werden
			KernelContextZZZ objContext = new KernelContextZZZ(this.getClass());
			KernelZZZ objKernelContext =new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini", objContext, (String)null);
			
			LogZZZ objLogContext = objKernelContext.getLogObject();
			assertEquals("c:\\fglKernel\\kernellog", objLogContext.getDirectory());
			assertEquals("ZKernelLog_LogZZZtest.txt", objLogContext.getFilename());	
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	public void testcomputeLineDate() {
		try {
			boolean btemp; int itemp;
			String sValue; boolean bValue;
			String sLog1; String sLog2;
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			sLog1 = strTEST_ENTRY01_DEFAULT;
			sLog2 = strTEST_ENTRY02_DEFAULT;
			
			
			sValue = objLogTest.computeLineDate(this);
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			
			//0x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==0);
			assertTrue("Keinen LogString '" + sLog1 + "' erwartet", bValue);
			
			//0x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==0);
			assertTrue("Keinen Kommentarseparator erwartet '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			
			//################################################
			sValue = objLogTest.computeLineDate(sLog1); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
			//################################################
			sValue = objLogTest.computeLineDate(sLog1, sLog2); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur jeweils 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString1 '" + sLog1 + "' erwartet", bValue);
			
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString2 '" + sLog2 + "' erwartet", bValue);
			
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testcomputeLineDateWithPosition() {
		try {
			boolean btemp; int itemp;
			String sValue; boolean bValue;
			String sLog1;String sLog2;
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			sLog1 = strTEST_ENTRY01_DEFAULT;
			sLog2 = strTEST_ENTRY02_DEFAULT;
			
			sValue = objLogTest.computeLineDateWithPosition(this);
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//0x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==0);
			assertTrue("Keinen LogString '" + sLog1 + "' erwartet", bValue);
			
			//0x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==0);
			assertTrue("Keinen Kommentarseparator erwartet '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			
			//########################################
			sValue = objLogTest.computeLineDateWithPosition(sLog1); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//#########################################
			
			sValue = objLogTest.computeLineDateWithPosition(sLog1, sLog2); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den jeweiligen LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString1 '" + sLog1 + "' erwartet", bValue);
			
			itemp = StringZZZ.count(sValue, sLog2);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString2 '" + sLog2 + "' erwartet", bValue);
						
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testWriteLineDate(){
		try {
			assertTrue(objLogTest.WriteLineDate(strTEST_ENTRY01_DEFAULT));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPosition(){
		try {
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			
			
			//Verwende intern das Format STRING_BY_XML
			assertTrue(objLogTest.WriteLineDateWithPosition(this, strTEST_ENTRY01_DEFAULT));
			
			//bValue=objLog01.WriteLineDateWithPosition(this, strTEST_ENTRY_DEFAULT);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPositionXml(){
		try {
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			
			
			boolean bDummy = objLogTest.WriteLineDateWithPositionXml(this, "");
			assertTrue(bDummy);
			
			//Verwende intern das Format XML_BY_XML
			boolean bValue = objLogTest.WriteLineDateWithPositionXml(this, strTEST_ENTRY01_DEFAULT); 
			assertTrue(bValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testConstructorWithDifferentLogFile() {
		try {
			String[]saArg=null; boolean bValue=false;
					
			saArg=new String[4];
			saArg[0]="-ld";
			saArg[1]="c:\\fglkernel\\kernellog";
			saArg[2]="-lf";
			saArg[3]="testLog_Constructor01.txt";
			IKernelConfigZZZ objConfig = new ConfigZZZ(saArg);
			LogZZZ objLog01 = new LogZZZ(objConfig);
			bValue=objLog01.WriteLineDateWithPosition(this, strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
}
