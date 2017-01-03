package zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;

public class FileTextParserZZZTest extends TestCase{
	private File objFileSource=null;
	private File objFileTarget=null;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitKernelFileParseTest.txt");

	
	private FileTextParserZZZ objParserTest = null;

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erh�lt.
			//Merke: Ddurch, dass man die Datei per stream - aufbaut, werden keine Eintr�ge an eine vorherige Datei angeh�ngt, sondern die Datei wird f�r jeden Test neu geschrieben.
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.println("erste zeile");      //Now the File is created
			objStreamFile.println("zweite zeile");   
			objStreamFile.println("dritte zeile");    
			objStreamFile.println("vierte zeile");   
			objStreamFile.println("f�nfte zeile");     
			objStreamFile.println("sechste zeile");      
			objStreamFile.println("sechste zeile ist doppelt");     
			objStreamFile.println("doppelte sechste zeile");     
			objStreamFile.println("erste zeile ist doppelt");  
			objStreamFile.println("doppelte erste zeile");    
			objStreamFile.println("noch eine doppelte erste zeile");   
			objStreamFile.close();
			
			this.objFileSource = new File(sFilePathTotal);
			
			sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, "JUnitKernelFileParseResult.txt" );
			this.objFileTarget = new File(sFilePathTotal);			
			
			//The main object used for testing
			objParserTest = new FileTextParserZZZ(objFileSource, (String[]) null);
			
		
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
	 
	
	public void testConstructor(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			FileTextParserZZZ objParserInit = new FileTextParserZZZ(null, saFlag);
			assertTrue(objParserInit.getFlag("init")==true); 
			
			
			//TestKonfiguration pr�fen
			assertFalse(objParserTest.getFlag("init")==true); //Nun w�re init falsch
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testLineNumberAll(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			Integer[] intaLine = objParserTest.readLineNumberAll(objRe);
			assertNotNull(intaLine);
			assertEquals(intaLine.length, 2);
			
			
			RE objRe2 = new RE(s2Search);
			Integer[] intaLine2 = objParserTest.readLineNumberAll(objRe2);
			assertNotNull(intaLine2);
			assertEquals(intaLine2.length, 4);
			
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testReplaceLine(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			int iLineReplaced = objParserTest.replaceLine(this.objFileTarget, objRe, "das war ein Test");
			assertEquals(2,iLineReplaced);
			
			RE objRe2 = new RE("^das war ein Test");
			iLineReplaced = objParserTest.replaceLine(this.objFileTarget, objRe2, "das war ein weiterer Test");
			assertEquals(2, iLineReplaced);
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testHasLine(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			boolean btemp = objParserTest.hasLine(objRe);
			assertTrue(btemp);
			
			objRe = new RE("^DAS GIBT ES NICHT");
			btemp = objParserTest.hasLine(objRe);
			assertFalse(btemp);
			
			//###################################
			btemp = objParserTest.hasLine("erste zeile", false);
			assertTrue(btemp);
			
			btemp = objParserTest.hasLine("das gibt es nicht", false);
			assertFalse(btemp);
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testAppendLine(){
		String s2Append = "Das soll die letzte Zeile werden";
		try{
			long ltemp = objParserTest.appendLine(objFileTarget, s2Append);
			assertTrue(ltemp >= 2); //Weil eine Zeil ja schon vorher drin stehen soll
			
			long ltemp2 = objParserTest.appendLine(objFileTarget, "Nein, das hier ist jetzt das allerletzte");
			assertTrue(ltemp2 > ltemp); //Weil eine Zeil ja schon vorher drin stehen soll
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}//END Class
