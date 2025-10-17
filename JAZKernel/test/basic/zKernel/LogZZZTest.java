package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
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
			assertFalse(objLogTest.getFlag("init")==true); //Nun w�re init falsch
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	public void testGetPathDetailAll(){		
		try {
			assertEquals("c:\\fglKernel\\KernelLog", objLogTest.getDirectory());
			assertEquals("ZKernelLog_test.txt", objLogTest.getFilename());	
			
		
			//20080106 Ein Log kann auch speziell auf Programmebene definiert werden
			KernelContextZZZ objContext = new KernelContextZZZ(this.getClass());
			KernelZZZ objKernelContext =new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini", objContext, (String)null);
			
			LogZZZ objLogContext = objKernelContext.getLogObject();
			assertEquals("c:\\fglKernel\\KernelLog", objLogContext.getDirectory());
			assertEquals("ZKernelLog_LogZZZtest.txt", objLogContext.getFilename());	
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	
	public void testWriteLineDate(){
		try {
			assertTrue(objLogTest.WriteLineDate(strTEST_ENTRY_DEFAULT));
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPosition(){
		try {
			assertTrue(objLogTest.WriteLineDateWithPosition(this, strTEST_ENTRY_DEFAULT));
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}
	}
	
	
	
	
}
