package basic.zUtil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ;
import junit.framework.TestCase;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;

public class FileZZZTest extends TestCase {	
	private FileZZZ objFileTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
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
			objFileTest = new FileZZZ(sFilePathTotal, strFILE_NAME_DEFAULT, "use_file_expansion");
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
		try{
		boolean  bExists = objFileTest.proofFlagZExists("NIXDA");
		assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
		boolean bSetted = false;
			try{
				bSetted = objFileTest.setFlagZ("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			
			//TestKonfiguration prüfen
			assertFalse(objFileTest.getFlag("init")==true); //Nun wäre init falsch		
			
			//An object just initialized
			FileZZZ objFileInit = new FileZZZ();
			assertTrue(objFileInit.getFlag("init")==true);
			
			//Flags aus der FileZZZ-Klasse
			assertTrue(objFileTest.getFlag("use_file_expansion")==true);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
	
	/** Test: Join the filepath and a filename undere any circumstances
	 */
	public void testJoinFilePathName(){
		try{				
		//Normal case
		assertEquals("c:\\test\\test.txt", FileEasyZZZ.joinFilePathName("c:\\test", "test.txt"));
				
		//Path has the backslash (or more backslashes) at the end
		assertEquals("c:\\test\\test.txt", FileEasyZZZ.joinFilePathName("c:\\test\\\\", "test.txt"));
		
		//An here with more directories
		assertEquals("c:\\test\\1\\test.txt", FileEasyZZZ.joinFilePathName("c:\\test\\1\\\\", "test.txt"));
		
		
		//Path has to be the first param only (ignore any obsolete backslashes)
		assertEquals("c:\\test", FileEasyZZZ.joinFilePathName("c:\\test\\\\", ""));
		
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	/** Test: Splitting the filename: 
	 * .NameOnly
	 * .NameEnd
	 * 
	 */
	public void testGetPathDetailAll(){
		try{
		assertEquals("JUnitTest", objFileTest.getNameOnly());
		assertEquals("txt", objFileTest.getNameEnd());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}

	/** Test: Computing possible expansions of the filename-ending
	 * 
	 */
	public void testPathNameExpansion(){
try{
		//First, no file expected to be there or only one file is there
		assertEquals("", objFileTest.getExpansionCurrent());
		
		// unverändert ?
		//3stellig
		assertEquals("", objFileTest.getExpansionCurrent());
		assertEquals("", objFileTest.getExpansionNext());
		
		//Erst mit dem Flag, dass kennzeichnet, das angehängt werden soll wird der Name erweitert.
		//Merke: Das passiert dann, wenn die Ausgangsdatei tatsächlich existiert
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), true);
		assertEquals("001", objFileTest.getExpansionCurrent());
		assertEquals("001", objFileTest.getExpansionNext()); //Da es die Datei nicht gibt, bleibt es beim Wert
		
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), true);
		assertEquals("001", objFileTest.getExpansionCurrent()); //Da es die Datei nicht gibt, bleibt es beim Wert
		assertEquals("002", objFileTest.getExpansionNext()); //Da es die Datei nicht gibt, bleibt es beim Wert
		
		
		//4stelling: Merke: Das dauer wg. der Suche der Dateinamen von 9999 bis 0000 lange....
		objFileTest.setExpansionLength(4);
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), false);
		assertEquals("",objFileTest.getExpansionCurrent());
		
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), true);
		assertEquals("0001",objFileTest.getExpansionCurrent());
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), false);
		assertEquals("0001",objFileTest.getExpansionNext()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
		
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), true);
		assertEquals("0002",objFileTest.getExpansionNext()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
		
		
		objFileTest.setExpansionFilling('-');
		assertEquals("-", objFileTest.getExpansionFilling());
		
		//	Now get the next expansion
		objFileTest.setExpansionLength(3);
		objFileTest.getFileExpansionObject().setFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), false);
		assertEquals("--1",objFileTest.getExpansionNext());		
		objFileTest.setExpansionLength(4);
		assertEquals("---1",objFileTest.getExpansionNext());
		
		//#### 
		objFileTest.setExpansionLength(3);
		String sDirectory = objFileTest.getParent();
		String sReturn = objFileTest.PathNameTotalExpandedNextCompute(null, null);
		assertEquals( sDirectory + File.separator + "JUnitTest--1.txt", sReturn);
		//System.out.println("the expanded filename: '" + sReturn + "'");
}catch(ExceptionZZZ ez){
	fail("An exception happend testing: " + ez.getDetailAllLast());
}
	
	}
	
	/** void, Tests: Validating the result of computing a expansion. 
	 * a) Computing the biggest expansion number which could be created, by a given number of digits 
	* Lindhauer; 21.04.2006 09:51:08
	 */
	public void testExpansionLookalike(){
		try {
		//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
		//Zu beachten ist, das die Funktion einen String zur�ckliefert.
		
		assertEquals("99",FileExpansionZZZ.getExpansionMax(2));		
		assertEquals("999",FileExpansionZZZ.getExpansionMax(3));
		assertEquals("9999",FileExpansionZZZ.getExpansionMax(4));
		assertEquals("99999",FileExpansionZZZ.getExpansionMax(5));
		
		//Hier wird eine F�llvariable zur Berechnung verwendet.
		//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
		objFileTest.setExpansionLength(0);
		assertEquals("",objFileTest.ExpansionCompute("0",2));
		
		objFileTest.setExpansionLength(1);
		assertEquals("2",objFileTest.ExpansionCompute("0",2));
		
		objFileTest.setExpansionLength(3);
		assertEquals("002",objFileTest.ExpansionCompute("0",2));
		
		objFileTest.setExpansionLength(4);
		assertEquals("0002",objFileTest.ExpansionCompute("0",2));
		
		//Falls ein anderes F�llzeichen �bergeben werden soll
		// Hier 3 Unterstriche vor der Ziffer
		assertEquals("___2",objFileTest.ExpansionCompute("_",2));
		
		// Hier 2 Unterstriche und die Zahl ist 2 stellig
		assertEquals("__32",objFileTest.ExpansionCompute("_",32));
		
//		 Hier kein Unterstrich und die Zahl ist 4 stellig
		assertEquals("4321",objFileTest.ExpansionCompute("_",4321));
		
		
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	}
}
