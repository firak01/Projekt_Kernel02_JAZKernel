package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import junit.framework.TestCase;

public class KernelUseObjectZZZTest extends TestCase {
	private KernelZZZ objKernelFGL;
	private KernelUseObjectZZZ objKernelUserTest;
	protected void setUp() throws Exception {
		try {			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelFGL = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
			
			//Das eigentliche Testobjekt
			objKernelUserTest = new KernelUseObjectZZZ(objKernelFGL);
	
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
	* Lindhauer; 20.04.2006 09:05:11
	 */
	public void testFlagHandling(){
		//try{			
			//Testen, ob das Setzen der Flags möglich ist.
			assertTrue("Setting the Flag 'DEBUG' failed.", objKernelUserTest.setFlag("debug", true));			
			assertTrue("Receiving the previously set Flag 'DEBUG' failed.", objKernelUserTest.getFlag("debug"));
			
			//Testen, ob das Setzen der Flags möglich ist.
			assertTrue("Setting the Flag 'DEBUG' failed.", objKernelUserTest.setFlag("debug", false));			
			assertFalse("Receiving the previously set Flag 'DEBUG' failed.", objKernelUserTest.getFlag("debug"));
			
//		}catch(ExceptionZZZ ez){
//			fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
//		}
	}

}
