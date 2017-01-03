package zBasic.util.file.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ;

public class TxtReaderZZZTest  extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_SORTED_NAME_DEFAULT = new String("JUnitTest_sorted.txt");
	private final static String strFILE_UNSORTED_NAME_DEFAULT = new String("JUnitTest_unsorted.txt");
	private final static String strFILE_EMPTY_NAME_DEFAULT = new String("JUnitTest_empty.txt");
	
	
	private File objFileSorted;
	private File objFileUnsorted;
	private File objFileEmpty;
	
	private String sLineFirstForTest=null;
	private String sLineSecondForTest=null;
	
	/// +++ Die eigentlichen Test-Objekte
	private TxtReaderZZZ objReaderInit;
	private TxtReaderZZZ objReaderSorted;
	private TxtReaderZZZ objReaderUnsorted;
	private TxtReaderZZZ  objReaderEmpty;
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Eintr�ge werden immer neu geschrieben und nicht etwa angeh�ngt.
			//Merke: Es soll nicht versucht werden die Datei z.B. mit der Datei aus dem FileIniZZZTest 
			//Merke: Erst wenn es �berhaupt einen Test gibt, wird diese Datei erstellt
			String sFileSortedPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_SORTED_NAME_DEFAULT );
			String sFileUnsortedPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_UNSORTED_NAME_DEFAULT );
			String sFileEmptyPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_EMPTY_NAME_DEFAULT );
			
			sLineFirstForTest = ";This is a temporarily test file for TxtWriterZZZTest.";
			sLineSecondForTest = "#This text has sorted lines. Comment lines should be ignored.";
			
			Stream objStreamFile = new Stream(sFileSortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(sLineFirstForTest);      //Now the File is created. This is a comment line
			objStreamFile.println(sLineSecondForTest);
			objStreamFile.println("A Line");
			objStreamFile.println("B Line");			
			objStreamFile.println("C Line");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("D Line");
			objStreamFile.println("  ");  //empty line 
			objStreamFile.println("E Line");			
			objStreamFile.close();
			
			objStreamFile = new Stream(sFileUnsortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for TxtWriterZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println("#This text has unsorted lines. Comment lines should be ignored and when sorted: placed to the beginning.");
			objStreamFile.println("A Line original unsorted");			
			objStreamFile.println("C Line original unsorted");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("F Line original unsorted");
			objStreamFile.println("B Line original unsorted");			
			objStreamFile.println("D Line original unsorted");
			objStreamFile.println("  ");  //empty line 
			objStreamFile.println("E Line original unsorted");		
			objStreamFile.close();
			
			objStreamFile = new Stream(sFileEmptyPathTotal, 1);
			objStreamFile.close();
			
			
		
			
			objFileSorted = new File(sFileSortedPathTotal);
			objFileUnsorted = new File(sFileUnsortedPathTotal);
			objFileEmpty = new File(sFileEmptyPathTotal);
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objReaderInit = new TxtReaderZZZ(); 
			
			//The main objects used for testing
			String[] saFlag = {"IsFileSorted", "IgnoreCommentLine", "IgnoreEmptyLine"};
			objReaderSorted = new TxtReaderZZZ(objFileSorted, saFlag);
			objReaderUnsorted = new TxtReaderZZZ(objFileUnsorted);
			objReaderEmpty = new TxtReaderZZZ(objFileEmpty, (String) null);
			
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	
	
	public void testReadPositionLineFirst(){
		try{
			long lPosition = objReaderUnsorted.readPositionLineFirst("A Line original unsorted", 0);
			assertTrue(lPosition >= 1);
			
			lPosition = objReaderSorted.readPositionLineFirst("A Line original unsorted", 0);
			assertFalse(lPosition >= 1);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testReadLine(){
		try{
			long lStartPosition = this.sLineFirstForTest.length() + 2;  //+2 wg CR + LF
			
			String sLine = objReaderSorted.readLineByByte(lStartPosition);
			assertEquals(sLine, this.sLineSecondForTest);
			
			lStartPosition = this.sLineFirstForTest.length();
			sLine = objReaderSorted.readLineNextByByte(lStartPosition);
			assertEquals(sLine, this.sLineSecondForTest);
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Receiving the content of an .ini-section
	* Lindhauer; 23.04.2006 09:13:04
	 */
	public void testReadVectorString(){
		try{
			long lStartByte = 0;
			
			//This will test the reading of text
			Vector vec = objReaderEmpty.readVectorStringByByte(lStartByte);
			assertTrue(vec.size()==0);
			
			vec.clear();
			vec = objReaderSorted.readVectorStringByByte(lStartByte);
			assertTrue(vec.size() == 5);
			
			vec.clear();
			vec = objReaderUnsorted.readVectorStringByByte(lStartByte);
			assertTrue(vec.size() == 10);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
}//end class