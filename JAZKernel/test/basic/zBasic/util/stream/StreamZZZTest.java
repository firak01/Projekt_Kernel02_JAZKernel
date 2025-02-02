package basic.zBasic.util.stream;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.*;
import custom.zUtil.io.FileZZZ;


public class StreamZZZTest extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitStreamTest.ini");
	private final static String strFILE_NAME_ANSI_DEFAULT = new String("JUnitStreamANSITest.ini");
	private final static String strFILE_NAME_UTF8_DEFAULT = new String("JUnitStreamUTF8Test.ini");
	
	
	File objFileDEFAULT; File objFileANSI; File objFileUTF8;
	
	/// +++ Die eigentlichen Test-Objekte
	private StreamZZZ objStreamInit;
	private StreamZZZ objStream;
	
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehängt.
			//Merke: Es soll nicht versucht werden die Datei z.B. mit der Datei aus dem FileIniZZZTest 
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
			String sFilePathTotal = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				String sFilePathUsed = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
				if(FileEasyZZZ.exists(sFilePathUsed)) sFilePathTotal = sFilePathUsed;
			}
								
			if(sFilePathTotal==null){
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
			}
			objFileDEFAULT = new File(sFilePathTotal);
			//+++++++++++++++++++++++++++++++++++++++++++
			String sFilePathTotalANSI = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				String sFilePathUsed = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_ANSI_DEFAULT );
				if(FileEasyZZZ.exists(sFilePathUsed)) sFilePathTotalANSI = sFilePathUsed;
			}
								
			if(sFilePathTotalANSI==null){
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotalANSI = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_ANSI_DEFAULT );			   
			}
			objFileANSI = new File(sFilePathTotalANSI);
			//+++++++++++++++++++++++++++++++++++++++++++++
			String sFilePathTotalUTF8 = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				String sFilePathUsed = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_UTF8_DEFAULT );
				if(FileEasyZZZ.exists(sFilePathUsed)) sFilePathTotalUTF8 = sFilePathUsed;
			}
								
			if(sFilePathTotalUTF8==null){
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotalUTF8 = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_UTF8_DEFAULT );			   
			}
			objFileUTF8 = new File(sFilePathTotalUTF8);
			
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objStreamInit = new StreamZZZ();

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	
	public void testInit() {
		
		String stemp = objStreamInit.getCharsetName();
		assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp);
		
	}
	public void testWriteReadDEFAULT() {
		
		String sFilePathTotal = objFileDEFAULT.getAbsolutePath();
		
		try {
			FileEasyZZZ.removeFile(sFilePathTotal);//Am Anfang des Tests ggfs. vorhandene Datei löschen
						
			objStream = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file
			objStream.println(";This is a temporarily test file for StreamZZZTest DEFAULT encoding type of the operation system / environment.");      //Now the File is created. This is a comment line
			objStream.println(";SpecialCharacters:");
			objStream.println("äüöß");
			objStream.close();
			
			//Nun das Auslesen
			objStream = new StreamZZZ(sFilePathTotal, 0);  //This is not enough, to create the file
			String sLine = objStream.readLineLast();
			System.out.println(sLine); //Ausgabe der letzten Zeile mit den Spezialzeichen.
			objStream.close();
			
			//Kein weiterer Vergleich für DEFAULT

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (IOException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (Exception e) {
			fail("Method throws an exception." + e.getMessage());
		}
	}
	
	public void testWriteReadANSI() {
		
		String sFilePathTotal = objFileANSI.getAbsolutePath();
		
		try {		
			FileEasyZZZ.removeFile(sFilePathTotal);//Am Anfang des Tests ggfs. vorhandene Datei löschen
			
			String sEncoding = StreamZZZ.CharsetUsedZZZ.ANSI.getAbbreviation();	 
			objStream = new StreamZZZ(sFilePathTotal, 1, sEncoding);  //This is not enough, to create the file
			
			String sCharsetEnum = StreamZZZ.CharsetUsedZZZ.ANSI.getName();
			String sCharsetDescription = StreamZZZ.CharsetUsedZZZ.ANSI.getDescription();
			
			objStream.println(";This is a temporarily test file for StreamZZZTest.");      //Now the File is created. This is a comment line
			objStream.println(";The characerset used shoud be: '" + sEncoding + "'");      //Now the File is created. This is a comment line
			objStream.println(";This is defined in enum: '" + sCharsetEnum + "'");
			objStream.println(";Description: '" + sCharsetDescription + "'");
			objStream.println(";SpecialCharacters:");
			objStream.println("äüöß");
			objStream.close();
			
			//Nun das Auslesen
			objStream = new StreamZZZ(sFilePathTotal, 0,sEncoding);  //This is not enough, to create the file
			String sLine = objStream.readLineLast();
			System.out.println(sLine); //Ausgabe der letzten Zeile mit den Spezialzeichen.
			objStream.close();
			
			//Nun das falsche Auslesen
			sEncoding = StreamZZZ.CharsetUsedZZZ.UTF8.getAbbreviation();
			
			objStream = new StreamZZZ(sFilePathTotal, 0,sEncoding);  //This is not enough, to create the file
			String sLineWrong = objStream.readLineLast();
			System.out.println(sLineWrong); //Ausgabe der letzten Zeile mit den Spezialzeichen.
			objStream.close();
		
			//Vergleiche die beiden Strings
			assertFalse(sLine.equals(sLineWrong));
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (IOException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (Exception e) {
			fail("Method throws an exception." + e.getMessage());
		}
	}

	public void testWriteReadUTF8() {
	
		String sFilePathTotal = objFileUTF8.getAbsolutePath();
		
		try {	
			FileEasyZZZ.removeFile(sFilePathTotal);//Am Anfang des Tests ggfs. vorhandene Datei löschen
			
			String sEncoding = StreamZZZ.CharsetUsedZZZ.UTF8.getAbbreviation();	 
			objStream = new StreamZZZ(sFilePathTotal, 1, sEncoding);  //This is not enough, to create the file
			
			String sCharsetEnum = StreamZZZ.CharsetUsedZZZ.UTF8.getName();
			String sCharsetDescription = StreamZZZ.CharsetUsedZZZ.UTF8.getDescription();
			
			objStream.println(";This is a temporarily test file for StreamZZZTest.");      //Now the File is created. This is a comment line
			objStream.println(";The characerset used shoud be: '" + sEncoding + "'");      //Now the File is created. This is a comment line
			objStream.println(";This is defined in enum: '" + sCharsetEnum + "'");
			objStream.println(";Description: '" + sCharsetDescription + "'");
			objStream.println(";SpecialCharacters:");
			objStream.println("äüöß");
			objStream.close();
		
			//Nun das Auslesen
			objStream = new StreamZZZ(sFilePathTotal, 0,sEncoding);  //This is not enough, to create the file
			String sLine = objStream.readLineLast();
			System.out.println(sLine); //Ausgabe der letzten Zeile mit den Spezialzeichen.
			objStream.close();

			//Nun das falsche Auslesen
			sEncoding = StreamZZZ.CharsetUsedZZZ.ANSI.getAbbreviation();
			
			objStream = new StreamZZZ(sFilePathTotal, 0,sEncoding);  //This is not enough, to create the file
			String sLineWrong = objStream.readLineLast();
			System.out.println(sLineWrong); //Ausgabe der letzten Zeile mit den Spezialzeichen.
			objStream.close();
			
			//Vergleiche die beiden Strings
			assertFalse(sLine.equals(sLineWrong));
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (IOException e) {
			fail("Method throws an exception." + e.getMessage());
		} catch (Exception e) {
			fail("Method throws an exception." + e.getMessage());
		}
	}

}

