package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class KernelZZZIsolatedTest extends TestCase {
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgef�hrt werden.
	 */
	
	private KernelZZZ objKernelFGL;
	private KernelZZZ objKernelTest;
	
	
	protected void setUp(){
		try {			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelFGL = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelTest = new KernelZZZ("TEST", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
	
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	private void removeLogFile(KernelZZZ objKerneltemp){
		//		Log-File entfernen
		if(objKerneltemp != null){
			LogZZZ objLog = objKerneltemp.getLogObject();
			if(objLog != null){
				FileZZZ objFile = objLog.getFileObject();
				if(objFile!=null){
					objFile.deleteOnExit();
				}
			}
		}
	}
	


/** Kopiere hier einzelne Testausdrücke rein, um nicht alle Tests debuggen zu müssen
 * 
 * @author Fritz Lindhauer, 13.07.2019, 08:38:03
 */
public void testParameterByProgramAlias(){
//Merke: Mache folgendes, damit kein Unterschied vom Isolated zum normalen Test besteht.
String sClassname = this.getClass().getName(); 
sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	
			
}


public void testGetModuleAliasAll(){
	
}

public void testConstructorConfigObject(){
	try{
		String[] saArg2 ={"-s","01" ,"-d","test", "-f", "ZKernelConfigKernel_test.ini"};
		ConfigZZZ objConfig = new ConfigZZZ(saArg2);
		assertTrue(objConfig.isOptionObjectLoaded());
		KernelZZZ objKerneltemp  = new KernelZZZ(objConfig, (String[]) null);
		try{					
			//Der Default - Applikation - key (des Config-Objekts)  sollte hier stehen, weil es wurde kein anderer �bergeben.
			assertEquals(objKerneltemp.getApplicationKey(), objConfig.getApplicationKeyDefault());
			assertFalse(objKerneltemp.getApplicationKey().equals(""));
		}catch(ExceptionZZZ ez){
			//erwarteter Fehler
//			Log-File entfernen
			this.removeLogFile(objKerneltemp);
		}
		
	}catch(Exception e){
		fail("Method throws an exception." + e.getMessage());
	}
}


}//END Class