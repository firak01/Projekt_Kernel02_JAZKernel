package zUtil.io;

import java.io.FileNotFoundException;
import java.io.IOException;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import junit.framework.TestCase;
import custom.zUtil.io.FileZZZ;

public class FileZZZTest extends TestCase {
	private FileZZZ objFileInit;
	private FileZZZ objFileTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.txt");
	

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erh�lt.
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.close();
			
			
			//An object just initialized
			objFileInit = new FileZZZ();

			//The main object used for testing
			objFileTest = new FileZZZ(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT, (String[]) null);
			
			//TestKonfiguration pr�fen
			assertFalse(objFileTest.getFlag("init")==true); //Nun w�re init falsch
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
		assertEquals("", objFileTest.getExpansionCurrent(3));
		
		// Now there should be a file available, but the ending ?
		assertEquals("", objFileTest.getExpansionCurrent(3));
		
		//Now get the next expansion
		assertEquals("001",objFileTest.getExpansionNext(3));				
		assertEquals("0001",objFileTest.getExpansionNext(4));
		
		objFileTest.setExpansionFilling('-');
		assertEquals("-", objFileTest.getExpansionFilling());
		
		//	Now get the next expansion
		assertEquals("--1",objFileTest.getExpansionNext(3));				
		assertEquals("---1",objFileTest.getExpansionNext(4));
		
		//#### 
		String sReturn = objFileTest.PathNameTotalExpandedNextCompute(null, null, 3);
		assertEquals( "c:\\fglKernel\\KernelTest\\JUnitTest--1.txt", sReturn);
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
		//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
		//Zu beachten ist, das die Funktion einen String zur�ckliefert.
		assertEquals("99",FileZZZ.getExpansionMax(2));
		assertEquals("999",FileZZZ.getExpansionMax(3));
		assertEquals("9999",FileZZZ.getExpansionMax(4));
		assertEquals("99999",FileZZZ.getExpansionMax(5));
		
		//Hier wird eine F�llvariable zur Berechnung verwendet.
		//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
		assertEquals("",objFileTest.ExpansionCompute("0",2,0));
		assertEquals("2",objFileTest.ExpansionCompute("0",2,1));
		assertEquals("002",objFileTest.ExpansionCompute("0",2,3));
		assertEquals("0002",objFileTest.ExpansionCompute("0",2,4));
		
		//Falls ein anderes F�llzeichen �bergeben werden soll
		// Hier 3 Unterstriche vor der Ziffer
		assertEquals("___2",objFileTest.ExpansionCompute("_",2,4));
		
		// Hier 2 Unterstriche und die Zahl ist 2 stellig
		assertEquals("__32",objFileTest.ExpansionCompute("_",32,4));
		
//		 Hier kein Unterstrich und die Zahl ist 4 stellig
		assertEquals("4321",objFileTest.ExpansionCompute("_",4321,4));
		
		
		
	}
}
