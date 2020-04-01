package basic.zUtil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.DummyObjectZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ;
import junit.framework.TestCase;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;

public class FileExpansionZZZTest extends TestCase {	
	private FileExpansionZZZ objExpansionTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglkernel\\kerneltest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.txt");
	

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
			String sFilePathUsed = null;		
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathUsed = strFILE_DIRECTORY_DEFAULT;
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    
			    sFilePathUsed = sPathEclipse + File.separator + "test";		   
			}
			
			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, strFILE_NAME_DEFAULT );			
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.close();

			//The main object used for testing
			FileZZZ objFileTest = new FileZZZ(sFilePathUsed, strFILE_NAME_DEFAULT);
			objExpansionTest = new FileExpansionZZZ(objFileTest);
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagZ(){
//		try{
		boolean  bExists = objExpansionTest.proofFlagZExists("NIXDA");
		assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
		boolean bSetted = false;
			try{
				bSetted = objExpansionTest.setFlagZ("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			
			//TestKonfiguration prüfen
			assertTrue(objExpansionTest.getFlag("init")==false); //Nun wäre init falsch		
			
			//An object just initialized
			FileExpansionZZZ objFileExpansionInit = new FileExpansionZZZ();
			assertTrue(objFileExpansionInit.getFlag("init")==true);
			
			//Flags aus der FileZZZ-Klasse
			assertTrue(objExpansionTest.getFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name())==false);
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
		
	}
		
	/** void, Tests: Validating the result of computing a expansion. 
	 * a) Computing the biggest expansion number which could be created, by a given number of digits 
	* Lindhauer; 21.04.2006 09:51:08
	 */
	public void testExpansionLookalike(){
//		try {
		//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
		//Zu beachten ist, das die Funktion einen String zur�ckliefert.
		
		assertEquals("99",FileExpansionZZZ.getExpansionMax(2));		
		assertEquals("999",FileExpansionZZZ.getExpansionMax(3));
		assertEquals("9999",FileExpansionZZZ.getExpansionMax(4));
		assertEquals("99999",FileExpansionZZZ.getExpansionMax(5));
		
		//Hier wird eine F�llvariable zur Berechnung verwendet.
		//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
		objExpansionTest.setExpansionLength(0);
		assertEquals("",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(1);
		assertEquals("2",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(3);
		assertEquals("002",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(4);
		assertEquals("0002",objExpansionTest.computeExpansion("0",2));
		
		//Falls ein anderes F�llzeichen �bergeben werden soll
		// Hier 3 Unterstriche vor der Ziffer
		assertEquals("___2",objExpansionTest.computeExpansion("_",2));
		
		// Hier 2 Unterstriche und die Zahl ist 2 stellig
		assertEquals("__32",objExpansionTest.computeExpansion("_",32));
		
//		 Hier kein Unterstrich und die Zahl ist 4 stellig
		assertEquals("4321",objExpansionTest.computeExpansion("_",4321));
		
		
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}
	}
	
	public void testIterateExpansionAll(){
//		try {
		int iCounter=0;
		objExpansionTest.setExpansionFilling('0');
		objExpansionTest.setExpansionLength(3);
		Iterator<String> itExpansion = objExpansionTest.iterator();		
		while(itExpansion.hasNext()) {
			String sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "':");
			if(iCounter%10==0)System.out.println();
			iCounter++;
		}
				
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}
	}
	
	
}
