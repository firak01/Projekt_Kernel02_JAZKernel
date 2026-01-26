package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zKernel.KernelContextZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import junit.framework.TestCase;

public class LogZZZTest extends TestCase{
	private KernelZZZ objKernelTest;
	private LogZZZ objLogTest;
	
	private static final String strTEST_ENTRY_DEFAULT = new String("bla");
	
	
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
	
	public void testComputeLine() {
		try {
			String sLog1=null; String sLog2=null;
			String sValue=null; String sMid=null;
			String sValueExpectedStart=null; String sValueExpectedMid=null; String sValueExpectedEnd=null;
			boolean bStartsWith=false; boolean bMidsWith=false; boolean bEndsWith=false;
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTXXX";
			sValue = objLogTest.computeLine(sLog1);
			System.out.println("LogZZZTest.testComputeLine(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T]";
			sValueExpectedMid = ("[A00/]" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "").trim();
			sValueExpectedEnd = "[A01]" + sLog1 + "[/A01]" + "[A00/]" +ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT;
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			sMid = sMid.trim();
			bMidsWith = sMid.equals(sValueExpectedMid);
			assertTrue(bMidsWith);
			
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			
			sValue = objLogTest.computeLine(sLog1, sLog2);
			System.out.println("LogZZZTest.testComputeLine(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T]";
			sValueExpectedMid = "[A00/]" + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "[A01]XXXTESTXXX[/A01]";
			sValueExpectedEnd = "[A00/]" + ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT + "[A01]ZZZTESTZZZ[/A01]";
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			bMidsWith = sMid.equals(sValueExpectedMid);
			assertTrue(bMidsWith);
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
	
	
	public void testWriteLineDate(){
		try {
			assertTrue(objLogTest.WriteLineDate(strTEST_ENTRY_DEFAULT));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPosition(){
		try {
			//Verwende intern das Format STRING_BY_XML
			assertTrue(objLogTest.WriteLineDateWithPosition(this, strTEST_ENTRY_DEFAULT));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPositionXml(){
		try {
			boolean bDummy = objLogTest.WriteLineDateWithPositionXml(this, "");
			assertTrue(bDummy);
			
			//Verwende intern das Format XML_BY_XML
			boolean bValue = objLogTest.WriteLineDateWithPositionXml(this, strTEST_ENTRY_DEFAULT); 
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
			bValue=objLog01.WriteLineDateWithPosition(this, strTEST_ENTRY_DEFAULT);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
}
