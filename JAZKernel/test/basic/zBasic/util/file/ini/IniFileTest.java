package basic.zBasic.util.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.*;
import custom.zUtil.io.FileZZZ;


public class IniFileTest extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.ini");
	
	private File objFile;
	
	
	/// +++ Die eigentlichen Test-Objekte
	private IniFile objIniFileInit;
	private IniFile objIniFileTest;
	

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

			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for FileIniTest.");      //Now the File is created. This is a comment line
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=Testvalue1");			
			objStreamFile.println("Testentry2=Testvalue2");
			objStreamFile.println("[Section B]");
			objStreamFile.println("Testentry1=Testvalue1");
			
			objStreamFile.println("#Don�t change the number of properties in this section, for testing reasons.");
			objStreamFile.println("#Don�t give the properties as value, for testing reasons.");
			objStreamFile.println("[Section empty testGetVariables]");
			objStreamFile.println("Property1=");
			objStreamFile.println("Property2=");
			objStreamFile.println("Property3=");
			objStreamFile.println("Property4=");
			objStreamFile.println("Property5=");
			
			objStreamFile.println("#Don�t change the number of properties in this section, for testing reasons.");
			objStreamFile.println("[Section testGetVariables]");
			objStreamFile.println("Property in ResultArray1=Testvalue1");			
			objStreamFile.println("Property in ResultArray2=Testvalue2");
			objStreamFile.println("Property in ResultArray3=Testvalue3");
			
			objStreamFile.println("#This section should be left empty for testing reasons.");
			objStreamFile.println("[Section empty]");
			
			objStreamFile.println("#This section should contain 'deutsche Umlaute' for testing reasons. READING");
			objStreamFile.println("[Section C]");
			objStreamFile.println("Testentry1=Testvalü1");
			objStreamFile.println("#This section should contain 'deutsche Umlaute' for testing reasons. WRITING");
			objStreamFile.println("[Section D]");
			objStreamFile.close();
			
			objFile = new File(sFilePathTotal);
			
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objIniFileInit = new IniFile();
			
			//The main object used for testing
			objIniFileTest = new IniFile(sFilePathTotal, false);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {		
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}		
	}//END setup
	
	/** void, Test: Receiving the content of an .ini-section
	* Lindhauer; 23.04.2006 09:13:04
	 */
	public void testGetVariables(){
		//This will test the number of entries in the section
		String[] saProperties = objIniFileTest.getVariables("Section testGetVariables");
		assertNotNull("Unexpected: The array of properties is null", saProperties);
		assertEquals(3, saProperties.length);
		
		//This will test the number of entries in a section, where no entry has a value !!!
		String[] saProperties2 = objIniFileTest.getVariables("Section empty testGetVariables");
		assertNotNull("Unexpected: The array of properties is null", saProperties2);
		assertEquals(5, saProperties2.length);
	}
		
	public void testGetValue(){
		
		//Hole einen Wert mit deutschen Umlauten
		String sValue1 = objIniFileTest.getValue("Section C", "Testentry1");
		assertEquals("Testvalü1",sValue1);
		assertEquals(9,sValue1.length());//Ein (heuristischer) Indikator für die "Nichtumcodierung" der Zeichen ist, dass die Länge des Strings gleich bleibt. 
	
	}
	
	public void testSetValue(){
		
		
		try{
			//Setze einen Wert mit deutschen Umlauten
			boolean bValueSet = objIniFileTest.setValue("Section D", "Testentry1","Testvalü1");
			assertTrue(bValueSet);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (IOException e) {		
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		}	
		
		String sValue1 = objIniFileTest.getValue("Section C", "Testentry1");
		assertEquals("Testvalü1",sValue1);
		assertEquals(9,sValue1.length());//Ein (heuristischer) Indikator für die "Nichtumcodierung" der Zeichen ist, dass die Länge des Strings gleich bleibt. 
	
		
		assertEquals(9,sValue1.length());//Ein (heuristischer) Indikator für die "Nichtumcodierung" der Zeichen ist, dass die Länge des Strings gleich bleibt. 
	}
	
	public void testGetSubject(){
		String[] saSection = objIniFileTest.getSubjects();
		assertNotNull("Unexpected: The array of sections is null", saSection);
		assertEquals(7, saSection.length);
	}
}
